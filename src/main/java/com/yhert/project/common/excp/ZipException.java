package com.yhert.project.common.excp;

/**
 * 进行文件压缩时出现错误
 * 
 * @author Ricardo Li 2017年6月8日 上午10:26:06
 *
 */
public class ZipException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ZipException(Object... args) {
		super(args);
	}

	public ZipException(String message, Object... args) {
		super(message, args);
	}

	public ZipException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public ZipException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
