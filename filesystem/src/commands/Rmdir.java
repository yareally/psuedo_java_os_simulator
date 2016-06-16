package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.Permissions;
import filesystem.access.User;

import java.util.HashMap;
import java.util.Map;

/**
 * deletes directories *
 *
 * ex: *
 * rm d1 *
 * rm d1,d2 *
 * rm *
 */
public class Rmdir extends BaseCommand
{
    private final Map<String, File> filesToRemove;
    private boolean removed = false;

    public Rmdir(String[] args, User currentUser, String... fsElements)
    {

        super(currentUser);

        errorMessage += "cannot delete file `%s': %s";
        filesToRemove = new HashMap<String, File>(fsElements.length * 2);
        Permissions[] perms = {
            currentUser.getCurrentDirectory().getUserPerms(),
            currentUser.getCurrentDirectory().getGroupPerms(),
            currentUser.getCurrentDirectory().getEveryonePerms()};


        for (String fsElement : fsElements) {
            filesToRemove.put(fsElement, new File(fsElement, currentUser, perms));
        }

        fsElements[0] = fsElements[0] == null || fsElements[0].isEmpty() ? "*" : fsElements[0];
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
