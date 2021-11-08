package seedu.tp.command;

import java.util.Map;
import seedu.tp.command.flags.DeadlineFlag;
import seedu.tp.command.flags.EventFlag;
import seedu.tp.command.flags.LessonFlag;
import seedu.tp.command.flags.TaskFlag;
import seedu.tp.command.flags.TodoFlag;
import seedu.tp.exception.EmptyDescriptionException;
import seedu.tp.exception.EmptyTasklistException;
import seedu.tp.exception.InvalidFlagsException;
import seedu.tp.exception.InvalidPriorityException;
import seedu.tp.exception.InvalidTaskIndexException;
import seedu.tp.exception.ParseDateFailedException;
import seedu.tp.exception.StartDateAfterEndDateException;
import seedu.tp.parser.CommandParser;
import seedu.tp.parser.DateParser;
import seedu.tp.parser.TaskUsageParser;
import seedu.tp.task.Task;
import seedu.tp.task.taskmanager.TaskManager;

/**
 * Class for EditCommand. to be executed when editing tasks from {@link #taskManager}.
 */
public class EditCommand extends Command {
    private static final String TASK_EDITED = "Task(s) edited:\n";
    private static final String USAGE = "-> Editing a task: edit <index> <--flag value> [--flag value] "
        + "[--flag value]...";

    private static final String SEE_USAGE_MESSAGE = "Type 'edit' to see command usage.";

    private static final String FLAG_HEADER = CommandParser.FLAG_HEADER;
    private static final char NEWLINE = '\n';

    private static final String FLAGS_MESSAGE =
        "Flags:\n"
            + FLAG_HEADER + TaskFlag.EDIT_DESCRIPTION + " <description> modifies Task description." + NEWLINE
            + FLAG_HEADER + TaskFlag.PRIORITY + " <" + TaskUsageParser.getPrioritiesListString()
                + "> modifies Task priority." + NEWLINE
            + FLAG_HEADER + TaskFlag.RECURRENCE + " <" + TaskUsageParser.getRecurrencesListString()
                + "> modifies Task recurrence." + NEWLINE
            + FLAG_HEADER + TodoFlag.DO_ON_DATE + " <" + DateParser.getDefaultDateFormat()
                + "> modifies when Todo is to be done." + NEWLINE
            + FLAG_HEADER + DeadlineFlag.DUE_DATE + " <" + DateParser.getDefaultDateFormat()
            + "> modifies when Deadline is to due." + NEWLINE
            + FLAG_HEADER + EventFlag.START_DATE + " <" + DateParser.getDefaultDateFormat()
            + "> modifies when Event starts." + NEWLINE
            + FLAG_HEADER + EventFlag.END_DATE + " <" + DateParser.getDefaultDateFormat()
            + "> modifies when Event ends." + NEWLINE
            + FLAG_HEADER + LessonFlag.LINK + " <url> modifies your Lesson link.";

    /**
     * Constructs the EditCommand with the program {@link #taskManager}
     * and the <code>commandArguments</code> specified in Command.
     *
     * @param taskManager      the program's {@link seedu.tp.task.taskmanager.TaskManager}.
     * @param commandArguments a <code>Map&lt;String, String&gt;</code>
     *                         of <code>flags</code> to <code>flag values</code>.
     */
    public EditCommand(TaskManager taskManager, Map<String, String> commandArguments) {
        super(taskManager, commandArguments);
    }

    @Override
    protected String getUsage() {
        return USAGE;
    }

    /**
     * Parses and edits the task specified in {@link #getMainArgument()}
     * using the {@link seedu.tp.command.flags.TaskFlag#EDIT_DESCRIPTION} to change
     * Task description and respective flags in superclasses of {@link seedu.tp.command.flags.TaskFlag}
     * to modify Task superclass values.
     *
     * @return The message from the command in CommandResult.
     * @throws java.lang.Exception Any uncaught Exceptions.
     */
    @Override
    public CommandResult executeCommand() throws Exception {
        String message = TASK_EDITED;
        try {
            String mainArgument = getMainArgument();
            if (mainArgument == null || mainArgument.equals("") || commandArguments.size() == 1) {
                throw new NullPointerException();
            }
            if (taskManager.isEmpty()) {
                throw new EmptyTasklistException();
            }
            int index = CommandParser.parseTaskIndex(mainArgument);
            Task editedTask = taskManager.editFilteredTask(index - 1, commandArguments);
            message += editedTask.getTaskEntryDescription();
        } catch (NullPointerException npe) {
            message = getUsageMessage() + NEWLINE + FLAGS_MESSAGE;
        } catch (EmptyTasklistException etle) {
            message = etle.getMessage();
        } catch (NumberFormatException nfe) {
            message = nfe.getMessage();
        } catch (EmptyDescriptionException ede) {
            message = ede.getMessage();
        } catch (InvalidTaskIndexException itie) {
            message = itie.getMessage();
        } catch (InvalidPriorityException ipe) {
            message = ipe.getMessage();
        } catch (ParseDateFailedException pdfe) {
            message = pdfe.getMessage();
        } catch (StartDateAfterEndDateException sdaede) {
            message = sdaede.getMessage();
        } catch (InvalidFlagsException ife) {
            message = ife.getMessage();
            message += SEE_USAGE_MESSAGE;
        }
        return new CommandResult(message, false);
    }
}
