package com.yhert.project.common.excp;

/**
 * 模板相关异常
 * 
 * @author Ricardo Li 2017年6月20日 上午11:12:37
 *
 */
public class TemplateException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TemplateException(Object... args) {
		super(args);
	}

	public TemplateException(String message, Object... args) {
		super(message, args);
	}

	public TemplateException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public TemplateException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
