package com.yhert.project.common.beans;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询结果集
 * 
 * @author Ricardo Li 2017年3月22日 下午9:20:38
 *
 * @param <T> 类型
 */
@ApiModel(value = "响应信息封装", description = "响应的信息结果")
public class Result<T> extends Model implements Serializable {
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
	@ApiModelProperty(name = "data", value = "结果集")
	private List<T> data;
	/**
	 * 总数据量
	 */
	@ApiModelProperty(name = "allCount", value = "总数据量", example = "100")
	private int allCount;
	/**
	 * 分页时开始位置：从0开始
	 */
	@ApiModelProperty(name = "start", value = "分页时开始位置：从0开始", example = "0")
	private int start;
	/**
	 * 当前数量
	 */
	@ApiModelProperty(name = "count", value = "当前数量", example = "9")
	private int count;
	/**
	 * 分页数量
	 */
	@ApiModelProperty(name = "limit", value = "分页数量", example = "10")
	private int limit;
	/**
	 * 名称，为xls等表单sheet名称
	 */
	@ApiModelProperty(name = "sheetName", value = "名称，为xls等表单sheet名称", example = "用户信息")
	private String sheetName;
	/**
	 * 下载文件文件名
	 */
	@ApiModelProperty(name = "fileName", value = "下载文件文件名", example = "首页图片.jpg")
	private String fileName;

	/**
	 * 获得查询的结果集
	 * 
	 * @return 查询的结果集
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * 设置查询的结果集
	 * 
	 * @param data 查询的结果集
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * 获得总数据量
	 * 
	 * @return 总数据量
	 */
	public int getAllCount() {
		return allCount;
	}

	/**
	 * 设置总数据量
	 * 
	 * @param allCount 总数据量
	 */
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	/**
	 * 获取分页时开始位置：从0开始
	 * 
	 * @return 分页时开始位置：从0开始
	 */
	public int getStart() {
		return start;
	}

	/**
	 * 设置分页时开始位置：从0开始
	 * 
	 * @param start 分页时开始位置：从0开始
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 获取当前数量
	 * 
	 * @return 当前数量
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 设置当前数量
	 * 
	 * @param count 当前数量
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 获取名称，为xls等表单sheet名称
	 * 
	 * @return 名称，为xls等表单sheet名称
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * 设置名称，为xls等表单sheet名称
	 * 
	 * @param sheetName 名称，为xls等表单sheet名称
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * 获取下载文件文件名
	 * 
	 * @return 下载文件文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置下载文件文件名
	 * 
	 * @param fileName 下载文件文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 获取分页数量
	 * 
	 * @return 分页数量
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * 设置分页数量
	 * 
	 * @param limit 分页数量
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

}
