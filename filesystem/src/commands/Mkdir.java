package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.Permissions;
import filesystem.access.User;
import filesystem.search.PartialMatchSearch;
import filesystem.search.SearchStrategy;

/**
 * creates a directory in the filesystem *
 *
 * ex: *
 * mkdir d1 *
 * mkdir d1,d2 *
 */
public class Mkdir extends BaseCommand
{
    private Directory[]    dirToCreate;
    private SearchStrategy search;

    public Mkdir(String[] args, User currentUser, String... fsElements)
    {
        super(currentUser);
        errorMessage += "cannot create directory `%s': %s";
        dirToCreate = new Directory[fsElements.length];
        Permissions[] perms = {
            currentUser.getCurrentDirectory().getUserPerms(),
            currentUser.getCurrentDirectory().getGroupPerms(),
            currentUser.getCurrentDirectory().getEveryonePerms()};
        for (int i = 0; i < dirToCreate.length; ++i) {
            dirToCreate[i] = new Directory(fsElements[i], currentUser, perms);
        }
        search = new PartialMatchSearch(currentUser.getCurrentDirectory());
    }

    /**
     * Called when Directory is first visited.
     *
     * @param parentDir the Directory you are adding command to
     * @return true if all created. false if there are errors.
     */
    @Override
    public boolean startVisit(Directory parentDir)
    {
        if (!hasPerms(parentDir) && search.matches(parentDir)) {
            String error = String.format(errorMessage, parentDir.getName(), "permission denied");
            allErrorMessages.add(error);
        }
        else {
        /* TODO: write in notes
        for (int i = 0; i < dirToCreate.length; ++i) {
            Directory childDir = dirToCreate[i];
        }
        */
            boolean result = search.matches(parentDir);

            if (result) {
                // for each childDir in dirToCreate
                for (Directory childDir : dirToCreate) {
                    if (!parentDir.addChild(childDir)) {
                        String error = String.format(errorMessage, childDir.getName(), "File exists");
                        allErrorMessages.add(error);
                    }
                }
            }
        }
        return allErrorMessages.size() <= 0;
    }

    @Override
    protected boolean hasPerms(Directory dir)
    {
        return super.hasPerms(dir) || currentUser.canWrite(dir);
    }

    /**
     * Called when a Directory's children have all been visited.
     *
     * @param dir the Directory
     */
    @Override
    public boolean endVisit(Directory dir)
    {

        if (search.matches(dir)) {
            super.endVisit(dir);
            done = true;
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
        //do nothing, we are not working with files in mkdir
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
