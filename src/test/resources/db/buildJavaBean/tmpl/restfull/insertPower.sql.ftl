insertPowerSql
授权Sql/@[javaname]InsertPower.sql
true
-- 增加${tableComment}的权限列表Sql
insert into base_power(`id`, `code`, `name`, `power_url`, `parent_id`, `munt`, `icon`, `url_path`, `explain`, `order_num`, `product`) values('${random.uuid1}', 'POWER_${ucUrlPrefix}${ucTableName}_QUERY', '${tableComment}管理', '^/${urlPrefix}${url}/(list|query)?$', <#if param.param_buildInsertPowerSql_parentPowerId??>${param.param_buildInsertPowerSql_parentPowerId}</#if>, '1', '', '${urlPrefix}${url}/list', '${tableComment}管理列表', '2000', '${param.param_buildInsertPowerSql_project}');
insert into base_power(`id`, `code`, `name`, `power_url`, `parent_id`, `munt`, `icon`, `url_path`, `explain`, `order_num`, `product`) values('${random.uuid2}', 'POWER_${ucUrlPrefix}${ucTableName}_CUD', '${tableComment}增删改操作', '^/${urlPrefix}${url}/[\\w\\.\\_]*$', '${random.uuid1}', '0', '', '', '进行${tableComment}的增加修改删除管理', '3000', '${param.param_buildInsertPowerSql_project}');
