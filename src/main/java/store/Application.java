package store;

import store.view.InputView;

public class Application {
    public static void main(String[] args) {
        final InputView inputView = new InputView();

        final StoreApplication application = new StoreApplication(inputView);
        application.run();
    }
}
