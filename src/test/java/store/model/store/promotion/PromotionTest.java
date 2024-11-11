package store.model.store.promotion;

import camp.nextstep.edu.missionutils.DateTimes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("프로모션 모델에 대한 테스트")
public class PromotionTest {

    @Test
    public void 프로모션_정상_생성() {
        Assertions.assertThatNoException()
                .isThrownBy(() ->
                        Promotion.of("hello", 1, 1, DateTimes.now(), DateTimes.now()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    public void 프로모션_구매개수_0이하시_예외_발생(int buyAmount) {
        Assertions.assertThatThrownBy(() ->
                        Promotion.of("hello", buyAmount, 1, DateTimes.now(), DateTimes.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 프로모션_증정개수_1아닐시_예외_발생() {
        Assertions.assertThatThrownBy(() ->
                        Promotion.of("hello", 1, 2, DateTimes.now(), DateTimes.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
