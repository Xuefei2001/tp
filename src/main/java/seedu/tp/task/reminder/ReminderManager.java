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

//@@author Xuefei2001
/**
 * Handle checking the task list for reminder and cutomization of reminder.
 */
public class ReminderManager {

    private static final String LINE_SEPARATOR = "---------------------------------------"
            + "----------------------------------";

    public ReminderManager() {

    }

    //@@author Xuefei2001
    /**
     * Check through the current task list and give the reminder message need to be sent if any.
     */
    public static String sendReminder(TaskManager taskManager) {
        LocalDateTime now = LocalDateTime.now();
        String message = "";
        if (taskManager != null) {
            for (int i = 0; i < taskManager.getTaskListSize(); i++) {
                if (taskManager.getTask(i).needReminder()) {
                    message += (taskManager.getTask(i).getReminderMessage(now));
                }
            }
        }
        return message;
    }

    public static void printReminder(TaskManager taskManager) {
        String message = sendReminder(taskManager);
        if (!message.equals("")) {
            System.out.println(message);
            System.out.println(LINE_SEPARATOR);
        }
    }

    public static void customizeReminderMinute(long userMinute, int index, TaskManager taskManager)
            throws InvalidTaskIndexException, ReminderNotRequiredException {
        if (index < 0 || index > taskManager.getTaskListSize() - 1) {
            throw new InvalidTaskIndexException(++index);
        } else if (taskManager.getTask(index).needReminder()) {
            Reminder reminder = taskManager.getTask(index).getReminder();
            reminder.setUserMinute(userMinute);
        } else {
            throw new ReminderNotRequiredException();
        }

    }

    public static void customizeReminderDay(long userDay, int index, TaskManager taskManager)
            throws InvalidTaskIndexException, ReminderNotRequiredException {
        if (index < 0 || index > taskManager.getTaskListSize() - 1) {
            throw new InvalidTaskIndexException(++index);
        } else if (taskManager.getTask(index).needReminder()) {
            Reminder reminder = taskManager.getTask(index).getReminder();
            reminder.setUserDay(userDay);
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

    //@@author Xuefei2001
    /**
     * Change the minute,day and message according to user input.
     *
     * @return outMessage the message showing what has been updated.
     * @throws NumberFormatException if the field needs to be integer but put in something else by user
     * @throws MissingReminderFieldException if no fields are used, nothing need to be changed
     * @throws MissingUserTimeException if the place that need a time input is empty
     * @throws MissingUserMessageException if the place that need a message input is empty
     */
    public static String customizeReminder(TaskManager taskManager, Map<String, String> commandArguments)
            throws NumberFormatException, MissingUserTimeException,
            MissingUserMessageException, MissingReminderFieldException {
        int index = Integer.parseInt(commandArguments.get(Command.MAIN_ARGUMENT));
        String outMessage = "";
        if (commandArguments.containsKey(ReminderFlag.MINUTE_AHEAD)) {
            if (commandArguments.get(ReminderFlag.MINUTE_AHEAD) != null) {
                long userMinute = Long.parseLong(commandArguments.get(ReminderFlag.MINUTE_AHEAD));
                try {
                    customizeReminderMinute(userMinute, index - 1, taskManager);
                    outMessage += "The minutes for reminder is updated to " + userMinute + "\n";
                } catch (InvalidTaskIndexException itie) {
                    return itie.getMessage();
                } catch (ReminderNotRequiredException rnre) {
                    return rnre.toString();
                }
            } else {
                throw new MissingUserTimeException();
            }
        }
        if (commandArguments.containsKey(ReminderFlag.DAY_AHEAD)) {
            if (commandArguments.get(ReminderFlag.DAY_AHEAD) != null) {
                long userDay = Long.parseLong(commandArguments.get(ReminderFlag.DAY_AHEAD));
                try {
                    customizeReminderDay(userDay, index - 1, taskManager);
                    outMessage += "The days for reminder is updated to " + userDay + "\n";
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
        if (!commandArguments.containsKey(ReminderFlag.MINUTE_AHEAD)
                && !commandArguments.containsKey(ReminderFlag.DAY_AHEAD)
                && !commandArguments.containsKey(ReminderFlag.REMINDER_MESSAGE)) {
            throw new MissingReminderFieldException();
        }
        return outMessage;
    }
}
