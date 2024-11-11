package store.model.order;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Map;
import java.util.stream.Collectors;
import store.model.store.StoreRoom;
import store.model.store.product.Product;

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

    public long calculateActualPrice() {
        long promotionalDiscount = calculatePromotionalDiscount();
        long membershipDiscount = calculateMembershipDiscount();
        long totalPrice = calculateTotalPrice();
        return totalPrice - (promotionalDiscount + membershipDiscount);
    }

    public long calculatePromotionalDiscount() {
        return getPromotionalProducts().entrySet().stream()
                .mapToLong(entry -> (long) storeRoom.getProductPrice(entry.getKey()) * entry.getValue())
                .sum();
    }

    public long calculateMembershipDiscount() {
        if (!hasMembership) {
            return 0;
        }
        long targetPrice = purchaseOrder.getPurchaseOrder().entrySet().stream()
                .filter(entry -> !getPromotionalProducts().containsKey(entry.getKey()))
                .mapToLong(entry -> (long) storeRoom.getProductPrice(entry.getKey()) * entry.getValue())
                .sum();
        return (long) (targetPrice * 0.3);
    }

    public long calculateTotalPrice() {
        return purchaseOrder.getPurchaseOrder().entrySet().stream()
                .mapToLong(entry -> (long) storeRoom.getProductPrice(entry.getKey()) * entry.getValue())
                .sum();
    }

    public long calculateProductPrice(String productName) {
        int buyAmount = purchaseOrder.getPurchaseOrder().get(productName);
        int productPrice = storeRoom.getProductPrice(productName);
        return (long) buyAmount * productPrice;
    }

    public long calculateTotalBuyAmount() {
        return purchaseOrder.getPurchaseOrder().values().stream()
                .mapToLong(Long::valueOf)
                .sum();
    }

    private int calculateGiveawayAmount(String productName, int quantity) {
        Product product = storeRoom.getPromotionProducts().findNullableProductByName(productName);
        if (product != null
                && product.isPromotionPeriod(DateTimes.now())
                && product.getStock() > product.getPromotionUnit()) {
            return quantity / product.getPromotionUnit();
        }
        return 0;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public Map<String, Integer> getPromotionalProducts() {
        return purchaseOrder.getPurchaseOrder().entrySet().stream()
                .map(entry -> Map.entry(
                        entry.getKey(),
                        calculateGiveawayAmount(entry.getKey(), entry.getValue())
                ))
                .filter(entry -> entry.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
