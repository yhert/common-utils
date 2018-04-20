package com.yhert.project.common.excp;

/**
 * 效验异常
 * 
 * @author Ricardo Li 2017年6月7日 下午2:48:10
 *
 */
public class ValidationException extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException(Object... args) {
		super(args);
	}

	public ValidationException(String message, Object... args) {
		super(message, args);
	}

	public ValidationException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public ValidationException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
