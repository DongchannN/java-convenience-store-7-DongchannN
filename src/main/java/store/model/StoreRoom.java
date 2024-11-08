package store.model;

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
}
