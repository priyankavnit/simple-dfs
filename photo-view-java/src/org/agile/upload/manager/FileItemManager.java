package org.agile.upload.manager;

import java.util.List;

import org.agile.upload.entity.FileItem;
import org.springframework.orm.jpa.JpaTemplate;

public class FileItemManager {
	private JpaTemplate template;

	public String hello(String name) {
		return "hello " + name + " at " + new java.util.Date();
	}

	@SuppressWarnings("unchecked")
	public List list() {
		List ret = template.find("select item.id,item.name,item.modified from FileItem item");
		return ret;
	}

	@SuppressWarnings("unchecked")
	public FileItem findById(long id) {
		FileItem item = template.find(FileItem.class, id);
		// List list = template.find("select item from FileItem fetch item.data item where item.id=" + id);
		// return (FileItem) (list.size() > 0 ? list.get(0) : null);
		return item;
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
