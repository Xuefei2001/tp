package seedu.duke.task.factory;

import java.time.LocalDateTime;
import java.util.Map;

import seedu.duke.exception.GetTaskFailedException;
import seedu.duke.exception.ParseDateFailedException;
import seedu.duke.command.flags.TodoFlag;
import seedu.duke.parser.TaskParser;
import seedu.duke.task.PriorityEnum;
import seedu.duke.task.RecurrenceEnum;
import seedu.duke.task.Task;
import seedu.duke.task.TypeEnum;
import seedu.duke.task.type.Todo;

//@@author SeanRobertDH
public class TodoFactory extends TaskFactory {
    private static final TypeEnum taskType = TypeEnum.TODO;

    LocalDateTime doOnDate;

    public TodoFactory(Map<String, String> flags) {
        super(taskType, TodoFlag.REQUIRED_FLAGS, flags);
    }


    @Override
    void setAdditionalVariables() throws GetTaskFailedException {
        try {
            String doOn = flags.get(TodoFlag.DO_ON_DATE);
            doOnDate = TaskParser.getDate(doOn);

        } catch (ParseDateFailedException pdfe) {
            throw new GetTaskFailedException(pdfe.getMessage());
        }
    }

    @Override
    Task decideConstructor() {
        if (priorityEnum == null) {
            return getTodoWithDefaultPriority(description, doOnDate, recurrenceEnum);
        } else {
            return getTodoWithPriority(description, priorityEnum, doOnDate, recurrenceEnum);
        }
    }

    private static Todo getTodoWithDefaultPriority(String description, LocalDateTime doOn, RecurrenceEnum recurrence) {
        if (doOn == null) {
            return new Todo(description);
        } else {
            if (recurrence == null) {
                return new Todo(description, doOn);
            } else {
                return new Todo(description, doOn, recurrence);
            }
        }
    }

    private static Todo getTodoWithPriority(String description,
                                            PriorityEnum priority, LocalDateTime doOn, RecurrenceEnum recurrence) {
        if (doOn == null) {
            return new Todo(description, priority);
        } else {
            if (recurrence == null) {
                return new Todo(description, priority, doOn);
            } else {
                return new Todo(description, priority, doOn, recurrence);
            }
        }
    }
}
