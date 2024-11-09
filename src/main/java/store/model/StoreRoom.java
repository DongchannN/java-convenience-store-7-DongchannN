package store.model;

import camp.nextstep.edu.missionutils.DateTimes;

public class StoreRoom {
    private final Products generalProducts;
    private final Products promotionsProducts;

    private StoreRoom(Products generalProducts, Products promotionsProducts) {
        this.generalProducts = generalProducts;
        this.promotionsProducts = promotionsProducts;
    }

    public static StoreRoom from(Products products) {
        Products promotionProducts = products.getPromotionProducts();
        Products generalProducts = products.getGeneralProducts();
        return new StoreRoom(generalProducts, promotionProducts);
    }

    public boolean hasProduct(String name) {
        return generalProducts.contains(name);
    }

    public boolean hasAvailableStock(String name, int buyAmount) {
        Product generalProduct = generalProducts.findNullableProductByName(name);
        Product promotionProduct = promotionsProducts.findNullableProductByName(name);
        int totalStock = 0;
        if (generalProduct != null) {
            totalStock += generalProduct.getStock();
        }
        if (promotionProduct != null && promotionProduct.isPromotionPeriod(DateTimes.now())) {
            totalStock += promotionProduct.getStock();
        }
        return totalStock >= buyAmount;
    }

    public int getNonPromotionalQuantity(String name, int buyAmount) {
        Product promotionProduct = promotionsProducts.findNullableProductByName(name);
        if (promotionProduct == null) {
            return 0;
        }
        if (!promotionProduct.isPromotionPeriod(DateTimes.now()) || buyAmount < promotionProduct.getStock()) {
            return 0;
        }
        int promotionApplied = (promotionProduct.getStock() / promotionProduct.getPromotionUnit())
                * promotionProduct.getPromotionUnit();
        return buyAmount - promotionApplied;
    }

    public boolean canGetOneMore(String name, int buyAmount) {
        Product promotionProduct = promotionsProducts.findNullableProductByName(name);
        if (promotionProduct == null) {
            return false;
        }
        if (!promotionProduct.isPromotionPeriod(DateTimes.now()) || buyAmount + 1 > promotionProduct.getStock()) {
            return false;
        }
        return (buyAmount + 1) % promotionProduct.getPromotionUnit() == 0;
    }
}
