package com.yhert.project.common.db.dao.support;

import com.yhert.project.common.db.operate.DbExecution;
import com.yhert.project.common.excp.dao.DaoOperateException;
import com.yhert.project.common.util.StringUtils;

/**
 * mysql处理
 * 
 * @author Ricardo Li 2017年3月23日 下午1:21:51
 *
 */
public class PostgreSqlOperate extends BaseSqlOperate implements SqlOperate {

	PostgreSqlOperate(DbExecution dbExecution) {
		super(dbExecution);
	}

	@Override
	public String sqlAddPageLimit(String sql, int start, int limit) {
		return sql + " limit " + limit + " offset " + start;
	}

	@Override
	protected String formatTableName(String tableName) {
		return tableName.toLowerCase();
	}

	@Override
	protected String formateSchema(String schema) {
		return schema.toLowerCase();
	}

	@Override
	public String getTableName(String tableName) {
		if (StringUtils.isEmpty(tableName)) {
			throw new DaoOperateException("tableName不能为空，" + tableName);
		}
		if (tableName.indexOf("\"") == -1) {
			String quotes = "\"";
			String[] tns = StringUtils.split(tableName, ".");
			if (tns.length <= 1) {
				if (tns[0].indexOf(quotes) != -1) {
					return tableName;
				} else {
					return quotes + tns[0] + quotes;
				}
			} else {
				if (!StringUtils.isEmpty(tns[0])) {
					StringBuilder sb = new StringBuilder();
					if (tns[0].indexOf(quotes) != -1) {
						sb.append(tns[0]);
					} else {
						sb.append(quotes);
						sb.append(tns[0]);
						sb.append(quotes);
					}
					sb.append(".");
					if (tns[1].indexOf(quotes) != -1) {
						sb.append(tns[1]);
					} else {
						sb.append(quotes);
						sb.append(tns[1]);
						sb.append(quotes);
					}
					return sb.toString();
				} else {
					if (tns[1].indexOf(quotes) != -1) {
						return tableName;
					} else {
						return quotes + tns[1] + quotes;
					}
				}
			}
		} else {
			return tableName;
		}
	}

	@Override
	public String getColumnName(String columnName) {
		if (columnName.indexOf("\"") == -1) {
			return "\"" + columnName + "\"";
		} else {
			return columnName;
		}
	}

}
