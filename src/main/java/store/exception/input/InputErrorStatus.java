package store.exception.input;

public enum InputErrorStatus {

    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요.");
    private final String message;

    InputErrorStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
