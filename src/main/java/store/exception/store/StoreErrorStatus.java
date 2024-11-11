package store.exception.store;

public enum StoreErrorStatus {

    INSUFFICIENT_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    NON_EXIST_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    DUPLICATE_GENERAL_PRODUCT("상품이 중복되었습니다."),
    DUPLICATE_PROMOTION_PRODUCT("하나의 상품에 두개의 프로모션이 적용될 수 없습니다."),

    INVALID_PROMOTION_BUY_AMOUNT("구매 개수는 0 이하가 될 수 없습니다."),
    INVALID_PROMOTION_GIVE_AMOUNT("추가 증정은 하나만 가능합니다."),
    NON_EXIST_PROMOTION("존재하지 않는 프로모션이 있습니다."),
    DUPLICATE_PROMOTION_NAME("프로모션의 이름은 중복될 수 없습니다."),

    INVALID_PRODUCT_PRICE("가격은 0 이하가 될 수 없습니다."),
    INVALID_PRODUCT_STOCK("재고는 음수가 될 수 없습니다."),

    PRODUCT_PRICE_INCONSISTENT("일반 상품과 프로모션 상품의 가격은 동일해야합니다.");

    private final String message;

    StoreErrorStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
