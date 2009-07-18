package org.agile.dfs.name.setup;

import org.agile.dfs.name.manager.FileItemManager;
import org.agile.dfs.name.manager.TableManager;
import org.agile.dfs.util.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameInstaller {
    private static final Logger logger = LoggerFactory.getLogger(FileItemManager.class);
    private final TableManager tblMgr = (TableManager) ServiceFactory.findService(TableManager.class);

    public void install() {
        logger.info("Ready to install name node... ");
        logger.info(" -create name space table. ");
        createSchemaTable();
    }

    private void createSchemaTable() {
        tblMgr.createSchemaTable();
    }

}
