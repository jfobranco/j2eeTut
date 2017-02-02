package com.jb.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jb.dao.PostDao;
import com.jb.entities.Post;

@ManagedBean
@RequestScoped
public class ShowPost implements Serializable {
	private static final long serialVersionUID = 1L;

	private Post post;
	private Long postId;
	private boolean disabled = true;
	@EJB
	private PostDao postDao;

	public ShowPost() {
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
		getPost();
	}

	public void load() {
		if (postId != null)
			post = postDao.findId(postId);
	}

	public Post getPost() {
		if (postId != null && (post == null || post.getId() != postId))
			post = postDao.findId(postId);

		return post;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
