package seedu.geekeep.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.geekeep.commons.core.Messages;
import seedu.geekeep.commons.util.CollectionUtil;
import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.Location;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Phone;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.Title;
import seedu.geekeep.model.task.UniqueTaskList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/91234567 e/johndoe@yahoo.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final int filteredPersonListIndex;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param filteredPersonListIndex the index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(int filteredPersonListIndex, EditPersonDescriptor editPersonDescriptor) {
        assert filteredPersonListIndex > 0;
        assert editPersonDescriptor != null;

        // converts filteredPersonListIndex from one-based to zero-based.
        this.filteredPersonListIndex = filteredPersonListIndex - 1;

        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

        if (filteredPersonListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToEdit = lastShownList.get(filteredPersonListIndex);
        Task editedTask = createEditedTask(personToEdit, editPersonDescriptor);

        try {
            model.updatePerson(filteredPersonListIndex, editedTask);
        } catch (UniqueTaskList.DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Title updatedTitle = editPersonDescriptor.getTitle().orElseGet(personToEdit::getTitle);
        Phone updatedPhone = editPersonDescriptor.getPhone().orElseGet(personToEdit::getPhone);
        DateTime updatedDateTime = editPersonDescriptor.getDateTime().orElseGet(personToEdit::getDateTime);
        Location updatedLocation = editPersonDescriptor.getLocation().orElseGet(personToEdit::getLocation);
        UniqueTagList updatedTags = editPersonDescriptor.getTags().orElseGet(personToEdit::getTags);

        return new Task(updatedTitle, updatedPhone, updatedDateTime, updatedLocation, updatedTags);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Optional<Title> title = Optional.empty();
        private Optional<Phone> phone = Optional.empty();
        private Optional<DateTime> dateTime = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.title = toCopy.getTitle();
            this.phone = toCopy.getPhone();
            this.dateTime = toCopy.getDateTime();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.title, this.phone, this.dateTime, this.location, this.tags);
        }

        public void setTitle(Optional<Title> title) {
            assert title != null;
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return title;
        }

        public void setPhone(Optional<Phone> phone) {
            assert phone != null;
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return phone;
        }

        public void setDateTime(Optional<DateTime> dateTime) {
            assert dateTime != null;
            this.dateTime = dateTime;
        }

        public Optional<DateTime> getDateTime() {
            return dateTime;
        }

        public void setLocation(Optional<Location> location) {
            assert location != null;
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return location;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
