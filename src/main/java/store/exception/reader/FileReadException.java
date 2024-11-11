package store.exception.reader;

public class FileReadException extends IllegalArgumentException {

    private static final String FILE_READ_MESSAGE = "유효하지 않은 파일이거나 파일 형식입니다.";

    public FileReadException() {
        super(FILE_READ_MESSAGE);
    }
}
