package store.util;

import java.util.List;
import store.model.store.product.Products;
import store.model.store.promotion.Promotions;
import store.model.store.StoreRoom;
import store.util.converter.ProductConverter;
import store.util.converter.PromotionConverter;
import store.util.reader.StoreFileReader;

public class StoreRoomLoader {
    private StoreRoomLoader() {
    }

    public static StoreRoom initStoreRoom() {
        Promotions promotions = loadPromotions();
        Products products = loadProducts(promotions);
        return StoreRoom.from(products);
    }

    private static Promotions loadPromotions() {
        List<List<String>> rawPromotions = StoreFileReader.readFile("src/main/resources/promotions.md");
        Promotions promotions = PromotionConverter.convertToPromotions(rawPromotions);
        return promotions;
    }

    private static Products loadProducts(Promotions promotions) {
        List<List<String>> rawProducts = StoreFileReader.readFile("src/main/resources/products.md");
        Products products = ProductConverter.convertToProducts(rawProducts, promotions);
        return products;
    }
}
