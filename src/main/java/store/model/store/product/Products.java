package store.model.store.product;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        validateDistinctGeneralProducts(products);
        validateDistinctPromotionProduct(products);
        this.products = products;
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

    public Products extractPromotionProducts() {
        return new Products(
                products.stream()
                        .filter(Product::isPromotionProduct)
                        .toList()
        );
    }

    public Products extractGeneralProducts() {
        List<Product> generalProducts = this.products.stream()
                .filter(Product::isGeneralProduct)
                .toList();

        List<Product> soldOutGeneralProducts = extractPromotionProducts().products.stream()
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

    private void validateDistinctGeneralProducts(List<Product> products) {
        long distinctCount = products.stream()
                .filter(Product::isGeneralProduct)
                .distinct().count();

        long totalCount = products.stream()
                .filter(Product::isGeneralProduct).count();
        if (distinctCount != totalCount) {
            throw new IllegalArgumentException("상품이 중복되었습니다.");
        }
    }

    private void validateDistinctPromotionProduct(List<Product> products) {
        long distinctCount = products.stream()
                .filter(Product::isPromotionProduct)
                .distinct().count();

        long totalCount = products.stream()
                .filter(Product::isPromotionProduct).count();
        if (distinctCount != totalCount) {
            throw new IllegalArgumentException("하나의 상품에 두개의 프로모션이 적용될 수 없습니다.");
        }
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
