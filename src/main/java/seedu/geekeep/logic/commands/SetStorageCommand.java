package seedu.geekeep.logic.commands;

import seedu.geekeep.logic.commands.exceptions.CommandException;

/**
 * Lists all tasks in GeeKeep to the user.
 */
public class SetStorageCommand extends Command {
    public static final String COMMAND_WORD = "setstorage";
    public static final String MESSAGE_SUCCESS = "GeeKeep file path successfully set to %1$s";

    private String filePath;

    public SetStorageCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.setGeekeepFilePath(filePath);
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }
}
