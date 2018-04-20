package com.yhert.project.common.excp;

/**
 * bean异常
 * 
 * @author Ricardo Li 2017年4月17日 下午1:35:53
 *
 */
public class BeanException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BeanException(Object... args) {
		super(args);
	}

	public BeanException(String message, Object... args) {
		super(message, args);
	}

	public BeanException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public BeanException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
