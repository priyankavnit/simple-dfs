package org.agile.dfs.client;

import java.io.File;
import java.io.IOException;

import org.agile.dfs.client.service.DfsServiceLocator;
import org.agile.dfs.core.entity.FileItem;
import org.agile.dfs.name.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DfsFile extends BaseDfsFile {
    private static final long serialVersionUID = 1978;
    private static final Logger logger = LoggerFactory.getLogger(DfsFile.class);
    private static final FileService fileService = DfsServiceLocator.lookup(FileService.class);
    private FileItem item;

    public DfsFile(String schema, String fullPath) {
        super(schema, fullPath);
    }

    @Override
    public boolean createNewFile() throws IOException {
        String id = fileService.createNewFile(this.getSchema(), this.getFullPath());
        if (id != null) {
            this.setId(id);
            logger.info("Success to create file {}:{} ", this.getSchema(), this.getFullPath());
        }
        return id != null;
    }

    @Override
    public boolean mkdir() {
        boolean flag = fileService.mkdir(this.getSchema(), this.getFullPath());
        if (flag) {
            logger.info("Success to create directory {}:{})", this.getSchema(), this.getFullPath());
        }
        return flag;
    }

    @Override
    public boolean mkdirs() {
        boolean flag = fileService.mkdirs(this.getSchema(), this.getFullPath());
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
        fileService.delete(this.getSchema(), this.getFullPath());
        this.setId(null);
        return true;
    }

    @Override
    public String getId() {
        if (item == null) {
            item = fileService.findByPath(this.getSchema(), this.getFullPath());
        }
        return item == null ? null : item.getId();
    }

}
