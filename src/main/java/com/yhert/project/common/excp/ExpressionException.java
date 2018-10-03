package com.yhert.project.common.excp;

/**
 * 表达式异常
 * 
 * @author Ricardo Li 2017年4月17日 下午1:35:53
 *
 */
public class ExpressionException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpressionException(Object... args) {
		super(args);
	}

	public ExpressionException(String message, Object... args) {
		super(message, args);
	}

	public ExpressionException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public ExpressionException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
