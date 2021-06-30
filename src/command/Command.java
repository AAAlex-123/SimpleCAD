package command;

import java.awt.Frame;
import java.io.Serializable;
import java.util.List;

import application.editor.Editor;
import application.editor.Undoable;
import components.ComponentType;
import requirement.Requirements;

/**
 * An implementation of the {@code Undoable} interface, specific to this
 * Application. {@link command.Command Commands} have certain
 * {@link requirement.Requirement requirements}, act on a
 * {@link application.editor.Editor context} and manipulate
 * {@link components.Component Components} by creating or deleting them.
 *
 * @author alexm
 */
public abstract class Command implements Undoable, Serializable, Cloneable {

	private static final long serialVersionUID = 4L;

	/**
	 * Creates a Command that creates a {@code Component} of the given
	 * {@link components.ComponentType Type}.
	 *
	 * @param componentType the type of the Component
	 *
	 * @return the Command
	 */
	public static Command create(ComponentType componentType) {
		return new CreateCommand(null, componentType);
	}

	/**
	 * Creates a Command that creates a composite {@code Gate}.
	 *
	 * @param commands    the instructions to create the Gate
	 * @param description the description
	 *
	 * @return the Command
	 *
	 * @see components.ComponentType#GATE
	 */
	public static Command create(List<Command> commands, String description) {
		return new CreateGateCommand(null, commands, description);
	}

	/**
	 * Creates a Command that deletes a {@code Component}.
	 *
	 * @return the Command
	 */
	public static Command delete() {
		return new DeleteCommand(null);
	}

	/** What this Command needs to execute */
	protected Requirements<String> requirements;

	/** Where this Command will act */
	protected transient Editor context;

	/**
	 * Constructs the Command with the given {@code context}.
	 *
	 * @param editor the context
	 */
	public Command(Editor editor) {
		context = editor;
		requirements = new Requirements<>();
	}

	/**
	 * Clones the Command so that the execution of this Command doesn't affect
	 * future executions.
	 *
	 * @return the cloned Command
	 */
	@Override
	public abstract Command clone();

	/**
	 * Fulfils the Command's {@code requirements} with a dialog while also
	 * specifying its {@code context}.
	 *
	 * @param parentFrame the parent of the dialog
	 * @param newContext  the Command's context
	 */
	public void fillRequirements(Frame parentFrame, Editor newContext) {
		requirements.fulfillWithDialog(parentFrame, toString());
		context(newContext);
	}

	/**
	 * Returns whether or not this Command is ready to be executed.
	 *
	 * @return {@code true} if ready to be executed, {@code false} otherwise
	 */
	public final boolean canExecute() {
		return requirements.fulfilled();
	}

	/**
	 * Sets the Command's {@code context}.
	 *
	 * @param c the context
	 */
	public final void context(Editor c) {
		context = c;
	}

	@Override
	public abstract String toString();
}
