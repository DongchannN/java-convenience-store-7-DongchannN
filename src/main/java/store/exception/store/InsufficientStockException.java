package store.exception.store;

public class InsufficientStockException extends IllegalArgumentException {

    private static final String INSUFFICIENT_STOCK_MESSAGE = "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";

    public InsufficientStockException() {
        super(INSUFFICIENT_STOCK_MESSAGE);
    }
}
