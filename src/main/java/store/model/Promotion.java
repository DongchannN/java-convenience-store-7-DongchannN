package store.model;

import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final int buyAmount;
    private final int giveAmount;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    private Promotion(String name, int buyAmount, int giveAmount, LocalDateTime startAt, LocalDateTime endAt) {
        validateBuyAmount(buyAmount);
        validateGiveAmount(giveAmount);
        this.name = name;
        this.buyAmount = buyAmount;
        this.giveAmount = giveAmount;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static Promotion of(String name,
                               int buyAmount,
                               int giveAmount,
                               LocalDateTime startAt,
                               LocalDateTime endAt) {
        return new Promotion(name, buyAmount, giveAmount, startAt, endAt);
    }

    public boolean isNameEquals(String name) {
        return this.name.equals(name);
    }

    private void validateBuyAmount(int buyAmount) {
        if (buyAmount < 1) {
            throw new IllegalArgumentException("프로모션 구매 개수는 0 이상이여야 합니다.");
        }
    }

    private void validateGiveAmount(int giveAmount) {
        if (giveAmount != 1) {
            throw new IllegalArgumentException("프로모션 증정 개수는 1 이여야 합니다.");
        }
    }
}
