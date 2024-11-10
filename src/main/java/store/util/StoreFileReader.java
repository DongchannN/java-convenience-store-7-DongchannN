package store.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
            throw new IllegalStateException("파일 읽기 중 오류가 발생했습니다: ");
        }
    }

    private static List<String> parseLine(String line) {
        return List.of(line.split(DELIMITER));
    }

    public static void validateFileReadable(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("읽을 수 없는 파일입니다.");
        }
        Path path = Paths.get(fileName);
        if (!(Files.exists(path) && Files.isReadable(path))) {
            throw new IllegalArgumentException("읽을 수 없는 파일입니다.");
        }
    }
}
