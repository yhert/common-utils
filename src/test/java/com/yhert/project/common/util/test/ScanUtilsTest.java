package com.yhert.project.common.util.test;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.yhert.project.common.util.ClassScanUtils;

public class ScanUtilsTest {
	@Test
	public void scan() throws Exception {
		Set<Class<?>> scanAllClass = ClassScanUtils.scanAllClass("com.yhert.*");
		System.out.println(scanAllClass);
		assertTrue("扫描java类工具出错", scanAllClass.size() > 0);
		// Long[] sfa = new Long[2];
		// Class<?> type = sfa.getClass();
		// System.out.println(type);
		// System.out.println(type.isArray());
		// System.out.println(type.equals(Long.class));
		// System.out.println(type.getComponentType());
		// System.out.println(type.getComponentType().equals(long.class));
		// System.out.println(type.getComponentType().equals(Long.class));
	}

}
