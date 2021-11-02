package seedu.duke.command;

import java.util.Map;
import seedu.duke.exception.EmptyTasklistException;
import seedu.duke.exception.InvalidPriorityException;
import seedu.duke.exception.InvalidTaskIndexException;
import seedu.duke.exception.ParseDateFailedException;
import seedu.duke.exception.StartDateAfterEndDateException;
import seedu.duke.parser.CommandParser;
import seedu.duke.task.Task;
import seedu.duke.task.taskmanager.TaskManager;

public class EditCommand extends Command {
    private static final String TASK_EDITED = "Tasks edited:\n";
    private static final String USAGE = "edit <index> --<flag> <value>";

    public EditCommand(TaskManager taskManager, Map<String, String> commandArguments) {
        super(taskManager, commandArguments);
    }

    @Override
    protected String getUsage() {
        return USAGE;
    }

    @Override
    public CommandResult executeCommand() throws Exception {
        String message = TASK_EDITED;
        try {
            String mainArgument = getMainArgument();
            if (mainArgument == null) {
                throw new NullPointerException();
            }
            if (taskManager.isEmpty()) {
                throw new EmptyTasklistException();
            }
            int index = CommandParser.parseTaskIndex(getMainArgument());
            Task editedTask = taskManager.editFilteredTask(index - 1, commandArguments);
            message += editedTask.getTaskEntryDescription();
        } catch (NullPointerException npe) {
            message = getUsageMessage();
        } catch (EmptyTasklistException etle) {
            message = etle.getMessage();
        } catch (NumberFormatException nfe) {
            message = nfe.getMessage();
        } catch (InvalidTaskIndexException itie) {
            message = itie.getMessage();
        } catch (InvalidPriorityException ipe) {
            message = ipe.getMessage();
        } catch (ParseDateFailedException pdfe) {
            message = pdfe.getMessage();
        } catch (StartDateAfterEndDateException sdaede) {
            message = sdaede.getMessage();
        }
        return new CommandResult(message, true, false);
    }
}
