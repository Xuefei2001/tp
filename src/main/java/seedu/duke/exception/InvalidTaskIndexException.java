package seedu.duke.exception;

public class InvalidTaskIndexException extends Exception {
    private static final String MESSAGE = "[!] Task index '%d' does not correspond to any task!";

    private static final String INVALID_TASK_ID_MESSAGE = "[!] The task ID you entered is not valid...\n"
            + "It must be a non-negative number that is within the bounds of the tasklist!";

    @Override
    public String toString() {
        return INVALID_TASK_ID_MESSAGE;
    }

    public InvalidTaskIndexException(int index) {
        super(String.format(MESSAGE, index));
    }
}
