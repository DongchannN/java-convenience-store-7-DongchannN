package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public static final String PURCHASE_ORDER_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    public static final String NON_PROMOTION_APPLIED_MESSAGE = "현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    public static final String ONE_MORE_AVAILABLE_MESSAGE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    public static final String MEMBERSHIP_EXIST_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)";

    public String readItem() {
        System.out.println(PURCHASE_ORDER_MESSAGE);
        return Console.readLine();
    }

    public String readPromotionNonApplied(String productName, int nonPromotionApplied) {
        System.out.println(String.format(NON_PROMOTION_APPLIED_MESSAGE, productName, nonPromotionApplied));
        return Console.readLine();
    }

    public String readOneMoreAvailable(String productName) {
        System.out.println(String.format(ONE_MORE_AVAILABLE_MESSAGE, productName));
        return Console.readLine();
    }

    public String readMembershipExist() {
        System.out.println(MEMBERSHIP_EXIST_MESSAGE);
        return Console.readLine();
    }
}
