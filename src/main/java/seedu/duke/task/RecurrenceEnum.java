package seedu.duke.task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import seedu.duke.exception.InvalidRecurrenceException;

//@@author SeanRobertDH
public enum RecurrenceEnum {
    NONE(null),
    DAILY(ChronoUnit.DAYS),
    WEEKLY(ChronoUnit.WEEKS),
    MONTHLY(ChronoUnit.MONTHS),
    YEARLY(ChronoUnit.YEARS);

    private final ChronoUnit chronoUnit;

    RecurrenceEnum(ChronoUnit chronoUnit) {
        this.chronoUnit = chronoUnit;
    }

    /**
     * Returns the name of enum in lowercase.
     *
     * @return <code>String</code> of {@link RecurrenceEnum} enum name in lowercase.
     */
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public List<LocalDateTime> getNextNRecurredDates(LocalDateTime date, int n) {
        ArrayList<LocalDateTime> recurredDates = new ArrayList<>(n);
        if (this == NONE) {
            return recurredDates;
        }
        for (int i = 1; i <= n; i++) {
            recurredDates.add(date.plus(i, chronoUnit));
        }
        return recurredDates;
    }

    public LocalDateTime getNextRecurredDate(LocalDateTime date) {
        if (this == NONE) {
            return date;
        }
        long i = 0;
        LocalDateTime recurredDate;
        do {
            recurredDate = date.plus(i++, chronoUnit);
        } while (recurredDate.isBefore(LocalDateTime.now()));

        return recurredDate;
    }

    public static RecurrenceEnum getRecurrence(String recurrence) throws InvalidRecurrenceException {
        for (RecurrenceEnum recurrenceEnum : values()) {
            if (recurrence.equalsIgnoreCase(recurrenceEnum.name())) {
                return recurrenceEnum;
            }
        }
        throw new InvalidRecurrenceException(recurrence);
    }


}
