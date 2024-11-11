package store.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PurchaseOrder {
    private final Map<String, Integer> purchaseOrder;

    private PurchaseOrder(Map<String, Integer> purchaseOrder) {
        this.purchaseOrder = new HashMap<>(purchaseOrder);
    }

    public static PurchaseOrder from(Map<String, Integer> items, StoreRoom storeRoom) {
        validateHasProduct(items.keySet(), storeRoom);
        validateHasStock(items, storeRoom);
        return new PurchaseOrder(items);
    }

    public PurchaseOrder decreaseBuyAmount(String name, int decreaseAmount) {
        HashMap<String, Integer> newPurchaseOrder = new HashMap<>(this.purchaseOrder);
        int currentBuyAmount = purchaseOrder.get(name);
        newPurchaseOrder.put(name, currentBuyAmount - decreaseAmount);
        return new PurchaseOrder(newPurchaseOrder);
    }

    public PurchaseOrder increaseBuyAmount(String name, int increaseAmount) {
        HashMap<String, Integer> newPurchaseOrder = new HashMap<>(this.purchaseOrder);
        int currentBuyAmount = purchaseOrder.get(name);
        newPurchaseOrder.put(name, currentBuyAmount + increaseAmount);
        return new PurchaseOrder(newPurchaseOrder);
    }

    private static void validateHasProduct(Set<String> productNames, StoreRoom storeRoom) {
        List<String> nonExistProducts = productNames.stream()
                .filter(productName -> !storeRoom.hasProduct(productName))
                .toList();
        if (!nonExistProducts.isEmpty()) {
            throw new IllegalArgumentException("");
        }
    }

    private static void validateHasStock(Map<String, Integer> products, StoreRoom storeRoom) {
        products.forEach((name, quantity) -> {
            if (!storeRoom.hasAvailableStock(name, quantity)) {
                throw new IllegalArgumentException("");
            }
        });
    }

    public Map<String, Integer> getPurchaseOrder() {
        return Collections.unmodifiableMap(purchaseOrder);
    }
}
