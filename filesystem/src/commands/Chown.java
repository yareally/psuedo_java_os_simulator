package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.User;

/**
 * used to change the ownership of a file *
 *
 * ex: *
 * if Lisa owns file1 and she or the super user wants to change *
 * ownership to tim... *
 *
 * chown tim file1 *
 *
 * lisa will continue to have the permissions and access rights *
 * she already had on the file but Tim is the new owner and has *
 * RWX perm as a result of the command *
 */
public class Chown extends BaseCommand
{

    public Chown(String[] args, User currentUser, String... fsElements)
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
