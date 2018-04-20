package com.yhert.project.common.beans;

import java.io.Serializable;

import com.yhert.project.common.db.dao.IDao;

/**
 * 抽象Condition接口
 * 
 * @author Ricardo Li 2017年5月22日 上午10:35:01
 *
 */
public class AbstractCondition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIELD_SORT_COLUMN = IDao.SORT_COLUMN;
	public static final String FIELD_SORT_TYPE = IDao.SORT_TTYPE;
	public static final String FIELD_LIMIT = IDao.LIMIT;
	public static final String FIELD_START = IDao.START;
	
	private String sortColumn;
	private String sortType;
	private Integer limit;
	private Integer start;

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
}
