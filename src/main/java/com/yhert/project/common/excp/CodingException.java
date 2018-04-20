package com.yhert.project.common.excp;

/**
 * 编码错误
 * @author Ricardo Li 2017年6月9日 下午2:36:39
 *
 */
public class CodingException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodingException(Object... args) {
		super(args);
	}

	public CodingException(String message, Object... args) {
		super(message, args);
	}

	public CodingException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public CodingException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
