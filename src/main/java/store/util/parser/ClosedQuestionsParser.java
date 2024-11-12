package store.util.parser;

import static store.exception.input.InputErrorStatus.INVALID_INPUT;

import store.exception.input.UserInputException;

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
            throw new UserInputException(INVALID_INPUT);
        }
        if (!rawAnswer.equals("Y") && !rawAnswer.equals("N")) {
            throw new UserInputException(INVALID_INPUT);
        }
    }
}
