package store.model;

public class StoreRoom {
    private final Products generalProducts;
    private final Products promotionsProducts;

    private StoreRoom(Products generalProducts, Products promotionsProducts) {
        this.generalProducts = generalProducts;
        this.promotionsProducts = promotionsProducts;
    }

    public static StoreRoom from(Products products) {
        Products generalProducts = products.getGeneralProducts();
        Products promotionProducts = products.getPromotionProducts();
        return new StoreRoom(generalProducts, promotionProducts);
    }
}
