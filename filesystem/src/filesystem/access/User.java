package filesystem.access;

import filesystem.AbstractFileSystemElement;
import filesystem.Directory;

/**
 *
 */
public class User extends AbstractGroupUser
{
    private Directory homeDir;
    private boolean   rootUser;
    private Directory currentDirectory;

    /**
     * takes a string
     *
     * @param userName - name of user
     */
    public User(String userName)
    {
        super(userName);
        homeDir = new Directory(userName, this, new Permissions("7"), new Permissions("5"), new Permissions("0"));
        currentDirectory = homeDir;
        rootUser = false;
    }

    /**
     * takes a string and boolean
     *
     * @param userName - name of user
     * @param rootUser - if root user or not
     */
    public User(String userName, boolean rootUser)
    {
        this(userName);
        this.rootUser = rootUser;
    }

    /**
     * get the users home directory
     *
     * @return - users home directory
     */
    public Directory getHomeDir()
    {
        return homeDir;
    }

    /**
     * get the users current directory
     *
     * @return - users current directory
     */
    public Directory getCurrentDirectory()
    {
        return currentDirectory;
    }

    public void setCurrentDirectory(Directory directory)
    {
        currentDirectory = directory;
    }

    /**
     * determine if user is root user
     *
     * @return - true if is root user
     */
    public boolean isRootUser()
    {
        return rootUser;
    }

    public boolean isOwner(AbstractFileSystemElement element)
    {
        return element.getUser().equals(this);
    }

    public boolean inGroup(AbstractFileSystemElement element)
    {
        return element.getGroup().userExist(this);
    }

    /**
     * determines whether a user, group or everyone else can write file/directory
     * element
     *
     * @param element - file/directory
     * @return true if user, group or everyone can write, otherwise false
     */
    public boolean canWrite(AbstractFileSystemElement element)
    {
        if (isOwner(element) && element.getUserPerms().canWrite()) {
            return true;
        }

        if (inGroup(element) && element.getGroupPerms().canWrite()) {
            return true;
        }

        if (element.getEveryonePerms().canWrite()) {
            return true;
        }
        return false;
    }

    /**
     * determines whether a user, group or everyone else can read file/directory
     * element
     *
     * @param element - file/directory
     * @return true if user, group or everyone can read, otherwise false
     */
    public boolean canRead(AbstractFileSystemElement element)
    {
        if (isOwner(element) && element.getUserPerms().canRead()) {
            return true;
        }

        if (inGroup(element) && element.getGroupPerms().canRead()) {
            return true;
        }

        if (element.getEveryonePerms().canRead()) {
            return true;
        }
        return false;
    }

    /**
     * determines whether a user, group or everyone else can execute file/directory
     * element
     *
     * @param element - file/directory
     * @return true if user, group or everyone can execute, otherwise false
     */
    public boolean canExecute(AbstractFileSystemElement element)
    {
        if (isOwner(element) && element.getUserPerms().canExecute()) {
            return true;
        }

        if (inGroup(element) && element.getGroupPerms().canExecute()) {
            return true;
        }

        if (element.getEveryonePerms().canExecute()) {
            return true;
        }
        return false;
    }
}
