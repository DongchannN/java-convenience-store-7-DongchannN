package store.model;

import java.util.List;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public Products getGeneralProducts() {
        return new Products(
                products.stream()
                        .filter(Product::isGeneralProduct)
                        .toList()
        );
    }

    public Products getPromotionProducts() {
        return new Products(
                products.stream()
                        .filter(Product::isPromotionProduct)
                        .toList()
        );
    }
}
