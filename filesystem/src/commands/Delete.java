package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.Permissions;
import filesystem.access.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * simply delete a file *
 *
 * delete -> rm *
 */
public class Delete extends BaseCommand
{
    private final Map<String, File> filesToRemove;
    private boolean removed = false;

    public Delete(String[] args, User currentUser, String... fsElements)
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
        return allErrorMessages.size() <= 0;
    }

    @Override
    protected boolean hasPerms(File file)
    {
        return super.hasPerms(file) || currentUser.canWrite(file);
    }

    /**
     * Called when a Directory's children have all been visited.
     *
     * @param dir the Directory
     */
    @Override
    public boolean endVisit(Directory dir)
    {
        for (Entry<String, File> child : filesToRemove.entrySet()) {
            boolean removed = currentUser.getCurrentDirectory().removeChild(child.getValue());

            if (!hasPerms(child.getValue()) && !removed) {
                String error = !hasPerms(child.getValue()) && !removed
                    ? String.format(errorMessage, child.getKey(), "Permission denied")
                    : String.format(errorMessage, child.getKey(), "No such file or directory");
                allErrorMessages.add(error);
            }
        }


        if (!removed) {
            super.endVisit(dir);
        }
        if (!hasPerms(dir) || dir.equals(currentUser.getCurrentDirectory())) {
            done = true;
        }
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
        return false;
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
        return done;
    }
}
