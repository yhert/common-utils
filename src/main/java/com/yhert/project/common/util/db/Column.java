package com.yhert.project.common.util.db;

import java.io.Serializable;

import com.yhert.project.common.beans.Model;

/**
 * 字段信息
 * 
 * @author Ricardo Li 2017年6月17日 上午11:50:59
 *
 */
public class Column extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否为主键
	 */
	private boolean pk;
	/**
	 * 字段名称
	 */
	private String columnName;
	/**
	 * 说明
	 */
	private String remarks;
	/**
	 * 字段类型
	 */
	private String typeName;
	/**
	 * 字段尺寸
	 */
	private long columnSize;
	/**
	 * 允许为空
	 */
	private boolean nullable;
	/**
	 * 默认值
	 */
	private String columnDef;
	/**
	 * 转换为Java对象
	 */
	private Class<?> type;
	/**
	 * 是否是唯一索引
	 */
	private boolean unique;
	/**
	 * 是否是普通索引
	 */
	private boolean index;

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public long getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(long columnSize) {
		this.columnSize = columnSize;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getColumnDef() {
		return columnDef;
	}

	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

}