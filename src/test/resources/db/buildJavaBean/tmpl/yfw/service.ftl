service
@[spackagepath]/@[spackage]/service/@[javaname]Service.java
false
package ${package}.${spackage}.service;

import java.util.List;
import java.util.Map;

import ${package}.${spackage}.beans.${javaname};
import com.yhert.project.common.service.BaseService;
import ${package}.${spackage}.beans.condition.${javaname}Condition;
import ${package}.${spackage}.beans.vo.${javaname}Vo;
import com.yhert.project.common.beans.Result;


/**
 * ${tableComment}服务层
 * 
 * @author ${author} ${newdate}
 *
 */
public interface ${javaname}Service extends BaseService<${javaname}> {
	/**
	 * 查询出一个数据
	 * 
	 * @param ${cname}Condition
	 *            参数
	 * @return 查询结果
	 */
	${javaname}Vo queryOneVo(${javaname}Condition ${cname}Condition);

	/**
	 * 查询出一个数据
	 * 
	 * @param whereMap
	 *            参数
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	${javaname}Vo queryOneVo(Map whereMap);

	/**
	 * 查询数据
	 * 
	 * @param ${cname}Condition
	 *            参数
	 * @return 查询结果
	 */
	Result<${javaname}Vo> queryVo(${javaname}Condition ${cname}Condition);

	/**
	 * 查询数据
	 * 
	 * @param whereMap
	 *            参数
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	Result<${javaname}Vo> queryVo(Map whereMap);

	/**
	 * 查询数据，并分页
	 * 
	 * @param ${cname}Condition
	 *            参数
	 * @return 查询结果
	 */
	List<${javaname}Vo> queryVoListLimit(${javaname}Condition ${cname}Condition);

	/**
	 * 查询数据，并分页
	 * 
	 * @param whereMap
	 *            参数
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	List<${javaname}Vo> queryVoListLimit(Map whereMap);

	/**
	 * 通过主键查询出数据
	 * <#list pks as pk>
	 * @param ${pk.cname}
	 *            ${pk.remarks}</#list>
	 * @return 查询结果
	 */
	${javaname}Vo queryOneVoByPk(<#list pks as pk><#if pk_index!=0>, </#if>${pk.typeName} ${pk.cname}</#list>);
	<#list columns as column><#if column.unique>

	/**
	 * 通过${column.remarks}查询出一个数据
	 * 
	 * @param ${column.cname}
	 *            ${column.remarks}
	 * @return 查询结果
	 */
	${javaname}Vo queryOneVoBy${column.ucname}(${column.typeName} ${column.cname});
	</#if></#list>
}
