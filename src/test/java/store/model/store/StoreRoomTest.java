package store.model.store;

import static camp.nextstep.edu.missionutils.DateTimes.now;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.model.store.product.Product;
import store.model.store.product.Products;
import store.model.store.promotion.Promotion;

@DisplayName("가게 창소 모델에 대한 테스트")
class StoreRoomTest {

    @Nested
    @DisplayName("상품 존재 여부 테스트")
    class HasProductTest {
        @Test
        public void 존재하는_상품_프로모션재고만_있을시_TRUE_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            boolean result = storeRoom.hasProduct("사이다");

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        public void 존재하는_상품_일반재고만_있을시_TRUE_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            boolean result = storeRoom.hasProduct("국수");

            // then
            Assertions.assertThat(result).isTrue();
        }

        public void 상품_존재하지_않을시_FALSE_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            boolean result = storeRoom.hasProduct("라면");

            // then
            Assertions.assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("상품 개수 테스트")
    class HasAvailableStockTest {
        @Test
        public void 구입_개수_이상_재고_없을시_FALSE_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            boolean result = storeRoom.hasAvailableStock("사이다", 6);

            // then
            Assertions.assertThat(result).isFalse();
        }

        @Test
        public void 구입_개수_이상_재고_있을시_TRUE_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            boolean result = storeRoom.hasAvailableStock("사이다", 5);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        public void 프로모션_종료된_상품에_대해_카운팅_안함() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            boolean result = storeRoom.hasAvailableStock("우유", 3);

            // then
            Assertions.assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("프로모션 재고 초과시 정가 결제 관련 테스트")
    class AdditionalAmountTest {
        @Test
        public void 프로모션_재고_초과시_정가_개수_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            int result = storeRoom.getNonPromotionalQuantity("콜라", 15);

            // then
            Assertions.assertThat(result).isEqualTo(6);
        }

        @Test
        public void 프로모션_재고_미초과시_0_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            int result = storeRoom.getNonPromotionalQuantity("콜라", 9);

            // then
            Assertions.assertThat(result).isEqualTo(0);
        }

        @Test
        public void 프로모션_종료된_상품에_대해_0_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            int result = storeRoom.getNonPromotionalQuantity("우유", 6);

            // then
            Assertions.assertThat(result).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("혜택 안내 여부 관련 테스트")
    class LikelyToBenefit {
        @Test
        public void 혜택_받을수_있을시_TRUE_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            boolean result = storeRoom.canGetOneMore("콜라", 5);

            // then
            Assertions.assertThat(result).isTrue();
        }

        @Test
        public void 추가구매_필요시_FALSE_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            boolean result = storeRoom.canGetOneMore("콜라", 4);

            // then
            Assertions.assertThat(result).isFalse();
        }

        @Test
        public void 혜택_받았을시_FALSE_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            boolean result = storeRoom.canGetOneMore("콜라", 6);

            // then
            Assertions.assertThat(result).isFalse();
        }

        @Test
        public void 추가_구매해도_혜택_불가능시_FALSE_반환() {
            // given
            StoreRoom storeRoom = createStoreRoom();

            // when
            boolean result = storeRoom.canGetOneMore("콜라", 10);

            // then
            Assertions.assertThat(result).isFalse();
        }
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