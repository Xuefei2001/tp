package seedu.duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.EmptyTasklistException;
import seedu.duke.exception.InvalidPriorityException;
import seedu.duke.exception.InvalidRecurrenceException;
import seedu.duke.exception.InvalidTaskTypeException;
import seedu.duke.exception.ListFormatException;
import seedu.duke.exception.MissingFilterArgumentException;
import seedu.duke.exception.ParseDateFailedException;
import seedu.duke.parser.DateParser;
import seedu.duke.task.taskmanager.TaskManager;
import seedu.duke.task.type.Deadline;
import seedu.duke.task.type.Event;
import seedu.duke.task.type.Todo;

import java.util.HashMap;
import java.util.Map;


class TaskManagerTest {

    Task newToDo1;
    Task newDeadline1;
    Task newEvent1;
    Task newToDo2;
    Task newDeadline2;
    Task newEvent2;
    private static final String VALID_DATE1 = "22-10-2021 02:00";
    private static final String VALID_DATE2 = "22-10-2021 05:00";

    private TaskManager taskManager;

    //@@author APZH
    private TaskManagerTest() throws ParseDateFailedException {
        taskManager = new TaskManager();
        LocalDateTime startDate = DateParser.stringToDate(VALID_DATE1);
        LocalDateTime endDate = DateParser.stringToDate(VALID_DATE2);
        newToDo1 = new Todo("read book", PriorityEnum.LOW, startDate, RecurrenceEnum.DAILY);
        newDeadline1 = new Deadline("return book", startDate, PriorityEnum.MEDIUM, RecurrenceEnum.WEEKLY);
        newEvent1 = new Event("project meeting", startDate, endDate, PriorityEnum.HIGH, RecurrenceEnum.MONTHLY);
        newToDo2 = new Todo("buy groceries", PriorityEnum.MEDIUM, startDate, RecurrenceEnum.WEEKLY);
        newDeadline2 = new Deadline("project submission", startDate, PriorityEnum.HIGH, RecurrenceEnum.MONTHLY);
        newEvent2 = new Event("watch movie", startDate, endDate, PriorityEnum.LOW, RecurrenceEnum.DAILY);
        taskManager.addTask(newEvent1);
        taskManager.addTask(newToDo1);
        taskManager.addTask(newDeadline1);
        taskManager.addTask(newEvent2);
        taskManager.addTask(newToDo2);
        taskManager.addTask(newDeadline2);
    }

    //@@author APZH
    @Test
    void testListTasklistWithFilterFormat() {
        Map<String, String> listArguments = new HashMap<>();
        try {
            System.out.println("testListTasklistFormat");
            System.out.println(taskManager.listTasklistWithFilter(listArguments));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //@@author APZH
    @Test
    void listTasklistWithFilter_ThrowsException_IfInvalidFlagEntered() {

        String expectedErrorMessage = "[!] Your list command is wrong...\n"
                + "Please follow one of the formats below:\n"
                + "-> list\n"
                + "-> list --[type, priority, recur] <argument>\n"
                + "-> list <task id>";

        Map<String, String> listArguments = new HashMap<>();
        listArguments.put("invalid_flag", "dummy_value");

        try {
            System.out.println("listTasklistWithFilter_ThrowsException_IfInvalidFlagEntered");
            taskManager.listTasklistWithFilter(listArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals(expectedErrorMessage, e.toString());
            System.out.println("ListFormatException caught");
        }
    }

    //@@author APZH
    @Test
    void listTasklistWithFilter_ThrowsException_IfMissingFilterArgumentWithValidTaskTypeFlag() {

        String expectedErrorMessage = "[!] One of your filter's is missing an argument...\n"
                + "Please follow the format: list or list --[type, priority, recur] <argument>";

        Map<String, String> listArguments = new HashMap<>();
        listArguments.put("type", "");

        try {
            System.out.println("listTasklistWithFilter_ThrowsException_IfMissingFilterArgumentWithValidTaskTypeFlag");
            taskManager.listTasklistWithFilter(listArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals(expectedErrorMessage, e.toString());
            System.out.println("MissingFilterArgumentException caught");
        }
    }

    //@@author APZH
    @Test
    void listTasklistWithFilter_ThrowsException_IfMissingFilterArgumentWithValidPriorityFlag() {

        String expectedErrorMessage = "[!] One of your filter's is missing an argument...\n"
                + "Please follow the format: list or list --[type, priority, recur] <argument>";

        Map<String, String> listArguments = new HashMap<>();
        listArguments.put("priority", "");

        try {
            System.out.println("listTasklistWithFilter_ThrowsException_IfMissingFilterArgumentWithValidPriorityFlag");
            taskManager.listTasklistWithFilter(listArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals(expectedErrorMessage, e.toString());
            System.out.println("MissingFilterArgumentException caught");
        }
    }

    //@@author APZH
    @Test
    void listTasklistWithFilter_ThrowsException_IfMissingFilterArgumentWithValidRecurrenceFlag() {

        String expectedErrorMessage = "[!] One of your filter's is missing an argument...\n"
                + "Please follow the format: list or list --[type, priority, recur] <argument>";

        Map<String, String> listArguments = new HashMap<>();
        listArguments.put("recur", "");

        try {
            System.out.println("listTasklistWithFilter_ThrowsException_IfMissingFilterArgumentWithValidRecurrenceFlag");
            taskManager.listTasklistWithFilter(listArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals(expectedErrorMessage, e.toString());
            System.out.println("MissingFilterArgumentException caught");
        }
    }

    //@@author APZH
    @Test
    void listTasklistWithFilter_ThrowsException_IfInvalidFilterArgumentWithValidTaskTypeFlag() {

        String expectedErrorMessage = "[!] The task type you entered is not valid...\n"
                + "The following are examples of valid task types: {Todo, Deadline, Event, Lesson}";

        Map<String, String> listArguments = new HashMap<>();
        listArguments.put("type", "invalid_type");

        try {
            System.out.println("listTasklistWithFilter_ThrowsException_IfInvalidFilterArgumentWithValidTaskTypeFlag");
            taskManager.listTasklistWithFilter(listArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals(expectedErrorMessage, e.toString());
            System.out.println("InvalidTaskTypeException caught");
        }
    }

    //@@author APZH
    @Test
    void listTasklistWithFilter_ThrowsException_IfInvalidFilterArgumentWithValidPriorityFlag() {

        String expectedErrorMessage = "[!] The priority you entered is not valid...\n"
                + "The following are examples of valid priorities: {Low, Medium, High}";

        Map<String, String> listArguments = new HashMap<>();
        listArguments.put("priority", "invalid_priority");

        try {
            System.out.println("listTasklistWithFilter_ThrowsException_IfInvalidFilterArgumentWithValidPriorityFlag");
            taskManager.listTasklistWithFilter(listArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals(expectedErrorMessage, e.toString());
            System.out.println("InvalidPriorityException caught");
        }
    }

    //@@author APZH
    @Test
    void listTasklistWithFilter_ThrowsException_IfInvalidFilterArgumentWithValidRecurrenceFlag() {

        String expectedErrorMessage = "[!] The recurrence you entered is not valid...\n"
                + "The following are examples of valid recurrence: {None, Daily, Weekly, Monthly, Yearly}";

        Map<String, String> listArguments = new HashMap<>();
        listArguments.put("recur", "invalid_recurrence");

        try {
            System.out.println("listTasklistWithFilter_ThrowsException_IfInvalidFilterArgumentWithValidRecurrenceFlag");
            taskManager.listTasklistWithFilter(listArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals(expectedErrorMessage, e.toString());
            System.out.println("InvalidRecurrenceException caught");
        }
    }

    //@@author APZH
    @Test
    void testListTaskRecurrenceFormat() {
        Map<String, String> listArguments = new HashMap<>();
        listArguments.put("mainArgument", "1");
        try {
            System.out.println("testListTaskRecurrenceFormat");
            System.out.println(taskManager.listTaskRecurrence(listArguments));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //@@author APZH
    @Test
    void listTaskRecurrence_ThrowsException_IfTaskIdBiggerThanTaskListSize() {

        String expectedErrorMessage = "[!] The task ID you entered is not valid...\n"
                + "It must be a non-negative number that is within the bounds of the tasklist!";

        Map<String, String> listArguments = new HashMap<>();
        listArguments.put("mainArgument", "999");

        try {
            System.out.println("listTaskRecurrence_ThrowsException_IfTaskIdBiggerThanTaskListSize");
            taskManager.listTaskRecurrence(listArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals(expectedErrorMessage, e.toString());
            System.out.println("InvalidTaskIndexException caught");
        }
    }

    //@@author APZH
    @Test
    void listTaskRecurrence_ThrowsException_IfTaskIdIsNegative() {

        String expectedErrorMessage = "[!] The task ID you entered is not valid...\n"
                + "It must be a non-negative number that is within the bounds of the tasklist!";

        Map<String, String> listArguments = new HashMap<>();
        listArguments.put("mainArgument", "-1");

        try {
            System.out.println("listTaskRecurrence_ThrowsException_IfInvalidTaskIdBiggerThanTaskListSize");
            taskManager.listTaskRecurrence(listArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals(expectedErrorMessage, e.toString());
            System.out.println("InvalidTaskIndexException caught");
        }
    }

    //@@author APZH
    @Test
    void testSortTasklistByPriority() {

        Map<String, String> sortArguments = new HashMap<>();
        sortArguments.put("by", "priority");

        Map<String, String> listArguments = new HashMap<>();

        try {
            System.out.println("testSortTasklistByPriority");
            taskManager.sortTasklist(sortArguments);
            System.out.println(taskManager.listTasklistWithFilter(listArguments));
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }

    }

    //@@author APZH
    @Test
    void testSortTasklistByDescription() {

        Map<String, String> sortArguments = new HashMap<>();
        sortArguments.put("by", "description");

        Map<String, String> listArguments = new HashMap<>();

        try {
            System.out.println("testSortTasklistByDescription");
            taskManager.sortTasklist(sortArguments);
            System.out.println(taskManager.listTasklistWithFilter(listArguments));
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }

    }

    //@@author APZH
    @Test
    void testSortTasklistByTaskType() {

        Map<String, String> sortArguments = new HashMap<>();
        sortArguments.put("by", "type");

        Map<String, String> listArguments = new HashMap<>();

        try {
            System.out.println("testSortTasklistByTaskType");
            taskManager.sortTasklist(sortArguments);
            System.out.println(taskManager.listTasklistWithFilter(listArguments));
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }

    }

    //@@author APZH
    @Test
    void sortTasklist_ThrowsException_IfMissingByFlag() {

        Map<String, String> sortArguments = new HashMap<>();
        sortArguments.put("random_flag", "random_criteria");

        try {
            System.out.println("sortTasklist_ThrowsException_IfMissingByFlag");
            taskManager.sortTasklist(sortArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals("[!] Your sort command is wrong...\n"
                    + "Please follow the format: sort --by [criteria]", e.toString());
            System.out.println("SortFormatException caught");
        }
    }

    //@@author APZH
    @Test
    void sortTasklist_ThrowsException_IfSortCriteriaEmpty() {

        Map<String, String> commandArguments = new HashMap<>();
        commandArguments.put("by", "");

        try {
            System.out.println("sortTasklist_ThrowsException_IfSortCriteriaEmpty");
            taskManager.sortTasklist(commandArguments);
            fail(); // the test should not reach here
        } catch (Exception e) {
            assertEquals("[!] Please specify a sort criteria...\n", e.toString());
            System.out.println("EmptySortCriteriaException caught");
        }
    }

}