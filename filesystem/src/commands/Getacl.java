package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.User;

/**
 * used to display the access control list on a file *
 * or directory *
 *
 * getacl file 1 should return: *
 * #object: file1 *
 * #owner: Lisa *
 * #group: students *
 * user::rwx *
 * user:roshandak rw *
 * user:tom x *
 * group::rwx *
 * group:teachers rx *
 * ... *
 *
 * getacl -> ls -l *
 */
public class Getacl extends BaseCommand
{
    public Getacl(String[] args, User currentUser, String... fsElements)
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
