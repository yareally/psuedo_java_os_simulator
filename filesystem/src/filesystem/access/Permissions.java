package filesystem.access;

public class Permissions
{

    boolean read    = false;
    boolean write   = false;
    boolean execute = false;

    /**
     * sets the permissions with booleans
     *
     * @param read
     * @param write
     * @param execute
     */
    public Permissions(boolean read, boolean write, boolean execute)
    {
        setPerms(read, write, execute);
    }

    /**
     * sets the perm with strings
     *
     * @param numericPerms
     * @throws NumberFormatException
     */
    public Permissions(String numericPerms) throws NumberFormatException
    {
        setPerms(numericPerms);
    }

    /**
     * creates permissions based on booleans
     *
     * @param read
     * @param write
     * @param execute
     */
    public final void setPerms(boolean read, boolean write, boolean execute)
    {
        this.read = read;
        this.write = write;
        this.execute = execute;
    }

    /**
     * this is incase the user wants to use numerics to set permissions
     *
     * @param numericPerms - numaric version of perm
     * @throws NumberFormatException
     */
    public final void setPerms(String numericPerms) throws NumberFormatException
    {
        if (numericPerms.length() > 1 || Integer.parseInt(numericPerms) > 7 || Integer.parseInt(numericPerms) < 0) {
            throw new NumberFormatException("Permissions must be between 0 or 7 inclusive");
        }

        //for (int i = 0; i < numericPerms.length(); ++i) {
        int perm = Character.getNumericValue(numericPerms.charAt(0));

        switch (perm) {
            case 1:
                // execute only
                setPerms(false, false, true);
                break;
            case 2:
                // write only
                setPerms(false, true, false);
                break;
            case 3:
                // write & execute only
                setPerms(false, true, true);
                break;
            case 4:
                // read only
                setPerms(true, false, false);
                break;
            case 5:
                // read & execute
                setPerms(true, false, true);
                break;
            case 6:
                // read & write
                setPerms(true, true, false);
                break;
            case 7:
                // all permissions
                setPerms(true, true, true);
                break;
            default:
                if (perm < 0 || perm > 7) {
                    throw new NumberFormatException("Numeric permission must be between 0 and 7 inclusive");
                }
                break;
        }
        // }
    }

    /**
     * determines if able to read or not
     *
     * @return true if there are read perm
     */
    public boolean canRead()
    {
        return read;
    }

    /**
     * determines if able to write or not
     *
     * @return true if there are write perm
     */
    public boolean canWrite()
    {
        return write;
    }

    /**
     * determines if able to execute or not
     *
     * @return true if there are execute perm
     */
    public boolean canExecute()
    {
        return execute;
    }

    /**
     * set this.read to the read state passed in
     *
     * @param read
     */
    public void setRead(boolean read)
    {
        this.read = read;
    }

    /**
     * set this.write to the write state passed in
     *
     * @param write
     */
    public void setWrite(boolean write)
    {
        this.write = write;
    }

    /**
     * set this.execute to the execute state passed in
     *
     * @param execute
     */
    public void setExecute(boolean execute)
    {
        this.execute = execute;
    }

    /**
     * converts the true/false to a string that consists of r,w,x or - if none
     *
     * @return string with the proper perm format
     */
    public String toString()
    {
        String newRead;
        String newWrite;
        String newExecute;

        newRead = read ? "r" : "-";

        newWrite = write ? "w" : "-";

        newExecute = execute ? "x" : "-";

        return newRead + newWrite + newExecute;
    }
}
