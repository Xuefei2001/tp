package seedu.duke.task;

import java.time.LocalDateTime;
import java.util.Map;
import seedu.duke.command.flags.EditFlag;
import seedu.duke.command.flags.TaskFlag;
import seedu.duke.task.reminder.Reminder;
import seedu.duke.task.reminder.ReminderInformation;
import seedu.duke.exception.InvalidPriorityException;
import seedu.duke.exception.InvalidRecurrenceException;
import seedu.duke.exception.ParseDateFailedException;
import seedu.duke.exception.StartDateAfterEndDateException;
import seedu.duke.parser.TaskParser;

public abstract class Task {

    private static final PriorityEnum DEFAULT_PRIORITY = PriorityEnum.MEDIUM;
    protected static final RecurrenceEnum DEFAULT_RECURRENCE = RecurrenceEnum.NONE;

    private static final String TASK_ENTRY_DESCRIPTION_REGEX = "%s <%s> {%s}";

    private static final String DESCRIPTION_NOT_EMPTY_ASSERTION = "description should not be empty.";
    private static final String DESCRIPTION_NOT_NULL_ASSERTION = "description should not be null.";
    private static final String PRIORITY_ASSERTION = "priority should not be null.";
    private static final String RECURRENCE_ASSERTION = "recurrence should not be null.";

    private String description;
    private PriorityEnum priority;
    private RecurrenceEnum recurrence;

    protected Reminder reminder;

    protected Task(String description) {
        setDescription(description);
        setPriority(DEFAULT_PRIORITY);
        setRecurrence(DEFAULT_RECURRENCE);
    }

    protected Task(String description, PriorityEnum priority) {
        this(description);
        setPriority(priority);
    }

    protected Task(String description, RecurrenceEnum recurrence) {
        this(description);
        setRecurrence(recurrence);
    }

    protected Task(String description, PriorityEnum priority, RecurrenceEnum recurrence) {
        this(description);
        setPriority(priority);
        setRecurrence(recurrence);
    }

    public String getTaskEntryDescription() {
        return String.format(TASK_ENTRY_DESCRIPTION_REGEX, getDescription(), getPriority(), getRecurrence());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        assert !description.equals("") : DESCRIPTION_NOT_EMPTY_ASSERTION;
        assert description != null : DESCRIPTION_NOT_NULL_ASSERTION;
        this.description = description;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityEnum priority) {
        assert priority != null : PRIORITY_ASSERTION;
        this.priority = priority;
    }

    public abstract boolean needReminder();

    public abstract String getReminder(LocalDateTime now);

    public abstract void updateReminderMessage(String message);

    public abstract void updateReminderTime(long reminderTime);

    public abstract ReminderInformation getReminderInformation();

    public RecurrenceEnum getRecurrence() {
        return this.recurrence;
    }

    public void setRecurrence(RecurrenceEnum recurrence) {
        assert recurrence != null : RECURRENCE_ASSERTION;
        this.recurrence = recurrence;
    }

    public abstract TypeEnum getTaskType();

    public void edit(Map<String, String> arguments) throws InvalidPriorityException,
            InvalidRecurrenceException, ParseDateFailedException, StartDateAfterEndDateException {
        if (arguments.containsKey(EditFlag.DESCRIPTION)) {
            setDescription(arguments.get(EditFlag.DESCRIPTION));
        }
        if (arguments.containsKey(TaskFlag.PRIORITY)) {
            String priority = arguments.get(TaskFlag.PRIORITY);
            setPriority(TaskParser.getPriorityEnum(priority));
        }
        if (arguments.containsKey(TaskFlag.RECURRENCE)) {
            String recurrence = arguments.get(TaskFlag.RECURRENCE);
            setRecurrence(TaskParser.getRecurrenceEnum(recurrence));
        }
        taskEdit(arguments);
    }

    protected abstract void taskEdit(Map<String, String> arguments)
        throws ParseDateFailedException, StartDateAfterEndDateException;
}
