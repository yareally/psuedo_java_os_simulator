package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.User;

/**
 * applied on a per-user basis. Requires retaining of state *
 * for each user so you know what directory they are in. *
 * if a directory they try to access doesnt exist just display *
 * an error message. Do the same if permissions are not sufficiant *
 */
public class Cd extends BaseCommand
{

    public Cd(String[] args, User currentUser, String... fsElements)
    {
        super(currentUser);
        errorMessage += "%s: %s";

        changeDir(fsElements);
        done = true;
        endVisit(currentUser.getCurrentDirectory());
    }

    private boolean changeDir(String... fsElements)
    {
        Directory newCurrentDir = "..".equals(fsElements[0]) || "../".equals(fsElements[0])
            ? (Directory) currentUser.getCurrentDirectory().getParent()
            : (Directory) currentUser.getCurrentDirectory().getChildren().get(fsElements[0]);

        if (newCurrentDir != null) {
            if (hasPerms(newCurrentDir)) {
                currentUser.setCurrentDirectory(newCurrentDir);
                return true;
            }
            else {
                allErrorMessages.add(String.format(errorMessage, newCurrentDir.getName(), "Permission denied"));
            }
        }
        else {
            allErrorMessages.add(String.format(errorMessage, fsElements[0], "No such file or directory"));
        }
        return false;
    }

    @Override
    protected boolean hasPerms(Directory newDirectory)
    {
        return super.hasPerms(newDirectory) || currentUser.canExecute(newDirectory);
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
        super.endVisit(dir);
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
        return done;
    }
}
