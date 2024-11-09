package store;

import java.util.List;
import java.util.Map;
import store.model.Products;
import store.model.Promotions;
import store.model.StoreRoom;
import store.util.ProductConverter;
import store.util.PromotionConverter;
import store.util.PurchaseParser;
import store.util.StoreFileReader;
import store.view.InputView;
import store.view.OutputView;

public class StoreApplication {
    private final InputView inputView;
    private final OutputView outputView;

    public StoreApplication(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Promotions promotions = readPromotions();
        Products products = readProducts(promotions);
        StoreRoom storeRoom = StoreRoom.from(products);
        outputView.printProducts(storeRoom);
        String inputItems = inputView.readItem();
        Map<String, Integer> items = PurchaseParser.parseInputItems(inputItems);
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
