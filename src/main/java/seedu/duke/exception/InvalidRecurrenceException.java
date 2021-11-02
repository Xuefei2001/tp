package seedu.duke.exception;

public class InvalidRecurrenceException extends Exception {
    private static final String MESSAGE = "Invalid parameter for recurrence inserted, '%s'.";

    private static final String INVALID_RECURRENCE_MESSAGE = "[!] The recurrence you entered is not valid...\n"
            + "The following are examples of valid recurrence: {None, Daily, Weekly, Monthly, Yearly}";

    @Override
    public String toString() {
        return INVALID_RECURRENCE_MESSAGE;
    }

    public InvalidRecurrenceException(String recurrence) {
        super(String.format(MESSAGE, recurrence));
    }
}
