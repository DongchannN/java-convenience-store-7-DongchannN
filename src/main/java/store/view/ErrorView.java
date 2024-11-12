package store.view;

public class ErrorView {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public void errorPage(String message) {
        System.out.println(ERROR_PREFIX + message);
    }
}
