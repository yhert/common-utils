package com.yhert.project.common.excp.dao;

/**
 * 数据库中结果操作一行
 * 
 * @author RicardoLi<ricardo@yhert.com> 2018年10月2日上午10:27:36
 *
 */
public class DaoHasMoreThenOneResultException extends DBOperateException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DaoHasMoreThenOneResultException(Object... args) {
		super(args);
	}

	public DaoHasMoreThenOneResultException(String message, Object... args) {
		super(message, args);
	}

	public DaoHasMoreThenOneResultException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public DaoHasMoreThenOneResultException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
