package store.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product findNullableProductByName(String name) {
        return products.stream()
                .filter(p -> p.isNameEquals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean contains(String name) {
        return products.stream()
                .anyMatch(product -> product.isNameEquals(name));
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
                .filter(product -> !hasGeneralProduct(product))
                .map(Product::createEmptyGeneralProduct)
                .toList();

        return new Products(Stream.concat(generalProducts.stream(), soldOutGeneralProducts.stream()).toList());
    }

    private boolean hasGeneralProduct(Product product) {
        return products.stream()
                .filter(Product::isGeneralProduct)
                .anyMatch(compared -> compared.isNameEquals(product));
    }
}
