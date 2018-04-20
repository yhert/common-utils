package com.yhert.project.common.excp;

/**
 * 网络操作异常
 * 
 * @author Ricardo Li 2016年12月17日 下午6:32:41
 *
 */
public class NetException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NetException(Object... args) {
		super(args);
	}

	public NetException(String message, Object... args) {
		super(message, args);
	}

	public NetException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public NetException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
