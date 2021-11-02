package seedu.duke.task.type;


import java.time.LocalDateTime;
import java.util.Map;
import seedu.duke.command.flags.DeadlineFlag;
import seedu.duke.exception.ParseDateFailedException;
import seedu.duke.parser.DateParser;
import seedu.duke.task.PriorityEnum;
import seedu.duke.task.RecurrenceEnum;
import seedu.duke.task.Task;
import seedu.duke.task.TypeEnum;
import seedu.duke.task.reminder.Reminder;
import seedu.duke.task.reminder.ReminderInformation;

public class Deadline extends Task {

    private static final TypeEnum TASK_TYPE = TypeEnum.DEADLINE;

    private static final String DEADLINE_ICON = "[D]";
    private static final String DEADLINE_DATE_DESCRIPTION_REGEX = " (dueDate: %s)";

    private static final String DUE_DATE_NOT_NULL_ASSERTION = "dueDate for Deadline cannot be null.";

    private LocalDateTime dueDate;

    public Deadline(String description, LocalDateTime dueDate) {
        super(description);
        setDueDate(dueDate);
    }

    public Deadline(String description, LocalDateTime dueDate, PriorityEnum priority) {
        this(description, dueDate);
        setPriority(priority);
    }
    
    public Deadline(String description, LocalDateTime dueDate, RecurrenceEnum recurrence) {
        this(description, dueDate);
        setRecurrence(recurrence);
    }

    public Deadline(String description, LocalDateTime dueDate, PriorityEnum priority, RecurrenceEnum recurrence) {
        this(description, dueDate);
        setPriority(priority);
        setRecurrence(recurrence);
    }

    public TypeEnum getTaskType() {
        return this.TASK_TYPE;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        assert dueDate != null : DUE_DATE_NOT_NULL_ASSERTION;
        this.dueDate = dueDate;
        reminder = new Reminder(dueDate);
    }

    @Override
    public boolean needReminder() {
        return (reminder != null);
    }
    
    public String getReminder(LocalDateTime now) {
        return reminder.getRecurrenceMessage(now, getTaskEntryDescription(), getRecurrence());
    }

    @Override
    public void updateReminderMessage(String message) {
        reminder.setMessage(message);
    }

    @Override
    public void updateReminderTime(long reminderTime) {
        reminder.setUserTime(reminderTime);
    }

    public ReminderInformation getReminderInformation() {
        return reminder.getInformation();
    }

    @Override
    public String getTaskEntryDescription() {
        return DEADLINE_ICON + " " + super.getTaskEntryDescription()
                + String.format(DEADLINE_DATE_DESCRIPTION_REGEX, DateParser.dateToString(getDueDate()));
    }

    @Override
    protected void taskEdit(Map<String, String> arguments) throws ParseDateFailedException {
        if (arguments.containsKey(DeadlineFlag.DUE_DATE)) {
            String due = arguments.get(DeadlineFlag.DUE_DATE);
            setDueDate(DateParser.stringToDate(due));
        }
    }
}
