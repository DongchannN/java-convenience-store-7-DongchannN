package store.util.converter;

import java.util.List;
import store.exception.reader.FileReadException;
import store.model.store.product.Product;
import store.model.store.product.Products;
import store.model.store.promotion.Promotions;

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

        return Product.of(name, price, stock, promotions.findNullablePromotionByName(promotionName));
    }

    private static void validateRawProducts(List<List<String>> rawPromotions) {
        if (rawPromotions == null || rawPromotions.isEmpty()) {
            throw new FileReadException();
        }
    }

    private static void validateSize(List<String> rawPromotion) {
        if (rawPromotion == null || rawPromotion.isEmpty()) {
            throw new FileReadException();
        }
        if (rawPromotion.size() != PROMOTION_COLUMN_SIZE) {
            throw new FileReadException();
        }
    }

    private static int parseInteger(String rawNumber) {
        try {
            return Integer.parseInt(rawNumber.trim());
        } catch (NumberFormatException e) {
            throw new FileReadException();
        }
    }
}
