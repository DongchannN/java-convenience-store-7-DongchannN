package store.exception.store;

public class NonExistProductException extends IllegalArgumentException {

    private static final String NON_EXIST_PRODUCT_MESSAGE = "존재하지 않는 상품입니다. 다시 입력해 주세요.";

    public NonExistProductException() {
        super(NON_EXIST_PRODUCT_MESSAGE);
    }
}
