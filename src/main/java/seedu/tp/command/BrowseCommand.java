package seedu.tp.command;

import seedu.tp.exception.BrowseFailException;
import seedu.tp.exception.InvalidTaskIndexException;
import seedu.tp.exception.NoLinkException;
import seedu.tp.exception.NotALessonException;
import seedu.tp.parser.CommandParser;
import seedu.tp.task.Task;
import seedu.tp.task.taskmanager.TaskManager;
import seedu.tp.task.type.Lesson;
import seedu.tp.utility.ExternalHelper;

import java.net.URI;
import java.util.Map;

//@@author SuibianP
/**
 * The command for browsing a link within a Lesson.
 */
public class BrowseCommand extends Command {
    private static final String USAGE = "-> Launching a link tagged to a task: browse <index>";

    public BrowseCommand(TaskManager taskManager, Map<String, String> commandArguments) {
        super(taskManager, commandArguments);
    }

    @Override
    protected String getUsage() {
        return USAGE;
    }

    @Override
    public CommandResult executeCommand() throws Exception {
        String message;
        try {
            String mainArgument = getMainArgument();
            if (mainArgument == null) {
                throw new NullPointerException();
            }
            int index = CommandParser.parseTaskIndex(mainArgument) - 1;
            assert taskManager != null;
            Task task = taskManager.getFilteredTask(index);
            if (!(task instanceof Lesson)) {
                throw new NotALessonException();
            }
            URI link = ((Lesson) task).getLink();
            ExternalHelper.browseUri(link);
            message = link.toString();
        } catch (NullPointerException npe) {
            message = USAGE;
        } catch (ClassCastException cce) {
            message = cce.getMessage();
        } catch (InvalidTaskIndexException itie) {
            message = itie.getMessage();
        } catch (BrowseFailException bfe) {
            message = bfe.getMessage();
        } catch (NoLinkException nle) {
            message = nle.getMessage();
        } catch (NotALessonException nale) {
            message = nale.getMessage();
        }
        return new CommandResult(message, false);
    }
}
