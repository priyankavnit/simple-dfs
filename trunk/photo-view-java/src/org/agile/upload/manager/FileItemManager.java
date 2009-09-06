package org.agile.upload.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.agile.upload.entity.FileItem;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class FileItemManager {
	private JpaTemplate template;
	private TransformManager transformer;

	@SuppressWarnings("unchecked")
	public List list() {
		List ret = template.find("select item.id,item.name,item.modified from FileItem item");
		return ret;
	}

	@SuppressWarnings("unchecked")
	public FileItem findById(long id) {
		List list = template.find("select item from FileItem item where item.id=" + id);
		FileItem item = (FileItem) (list.size() > 0 ? list.get(0) : null);
		if (item != null) {
			item.setRawimage(findRawimageById(id));
		}
		return item;
	}

	public Blob findRawimageById(long id) {
		final String ql = "select item.rawimage from FileItem item  where item.id=" + id;
		Object obj = template.execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(ql);
				return query.getSingleResult();
			}

		});
		return (Blob) obj;
	}

	public FileItem save(FileItem item) {
		synchronized (template) {
			if (item.getRawimage() != null) {
				transformer.thumbnail(item.getRawimage().getBytes(), 32, 32);
			}
			if (item.getId() == null) {
				template.persist(item);
			} else {
				item.setModified(new Date());
				template.merge(item);
			}
			template.flush();
			return item;
		}
	}

	public void remove(final List<Long> list) {
		template.execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				// Key parent = KeyFactory.createKey("yar", "does not exist");
				List<Key> keys = new ArrayList<Key>();
				// keys.add(parent);
				for (Number id : list) {
					Key key = KeyFactory.createKey( FileItem.class.getSimpleName(), id.longValue());
					keys.add(key);
				}
				Query query = em.createQuery("delete from " + FileItem.class.getName() + " where id = :ids");
				query.setParameter("ids", keys);
				query.executeUpdate();
				return null;
			}
		});
	}

	public void setTemplate(JpaTemplate template) {
		this.template = template;
	}

	public void setTransformer(TransformManager transformer) {
		this.transformer = transformer;
	}

}
