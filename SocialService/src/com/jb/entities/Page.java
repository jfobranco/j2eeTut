package com.jb.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Page {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private int type; // type of page, some special templates?
	private String content;
	// @Lob
	// private byte[] headerPic;
	@ManyToOne(optional = false)
	@JoinColumn(name = "service", referencedColumnName = "id")
	private Service service;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "page")
	private List<PageItem> items;

	public List<PageItem> getItems() {
		return items;
	}

	public void setItems(List<PageItem> items) {
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	// public byte[] getHeaderPic() {
	// return headerPic;
	// }
	//
	// public void setHeaderPic(byte[] headerPic) {
	// this.headerPic = headerPic;
	// }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}