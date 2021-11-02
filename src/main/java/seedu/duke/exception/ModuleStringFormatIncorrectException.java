package seedu.duke.exception;

public class ModuleStringFormatIncorrectException extends Exception {
    private static final String MESSAGE = "[!] The string you supplied cannot be parsed"
            + "as a valid list of module-class combinations.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
