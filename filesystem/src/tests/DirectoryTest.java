package tests;

import filesystem.Directory;
import filesystem.File;
import filesystem.access.Group;
import filesystem.access.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 *
 */
public class DirectoryTest
{
    private Directory directory1;
    private Directory directory2;
    private Directory directory3;

    private File file1;
    private File file2;
    private File file3;

    private User user1;
    private User user2;

    private Group group1;
    private Group group2;

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
        file1 = new File("file1", user1, group1);
        directory1 = new Directory("directory1", user1, group1);
        directory2 = new Directory("directory2", user2, group2);
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testHasChildren()
    {

    }

    @Test
    public void testAddChild()
    {

        directory1.addChild(file1);
        assertTrue("child was not added", directory1.hasChildren());
    }

    @Test
    public void testAccept()
    {

    }

    @Test
    public void testIsRoot()
    {
        directory1.addChild(directory2);
        assertTrue("not the root", directory1.isRoot());
        assertFalse("directory2 is a child!", directory2.isRoot());
    }
}
