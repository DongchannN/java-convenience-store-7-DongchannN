package store.model;

import static camp.nextstep.edu.missionutils.DateTimes.now;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("결제 관련 모델 테스트")
class PaymentTest {
    private PurchaseOrder purchaseOrder;
    private StoreRoom storeRoom;

    @BeforeEach
    public void setUp() {
        storeRoom = createStoreRoom();
        purchaseOrder = PurchaseOrder.from(createItems(), storeRoom);
    }

    @Test
    public void 총_구매_금액_반환_성공() {
        // given
        boolean hasMembership = false;
        Payment payment = Payment.from(purchaseOrder, storeRoom, hasMembership);

        // when
        long totalPrice = payment.getTotalPrice();

        // then
        Assertions.assertThat(totalPrice).isEqualTo(6600L);
    }

    @Test
    public void 증정받은_상품들_반환_성공() {
        // given
        boolean hasMembership = false;
        Payment payment = Payment.from(purchaseOrder, storeRoom, hasMembership);

        // when
        Map<String, Integer> giveaways = payment.getPromotionalProducts();

        // then
        Assertions.assertThat(giveaways)
                .hasSize(2)
                .containsEntry("사이다", 1)
                .containsEntry("콜라", 1);
    }

    @Test
    public void 행사_할인_금액_반환_성공() {
        // given
        boolean hasMembership = false;
        Payment payment = Payment.from(purchaseOrder, storeRoom, hasMembership);

        // when
        long promotionDiscount = payment.getPromotionalDiscount();

        // then
        Assertions.assertThat(promotionDiscount).isEqualTo(1800L);
    }

    @Test
    public void 멤버십_있을시_맴버십_할인_금액_반환_성공() {
        // given
        boolean hasMembership = true;
        Payment payment = Payment.from(purchaseOrder, storeRoom, hasMembership);

        // when
        long membershipDiscount = payment.getMembershipDiscount();

        // then
        Assertions.assertThat(membershipDiscount).isEqualTo(600L);
    }

    @Test
    public void 멤버십_없을시_맴버십_할인_금액_반환_성공() {
        // given
        boolean hasMembership = false;
        Payment payment = Payment.from(purchaseOrder, storeRoom, hasMembership);

        // when
        long membershipDiscount = payment.getMembershipDiscount();

        // then
        Assertions.assertThat(membershipDiscount).isEqualTo(0L);
    }

    @Test
    public void 맴버십_있을시_실제_구매_금액_반환_성공() {
        // given
        boolean hasMembership = true;
        Payment payment = Payment.from(purchaseOrder, storeRoom, hasMembership);

        // when
        long actualPrice = payment.getActualPrice();

        // then
        Assertions.assertThat(actualPrice).isEqualTo(4200L);
    }

    @Test
    public void 맴버십_없을시_실제_구매_금액_반환_성공() {
        // given
        boolean hasMembership = false;
        Payment payment = Payment.from(purchaseOrder, storeRoom, hasMembership);

        // when
        long actualPrice = payment.getActualPrice();

        // then
        Assertions.assertThat(actualPrice).isEqualTo(4800L);
    }

    @Test
    public void 프로모션_재고_완전_부족시_맴버십_혜택_있음() {
        // given
        Map<String, Integer> items = Map.of("사이다", 2
                , "콜라", 3
                , "우유", 2);
        StoreRoom storeRoom = createStoreRoom2();
        PurchaseOrder purchaseOrder = PurchaseOrder.from(items, storeRoom);
        Payment payment = Payment.from(purchaseOrder, storeRoom, true);

        // when
        long promotionDiscount = payment.getPromotionalDiscount();
        long membershipDiscount = payment.getMembershipDiscount();
        long actualPrice = payment.getActualPrice();

        // then
        Assertions.assertThat(promotionDiscount).isEqualTo(800L);
        Assertions.assertThat(membershipDiscount).isEqualTo(1500L);
        Assertions.assertThat(actualPrice).isEqualTo(4300L);
    }

    private Map<String, Integer> createItems() {
        return Map.of("사이다", 2
                , "콜라", 3
                , "우유", 2);
    }

    private StoreRoom createStoreRoom() {
        Promotion twoPlusOne = Promotion.of("2+1", 2, 1, now(), now().plusDays(2));
        Promotion onePlusOne = Promotion.of("1+1", 1, 1, now(), now().plusDays(2));
        Promotion endPromotion = Promotion
                .of("1++1", 1, 1, now().minusDays(7), now().minusDays(2));

        Product generalCoke = Product.of("콜라", 1000, 10, null);
        Product promotionCoke = Product.of("콜라", 1000, 10, twoPlusOne);
        Product promotionCider = Product.of("사이다", 800, 5, onePlusOne);
        Product generalNoodle = Product.of("국수", 1500, 5, null);
        Product generalMilk = Product.of("우유", 1000, 2, null);
        Product promotionMilk = Product.of("우유", 1000, 7, endPromotion);

        Products products = new Products(List
                .of(generalCoke, promotionCoke, promotionCider, generalNoodle, generalMilk, promotionMilk));
        return StoreRoom.from(products);
    }

    private StoreRoom createStoreRoom2() {
        Promotion twoPlusOne = Promotion.of("2+1", 2, 1, now(), now().plusDays(2));
        Promotion onePlusOne = Promotion.of("1+1", 1, 1, now(), now().plusDays(2));
        Promotion endPromotion = Promotion
                .of("1++1", 1, 1, now().minusDays(7), now().minusDays(2));

        Product generalCoke = Product.of("콜라", 1000, 10, null);
        Product promotionCoke = Product.of("콜라", 1000, 1, twoPlusOne);
        Product promotionCider = Product.of("사이다", 800, 5, onePlusOne);
        Product generalNoodle = Product.of("국수", 1500, 5, null);
        Product generalMilk = Product.of("우유", 1000, 2, null);
        Product promotionMilk = Product.of("우유", 1000, 7, endPromotion);

        Products products = new Products(List
                .of(generalCoke, promotionCoke, promotionCider, generalNoodle, generalMilk, promotionMilk));
        return StoreRoom.from(products);
    }
}
