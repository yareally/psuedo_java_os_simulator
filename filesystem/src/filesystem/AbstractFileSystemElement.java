package filesystem;

import filesystem.access.AbstractGroupUser;
import filesystem.access.Group;
import filesystem.access.Permissions;
import filesystem.access.User;

/**
 * holds the shared properties of files and directories *
 */
public abstract class AbstractFileSystemElement implements Visitable
{
    private static final int                       USER_PERMS     = 0;
    private static final int                       GROUP_PERMS    = 1;
    private static final int                       EVERYONE_PERMS = 2;
    protected            AbstractFileSystemElement parent         = null;
    protected Group         group;
    protected User          user;
    private   String        name;
    private   Permissions[] perms;

    /**
     * creates file or directory
     *
     * @param name of file or directory
     */
    protected AbstractFileSystemElement(String name, User user, Group group)
    {
        this.name = name;
        this.group = group;
        this.user = user;
        perms = new Permissions[3];
        for (int i = 0; i < perms.length; ++i) {
            perms[i] = new Permissions("0");
        }
    }

    /**
     * creates file or directory
     *
     * @param name of file or directory
     */
    protected AbstractFileSystemElement(String name, User user, Group group, Permissions... perms)
    {
        this(name, user, group);
        System.arraycopy(perms, 0, this.perms, 0, perms.length);
    }

    /**
     * creates file or directory
     *
     * @param name of file or directory
     */
    protected AbstractFileSystemElement(String name, AbstractGroupUser user)
    {
        this(name, (User) user, (Group) user);
    }

    /**
     * creates file or directory
     *
     * @param name of file or directory
     */
    protected AbstractFileSystemElement(String name, AbstractGroupUser user, Permissions... perms)
    {
        this(name, (User) user, (Group) user, perms);
    }

    /**
     * creates file or directory
     *
     * @param name of file or directory
     */
    protected AbstractFileSystemElement(String name)
    {
        this(name, new User("None"), new Group("None"));
    }

    /**
     * creates file or directory
     *
     * @param name of file or directory
     */
    protected AbstractFileSystemElement(String name, Permissions... perms)
    {
        this(name, new User("None"), new Group("None"), perms);
    }

    /**
     * get the group for the file or directory
     *
     * @return the group of the file or directory
     */
    public Group getGroup()
    {
        return this.group;
    }

    /**
     * set the group for the file or directory
     *
     * @param group - a group that has users in it
     */
    public void setGroup(Group group)
    {
        this.group = group;
    }

    /**
     * gets the user of the file or directory
     *
     * @return the user of the file or directory
     */
    public User getUser()
    {
        return this.user;
    }

    /**
     * sets the user of the file or directory
     *
     * @param user - a user
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    public AbstractFileSystemElement getParent()
    {
        return this.parent;
    }

    /**
     * sets the parent to the current child. if the current element doesnt have
     * a parent then it is the root (null)
     *
     * @param parent - the parent of the child being set
     */
    public void setParent(AbstractFileSystemElement parent)
    {
        this.parent = parent;
    }

    /**
     * gets the name of the file or directory
     *
     * @return the name of file name or directory
     */
    public String getName()
    {
        return name;
    }

    /**
     * gets the perm of user
     *
     * @return the perm of user
     */
    public Permissions getUserPerms()
    {
        return perms[USER_PERMS];
    }

    /**
     * gets the perm of group
     *
     * @return the perm of group
     */
    public Permissions getGroupPerms()
    {
        return perms[GROUP_PERMS];
    }

    /**
     * get everyone perm
     *
     * @return the perm of everyone
     */
    public Permissions getEveryonePerms()
    {
        return perms[EVERYONE_PERMS];
    }

    /**
     * format for displaying information
     *
     * @return
     */
    @Override
    public String toString()
    {
        return perms[USER_PERMS].toString()
            + perms[GROUP_PERMS].toString()
            + perms[EVERYONE_PERMS].toString() + " "
            + user.toString() + "\t"
            + group.toString() + " "
            + name;
    }

    /**
     * Checks to see if this is a file or a directory.
     * A directory may return false if it's empty though.
     *
     * @return true if has children.
     */
    public abstract boolean hasChildren();
}
