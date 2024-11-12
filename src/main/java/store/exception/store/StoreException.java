package store.exception.store;

public class StoreException extends IllegalArgumentException {

    public StoreException(StoreErrorStatus storeErrorStatus) {
        super(storeErrorStatus.getMessage());
    }
}
