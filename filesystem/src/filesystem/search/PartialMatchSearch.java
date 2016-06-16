package filesystem.search;

import filesystem.AbstractFileSystemElement;
import filesystem.Directory;
import filesystem.File;
import filesystem.access.Group;
import filesystem.access.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class PartialMatchSearch implements SearchStrategy
{
    private File      file = null;
    private Directory dir  = null;
    private String  stringToMatch;
    private User    user;
    private Group   group;
    private Matcher matcher;
    private Pattern searchPattern;

    private PartialMatchSearch(AbstractFileSystemElement elem)
    {
        this(elem.getName());
    }

    public PartialMatchSearch(String elem)
    {
        elem = elem.replace("*", "\\S*");
        searchPattern = Pattern.compile("^" + elem + "\\b");
    }

    /**
     * create a search to match a file exactly
     *
     * @param file - file to match
     */
    public PartialMatchSearch(File file)
    {
        this((AbstractFileSystemElement) file);
        this.file = file;
    }

    /**
     * create a search to match a directory exactly
     *
     * @param dir - dir to match
     */
    public PartialMatchSearch(Directory dir)
    {
        this((AbstractFileSystemElement) dir);
        this.dir = dir;
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
        if (element != null) {
            matcher = searchPattern.matcher(element);
        }

        return element != null && matcher.find();
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
        if (file != null) {
            matcher = searchPattern.matcher(file.getName());
        }

        return file != null && matcher.find();
        //return file != null && this.file.getName().matches(file.getName());
        //return file != null && this.file.getName().contains(file.getName());
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
        if (dir != null) {
            matcher = searchPattern.matcher(dir.getName());
        }
        boolean result = matcher.find();
        boolean finalResult = dir != null && result;
        return finalResult;
        //return dir != null && this.dir.getName().matches(dir.getName());
        //return dir != null && this.dir.getName().contains(dir.getName());
    }
}
