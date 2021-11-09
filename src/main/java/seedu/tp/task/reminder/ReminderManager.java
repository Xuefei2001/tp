package seedu.tp.task.reminder;

import seedu.tp.command.Command;
import seedu.tp.command.flags.ReminderFlag;
import seedu.tp.exception.InvalidTaskIndexException;
import seedu.tp.exception.ReminderNotRequiredException;
import seedu.tp.exception.MissingUserTimeException;
import seedu.tp.exception.MissingUserMessageException;
import seedu.tp.exception.MissingReminderFieldException;
import seedu.tp.task.taskmanager.TaskManager;

import java.time.LocalDateTime;
import java.util.Map;

public class ReminderManager {

    public ReminderManager() {

    }

    public static String sendReminder(TaskManager taskManager) {
        LocalDateTime now = LocalDateTime.now();
        String message = "";

        for (int i = 0; i < taskManager.getTaskListSize(); i++) {
            if (taskManager.getTask(i).needReminder()) {
                message += (taskManager.getTask(i).getReminder(now));
            }
        }
        return message;
    }

    public static void customizeReminderTime(long userTime, int index, TaskManager taskManager)
            throws InvalidTaskIndexException, ReminderNotRequiredException {
        if (index < 0 || index > taskManager.getTaskListSize() - 1) {
            throw new InvalidTaskIndexException(++index);
        } else if (taskManager.getTask(index).needReminder()) {
            Reminder reminder = taskManager.getTask(index).getReminder();
            reminder.setUserTime(userTime);
        } else {
            throw new ReminderNotRequiredException();
        }

    }

    public static void customizeReminderMessage(String message, int index, TaskManager taskManager)
            throws InvalidTaskIndexException, ReminderNotRequiredException {
        if (index < 0 || index > taskManager.getTaskListSize() - 1) {
            throw new InvalidTaskIndexException(++index);
        } else if (taskManager.getTask(index).needReminder()) {
            Reminder reminder = taskManager.getTask(index).getReminder();
            reminder.setMessage(message);
        } else {
            throw new ReminderNotRequiredException();
        }
    }

    public static String customizeReminder(TaskManager taskManager, Map<String, String> commandArguments)
            throws NumberFormatException, MissingUserTimeException,
            MissingUserMessageException, MissingReminderFieldException {
        int index = Integer.parseInt(commandArguments.get(Command.MAIN_ARGUMENT));
        String outMessage = "";
        if (commandArguments.containsKey(ReminderFlag.TIME_AHEAD)) {
            if (commandArguments.get(ReminderFlag.TIME_AHEAD) != null) {
                long userTime = Long.parseLong(commandArguments.get(ReminderFlag.TIME_AHEAD));
                try {
                    customizeReminderTime(userTime, index - 1, taskManager);
                    outMessage += "The time for reminding before task is updated to " + userTime + " minutes.";
                } catch (InvalidTaskIndexException itie) {
                    return itie.getMessage();
                } catch (ReminderNotRequiredException rnre) {
                    return rnre.toString();
                }
            } else {
                throw new MissingUserTimeException();
            }
        }
        if (commandArguments.containsKey(ReminderFlag.REMINDER_MESSAGE)) {
            if (commandArguments.get(ReminderFlag.REMINDER_MESSAGE) != null) {
                String message = commandArguments.get(ReminderFlag.REMINDER_MESSAGE);
                try {
                    customizeReminderMessage(message, index - 1, taskManager);
                    outMessage += "The reminder message is updated to " + message;
                } catch (InvalidTaskIndexException itie) {
                    return itie.getMessage();
                } catch (ReminderNotRequiredException rnre) {
                    return rnre.toString();
                }
            } else {
                throw new MissingUserMessageException();
            }
        }
        if (!commandArguments.containsKey(ReminderFlag.TIME_AHEAD)
            && !commandArguments.containsKey(ReminderFlag.REMINDER_MESSAGE)) {
            throw new MissingReminderFieldException();
        }
        return outMessage;
    }
}
