package seedu.duke.exception;

public class InvalidPriorityException extends Exception {
    private static final String MESSAGE = "Invalid parameter for priority inserted, '%s'.";

    private static final String INVALID_PRIORITY_MESSAGE = "[!] The priority you entered is not valid...\n"
            + "The following are examples of valid priorities: {Low, Medium, High}";

    @Override
    public String toString() {
        return INVALID_PRIORITY_MESSAGE;
    }

    public InvalidPriorityException(String priority) {
        super(String.format(MESSAGE, priority));
    }
}
