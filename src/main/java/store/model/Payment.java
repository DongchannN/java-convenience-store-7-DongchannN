package store.model;

public class Payment {
    private final PurchaseOrder purchaseOrder;
    private final StoreRoom storeRoom;
    private final boolean hasMembership;

    private Payment(PurchaseOrder purchaseOrder, StoreRoom storeRoom, boolean hasMembership) {
        this.purchaseOrder = purchaseOrder;
        this.storeRoom = storeRoom;
        this.hasMembership = hasMembership;
    }

    public static Payment from(PurchaseOrder purchaseOrder, StoreRoom storeRoom, boolean hasMembership) {
        return new Payment(purchaseOrder, storeRoom, hasMembership);
    }

    // public long getTotalPrice()

    // public Map<String, Integer> getPromotionalProducts()

    // public long getPromotionalDiscount()

    // public long getMembershipDiscount()

    // public long getActualPrice()
}
