package filesystem.search;

import filesystem.Directory;
import filesystem.File;

/**
 * matches everything in a directory etc.. wildcard *
 */
public class MatchAll implements SearchStrategy
{
    /**
     * Returns true if the given file matches the search criteria.
     *
     * @param file the file
     * @return true if it's a match
     */
    @Override
    public boolean matches(File file)
    {
        return false;
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
        return false;
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
     * @param stringToMatch - string to match the search on
     * @return true if its a match

     @Override public boolean matches(String stringToMatch)
     {
     return false;
     }
     */

    /**
     * returns true if the given user matches the search criteria
     *
     * @param user - the user
     * @return true if its a match

     @Override public boolean matches(User user)
     {
     return false;
     }  */

    /**
     * returns true if the given group matches the search criteria
     *
     * @param group - the group
     * @return true if its a match

     @Override public boolean matches(Group group)
     {
     return false;
     }*/
}
