package seedu.tp.exception;

//@@author Xuefei2001
/**
 * Exception to be used when user put "--minute/day" but no number after the flags.
 */
public class MissingUserTimeException extends Exception {
    private static final String MESSAGE = "You need to give a integer number for time field";

    public String toString() {
        return MESSAGE;
    }
}
