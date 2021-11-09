package seedu.tp.command;

import seedu.tp.exception.MissingReminderFieldException;
import seedu.tp.exception.MissingUserMessageException;
import seedu.tp.exception.MissingUserTimeException;
import seedu.tp.task.taskmanager.TaskManager;
import seedu.tp.task.reminder.ReminderManager;

import java.util.Map;

public class ReminderCommand extends Command {
    private static final String USAGE = "-> Setting a reminder for a task: reminder <index>";

    public ReminderCommand(TaskManager taskManager, Map<String, String> commandArguments) {
        super(taskManager, commandArguments);
    }

    @Override
    protected String getUsage() {
        return USAGE;
    }

    @Override
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
