package com.yhert.project.common.util;

import java.io.ByteArrayOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import com.yhert.project.common.excp.CmdExecuteException;

/**
 * 执行外部命令工具
 * 
 * @author Ricardo Li 2017年10月11日 上午9:42:11
 *
 */
public class CmdExecuteUtils {

	/**
	 * 执行命令
	 * 
	 * @param cmd
	 *            命令
	 * @return 输出结果
	 */
	public static String exec(String cmd) {
		final CommandLine command = CommandLine.parse(cmd);
		DefaultExecutor executor = new DefaultExecutor();
		ByteArrayOutputStream bao = new ByteArrayOutputStream(1024);
		PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(bao);
		executor.setStreamHandler(pumpStreamHandler);
		try {
			if (executor.execute(command) == 0) {
				return bao.toString().trim();
			} else {
				throw new CmdExecuteException("执行命令" + cmd + "出现错误,错误信息：" + bao.toString().trim());
			}
		} catch (Exception e) {
			throw new CmdExecuteException("执行命令" + cmd + "出现错误", e);
		}
	}
}
