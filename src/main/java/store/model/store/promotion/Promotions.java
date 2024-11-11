package store.model.store.promotion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<String> promotionNames = promotions.stream()
                .map(Promotion::getName)
                .collect(Collectors.toSet());
        if (promotionNames.size() != promotions.size()) {
            throw new IllegalArgumentException("프로모션의 이름은 중복될 수 없습니다.");
        }
    }
}
