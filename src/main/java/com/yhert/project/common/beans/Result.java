package com.yhert.project.common.beans;

import java.io.Serializable;
import java.util.List;

/**
 * 查询结果集
 * 
 * @author Ricardo Li 2017年3月22日 下午9:20:38
 *
 * @param <T>
 *            类型
 */
public class Result<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String FINAL_DATA = "data";
	public static final String FINAL_ALL_COUNT = "allCount";
	public static final String FINAL_START = "start";
	public static final String FINAL_COUNT = "count";
	public static final String FINAL_LIMIT = "limit";
	public static final String FINAL_SHEET_NAME = "sheetName";
	public static final String FINAL_FILE_NAME = "fileName";

	/**
	 * 查询的结果集
	 */
	private List<T> data;
	/**
	 * 总数据量
	 */
	private int allCount;
	/**
	 * 开始位置：从0开始
	 */
	private int start;
	/**
	 * 当前数量
	 */
	private int count;
	/**
	 * 分页数量
	 */
	private int limit;
	/**
	 * 名称，为xls等表单sheet名称
	 */
	private String sheetName;
	/**
	 * 下载文件文件名
	 */
	private String fileName;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "Result [data=" + data + ", allCount=" + allCount + ", start=" + start + ", count=" + count + ", limit="
				+ limit + ", sheetName=" + sheetName + ", fileName=" + fileName + "]";
	}

}
