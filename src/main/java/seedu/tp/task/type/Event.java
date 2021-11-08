package seedu.tp.task.type;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Map;

import org.jetbrains.annotations.Nullable;
import seedu.tp.command.flags.EventFlag;
import seedu.tp.exception.ParseDateFailedException;
import seedu.tp.exception.StartDateAfterEndDateException;
import seedu.tp.parser.DateParser;
import seedu.tp.task.PriorityEnum;
import seedu.tp.task.RecurrenceEnum;
import seedu.tp.task.Task;
import seedu.tp.task.TypeEnum;
import seedu.tp.task.reminder.Reminder;

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
        setReminder(new Reminder(startDate));
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
    public String getTaskEntryDescription() {
        return EVENT_ICON + " " + super.getTaskEntryDescription()
            + String.format(DEADLINE_DATE_DESCRIPTION_REGEX,
            DateParser.dateToString(getStartDate()), DateParser.dateToString(getEndDate()));
    }

    //@@author SeanRobertDH
    /**
     * Edits the {@link #startDate} and/or {@link #endDate} if {@link seedu.tp.command.flags.EventFlag#START_DATE}
     * and/or {@link seedu.tp.command.flags.EventFlag#END_DATE} is a key in <code>arguments</code>.
     *
     * @param arguments <code>Map&lt;String, String&gt;</code> of flags to values
     *     that should be edited in {@link seedu.tp.task.type.Event}.
     * @throws seedu.tp.exception.ParseDateFailedException if unable to parse <code>start</code> or <code>end</code>.
     * @throws seedu.tp.exception.StartDateAfterEndDateException if edited <code>startDate</code> and/or
     *     <code>endDate</code> would result in {@link #startDate} being before {@link #endDate}.
     */
    @Override
    protected void taskEdit(Map<String, String> arguments)
            throws ParseDateFailedException, StartDateAfterEndDateException, URISyntaxException {
        LocalDateTime startDate = getStartDate();
        LocalDateTime endDate = getEndDate();
        if (arguments.containsKey(EventFlag.START_DATE)) {
            String start = arguments.get(EventFlag.START_DATE);
            startDate = DateParser.stringToDate(start);
        }
        if (arguments.containsKey(EventFlag.END_DATE)) {
            String end = arguments.get(EventFlag.END_DATE);
            endDate = DateParser.stringToDate(end);
        }
        refreshDate();
        if (startDate.isAfter(endDate)) {
            throw new StartDateAfterEndDateException();
        }
        setStartDate(startDate);
        setEndDate(endDate);
    }

    //@@author SeanRobertDH
    /**
     * Updates the {@link #startDate} and {@link #endDate}
     * if {@link seedu.tp.task.type.Event} has a recurrence to the latest date.
     */
    @Override
    public void refreshDate() {
        LocalDateTime newStartDate = getRecurrence().getNextRecurredDate(getStartDate());
        LocalDateTime newEndDate = getRecurrence().getNextRecurredDate(getEndDate());
        setEndDate(newEndDate);
        setStartDate(newStartDate);
    }

    //@@author SeanRobertDH
    protected EventFlag getTaskFlag() {
        return new EventFlag();
    }

    //@@author SeanRobertDH
    /**
     * Returns {@link #startDate}.
     */
    @Override
    public LocalDateTime getListDate() {
        return getStartDate();
    }
}
