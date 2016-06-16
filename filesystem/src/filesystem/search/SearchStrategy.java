package filesystem.search;

import filesystem.Directory;
import filesystem.File;

/**
 *
 */
public interface SearchStrategy
{
    /**
     * Returns true if the given file matches the search criteria.
     *
     * @param file the file
     * @return true if it's a match
     */
    boolean matches(File file);

    /**
     * Returns true if the given directory matches the search criteria.
     *
     * @param dir the directory
     * @return true if it's a match
     */
    boolean matches(Directory dir);

    /**
     * Returns true if the given file matches the search criteria.
     *
     * @param element the file or directory (or wildcard *)
     * @return true if it's a match
     */
    public boolean matches(String element);

    /**
     * Returns true if the given string matches the serach criteria
     *
     * @param stringToMatch - string to match the search on
     * @return true if its a match

    boolean matches(String stringToMatch);
     */

    /**
     * returns true if the given user matches the search criteria
     *
     * @param user - the user
     * @return true if its a match

    boolean matches(User user);
     */
    /**
     * returns true if the given group matches the search criteria
     *
     * @param group - the group
     * @return true if its a match

    boolean matches(Group group);
     */
}
