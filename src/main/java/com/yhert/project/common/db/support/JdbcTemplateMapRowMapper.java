package com.yhert.project.common.db.support;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import com.yhert.project.common.beans.Record;
import com.yhert.project.common.util.BeanUtils;
import com.yhert.project.common.util.StringUtils;

@SuppressWarnings("rawtypes")
public class JdbcTemplateMapRowMapper<T extends Map> implements RowMapper<T> {
	private Class<T> type;

	@SuppressWarnings("unchecked")
	public JdbcTemplateMapRowMapper(Class<T> type) {
		super();
		this.type = type;
		if (Map.class.equals(this.type)) {
			this.type = (Class<T>) Record.class;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map mapOfColValues = BeanUtils.newInstance(type);
		for (int i = 1; i <= columnCount; i++) {
			String key = JdbcUtils.lookupColumnName(rsmd, i);
			Object obj = JdbcUtils.getResultSetValue(rs, i);
			key = StringUtils.camelName(key);
			mapOfColValues.put(key, obj);
		}
		return (T) mapOfColValues;
	}

}
