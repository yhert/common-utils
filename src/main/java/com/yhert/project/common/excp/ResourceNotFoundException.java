package com.yhert.project.common.excp;

/**
 * 资源未找到
 * 
 * @author Ricardo Li 2017年8月11日 上午12:06:46
 *
 */
public class ResourceNotFoundException extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(Object... args) {
		super(args);
	}

	public ResourceNotFoundException(String message, Object... args) {
		super(message, args);
	}

	public ResourceNotFoundException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public ResourceNotFoundException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
