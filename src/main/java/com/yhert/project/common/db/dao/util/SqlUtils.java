package com.yhert.project.common.db.dao.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomUtils;

import com.yhert.project.common.db.dao.IDao;
import com.yhert.project.common.db.dao.interfaces.Dao;
import com.yhert.project.common.db.dao.support.SqlOperate;
import com.yhert.project.common.excp.dao.DaoOperateException;
import com.yhert.project.common.util.CommonFunUtils;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.db.Column;

/**
 * sql语句相关工具
 * 
 * @author ricardo
 *
 */
public class SqlUtils {
	private SqlUtils() {
	}

	/**
	 * sql参数信息头
	 */
	public static final String SQL_P_ = "sqlp_";
	/**
	 * sql等于
	 */
	public static final String SQL_P_EQ = "eq_";
	/**
	 * 小于
	 */
	public static final String SQL_P_LT = "lt_";
	/**
	 * 小于等于
	 */
	public static final String SQL_P_LE = "le_";
	/**
	 * 大于等于
	 */
	public static final String SQL_P_GE = "ge_";
	/**
	 * 大于
	 */
	public static final String SQL_P_GT = "gt_";
	/**
	 * 不等于
	 */
	public static final String SQL_P_NE = "ne_";
	/**
	 * 模糊匹配
	 */
	public static final String SQL_P_LIKE = "like_";
	/**
	 * 包含
	 */
	public static final String SQL_P_IN = "in_";
	/**
	 * 不包含
	 */
	public static final String SQL_P_NOT_IN = "notin_";

	/**
	 * SQL中表参数表达式匹配
	 */
	private static final Pattern SQL_TABLE_PARAM_PATTERN = Pattern.compile("(([^A-Za-z0-9_]{1}):[A-Za-z0-9_]+)");

	/**
	 * 合成插入的SQL语句
	 * 
	 * @param insertParams 对象
	 * @param tableName    表名
	 * @param sqlOperate   SQL操作工具
	 * @param args         参数
	 * @return SQL语句
	 */
	public static <E> StringBuilder getInsertSql(List<Map<String, ?>> insertParams, String tableName,
			SqlOperate sqlOperate, List<Object> args) {
		if (insertParams == null || insertParams.size() <= 0) {
			throw new IllegalArgumentException("插入数据不能够为空");
		}
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		sql.append(sqlOperate.getTableName(tableName));
		sql.append("(");
		StringBuilder values = new StringBuilder(" ");
		boolean firstFor = true;
		// 准备表字段信息
		List<Column> columns = sqlOperate.getTables(tableName);
		Set<String> names = new HashSet<>();
		for (Column column : columns) {
			names.add(column.getColumnName());
		}
		for (Map<?, ?> insertParam : insertParams) { // 遍历输入值
			if (firstFor) {
				values.append("values(");
			} else {
				values.append(", (");
			}
			boolean first = true;
			for (String name : names) { // 遍历字段获取输入值
				String uname = StringUtils.underscoreName(name);
				String cname = StringUtils.camelName(name);
				if (insertParam.containsKey(uname) || insertParam.containsKey(cname)) {
					if (first) {
						first = false;
					} else {
						if (firstFor) {
							sql.append(", ");
						}
						values.append(", ");
					}
					if (firstFor) {
						sql.append(sqlOperate.getColumnName(uname));
					}

					Object value = null;
					if (insertParam.containsKey(uname)) {
						value = insertParam.get(uname);
					} else if (insertParam.containsKey(cname)) {
						value = insertParam.get(cname);
					}
					values.append("?");
					args.add(value);
				}
			}
			values.append(")");
			firstFor = false;
		}
		sql.append(")");
		sql.append(values);
		return sql;
	}

	/**
	 * 合成插入的SQL语句
	 * 
	 * @param insertParam 对象
	 * @param tableName   表名
	 * @param sqlOperate  SQL操作工具
	 * @return SQL语句
	 */
	public static <E> StringBuilder getInsertSql(Map<String, ?> insertParam, String tableName, SqlOperate sqlOperate) {
		if (insertParam == null) {
			throw new IllegalArgumentException("insert参数不能够为null");
		}
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		sql.append(sqlOperate.getTableName(tableName));
		sql.append("(");
		StringBuilder values = new StringBuilder();
		values.append(" values(");
		boolean first = true;
		// 准备表字段信息
		List<Column> columns = sqlOperate.getTables(tableName);
		for (Column column : columns) {
			String name = column.getColumnName();
			String uname = StringUtils.underscoreName(name);
			String cname = StringUtils.camelName(name);
			String pName = null;
			if (insertParam.containsKey(uname)) {
				pName = uname;
			} else if (insertParam.containsKey(cname)) {
				pName = cname;
			}
			if (pName != null) {
				if (first) {
					first = false;
				} else {
					sql.append(", ");
					values.append(", ");
				}
				sql.append(sqlOperate.getColumnName(uname));
				values.append(":");
				values.append(pName);
			}
		}
		sql.append(")");
		values.append(")");
		sql.append(values);
		return sql;
	}

	/**
	 * 获得删除的SQL
	 * 
	 * @param param      条件
	 * @param tableName  表名
	 * @param sqlOperate SQL操作器
	 * @return SQL语句
	 */
	public static StringBuilder getDeleteSql(Map<String, ?> param, String tableName, SqlOperate sqlOperate) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append(sqlOperate.getTableName(tableName));
		sql.append(" where ");
		boolean first = true;
		// 准备表字段信息
		List<Column> columns = sqlOperate.getTables(tableName);
		for (Column column : columns) {
			if (column.isPk()) {
				String name = column.getColumnName();
				String uname = StringUtils.underscoreName(name);
				String cname = StringUtils.camelName(name);
				String pName = null;
				if (param.containsKey(uname)) {
					pName = uname;
				} else if (param.containsKey(cname)) {
					pName = cname;
				}
				if (pName == null) {
					throw new DaoOperateException("删除数据必须对应所有数据库主键，批量删除请执行使用SQL语句进行操作");
				} else {
					if (first) {
						first = false;
					} else {
						sql.append(" and ");
					}
					sql.append(sqlOperate.getColumnName(uname));
					sql.append("=");
					sql.append(":");
					sql.append(pName);
				}
			}
		}
		return sql;
	}

	/**
	 * 获得更新的SQL
	 * 
	 * @param updateColumn 需要更新的字段
	 * @param whereParam   判断条件
	 * @param tableName    表名
	 * @param sqlOperate   sql器
	 * @param paramR       参数(返回SQL执行所需参数)
	 * @return SQL语句
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static StringBuilder getUpdateSql(Map<String, ?> updateColumn, Map<?, ?> whereParam, String tableName,
			SqlOperate sqlOperate, Map paramR) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ");
		sql.append(sqlOperate.getTableName(tableName));
		sql.append(" set ");
		StringBuilder whereSql = new StringBuilder();

		boolean first = true;
		boolean whereFirst = true;
		List<Column> columns = sqlOperate.getTables(tableName);
		for (Column column : columns) {
			String name = column.getColumnName();
			String uname = StringUtils.underscoreName(name);
			String cname = StringUtils.camelName(name);
			if (updateColumn.containsKey(uname) || updateColumn.containsKey(cname)) {
				if (first) {
					first = false;
				} else {
					sql.append(", ");
				}
				Object value = null;
				if (updateColumn.containsKey(uname)) {
					value = updateColumn.get(uname);
				} else {
					value = updateColumn.get(cname);
				}
				sql.append(sqlOperate.getColumnName(uname));
				sql.append("=");
				sql.append(":");
				sql.append(cname);
				paramR.put(cname, value);
			}
			if (whereParam.containsKey(name) || whereParam.containsKey(uname)) {
				if (whereFirst) {
					whereFirst = false;
				} else {
					sql.append(" and ");
				}
				Object value = null;
				if (whereParam.containsKey(uname)) {
					value = whereParam.get(uname);
				} else {
					value = whereParam.get(cname);
				}
				whereSql.append(sqlOperate.getColumnName(uname));
				whereSql.append("=");
				whereSql.append(":key_");
				whereSql.append(uname);
				paramR.put("key_" + uname, value);
			}
		}
		if (whereSql.length() > 0) {
			sql.append(" where ");
			sql.append(whereSql);
		}
		return sql;
	}

	/**
	 * 获得查询SQL
	 * 
	 * @param whereParam 条件
	 * @param tableName  表名称
	 * @param sqlOperate SQL操作器
	 * @return SQL语句
	 */
	@SuppressWarnings({ "rawtypes" })
	public static StringBuilder getQuerySql(Map whereParam, String tableName, SqlOperate sqlOperate) {
		return getQuerySql(whereParam, sqlOperate.getTableName(tableName), sqlOperate, sqlOperate.getTables(tableName));
	}

	/**
	 * 获得查询SQL
	 * 
	 * @param whereParam 条件
	 * @param tableName  表名称
	 * @param sqlOperate SQL操作器
	 * @param columns    字段信息
	 * @return SQL语句
	 */
	@SuppressWarnings({ "rawtypes" })
	public static StringBuilder getQuerySql(Map whereParam, String tableName, SqlOperate sqlOperate,
			List<Column> columns) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ");
		sql.append(tableName);
		StringBuilder s = getWhereParamSql(whereParam, sqlOperate, columns);
		if (s.length() > 0) {
			sql.append(" where ");
			sql.append(s);
		}
		setSortSql(whereParam, sql, sqlOperate);
		return sql;
	}

	/**
	 * 获得查询SQL
	 * 
	 * @param whereParam 条件
	 * @param sqlOperate SQL操作器
	 * @param columns    字段信息
	 * @return SQL语句
	 */
	@SuppressWarnings({ "rawtypes" })
	public static StringBuilder getWhereParamSql(Map whereParam, SqlOperate sqlOperate, List<Column> columns) {
		StringBuilder sql = new StringBuilder();
		// 准备表字段信息
		Set<String> propertis = new HashSet<>();
		for (Column column : columns) {
			propertis.add(column.getColumnName());
		}
		boolean first = true;
		for (Object keyObj : whereParam.keySet()) {
			first = getQueryColumnSql(whereParam, sqlOperate, sql, propertis, first, keyObj);
		}
		return sql;
	}

	@SuppressWarnings({ "rawtypes" })
	private static boolean getQueryColumnSql(Map whereParam, SqlOperate sqlOperate, StringBuilder sql,
			Set<String> propertis, boolean first, Object keyObj) {
		if (keyObj != null) {
			String key = keyObj.toString();
			String exp = "=";
			String operate = null;
			String column = key;
			if (column.startsWith(SQL_P_)) {
				column = column.substring(SQL_P_.length());
				if (column.startsWith(SQL_P_EQ)) {
					operate = SQL_P_EQ;
					exp = "=";
					column = column.substring(SQL_P_EQ.length());
				} else if (column.startsWith(SQL_P_GE)) {
					operate = SQL_P_GE;
					exp = ">=";
					column = column.substring(SQL_P_GE.length());
				} else if (column.startsWith(SQL_P_GT)) {
					operate = SQL_P_GT;
					exp = ">";
					column = column.substring(SQL_P_GT.length());
				} else if (column.startsWith(SQL_P_IN)) {
					operate = SQL_P_IN;
					exp = "in";
					column = column.substring(SQL_P_IN.length());
				} else if (column.startsWith(SQL_P_LE)) {
					operate = SQL_P_LE;
					exp = "<=";
					column = column.substring(SQL_P_LE.length());
				} else if (column.startsWith(SQL_P_LIKE)) {
					operate = SQL_P_LIKE;
					exp = "like";
					column = column.substring(SQL_P_LIKE.length());
				} else if (column.startsWith(SQL_P_LT)) {
					operate = SQL_P_LT;
					exp = "<";
					column = column.substring(SQL_P_LT.length());
				} else if (column.startsWith(SQL_P_NE)) {
					operate = SQL_P_NE;
					exp = "!=";
					column = column.substring(SQL_P_NE.length());
				} else if (column.startsWith(SQL_P_NOT_IN)) {
					operate = SQL_P_NOT_IN;
					exp = "not in";
					column = column.substring(SQL_P_NOT_IN.length());
				}
			}
			if (CommonFunUtils.isNe(column) || CommonFunUtils.isNe(exp)
					|| CommonFunUtils.isNe(whereParam.get(keyObj))) {
				return first;
			}
			String uname = StringUtils.underscoreName(column);
			String cname = StringUtils.camelName(column);
			if (propertis.contains(cname) || propertis.contains(uname)) {
				StringBuilder nsql = getQueryColumnValueSql(whereParam, key, sqlOperate.getColumnName(uname), exp,
						operate);

				// 追加到SQL上
				if (nsql.length() > 0) {
					if (first) {
						first = false;
						sql.append(" ");
					} else {
						sql.append(" and ");
					}
					sql.append(nsql);
				}
			}
		}
		return first;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static StringBuilder getQueryColumnValueSql(Map whereParam, String key, String columnName, String exp,
			String operate) {
		StringBuilder nsql = new StringBuilder();
		if (SQL_P_IN.equals(operate) || SQL_P_NOT_IN.equals(operate)) {
			// 分别处理in操作
			Object valueObj = whereParam.get(key);
			if (valueObj != null) {
				Class<?> valType = valueObj.getClass();
				if (valType.isArray()) {
					Object[] value = (Object[]) valueObj;
					if (value.length > 0) {
						nsql.append(columnName);
						nsql.append(" ");
						nsql.append(exp);
						nsql.append(" (");
						boolean firstVal = true;
						for (Object obj : value) {
							if (firstVal) {
								firstVal = false;
							} else {
								nsql.append(", ");
							}
							String keyStr = StringUtils.join(key, "_", exp, "_",
									String.valueOf(RandomUtils.nextInt() % 1000));
							nsql.append(" :");
							nsql.append(keyStr);
							whereParam.put(keyStr, obj);
						}
						nsql.append(")");
					} else {
						if (SQL_P_IN.equals(operate)) {
							nsql.append("1 != 1");
						}
					}
				} else if (valueObj instanceof Collection) {
					Collection value = (Collection) valueObj;
					if (value.size() > 0) {
						nsql.append(columnName);
						nsql.append(" ");
						nsql.append(exp);
						nsql.append(" (");
						boolean firstVal = true;
						for (Object obj : value) {
							if (firstVal) {
								firstVal = false;
							} else {
								nsql.append(", ");
							}
							String keyStr = StringUtils.join(key, "_", exp, "_",
									String.valueOf(RandomUtils.nextInt() % 1000));
							nsql.append(" :");
							nsql.append(keyStr);
							whereParam.put(keyStr, obj);
						}
						nsql.append(")");
					} else {
						if (SQL_P_IN.equals(operate)) {
							nsql.append("1 != 1");
						}
					}
				} else {
					nsql.append(columnName);
					nsql.append(" ");
					nsql.append(exp);
					nsql.append(" :");
					nsql.append(key);
				}
			}
		} else {
			nsql.append(columnName);
			nsql.append(" ");
			nsql.append(exp);
			nsql.append(" :");
			nsql.append(key);
		}
		return nsql;
	}

	/**
	 * 替换sql语句中的占位符数据
	 * 
	 * @param sql   SQL
	 * @param param 参数
	 * @param args  队列
	 * @return 结果
	 */
	public static String sqlSwitch(String sql, Map<?, ?> param, List<Object> args) {
		if (args == null) {
			args = new ArrayList<>();
		}
		Matcher matcher = SQL_TABLE_PARAM_PATTERN.matcher(sql);
		StringBuilder newSql = new StringBuilder();
		int lastEnd = 0;
		while (matcher.find()) {
			// 处理SQL
			int start = matcher.start() + 1;
			String word = matcher.group();
			newSql.append(sql.substring(lastEnd, start));
			lastEnd = matcher.end();
			newSql.append("?");
			// 处理参数
			word = word.substring(2, word.length());
			Object value = null;
			if (param.containsKey(word)) {
				value = param.get(word);
			} else {
				throw new DaoOperateException("执行SQL语句“" + sql + "”时，参数 “" + word + "” 未找到");
			}
			args.add(value);
		}
		newSql.append(sql.substring(lastEnd, sql.length()));
		return newSql.toString();
	}

	/**
	 * 设置SQL排序
	 * 
	 * @param whereParam 参数
	 * @param sql        SQL
	 */
	public static void setSortSql(Map<?, ?> whereParam, StringBuilder sql, SqlOperate sqlOperate) {
		Object sortColumnObj = whereParam.get(IDao.SORT);
		if (!CommonFunUtils.isNe(sortColumnObj)) {
			String sort = StringUtils.trim(sortColumnObj.toString());
			String[] sorts = sort.split(",");
			boolean first = true;
			for (String s : sorts) {
				s = StringUtils.trim(s.toLowerCase());
				String[] sts = s.split(" ");
				if (sts.length > 0) {
					String column = StringUtils.trim(sts[0]);
					if (!StringUtils.isEmpty(column)) {
						String type = null;
						if (sts.length > 1) {
							type = StringUtils.trim(sts[1]);
							if (StringUtils.isEmpty(type)) {
								type = Dao.VALUE_SORT_TTYPE_ASC;
							} else {
								if (!Dao.VALUE_SORT_TTYPE_ASC.equals(type) && !Dao.VALUE_SORT_TTYPE_DESC.equals(type)) {
									throw new DaoOperateException(
											"合成SQL语句“" + sql + "”时，排序方式“" + type + "”无法识别，请使用[asc|desc]");
								}
							}
						} else {
							type = Dao.VALUE_SORT_TTYPE_ASC;
						}
						if (first) {
							sql.append(" order by ");
							first = false;
						} else {
							sql.append(", ");
						}
						sql.append(sqlOperate.getColumnName(column));
						sql.append(" ");
						sql.append(sort);
						sql.append(" ");
					}
				}
			}
		}
	}
}
