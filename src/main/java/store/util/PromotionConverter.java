package store.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import store.model.Promotion;
import store.model.Promotions;

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
            throw new IllegalArgumentException(""); // todo : 예외 이름 생각하기
        }
    }

    private static void validateSize(List<String> rawPromotion) {
        if (rawPromotion == null || rawPromotion.isEmpty()) {
            throw new IllegalArgumentException(""); // todo : 예외 이름 생각하기
        }
        if (rawPromotion.size() != PROMOTION_COLUMN_SIZE) {
            throw new IllegalArgumentException(""); // todo : 예외 이름 생각하기
        }
    }

    private static void validateName(String name) {
        if (name.equals("null")) {
            throw new IllegalArgumentException(""); // todo : 예외 이름 생각하기
        }
    }

    private static int parseInteger(String rawNumber) {
        try {
            return Integer.parseInt(rawNumber.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(""); // todo : 예외 이름 생각하기
        }
    }

    private static LocalDateTime parseLocalDateTime(String rawDate) {
        try {
            LocalDate date = LocalDate.parse(rawDate.trim());
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            System.out.println(rawDate);
            throw new IllegalArgumentException(""); // todo : 예외 이름 생각하기
        }
    }
}
