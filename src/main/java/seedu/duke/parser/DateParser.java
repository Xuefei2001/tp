package seedu.duke.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import seedu.duke.exception.ParseDateFailedException;
import seedu.duke.log.Log;

//@@author SeanRobertDH
public class DateParser {
    private static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy HH:mm";

    private static final String[] ACCEPTED_DATE_FORMATS
        = {"d-M-[yyyy][yy]_H-m", "d-M-[yyyy][yy]_H", "d-M_H-m", "d-M_H", "d_H-m", "d_H", "H-m", "H"};
    private static final String YEAR_CHAR_REPRESENTATION = "y";

    private static final String STARTS_WITH_REGEX = "^";
    private static final String ENDS_WITH_REGEX = "$";
    private static final String WHITESPACE_REGEX = "\\s+";

    private static final String DEFAULT_SEPARATOR = "-";

    private static final String DEFAULT_SPACE = "_";

    private static int HALF_HOUR = 30;
    private static int HOUR = 60;

    private static final String LOG_FORMATTED_DATE_MESSAGE = "Formatted date String is: ";


    private static final String[] ACCEPTED_SEPARATORS
        = {":", "~", "\\.", "\\/", "\\|", "\\\\"};

    private enum Day {
        YESTERDAY(-1),
        TODAY(0),
        TOMORROW(1);

        private final int daysToAdd;

        Day(int daysToAdd) {
            this.daysToAdd = daysToAdd;
        }

        private String getRegex() {
            return STARTS_WITH_REGEX + super.toString().toLowerCase() + ENDS_WITH_REGEX;
        }

        private static boolean isDay(String input) {
            input = input.toLowerCase().replace(WHITESPACE_REGEX, "");
            for (Day day : Day.values()) {
                if (input.matches(day.getRegex())) {
                    return true;
                }
            }
            return false;
        }

        private static LocalDateTime getDate(String input) {
            input = input.toLowerCase().replace(WHITESPACE_REGEX, "");
            for (Day day : Day.values()) {
                if (input.matches(day.getRegex())) {
                    LocalDateTime dateTime = LocalDateTime.now().plusDays(day.daysToAdd);
                    return roundToClosestHour(dateTime);
                }
            }
            return null;
        }
    }

    public static LocalDateTime roundToClosestHour(LocalDateTime dateTime) {
        int minutes = dateTime.getMinute();
        if (minutes >= HALF_HOUR) {
            return dateTime.plusMinutes(HOUR - minutes);
        }
        System.out.printf(Integer.toString(minutes));
        return dateTime.minusMinutes(minutes);
    }

    public static LocalDateTime roundUpHour(LocalDateTime dateTime) {
        int minutes = dateTime.getMinute();
        dateTime = dateTime.minusMinutes(minutes);
        return dateTime.plusHours(1);
    }

    public static String dateToString(LocalDateTime dateTime) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return dateFormat.format(dateTime);
    }

    public static LocalDateTime stringToDate(String dateTime) throws ParseDateFailedException {
        if (Day.isDay(dateTime)) {
            return Day.getDate(dateTime);
        } else {
            String formattedDate = formatDateString(dateTime);
            Log.info(LOG_FORMATTED_DATE_MESSAGE + formattedDate);
            for (String dateFormatString : ACCEPTED_DATE_FORMATS) {
                try {
                    DateTimeFormatter dateFormat = getDateTimeFormatter(dateFormatString);
                    return LocalDateTime.parse(formattedDate, dateFormat);
                } catch (DateTimeParseException dtpe) {
                    continue;
                }
            }
            throw new ParseDateFailedException(getDefaultDateFormat());
        }
    }

    private static String formatDateString(String dateTime) {
        for (String separator : ACCEPTED_SEPARATORS) {
            dateTime = dateTime.replaceAll(separator, DEFAULT_SEPARATOR);
        }
        if (hasDateAndTime(dateTime)) {
            dateTime = dateTime.replaceFirst(WHITESPACE_REGEX, DEFAULT_SPACE);
        }
        dateTime = dateTime.replaceAll(WHITESPACE_REGEX, "");
        return dateTime;
    }

    private static boolean hasDateAndTime(String date) {
        return date.split(WHITESPACE_REGEX).length >= 2;
    }

    private static DateTimeFormatter getDateTimeFormatter(String dateFormatString) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder()
            .appendPattern(dateFormatString);
        if (!dateFormatString.contains(YEAR_CHAR_REPRESENTATION)) {
            builder = builder.parseDefaulting(ChronoField.YEAR, LocalDateTime.now().getYear());
        }
        return builder
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, LocalDateTime.now().getMonthValue())
            .parseDefaulting(ChronoField.DAY_OF_MONTH, LocalDateTime.now().getDayOfMonth())
            .toFormatter();
    }

    public static String getDefaultDateFormat() {
        return DEFAULT_DATE_FORMAT;
    }
}
