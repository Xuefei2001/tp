package seedu.duke.command;

import java.util.Map;

import seedu.duke.exception.EmptyTasklistException;
import seedu.duke.exception.InvalidPriorityException;
import seedu.duke.exception.InvalidRecurrenceException;
import seedu.duke.exception.InvalidTaskIndexException;
import seedu.duke.exception.InvalidTaskTypeException;
import seedu.duke.exception.ListFormatException;
import seedu.duke.exception.MissingFilterArgumentException;
import seedu.duke.exception.TaskIsNonRecurringException;
import seedu.duke.task.taskmanager.TaskManager;

//@@author APZH
public class ListCommand extends Command {

    private static final String USAGE = "list";

    public ListCommand(TaskManager taskManager, Map<String, String> commandArguments) {
        super(taskManager, commandArguments);
    }

    //@@author APZH
    @Override
    public CommandResult executeCommand() {
        String message = "";
        boolean containsMainArgument = commandArguments.containsKey(MAIN_ARGUMENT);

        try {
            if (!containsMainArgument || (containsMainArgument && commandArguments.get(MAIN_ARGUMENT).equals(""))) {
                message = taskManager.listTasklistWithFilter(commandArguments);
            } else if (containsMainArgument) {
                message = taskManager.listTaskRecurrence(commandArguments);
            }
        } catch (EmptyTasklistException ete) {
            message = ete.toString();
        } catch (ListFormatException lfe) {
            message = lfe.toString();
        } catch (MissingFilterArgumentException mfae) {
            message = mfae.toString();
        } catch (InvalidTaskIndexException itie) {
            message = itie.toString();
        } catch (InvalidTaskTypeException itte) {
            message = itte.toString();
        } catch (InvalidPriorityException ipe) {
            message = ipe.toString();
        } catch (InvalidRecurrenceException ire) {
            message = ire.toString();
        } catch (TaskIsNonRecurringException tnrx) {
            message = tnrx.toString();
        }
        return new CommandResult(message, false, false);
    }

    @Override
    protected String getUsage() {
        return USAGE;
    }
}
