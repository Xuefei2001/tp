package seedu.tp.task.reminder;

public class ReminderInformation {
    private boolean reminderDone;
    private long userTime = 10;
    private String message = "Reminder! 10 min before the following task:\n";

    public ReminderInformation(boolean reminderDone, long userTime, String message) {
        this.reminderDone = reminderDone;
        this.userTime = userTime;
        this.message = message;
    }

    public String getDone() {
        if (reminderDone) {
            return "T";
        } else {
            return "F";
        }
    }

    public String getMessage() {
        return message;
    }

    public String getUserTime() {
        return Long.toString(userTime);
    }
}
