package store.exception.reader;

public enum FileReadErrorStatus {

    INVALID_FILE_PATH("파일 경로가 유효하지 않습니다."),
    INVALID_FILE_CONTENT("파일 내부 형식을 다시 확인해주세요."),
    INVALID_COLUMN_CONTENT("파일의 컬럼을 다시 확인해주세요"),
    UNEXPECTED_IO_ERROR("I/O 처리 중 예외가 발생하였습니다.");

    private final String message;

    FileReadErrorStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
