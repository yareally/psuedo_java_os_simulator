package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.User;

/**
 * used with exiting the pgm *
 */
public class Exit extends BaseCommand
{
    /**
     * displays the exiting message to user
     *
     * @param args
     * @param fsElements
     */
    public Exit(String[] args, User currentUser, String... fsElements)
    {
        super(currentUser);
        System.out.println("exiting...");
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
        return true;
    }
}
