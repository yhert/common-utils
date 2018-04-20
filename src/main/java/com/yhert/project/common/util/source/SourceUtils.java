package com.yhert.project.common.util.source;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

/**
 * 源码工具
 * 
 * @author Ricardo Li 2017年11月1日 下午1:57:47
 *
 */
public class SourceUtils {
	private static Object lock = new Object();
	private static CodeFormatter formatter = null;

	/**
	 * 获得格式化工厂
	 * 
	 * @return 格式化
	 */
	private static CodeFormatter getFormatter() {
		if (formatter == null) {
			synchronized (lock) {
				if (formatter == null) {
					Map<String, String> hashMap = new HashMap<>();
					hashMap.put(JavaCore.COMPILER_SOURCE, "1.8");
					hashMap.put(JavaCore.COMPILER_COMPLIANCE, "1.8");
					hashMap.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, "1.8");
					formatter = ToolFactory.createCodeFormatter(hashMap);
				}
			}
		}
		return formatter;
	}

	/**
	 * 格式化代码
	 * 
	 * @param source
	 *            源码
	 * @return 格式化后的源码
	 */
	public static String defaultFormat(String source) {
		TextEdit edit = getFormatter().format(CodeFormatter.K_COMPILATION_UNIT, source, 0, source.length(), 0, null);
		if (edit == null) {
			return source;
		}
		IDocument doc = new Document();
		doc.set(source);
		try {
			edit.apply(doc);
		} catch (Exception e) {
			return source;
		}
		return doc.get();
	}
}
