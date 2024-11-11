package store.model;

import java.time.LocalDateTime;

public class Product {
    private final String name;
    private final int price;
    private int stock;
    private final Promotion promotion;

    private Product(String name, int price, int stock, Promotion promotion) {
        validatePrice(price);
        validateStock(stock);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public static Product of(String name, int price, int stock, Promotion promotion) {
        return new Product(name, price, stock, promotion);
    }

    public static Product createEmptyGeneralProduct(Product product) {
        return new Product(product.name, product.price, 0, null);
    }

    public void decreaseStock(int amount) {
        stock -= amount;
    }

    public boolean isNameEquals(Product product) {
        return this.name.equals(product.name);
    }

    public boolean isNameEquals(String name) {
        return this.name.equals(name);
    }

    public boolean isPromotionPeriod(LocalDateTime now) {
        if (this.promotion == null) {
            return false;
        }
        return promotion.isPeriod(now);
    }

    public boolean isGeneralProduct() {
        return promotion == null;
    }

    public boolean isPromotionProduct() {
        return promotion != null;
    }

    private void validatePrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException(""); // todo : 예외 메시지
        }
    }

    private void validateStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException(""); // todo : 예외 메시지
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getPromotionName() {
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }

    public int getPromotionUnit() {
        return promotion.getUnit();
    }
}
