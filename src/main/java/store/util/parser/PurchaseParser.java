package store.util.parser;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class PurchaseParser {

    private static final String PURCHASE_DELIMITER = ",";
    private static final String ITEM_DELIMITER = "-";
    private static final char START_OF_ITEM = '[';
    private static final char END_OF_ITEM = ']';

    private PurchaseParser() {
    }

    public static Map<String, Integer> parseInputItems(String rawInput) {
        return Arrays.stream(rawInput.split(PURCHASE_DELIMITER))
                .map(String::trim)
                .peek(PurchaseParser::validateElementFormat)
                .map(element -> element
                        .replace(String.valueOf(START_OF_ITEM), "")
                        .replace(String.valueOf(END_OF_ITEM), ""))
                .map(PurchaseParser::parseKeyValue)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map.Entry<String, Integer> parseKeyValue(String item) {
        String[] keyValue = item.split(ITEM_DELIMITER);
        validateKeyValuePair(keyValue);

        String productName = keyValue[0].trim();
        int buyAmount = parseInteger(keyValue[1].trim());
        validateBuyAmount(buyAmount);

        return Map.entry(productName, buyAmount);
    }

    private static int parseInteger(String rawQuantity) {
        try {
            return Integer.parseInt(rawQuantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(""); // todo : 예외 메시지
        }
    }

    private static void validateElementFormat(String element) {
        if (!(element.charAt(0) == START_OF_ITEM && element.charAt(element.length() - 1) == END_OF_ITEM)) {
            throw new IllegalArgumentException(""); // todo : 예외 메시지
        }
        String content = element.substring(1, element.length() - 1);
        if (content.contains(String.valueOf(START_OF_ITEM)) || content.contains(String.valueOf(END_OF_ITEM))) {
            throw new IllegalArgumentException(""); // todo : 예외 메시지
        }
    }

    private static void validateKeyValuePair(String[] keyValue) {
        if (keyValue.length != 2) {
            throw new IllegalArgumentException(""); // todo : 예외 메시지
        }
    }

    private static void validateBuyAmount(int buyAmount) {
        if (buyAmount <= 0) {
            throw new IllegalArgumentException(""); // todo : 예외 메시지
        }
    }
}
