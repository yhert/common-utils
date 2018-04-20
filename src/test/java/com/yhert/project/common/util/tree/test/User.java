package com.yhert.project.common.util.tree.test;

import java.io.Serializable;

/**
 * 测试用户
 * 
 * @author Ricardo Li 2017年3月22日 下午7:22:02
 *
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String parentId;

	public User() {
		super();
	}

	public User(String id, String parentId, String username) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.username = username;
	}

	public User(String id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", parentId=" + parentId + "]";
	}

}
