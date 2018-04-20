package com.yhert.project.common.excp;

/**
 * 文件异常
 * 
 * @author Ricardo Li 2017年6月16日 下午2:57:25
 *
 */
public class FileException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileException(Object... args) {
		super(args);
	}

	public FileException(String message, Object... args) {
		super(message, args);
	}

	public FileException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public FileException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
