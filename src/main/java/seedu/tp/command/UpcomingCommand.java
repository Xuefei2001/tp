package seedu.tp.command;

import seedu.tp.task.RecurrenceEnum;
import seedu.tp.task.Task;
import seedu.tp.task.taskmanager.TaskManager;
import seedu.tp.task.type.Lesson;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class UpcomingCommand extends Command {

    public static final String NO_UPCOMING_MESSAGE = "[!] There is no upcoming task in your tasklist.";

    public UpcomingCommand(TaskManager taskManager, Map<String, String> commandOptions) {
        super(taskManager, commandOptions);
    }

    @Override
    protected String getUsage() {
        return null;
    }


    private class SortByTaskHappenTime implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            assert o1.getListDate() != null && o2.getListDate() != null;
            return o1.getListDate().compareTo(o2.getListDate());
        }
    }

    @Override
    public CommandResult executeCommand() throws Exception {
        Period distance = Period.ofWeeks(1);
        LocalDateTime end = LocalDateTime.now().plus(distance);
        String message = taskManager.getAllTasksView().stream().filter(task -> {
            LocalDateTime startTime = task.getListDate();
            LocalDateTime next;
            if (startTime == null) {
                return true;
            }
            if (task instanceof Lesson) {
                next = ((Lesson)task).getNextOccurrence();
            } else {
                // check if start time fall in the next distance time
                return end.isAfter(task.getRecurrence().getNextRecurredDate(startTime));
            }
            return end.isAfter(next);
        }).sorted(new SortByTaskHappenTime()).map(Task::getTaskEntryDescription)
                .collect(Collectors.joining(System.lineSeparator()));
        if (message.isEmpty()) {
            message = NO_UPCOMING_MESSAGE;
        }
        return new CommandResult(message, false);
    }
}
