package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.User;
import filesystem.search.PartialMatchSearch;
import filesystem.search.SearchStrategy;

/**
 * print -> ls *
 */
public class Print extends BaseCommand
{
    private SearchStrategy search;

    public Print(String[] args, User currentUser, String... fsElements)
    {
        super(currentUser);
        errorMessage += "`%s': %s";


        fsElements[0] = fsElements[0] == null || fsElements[0].isEmpty() ? "*" : fsElements[0];
        search = new PartialMatchSearch(fsElements[0]);
    }

    /**
     * Called when Directory is first visited.
     *
     * @param dir the Directory
     */
    @Override
    public boolean startVisit(Directory dir)
    {
        if (hasPerms(dir) && !dir.equals(currentUser.getCurrentDirectory()) && search.matches(dir)) {
            if (dir.getParent().equals(currentUser.getCurrentDirectory())) {
                System.out.println(dir.toString());
            }
            return true;
        }
        String errorType = "";

        if (!hasPerms(dir) && search.matches(dir)) {
            errorType = "Permission denied";

            String error = String.format(errorMessage, dir.getName(), errorType);
            allErrorMessages.add(error);
        }


        return false;
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
        if (search != null && dir.equals(currentUser.getCurrentDirectory())) {
            this.done = true;
            return true;
        }
        return false;
    }

    /**
     * Called to visit a File.
     *
     * @param file the File
     */
    @Override
    public boolean visit(File file)
    {
        if (hasPerms(file) && search.matches(file)) {
            System.out.println(file.toString());
            return true;
        }
        String error = String.format(errorMessage, file.getName(), "No such file or directory");
        allErrorMessages.add(error);
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

    /**
     * need read and execute perms to run print command
     *
     * @param dir
     * @return
     */
    @Override
    protected boolean hasPerms(Directory dir)
    {
        return super.hasPerms(dir) || currentUser.canRead(dir) && currentUser.canExecute(dir);
    }

    /**
     * need read and execute perms to run print command
     *
     * @param file
     * @return
     */
    @Override
    protected boolean hasPerms(File file)
    {
        return true;
    }
}
