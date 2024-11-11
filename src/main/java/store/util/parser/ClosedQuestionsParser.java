package store.util.parser;

import store.exception.input.InvalidInputException;

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
            throw new InvalidInputException();
        }
        if (!rawAnswer.equals("Y") && !rawAnswer.equals("N")) {
            throw new InvalidInputException();
        }
    }
}
