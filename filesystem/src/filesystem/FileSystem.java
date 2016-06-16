package filesystem;

import commands.Visitor;
import filesystem.access.Group;
import filesystem.access.Permissions;
import filesystem.access.User;
import filesystem.util.Helpers;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 */
public class FileSystem
{
    private static final Pattern SPACE = Pattern.compile(" ");
    // all filesystems should have the same groups & users
    private static Map<String, Group> groups;
    private static Map<String, User>  users;
    // may have more than one partition, so this accounts for that
    private        Directory          root;

    /**
     * creates a file system for the OS
     */
    private FileSystem()
    {
        root = new Directory("/", new Permissions("7"), new Permissions("5"), new Permissions("5"));
        groups = new HashMap<String, Group>(16);
        users = new HashMap<String, User>(16);
    }

    public FileSystem(Reader userReader, Reader groupReader)
    {
        this();
        init(userReader, groupReader);
    }

    /**
     * creates a file system for the OS
     *
     * @param userFileName - name of the file that holds the users for the OS
     * @param groupFileName - name of the file that holds the groups for the OS
     */
    public FileSystem(String userFileName, String groupFileName)
    {
        this();
        FileReader userReader = null;
        FileReader groupReader = null;

        try {
            userReader = new FileReader(userFileName);
            groupReader = new FileReader(groupFileName);
            init(userReader, groupReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (userReader != null) {
                try {
                    userReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (groupReader != null) {
                try {
                    groupReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * returns true if the command executed
     *
     * @param command - the command
     * @return true if the command executes
     */
    public boolean executeCommand(Visitor command)
    {
        this.root.accept(command);
        return true;
    }

    /**
     * returns true if the command executed
     *
     * @param command - the command
     * @param user - the user
     * @return true if the command executes
     */
    public boolean executeCommand(Visitor command, User user)
    {
        user.getCurrentDirectory().accept(command);
        // this.root.accept(command);
        return true;
    }

    /**
     * returns true if the command executed
     *
     * @param command - the command
     * @param user - the user
     * @param group - the group
     * @return true if the command executes
     */
    public boolean executeCommand(Visitor command, User user, Group group)
    {
        return false;
    }

    /**
     * Returns the user object if it exists. Returns null
     * otherwise.
     *
     * @param userName - user to get
     * @return the user object or null
     */
    public User getUser(String userName)
    {
        return users.get(userName);
    }

    /**
     * initalize the file system with given users/groups
     *
     * @param userReader - name of the reader for user
     * @param groupReader - name of reader for group
     */
    private void init(Reader userReader, Reader groupReader)
    {
        BufferedReader brUsers = null;
        BufferedReader brGroups = null;

        try {
            brUsers = new BufferedReader(userReader);
            brGroups = new BufferedReader(groupReader);
            String line;
            Directory homeDir;

            while ((line = brUsers.readLine()) != null) {
                line = line.toLowerCase(); // avoid dealing with upper/lowercase
                User user;
                Group group = new Group(line);
                // each user should have their own group (like linux)
                groups.put(group.getName(), group);

                if ("super".equals(line) || "root".equals(line)) {
                    user = new User(line, true);
                    root.setUser(user);
                    root.setGroup(group);
                }
                else {
                    user = new User(line);
                }

                homeDir = user.getHomeDir();
                users.put(line, user);
                root.addChild(homeDir);
            }

            while ((line = brGroups.readLine()) != null) {
                Group group = parseGroup(line);
                groups.put(group.getName(), group);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (brUsers != null) {
                try {
                    brUsers.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (brGroups != null) {
                try {
                    brGroups.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Group parseGroup(CharSequence groupString)
    {
        String[] groupTokens = Helpers.stripCharsFromStrings(" ", SPACE.split(groupString));
        Group group = new Group(groupTokens[0]); // the group name is first

        for (int i = 1; i < groupTokens.length; ++i) {
            User user = users.get(groupTokens[i]);

            if (user != null) {
                group.addUser(user);
            }
        }
        return group;
    }
}
