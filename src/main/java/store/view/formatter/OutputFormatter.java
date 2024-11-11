package store.view.formatter;

import store.model.order.Payment;
import store.model.store.StoreRoom;
import store.model.store.product.Product;
import store.model.store.product.Products;

public class OutputFormatter {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";
    private static final String PRODUCT_PREFIX = "- ";
    private static final String SOLD_OUT = "재고 없음";
    private static final String RECEIPT_START_MESSAGE = "==============W 편의점================";
    private static final String RECEIPT_COLUMN_MESSAGE = "상품명\t\t수량\t금액";
    private static final String RECEIPT_PURCHASE_MESSAGE_FORMAT = "%s\t\t%,d \t%,d";
    private static final String RECEIPT_GIVEAWAY_MESSAGE = "=============증\t정===============";
    private static final String RECEIPT_GIVEAWAY_PRODUCT_MESSAGE_FORMAT = "%s\t\t%,d";
    private static final String RECEIPT_LINE_SEPARATOR = "====================================";
    private static final String RECEIPT_TOTAL_PRICE_MESSAGE_FORMAT = "총구매액\t\t%,d\t%,d";
    private static final String RECEIPT_PROMOTION_DISCOUNT_MESSAGE_FORMAT = "행사할인\t\t\t-%,d";
    private static final String RECEIPT_MEMBERSHIP_DISCOUNT_MESSAGE_FORMAT = "멤버십할인\t\t\t-%,d";
    private static final String RECEIPT_ACTUAL_PRICE_MESSAGE_FORMAT = "내실돈\t\t\t%,d";
    private static final String SYSTEM_LINE_SEPARATOR = System.lineSeparator();

    private OutputFormatter() {
    }

    public static String buildStoreStock(StoreRoom storeRoom) {
        StringBuilder sb = new StringBuilder();
        sb.append(WELCOME_MESSAGE).append(SYSTEM_LINE_SEPARATOR).append(SYSTEM_LINE_SEPARATOR);

        String menus = buildProducts(storeRoom.getGeneralProducts(), storeRoom.getPromotionProducts());
        sb.append(menus);
        return sb.toString();
    }

    public static String buildReceipt(Payment payment) {
        StringBuilder sb = new StringBuilder();
        sb.append(buildReceiptHeader())
                .append(buildPurchaseItems(payment))
                .append(buildGiveawayIems(payment))
                .append(buildReceiptSummary(payment));
        return sb.toString();
    }

    private static String buildProducts(Products generalProducts, Products promotionProducts) {
        StringBuilder sb = new StringBuilder();
        for (Product product : generalProducts.getProducts()) {
            if (promotionProducts.contains(product.getName())) {
                sb.append(buildProductMessage(promotionProducts.findNullableProductByName(product.getName())));
            }
            sb.append(buildProductMessage(product));
        }
        return sb.toString();
    }

    private static String buildProductMessage(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append(PRODUCT_PREFIX)
                .append(product.getName()).append(" ")
                .append(String.format("%,d원", product.getPrice())).append(" ")
                .append(buildProductStock(product.getStock())).append(" ")
                .append(product.getPromotionName()).append(SYSTEM_LINE_SEPARATOR);
        return sb.toString();
    }

    private static String buildProductStock(int stock) {
        if (stock == 0) {
            return SOLD_OUT;
        }
        return String.format("%,d개", stock);
    }

    private static String buildReceiptHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(RECEIPT_START_MESSAGE).append(SYSTEM_LINE_SEPARATOR)
                .append(RECEIPT_COLUMN_MESSAGE).append(SYSTEM_LINE_SEPARATOR);
        return sb.toString();
    }

    private static String buildPurchaseItems(Payment payment) {
        StringBuilder sb = new StringBuilder();
        payment.getPurchaseOrder().getPurchaseOrder().forEach((name, quantity) ->
                sb.append(String.format(RECEIPT_PURCHASE_MESSAGE_FORMAT,
                                name, quantity, payment.calculateProductPrice(name)))
                        .append(SYSTEM_LINE_SEPARATOR)
        );
        return sb.toString();
    }

    private static String buildGiveawayIems(Payment payment) {
        StringBuilder sb = new StringBuilder();
        sb.append(RECEIPT_GIVEAWAY_MESSAGE).append(SYSTEM_LINE_SEPARATOR);

        payment.extractPromotionalProducts().forEach((name, quantity) ->
                sb.append(String.format(RECEIPT_GIVEAWAY_PRODUCT_MESSAGE_FORMAT, name, quantity))
                        .append(SYSTEM_LINE_SEPARATOR)
        );
        return sb.toString();
    }

    private static String buildReceiptSummary(Payment payment) {
        StringBuilder sb = new StringBuilder();
        sb.append(RECEIPT_LINE_SEPARATOR).append(SYSTEM_LINE_SEPARATOR)
                .append(buildTotalPrice(payment))
                .append(buildDiscounts(payment))
                .append(buildFinalPrice(payment));
        return sb.toString();
    }

    private static String buildTotalPrice(Payment payment) {
        return String.format(RECEIPT_TOTAL_PRICE_MESSAGE_FORMAT,
                payment.calculateTotalBuyAmount(), payment.calculateTotalPrice())
                + SYSTEM_LINE_SEPARATOR;
    }

    private static String buildDiscounts(Payment payment) {
        return String.format(RECEIPT_PROMOTION_DISCOUNT_MESSAGE_FORMAT,
                payment.calculatePromotionalDiscount())
                + SYSTEM_LINE_SEPARATOR
                + String.format(RECEIPT_MEMBERSHIP_DISCOUNT_MESSAGE_FORMAT,
                payment.calculateMembershipDiscount())
                + SYSTEM_LINE_SEPARATOR;
    }

    private static String buildFinalPrice(Payment payment) {
        return String.format(RECEIPT_ACTUAL_PRICE_MESSAGE_FORMAT,
                payment.calculateActualPrice())
                + SYSTEM_LINE_SEPARATOR;
    }
}
