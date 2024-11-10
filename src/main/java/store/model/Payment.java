package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Map;
import java.util.stream.Collectors;

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

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public long getProductTotalPrice(String productName) {
        int buyAmount = purchaseOrder.getPurchaseOrder().get(productName);
        int productPrice = storeRoom.getProductPrice(productName);
        return (long) buyAmount * productPrice;
    }

    public long getTotalBuyAmount() {
        return purchaseOrder.getPurchaseOrder().values().stream()
                .mapToLong(Long::valueOf)
                .sum();
    }

    public long getTotalPrice() {
        return purchaseOrder.getPurchaseOrder().entrySet().stream()
                .mapToLong(entry -> (long) storeRoom.getProductPrice(entry.getKey()) * entry.getValue())
                .sum();
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

    public long getPromotionalDiscount() {
        return getPromotionalProducts().entrySet().stream()
                .mapToLong(entry -> (long) storeRoom.getProductPrice(entry.getKey()) * entry.getValue())
                .sum();
    }

    public long getMembershipDiscount() {
        if (!hasMembership) {
            return 0;
        }
        long targetPrice = purchaseOrder.getPurchaseOrder().entrySet().stream()
                .filter(entry -> !getPromotionalProducts().containsKey(entry.getKey()))
                .mapToLong(entry -> (long) storeRoom.getProductPrice(entry.getKey()) * entry.getValue())
                .sum();
        return (long) (targetPrice * 0.3);
    }

    public long getActualPrice() {
        long promotionalDiscount = getPromotionalDiscount();
        long membershipDiscount = getMembershipDiscount();
        long totalPrice = getTotalPrice();
        return totalPrice - (promotionalDiscount + membershipDiscount);
    }

    private int calculateGiveawayAmount(String productName, int quantity) {
        Product product = storeRoom.getPromotionsProducts().findNullableProductByName(productName);
        if (product != null
                && product.isPromotionPeriod(DateTimes.now())
                && product.getStock() > product.getPromotionUnit()) {
            return quantity / product.getPromotionUnit();
        }
        return 0;
    }
}
