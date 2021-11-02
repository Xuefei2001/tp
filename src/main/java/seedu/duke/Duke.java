package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.command.CommandEnum;
import seedu.duke.command.CommandResult;
import seedu.duke.parser.CommandParser;
import seedu.duke.storage.FileCreater;
import seedu.duke.storage.StorageManager;
import seedu.duke.task.reminder.ReminderManager;
import seedu.duke.task.taskmanager.TaskManager;
import seedu.duke.ui.Ui;

import java.util.Scanner;

public class Duke {

    private final Scanner in;
    private final Ui ui;
    private ReminderManager reminderManager;
    private TaskManager taskManager;

    public Duke() {
        in = new Scanner(System.in);
        ui = new Ui();
        reminderManager = new ReminderManager();
        taskManager = new TaskManager();
    }

    public String readInput() {
        ui.printCursor();
        if (!in.hasNextLine()) {
            return CommandEnum.BYE.toString();
        }
        String input = in.nextLine();
        return input;
    }

    public CommandResult runCommand(Command userCommand) {
        CommandResult commandResult = null;
        try {
            commandResult = userCommand.executeCommand();
        } catch (Exception e) {
            commandResult = new CommandResult(e.toString(), false, false);
        }
        return commandResult;
    }

    public String checkReminder() {
        return reminderManager.sendReminder(taskManager);
    }

    public void startProgram() {

        ui.printLogo();

        StorageManager.readLocalData(taskManager);

        Command userCommand;
        CommandResult commandResult = null;

        do {

            String userInput = readInput();

            userCommand = CommandParser.parseCommand(taskManager, userInput);

            commandResult = runCommand(userCommand);

            ui.printMessage(commandResult.getMessage());

        } while (commandResult.getIsExited() != true);

    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.startProgram();
    }
}
