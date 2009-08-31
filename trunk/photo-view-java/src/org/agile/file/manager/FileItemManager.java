package org.agile.file.manager;

import org.agile.file.entity.FileItem;
import org.springframework.orm.jpa.JpaTemplate;

public class FileItemManager {
    private JpaTemplate template;

    public FileItem save(FileItem item) {
        if (item.getId() == null) {
            template.persist(item);
        } else {
            template.merge(item);
        }
        template.flush();
        return item;
    }

    public void setTemplate(JpaTemplate template) {
        this.template = template;
    }

}
