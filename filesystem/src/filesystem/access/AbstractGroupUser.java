package filesystem.access;

/**
 * Base class that holds related content for *
 * groups and users. *
 */
public class AbstractGroupUser
{
    private String name;

    /**
     * creates an object that holds the related content for groups and users
     *
     * @param name
     */
    public AbstractGroupUser(String name)
    {
        this.name = name;
    }

    /**
     * gets the name of the object (user/group)
     *
     * @return name of user or group
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return the name of the user or group in a string
     */
    @Override
    public String toString()
    {
        return this.name;
    }
}
