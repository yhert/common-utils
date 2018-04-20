dao
@[spackagepath]/@[spackage]/dao/impl/@[javaname]DaoImpl.java
false
package ${package}.${spackage}.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ${package}.${spackage}.beans.${javaname};
import ${package}.${spackage}.beans.condition.${javaname}Condition;
import ${package}.${spackage}.beans.vo.${javaname}Vo;
import ${package}.${spackage}.dao.${javaname}Dao;
import com.yhert.project.oa.common.yfw.dao.AbstractDao;
import com.yhert.project.common.beans.Param;
import com.yhert.project.common.beans.Result;

/**
 * ${tableComment}持久化层
 * 
 * @author ${author} ${newdate}
 *
 */
@Repository("${cname}Dao")
public class ${javaname}DaoImpl extends AbstractDao<${javaname}> implements ${javaname}Dao {

	/**
	 * 查询数据
	 * 
	 * @param ${cname}Condition
	 *            参数
	 * @return 查询结果
	 */
	@Override
	public ${javaname}Vo queryOneVo(${javaname}Condition ${cname}Condition) {
		return queryOne(${cname}Condition, ${javaname}Vo.class);
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
		return queryOne(whereMap, ${javaname}Vo.class);
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
		return query(${cname}Condition, ${javaname}Vo.class);
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
		return query(whereMap, ${javaname}Vo.class);
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
		return queryListLimit(${cname}Condition, ${javaname}Vo.class);
	}

	/**
	 * 查询数据，并分页
	 * 
	 * @param whereMap
	 *            参数
	 * @return 查询结果
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<${javaname}Vo> queryVoListLimit(Map whereMap) {
		return queryListLimit(whereMap, ${javaname}Vo.class);
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
		return queryOne(Param.getParam(<#list pks as pk><#if pk_index!=0>, </#if>${javaname}Vo.FIELD_${pk.uname}, ${pk.cname}</#list>), ${javaname}Vo.class);
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
		return queryOne(Param.getParam(${javaname}.FIELD_${column.uname}, ${column.cname}), ${javaname}Vo.class);
	}
	</#if></#list>
}
