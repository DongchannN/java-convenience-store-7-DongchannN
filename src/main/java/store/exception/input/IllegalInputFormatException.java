package store.exception.input;

public class IllegalInputFormatException extends IllegalArgumentException {

    private static final String ILLEGAL_INPUT_FORMAT_MESSAGE = "올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";

    public IllegalInputFormatException() {
        super(ILLEGAL_INPUT_FORMAT_MESSAGE);
    }
}
