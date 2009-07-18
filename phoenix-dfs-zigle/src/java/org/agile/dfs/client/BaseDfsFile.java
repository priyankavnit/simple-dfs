package org.agile.dfs.client;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class BaseDfsFile extends java.io.File {
    private static final long serialVersionUID = 1978;
    private String id;
    private String nameSpace;
    private String fullPath;

    /**
     * 
     * DfsFile schema style: http://www.agile.org/photo (like url)
     * 
     * DfsFile path style: /home/agile/some.jpg (file), /home/photo (directory)
     * 
     * @param nameSpace
     * @param fullPath
     */
    public BaseDfsFile(String nameSpace, String fullPath) {
        super(fullPath);
        this.nameSpace = nameSpace;
        this.fullPath = fullPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchema() {
        return nameSpace;
    }

    public String getFullPath() {
        return fullPath;
    }

    public boolean canRead() {
        throw new UnsupportedOperationException();
    }

    public boolean canWrite() {
        throw new UnsupportedOperationException();
    }

    public int compareTo(File pathname) {
        throw new UnsupportedOperationException();
    }

    public boolean createNewFile() throws IOException {
        throw new UnsupportedOperationException();
    }

    public boolean delete() {
        throw new UnsupportedOperationException();
    }

    public void deleteOnExit() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

    public boolean exists() {
        throw new UnsupportedOperationException();
    }

    public File getAbsoluteFile() {
        throw new UnsupportedOperationException();
    }

    public String getAbsolutePath() {
        throw new UnsupportedOperationException();
    }

    public File getCanonicalFile() throws IOException {
        throw new UnsupportedOperationException();
    }

    public String getCanonicalPath() throws IOException {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public String getParent() {
        return super.getParent();
    }

    public File getParentFile() {
        throw new UnsupportedOperationException();
    }

    public String getPath() {
        return super.getPath();
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public boolean isAbsolute() {
        throw new UnsupportedOperationException();
    }

    public boolean isDirectory() {
        throw new UnsupportedOperationException();
    }

    public boolean isFile() {
        throw new UnsupportedOperationException();
    }

    public boolean isHidden() {
        throw new UnsupportedOperationException();
    }

    public long lastModified() {
        throw new UnsupportedOperationException();
    }

    public long length() {
        throw new UnsupportedOperationException();
    }

    public String[] list() {
        throw new UnsupportedOperationException();
    }

    public String[] list(FilenameFilter filter) {
        throw new UnsupportedOperationException();
    }

    public File[] listFiles() {
        throw new UnsupportedOperationException();
    }

    public File[] listFiles(FileFilter filter) {
        throw new UnsupportedOperationException();
    }

    public File[] listFiles(FilenameFilter filter) {
        throw new UnsupportedOperationException();
    }

    public boolean mkdir() {
        throw new UnsupportedOperationException();
    }

    public boolean mkdirs() {
        throw new UnsupportedOperationException();
    }

    public boolean renameTo(File dest) {
        throw new UnsupportedOperationException();
    }

    public boolean setLastModified(long time) {
        throw new UnsupportedOperationException();
    }

    public boolean setReadOnly() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return "Dfs file, ns:" + this.nameSpace + ", path:" + fullPath;
    }

    public URI toURI() {
        throw new UnsupportedOperationException();
    }

    public URL toURL() throws MalformedURLException {
        throw new UnsupportedOperationException();
    }

}
