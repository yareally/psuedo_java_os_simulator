package commands;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.Permissions;
import filesystem.access.User;

/**
 * simply creates a file *
 *
 * create -> touch *
 */
public class Create extends BaseCommand
{

    private File[] filesToCreate;

    public Create(String[] args, User currentUser, String... fsElements)
    {
        super(currentUser);
        errorMessage += "cannot create file `%s': %s";
        filesToCreate = new File[fsElements.length];
        Permissions[] perms = {
            currentUser.getCurrentDirectory().getUserPerms(),
            currentUser.getCurrentDirectory().getGroupPerms(),
            currentUser.getCurrentDirectory().getEveryonePerms()};


        for (int i = 0; i < filesToCreate.length; ++i) {
            filesToCreate[i] = new File(fsElements[i], currentUser, perms);
        }
    }

    /**
     * Called when Directory is first visited.
     *
     * @param dir the Directory
     */
    @Override
    public boolean startVisit(Directory dir)
    {
        if (!hasPerms(dir) && dir.equals(currentUser.getCurrentDirectory())) {
            String error = String.format(errorMessage, dir.getName(), "permission denied");
            allErrorMessages.add(error);
            done = true;
        }

        for (File file : filesToCreate) {
            dir.addChild(file);
        }
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
        super.endVisit(dir);
        done = true;
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
