package tests;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.Group;
import filesystem.access.User;
import filesystem.search.ExactMatchSearch;
import filesystem.search.SearchVisitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 *
 */
public class ExactMatchSearchTest
{
    private Directory root;
    private Directory childDir1;
    private Directory childDir2;

    private File file1;
    private File file2;
    private File file3;
    private File file4;

    private User user1;
    private User user2;

    private Group group1;
    private Group group2;

    private ExactMatchSearch file1Search;
    private ExactMatchSearch file2Search;
    private ExactMatchSearch file3Search;

    private ExactMatchSearch rootSearch;
    private ExactMatchSearch child1Search;
    private ExactMatchSearch child2Search;

    private SearchVisitor searchVisitor;

    @Before
    public void setUp()
    {
        user1 = new User("user1");
        user2 = new User("user2");
        group1 = new Group("group1");
        group2 = new Group("group2");

        group1.addUser(user1);
        group2.addUser(user1);
        group2.addUser(user2);

        file1 = new File("file1", user2, group1);
        file2 = new File("file2", user1, group1);
        file3 = new File("file3", user2, group2);
        file4 = new File("file1", user2, group1);

        root = new Directory("root", user1, group1);
        childDir1 = new Directory("childDir1", user2, group2);
        childDir2 = new Directory("childDir2", user1, group2);

        file1Search = new ExactMatchSearch(file1);
        file2Search = new ExactMatchSearch(file2);
        file3Search = new ExactMatchSearch(file3);


        rootSearch = new ExactMatchSearch(childDir1);
        child1Search = new ExactMatchSearch(childDir1);
        child2Search = new ExactMatchSearch(childDir2);
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testMatchRootDirectory()
    {
        searchVisitor = new SearchVisitor(rootSearch);
        root.addChild(childDir2);
        root.addChild(childDir1);

        root.accept(searchVisitor);

        assertEquals("there should be only one match.", 1, searchVisitor.getMatches().size());
    }

    @Test
    public void testMatchChildDirectory()
    {
        searchVisitor = new SearchVisitor(child1Search);
        root.addChild(childDir1);
        root.addChild(childDir2);

        root.accept(searchVisitor);

        assertEquals("there should be only one match.", 1, searchVisitor.getMatches().size());
    }

    @Test
    public void testMatchSubChildDirectory()
    {
        searchVisitor = new SearchVisitor(child2Search);

        childDir1.addChild(childDir2);
        root.addChild(childDir1);


        root.accept(searchVisitor);

        assertEquals("there should be only one match.", 1, searchVisitor.getMatches().size());
    }

    @Test
    public void testMatchFile1()
    {
        searchVisitor = new SearchVisitor(file1Search);
        assertEquals("Users do not match", user2, file1.getUser());
        file1.setUser(user1);
        assertEquals("Users do not match", user1, file1.getUser());
        childDir1.addChild(file1);
        childDir1.addChild(file2);
        childDir1.addChild(file3);
        childDir2.addChild(file4);
        root.addChild(childDir1);
        root.addChild(childDir2);

        root.accept(searchVisitor);

        assertEquals("there should be only one match. total matches: "
            + searchVisitor.getMatches().size(), 1, searchVisitor.getMatches().size());
        assertEquals("files do not match", searchVisitor.getMatches().get(0), file1);
        assertEquals("file owners do not match", searchVisitor.getMatches().get(0).getUser(), file1.getUser());
    }

    @Test
    public void testMatchFile2InSplitDir()
    {
        searchVisitor = new SearchVisitor(file2Search);

        childDir1.addChild(file1);
        childDir2.addChild(file2);
        childDir2.addChild(file3);
        root.addChild(childDir1);
        root.addChild(childDir2);

        root.accept(searchVisitor);

        assertEquals("there should be only one match. total matches: "
            + searchVisitor.getMatches().size(), 1, searchVisitor.getMatches().size());
    }

    @Test
    public void testMatchFile3InSubDir()
    {
        searchVisitor = new SearchVisitor(file3Search);

        childDir1.addChild(file1);
        childDir2.addChild(file2);
        childDir2.addChild(file3);
        childDir2.addChild(childDir1);
        root.addChild(childDir2);

        root.accept(searchVisitor);

        assertEquals("there should be only one match. total matches: "
            + searchVisitor.getMatches().size(), 1, searchVisitor.getMatches().size());
    }
}
