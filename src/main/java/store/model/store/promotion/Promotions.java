package store.model.store.promotion;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로모션이 있습니다."));
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
