package store;

import store.model.store.StoreRoom;
import store.util.StoreRoomLoader;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final StoreRoom storeRoom = StoreRoomLoader.initStoreRoom();

        final StoreApplication application = new StoreApplication(inputView, outputView, storeRoom);
        application.run();
    }
}
