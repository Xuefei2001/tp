package seedu.tp;

import seedu.tp.command.Command;
import seedu.tp.command.CommandResult;
import seedu.tp.storage.DataManager;
import seedu.tp.parser.CommandParser;
import seedu.tp.storage.FileCreator;
import seedu.tp.task.taskmanager.TaskManager;
import seedu.tp.ui.Ui;

public class SchedUrMods {

    private Ui ui;
    private TaskManager taskManager;

    public SchedUrMods() {
        ui = new Ui();

        FileCreator fileCreator = new FileCreator();
        if (fileCreator.hasCreatedFolder()) {
            DataManager dataManager = new DataManager(fileCreator);
            taskManager = new TaskManager(dataManager);
        } else {
            taskManager = new TaskManager();
        }
    }

    public CommandResult runCommand(Command userCommand) {
        CommandResult commandResult = null;
        try {
            commandResult = userCommand.executeCommand();
        } catch (Exception e) {
            commandResult = new CommandResult(e.toString(), false);
        }
        return commandResult;
    }

    public void startProgram() {

        ui.printLogo();

        Command userCommand;
        CommandResult commandResult;

        do {

            String userInput = ui.readInput();

            userCommand = CommandParser.parseCommand(taskManager, userInput);

            commandResult = runCommand(userCommand);

            ui.printMessage(commandResult.getMessage());

        } while (commandResult.getIsExited() != true);
    }

    public static void main(String[] args) {
        SchedUrMods schedUrMods = new SchedUrMods();
        schedUrMods.startProgram();
    }
}
