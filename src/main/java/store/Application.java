package store;

import store.model.store.StoreRoom;
import store.util.StoreRoomLoader;
import store.view.ErrorView;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final ErrorView errorView = new ErrorView();
        final StoreRoom storeRoom;

        try {
            storeRoom = StoreRoomLoader.initStoreRoom();
            final StoreApplication application = new StoreApplication(inputView, outputView, errorView, storeRoom);
            application.run();
        } catch (IllegalArgumentException e) {
            errorView.errorPage(e.getMessage());
        }
    }
}
