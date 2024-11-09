package store.view;

import store.model.Product;
import store.model.Products;
import store.model.StoreRoom;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";
    private static final String PRODUCT_PREFIX = "- ";

    public void printProducts(StoreRoom storeRoom) {
        System.out.println(WELCOME_MESSAGE);
        Products products = storeRoom.getGeneralProducts();
        Products promotionProduct = storeRoom.getPromotionsProducts();
        for (Product product : products.getProducts()) {
            if (promotionProduct.contains(product.getName())) {
                System.out.print(buildProductMessage(promotionProduct.findNullableProductByName(product.getName())));
            }
            System.out.print(buildProductMessage(product));
        }
        System.out.println();
    }

    private String buildProductMessage(Product product) {
        if (product == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(PRODUCT_PREFIX)
                .append(product.getName()).append(" ")
                .append(String.format("%,d원", product.getPrice())).append(" ")
                .append(buildProductStock(product.getStock())).append(" ")
                .append(product.getPromotionName())
                .append("\n");
        return sb.toString();
    }

    private String buildProductStock(int stock) {
        if (stock == 0) {
            return "재고 없음";
        }
        return String.format("%,d개", stock);
    }
}
