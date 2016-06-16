package filesystem;

import commands.Visitor;
import filesystem.access.AbstractGroupUser;
import filesystem.access.Group;
import filesystem.access.Permissions;
import filesystem.access.User;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * creates a directory that holds files and other directories *
 */
public class Directory extends AbstractFileSystemElement
{
    // TODO: probably change me back to a listtttttttttttttttt >:O
    private Map<String, AbstractFileSystemElement> children;

    /**
     * creates a directory
     *
     * @param name - name of directory
     */
    public Directory(String name, User user, Group group)
    {
        super(name, user, group);
        this.children = new LinkedHashMap<String, AbstractFileSystemElement>(0);
    }

    public Directory(String name, User user, Group group, Permissions... perms)
    {
        super(name, user, group, perms);
        this.children = new LinkedHashMap<String, AbstractFileSystemElement>(0);
    }

    /**
     * creates a directory
     *
     * @param name - name of directory
     * @param user - the user
     */
    public Directory(String name, AbstractGroupUser user)
    {
        super(name, (User) user, new Group(user.getName()));
        this.children = new LinkedHashMap<String, AbstractFileSystemElement>(0);
    }

    public Directory(String name, AbstractGroupUser user, Permissions... perms)
    {
        super(name, (User) user, new Group(user.getName()), perms);
        this.children = new LinkedHashMap<String, AbstractFileSystemElement>(0);
    }

    /**
     * creates a directory
     *
     * @param name - name of directory
     */
    public Directory(String name)
    {
        super(name);
        this.children = new LinkedHashMap<String, AbstractFileSystemElement>(0);
    }

    /**
     * creates a directory
     *
     * @param name - name of directory
     */
    public Directory(String name, Permissions... perms)
    {
        super(name, perms);
        this.children = new LinkedHashMap<String, AbstractFileSystemElement>(0);
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
        return !children.isEmpty();
    }

    /**
     * adds a file or directory within the current directory
     *
     * @param child file or directory to add
     * @return true if created, false if a file/directory of this name exists
     */
    public boolean addChild(AbstractFileSystemElement child)
    {
        // currently we are in a directory and want to add a file or
        // directory as a child to this directory

        if (!children.containsKey(child.getName())) {
            child.setParent(this);
            children.put(child.getName(), child);
            return true;
        }
        return false;
    }

    /**
     * adds a file or directory within the current directory
     *
     * @param child file or directory to add
     * @return true if created, false if a file/directory of this name exists
     */
    public boolean removeChild(AbstractFileSystemElement child)
    {
        // currently we are in a directory and want to add a file or
        // directory as a child to this directory

        if (children.containsKey(child.getName())) {
            children.remove(child.getName());
            return true;
        }
        return false;
    }

    /**
     * executes the command (visitor) on current directory and its children
     *
     * @param visitor the command object to visit this object.
     */
    @Override
    public boolean accept(Visitor visitor)
    {
        if (visitor.isDone()) {
            return visitor.getErrorMsgs().isEmpty();
        }

        visitor.startVisit(this);
        acceptAll(visitor, false);
        visitor.endVisit(this);
        return visitor.getErrorMsgs().isEmpty();
    }

    /**
     * Accepts the visitor to process this object. Also
     * takes a value to match in order to find the element to accept
     * without looping to find it.
     *
     * @param visitor the visitor object to visit this object.
     * @param matchValue value to match for the visitable
     */
    @Override
    public boolean accept(Visitor visitor, String matchValue)
    {
        AbstractFileSystemElement child = children.get(matchValue);

        if (child != null) {
            visitor.startVisit(this);
            child.accept(visitor);
            visitor.endVisit(this);
        }
/*        else if ("*".equals(matchValue)) {
            visitor.startVisit(this);
            acceptAll(visitor, true);
            visitor.endVisit(this);
        }*/
        else { // if we didn't have a match then it's not in the current dir and we must run accept as normal
            accept(visitor);
        }
        return visitor.getErrorMsgs().isEmpty();
    }

    /**
     * checking if the current directory (this) is the root
     *
     * @return true if parent is null
     */
    public boolean isRoot()
    {
        return this.parent == null;
    }

    /**
     * Accepts all child objects for the directory and passes them to the visitor
     * so it can execute commands on them.
     *
     * @param visitor - the command to visit the object and execute commands on it
     * @param currentDirOnly - should we only visit objects in the currect directory?
     * @return true when done.
     */
    private boolean acceptAll(Visitor visitor, boolean currentDirOnly)
    {


        for (Entry<String, AbstractFileSystemElement> child : children.entrySet()) {
            // if (!currentDirOnly || !child.getClass().equals(this.getClass())) {
            child.getValue().accept(visitor);
            //  }

            if (visitor.isDone()) {
                return true;
            }
        }
        return false;
    }

    /**
     * overrides the parent toString to add a d for directory as well as whatever
     * the parent class has
     *
     * @return
     */
    @Override
    public String toString()
    {
        return "d" + super.toString();
    }

    public Map<String, AbstractFileSystemElement> getChildren()
    {
        return Collections.unmodifiableMap(children);
    }

    public AbstractFileSystemElement getChild(String childName)
    {
        return children.get(childName);
    }
}
