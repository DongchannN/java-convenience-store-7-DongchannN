package store;

import java.util.List;
import java.util.Map;
import store.model.Products;
import store.model.Promotions;
import store.model.PurchaseOrder;
import store.model.StoreRoom;
import store.util.DichotomousQuestionsParser;
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
        StoreRoom storeRoom = initStoreRoom();
        outputView.printProducts(storeRoom);
        PurchaseOrder purchaseOrder = createPurchaseOrder(storeRoom);
    }

    private StoreRoom initStoreRoom() {
        Promotions promotions = loadPromotions();
        Products products = loadProducts(promotions);
        return StoreRoom.from(products);
    }

    private Promotions loadPromotions() {
        List<List<String>> rawPromotions = StoreFileReader.readFile("src/main/resources/promotions.md");
        Promotions promotions = PromotionConverter.convertToPromotions(rawPromotions);
        return promotions;
    }

    private Products loadProducts(Promotions promotions) {
        List<List<String>> rawProducts = StoreFileReader.readFile("src/main/resources/products.md");
        Products products = ProductConverter.convertToProducts(rawProducts, promotions);
        return products;
    }

    private PurchaseOrder createPurchaseOrder(StoreRoom storeRoom) {
        while (true) {
            try {
                String userInput = inputView.readItem();
                Map<String, Integer> items = PurchaseParser.parseInputItems(userInput);
                return PurchaseOrder.from(items, storeRoom);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean askForNonPromotionalPurchase(String productName, int amount) {
        while (true) {
            try {
                String userInput = inputView.readPromotionNonApplied(productName, amount);
                return DichotomousQuestionsParser.parseAnswer(userInput);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean askForBuyOneGetOne(String productName) {
        while (true) {
            try {
                String userInput = inputView.readOneMoreAvailable(productName);
                return DichotomousQuestionsParser.parseAnswer(userInput);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean askForMembership() {
        while (true) {
            try {
                String userInput = inputView.readMembershipExist();
                return DichotomousQuestionsParser.parseAnswer(userInput);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
