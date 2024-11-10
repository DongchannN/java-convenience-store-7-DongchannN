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

    public boolean isNameEquals(Product product) {
        return this.name.equals(product.name);
    }

    public boolean isNameEquals(String name) {
        return this.name.equals(name);
    }

    public boolean isPromotionPeriod(LocalDateTime now) {
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
            throw new IllegalArgumentException("가격은 0 이하일 수 없습니다.");
        }
    }

    private void validateStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("재고는 음수일 수 없습니다.");
        }
    }
}
