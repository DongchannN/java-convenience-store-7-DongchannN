package store.util;

import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("사용자 입력 구매목록 파싱 테스트")
class PurchaseParserTest {

    @Test
    public void 사용자_정상_입력시_MAP_반환() {
        // given
        String rawInput = "[사이다-1], [콜라 - 2], [배 -3]";

        // when
        Map<String, Integer> purchases = PurchaseParser.parseUserPurchases(rawInput);

        // then
        Assertions.assertThat(purchases)
                .containsEntry("사이다", 1)
                .containsEntry("콜라", 2)
                .containsEntry("배", 3)
                .hasSize(3);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[사이다--1], [콜라 - 2], [배 -3]"
            , "사이다-1], [콜라 - 2], [배 -3]"
            , "[사이다-1], [콜라 - 2], [배 3]"
            , "[사이다-1], [콜라 - 2], 배 -3]"
            , "[사이다-1], 콜라 - 2, [배 -3]"
            , "[사이다-1], [콜라 - 2], [배3 -]"
    })
    public void 사용자_입력형식_이상시_예외_발생(String rawInput) {
        // when, then
        Assertions.assertThatThrownBy(() -> PurchaseParser.parseUserPurchases(rawInput))
                .isInstanceOf(IllegalArgumentException.class);
    }
}