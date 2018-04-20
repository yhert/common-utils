package com.yhert.project.common.excp;

/**
 * 序列化错误
 * 
 * @author Ricardo Li 2016年12月17日 下午6:46:06
 *
 */
public class SerializableException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SerializableException(Object... args) {
		super(args);
	}

	public SerializableException(String message, Object... args) {
		super(message, args);
	}

	public SerializableException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public SerializableException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
