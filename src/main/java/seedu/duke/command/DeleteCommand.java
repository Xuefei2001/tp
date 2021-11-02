package seedu.duke.command;

import java.util.Map;
import java.util.TreeSet;
import seedu.duke.exception.EmptyTasklistException;
import seedu.duke.exception.InvalidTaskIndexException;
import seedu.duke.local.DataManager;
import seedu.duke.parser.CommandParser;
import seedu.duke.task.Task;
import seedu.duke.task.taskmanager.TaskManager;

//@@author SeanRobertDH
public class DeleteCommand extends Command {

    private static final String TASK_DELETED = "Tasks deleted:\n";
    private static final String USAGE = "delete <index>";

    private static final String SPACE_REGEX = "[\\s|_]+";
    private static final String SEPARATOR = ",";
    private static final String LIST_NUMBERS = "-";


    public DeleteCommand(TaskManager taskManager, Map<String, String> commandArguments) {
        super(taskManager, commandArguments);
    }

    @Override
    public CommandResult executeCommand() throws Exception {
        String message = TASK_DELETED;
        try {
            String mainArgument = getMainArgument();
            if (mainArgument == null) {
                throw new NullPointerException();
            }
            if (taskManager.isEmpty()) {
                throw new EmptyTasklistException();
            }
            String[] taskIndexStrings = splitIndexesString(mainArgument);
            TreeSet<Integer> tasksToDelete = getTasksToDelete(taskIndexStrings);
            message += deleteTasks(tasksToDelete);
        } catch (NullPointerException npe) {
            message = getUsageMessage();
        } catch (EmptyTasklistException etle) {
            message = etle.getMessage();
        } catch (NumberFormatException nfe) {
            message = nfe.getMessage();
        } catch (InvalidTaskIndexException itie) {
            message = itie.getMessage();
        }
        return new CommandResult(message, true, false);
    }

    @Override
    protected String getUsage() {
        return USAGE;
    }

    private String[] splitIndexesString(String argument) {
        String parsedArgument = argument.replaceAll(SPACE_REGEX, "");
        return parsedArgument.split(SEPARATOR);
    }

    private TreeSet<Integer> getTasksToDelete(String[] indexStrings) throws NumberFormatException {
        TreeSet<Integer> indexes = new TreeSet<>();
        for (String indexString : indexStrings) {
            if (indexString.contains(LIST_NUMBERS)) {
                String[] listIndexes = indexString.split(LIST_NUMBERS, 2);
                Integer startIndex = CommandParser.parseTaskIndex(listIndexes[0]);
                Integer endIndex = CommandParser.parseTaskIndex(listIndexes[1]);
                for (Integer index = startIndex; index <= endIndex; index++) {
                    indexes.add(index);
                }
            } else {
                indexes.add(CommandParser.parseTaskIndex(indexString));
            }
        }
        return indexes;
    }

    private String deleteTasks(TreeSet<Integer> indexes) throws InvalidTaskIndexException {
        String message = "";
        int offset = 0;
        taskManager.checkFilteredListIndexValid(indexes.first() - 1);
        taskManager.checkFilteredListIndexValid(indexes.last() - 1);
        for (Integer index : indexes) {
            Task deletedTask = taskManager.deleteFilteredTask(index - 1 - offset++);
            DataManager.deleteTask(index - 1 - offset);
            message += deletedTask.getTaskEntryDescription() + '\n';
        }
        return message;
    }
}
