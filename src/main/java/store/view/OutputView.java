package store.view;

import store.model.Payment;
import store.model.StoreRoom;

public class OutputView {

    public void printProducts(StoreRoom storeRoom) {
        System.out.println(OutputFormatter.buildStoreStock(storeRoom));
    }

    public void printReceipt(Payment payment) {
        System.out.println(OutputFormatter.buildReceipt(payment));
    }
}
