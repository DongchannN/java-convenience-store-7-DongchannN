package store.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        validateDistinct(promotions);
        this.promotions = promotions;
    }

    public Promotion findNullablePromotionByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.isNameEquals(name))
                .findFirst()
                .orElse(null);
    }

    private void validateDistinct(List<Promotion> promotions) {
        Set<Promotion> promotionSet = new HashSet<>(promotions);
        if (promotionSet.size() != promotions.size()) {
            throw new IllegalArgumentException("");
        }
    }
}
