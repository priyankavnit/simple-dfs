package org.agile.dfs.core.action;

import java.util.HashMap;
import java.util.Map;

import org.agile.dfs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DfsActionFactory {
    private final static Logger logger = LoggerFactory.getLogger(DfsActionFactory.class);
    private static Map map = new HashMap();
    // init action name and class, cache them
    static {
        map.put(HelloWord.ACTION_NAME, HelloWord.class);
        map.put(FileCreate.ACTION_NAME, FileCreate.class);
        map.put(FileExist.ACTION_NAME, FileExist.class);
        map.put(BlockLocate.ACTION_NAME, BlockLocate.class);
        map.put(SpaceLocate.ACTION_NAME, SpaceLocate.class);
        map.put(SpaceCommit.ACTION_NAME, SpaceCommit.class);
        map.put(BlockWrite.ACTION_NAME, BlockWrite.class);
    }

    public DfsBaseAction parser(String line) {
        String action = "", arg = "";
        if (!StringUtil.isBlank(line)) {
            //TODO mdy
            String[] action2arg = StringUtil.simpleSplit(line, ';');
            // String[] action2arg = line.split(DfsBaseAction.GLB_SPLIT);
            if (action2arg.length >= 1) {
                action = action2arg[0];
            }
            if (action2arg.length >= 2) {
                arg = action2arg[1];
            }
        }
        DfsBaseAction inst = null;
        // init action
        if (!StringUtil.isBlank(action) && action.startsWith(DfsBaseAction.CMD_PREFIX)) {
            String realCmd = action.substring(DfsBaseAction.CMD_PREFIX.length());
            inst = instance(realCmd);
        }

        // init arg
        if (inst != null && !StringUtil.isBlank(arg) && arg.startsWith(DfsBaseAction.ARG_PREFIX)) {
            String realArg = arg.substring(DfsBaseAction.ARG_PREFIX.length());
            //TODO mdy
            String[] args = StringUtil.simpleSplit(realArg, ',');
            // String[] args = realArg.split(DfsBaseAction.ARG_SPLIT);
            inst.setArguments(args);
        }
        if (inst == null && !StringUtil.isBlank(line)) {
            logger.error("Not found action for line {} ", line);
        }
        return inst;
    }

    private DfsBaseAction instance(String action) {
        Class clz = (Class) map.get(action);
        if (clz != null) {
            try {
                return (DfsBaseAction) clz.newInstance();
            } catch (Exception e) {
                logger.error("Instance class error " + clz, e);
                return null;
            }
        } else {
            logger.error("Not find class for action: " + action);
            return null;
        }
    }
}
