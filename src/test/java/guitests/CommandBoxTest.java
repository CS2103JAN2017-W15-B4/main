package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.geekeep.ui.CommandBox;

public class CommandBoxTest extends GeeKeepGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = "find NOSUCHTASKEXIST";
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    @Before
    public void setUp() {
        defaultStyleOfCommandBox = new ArrayList<>(commandBox.getStyleClass());
        assertFalse("CommandBox default style classes should not contain error style class.",
                    defaultStyleOfCommandBox.contains(CommandBox.ERROR_STYLE_CLASS));

        // build style class for error
        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_commandSucceeds_textClearedAndStyleClassRemainsTheSame() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandFails_textStaysAndErrorStyleClassAdded() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput());
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandSucceedsAfterFailedCommand_textClearedAndErrorStyleClassRemoved() {
        //One failed command
        commandBox.runCommand(COMMAND_THAT_FAILS);

        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

    //@@author A0148037E
    @Test
    public void commandBox_commandSucceedsAfterMultipleFailedCommands_textClearedAndErrorStyleClassRemoved() {
        //Multiple failed commands
        commandBox.runCommand(COMMAND_THAT_FAILS);
        commandBox.runCommand(COMMAND_THAT_FAILS);

        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }
    //@@author
}
