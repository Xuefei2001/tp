package seedu.duke.exception;

public class ListFormatException extends Exception {

    private static final String INVALID_LIST_FORMAT_MESSAGE = "[!] Your list command is wrong...\n"
            + "Please follow one of the formats below:\n"
            + "-> list\n"
            + "-> list --[type, priority, recur] <argument>\n"
            + "-> list <task id>";

    @Override
    public String toString() {
        return INVALID_LIST_FORMAT_MESSAGE;
    }

}
