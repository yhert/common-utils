package com.yhert.project.common.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.yhert.project.common.conf.excp.ConfigException;
import com.yhert.project.common.util.CommonFunUtils;
import com.yhert.project.common.util.DateUtils;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.file.FileUtils;

/**
 * 系统配置
 * 
 * @author Ricardo Li 2017年4月16日 下午2:48:27
 *
 */
public class Configuration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 配置文件后缀
	 */
	public static final String PROPERTIES_FILE_SUFFIX = "properties";
	/**
	 * 标记前缀
	 */
	public static final String SERVER_CONFIG_GOTO_TAG = "goto:";

	/**
	 * 配置文件
	 */
	private Map<String, String> config = new HashMap<>();

	/**
	 * 加载配置文件
	 * 
	 * @param filename
	 *            文件名
	 */
	public void loadProperties(String filename) {
		loadProperties(FileUtils.openInputStream(filename));
	}

	/**
	 * 加载配置
	 * 
	 * @param file
	 *            文件
	 */
	public void loadProperties(File file) {
		try {
			try (FileInputStream inputStream = new FileInputStream(file)) {
				this.loadProperties(inputStream);
			}
		} catch (Exception e) {
			throw new ConfigException("加载配置文件" + file.getAbsolutePath() + "失败");
		}
	}

	/**
	 * 加载配置
	 * 
	 * @param inputStream
	 *            文件流
	 */
	public void loadProperties(InputStream inputStream) {
		try {
			Properties properties = new Properties();
			properties.load(inputStream);
			for (Entry<Object, Object> entry : properties.entrySet()) {
				Object key = entry.getKey();
				if (null != key) {
					put(key.toString(), entry.getValue());
				}
			}
		} catch (Exception e) {
			throw new ConfigException("加载配置文件失败");
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 插入对象
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void put(String key, Object value) {
		String val = null;
		if (value != null) {
			val = value.toString();
		}
		config.put(key, val);
	}

	/**
	 * 插入所有数据
	 * 
	 * @param map
	 *            map
	 */
	public void putAll(Map<String, String> map) {
		this.config.putAll(map);
	}

	/**
	 * 清空数据
	 */
	public void clear() {
		this.config.clear();
	}

	/**
	 * 获得配置
	 * 
	 * @return 配置
	 */
	public Map<String, String> getConfig() {
		return config;
	}

	/**
	 * 设置配置
	 * 
	 * @param config
	 *            配置
	 */
	public void setConfig(Map<String, String> config) {
		this.config = config;
	}

	/**
	 * 通过Key获得信息
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public Object getByKey(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		String value = config.get(key);
		if (value != null) {
			if (value.startsWith(SERVER_CONFIG_GOTO_TAG)) {
				value = value.substring(SERVER_CONFIG_GOTO_TAG.length(), value.length());
				value = StringUtils.trim(value);
				return getByKey(value);
			}
		}
		return value;
	}

	/**
	 * 获得boolean类型的数据
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 值
	 */
	public boolean getBoolean(String key, boolean def) {
		Object obj = getByKey(key);
		if (obj == null || StringUtils.isEmpty(obj.toString())) {
			return def;
		} else {
			return CommonFunUtils.parseBoolean(obj.toString());
		}
	}

	/**
	 * 获得boolean配置信息
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public boolean getBoolean(String key) {
		Object obj = getByKey(key);
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		} else {
			if (obj == null || StringUtils.isEmpty(obj.toString())) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj);
			} else {
				return CommonFunUtils.parseBoolean(obj.toString());
			}
		}
	}

	/**
	 * 获得String类型的数据
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 值
	 */
	public String getString(String key, String def) {
		Object obj = getByKey(key);
		if (obj == null) {
			return def;
		} else {
			return obj.toString();
		}
	}

	/**
	 * 获得String配置信息
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public String getString(String key) {
		Object obj = getByKey(key);
		if (obj == null) {
			throw new ConfigException("当前配置异常：" + key + "=>" + obj);
		} else {
			return obj.toString();
		}
	}

	/**
	 * 获得long类型的数据
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 值
	 */
	public long getLong(String key, long def) {
		Object obj = getByKey(key);
		if (obj == null) {
			return def;
		} else if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else {
			try {
				return Long.parseLong(obj.toString());
			} catch (Exception e) {
				return def;
			}
		}
	}

	/**
	 * 获得long配置信息
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public long getLong(String key) {
		Object obj = getByKey(key);
		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else {
			try {
				return Long.parseLong(obj.toString());
			} catch (Exception e) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj);
			}
		}
	}

	/**
	 * 获得byte类型的数据
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 值
	 */
	public byte getByte(String key, byte def) {
		Object obj = getByKey(key);
		if (obj == null) {
			return def;
		} else if (obj instanceof Number) {
			return ((Number) obj).byteValue();
		} else {
			try {
				return Byte.parseByte(obj.toString());
			} catch (Exception e) {
				return def;
			}
		}
	}

	/**
	 * 获得byte配置信息
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public byte getByte(String key) {
		Object obj = getByKey(key);
		if (obj instanceof Number) {
			return ((Number) obj).byteValue();
		} else {
			try {
				return Byte.parseByte(obj.toString());
			} catch (Exception e) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj, e, key, obj);
			}
		}
	}

	/**
	 * 获得short类型的数据
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 值
	 */
	public short getShort(String key, short def) {
		Object obj = getByKey(key);
		if (obj == null) {
			return def;
		} else if (obj instanceof Number) {
			return ((Number) obj).shortValue();
		} else {
			try {
				return Short.parseShort(obj.toString());
			} catch (Exception e) {
				return def;
			}
		}
	}

	/**
	 * 获得short配置信息
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public short getShort(String key) {
		Object obj = getByKey(key);
		if (obj instanceof Number) {
			return ((Number) obj).shortValue();
		} else {
			try {
				return Short.parseShort(obj.toString());
			} catch (Exception e) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj);
			}

		}
	}

	/**
	 * 获得float类型的数据
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 值
	 */
	public float getFloat(String key, float def) {
		Object obj = getByKey(key);
		if (obj == null) {
			return def;
		} else if (obj instanceof Number) {
			return ((Number) obj).floatValue();
		} else {
			try {
				return Float.parseFloat(obj.toString());
			} catch (Exception e) {
				return def;
			}
		}
	}

	/**
	 * 获得float配置信息
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public float getFloat(String key) {
		Object obj = getByKey(key);
		if (obj instanceof Number) {
			return ((Number) obj).floatValue();
		} else {
			try {
				return Float.parseFloat(obj.toString());
			} catch (Exception e) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj);
			}
		}
	}

	/**
	 * 获得int类型的数据
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 值
	 */
	public int getInt(String key, int def) {
		Object obj = getByKey(key);
		if (obj == null) {
			return def;
		} else if (obj instanceof Number) {
			return ((Number) obj).intValue();
		} else {
			try {
				return Integer.parseInt(obj.toString());
			} catch (Exception e) {
				return def;
			}
		}
	}

	/**
	 * 获得Int配置信息
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public int getInt(String key) {
		Object obj = getByKey(key);
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		} else {
			try {
				return Integer.parseInt(obj.toString());
			} catch (Exception e) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj);
			}

		}
	}

	/**
	 * 获得double类型的数据
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 值
	 */
	public double getDouble(String key, double def) {
		Object obj = getByKey(key);
		if (obj == null) {
			return def;
		} else if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		} else {
			try {
				return Double.parseDouble(obj.toString());
			} catch (Exception e) {
				return def;
			}
		}
	}

	/**
	 * 获得double配置信息
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public double getDouble(String key) {
		Object obj = getByKey(key);
		if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		} else {
			try {
				return Double.parseDouble(obj.toString());
			} catch (Exception e) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj);
			}
		}
	}

	/**
	 * 解析成时间长度
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 时间长度（毫秒）
	 */
	public long getTime(String key, Object def) {
		Object obj = getByKey(key);
		if (obj == null) {
			obj = def;
		}
		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else {
			try {
				return DateUtils.parseTime(obj.toString());
			} catch (Exception e) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj);
			}
		}
	}

	/**
	 * 解析成时间长度
	 * 
	 * @param key
	 *            键
	 * @return 时间长度（毫秒）
	 */
	public long getTime(String key) {
		Object obj = getByKey(key);
		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else {
			try {
				return DateUtils.parseTime(obj.toString());
			} catch (Exception e) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj);
			}
		}
	}

	/**
	 * 解析成文件大小
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 时间长度（毫秒）
	 */
	public long getFileSize(String key, Object def) {
		Object obj = getByKey(key);
		if (obj == null) {
			obj = def;
		}
		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else {
			try {
				return DateUtils.parseTime(obj.toString());
			} catch (Exception e) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj);
			}
		}
	}

	/**
	 * 解析成文件大小
	 * 
	 * @param key
	 *            键
	 * @param def
	 *            默认值
	 * @return 大小（字节）
	 */
	public long getFileSize(String key) {
		Object obj = getByKey(key);
		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else {
			try {
				return CommonFunUtils.parseFileSize(obj.toString());
			} catch (Exception e) {
				throw new ConfigException("当前配置异常：" + key + "=>" + obj);
			}
		}
	}

	@Override
	public String toString() {
		return this.config.toString();
	}

}
