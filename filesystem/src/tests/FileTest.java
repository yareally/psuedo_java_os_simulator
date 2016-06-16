package tests;

import filesystem.File;
import filesystem.access.Group;
import filesystem.access.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;

/**
 *
 */
public class FileTest
{
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
        file2 = new File("file2", user2, group2);
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testHasChildren()
    {
        assertFalse("Files should not have children!", file1.hasChildren());
        assertFalse("Files should not have children!", file2.hasChildren());
    }

    @Test
    public void testAccept()
    {

    }
}
