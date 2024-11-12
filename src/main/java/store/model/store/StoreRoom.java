package store.model.store;

import static store.exception.store.StoreErrorStatus.PRODUCT_PRICE_INCONSISTENT;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Map;
import store.exception.store.StoreException;
import store.model.store.product.Product;
import store.model.store.product.Products;

public class StoreRoom {
    private final Products generalProducts;
    private final Products promotionProducts;

    private StoreRoom(Products generalProducts, Products promotionProducts) {
        validatePriceConsistency(generalProducts, promotionProducts);
        this.generalProducts = generalProducts;
        this.promotionProducts = promotionProducts;
    }

    public static StoreRoom from(Products products) {
        Products promotionProducts = products.extractPromotionProducts();
        Products generalProducts = products.extractGeneralProducts();
        return new StoreRoom(generalProducts, promotionProducts);
    }

    public boolean hasProduct(String name) {
        return generalProducts.contains(name);
    }

    public boolean hasAvailableStock(String name, int buyAmount) {
        Product generalProduct = generalProducts.findNullableProductByName(name);
        Product promotionProduct = promotionProducts.findNullableProductByName(name);
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
        Product promotionProduct = promotionProducts.findNullableProductByName(name);
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
        Product promotionProduct = promotionProducts.findNullableProductByName(name);
        if (promotionProduct == null) {
            return false;
        }
        if (!promotionProduct.isPromotionPeriod(DateTimes.now()) || buyAmount + 1 > promotionProduct.getStock()) {
            return false;
        }
        return (buyAmount + 1) % promotionProduct.getPromotionUnit() == 0;
    }

    public void arrange(Map<String, Integer> purchasedItems) {
        purchasedItems.forEach(this::decreaseProductStock);
    }

    private void decreaseProductStock(String productName, int quantity) {
        int remainingQuantity = decreasePromotionProduct(productName, quantity);
        decreaseGeneralProduct(productName, remainingQuantity);
    }

    private int decreasePromotionProduct(String productName, int quantity) {
        Product promotionProduct = promotionProducts.findNullableProductByName(productName);

        if (!isValidPromotionProduct(promotionProduct)) {
            return quantity;
        }
        int decreaseAmount = Math.min(promotionProduct.getStock(), quantity);
        promotionProduct.decreaseStock(decreaseAmount);

        return quantity - decreaseAmount;
    }

    private boolean isValidPromotionProduct(Product promotionProduct) {
        return promotionProduct != null &&
                promotionProduct.isPromotionPeriod(DateTimes.now());
    }

    private void decreaseGeneralProduct(String productName, int quantity) {
        Product generalProduct = generalProducts.findNullableProductByName(productName);
        if (generalProduct != null) {
            generalProduct.decreaseStock(quantity);
        }
    }

    private void validatePriceConsistency(Products generalProducts, Products promotionProducts) {
        long priceNotMatched = promotionProducts.getProducts().stream()
                .filter(product -> {
                    Product generalProduct = generalProducts.findNullableProductByName(product.getName());
                    return generalProduct.getPrice() != product.getPrice();
                }).count();
        if (priceNotMatched > 0) {
            throw new StoreException(PRODUCT_PRICE_INCONSISTENT);
        }
    }

    public Products getGeneralProducts() {
        return generalProducts;
    }

    public Products getPromotionProducts() {
        return promotionProducts;
    }

    public int getProductPrice(String name) {
        Product product = generalProducts.findNullableProductByName(name);
        return product.getPrice();
    }
}
