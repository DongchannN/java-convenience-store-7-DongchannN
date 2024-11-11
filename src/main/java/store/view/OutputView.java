package store.view;

import store.model.order.Payment;
import store.model.store.StoreRoom;
import store.view.formatter.OutputFormatter;

public class OutputView {

    public void printProducts(StoreRoom storeRoom) {
        System.out.println(OutputFormatter.buildStoreStock(storeRoom));
    }

    public void printReceipt(Payment payment) {
        System.out.println(OutputFormatter.buildReceipt(payment));
    }
}
