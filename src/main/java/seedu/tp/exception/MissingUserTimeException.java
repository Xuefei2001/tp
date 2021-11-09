package seedu.tp.exception;

public class MissingUserTimeException extends Exception {
    private static final String MESSAGE = "You need to give a integer number for time field";

    public String toString() {
        return MESSAGE;
    }
}
