javaBeanCondition
@[spackagepath]/@[spackage]/beans/condition/@[javaname]Condition.java
false
package ${package}.${spackage}.beans.condition;

<#if hasDate>
import java.util.Date;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>

import com.yhert.project.common.beans.AbstractCondition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ${tableComment}的查询参数
 * 
 * @author ${author} ${newdate}
 *
 */
 @ApiModel(value = "${tableComment}", description = "${@description}")
public class ${javaname}Condition extends AbstractCondition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	<#list columns as column>
	/**
	 * ${column.remarks}<#if column.pk>,[主键]</#if><#if column.index>,[索引]</#if><#if column.unique>,[唯一索引]</#if>
	 */
	@ApiModelProperty(name = "${column.cname}", value = "${column.remarks}"<#if column.@example?? && column.@example!=''>, example = "${column.@example}"</#if>)
	private ${column.typeName} ${column.cname};
	</#list>

	<#list columns as column>
	/**
	 * 获取${column.remarks}<#if column.pk>,[主键]</#if><#if column.index>,[索引]</#if><#if column.unique>,[唯一索引]</#if>
	 * 
	 * @return ${column.remarks}
	 */
	public ${column.typeName} get${column.ucname}() {
		return ${column.cname};
	}

	/**
	 * 设置${column.remarks}
	 * 
	 * @param ${column.cname} ${column.remarks}
	 */
	public void set${column.ucname}(${column.typeName} ${column.cname}) {
		this.${column.cname} = ${column.cname};
	}

	</#list>
}
