import commands.Visitor;
import filesystem.FileSystem;
import filesystem.access.User;
import filesystem.util.Helpers;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.Scanner;

/**
 *
 */
public class Os
{
    private static final String     USERS       = "James\n" +
        "Jones\n" +
        "Jackson\n" +
        "Chang\n" +
        "Avis\n" +
        "super";
    private static final String     GROUPS      = "faculty James Chang\n" +
        "student Jackson Jones";
    private static final int        ARGS        = 1;
    private static final int        FS_ELEMENTS = 2;
    private static final String     USER_FILE   = "D:/schoolStuff2012/filesystem/users.txt";
    private static final String     GROUP_FILE  = "D:/schoolStuff2012/filesystem/groups.txt";
    private static final String     HOST_NAME   = "HAL-9000";
    private static       FileSystem fs          = null;
    private              User       currentUser = null;
    private              Scanner    keyboard    = new Scanner(System.in);

    /**
     * default constructor
     */
    public Os()
    {
        fs = new FileSystem(new StringReader(USERS), new StringReader(GROUPS));
    }

    /**
     * constructor that passes in the files that contain users/groups
     * to be used with the filesystem
     *
     * @param userFileName - file name for the users
     * @param groupFileName - file name for the group
     */
    public Os(String userFileName, String groupFileName)
    {
        fs = new FileSystem(userFileName, groupFileName);
    }

    /**
     * starts a new session and displays a messaage to the currentUser. If login method
     * failed then will show an error. If not it will continue and get the users
     * command
     *
     * @param loginMsg - login message that is displayed to the currentUser
     */
    public void newSession(String loginMsg)
    {
        System.out.println(loginMsg + '\n');

        while (!login()) {
            System.out.println("Invalid currentUser! Please re-enter username.");
        }

        String cmdString = "";

        while (!"exit".equals(cmdString) && !"logout".equals(cmdString)) {
            System.out.print(getCmdPromptString());
            cmdString = keyboard.nextLine();
            Visitor command = createCommand(Helpers.parseCommand(cmdString));
            fs.executeCommand(command, currentUser);
        }

        if ("logout".equals(cmdString)) {
            newSession(loginMsg);
        }
    }

    /**
     * asks the currentUser to enter their currentUser name and checks to see if it matches
     * a currentUser name in the currentUser.txt file
     *
     * @return true if the currentUser is created/found
     */
    private boolean login()
    {
        System.out.print("Enter your username: ");
        String userName = keyboard.nextLine().toLowerCase();
        currentUser = fs.getUser(userName);
        System.out.println();

        return currentUser != null;
    }

    /**
     * gets the cmd prompt
     *
     * @return - cmd prompt
     */
    private String getCmdPromptString()
    {
        String prompt = currentUser.getName() + '@' + HOST_NAME + ':';
        prompt += currentUser.getHomeDir().getName().equals(currentUser.getCurrentDirectory().getName())
            ? '~'
            : currentUser.getCurrentDirectory().getName();

        prompt += currentUser.isRootUser() ? "# " : "$ ";
        return prompt;
    }

    /**
     * creates a command (visitor) object. Then finds the class that contains
     * that command by class name which will provide functionality for that command.
     *
     * @param cmdTokens - contains the command to run, args for it and files/directories to execute it on
     * @return the created command object instead of command token strings
     */
    private Visitor createCommand(String[][] cmdTokens)
    {
        Visitor cmd = null;

        try {
            String cmdName = Character.toUpperCase(cmdTokens[0][Helpers.CMD].charAt(0)) + cmdTokens[Helpers.CMD][0].substring(1);
            Class<?> aClass = Class.forName(Visitor.class.getPackage().getName() + '.' + cmdName);
            Constructor<?> ctor = aClass.getConstructor(String[].class, User.class, String[].class);
            cmd = (Visitor) ctor.newInstance(cmdTokens[ARGS], currentUser, cmdTokens[FS_ELEMENTS]);
        } catch (Exception ignored) {
            System.out.println(cmdTokens[0][Helpers.CMD] + ": command not found");
        } finally {
            System.out.println(); // print out a new line after the command runs (or errors)
        }

        return cmd;
    }
}
