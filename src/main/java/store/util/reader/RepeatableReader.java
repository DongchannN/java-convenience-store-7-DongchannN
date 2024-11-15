package store.util.reader;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class RepeatableReader {
    public static <T> T handle(Supplier<String> viewMethod,
                               Function<String, T> converter,
                               Consumer<String> errorHandler) {
        while (true) {
            try {
                String userInput = viewMethod.get();
                return converter.apply(userInput);
            } catch (IllegalArgumentException e) {
                errorHandler.accept(e.getMessage());
            }
        }
    }
}
