package org.agile.dfs.core.action;

public abstract class DfsBaseAction {
    public static final int MAX_ACTION_CONTENT = 1 * 1024;
    public static final int MAX_ACTION_RESPONSE = 1 * 1024;

    public static final String CMD_RESPONSE_SUCCESS = "success";
    public static final String CMD_RESPONSE_EXCEPTION = "exception";
    public static final String CMD_RESPONSE_SPLIT = ":";

    // example action:file.create;arg:/home/agile/zou.jpg \n
    public static final String GLB_SPLIT = ";";
    // public static final String GLB_END = "\0\0\0\0";
    public static final String CMD_PREFIX = "action:";
    public static final String ARG_PREFIX = "arg:";
    public static final String ARG_SPLIT = ",";

    private String name;
    private String[] arguments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] args) {
        this.arguments = args;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append(CMD_PREFIX);
        sb.append(name);
        sb.append(GLB_SPLIT);
        sb.append(ARG_PREFIX);
        String[] args = getArguments();
        for (int i = 0; args != null && i < args.length; i++) {
            String arg = args[i];
            sb.append(arg);
            if (i != args.length - 1) {
                sb.append(ARG_SPLIT);
            }
        }
        // sb.append(GLB_END);
        return sb.toString();
    }
}
