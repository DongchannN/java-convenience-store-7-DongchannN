package store.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("물품 모델에 대한 테스트")
public class ProductTest {

    @Test
    public void 물품_정상_생성() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> new Product("hello", 100, 1, null));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -1000})
    public void 가격_0이하시_예외_발생(int price) {
        Assertions.assertThatThrownBy(() -> new Product("hello", price, 1, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 재고_음수시_예외_발생() {
        Assertions.assertThatThrownBy(() -> new Product("hello", 100, -1, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
