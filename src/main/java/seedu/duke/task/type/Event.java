package seedu.duke.task.type;

import java.time.LocalDateTime;
import java.util.Map;
import seedu.duke.command.flags.EventFlag;
import seedu.duke.exception.ParseDateFailedException;
import seedu.duke.exception.StartDateAfterEndDateException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.TaskParser;
import seedu.duke.task.PriorityEnum;
import seedu.duke.task.RecurrenceEnum;
import seedu.duke.task.Task;
import seedu.duke.task.TypeEnum;
import seedu.duke.task.reminder.Reminder;
import seedu.duke.task.reminder.ReminderInformation;

public class Event extends Task {

    private static final TypeEnum TASK_TYPE = TypeEnum.EVENT;

    private static final String EVENT_ICON = "[E]";
    private static final String DEADLINE_DATE_DESCRIPTION_REGEX = " (startDate: %s - endDate: %s)";

    private static final String START_DATE_NOT_NULL_ASSERTION = "startDate for Event cannot be null";
    private static final String END_DATE_NOT_NULL_ASSERTION = "endDate for Event cannot be null";
    private static final String START_DATE_BEFORE_END_DATE_ASSERTION = "Start date must be before end date!";

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public Event(String description, LocalDateTime startDate, LocalDateTime endDate, PriorityEnum priority) {
        this(description, startDate, endDate);
        setPriority(priority);
    }

    public Event(String description, LocalDateTime startDate, LocalDateTime endDate, RecurrenceEnum recurrence) {
        this(description, startDate, endDate);
        setRecurrence(recurrence);
    }

    public Event(String description, LocalDateTime startDate,
                 LocalDateTime endDate, PriorityEnum priority, RecurrenceEnum recurrence) {
        this(description, startDate, endDate);
        setPriority(priority);
        setRecurrence(recurrence);
    }

    public TypeEnum getTaskType() {
        return this.TASK_TYPE;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        assert startDate != null : START_DATE_NOT_NULL_ASSERTION;
        if (endDate != null) {
            assert startDate.isBefore(endDate) : START_DATE_BEFORE_END_DATE_ASSERTION;
        }
        this.startDate = startDate;
        reminder = new Reminder(startDate);
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        assert endDate != null : END_DATE_NOT_NULL_ASSERTION;
        if (startDate != null) {
            assert startDate.isBefore(endDate) : START_DATE_BEFORE_END_DATE_ASSERTION;
        }
        this.endDate = endDate;
    }

    @Override
    public boolean needReminder() {
        return (reminder != null);
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
        return EVENT_ICON + " " + super.getTaskEntryDescription()
            + String.format(DEADLINE_DATE_DESCRIPTION_REGEX,
            DateParser.dateToString(getStartDate()), DateParser.dateToString(getEndDate()));
    }

    public String getReminder(LocalDateTime now) {
        return reminder.getRecurrenceMessage(now, getTaskEntryDescription(), getRecurrence());
    }

    @Override
    protected void taskEdit(Map<String, String> arguments)
            throws ParseDateFailedException, StartDateAfterEndDateException {
        LocalDateTime startDate = getStartDate();
        LocalDateTime endDate = getEndDate();
        if (arguments.containsKey(EventFlag.START_DATE)) {
            String start = arguments.get(EventFlag.START_DATE);
            startDate = TaskParser.getDate(start);
        }
        if (arguments.containsKey(EventFlag.END_DATE)) {
            String end = arguments.get(EventFlag.END_DATE);
            endDate = TaskParser.getDate(end);
        }
        if (startDate.isAfter(endDate)) {
            throw new StartDateAfterEndDateException();
        }
        setStartDate(startDate);
        setEndDate(endDate);
    }
}
