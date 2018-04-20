package com.yhert.project.common.excp;

/**
 * 应用异常
 * 
 * @author Ricardo Li 2017年7月24日 下午10:42:42
 *
 */
public class ApplicationException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApplicationException(Object... args) {
		super(args);
	}

	public ApplicationException(String message, Object... args) {
		super(message, args);
	}

	public ApplicationException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public ApplicationException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
