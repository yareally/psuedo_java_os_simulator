package tests;

import filesystem.AbstractFileSystemElement;
import filesystem.Directory;
import filesystem.File;
import filesystem.access.Group;
import filesystem.access.User;
import filesystem.search.PartialMatchSearch;
import filesystem.search.SearchVisitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class PartialMatchSearchTest
{

    private Directory root;
    private Directory childDir1;
    private Directory childDir2;
    private Directory childDir3;

    private File file1;
    private File file2;
    private File file3;
    private File file4;

    private User user1;
    private User user2;

    private Group group1;
    private Group group2;

    private PartialMatchSearch file1Search;
    private PartialMatchSearch file2Search;
    private PartialMatchSearch file3Search;

    private PartialMatchSearch rootSearch;
    private PartialMatchSearch child1Search;
    private PartialMatchSearch child2Search;
    private PartialMatchSearch child3Search;

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

        root = new Directory("r", user1, group1);
        childDir1 = new Directory("Dir1", user2, group2);
        childDir2 = new Directory("childDir2", user1, group2);
        childDir3 = new Directory("childDir3", user1, group1);

        file1Search = new PartialMatchSearch(file1);
        file2Search = new PartialMatchSearch(file2);
        file3Search = new PartialMatchSearch(file3);


        rootSearch = new PartialMatchSearch(root);

        child1Search = new PartialMatchSearch(new Directory("Dir*"));
        child2Search = new PartialMatchSearch(childDir2);
        child3Search = new PartialMatchSearch(new Directory("child*"));
    }

    @After
    public void tearDown()
    {

    }
/*
    @Test
    public void testMatchRootDirectory()
    {
        searchVisitor = new SearchVisitor(rootSearch);
        root.addChild(childDir2);
        root.addChild(childDir1);

        root.accept(searchVisitor);

        assertEquals("there should be only one match.", 1, searchVisitor.getMatches().size());
    }*/

    @Test
    public void testMatchChildDirectory()
    {
        searchVisitor = new SearchVisitor(child1Search);
        root.addChild(childDir1);
        root.addChild(childDir2);
/*        assertFalse("Should not contain the other", "r".contains("Dir1"));*/
        assertTrue("Should not contain the other", "Dir1".contains("r"));
        root.accept(searchVisitor);

        String matches = "";

        for (AbstractFileSystemElement elem : searchVisitor.getMatches()) {
            matches += elem.getName() + '\n';
        }
        assertEquals("Matches returned are not equal. Returned: " + matches, 1, searchVisitor.getMatches().size());
    }

    @Test
    public void testMatchChildOfChildDirectory()
    {
        searchVisitor = new SearchVisitor(child3Search, childDir2);

        childDir2.addChild(childDir3);
        root.addChild(childDir1);
        root.addChild(childDir2);

        root.accept(searchVisitor);

        String matches = "";

        for (AbstractFileSystemElement elem : searchVisitor.getMatches()) {
            matches += elem.getName() + '\n';
        }
        assertEquals("Matches returned are not equal. Returned: " + matches, 1, searchVisitor.getMatches().size());
    }
}
