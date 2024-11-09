package store;

import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final StoreApplication application = new StoreApplication(inputView, outputView);
        application.run();
    }
}
