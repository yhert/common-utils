package com.yhert.project.common.util.db;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;

import com.yhert.project.common.beans.Param;
import com.yhert.project.common.db.dao.util.SqlUtils;
import com.yhert.project.common.db.operate.ConnectionCallBack;
import com.yhert.project.common.db.operate.DbExecution;
import com.yhert.project.common.excp.dao.DaoOperateException;
import com.yhert.project.common.util.BeanUtils;
import com.yhert.project.common.util.CommonFunUtils;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.db.supper.MySqlDbMetadataMessage;
import com.yhert.project.common.util.db.supper.OtherDbMetadataMessage;

/**
 * 数据库工具
 * 
 * @author Ricardo Li 2017年6月16日 下午10:04:39
 *
 */
public class DBUtils {
	/**
	 * 自动选择消息源
	 */
	public static boolean AUTO_SELECT_METADATA_SOURCE = true;

	/**
	 * 获得表名称数据库
	 * 
	 * @param type 类名
	 * @return 表名称
	 */
	public static String getTableName(Class<?> type) {
		Class<?> typeC = getEntryType(type);
		Table table = typeC.getAnnotation(Table.class);
		String tableName = StringUtils.underscoreName(typeC.getSimpleName());
		if (table != null) {
			String schema = table.schema();
			String name = table.name();
			if (!StringUtils.isEmpty(name)) {
				tableName = name;
			}
			if (!StringUtils.isEmpty(schema)) {
				tableName = schema + "." + tableName;
			}
		}
		return tableName;
	}

	/**
	 * 获得实体名称
	 * 
	 * @param type 类型
	 * @return 结果
	 */
	public static Class<?> getEntryType(Class<?> type) {
		if (type == null) {
			throw new DaoOperateException("传递的Javabean类型 为null");
		}
		if (type.isAssignableFrom(Map.class)) {
			throw new DaoOperateException("传递的Javabean类型 为Map，无法获取到实体名称");
		}
		Table table = type.getAnnotation(Table.class);
		Class<?> superType = type.getSuperclass();
		Class<?> tableType = type;
		while (table == null && superType != null) {
			table = superType.getAnnotation(Table.class);
			tableType = superType;
			superType = superType.getSuperclass();
		}
		if (table != null) {
			return tableType;
		} else {
			return type;
		}
	}

	/**
	 * 获得实体名称
	 * 
	 * @param type 类型
	 * @return 结果
	 */
	public static String getEntryName(Class<?> type) {
		return getEntryType(type).getSimpleName();
	}

	/**
	 * 获得数据库类型
	 * 
	 * @param dbExecution 数据库执行器
	 * @return 数据库类型
	 */
	public static String getDbType(DbExecution dbExecution) {
		return dbExecution.execution(new ConnectionCallBack<String>() {
			@Override
			public String doInConnection(Connection connection) throws SQLException {
				return connection.getMetaData().getDatabaseProductName();
			}
		});
	}

	/**
	 * 关闭链接
	 * 
	 * @param connection 链接
	 */
	public static void close(Connection connection) {
		close(connection, null, null);
	}

	/**
	 * 关闭链接
	 * 
	 * @param connection 链接
	 * @param ps         参数
	 * @param rs         结果
	 */
	public static void close(Connection connection, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 转换器
	 * 
	 * @param param 参数
	 * @param types 类型模板
	 * @return 结果
	 */
	public static Param switchParam(Map<String, String> param, Class<?>[] types) {
		Param result = Param.getParam();
		Map<String, Class<?>> name2Type = new HashMap<>();
		for (Class<?> type : types) {
			if (type != null && !Map.class.isAssignableFrom(type)) {
				for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(type)) {
					name2Type.put(pd.getName(), pd.getPropertyType());
				}
			}
		}
		// 依次遍历对象属性
		for (Entry<String, String> entry : param.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			Class<?> type = name2Type.get(key);
			if (type != null) {
				Object valObj = BeanUtils.switchType(value, type);
				if (valObj == null) {
					result.put(key, value);
				} else {
					result.put(key, valObj);
				}
			} else {
				String exp = null;
				String column = key;
				if (column.startsWith(SqlUtils.SQL_P_)) {
					column = column.substring(SqlUtils.SQL_P_.length());
					if (column.startsWith(SqlUtils.SQL_P_EQ)) {
						exp = "=";
						column = column.substring(SqlUtils.SQL_P_EQ.length());
					} else if (column.startsWith(SqlUtils.SQL_P_GE)) {
						exp = ">=";
						column = column.substring(SqlUtils.SQL_P_GE.length());
					} else if (column.startsWith(SqlUtils.SQL_P_GT)) {
						exp = ">";
						column = column.substring(SqlUtils.SQL_P_GT.length());
					} else if (column.startsWith(SqlUtils.SQL_P_IN)) {
						exp = "in";
						column = column.substring(SqlUtils.SQL_P_IN.length());
					} else if (column.startsWith(SqlUtils.SQL_P_LE)) {
						exp = "<=";
						column = column.substring(SqlUtils.SQL_P_LE.length());
					} else if (column.startsWith(SqlUtils.SQL_P_LIKE)) {
						exp = "like";
						column = column.substring(SqlUtils.SQL_P_LIKE.length());
					} else if (column.startsWith(SqlUtils.SQL_P_LT)) {
						exp = "<";
						column = column.substring(SqlUtils.SQL_P_LT.length());
					} else if (column.startsWith(SqlUtils.SQL_P_NE)) {
						exp = "!=";
						column = column.substring(SqlUtils.SQL_P_NE.length());
					} else if (column.startsWith(SqlUtils.SQL_P_NOT_IN)) {
						exp = "not in";
						column = column.substring(SqlUtils.SQL_P_NOT_IN.length());
					}
				}
				type = name2Type.get(column);
				if (type == null) {// 未找到就直接原始类型
					result.put(key, value);
				} else {
					if (!StringUtils.isEmpty(exp)) {
						if (exp.equals("in") || exp.equals("not in")) {
							type = Array.newInstance(type, 0).getClass();
						}
					}
					Object valObj = BeanUtils.switchType(value, type);
					result.put(key, valObj);
				}
			}
		}
		return result;
	}

	/**
	 * 获得数据库元信息操作（使用完成后记得关闭数据库连接）
	 * 
	 * @param dbExecution 执行对象
	 * @return 元数据信息
	 */
	public static DbMetadataMessage getDbOperate(DbExecution dbExecution) {
		if (!AUTO_SELECT_METADATA_SOURCE) {
			return new OtherDbMetadataMessage(dbExecution);
		}
		String dbType = getDbType(dbExecution).toLowerCase();
		DbMetadataMessage message = null;
		switch (dbType) {
		case "mysql":
			message = new MySqlDbMetadataMessage(dbExecution);
			break;
		default:
			message = new OtherDbMetadataMessage(dbExecution);
			// throw new DbMetadataMessageException("数据库不被支持" + dbType);
		}
		// message = new OtherDbMetadataMessage(connection);
		return message;
	}

	/**
	 * 处理表名称
	 * 
	 * @param tableName 表名称
	 * @return 名称
	 */
	public static String[] dealTableName(String tableName) {
		if (StringUtils.isEmpty(tableName)) {
			return new String[] { "", "%" };
		} else {
			tableName = tableName.toLowerCase();
			tableName = tableName.replaceAll("[^a-zA-Z0-9%_\\.]", "");
			String sname = "";
			String tname = "";
			if (tableName.endsWith(".")) {
				tableName += "%";
			}
			if (tableName.startsWith(".")) {
				tableName = tableName.substring(1);
			}
			String[] tns = tableName.split("\\.");
			if (tns.length > 0) {
				if (tns.length > 1) {
					if (!StringUtils.isEmpty(tns[0])) {
						sname = tns[0];
					}
					tname = tns[1];
				} else {
					tname = tns[0];
				}
			}
			String[] sts = new String[2];
			sts[0] = sname;
			sts[1] = tname;
			return sts;
		}
	}

	/**
	 * 设置参数
	 * 
	 * @param value 值
	 * @param nsql  拼接的SQL
	 * @param sql   sql
	 */
	public static void setParameter(Object value, String nsql, StringBuilder sql) {
		if (!CommonFunUtils.isNe(value)) {
			sql.append(" ");
			sql.append(nsql);
		}
	}

	/**
	 * 设置参数
	 * 
	 * @param param map对象
	 * @param key   键
	 * @param nsql  拼接的SQL
	 * @param sql   sql
	 */
	@SuppressWarnings("rawtypes")
	public static void setParameter(Map param, String key, String nsql, StringBuilder sql) {
		if (!CommonFunUtils.isNe(param.get(key))) {
			sql.append(" ");
			sql.append(nsql);
		}
	}
}
