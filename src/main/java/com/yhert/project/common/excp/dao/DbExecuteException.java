package com.yhert.project.common.excp.dao;

/**
 * 数据库执行异常
 * 
 * @author Ricardo Li 2017年9月6日 下午2:18:25
 *
 */
public class DbExecuteException extends DBOperateException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DbExecuteException(Object... args) {
		super(args);
	}

	public DbExecuteException(String message, Object... args) {
		super(message, args);
	}

	public DbExecuteException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public DbExecuteException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
