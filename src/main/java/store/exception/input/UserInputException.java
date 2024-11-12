package store.exception.input;

public class UserInputException extends IllegalArgumentException {

    public UserInputException(InputErrorStatus inputErrorStatus) {
        super(inputErrorStatus.getMessage());
    }
}
