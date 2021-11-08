package seedu.tp.exception;

//@@author Xuefei2001
/**
 * Exception to be used when user put "--message" but no description for the message.
 */
public class MissingUserMessageException extends Exception {
    private static final String MESSAGE = "You cannot leave reminder message empty";

    public String toString() {
        return MESSAGE;
    }

}
