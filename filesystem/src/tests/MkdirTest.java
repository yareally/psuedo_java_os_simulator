package tests;

import commands.Mkdir;
import filesystem.Directory;
import filesystem.File;
import filesystem.access.Group;
import filesystem.access.Permissions;
import filesystem.access.User;
import filesystem.search.PartialMatchSearch;
import filesystem.search.SearchVisitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 *
 */
public class MkdirTest
{
    private Directory root;
    private Directory childDir1;
    private Directory childDir2;
    private Directory childDir3;
    private Directory user1HomeDir;

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

    private SearchVisitor      searchVisitor;
    private Mkdir              mkdir;
    private PartialMatchSearch testSearch;

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
        Permissions[] perms = {
            user1.getCurrentDirectory().getUserPerms(),
            user1.getCurrentDirectory().getGroupPerms(),
            user1.getCurrentDirectory().getEveryonePerms()};
        testSearch = new PartialMatchSearch("test");


        mkdir = new Mkdir(new String[]{""}, user1, "test");
        user1HomeDir = user1.getHomeDir();
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testMkDir()
    {
        assertTrue("mkdir returned errors when running", user1HomeDir.accept(mkdir));
        assertTrue(user1HomeDir.hasChildren());
        searchVisitor = new SearchVisitor(testSearch);
        user1HomeDir.accept(searchVisitor);
        assertEquals("1 child should be created", "test", user1HomeDir.getChildren().get("test").getName());
        assertEquals("1 child should be created", 1, searchVisitor.getMatches().size());
    }
}
