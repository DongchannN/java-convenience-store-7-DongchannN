package store;

import store.model.store.StoreRoom;
import store.util.StoreRoomLoader;
import store.view.ErrorView;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        final ErrorView errorView = new ErrorView();
        final StoreRoom storeRoom;
        try {
            storeRoom = StoreRoomLoader.initStoreRoom();
            final StoreApplication application = new StoreApplication(new InputView(), new OutputView()
                    , errorView, storeRoom);
            application.run();
        } catch (IllegalArgumentException e) {
            errorView.errorPage(e.getMessage());
        }
    }
}
