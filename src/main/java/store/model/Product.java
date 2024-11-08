package store.model;

public class Product {
    private final String name;
    private final int price;
    private final int stock;
    private final Promotion promotion;

    public Product(String name, int price, int stock, Promotion promotion) {
        validatePrice(price);
        validateStock(stock);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
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
