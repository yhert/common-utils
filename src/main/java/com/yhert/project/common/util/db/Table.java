package com.yhert.project.common.util.db;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.yhert.project.common.beans.Model;

/**
 * 数据表信息
 * 
 * @author Ricardo Li 2017年9月6日 下午4:46:04
 *
 */
public class Table extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * catelog数据
	 */
	private String catalog;
	/**
	 * 数据库方案
	 */
	private String schema;
	/**
	 * 数据表名称
	 */
	private String tableName;
	/**
	 * 1：基础数据表，2、视图，3、系统表
	 */
	private int type;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 说明
	 */
	private String remake;
	/**
	 * 字段信息
	 */
	private List<Column> columns;

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getType() {
		return type;
	}

	/**
	 * 数据表类型
	 * 
	 * @param type 1：基础数据表，2、视图，3、系统表
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 数据表类型
	 * 
	 * @return 1：基础数据表，2、视图，3、系统表
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemake() {
		return remake;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}
