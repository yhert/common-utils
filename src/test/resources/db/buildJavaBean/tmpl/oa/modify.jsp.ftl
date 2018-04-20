jsp
jsp/@[urlPrefix]@[url]/modify.jsp
true
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="y" uri="https://www.yhert.com/yfw/jsp/core"%>
<y:pageHtml title="修改${tableComment}信息">
	<y:panel title="">
		<y:form action="${urlPrefix}${url}/save">
			<y:toolbar>
				<y:button label="提交" type="submit" />
			</y:toolbar>
			<div class="row">
				<input type="hidden" name="${pk.cname}" value="${r'$'}{result.${pk.cname}}" />
				<#list columns as column>
				<#if !column.pk>
				<#if column.typeName == 'Date'>
				<y:dateselect label="${column.remarks}" name="${column.cname}" value="${r'$'}{result.${column.cname}}" dateFormat="yyyy-MM-dd HH:mm:ss" />
				</#if>
				<#if column.typeName == 'String'>
				<y:textedit label="${column.remarks}" name="${column.cname}" value="${r'$'}{result.${column.cname}}" />
				</#if>
				<#if column.typeName == 'Boolean'>
				<y:booledit label="${column.remarks}" name="${column.cname}" value="${r'$'}{result.${column.cname}}" truetext="是" falsetext="否" />
				</#if>
				<#if column.typeName == 'Integer'>
				<y:number label="${column.remarks}" name="${column.cname}" value="${r'$'}{result.${column.cname}}" />
				</#if>
				<#if column.typeName == 'Long'>
				<y:number label="${column.remarks}" name="${column.cname}" value="${r'$'}{result.${column.cname}}" />
				</#if>
				<#if column.typeName == 'Byte'>
				<y:number label="${column.remarks}" name="${column.cname}" value="${r'$'}{result.${column.cname}}" />
				</#if>
				<#if column.typeName == 'Short'>
				<y:number label="名${column.remarks}" name="${column.cname}" value="${r'$'}{result.${column.cname}}" />
				</#if>
				</#if>
				</#list>
			</div>
		</y:form>
	</y:panel>
</y:pageHtml>
