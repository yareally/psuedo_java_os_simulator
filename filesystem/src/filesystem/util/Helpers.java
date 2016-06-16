package filesystem.util;

import java.util.regex.Pattern;

/**
 *
 */
public class Helpers
{
    private static final Pattern SPACE = Pattern.compile(" ");
    private static final Pattern COMMA = Pattern.compile(",");
    public static final  int     CMD   = 0;

    private Helpers() {}

    /**
     * strips out any whitespace the user enters
     *
     * @param chars - arity of the tokens in the string
     * @param tokens - arity of the tokens in the string
     * @return - tokens of the string without whitespace
     */
    public static String[] stripCharsFromStrings(CharSequence chars, String... tokens)
    {
        // Strip off whitespace a user might enter
        for (int i = 0; i < tokens.length; ++i) {
            tokens[i] = tokens[i].replace(chars, "");
        }
        return tokens;
    }

    /**
     * strips out whitespace of the entered command. parses out the file/directory
     * name for the command to do work on.
     *
     * @param cmdString - command that the currentUser entered
     * @return - a string 2d array containig the command, arguemnts and fsElements
     */
    public static String[][] parseCommand(String cmdString)
    {
        int firstSpacePos = cmdString.indexOf(' ');
        String command = firstSpacePos == -1
            ? cmdString
            : cmdString.substring(0, firstSpacePos);
        command = command.replace(" ", "");
        String argsAndFiles = firstSpacePos == -1
            ? ""
            : cmdString.substring(firstSpacePos, cmdString.length()).replace(" ", "");
        cmdString = command + " " + argsAndFiles;

        String[] tokens = SPACE.split(cmdString);
        String[] args = null;
        String[] cmd = {tokens[CMD]};

        // parse out the files/directories for the commmand (which are always at the end
        String[] fsElements = tokens.length > 1
            ? COMMA.split(tokens[tokens.length - 1])
            : new String[]{"*"};

        if (tokens.length > 2) {
            args = new String[tokens.length - 2];
            System.arraycopy(tokens, 1, args, 0, args.length - 1);
        }
        else {
            args = new String[]{""};
        }
        return new String[][]{cmd, args, fsElements};
    }
}
