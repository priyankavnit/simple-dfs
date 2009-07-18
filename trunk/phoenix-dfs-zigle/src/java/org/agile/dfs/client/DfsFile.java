package org.agile.dfs.client;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.agile.dfs.name.service.FileService;

public class DfsFile extends BaseDfsFile {
    private static final long serialVersionUID = 1978;
    private static final Logger logger = LoggerFactory.getLogger(DfsFile.class);
    private static final FileService fileService = ServiceLocator.lookup(FileService.class);

    public DfsFile(String nameSpace, String fullPath) {
        super(nameSpace, fullPath);
    }

    @Override
    public boolean createNewFile() throws IOException {
        boolean flag = fileService.createNewFile(this.getSchema(), this.getFullPath());
        if (flag) {
            logger.info("Success to create file {}:{} ", this.getSchema(), this.getFullPath());
        }
        return flag;
    }

    @Override
    public boolean mkdir() {
        boolean flag = fileService.mkdir(this.getSchema(), this.getFullPath(), false);
        if (flag) {
            logger.info("Success to create directory {}:{})", this.getSchema(), this.getFullPath());
        }
        return flag;
    }

    @Override
    public boolean mkdirs() {
        boolean flag = fileService.mkdir(this.getSchema(), this.getFullPath(), true);
        if (flag) {
            logger.info("Success to create directory {}:{})", this.getSchema(), this.getFullPath());
        }
        return flag;
    }

    @Override
    public boolean exists() {
        return fileService.exists(this.getSchema(), this.getFullPath());
    }

    @Override
    public File getParentFile() {
        String parent = super.getParent();
        return new DfsFile(this.getSchema(), parent);
    }

    @Override
    public boolean delete() {
        boolean flag = fileService.delete(this.getSchema(), this.getFullPath());
        if (flag) {
            this.setId(null);
        }
        return flag;
    }

}
