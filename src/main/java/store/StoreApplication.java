package store;

import java.util.Map;
import store.model.order.Payment;
import store.model.order.PurchaseOrder;
import store.model.store.StoreRoom;
import store.util.parser.ClosedQuestionsParser;
import store.util.parser.PurchaseParser;
import store.util.reader.RepeatableReader;
import store.view.ErrorView;
import store.view.InputView;
import store.view.OutputView;

public class StoreApplication {
    private final InputView inputView;
    private final OutputView outputView;
    private final ErrorView errorView;
    private final StoreRoom storeRoom;

    public StoreApplication(InputView inputView, OutputView outputView, ErrorView errorView, StoreRoom storeRoom) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.errorView = errorView;
        this.storeRoom = storeRoom;
    }

    public void run() {
        while (true) {
            outputView.printProducts(storeRoom);
            processPurchase();
            boolean buyMore = RepeatableReader.handle(inputView::askAdditionalPurchase,
                    ClosedQuestionsParser::parseAnswer, errorView::errorPage);
            if (!buyMore) {
                return;
            }
        }
    }

    private void processPurchase() {
        PurchaseOrder purchaseOrder = createPurchaseOrder(storeRoom);
        purchaseOrder = adjustOrderWithPromotions(purchaseOrder, storeRoom);
        if (purchaseOrder.getTotalBuyAmount() <= 0) {
            return;
        }
        boolean hasMembership = RepeatableReader.handle(inputView::askMembership, ClosedQuestionsParser::parseAnswer,
                errorView::errorPage);
        Payment payment = Payment.from(purchaseOrder, storeRoom, hasMembership);
        outputView.printReceipt(payment);
        storeRoom.arrange(payment.getPurchaseOrder().getPurchaseOrder());
    }

    private PurchaseOrder createPurchaseOrder(StoreRoom storeRoom) {
        return RepeatableReader.handle(inputView::readItem, (userInput) -> {
            Map<String, Integer> items = PurchaseParser.parseInputItems(userInput);
            return PurchaseOrder.from(items, storeRoom);
        }, errorView::errorPage);
    }

    private PurchaseOrder adjustOrderWithPromotions(PurchaseOrder purchaseOrder, StoreRoom storeRoom) {
        PurchaseOrder result = purchaseOrder;
        for (Map.Entry<String, Integer> entry : purchaseOrder.getPurchaseOrder().entrySet()) {
            result = applyPromotionToProduct(result, storeRoom, entry.getKey(), entry.getValue());
        }
        return result;
    }

    private PurchaseOrder applyPromotionToProduct(PurchaseOrder purchaseOrder,
                                                  StoreRoom storeRoom,
                                                  String productName,
                                                  int buyAmount) {
        int fullPriceAmount = storeRoom.getNonPromotionalQuantity(productName, buyAmount);
        if (fullPriceAmount > 0) {
            purchaseOrder = purchaseOrder
                    .decreaseBuyAmount(productName, getDecreaseAmount(productName, fullPriceAmount));
        }
        if (storeRoom.canGetOneMore(productName, buyAmount)) {
            purchaseOrder = purchaseOrder.increaseBuyAmount(productName, getIncreaseAmount(productName));
        }
        return purchaseOrder;
    }

    private int getDecreaseAmount(String productName, int fullPriceAmount) {
        boolean isAgree = RepeatableReader.handle(() ->
                        inputView.askFullPrice(productName, fullPriceAmount), ClosedQuestionsParser::parseAnswer,
                errorView::errorPage);
        if (!isAgree) {
            return fullPriceAmount;
        }
        return 0;
    }

    private int getIncreaseAmount(String productName) {
        boolean isAgree = RepeatableReader.handle(() ->
                inputView.askAddOne(productName), ClosedQuestionsParser::parseAnswer, errorView::errorPage);
        if (isAgree) {
            return 1;
        }
        return 0;
    }
}
