package seedu.tp.exception;

public class NotALessonException extends Exception {
    private static final String MESSAGE = "The task is not a lesson.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
