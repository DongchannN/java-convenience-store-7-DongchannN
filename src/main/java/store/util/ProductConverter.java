package store.util;

import java.util.List;
import store.model.Product;
import store.model.Products;
import store.model.Promotions;

public class ProductConverter {
    private static final int PROMOTION_COLUMN_SIZE = 4;
    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int STOCK_INDEX = 2;
    private static final int PROMOTION_INDEX = 3;

    public static Products convertToProducts(List<List<String>> rawProducts, Promotions promotions) {
        validateRawProducts(rawProducts);
        return new Products(
                rawProducts.stream()
                        .map(rawProduct -> convertToProduct(rawProduct, promotions))
                        .toList()
        );
    }

    private static Product convertToProduct(List<String> rawProduct, Promotions promotions) {
        validateSize(rawProduct);

        String name = rawProduct.get(NAME_INDEX).trim();
        int price = parseInteger(rawProduct.get(PRICE_INDEX));
        int stock = parseInteger(rawProduct.get(STOCK_INDEX));
        String promotionName = rawProduct.get(PROMOTION_INDEX).trim();

        return new Product(name, price, stock, promotions.findByName(promotionName));
    }

    private static void validateRawProducts(List<List<String>> rawPromotions) {
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

    private static int parseInteger(String rawNumber) {
        try {
            return Integer.parseInt(rawNumber.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(""); // todo : 예외 이름 생각하기
        }
    }
}
