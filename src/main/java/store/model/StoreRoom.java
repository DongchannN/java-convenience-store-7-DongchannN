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

    // 물품 유무 확인하기 :: boolean hasProduct(String name)

    // 재고 있는지 확인하기(현재 진행중인 프로모션 재고 + 일반 재고) :: boolean hasAvailableStock(String name)

    // 몇개를 정가로 결제해야하는지 확인 :: int getAdditionalAmount(String name)

    // 혜택을 안내 할 것인지 말 것인지 :: boolean isLikelyToBenefit(String name, int buyAmount)
}
