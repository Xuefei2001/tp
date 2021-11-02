package seedu.duke.task;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.ParseDateFailedException;
import seedu.duke.parser.DateParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

//@@author SeanRobertDH
class RecurrenceEnumTest {
    @Test
    void getNextNRecurredDates_validDate_expectRecurredDate() throws ParseDateFailedException {
        LocalDateTime dateTime = DateParser.stringToDate("10-04-2021 03:29");
        List<LocalDateTime> recurred = RecurrenceEnum.NONE.getNextNRecurredDates(dateTime, 1);
        assertEquals(0, recurred.size());

        recurred = RecurrenceEnum.DAILY.getNextNRecurredDates(dateTime, 1);
        assertEquals(DateParser.stringToDate("11-04-2021 03:29"), recurred.get(0));

        recurred = RecurrenceEnum.WEEKLY.getNextNRecurredDates(dateTime, 1);
        assertEquals(DateParser.stringToDate("17-04-2021 03:29"), recurred.get(0));

        recurred = RecurrenceEnum.MONTHLY.getNextNRecurredDates(dateTime, 1);
        assertEquals(DateParser.stringToDate("10-05-2021 03:29"), recurred.get(0));

        recurred = RecurrenceEnum.YEARLY.getNextNRecurredDates(dateTime, 1);
        assertEquals(DateParser.stringToDate("10-04-2022 03:29"), recurred.get(0));
    }

    @Test
    void getNextNRecurredDates_monthlyRecurredJan_expectFeb28() throws ParseDateFailedException {
        LocalDateTime dateTime = DateParser.stringToDate("30-01-2021 03:29");
        List<LocalDateTime> recurred = RecurrenceEnum.MONTHLY.getNextNRecurredDates(dateTime, 1);
        assertEquals(DateParser.stringToDate("28-02-2021 03:29"), recurred.get(0));
    }

    @Test
    void getNextNRecurredDates_yearlyRecurredLeapYear_expectFeb28() throws ParseDateFailedException {
        LocalDateTime dateTime = DateParser.stringToDate("29-02-2020 03:29");
        List<LocalDateTime> recurred = RecurrenceEnum.YEARLY.getNextNRecurredDates(dateTime, 1);
        assertEquals(DateParser.stringToDate("28-02-2021 03:29"), recurred.get(0));
    }

    @Test
    void getNextNRecurredDates_monthlyRecurred_expectRecurredDates() throws ParseDateFailedException {
        LocalDateTime dateTime = DateParser.stringToDate("31-01-2020 03:29");
        List<LocalDateTime> recurred = RecurrenceEnum.MONTHLY.getNextNRecurredDates(dateTime, 3);
        assertEquals(DateParser.stringToDate("29-02-2020 03:29"), recurred.get(0));
        assertEquals(DateParser.stringToDate("31-03-2020 03:29"), recurred.get(1));
        assertEquals(DateParser.stringToDate("30-04-2020 03:29"), recurred.get(2));
    }

    @Test
    void getNextRecurredDate_monthlyRecurred_expectRecurredDates() throws ParseDateFailedException {
        LocalDateTime dateTime = DateParser.stringToDate("15-01-2020 03:29");
        LocalDateTime nextDateTime = RecurrenceEnum.MONTHLY.getNextRecurredDate(dateTime);
        assertEquals(15, nextDateTime.getDayOfMonth());
        assertFalse(nextDateTime.isBefore(LocalDateTime.now()));
    }
}