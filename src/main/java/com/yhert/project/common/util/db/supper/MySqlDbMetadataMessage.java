package com.yhert.project.common.util.db.supper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.yhert.project.common.db.operate.ConnectionCallBack;
import com.yhert.project.common.db.operate.DbExecution;
import com.yhert.project.common.excp.dao.DbMetadataMessageException;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.db.Column;
import com.yhert.project.common.util.db.DBUtils;
import com.yhert.project.common.util.db.DbMetadataMessage;
import com.yhert.project.common.util.db.Table;

/**
 * mysql操作
 * 
 * @author Ricardo Li 2017年9月6日 下午2:32:12
 *
 */
public class MySqlDbMetadataMessage extends AbstractDbMetaMessage implements DbMetadataMessage {

	/**
	 * 表信息查询Sql
	 */
	private static final String tableSql = "SELECT * FROM `information_schema`.`tables` where table_type != 'SYSTEM VIEW'";
	/**
	 * 表字段查询Sql
	 */
	private static final String columnSql = "SELECT * FROM `information_schema`.`columns` where 1=1";

	public MySqlDbMetadataMessage(DbExecution dbExecution) {
		super(dbExecution);
	}

	/**
	 * 获得表元数据信息
	 * 
	 * @param tableName
	 *            表信息
	 * @return 表信息
	 */
	@Override
	public List<Table> getTableName(String tableName) {
		String[] sts = DBUtils.dealTableName(tableName);
		String newSchema = getNowSchema();
		return dbExecution.execution(new ConnectionCallBack<List<Table>>() {

			@Override
			public List<Table> doInConnection(Connection connection) throws SQLException {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					StringBuilder sql = new StringBuilder(tableSql);
					String schema = sts[0];
					if (StringUtils.isEmpty(schema)) {
						schema = newSchema;
					}
					if (!"%".equals(schema)) {
						sql.append(" and lower(table_schema) like '" + schema + "'");
					}
					String tableStr = sts[1];
					if (!"%".equals(tableStr)) {
						sql.append(" and lower(table_name) like '" + tableStr + "'");
					}
					ps = connection.prepareStatement(sql.toString());
					rs = ps.executeQuery();
					List<Table> tables = new ArrayList<>();
					while (rs.next()) {
						Table table = new Table();
						table.setCatalog(rs.getString("table_catalog"));
						if ("def".equals(table.getCatalog())) {
							table.setCatalog(null);
						}
						table.setSchema(rs.getString("table_schema"));
						table.setTableName(rs.getString("table_name"));
						table.setCreateTime(rs.getDate("create_time"));
						table.setRemake(rs.getString("table_comment"));
						String tableType = rs.getString("table_type");
						if (!StringUtils.isEmpty(tableType)) {
							tableType = tableType.toLowerCase();
							if ("base table".equals(tableType)) {
								table.setType(1);
							} else if ("view".equals(tableType)) {
								table.setType(2);
							}
						}
						tables.add(table);
					}
					return tables;
				} catch (SQLException e) {
					throw new DbMetadataMessageException("获取表信息失败[" + tableName + "]", e);
				} finally {
					DBUtils.close(null, ps, rs);
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

		return dbExecution.execution(new ConnectionCallBack<List<Table>>() {

			@Override
			public List<Table> doInConnection(Connection connection) throws SQLException {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					String[] sts = DBUtils.dealTableName(tableName);
					StringBuilder sql = new StringBuilder(columnSql);
					String schema = sts[0];
					if (StringUtils.isEmpty(schema)) {
						schema = getNowSchema();
					}
					if (!"%".equals(schema)) {
						sql.append(" and lower(table_schema) like '" + schema + "'");
					}
					String tableStr = sts[1];
					if (!"%".equals(tableStr)) {
						sql.append(" and lower(table_name) like '" + tableStr + "'");
					}
					ps = connection.prepareStatement(sql.toString());
					rs = ps.executeQuery();
					while (rs.next()) {
						String key = rs.getString("table_schema") + "." + rs.getString("table_name");
						key = key.toLowerCase();
						Table table = tableMap.get(key);
						if (table != null) {
							List<Column> columns = table.getColumns();
							if (columns == null) {
								columns = new ArrayList<>();
								table.setColumns(columns);
							}
							Column column = new Column();
							column.setColumnDef(rs.getString("column_default"));
							column.setNullable("yes".equals(rs.getString("is_nullable").toLowerCase()));
							column.setColumnName(rs.getString("column_name"));
							column.setColumnSize(rs.getInt("character_maximum_length"));
							column.setTypeName(rs.getString("data_type"));
							column.setRemarks(rs.getString("column_comment"));

							String ckey = rs.getString("column_key");
							ckey = ckey.toLowerCase();
							if ("pri".equals(ckey)) {
								column.setPk(true);
							} else if ("uni".equals(ckey)) {
								column.setUnique(true);
							} else if ("mul".equals(ckey)) {
								column.setIndex(true);
							}
							putColumnType(column);
							columns.add(column);
						}
					}
					return tables;
				} catch (SQLException e) {
					throw new DbMetadataMessageException("获取表信息失败[" + tableName + "]", e);
				} finally {
					DBUtils.close(null, ps, rs);
				}
			}
		});
	}
}
