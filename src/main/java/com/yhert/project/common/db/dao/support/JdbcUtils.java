package com.yhert.project.common.db.dao.support;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Jdbc操作相关工具
 * 
 * @author Ricardo Li 2017年4月3日 下午3:48:55
 *
 */
public class JdbcUtils {
	/**
	 * Retrieve a JDBC column value from a ResultSet, using the specified value
	 * type.
	 * <p>
	 * Uses the specifically typed ResultSet accessor methods, falling back to
	 * {@link #getResultSetValue(java.sql.ResultSet, int)} for unknown types.
	 * <p>
	 * Note that the returned value may not be assignable to the specified
	 * required type, in case of an unknown type. Calling code needs to deal
	 * with this case appropriately, e.g. throwing a corresponding exception.
	 * 
	 * @param rs
	 *            is the ResultSet holding the data
	 * @param index
	 *            is the column index
	 * @param requiredType
	 *            the required value type (may be {@code null})
	 * @return the value object
	 * @throws SQLException
	 *             if thrown by the JDBC API
	 */
	public static Object getResultSetValue(ResultSet rs, int index, Class<?> requiredType) throws SQLException {
		if (requiredType == null) {
			return getResultSetValue(rs, index);
		}

		Object value;
		// Explicitly extract typed value, as far as possible.
		if (String.class == requiredType) {
			return rs.getString(index);
		} else if (boolean.class == requiredType || Boolean.class == requiredType) {
			value = rs.getBoolean(index);
		} else if (byte.class == requiredType || Byte.class == requiredType) {
			value = rs.getByte(index);
		} else if (short.class == requiredType || Short.class == requiredType) {
			value = rs.getShort(index);
		} else if (int.class == requiredType || Integer.class == requiredType) {
			value = rs.getInt(index);
		} else if (long.class == requiredType || Long.class == requiredType) {
			value = rs.getLong(index);
		} else if (float.class == requiredType || Float.class == requiredType) {
			value = rs.getFloat(index);
		} else if (double.class == requiredType || Double.class == requiredType || Number.class == requiredType) {
			value = rs.getDouble(index);
		} else if (BigDecimal.class == requiredType) {
			return rs.getBigDecimal(index);
		} else if (java.sql.Date.class == requiredType) {
			return rs.getDate(index);
		} else if (java.sql.Time.class == requiredType) {
			return rs.getTime(index);
		} else if (java.sql.Timestamp.class == requiredType || java.util.Date.class == requiredType) {
			return rs.getTimestamp(index);
		} else if (byte[].class == requiredType) {
			return rs.getBytes(index);
		} else if (Blob.class == requiredType) {
			return rs.getBlob(index);
		} else if (Clob.class == requiredType) {
			return rs.getClob(index);
		} else {
			return getResultSetValue(rs, index);
		}
		return (rs.wasNull() ? null : value);
	}

	/**
	 * Retrieve a JDBC column value from a ResultSet, using the most appropriate
	 * value type. The returned value should be a detached value object, not
	 * having any ties to the active ResultSet: in particular, it should not be
	 * a Blob or Clob object but rather a byte array or String representation,
	 * respectively.
	 * <p>
	 * Uses the {@code getObject(index)} method, but includes additional "hacks"
	 * to get around Oracle 10g returning a non-standard object for its
	 * TIMESTAMP datatype and a {@code java.sql.Date} for DATE columns leaving
	 * out the time portion: These columns will explicitly be extracted as
	 * standard {@code java.sql.Timestamp} object.
	 * 
	 * @param rs
	 *            is the ResultSet holding the data
	 * @param index
	 *            is the column index
	 * @return the value object
	 * @throws SQLException
	 *             if thrown by the JDBC API
	 * @see java.sql.Blob
	 * @see java.sql.Clob
	 * @see java.sql.Timestamp
	 */
	public static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);
		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}
		if (obj instanceof Blob) {
			Blob blob = (Blob) obj;
			obj = blob.getBytes(1, (int) blob.length());
		} else if (obj instanceof Clob) {
			Clob clob = (Clob) obj;
			obj = clob.getSubString(1, (int) clob.length());
		} else if ("oracle.sql.TIMESTAMP".equals(className) || "oracle.sql.TIMESTAMPTZ".equals(className)) {
			obj = rs.getTimestamp(index);
		} else if (className != null && className.startsWith("oracle.sql.DATE")) {
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
				obj = rs.getTimestamp(index);
			} else {
				obj = rs.getDate(index);
			}
		} else if (obj != null && obj instanceof java.sql.Date) {
			if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
				obj = rs.getTimestamp(index);
			}
		}
		return obj;
	}
}
