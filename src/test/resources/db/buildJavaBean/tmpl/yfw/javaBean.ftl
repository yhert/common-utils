javaBean
@[spackagepath]/@[spackage]/beans/@[javaname].java
false
package ${package}.${spackage}.beans;

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

import com.yhert.project.common.beans.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ${tableComment}
 * 
 * @author ${author} ${newdate}
 *
 */
@ApiModel(value = "${tableComment}", description = "${@description}")
@Table(name = "${tablename}"<#if schema?? && schema!=''>, schema = "${schema}"</#if><#if catalog?? && catalog!=''>, catalog = "${catalog}"</#if>)
public class ${javaname} extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "${tablename}";

	<#list columns as column>
	public static final String FIELD_${column.uname} = "${column.cname}";
	</#list>

	/**
     * 空构造函数
     */
	public ${javaname}() {
		super();
	}

	/**
     * 带主键的构造函数
     * <#list pks as pk>
     * @param ${pk.cname} ${pk.remarks}</#list>
     */
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
	 @ApiModelProperty(name = "${column.cname}", value = "${column.remarks}"<#if column.@example?? && column.@example!=''>, example = "${column.@example}"</#if>)
	<#if !column.nullable>@NotNull(message = "${column.cname}不能为空")
	</#if><#if column.needSize>@Size(max = ${column.columnSize?c},<#if (!column.nullable && column.typeName == 'String')> min=1,</#if> message = "${column.cname}长度<#if (!column.nullable && column.typeName == 'String')>必须大于0且</#if>不能超过${column.columnSize?c}个字符")
	</#if>private ${column.typeName} ${column.cname};
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
