package store;

import java.util.Map;
import store.model.PurchaseOrder;
import store.model.StoreRoom;
import store.util.PurchaseParser;
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
}
