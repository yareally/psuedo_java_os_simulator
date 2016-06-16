package filesystem.access;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Group extends AbstractGroupUser
{
    private Map<String, User> users;

    /**
     * Creates a Group for the filesystem.
     *
     * @param name - name to give the group
     */
    public Group(String name)
    {
        super(name);
        users = new HashMap<String, User>(0);
    }

    /**
     * adds a user to a group (checking is done in the FileSystem class)
     *
     * @param user - a user
     */
    public void addUser(User user)
    {
        users.put(user.getName(), user);
    }

    /**
     * Checks to see if the user exists in the group
     *
     * @param user - user to verify
     * @return true if it exists
     */
    public boolean userExist(User user)
    {
        return users.containsKey(user.getName());
    }

    /**
     * Checks to see if the user exists in the group
     *
     * @param userName - user to verify
     * @return true if it exists
     */
    public boolean userExist(String userName)
    {
        return users.containsKey(userName);
    }
}
