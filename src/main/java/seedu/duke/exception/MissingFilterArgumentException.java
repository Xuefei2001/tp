package seedu.duke.exception;

public class MissingFilterArgumentException extends Exception {

    private static final String MISSING_FILTER_ARGUMENT_MESSAGE = "[!] One of your filter's is missing an argument...\n"
            + "Please follow the format: list or list --[type, priority, recur] <argument>";

    @Override
    public String toString() {
        return MISSING_FILTER_ARGUMENT_MESSAGE;
    }

}
