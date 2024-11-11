package store.util.parser;

public class ClosedQuestionsParser {
    private ClosedQuestionsParser() {
    }

    public static boolean parseAnswer(String userInput) {
        String rawAnswer = userInput.trim();
        validateQuestionFormat(rawAnswer);
        return rawAnswer.equals("Y");
    }

    private static void validateQuestionFormat(String rawAnswer) {
        if (rawAnswer.length() != 1) {
            throw new IllegalArgumentException(""); // todo : 예외 메시지
        }
        if (!rawAnswer.equals("Y") && !rawAnswer.equals("N")) {
            throw new IllegalArgumentException(""); // todo : 예외 메시지
        }
    }
}
