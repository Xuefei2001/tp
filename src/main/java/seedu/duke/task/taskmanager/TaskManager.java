package seedu.duke.task.taskmanager;

import org.apache.commons.lang3.EnumUtils;
import seedu.duke.command.Command;
import seedu.duke.command.flags.ListFlag;
import seedu.duke.command.flags.SortFlag;
import seedu.duke.exception.EmptySortCriteriaException;
import seedu.duke.exception.EmptyTasklistException;
import seedu.duke.exception.InvalidPriorityException;
import seedu.duke.exception.InvalidRecurrenceException;
import seedu.duke.exception.InvalidTaskIndexException;
import seedu.duke.exception.InvalidTaskTypeException;
import seedu.duke.exception.ListFormatException;
import seedu.duke.exception.MissingFilterArgumentException;
import seedu.duke.exception.ParseDateFailedException;
import seedu.duke.exception.SortFormatException;
import seedu.duke.exception.StartDateAfterEndDateException;
import seedu.duke.exception.TaskIsNonRecurringException;
import seedu.duke.local.DataManager;
import seedu.duke.log.Log;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

import seedu.duke.parser.DateParser;
import seedu.duke.task.PriorityEnum;
import seedu.duke.task.RecurrenceEnum;
import seedu.duke.task.Task;
import seedu.duke.task.TypeEnum;
import seedu.duke.task.type.Deadline;
import seedu.duke.task.type.Event;
import seedu.duke.task.type.Todo;

public class TaskManager implements Subject {

    private List<Task> taskList;
    private List<Task> latestFilteredList;
    static final int STARTING_SIZE = 128;

    private static final String LIST_HEADER = "-------------\n"
            + " MY TASKLIST\n"
            + "-------------\n";

    private static final int numOfRecurredDates = 4;
    private static final String DIGIT_REGEX = "^[+-]?[0-9]*$";

    public TaskManager(DataManager dataManager) {
        taskList = dataManager.loadTaskList(STARTING_SIZE);
        addObserver(dataManager);

        latestFilteredList = new ArrayList<>(STARTING_SIZE);

    }

    public TaskManager() {
        taskList = new ArrayList<>(STARTING_SIZE);

        latestFilteredList = new ArrayList<>(STARTING_SIZE);
    }

    //@@author APZH
    /**
     * Returns a filtered tasklist as a {@code String}.
     * If no filter is specified, returns the entire tasklist without any filter instead.
     *
     * @param filters Contains the filters to be applied to the tasklist.
     * @return Filtered tasklist as a {@code String}.
     * @throws EmptyTasklistException         If tasklist is empty.
     * @throws ListFormatException            If the parameter 'filters' contains invalid filter flags.
     * @throws MissingFilterArgumentException If filter flag does not contain any argument.
     * @throws InvalidTaskTypeException       If task type filter argument is not valid.
     * @throws InvalidPriorityException       If priority filter argument is not valid.
     * @throws InvalidRecurrenceException     If recurrence filter argument is not valid.
     */
    public String listTasklistWithFilter(Map<String, String> filters) throws EmptyTasklistException,
            ListFormatException, MissingFilterArgumentException, InvalidTaskTypeException,
            InvalidPriorityException, InvalidRecurrenceException {
        assert taskList.size() >= 0 : "Tasklist cannot be negative";
        if (taskList.size() == 0) {
            throw new EmptyTasklistException();
        }
        List<Task> filteredTasks = new ArrayList<>(taskList);
        for (HashMap.Entry<String, String> entry : filters.entrySet()) {
            String filter = entry.getKey();
            String filterArgument = entry.getValue();
            if (filter.equals(Command.MAIN_ARGUMENT)) {
                continue;
            }
            switch (filter) {
            case ListFlag.TASK_TYPE:
                filteredTasks = filterListByTaskType(filteredTasks, filterArgument);
                break;
            case ListFlag.PRIORITY:
                filteredTasks = filterListByPriority(filteredTasks, filterArgument);
                break;
            case ListFlag.RECURRENCE:
                filteredTasks = filterListByRecurrence(filteredTasks, filterArgument);
                break;
            default:
                throw new ListFormatException();
            }
        }
        updateFilteredTaskList(filteredTasks);
        return getListTasklistWithFilterMessage(filteredTasks);
    }

    //@@author APZH
    /**
     * Returns a formatted message of the list of tasks as a {@code String}.
     */
    private String getListTasklistWithFilterMessage(List<Task> filteredTasks) {
        String taskEntries = "";
        for (int i = 0; i < filteredTasks.size(); i++) {
            taskEntries += i + 1 + ". " + filteredTasks.get(i).getTaskEntryDescription() + "\n";
        }
        return LIST_HEADER + taskEntries;
    }

    public void refreshListDates() {
        for (Task task : taskList) {
            if (task.getRecurrence() != RecurrenceEnum.NONE && task.getListDate().isBefore(LocalDateTime.now())) {
                task.refreshDate();
            }
        }
        updateObservers();
    }

    //@@author APZH

    /**
     * Returns a {@code List} of tasks that matches the {@code taskTypeFilter}.
     *
     * @param taskList       Contains the tasklist to apply the {@code taskTypeFilter}.
     * @param taskTypeFilter Contains the task type to be filtered for.
     * @return Filtered {@code List} of tasks based on task type.
     * @throws MissingFilterArgumentException If {@code taskTypeFilter} is empty.
     * @throws InvalidTaskTypeException       If {@code taskTypeFilter} is not valid.
     */
    private List<Task> filterListByTaskType(List<Task> taskList, String taskTypeFilter)
            throws MissingFilterArgumentException, InvalidTaskTypeException {
        if (taskTypeFilter.isEmpty()) {
            throw new MissingFilterArgumentException();
        }
        if (!EnumUtils.isValidEnum(TypeEnum.class, taskTypeFilter.toUpperCase())) {
            throw new InvalidTaskTypeException(taskTypeFilter);
        }
        List<Task> filteredTasks = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            String currentTaskType = taskList.get(i).getTaskType().name();
            if (currentTaskType.equalsIgnoreCase(taskTypeFilter)) {
                filteredTasks.add(taskList.get(i));
            }
        }
        return filteredTasks;
    }

    //@@author APZH

    /**
     * Returns a {@code List} of tasks that matches the {@code priorityFilter}.
     *
     * @param taskList       Contains the tasklist to apply the {@code priorityFilter}.
     * @param priorityFilter Contains the priority to be filtered for.
     * @return Filtered {@code List} of tasks based on priority.
     * @throws MissingFilterArgumentException If {@code priorityFilter} is empty.
     * @throws InvalidPriorityException       If {@code priorityFilter} is not valid.
     */
    private List<Task> filterListByPriority(List<Task> taskList, String priorityFilter)
            throws MissingFilterArgumentException, InvalidPriorityException {
        if (priorityFilter.isEmpty()) {
            throw new MissingFilterArgumentException();
        }
        if (!EnumUtils.isValidEnum(PriorityEnum.class, priorityFilter.toUpperCase())) {
            throw new InvalidPriorityException(priorityFilter);
        }
        List<Task> filteredTasks = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            String currentPriority = taskList.get(i).getPriority().name();
            if (currentPriority.equalsIgnoreCase(priorityFilter)) {
                filteredTasks.add(taskList.get(i));
            }
        }
        return filteredTasks;
    }

    //@@author APZH

    /**
     * Returns a {@code List} of tasks that matches the {@code recurrenceFilter}.
     *
     * @param taskList         Contains the tasklist to apply the {@code recurrenceFilter}.
     * @param recurrenceFilter Contains the type of recurrence to be filtered for.
     * @return Filtered {@code List} of tasks based on recurrence.
     * @throws MissingFilterArgumentException If {@code recurrenceFilter} is empty.
     * @throws InvalidRecurrenceException     If {@code recurrenceFilter} is not valid.
     */
    private List<Task> filterListByRecurrence(List<Task> taskList, String recurrenceFilter)
            throws MissingFilterArgumentException, InvalidRecurrenceException {
        if (recurrenceFilter.isEmpty()) {
            throw new MissingFilterArgumentException();
        }
        if (!EnumUtils.isValidEnum(RecurrenceEnum.class, recurrenceFilter.toUpperCase())) {
            throw new InvalidRecurrenceException(recurrenceFilter);
        }
        List<Task> filteredTasks = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            String currentRecurrence = taskList.get(i).getRecurrence().name();
            if (currentRecurrence.equalsIgnoreCase(recurrenceFilter)) {
                filteredTasks.add(taskList.get(i));
            }
        }
        return filteredTasks;
    }

    //@@author APZH
    /**
     * Returns the next 4 recurrence of a task as a formatted {@code String}.
     *
     * @param parameters Contains the task ID of the task to list the recurrence.
     * @return Returns {@code String} containing next 4 recurrences of a task.
     * @throws EmptyTasklistException      If tasklist is empty.
     * @throws InvalidTaskIndexException   If task ID is lesser than 0 or greater than the tasklist size.
     * @throws ListFormatException         If the parameter 'filters' contains invalid filter flags.
     * @throws TaskIsNonRecurringException If the task is a non-recurring task.
     */
    public String listTaskRecurrence(Map<String, String> parameters) throws EmptyTasklistException,
            InvalidTaskIndexException, ListFormatException, TaskIsNonRecurringException {
        if (taskList.size() == 0) {
            throw new EmptyTasklistException();
        }

        String taskIdAsString = parameters.get(Command.MAIN_ARGUMENT);
        if (!taskIdAsString.matches(DIGIT_REGEX)) {
            throw new ListFormatException();
        }
        int taskId = Integer.parseInt(taskIdAsString);
        int taskIndex = taskId - 1;
        if (taskIndex < 0 || taskIndex > taskList.size() - 1) {
            throw new InvalidTaskIndexException(taskId);
        }
        RecurrenceEnum recurValue = taskList.get(taskIndex).getRecurrence();
        if (recurValue.equals(RecurrenceEnum.NONE)) {
            throw new TaskIsNonRecurringException();
        }

        List<LocalDateTime> recurredDatesList = new ArrayList<>();
        if (taskList.get(taskIndex) instanceof Todo) {
            recurredDatesList = getToDoListOfRecurrence((Todo) taskList.get(taskIndex));
        } else if (taskList.get(taskIndex) instanceof Deadline) {
            recurredDatesList = getDeadlineListOfRecurrence((Deadline) taskList.get(taskIndex));
        } else if (taskList.get(taskIndex) instanceof Event) {
            recurredDatesList = getEventListOfRecurrence((Event) taskList.get(taskIndex));
        }
        return getListTaskRecurrenceMessage(taskList.get(taskIndex).getTaskEntryDescription(), recurredDatesList,
                numOfRecurredDates);
    }

    //@@author APZH
    /**
     * Returns a formatted message of the next 4 recurrences of a task as a {@code String}.
     */
    private String getListTaskRecurrenceMessage(String task, List<LocalDateTime> recurredDatesList, int numRecurrence) {
        String dates = "Listing next " + numRecurrence + " recurrences for:\n" + task + "\n";
        for (int i = 0; i < numRecurrence; i++) {
            dates += "-> " + DateParser.dateToString(recurredDatesList.get(i)) + "\n";
        }
        return LIST_HEADER + dates;
    }

    //@@author APZH
    /**
     * Returns a {@code List} of the next 4 recurrences of a to-do task.
     *
     * @param task To-do task to get the recurrences for.
     * @return Next 4 recurrences of the to-do task as a {@code List}.
     */
    private List<LocalDateTime> getToDoListOfRecurrence(Todo task) {
        LocalDateTime initialDate = task.getDoOnDate();
        return task.getRecurrence().getNextNRecurredDates(initialDate, numOfRecurredDates);
    }

    //@@author APZH
    /**
     * Returns a {@code List} of the next 4 recurrences of a deadline task.
     *
     * @param task Deadline task to get the recurrences for.
     * @return Next 4 recurrences of the deadline task as a {@code List}.
     */
    private List<LocalDateTime> getDeadlineListOfRecurrence(Deadline task) {
        LocalDateTime initialDate = task.getDueDate();
        return task.getRecurrence().getNextNRecurredDates(initialDate, numOfRecurredDates);
    }

    //@@author APZH
    /**
     * Returns a {@code List} of the next 4 recurrences of a event task.
     *
     * @param task Event task to get the recurrences for.
     * @return Next 4 recurrences of the event task as a {@code List}.
     */
    private List<LocalDateTime> getEventListOfRecurrence(Event task) {
        LocalDateTime initialDate = task.getStartDate();
        return task.getRecurrence().getNextNRecurredDates(initialDate, numOfRecurredDates);
    }
    
    //@@author APZH
    public String sortTasklist(Map<String, String> criteria) throws EmptyTasklistException,
            SortFormatException, EmptySortCriteriaException {
        Log.info("sortTasklist method called");
        String sortCriteria = "";

        if (getTaskListSize() == 0) {
            Log.warning("tasklist is empty, throwing EmptyTasklistException");
            throw new EmptyTasklistException();
        }
        if (criteria.containsKey(SortFlag.SORT_BY)) {
            sortCriteria = criteria.get(SortFlag.SORT_BY);
        } else {
            Log.warning("user did not indicate 'by' flag, throwing SortFormatException");
            throw new SortFormatException();
        }
        if (sortCriteria.isEmpty()) {
            Log.warning("user did not indicate any sort criteria, throwing EmptySortCriteriaException");
            throw new EmptySortCriteriaException();
        }

        switch (sortCriteria) {
        case "type":
            SortByTaskType sortByTaskType = new SortByTaskType();
            Collections.sort(taskList, sortByTaskType);
            break;
        case "description":
            SortByDescription sortByDescription = new SortByDescription();
            Collections.sort(taskList, sortByDescription);
            break;
        case "priority":
            SortByPriority sortByPriority = new SortByPriority();
            Collections.sort(taskList, sortByPriority);
            break;
        default:
            return "The sort criteria entered is not valid";
        }

        Log.info("end of sortTasklist - no issues detected");
        updateObservers();
        return "[!] Tasklist has been sorted by " + sortCriteria;
    }

    //@@author APZH
    private class SortByTaskType implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.getTaskType().name().compareTo(o2.getTaskType().name());
        }
    }

    //@@author APZH
    private class SortByDescription implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.getDescription().compareTo(o2.getDescription());
        }
    }

    //@@author APZH
    private class SortByPriority implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {

            if (o1.getPriority().equals(PriorityEnum.LOW) && (o2.getPriority().equals(PriorityEnum.MEDIUM)
                    || o2.getPriority().equals(PriorityEnum.HIGH))) {
                return -1;
            }

            if (o1.getPriority().equals(PriorityEnum.MEDIUM) && o2.getPriority().equals(PriorityEnum.HIGH)) {
                return -1;
            }

            if (o1.getPriority().equals(PriorityEnum.HIGH) && (o2.getPriority().equals(PriorityEnum.MEDIUM)
                    || o2.getPriority().equals(PriorityEnum.LOW))) {
                return 1;
            }

            if (o1.getPriority().equals(PriorityEnum.MEDIUM) && o2.getPriority().equals(PriorityEnum.LOW)) {
                return 1;
            }
            // Returns 0 if both priorities are equal
            return 0;
        }
    }

    //@@author SeanRobertDH
    public int getTaskListSize() {
        return taskList.size();
    }

    //@@author SeanRobertDH
    public boolean isEmpty() {
        return getTaskListSize() == 0;
    }

    //@@author SeanRobertDH
    public Task getTask(int index) {
        return taskList.get(index);
    }

    //@@author SeanRobertDH
    public void addTask(Task task) {
        taskList.add(task);
        updateObservers();
    }

    public void addTasks(List<Task> tasks) {
        taskList.addAll(tasks);
        updateObservers();
    }

    //@@author SeanRobertDH
    public void checkFilteredListIndexValid(int index) throws InvalidTaskIndexException {
        if (latestFilteredList.isEmpty()) {
            latestFilteredList = taskList;
        }
        if (index < 0 || index > latestFilteredList.size() - 1) {
            throw new InvalidTaskIndexException(++index);
        }
    }

    //@@author SeanRobertDH
    public Task deleteFilteredTask(int index) throws InvalidTaskIndexException {
        checkFilteredListIndexValid(index);
        Task deletedTask = latestFilteredList.remove(index);
        taskList.remove(deletedTask);
        updateObservers();
        return deletedTask;
    }

    //@@author SeanRobertDH
    private void updateFilteredTaskList(List<Task> replacementTaskList) {
        latestFilteredList = replacementTaskList;
    }

    //@@author SeanRobertDH
    public Task editFilteredTask(int index, Map<String, String> arguments)
            throws InvalidTaskIndexException, InvalidPriorityException,
            InvalidRecurrenceException, ParseDateFailedException, StartDateAfterEndDateException {
        checkFilteredListIndexValid(index);
        latestFilteredList.get(index).edit(arguments);
        updateObservers();
        return latestFilteredList.get(index);
    }

    /*
    //@@author SeanRobertDH
    public void checkIndexValid(int index) throws InvalidTaskIndexException {
        if (index < 0 || index > getTaskListSize() - 1) {
            throw new InvalidTaskIndexException(++index);
        }
    }

    //@@author SeanRobertDH
    public Task deleteTask(int index) throws InvalidTaskIndexException {
        checkIndexValid(index);
        Task deletedTask = taskList.remove(index);
        updateObservers(this);
        return deletedTask;
    }
    */

    //@@author SeanRobertDH
    public void clear() {
        taskList.clear();
    }
}
