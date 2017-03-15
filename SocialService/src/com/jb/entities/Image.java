package com.jb.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Lob
	private byte[] image;
	private String reference; // how to add index?
	// private UploadedFile uploadedFile;

	public Image() {
		reference = UUID.randomUUID().toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		// don't let edit reference, but allow to access like property
	}
}