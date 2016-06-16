package commands;

import filesystem.Directory;
import filesystem.File;

import java.util.List;

public interface Visitor
{
    /**
     * Called when Directory is first visited.
     *
     * @param dir the Directory
     */
    boolean startVisit(Directory dir);

    /**
     * Called when a Directory's children have all been visited.
     *
     * @param dir the Directory
     */
    boolean endVisit(Directory dir);

    /**
     * Called to visit a File.
     *
     * @param file the File
     */
    boolean visit(File file);

    /**
     * Returns true if the visitor is done
     * processing its elements.
     *
     * @return true when visitation should stop.
     */
    boolean isDone();

    /**
     * gets the name of the command
     *
     * @return returns the name of the command
     */
    String getName();

    /**
     * Returns the error message resulting from an
     * invalid command.
     *
     * @return the error message or empty string if none.
     */
    List<String> getErrorMsgs();
}

