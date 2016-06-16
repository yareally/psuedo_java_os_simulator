package filesystem.search;

import commands.Visitor;
import filesystem.AbstractFileSystemElement;
import filesystem.Directory;
import filesystem.File;

import java.util.ArrayList;
import java.util.List;

/**
 * adds matches to list of search matches depending on whereabouts of the file *
 * system *
 *
 * serach visitor is going to look for matches in the file system that match whatever you *
 * define for matching (partial file name, full file name) *
 */
public class SearchVisitor implements Visitor
{
    private boolean done = false;
    private List<AbstractFileSystemElement> searchMatches;
    private SearchStrategy                  strategy;
    private Directory startDir = null;

    /**
     * it creates a search visitor
     *
     * @param strategy type of matching the (command) visitor should do
     */
    public SearchVisitor(SearchStrategy strategy)
    {
        this.strategy = strategy;
        this.searchMatches = new ArrayList<AbstractFileSystemElement>(0);
    }

    /**
     * it creates a search visitor that searches starting in a
     * defined directory instead of the root.
     *
     * @param strategy type of matching the (command) visitor should do
     * @param startDir where should the search start (instead of the root)
     */
    public SearchVisitor(SearchStrategy strategy, Directory startDir)
    {
        this.strategy = strategy;
        this.searchMatches = new ArrayList<AbstractFileSystemElement>(0);
        this.startDir = startDir;
    }

    /**
     * see if current directory matches what you are looking for
     * and collect - not aware of any files, only dir itself
     *
     * @param dir the current Directory
     */
    @Override
    public boolean startVisit(Directory dir)
    {
        if (!isTopDir(dir) && strategy.matches(dir)) {
            searchMatches.add(dir);
            return true;
        }
        return false;
    }

    private boolean isTopDir(Directory dir)
    {
        return dir.isRoot() || startDir != null && startDir.equals(dir);
    }

    /**
     * do whatever you need to do after you are done
     * steps out of current directory
     * already searched evrything in the directory what do you do next
     * also need to account for if its done
     * notify the user if it is done searching
     *
     * @param dir the Directory
     */
    @Override
    public boolean endVisit(Directory dir)
    {
        if (dir.isRoot()) {
            this.done = true;
            return true;
        }
        return false;
    }

    /**
     * visiting each file add them to the collection of matches
     *
     * @param file the current file to search for
     */
    @Override
    public boolean visit(File file)
    {
        if (strategy.matches(file)) {
            searchMatches.add(file);
            return true;
        }
        return false;
    }

    /**
     * reports whether or not you are done running the command or not
     * keep a boolean to tell if done or not
     *
     * @return true if the search is done
     */
    @Override
    public boolean isDone()
    {
        return this.done;
    }

    /**
     * gets the name of the command
     *
     * @return returns the name of the command
     */
    @Override
    public String getName()
    {
        return "";
    }

    /**
     * @return
     */
    @Override
    public List<String> getErrorMsgs()
    {
        return new ArrayList<String>(0);
    }

    /**
     * get the matches and return the files/directories that matched back
     *
     * @return list of files and directories that matched during the search
     */
    public List<AbstractFileSystemElement> getMatches()
    {
        return this.searchMatches;
    }
}
