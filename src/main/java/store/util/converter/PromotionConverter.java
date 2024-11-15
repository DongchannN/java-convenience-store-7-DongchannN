package store.util.converter;

import static store.exception.reader.FileReadErrorStatus.*;
import static store.exception.reader.FileReadErrorStatus.INVALID_FILE_CONTENT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import store.exception.reader.FileReadErrorStatus;
import store.exception.reader.FileReadException;
import store.model.store.promotion.Promotion;
import store.model.store.promotion.Promotions;

public class PromotionConverter {
    private static final int PROMOTION_COLUMN_SIZE = 5;
    private static final int NAME_INDEX = 0;
    private static final int BUY_AMOUNT_INDEX = 1;
    private static final int GIVE_AMOUNT_INDEX = 2;
    private static final int START_AT_INDEX = 3;
    private static final int END_AT_INDEX = 4;

    public static Promotions convertToPromotions(List<List<String>> rawPromotions) {
        validateRawPromotions(rawPromotions);
        return new Promotions(
                rawPromotions.stream()
                        .map(PromotionConverter::convertToPromotion)
                        .toList()
        );
    }

    private static Promotion convertToPromotion(List<String> rawPromotion) {
        validateSize(rawPromotion);
        String name = rawPromotion.get(NAME_INDEX).trim();
        validateName(name);
        int buyAmount = parseInteger(rawPromotion.get(BUY_AMOUNT_INDEX));
        int giveAmount = parseInteger(rawPromotion.get(GIVE_AMOUNT_INDEX));
        LocalDateTime startAt = parseLocalDateTime(rawPromotion.get(START_AT_INDEX));
        LocalDateTime endAt = parseLocalDateTime(rawPromotion.get(END_AT_INDEX));

        return Promotion.of(name, buyAmount, giveAmount, startAt, endAt);
    }

    private static void validateRawPromotions(List<List<String>> rawPromotions) {
        if (rawPromotions == null || rawPromotions.isEmpty()) {
            throw new FileReadException(INVALID_FILE_CONTENT);
        }
    }

    private static void validateSize(List<String> rawPromotion) {
        if (rawPromotion == null || rawPromotion.isEmpty()) {
            throw new FileReadException(INVALID_FILE_CONTENT);
        }
        if (rawPromotion.size() != PROMOTION_COLUMN_SIZE) {
            throw new FileReadException(INVALID_COLUMN_CONTENT);
        }
    }

    private static void validateName(String name) {
        if (name.equals("null")) {
            throw new FileReadException(INVALID_COLUMN_CONTENT);
        }
    }

    private static int parseInteger(String rawNumber) {
        try {
            return Integer.parseInt(rawNumber.trim());
        } catch (NumberFormatException e) {
            throw new FileReadException(INVALID_COLUMN_CONTENT);
        }
    }

    private static LocalDateTime parseLocalDateTime(String rawDate) {
        try {
            LocalDate date = LocalDate.parse(rawDate.trim());
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            System.out.println(rawDate);
            throw new FileReadException(INVALID_COLUMN_CONTENT);
        }
    }
}
