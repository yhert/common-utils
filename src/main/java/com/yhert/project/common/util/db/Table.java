package com.yhert.project.common.util.db;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 数据表信息
 * 
 * @author Ricardo Li 2017年9月6日 下午4:46:04
 *
 */
public class Table implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String catalog;
	private String schema;
	private String tableName;
	private int type; // 1：基础数据表，2、视图，3、系统表
	private Date createTime; // 创建时间
	private String remake; // 说明

	private List<Column> columns; // 字段信息

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
	 * @param type
	 *            1：基础数据表，2、视图，3、系统表
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

	@Override
	public String toString() {
		return "Table [catalog=" + catalog + ", schema=" + schema + ", tableName=" + tableName + ", type=" + type
				+ ", createTime=" + createTime + ", remake=" + remake + ", columns=" + columns + "]";
	}
}
