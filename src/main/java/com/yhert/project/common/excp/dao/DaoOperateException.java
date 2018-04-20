package com.yhert.project.common.excp.dao;

import com.yhert.project.common.excp.CommonException;

/**
 * Dao操作异常
 * @author Ricardo Li 2017年4月2日 下午9:44:44
 *
 */
public class DaoOperateException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DaoOperateException(Object... args) {
		super(args);
	}

	public DaoOperateException(String message, Object... args) {
		super(message, args);
	}

	public DaoOperateException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public DaoOperateException(Throwable cause, Object... args) {
		super(cause, args);
	}
}
