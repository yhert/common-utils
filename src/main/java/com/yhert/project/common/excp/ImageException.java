package com.yhert.project.common.excp;

/**
 * Image相关错误
 * 
 * @author Ricardo Li 2017年3月19日 下午4:05:22
 *
 */
public class ImageException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImageException(Object... args) {
		super(args);
	}

	public ImageException(String message, Object... args) {
		super(message, args);
	}

	public ImageException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public ImageException(Throwable cause, Object... args) {
		super(cause, args);
	}
}
