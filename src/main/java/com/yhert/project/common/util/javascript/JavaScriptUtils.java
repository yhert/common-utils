package com.yhert.project.common.util.javascript;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.yhert.project.common.excp.JavaScriptException;

/**
 * JavaScript引擎
 * 
 * @author Ricardo Li 2017年7月14日 下午11:33:23
 *
 */
public class JavaScriptUtils {
	/**
	 * 引擎管理器
	 */
	private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

	/**
	 * 执行jsdiam
	 * 
	 * @param js
	 *            js
	 * @param bindings
	 *            参数
	 * @return 执行结果
	 */
	public static Object eval(String js, Bindings bindings) {
		ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
		try {
			return nashorn.eval(js, bindings);
		} catch (ScriptException e) {
			throw new JavaScriptException("执行js代码时出现了异常", e);
		}
	}
}
