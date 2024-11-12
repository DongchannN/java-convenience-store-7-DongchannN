package store.exception.reader;

public class FileReadException extends IllegalArgumentException {

    public FileReadException(FileReadErrorStatus fileReadErrorStatus) {
        super(fileReadErrorStatus.getMessage());
    }
}
