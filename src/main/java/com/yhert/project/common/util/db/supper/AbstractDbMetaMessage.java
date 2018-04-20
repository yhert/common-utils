package com.yhert.project.common.util.db.supper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yhert.project.common.db.operate.ConnectionCallBack;
import com.yhert.project.common.db.operate.DbExecution;
import com.yhert.project.common.excp.dao.DbMetadataMessageException;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.db.Column;
import com.yhert.project.common.util.db.DBUtils;
import com.yhert.project.common.util.db.DbMetadataMessage;
import com.yhert.project.common.util.db.Table;

/**
 * 数据库元数据操作
 * 
 * @author Ricardo Li 2017年9月6日 下午1:54:42
 *
 */
public abstract class AbstractDbMetaMessage implements DbMetadataMessage {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 数据库执行对象
	 */
	protected DbExecution dbExecution;

	public AbstractDbMetaMessage(DbExecution dbExecution) {
		super();
		this.dbExecution = dbExecution;
	}

	@Override
	public String getNowSchema() {
		String url = dbExecution.execution(new ConnectionCallBack<String>() {

			@Override
			public String doInConnection(Connection connection) throws SQLException {
				try {
					return connection.getMetaData().getURL();
				} catch (Exception e) {
					throw new DbMetadataMessageException("获取当前使用中的方案(数据库)名称失败", e);
				}
			}
		});
		int i = url.indexOf("?");
		if (i != -1) {
			url = url.substring(0, i);
		}
		i = url.lastIndexOf("/");
		String schema = null;
		if (i != -1) {
			schema = url.substring(i + 1);
		} else {
			throw new DbMetadataMessageException("获取当前使用中的方案(数据库)名称失败", url);
		}
		return schema;
	}

	/**
	 * 获得表嘻嘻你
	 * 
	 * @param tableName
	 *            表信息
	 * @return 表信息
	 */
	@Override
	public List<Table> getTableName(String tableName) {
		String[] sts = DBUtils.dealTableName(tableName);
		if (StringUtils.isEmpty(sts[0])) {
			sts[0] = getNowSchema();
		}
		if (StringUtils.isEmpty(sts[1])) {
			sts[1] = null;
		}
		return dbExecution.execution(new ConnectionCallBack<List<Table>>() {

			@Override
			public List<Table> doInConnection(Connection connection) throws SQLException {
				ResultSet rs = null;
				try {
					DatabaseMetaData metadata = connection.getMetaData();
					rs = metadata.getTables(sts[0], sts[0], sts[1], new String[] { "TABLE", "VIEW" });
					boolean has = false;
					if (rs.next()) {
						has = true;
					} else {
						rs.close();
						rs = metadata.getTables(null, sts[0], sts[1], new String[] { "TABLE", "VIEW" });
						if (rs.next()) {
							has = true;
						} else {
							rs.close();
							rs = metadata.getTables(sts[0], null, sts[1], new String[] { "TABLE", "VIEW" });
						}
					}
					List<Table> tables = new ArrayList<>();
					while (has || rs.next()) {
						has = false;
						Table table = new Table();
						table.setCatalog(rs.getString("TABLE_CAT"));
						String schema = rs.getString("TABLE_SCHEM");
						if (StringUtils.isEmpty(schema)) {
							schema = table.getCatalog();
						}
						table.setSchema(schema);
						table.setTableName(rs.getString("TABLE_NAME"));
						String type = rs.getString("TABLE_TYPE");
						if (!StringUtils.isEmpty(type)) {
							// 1：基础数据表，2、视图，3、系统表
							type = type.toUpperCase();
							if ("TABLE".equals(type)) {
								table.setType(1);
							} else if ("VIEW".equals(type)) {
								table.setType(2);
							} else if ("SYSTEM TABLE".equals(type)) {
								table.setType(3);
							}
						}
						table.setRemake(rs.getString("REMARKS"));
						tables.add(table);
					}
					return tables;
				} catch (SQLException e) {
					throw new DbMetadataMessageException("获取表信息失败[" + tableName + "]", e);
				} finally {
					DBUtils.close(null, null, rs);
				}
			}
		});
	}

	/**
	 * 获得表信息
	 * 
	 * @param tableName
	 *            表信息
	 * @return 表信息
	 */
	@Override
	public List<Table> getColumn(String tableName) {
		List<Table> tables = getTableName(tableName);
		Map<String, Table> tableMap = tables.stream().collect(Collectors.toMap((x) -> {
			return (x.getSchema() + "." + x.getTableName()).toLowerCase();
		}, x -> {
			return x;
		}));
		String[] sts = DBUtils.dealTableName(tableName);
		if (StringUtils.isEmpty(sts[0])) {
			sts[0] = getNowSchema();
		}
		if (StringUtils.isEmpty(sts[1])) {
			sts[1] = null;
		}

		return dbExecution.execution(new ConnectionCallBack<List<Table>>() {

			@Override
			public List<Table> doInConnection(Connection connection) throws SQLException {
				ResultSet rs = null;
				try {
					DatabaseMetaData metadata = connection.getMetaData();
					Set<String> pkSet = getPkSet(metadata, sts[0], sts[1]);
					Set<String> uiSet = getUQSet(metadata, sts[0], sts[1], pkSet);
					Set<String> idxSet = getIdxSet(metadata, sts[0], sts[1], pkSet);
					rs = metadata.getColumns(sts[0], sts[0], sts[1], "%");
					boolean has = false;
					if (rs.next()) {
						has = true;
					} else {
						rs.close();
						rs = metadata.getColumns(sts[0], null, sts[1], "%");
						if (rs.next()) {
							has = true;
						} else {
							rs.close();
							rs = metadata.getColumns(null, sts[0], sts[1], "%");
						}
					}
					while (has || rs.next()) {
						has = false;
						String schema = rs.getString("TABLE_SCHEM");
						if (StringUtils.isEmpty(schema)) {
							schema = rs.getString("TABLE_CAT");
						}
						String key = schema + "." + rs.getString("TABLE_NAME");
						key = key.toLowerCase();
						Table table = tableMap.get(key);
						if (table != null) {
							List<Column> columns = table.getColumns();
							if (columns == null) {
								columns = new ArrayList<>();
								table.setColumns(columns);
							}
							Column column = new Column();

							column.setColumnName(rs.getString("COLUMN_NAME"));
							column.setColumnSize(rs.getInt("COLUMN_SIZE"));
							column.setNullable(rs.getInt("NULLABLE") != 0);
							column.setColumnDef(rs.getString("COLUMN_DEF"));
							column.setRemarks(rs.getString("REMARKS"));
							column.setTypeName(rs.getString("TYPE_NAME").toLowerCase());
							putColumnType(column);

							String idxKey = key + "." + column.getColumnName();
							idxKey = idxKey.toLowerCase();
							column.setPk(pkSet.contains(idxKey));
							column.setUnique(uiSet.contains(idxKey));
							column.setIndex(idxSet.contains(idxKey));

							columns.add(column);
						}
					}
					return tables;
				} catch (SQLException e) {
					throw new DbMetadataMessageException("获取表信息失败[" + tableName + "]", e);
				} finally {
					DBUtils.close(null, null, rs);
				}
			}
		});
	}

	/**
	 * 获取非唯一索引信息
	 * 
	 * @param metadata
	 *            元数据
	 * @param sc
	 *            方案
	 * @param tn
	 *            数据表名称
	 * @return 主键
	 * @param pkSet
	 *            主键信息
	 * @throws SQLException
	 *             获取异常F
	 */
	private static Set<String> getIdxSet(DatabaseMetaData metadata, String sc, String tn, Set<String> pkSet) throws SQLException {
		ResultSet rs = null;
		try {
			rs = metadata.getIndexInfo(sc, sc, tn, false, true);
			boolean has = false;
			if (rs.next()) {
				has = true;
			} else {
				rs.close();
				rs = metadata.getIndexInfo(sc, null, tn, false, true);
				if (rs.next()) {
					has = true;
				} else {
					rs.close();
					rs = metadata.getIndexInfo(null, sc, tn, false, true);
				}
			}
			Set<String> idxSet = new HashSet<>();
			while (has || rs.next()) {
				has = false;
				String columnName = rs.getString("COLUMN_NAME");
				String schema = rs.getString("TABLE_SCHEM");
				if (StringUtils.isEmpty(schema)) {
					schema = rs.getString("TABLE_CAT");
				}
				String key = schema + "." + rs.getString("TABLE_NAME") + "." + columnName;
				key = key.toLowerCase();
				if (!pkSet.contains(key)) {
					if (rs.getBoolean("NON_UNIQUE")) {
						idxSet.add(columnName);
					}
				}
			}
			return idxSet;
		} finally {
			DBUtils.close(null, null, rs);
		}
	}

	/**
	 * 获取唯一索引信息
	 * 
	 * @param metadata
	 *            元数据
	 * @param sc
	 *            方案
	 * @param tn
	 *            数据表名称
	 * @return 主键
	 * @param pkSet
	 *            主键信息
	 * @throws SQLException
	 *             获取异常F
	 */
	private static Set<String> getUQSet(DatabaseMetaData metadata, String sc, String tn, Set<String> pkSet) throws SQLException {
		ResultSet rs = null;
		try {
			rs = metadata.getIndexInfo(sc, sc, tn, true, true);
			boolean has = false;
			if (rs.next()) {
				has = true;
			} else {
				rs.close();
				rs = metadata.getIndexInfo(sc, null, tn, true, true);
				if (rs.next()) {
					has = true;
				} else {
					rs.close();
					rs = metadata.getIndexInfo(null, sc, tn, true, true);
				}
			}
			Set<String> uiSet = new HashSet<>();
			while (has || rs.next()) {
				has = false;
				String columnName = rs.getString("COLUMN_NAME");
				String schema = rs.getString("TABLE_SCHEM");
				if (StringUtils.isEmpty(schema)) {
					schema = rs.getString("TABLE_CAT");
				}
				String key = schema + "." + rs.getString("TABLE_NAME") + "." + columnName;
				key = key.toLowerCase();
				if (!pkSet.contains(key)) {
					if (rs.getBoolean("NON_UNIQUE")) {
						uiSet.add(columnName);
					}
				}
			}
			return uiSet;
		} finally {
			DBUtils.close(null, null, rs);
		}
	}

	/**
	 * 获取主键信息
	 * 
	 * @param metadata
	 *            元数据
	 * @param sc
	 *            方案
	 * @param tn
	 *            数据表名称
	 * @return 主键
	 * @throws SQLException
	 *             获取异常F
	 */
	private static Set<String> getPkSet(DatabaseMetaData metadata, String sc, String tn) throws SQLException {
		ResultSet rs = null;
		try {
			rs = metadata.getPrimaryKeys(sc, sc, tn);
			boolean has = false;
			if (rs.next()) {
				has = true;
			} else {
				rs.close();
				rs = metadata.getPrimaryKeys(sc, null, tn);
				if (rs.next()) {
					has = true;
				} else {
					rs.close();
					rs = metadata.getPrimaryKeys(null, sc, tn);
				}
			}
			Set<String> pkSet = new HashSet<>();
			while (has || rs.next()) {
				has = false;
				String columnName = rs.getString("COLUMN_NAME");
				String schema = rs.getString("TABLE_SCHEM");
				if (StringUtils.isEmpty(schema)) {
					schema = rs.getString("TABLE_CAT");
				}
				String key = schema + "." + rs.getString("TABLE_NAME") + "." + columnName;
				key = key.toLowerCase();
				pkSet.add(key);
			}
			return pkSet;
		} finally {
			DBUtils.close(null, null, rs);
		}
	}

	/**
	 * 设置类型
	 * 
	 * @param column
	 *            字段
	 */
	protected void putColumnType(Column column) {
		// -7 BIT Boolean
		// 12 VARCHAR String
		// 4 INT Integer
		// 93 DATATIME Date
		// -1 MEDIUMTEXT Long
		// -1 TEXT String
		String type = column.getTypeName();
		if (StringUtils.isEmpty(type)) {
			return;
		}
		type = type.toLowerCase();
		if ("varchar".equals(type)) {
			column.setType(String.class);
		} else if ("char".equals(type)) {
			column.setType(String.class);
		} else if ("blob".equals(type)) {
			column.setType(byte[].class);
		} else if ("text".equals(type)) {
			column.setType(String.class);
		} else if ("longblob".equals(type)) {
			column.setType(String.class);
		} else if ("date".equals(type)) {
			column.setType(Date.class);
		} else if ("datetime".equals(type)) {
			column.setType(Date.class);
		} else if ("timestamp".equals(type)) {
			column.setType(Date.class);
		} else if ("tinyint".equals(type)) {
			column.setType(Boolean.class);
		} else if ("bit".equals(type)) {
			column.setType(Boolean.class);
		} else if ("int".equals(type)) {
			column.setType(Integer.class);
		} else if ("mediumtext".equals(type)) {
			column.setType(Long.class);
		} else if ("float".equals(type)) {
			column.setType(Float.class);
		} else if ("double".equals(type)) {
			column.setType(Double.class);
		} else if ("decimal".equals(type)) {
			column.setType(BigDecimal.class);
		} else if ("binary".equals(type)) {
			column.setType(byte[].class);
		} else if ("varbinary".equals(type)) {
			column.setType(byte[].class);
		} else {
			log.warn("无法解析的字段类型[{}]", column);
		}
	}
}
