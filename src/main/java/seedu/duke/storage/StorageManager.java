package seedu.duke.storage;

import seedu.duke.command.CommandResult;
import seedu.duke.task.taskmanager.TaskManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StorageManager {
    public static void readLocalData(TaskManager taskManager) {
        try {
            FileCreater.createAll();
            FileReader.initializeList(taskManager);
        } catch (IOException ioe) {
            System.out.println("Something went wrong: \n" + ioe.getMessage());
        } catch (Exception e) {
            CommandResult failedResult = new CommandResult(e.toString(), false, false);
            System.out.println(failedResult.getMessage());
        }
    }
}
