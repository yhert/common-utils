package com.yhert.project.common.db.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

@Table(name = "a_user")
public class BaseUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIELD_ID = "id";
	public static final String FIELD_USERNAME = "username";
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_CREATE_TIME = "createTime";

	private String id;
	private String username;
	private String password;
	private Date createTime;
	private String name;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BaseUser [id=" + id + ", username=" + username + ", password=" + password + ", createTime=" + createTime
				+ ", name=" + name + "]";
	}
}
