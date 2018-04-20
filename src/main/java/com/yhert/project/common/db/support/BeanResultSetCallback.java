package com.yhert.project.common.db.support;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.jdbc.support.JdbcUtils;

import com.yhert.project.common.beans.Record;
import com.yhert.project.common.util.BeanUtils;
import com.yhert.project.common.util.StringUtils;

/**
 * 封装JavaBean到ResultSet
 * 
 * @author Ricardo Li 2017年4月3日 上午8:11:46
 *
 * @param <T>
 *            JavaBean
 */
public class BeanResultSetCallback<T> implements ResultSetCallback<List<T>> {
	/**
	 * 类型
	 */
	private Class<T> type;
	/**
	 * 读入条数
	 */
	private int readCount;
	/**
	 * 是否是map
	 */
	private boolean map = false;
	/**
	 * 参数集合
	 */
	private Map<String, PropertyDescriptor> propertyMap;

	public BeanResultSetCallback(Class<T> type) {
		this(type, 0);
	}

	public BeanResultSetCallback(Class<T> type, int readCount) {
		super();
		this.type = type;
		this.readCount = readCount <= 0 ? Integer.MAX_VALUE : readCount;
	}

	@SuppressWarnings("unchecked")
	protected void init() {
		if (Map.class.isAssignableFrom(type)) {
			if (Map.class.equals(type)) {
				type = (Class<T>) Record.class;
			}
			map = true;
		} else {
			map = false;
			this.propertyMap = new HashMap<>();
			for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(this.type)) {
				this.propertyMap.put(pd.getName(), pd);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> callback(ResultSet rs) throws Exception {
		if (this.type == null) {
			throw new IllegalArgumentException("参数错误，类型不能为null");
		}
		this.init();

		ResultSetMetaData mdrs = rs.getMetaData();
		int columnCount = mdrs.getColumnCount();

		List<T> datas = new ArrayList<>();
		int count = 0;
		while (rs.next() && count++ < this.readCount) {
			T t = BeanUtils.newInstance(this.type);
			for (int i = 0; i < columnCount; i++) {
				String dname = mdrs.getColumnName(i + 1).toLowerCase();
				String cname = StringUtils.camelName(dname);
				String uname = StringUtils.underscoreName(dname);
				if (map) {
					Object value = rs.getObject(dname);
					((Map<String, Object>) t).put(cname, value);
				} else {
					PropertyDescriptor pd = null;
					if (propertyMap.containsKey(cname)) {
						pd = propertyMap.get(cname);
					} else if (propertyMap.containsKey(uname)) {
						pd = propertyMap.get(uname);
					} else if (propertyMap.containsKey(dname)) {
						pd = propertyMap.get(dname);
					}
					if (null != pd) {
						Object value = JdbcUtils.getResultSetValue(rs, i + 1, pd.getPropertyType());
						PropertyUtils.setProperty(t, pd.getName(), value);
					}
				}
			}
			datas.add(t);
		}
		return datas;
	}
}
