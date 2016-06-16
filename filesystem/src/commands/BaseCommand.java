package commands;

import filesystem.AbstractFileSystemElement;
import filesystem.Directory;
import filesystem.File;
import filesystem.access.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseCommand implements Visitor
{
    protected static final String UNKNOWN_FS_ELEMENT = "No such file or directory";
    protected String       errorMessage;
    protected List<String> allErrorMessages;
    protected boolean done = false;
    protected User currentUser;

    protected BaseCommand(User user)
    {
        this.errorMessage = getName() + ": ";
        this.allErrorMessages = new ArrayList<String>();
        this.currentUser = user;
    }

    /**
     * gets the name of the class, thus the name of the command
     *
     * @return the name of the class (command)
     */
    @Override
    public final String getName()
    {
        return getClass().getSimpleName().toLowerCase();
    }

    private boolean hasDefaultPerms(AbstractFileSystemElement fileOrDir)
    {
        return currentUser.isRootUser()
            || currentUser.canRead(fileOrDir)
            && currentUser.canWrite(fileOrDir)
            && currentUser.canExecute(fileOrDir);
    }

    protected boolean hasPerms(File file)
    {
        return hasDefaultPerms(file);
    }

    protected boolean hasPerms(Directory dir)
    {
        return hasDefaultPerms(dir);
    }

    /**
     * prints out all of the error messages that were collected
     *
     * @param directory - the directory
     */
    @Override
    public boolean endVisit(Directory directory)
    {
/*        for(int i = 0; i < allErrorMessages.size(); ++i){
            System.out.println(allErrorMessages);
        } */
        for (String error : allErrorMessages) {
            System.out.println(error);
        }
        return true;
    }

    /**
     * Returns the error message resulting from an
     * invalid command.
     *
     * @return the error message or empty string if none.
     */
    @Override
    public List<String> getErrorMsgs()
    {
        return Collections.unmodifiableList(this.allErrorMessages);
    }
}
