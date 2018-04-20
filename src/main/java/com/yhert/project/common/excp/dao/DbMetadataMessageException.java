package com.yhert.project.common.excp.dao;

/**
 * 数据库元数据信息异常
 * 
 * @author Ricardo Li 2017年9月6日 下午2:18:05
 *
 */
public class DbMetadataMessageException extends DBOperateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DbMetadataMessageException(Object... args) {
		super(args);
	}

	public DbMetadataMessageException(String message, Object... args) {
		super(message, args);
	}

	public DbMetadataMessageException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public DbMetadataMessageException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
