jsp
jsp/@[urlPrefix]@[url]/list.jsp
true
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="y" uri="https://www.yhert.com/yfw/jsp/core"%>
<y:pageHtml title="${tableComment}管理">
	<y:panel title="${tableComment}管理">
		<y:hasAllRoleAndPower roleAndPower="POWER_${ucUrlPrefix}${ucTableName}_CUD">
			<y:toolbar>
				<y:button label="新增" type="add" click="openModel('${urlPrefix}${url}/modify', '新增${tableComment}')" />
	    	</y:toolbar>
	    </y:hasAllRoleAndPower>
		<y:datatable id="datatable" class="table table-bordered" action="${urlPrefix}${url}/query" pk="${pk.cname}" autoLoad="false">
			<#list columns as column>
			<#if !column.pk>
			<y:datatable.column label="${column.remarks}" code="${column.cname}"<#if column.typeName == 'Date'> type="date"</#if><#if column.typeName == 'Integer'> type="number"</#if><#if column.typeName == 'Boolean'> type="bool" truetext="是" falsetext="否"</#if> sort="true" />
			</#if>
			</#list>
			<y:hasAllRoleAndPower roleAndPower="POWER_${ucUrlPrefix}${ucTableName}_CUD">
				<y:datatable.column label="操作" code="opr">
					<a href="@{Common.getUrl('${urlPrefix}${url}/modify?id=@[id]')}" onclick="openModel('${urlPrefix}${url}/modify?id=@[id]', '修改[@[name]]');return false;">修改</a>	|
					<a href="@{Common.getUrl('${urlPrefix}${url}/delete?id=@[id]')}" onclick="remove('@[id]', '@[name]');return false;">删除</a>
				</y:datatable.column>
			</y:hasAllRoleAndPower>
		</y:datatable>
	</y:panel>
</y:pageHtml>
<!-- WebSocket End -->
<script type="text/javascript">
	function query() {
		$("#datatable").refresh();
	}
	
	/* function admin_enter_rowFun(column, data, full, dataTypeDeal, that) {
		if (column.code == 'opr') {
			var modifyUrl = Common.getUrl('${urlPrefix}${url}/modify?id=' + full['id']);
			var deleteUrl = Common.getUrl('${urlPrefix}${url}/delete?id=' + full['id']);
			var html = '';
			if (Common.hasAllRoleAndPower('POWER_${ucUrlPrefix}${ucTableName}_CUD')) {
				html += "<a href='" + modifyUrl + "' onclick='openModel(\"" + full['id'] + "\", \"修改" + full['name'] + "[" + full['code'] + "]\");return false;'>修改</a>";
				html += "|";
				html += "<a href='" + deleteUrl + "' onclick='remove(\"" + full['id'] + "\", \"" + full['name'] + "[" + full['code'] + "]\");return false;'>删除</a>";
			}
			return html;
		}
		return dataTypeDeal(column, data, full);
	} */
	
	function remove(id, title) {
		Common.confirm("警告", "确认要删除${tableComment}" + title + "吗？", function() {
			Common.ajax("${urlPrefix}${url}/delete", "POST", {
				id: id
			}, function(r) {
				$.unmask();
				if (r) {
					query();
					Common.notify("操作成功");
				}
			}, function() {
				$.unmask();
			});
		})
	}
	
	function openModel(url, title) {
		Common.model({
			url : url,
			title : title,
			callback: function(msg) {
				if (msg) {
					query();
				}
			}
		});
	}
	$(function() {
		query();
	});
</script>
