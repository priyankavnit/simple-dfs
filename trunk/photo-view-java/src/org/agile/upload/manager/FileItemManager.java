package org.agile.upload.manager;

import org.agile.upload.entity.FileItem;
import org.springframework.orm.jpa.JpaTemplate;

public class FileItemManager {
    private JpaTemplate template;

    public String hello(String name) {
        return "hello " + name + " at " + new java.util.Date();
    }

    public FileItem save(FileItem item) {
        synchronized (template) {
            if (item.getId() == null) {
                template.persist(item);
            } else {
                template.merge(item);
            }
            template.flush();
            return item;
        }
    }

    public void setTemplate(JpaTemplate template) {
        this.template = template;
    }

}
