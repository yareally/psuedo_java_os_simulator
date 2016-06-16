package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.User;

/**
 * used to change the access control list on a file or *
 * directory. Note that ACL is a propery of the file or *
 * directory not the user *
 *
 * setacl user.group permString objName *
 *
 * where permString specifies the list of rights to be *
 * added or removed from the objName which is file/directory *
 *
 * ex: *
 * setacl Lisa.Students +rw file 1 *
 * setacl *
 */
public class Setacl extends BaseCommand
{
    public Setacl(String[] args, User currentUser, String... fsElements)
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
