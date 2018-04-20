package com.yhert.project.common.excp;

/**
 * 账户异常，例如用户名密码错误，权限不足等
 * 
 * @author Ricardo Li
 *
 */
public class AccountException extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountException(Object... args) {
		super(args);
	}

	public AccountException(String message, Object... args) {
		super(message, args);
	}

	public AccountException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public AccountException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
