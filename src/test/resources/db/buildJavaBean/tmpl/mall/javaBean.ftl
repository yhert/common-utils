javaBean
@[spackagepath]/beans/@[javaname].java
false
package ${package}.beans;

import java.io.Serializable;
import javax.persistence.Table;
<#if hasDate>
import java.util.Date;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ${tableComment}
 * 
 * @author ${author} ${newdate}
 *
 */
@Table(name = "${tablename}"<#if schema?? && schema!=''>, schema = "${schema}"</#if><#if catalog?? && catalog!=''>, catalog = "${catalog}"</#if>)
public class ${javaname} implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	<#list columns as column>
	public static final String FIELD_${column.uname} = "${column.cname}";
	</#list>

	public ${javaname}() {
		super();
	}

	public ${javaname}(<#list pks as pk><#if pk_index!=0>, </#if>${pk.typeName} ${pk.cname}</#list>) {
		super();
		<#list pks as pk>
		this.${pk.cname} = ${pk.cname};
		</#list>
	}

	<#list columns as column>
	/**
	 * ${column.remarks}<#if column.pk>,[主键]</#if><#if column.index>,[索引]</#if><#if column.unique>,[唯一索引]</#if>
	 */
	<#if !column.nullable>@NotNull(message = "${column.cname}不能为空")
	</#if><#if column.needSize>@Size(max = ${column.columnSize?c},<#if (!column.nullable && column.typeName == 'String')> min=1,</#if> message = "${column.cname}长度<#if (!column.nullable && column.typeName == 'String')>必须大于0且</#if>不能超过${column.columnSize?c}个字符")
	</#if>private ${column.typeName} ${column.cname};
	</#list>

	<#list columns as column>
	public ${column.typeName} get${column.ucname}() {
		return ${column.cname};
	}

	public void set${column.ucname}(${column.typeName} ${column.cname}) {
		this.${column.cname} = ${column.cname};
	}

	</#list>
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		<#list columns as column>
		result = prime * result + ((${column.cname} == null) ? 0 : ${column.cname}.hashCode());
		</#list>
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		${javaname} other = (${javaname}) obj;
		<#list columns as column>
		if (${column.cname} == null) {
			if (other.${column.cname} != null)
				return false;
		} else if (!${column.cname}.equals(other.${column.cname}))
			return false;
		</#list>
		return true;
	}

	@Override
	public String toString() {
		return "${javaname} [<#list columns as column><#if column_index!=0>, </#if>${column.cname}=" + ${column.cname} + "</#list>]";
	}

}
