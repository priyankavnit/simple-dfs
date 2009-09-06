package org.agile.upload.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.ShortBlob;

@Entity
public class FileItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date modified;

    @Basic(fetch=FetchType.LAZY)
    @Enumerated
    private Blob rawimage;

    @Basic(fetch=FetchType.EAGER)
    @Enumerated
    private ShortBlob thumbnail;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

	public Blob getRawimage() {
		return rawimage;
	}

	public void setRawimage(Blob rawimage) {
		this.rawimage = rawimage;
	}

	public ShortBlob getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(ShortBlob thumbnail) {
		this.thumbnail = thumbnail;
	}
 

}
