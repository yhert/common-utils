package com.yhert.project.common.conf.excp;

import com.yhert.project.common.excp.CommonException;

/**
 * 配置异常
 * 
 * @author Ricardo Li 2017年4月16日 下午2:55:24
 *
 */
public class ConfigException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfigException(Object... args) {
		super(args);
	}

	public ConfigException(String message, Object... args) {
		super(message, args);
	}

	public ConfigException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public ConfigException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
