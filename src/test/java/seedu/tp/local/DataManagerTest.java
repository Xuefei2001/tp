package seedu.tp.local;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import seedu.tp.task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataManagerTest {
    private ArrayList<String> expectedOut = new ArrayList<>();
    Task todoReminder;
    Task todoNoReminder;
    Task deadline;
    Task event;
    private static final String DATE1 = "2021-10-30T02:00";
    private static final String DATE2 = "2021-10-30T05:00";

    LocalDateTime startDate = LocalDateTime.parse(DATE1);
    LocalDateTime endDate = LocalDateTime.parse(DATE2);

    DataManagerTest(){
    }

    @Test
    void getStringLineList() {
        /*
        todoReminder = new Todo("go jogging",PriorityEnum.LOW, startDate, RecurrenceEnum.DAILY);
        todoNoReminder = new Todo("go jogging");
        deadline = new Deadline("return book", startDate, RecurrenceEnum.WEEKLY);
        event = new Event("project meeting", startDate, endDate, RecurrenceEnum.MONTHLY);

        expectedOut.add("todo|go jogging|2021-10-30T02:00|low|daily|"
                + "10|Reminder! 10 min before the following task:");
        expectedOut.add("todo|go jogging|null|medium|none|null|null");
        expectedOut.add("deadline|return book|2021-10-30T02:00|medium|weekly|"
                + "10|Reminder! 10 min before the following task:");
        expectedOut.add("event|project meeting|2021-10-30T02:00|2021-10-30T05:00|medium|monthly|"
                + "10|Reminder! 10 min before the following task:");

        ArrayList<String> results = DataManager.getStringLineList();

        assertEquals(expectedOut, results);
        */
    }
}

