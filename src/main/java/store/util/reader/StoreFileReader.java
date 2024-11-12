package store.util.reader;

import static store.exception.reader.FileReadErrorStatus.INVALID_FILE_PATH;
import static store.exception.reader.FileReadErrorStatus.UNEXPECTED_IO_ERROR;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import store.exception.reader.FileReadException;

public class StoreFileReader {
    private static final String DELIMITER = ",";

    public static List<List<String>> readFile(String fileName) {
        validateFileReadable(fileName);
        Path path = Paths.get(fileName);
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return reader.lines().skip(1)
                    .filter(line -> line != null && !line.trim().isEmpty())
                    .map(StoreFileReader::parseLine)
                    .toList();
        } catch (IOException e) {
            throw new FileReadException(UNEXPECTED_IO_ERROR);
        }
    }

    private static List<String> parseLine(String line) {
        return List.of(line.split(DELIMITER));
    }

    public static void validateFileReadable(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new FileReadException(INVALID_FILE_PATH);
        }
        Path path = Paths.get(fileName);
        if (!(Files.exists(path) && Files.isReadable(path))) {
            throw new FileReadException(INVALID_FILE_PATH);
        }
    }
}
