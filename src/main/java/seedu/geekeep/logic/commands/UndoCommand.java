package seedu.geekeep.logic.commands;

import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.Model.NothingToUndoException;

/**
 * Lists all persons in the address book to the user.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undid previous command";
    public static final String MESSAGE_NOTHING_TO_UNDO = "Nothing to undo";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (NothingToUndoException e) {
            throw new CommandException(MESSAGE_NOTHING_TO_UNDO);
        }
    }
}