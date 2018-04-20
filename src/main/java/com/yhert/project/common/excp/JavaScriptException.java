package com.yhert.project.common.excp;

/**
 * 执行引擎内部异常
 * 
 * @author Ricardo Li 2017年7月14日 下午11:40:45
 *
 */
public class JavaScriptException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JavaScriptException(Object... args) {
		super(args);
	}

	public JavaScriptException(String message, Object... args) {
		super(message, args);
	}

	public JavaScriptException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public JavaScriptException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
