package seedu.teachbook.logic.commands;

import seedu.teachbook.logic.commands.exceptions.CommandException;
import seedu.teachbook.model.Model;
import seedu.teachbook.model.gradeobject.GradeComparator;
import seedu.teachbook.model.student.NameComparator;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts students by their names or grades.\n"
            + "Example: " + COMMAND_WORD + " name, " + COMMAND_WORD + " grade";

    public static final String MESSAGE_SUCCESS_SORT_NAME = "Sorted students by names";
    public static final String MESSAGE_SUCCESS_SORT_GRADE = "Sorted students by grades (High -> Low)";
    public static final String MESSAGE_GRADING_SYSTEM_NOT_SET = "Set a grading system before sorting";


    private final boolean isName;
    private final boolean isGrade;

    public SortCommand(boolean isName, boolean isGrade) {
        this.isName = isName;
        this.isGrade = isGrade;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert isName != isGrade;
        if (isName) {
            model.reorderStudents(new NameComparator());
        } else {
            if (!model.hasExistingGradingSystem()) {
                throw new CommandException(MESSAGE_GRADING_SYSTEM_NOT_SET);
            }
            model.reorderStudents(new GradeComparator(model.getGradingSystem().getGradeList()));
        }

        model.commitTeachBook();
        if (isName) {
            return new CommandResult(MESSAGE_SUCCESS_SORT_NAME);
        } else {
            return new CommandResult(MESSAGE_SUCCESS_SORT_GRADE);
        }
    }
}
