package store.model.store.promotion;

import static store.exception.store.StoreErrorStatus.INVALID_PROMOTION_BUY_AMOUNT;
import static store.exception.store.StoreErrorStatus.INVALID_PROMOTION_GIVE_AMOUNT;

import java.time.LocalDateTime;
import store.exception.store.StoreException;

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

    public boolean isPeriod(LocalDateTime now) {
        return startAt.isBefore(now) && endAt.isAfter(now);
    }

    private void validateBuyAmount(int buyAmount) {
        if (buyAmount < 1) {
            throw new StoreException(INVALID_PROMOTION_BUY_AMOUNT);
        }
    }

    private void validateGiveAmount(int giveAmount) {
        if (giveAmount != 1) {
            throw new StoreException(INVALID_PROMOTION_GIVE_AMOUNT);
        }
    }

    public String getName() {
        return name;
    }

    public int getUnit() {
        return buyAmount + giveAmount;
    }
}
