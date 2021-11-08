package seedu.tp.command;

import seedu.tp.exception.MissingReminderFieldException;
import seedu.tp.exception.MissingUserMessageException;
import seedu.tp.exception.MissingUserTimeException;
import seedu.tp.task.taskmanager.TaskManager;
import seedu.tp.task.reminder.ReminderManager;

import java.util.Map;

//@@author Xuefei2001
/**
 * To be used for reminder command, to customize reminder time and message.
 */
public class ReminderCommand extends Command {
    private static final String USAGE = "-> Setting a reminder for a task: reminder <index> "
            + "[--minute <minute>]  [--day <day>] [--message <your reminder message>]";

    public ReminderCommand(TaskManager taskManager, Map<String, String> commandArguments) {
        super(taskManager, commandArguments);
    }

    @Override
    protected String getUsage() {
        return USAGE;
    }

    @Override
    //@@author Xuefei2001
    /**
     * Execute reminder command to change reminder time and message,
     * according to the values from {@code commandArguments}
     *{@code message} is used to record the message after executing the command,
     * either tells reminder updated or tells the issue come across.
     */
    public CommandResult executeCommand() throws Exception {
        String message = "";
        try {
            message = ReminderManager.customizeReminder(taskManager, commandArguments);
        } catch (NumberFormatException nfe) {
            message = "Please use integer values for time and index";
        } catch (MissingUserTimeException mute) {
            message = mute.getMessage();
        } catch (MissingUserMessageException mume) {
            message = mume.getMessage();
        } catch (MissingReminderFieldException mrfe) {
            message = mrfe.getMessage();
        }
        return new CommandResult(message, false);
    }
}
