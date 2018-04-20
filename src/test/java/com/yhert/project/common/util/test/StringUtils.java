package com.yhert.project.common.util.test;

import org.junit.Assert;
import org.junit.Test;


/**
 * 测试字符串工具
 * 
 * @author Ricardo Li 2017年3月22日 下午8:15:48
 *
 */
public class StringUtils {
	@Test
	public void underscoreNameAndCamelName() throws Exception {
		String c = "aceAce";
		String a = com.yhert.project.common.util.StringUtils.underscoreName(c);
		a = com.yhert.project.common.util.StringUtils.underscoreName(a);
		Assert.assertTrue("StringUtil是转换字符串出错", "ace_ace".equals(a));
		String d = com.yhert.project.common.util.StringUtils.camelName(a);
		d = com.yhert.project.common.util.StringUtils.camelName(d);
		Assert.assertTrue("StringUtil是转换字符串出错", c.equals(d));
	}
}
