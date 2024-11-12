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
        return extractPromotionalProducts().entrySet().stream()
                .mapToLong(entry -> (long) storeRoom.getProductPrice(entry.getKey()) * entry.getValue())
                .sum();
    }

    public long calculateMembershipDiscount() {
        if (!hasMembership) {
            return 0;
        }
        long promotionAppliedPrice = extractPromotionalProducts().entrySet().stream()
                .mapToLong(entry -> {
                    Product product = storeRoom.getPromotionProducts().findNullableProductByName(entry.getKey());
                    return (long) product.getPrice() * entry.getValue() * product.getPromotionUnit();
                }).sum();
        long membershipDiscount = (long) ((calculateTotalPrice() - promotionAppliedPrice) * 0.3);
        return Math.min(8000L, membershipDiscount);
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
                && product.getStock() >= product.getPromotionUnit()) {
            int promotionalQuantity = quantity - storeRoom.getNonPromotionalQuantity(productName, quantity);
            return promotionalQuantity / product.getPromotionUnit();
        }
        return 0;
    }

    public Map<String, Integer> extractPromotionalProducts() {
        return purchaseOrder.getPurchaseOrder().entrySet().stream()
                .map(entry -> Map.entry(
                        entry.getKey(),
                        calculateGiveawayAmount(entry.getKey(), entry.getValue())
                ))
                .filter(entry -> entry.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }
}
