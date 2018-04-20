package com.yhert.project.common.excp.dao;

/**
 * 数据库操作异常
 * 
 * @author Ricardo Li 2017年9月6日 下午2:16:19
 *
 */
public class DBOperateException extends DaoOperateException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBOperateException(Object... args) {
		super(args);
	}

	public DBOperateException(String message, Object... args) {
		super(message, args);
	}

	public DBOperateException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public DBOperateException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
