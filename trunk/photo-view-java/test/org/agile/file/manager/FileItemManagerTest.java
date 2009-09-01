package org.agile.file.manager;

import java.util.Date;

import junit.framework.Assert;

import org.agile.file.entity.FileItem;
import org.agile.test.LocalDatastoreTestCase;

import com.google.appengine.api.datastore.Blob;

public class FileItemManagerTest extends LocalDatastoreTestCase {

    private FileItemManager manager;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        manager = (FileItemManager) this.getBean("photo.FileItemManager");
    }

    @Override
    public void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
    }

    public void testSave() {
        FileItem item = new FileItem();
        item.setName("some");
        item.setModified(new Date());
        item.setData(new Blob("some bytes".getBytes()));
        manager.save(item);
        Assert.assertNotNull(item.getId());
        Assert.assertNotNull(item.getId() > 0);
    }

}
