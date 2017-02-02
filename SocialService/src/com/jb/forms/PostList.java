package com.jb.forms;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jb.dao.PostDao;
import com.jb.entities.Post;

@ManagedBean
@RequestScoped
public class PostList implements Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Long, Post> posts;
	@EJB
	private PostDao postDao;

	public PostList() {
	}

	public Map<Long, Post> getPosts() {
		if (posts == null)
			posts = postDao.list();
		return posts;
	}

	public void setPosts(Map<Long, Post> posts) {
		this.posts = posts;
	}
}
