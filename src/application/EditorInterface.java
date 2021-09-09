package application;

import application.editor.FileInfo;
import application.editor.StatusBar;

/**
 * Interface for an Editor that edits a File, which will be used in the context
 * of an {@code EditorManager}. Editors need to be able to {@code close} and
 * provide information about their status and the file they edit in order for
 * the {@code EditorManager} to accurately represent them.
 *
 * @author Alex Mandelias
 *
 * @see EditorManager
 */
public interface EditorInterface {

	/**
	 * Closes this Editor, optionally asking for confirmation to save if there are
	 * unsaved changes.
	 *
	 * @return {@code false} if the close operation was cancelled, {@code true}
	 *         otherwise
	 */
	boolean close();

	/**
	 * Returns this Editor's {@code StatusBar}, a JPanel that can display a number
	 * of different messages when appropriate.
	 *
	 * @return the StatusBar
	 *
	 * @see StatusBar
	 */
	StatusBar getStatusBar();

	/**
	 * Returns this Editor's {@code FileInfo}, an object containing information
	 * about the File that the Editor is currently editing.
	 *
	 * @return the FileInfo object
	 *
	 * @see FileInfo
	 */
	FileInfo getFileInfo();
}