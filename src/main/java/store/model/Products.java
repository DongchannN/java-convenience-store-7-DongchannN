package store.model;

import java.util.List;
import java.util.stream.Stream;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public Products getPromotionProducts() {
        return new Products(
                products.stream()
                        .filter(Product::isPromotionProduct)
                        .toList()
        );
    }

    public Products getGeneralProducts() {
        List<Product> generalProducts = this.products.stream()
                .filter(Product::isGeneralProduct)
                .toList();

        List<Product> soldOutGeneralProducts = getPromotionProducts().products.stream()
                .filter(product -> !hasGeneralProductByName(product.getName()))
                .map(product -> new Product(product.getName(), product.getPrice(), 0, null))
                .toList();

        return new Products(Stream.concat(generalProducts.stream(), soldOutGeneralProducts.stream()).toList());
    }

    private boolean hasGeneralProductByName(String productName) {
        return products.stream()
                .filter(Product::isGeneralProduct)
                .anyMatch(product -> product.getName().equals(productName));
    }
}
