package com.yhert.project.common.beans;

import java.io.Serializable;

import com.yhert.project.common.db.dao.IDao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 抽象Condition接口
 * 
 * @author Ricardo Li 2017年5月22日 上午10:35:01
 *
 */
@ApiModel(value = "参数封装", description = "请求数据参数的公共封装包")
public class AbstractCondition extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIELD_SORT = IDao.SORT;
	public static final String FIELD_LIMIT = IDao.LIMIT;
	public static final String FIELD_START = IDao.START;
	/**
	 * 排序类型常量，asc
	 */
	public static String VALUE_SORT_TTYPE_ASC = IDao.VALUE_SORT_TTYPE_ASC;
	/**
	 * 排序类型常量，desc
	 */
	public static String VALUE_SORT_TTYPE_DESC = IDao.VALUE_SORT_TTYPE_DESC;

	/**
	 * 排序信息，格式：字段 (空格)顺序[asc|desc]，多个使用,(逗号分隔)，示例：create_time desc,id asc,name
	 */
	@ApiModelProperty(name = "sort", value = "排序信息，格式：字段 (空格)顺序[asc|desc]，多个使用,(逗号分隔)，示例：create_time desc,id asc,name")
	private String sort;
	/**
	 * 分页时，当前页查询数据量
	 */
	@ApiModelProperty(name = "limit", value = "分页时，当前页查询数据量")
	private Integer limit;
	/**
	 * 分页时开始位置：从0开始
	 */
	@ApiModelProperty(name = "start", value = "分页时开始位置：从0开始")
	private Integer start;

	/**
	 * 获取排序信息，格式：字段 (空格)顺序[asc|desc]，多个使用,(逗号分隔)，示例：create_time desc,id asc,name
	 * 
	 * @return 排序信息，格式：字段 (空格)顺序[asc|desc]，多个使用,(逗号分隔)，示例：create_time desc,id
	 *         asc,name
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * 设置排序信息，格式：字段 (空格)顺序[asc|desc]，多个使用,(逗号分隔)，示例：create_time desc,id asc,name
	 * 
	 * @param sort 排序信息，格式：字段 (空格)顺序[asc|desc]，多个使用,(逗号分隔)，示例：create_time desc,id
	 *             asc,name
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * 获取分页时，当前页查询数据量
	 * 
	 * @return 分页时，当前页查询数据量
	 */
	public Integer getLimit() {
		return limit;
	}

	/**
	 * 设置分页时，当前页查询数据量
	 * 
	 * @param limit 分页时，当前页查询数据量
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * 获取分页时开始位置：从0开始
	 * 
	 * @return 分页时开始位置：从0开始
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * 设置分页时开始位置：从0开始
	 * 
	 * @param start 分页时开始位置：从0开始
	 */
	public void setStart(Integer start) {
		this.start = start;
	}
}
