package store;

import java.util.Map;
import store.model.PurchaseOrder;
import store.model.StoreRoom;
import store.util.ClosedQuestionsParser;
import store.util.PurchaseParser;
import store.util.RepeatableReader;
import store.util.StoreRoomLoader;
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
        StoreRoom storeRoom = StoreRoomLoader.initStoreRoom();
        outputView.printProducts(storeRoom);
        PurchaseOrder purchaseOrder = createPurchaseOrder(storeRoom);
        purchaseOrder = processOrderWithPromotions(purchaseOrder, storeRoom);
    }

    private PurchaseOrder createPurchaseOrder(StoreRoom storeRoom) {
        return RepeatableReader.handle(inputView::readItem, (userInput) -> {
            Map<String, Integer> items = PurchaseParser.parseInputItems(userInput);
            return PurchaseOrder.from(items, storeRoom);
        });
    }

    private PurchaseOrder processOrderWithPromotions(PurchaseOrder purchaseOrder, StoreRoom storeRoom) {
        PurchaseOrder result = purchaseOrder;
        for (Map.Entry<String, Integer> entry : purchaseOrder.getPurchaseOrder().entrySet()) {
            result = applyPromotionToProduct(result, storeRoom, entry.getKey(), entry.getValue());
        }
        return purchaseOrder;
    }

    private PurchaseOrder applyPromotionToProduct(PurchaseOrder purchaseOrder,
                                                  StoreRoom storeRoom,
                                                  String productName,
                                                  int buyAmount) {
        int fullPriceAmount = storeRoom.getNonPromotionalQuantity(productName, buyAmount);
        if (fullPriceAmount > 0) {
            purchaseOrder = purchaseOrder
                    .decreaseItemBuyAmount(productName, getDecreaseAmount(productName, fullPriceAmount));
        }
        if (storeRoom.canGetOneMore(productName, buyAmount)) {
            purchaseOrder = purchaseOrder.increaseItemBuyAmount(productName, getIncreaseAmount(productName));
        }
        return purchaseOrder;
    }

    private int getDecreaseAmount(String productName, int fullPriceAmount) {
        boolean isAgree = RepeatableReader.handle(() ->
                inputView.askFullPrice(productName, fullPriceAmount), ClosedQuestionsParser::parseAnswer);
        if (isAgree) {
            return fullPriceAmount;
        }
        return 0;
    }

    private int getIncreaseAmount(String productName) {
        boolean isAgree = RepeatableReader.handle(() ->
                inputView.askAddOne(productName), ClosedQuestionsParser::parseAnswer);
        if (isAgree) {
            return 1;
        }
        return 0;
    }
}
