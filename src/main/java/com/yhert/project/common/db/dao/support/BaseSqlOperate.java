package com.yhert.project.common.db.dao.support;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yhert.project.common.db.operate.DbExecution;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.db.Column;
import com.yhert.project.common.util.db.DBUtils;
import com.yhert.project.common.util.db.Table;

/**
 * 基础SQL操作
 * 
 * @author Ricardo Li 2017年3月27日 下午3:37:38
 *
 */
public abstract class BaseSqlOperate implements SqlOperate {

	/**
	 * 表名称到字段信息的映射表，Key小写
	 */
	private Map<String, List<Column>> tableName2ColumnsMap = new HashMap<>();

	/**
	 * 获得表单名称
	 * 
	 * @param tableName
	 *            表名称
	 * @return 类型
	 */
	public List<Column> getTables(String tableName) {
		List<Column> columns = tableName2ColumnsMap.get(tableName.toLowerCase());
		if (columns == null) {
			synchronized (tableName2ColumnsMap) {
				columns = tableName2ColumnsMap.get(tableName.toLowerCase());
				if (columns == null) {
					columns = DBUtils.getDbOperate(dbExecution).getColumn(tableName).get(0).getColumns();
					tableName2ColumnsMap.put(tableName, columns);
				}
			}
		}
		return columns;
	}

	/**
	 * 数据库连接池
	 */
	protected DbExecution dbExecution;

	public BaseSqlOperate(DbExecution dbExecution) {
		super();
		this.dbExecution = dbExecution;
	}

	/**
	 * 表单主键缓存数据
	 */
	protected static final Map<String, Set<String>> tablePrimaryKeyCacheMap = new HashMap<>();

	@Override
	public Set<String> getTablePrimaryKey(String tableName) {
		if (StringUtils.isEmpty(tableName)) {
			throw new IllegalArgumentException("表名称不能为空");
		}
		String[] tns = DBUtils.dealTableName(tableName);
		String cacheKey = null;
		if (StringUtils.isEmpty(tns[0])) {
			cacheKey = tns[1];
		} else {
			cacheKey = tns[0] + "." + tns[1];
		}
		Set<String> pkSet = tablePrimaryKeyCacheMap.get(cacheKey);
		if (null == pkSet) {
			synchronized (tablePrimaryKeyCacheMap) {
				pkSet = tablePrimaryKeyCacheMap.get(cacheKey);
				if (null == pkSet) {
					pkSet = new HashSet<>();
					List<Table> tables = DBUtils.getDbOperate(dbExecution).getColumn(tableName);
					Table table = tables.get(0);
					for (Column column : table.getColumns()) {
						if (column.isPk()) {
							pkSet.add(column.getColumnName().toLowerCase());
						}
					}
					tablePrimaryKeyCacheMap.put(cacheKey, pkSet);
				}
			}
		}
		return pkSet;
	}

	/**
	 * 格式化表名
	 * 
	 * @param tableName
	 *            表名
	 * @return 处理后表名
	 */
	protected abstract String formatTableName(String tableName);

	/**
	 * 格式化方案名称
	 * 
	 * @param schema
	 *            方案名称
	 * @return 处理后方案名称
	 */
	protected abstract String formateSchema(String schema);
}
