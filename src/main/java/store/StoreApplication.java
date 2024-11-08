package store;

import java.util.List;
import store.model.Products;
import store.model.Promotions;
import store.util.ProductConverter;
import store.util.PromotionConverter;
import store.util.StoreFileReader;

public class StoreApplication {
    public void run() {
        Promotions promotions = readPromotions();
        Products products = readProducts(promotions);
    }

    private Promotions readPromotions() {
        List<List<String>> rawPromotions = StoreFileReader.readFile("src/main/resources/promotions.md");
        Promotions promotions = PromotionConverter.convertToPromotions(rawPromotions);
        return promotions;
    }

    private Products readProducts(Promotions promotions) {
        List<List<String>> rawProducts = StoreFileReader.readFile("src/main/resources/products.md");
        Products products = ProductConverter.convertToProducts(rawProducts, promotions);
        return products;
    }
}
