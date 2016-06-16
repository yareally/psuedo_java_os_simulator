package filesystem.search;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.Group;
import filesystem.access.User;

/**
 *
 */
public class ExactMatchSearch implements SearchStrategy
{

    private File      file;
    private Directory dir;
    private String    stringToMatch;
    private User      user;
    private Group     group;

    /**
     * create a search to match a file exactly
     *
     * @param file - file to match
     */
    public ExactMatchSearch(File file)
    {
        this.file = file;
    }

    /**
     * create a search to match a directory exactly
     *
     * @param dir - dir to match
     */
    public ExactMatchSearch(Directory dir)
    {
        this.dir = dir;
    }

    /**
     * create a search to match a String exactly
     *
     * @param stringToMatch - string to match
     */
    public ExactMatchSearch(String stringToMatch)
    {
        this.stringToMatch = stringToMatch;
    }

    /**
     * Returns true if the given file matches the search criteria.
     *
     * @param file the file
     * @return true if it's a match
     */
    @Override
    public boolean matches(File file)
    {
        return file != null && this.file != null && this.file.equals(file);
    }

    /**
     * Returns true if the given directory matches the search criteria.
     *
     * @param dir the directory
     * @return true if it's a match
     */
    @Override
    public boolean matches(Directory dir)
    {
        return dir != null && this.dir != null && this.dir.equals(dir);
    }

    /**
     * Returns true if the given file matches the search criteria.
     *
     * @param element the file or directory (or wildcard *)
     * @return true if it's a match
     */
    @Override
    public boolean matches(String element)
    {
        return false;
    }

    /**
     * Returns true if the given string matches the serach criteria
     *
     * @param stringToMatch - string to match
     * @return true if it's a match

     @Override public boolean matches(String stringToMatch)
     {
     return stringToMatch != null && this.stringToMatch.equals(stringToMatch);
     }     */


    /**
     * create a search to match a User exactly
     *
     * @param user - the user

    public ExactMatchSearch(User user)
    {
    this.user = user;
    }  */

    /**
     * create a search to match a Group exactly
     *
     * @param group - the group

    public ExactMatchSearch(Group group)
    {
    this.group = group;
    } */

    /**
     * returns true if the given user matches the search criteria
     *
     * @param user - the user
     * @return true if it's a match

     @Override public boolean matches(User user)
     {
     return user != null && this.user.getName().equals(user.getName());
     }
     */

    /**
     * returns true if the given group matches the search criteria
     *
     * @param group - the group
     * @return true if it's a match

     @Override public boolean matches(Group group)
     {
     return group != null && this.group.getName().equals(group.getName());
     }

     */
}
