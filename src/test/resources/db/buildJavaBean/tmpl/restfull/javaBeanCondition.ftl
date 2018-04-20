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

/**
 * ${tableComment}的查询参数
 * 
 * @author ${author} ${newdate}
 *
 */
public class ${javaname}Condition extends AbstractCondition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	<#list columns as column>
	/**
	 * ${column.remarks}<#if column.pk>,[主键]</#if><#if column.index>,[索引]</#if><#if column.unique>,[唯一索引]</#if>
	 */
	private ${column.typeName} ${column.cname};
	</#list>

	<#list columns as column>
	public ${column.typeName} get${column.ucname}() {
		return ${column.cname};
	}

	public void set${column.ucname}(${column.typeName} ${column.cname}) {
		this.${column.cname} = ${column.cname};
	}

	</#list>
}
