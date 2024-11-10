package store.model;

import static camp.nextstep.edu.missionutils.DateTimes.now;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("사용자 구매 목록 모델 테스트")
class PurchaseOrderTest {

    @Test
    public void 보유_제품_재고_충분시_정상_생성() {
        // given
        StoreRoom storeRoom = createStoreRoom();
        Map<String, Integer> items = Map.of("사이다", 2
                , "콜라", 3
                , "우유", 2);

        // when
        PurchaseOrder purchaseOrder = PurchaseOrder.from(items, storeRoom);

        // then
        Assertions.assertThat(purchaseOrder).isNotNull();
    }

    @Test
    public void 미보유_제품시_예외_발생() {
        // given
        StoreRoom storeRoom = createStoreRoom();
        Map<String, Integer> items = Map.of("사이다", 2
                , "라면", 3
                , "우유", 2);

        // when, then
        Assertions.assertThatThrownBy(() -> PurchaseOrder.from(items, storeRoom))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 보유_제품_재고_부족시_예외_발생() {
        // given
        StoreRoom storeRoom = createStoreRoom();
        Map<String, Integer> items = Map.of("사이다", 2
                , "콜라", 300
                , "우유", 2);

        // when, then
        Assertions.assertThatThrownBy(() -> PurchaseOrder.from(items, storeRoom))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 제품_구매개수_감소시키기() {
        // given
        StoreRoom storeRoom = createStoreRoom();
        Map<String, Integer> items = Map.of("사이다", 2
                , "콜라", 3
                , "우유", 2);
        PurchaseOrder purchaseOrder = PurchaseOrder.from(items, storeRoom);

        // when
        purchaseOrder = purchaseOrder.decreaseItemBuyAmount("콜라", 1);

        // then
        Assertions.assertThat(purchaseOrder.getPurchaseOrder())
                .containsEntry("콜라", 2);
    }

    @Test
    public void 제품_구매개수_증가시키기() {
        // given
        StoreRoom storeRoom = createStoreRoom();
        Map<String, Integer> items = Map.of("사이다", 2
                , "콜라", 3
                , "우유", 2);
        PurchaseOrder purchaseOrder = PurchaseOrder.from(items, storeRoom);

        // when
        purchaseOrder = purchaseOrder.increaseItemBuyAmount("콜라", 2);

        // then
        Assertions.assertThat(purchaseOrder.getPurchaseOrder())
                .containsEntry("콜라", 5);
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
}