package com.yhert.project.common.excp.app;

/**
 * Gone异常信息，资源为找到，状态码410（与404不同的是，410表示资源为找到，404表示URI未找到）
 * 
 * @author RicardoLi 2018年8月21日 下午10:00:09
 *
 */
public abstract class GoneException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GoneException(Object... args) {
		super(args);
	}

	public GoneException(String message, Object... args) {
		super(message, args);
	}

	public GoneException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public GoneException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
