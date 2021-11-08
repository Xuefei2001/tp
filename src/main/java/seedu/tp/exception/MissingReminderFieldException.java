package seedu.tp.exception;

//@@author Xuefei2001
/**
 * Exception to be used when user input no fields for reminder command.
 */
public class MissingReminderFieldException extends Exception {
    private static final String MESSAGE = "You cannot leave both time and message fields empty.";

    public String toString() {
        return MESSAGE;
    }
}
