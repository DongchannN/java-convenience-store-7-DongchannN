package store.exception.input;

public class InvalidInputException extends IllegalArgumentException {

    private static final String INVALID_INPUT_MESSAGE = "잘못된 입력입니다. 다시 입력해 주세요.";

    public InvalidInputException() {
        super(INVALID_INPUT_MESSAGE);
    }
}
