package com.yhert.project.common.excp;

/**
 * cmd执行异常
 * 
 * @author Ricardo Li 2017年10月11日 上午9:50:19
 *
 */
public class CmdExecuteException extends CommonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CmdExecuteException(Object... args) {
		super(args);
	}

	public CmdExecuteException(String message, Object... args) {
		super(message, args);
	}

	public CmdExecuteException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public CmdExecuteException(Throwable cause, Object... args) {
		super(cause, args);
	}

}
