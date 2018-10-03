package com.yhert.project.common.excp;

/**
 * 所有异常的公共类,说哟操作的异常信息
 * 
 * @author Ricardo Li 2016年12月17日 下午5:43:27
 *
 */
public class CommonException extends RuntimeException implements ResultDataGet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 内部参数
	 */
	private Object[] args;
	/**
	 * 设置响应数据
	 */
	private Object responseData;

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object... args) {
		this.args = args;
	}

	public CommonException(Object... args) {
		super();
	}

	public CommonException(String message, Throwable cause, Object... args) {
		super(message, cause);
		this.args = args;
	}

	public CommonException(String message, Object... args) {
		super(message);
		this.args = args;
	}

	public CommonException(Throwable cause, Object... args) {
		super(cause);
		this.args = args;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	@Override
	public Object getData() {
		return this.responseData;
	}
}
