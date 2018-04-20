service
@[spackagepath]/@[spackage]/service/impl/@[javaname]ServiceImpl.java
false
package ${package}.${spackage}.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ${package}.${spackage}.beans.${javaname};
import ${package}.${spackage}.beans.condition.${javaname}Condition;
import ${package}.${spackage}.beans.vo.${javaname}Vo;
import ${package}.${spackage}.dao.${javaname}Dao;
import ${package}.${spackage}.service.${javaname}Service;
import com.yhert.project.common.beans.Result;
import com.yhert.project.common.service.BaseServiceImpl;

/**
 * ${tableComment}服务层
 * 
 * @author ${author} ${newdate}
 *
 */
@Service("${cname}Service")
public class ${javaname}ServiceImpl extends BaseServiceImpl<${javaname}Dao, ${javaname}> implements ${javaname}Service {
	/**
	 * 查询出一个数据
	 * 
	 * @param ${cname}Condition
	 *            参数
	 * @return 查询结果
	 */
	@Override
	public ${javaname}Vo queryOneVo(${javaname}Condition ${cname}Condition) {
		return dao.queryOneVo(${cname}Condition);
	}

	/**
	 * 查询出一个数据
	 * 
	 * @param whereMap
	 *            参数
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ${javaname}Vo queryOneVo(Map whereMap) {
		return dao.queryOneVo(whereMap);
	}

	/**
	 * 查询数据
	 * 
	 * @param ${cname}Condition
	 *            参数
	 * @return 查询结果
	 */
	@Override
	public Result<${javaname}Vo> queryVo(${javaname}Condition ${cname}Condition) {
		return dao.queryVo(${cname}Condition);
	}

	/**
	 * 查询数据
	 * 
	 * @param whereMap
	 *            参数
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Result<${javaname}Vo> queryVo(Map whereMap) {
		return dao.queryVo(whereMap);
	}

	/**
	 * 查询数据，并分页
	 * 
	 * @param ${cname}Condition
	 *            参数
	 * @return 查询结果
	 */
	@Override
	public List<${javaname}Vo> queryVoListLimit(${javaname}Condition ${cname}Condition) {
		return dao.queryVoListLimit(${cname}Condition);
	}

	/**
	 * 查询数据，并分页
	 * 
	 * @param whereMap
	 *            参数
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<${javaname}Vo> queryVoListLimit(Map whereMap) {
		return dao.queryVoListLimit(whereMap);
	}

	/**
	 * 通过主键查询出数据
	 * <#list pks as pk>
	 * @param ${pk.cname}
	 *            ${pk.remarks}</#list>
	 * @return 查询结果
	 */
	@Override
	public ${javaname}Vo queryOneVoByPk(<#list pks as pk><#if pk_index!=0>, </#if>${pk.typeName} ${pk.cname}</#list>) {
		return dao.queryOneVoByPk(<#list pks as pk><#if pk_index!=0>, </#if>${pk.cname}</#list>);
	}
	<#list columns as column><#if column.unique>

	/**
	 * 通过${column.remarks}查询出一个数据
	 * 
	 * @param ${column.cname}
	 *            ${column.remarks}
	 * @return 查询结果
	 */
	@Override
	public ${javaname}Vo queryOneVoBy${column.ucname}(${column.typeName} ${column.cname}) {
		return dao.queryOneVoBy${column.ucname}(${column.cname});
	}
	</#if></#list>
}
