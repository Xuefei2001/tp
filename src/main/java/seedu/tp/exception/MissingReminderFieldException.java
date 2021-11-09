package seedu.tp.exception;

public class MissingReminderFieldException extends Exception {
    private static final String MESSAGE = "You cannot leave both time and message fields empty.";

    public String toString() {
        return MESSAGE;
    }
}
