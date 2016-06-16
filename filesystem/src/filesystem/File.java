package filesystem;

import commands.Visitor;
import filesystem.access.AbstractGroupUser;
import filesystem.access.Group;
import filesystem.access.Permissions;
import filesystem.access.User;

/**
 *
 */
public class File extends AbstractFileSystemElement
{

    /**
     * takes a string, user and group
     *
     * @param name - name of the file
     * @param group - group of users that have permissions on file
     * @param user - the user that has permissions on the file
     */
    public File(String name, User user, Group group)
    {
        super(name, user, group);
    }

    /**
     * takes a string and an abstractgroupuser user object
     *
     * @param name - name of the file
     * @param user - the user that has permissions on the file
     */
    public File(String name, AbstractGroupUser user, Permissions... perms)
    {
        super(name, (User) user, new Group(user.getName()), perms);
    }

    /**
     * takes a string and an abstractgroupuser user object
     *
     * @param name - name of the file
     * @param user - the user that has permissions on the file
     */
    public File(String name, AbstractGroupUser user)
    {
        super(name, (User) user, new Group(user.getName()));
    }

    /**
     * constructor that takes a string
     *
     * @param name - name of the file
     */
    public File(String name, Permissions... perms)
    {
        super(name, perms);
    }

    /**
     * constructor that takes a string
     *
     * @param name - name of the file
     */
    public File(String name)
    {
        super(name);
    }

    /**
     * Checks to see if this is a file or a directory.
     * A directory may return false if it's empty though.
     *
     * @return true if has children.
     */
    @Override
    public boolean hasChildren()
    {
        return false; // Never has children.
    }


    /**
     * @return public Date getTimestamp()
     *{
     *TODO - if needed
     *return null;
     * }
     */

    /**
     * takes a visitor (command) and determines if it is done or not, if
     * not then it will visit the object
     *
     * @param visitor - the visitor object to visit this object.
     */
    @Override
    public boolean accept(Visitor visitor)
    {
        if (!visitor.isDone()) {
            visitor.visit(this);
        }
        return visitor.getErrorMsgs().isEmpty();
    }

    @Override
    public boolean accept(Visitor visitor, String matchValue)
    {
        accept(visitor);
        return visitor.getErrorMsgs().isEmpty();
    }

    /**
     * overrides the parent toString to add a - for file as well as whatever
     * the parent class has
     *
     * @return - modified super string to add "-" for a file
     */
    @Override
    public String toString()
    {
        return "-" + super.toString();
    }
}
