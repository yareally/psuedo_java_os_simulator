package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.User;

/**
 * check whether or not the file is there to be read *
 *
 * message to the user saying "writing to f1...." *
 * even though there is nothing there since this is *
 * simulated *
 *
 * write -> echo "test ...." >> test/1.txt *
 */
public class Write extends BaseCommand
{
    public Write(String[] args, User currentUser, String... fsElements)
    {
        super(currentUser);
        System.out.println("");
    }

    /**
     * Called when Directory is first visited.
     *
     * @param dir the Directory
     */
    @Override
    public boolean startVisit(Directory dir)
    {
        return true;
    }

    /**
     * Called when a Directory's children have all been visited.
     *
     * @param dir the Directory
     */
    @Override
    public boolean endVisit(Directory dir)
    {
        return true;
    }

    /**
     * Called to visit a File.
     *
     * @param file the File
     */
    @Override
    public boolean visit(File file)
    {
        return true;
    }

    /**
     * Returns true if the visitor is done
     * processing its elements.
     *
     * @return true when visitation should stop.
     */
    @Override
    public boolean isDone()
    {
        return false;
    }
}
