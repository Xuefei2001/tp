package seedu.tp.exception;

public class MissingUserMessageException extends Exception {
    private static final String MESSAGE = "You cannot leave reminder message empty";

    public String toString() {
        return MESSAGE;
    }

}
