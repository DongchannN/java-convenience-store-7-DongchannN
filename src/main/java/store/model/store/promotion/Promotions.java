package store.model.store.promotion;

import static store.exception.store.StoreErrorStatus.DUPLICATE_PROMOTION_NAME;
import static store.exception.store.StoreErrorStatus.NON_EXIST_PROMOTION;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import store.exception.store.StoreException;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        validateDistinct(promotions);
        this.promotions = promotions;
    }

    public Promotion findPromotionByName(String name) {
        if (name.equals("null")) {
            return null;
        }
        return promotions.stream()
                .filter(promotion -> promotion.isNameEquals(name))
                .findFirst()
                .orElseThrow(() -> new StoreException(NON_EXIST_PROMOTION));
    }

    private void validateDistinct(List<Promotion> promotions) {
        Set<String> promotionNames = promotions.stream()
                .map(Promotion::getName)
                .collect(Collectors.toSet());
        if (promotionNames.size() != promotions.size()) {
            throw new StoreException(DUPLICATE_PROMOTION_NAME);
        }
    }
}
