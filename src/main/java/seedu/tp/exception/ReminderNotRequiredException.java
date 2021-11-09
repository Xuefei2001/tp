package seedu.tp.exception;

//@@author Xuefei2001
/**
 * Exception to be used when user want to change the reminder for a task that do not have time constraint.
 */
public class ReminderNotRequiredException extends Exception {
    private static final String MESSAGE = "This task do not need reminder, hence cannot customize";

    public String toString() {
        return MESSAGE;
    }

}
