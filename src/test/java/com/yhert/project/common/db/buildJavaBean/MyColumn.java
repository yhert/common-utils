package com.yhert.project.common.db.buildJavaBean;

import com.yhert.project.common.util.db.Column;

/**
 * 处理的数据
 * 
 * @author Ricardo Li 2017年6月20日 下午8:45:42
 *
 */
public class MyColumn extends Column {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uname;
	private String cname;
	private String typeName;
	private String ucname;
	private Boolean needSize = false;

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUcname() {
		return ucname;
	}

	public void setUcname(String ucname) {
		this.ucname = ucname;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Boolean getNeedSize() {
		return needSize;
	}

	public void setNeedSize(Boolean needSize) {
		this.needSize = needSize;
	}

}
