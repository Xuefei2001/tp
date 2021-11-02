package seedu.duke.exception;

public class TaskIsNonRecurringException extends Exception {

    private static final String TASK_IS_NON_RECURRING_MESSAGE = "[!] The task indicated is non-recurring, "
            + "please choose another task id...\n";

    @Override
    public String toString() {
        return TASK_IS_NON_RECURRING_MESSAGE;
    }

}
