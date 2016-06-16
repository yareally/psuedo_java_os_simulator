package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.User;

/**
 * message to the user "f1 is being read..." since *
 * this is simulated *
 *
 * read -> cat *
 */
public class Read extends BaseCommand
{
    public Read(String[] args, User currentUser, String... fsElements)
    {

        super(currentUser);
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
