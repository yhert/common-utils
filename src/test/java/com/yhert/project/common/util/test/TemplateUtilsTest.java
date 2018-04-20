package com.yhert.project.common.util.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.yhert.project.common.beans.Param;
import com.yhert.project.common.util.TemplateUtils;

/**
 * 模板测试
 * 
 * @author Ricardo Li 2017年6月20日 上午11:04:28
 *
 */
public class TemplateUtilsTest {
	@Test
	public void templatedeal() {
		String ss = "sfwef@[f(-e)w]sfesf@[gf]";
		Map<String, Object> map = new HashMap<>();
		map.put("f(-e)w", "_errwe_");
		String rs = TemplateUtils.templatedeal(ss, map);
		Assert.assertTrue("模板工具处理有误", "sfwef_errwe_sfesf".equals(rs));
	}

	@Test
	public void freemarkerFtl2Str() {
		System.out.println(
				TemplateUtils.freemarkerFtl2Str("templateUtils/temp.ftl", Param.getParam("test", "test data")));
		StringBuilder tmpl = new StringBuilder();
		tmpl.append("===============================\n");
		tmpl.append("测试模板：\n");
		tmpl.append("<#if test??>\n");
		tmpl.append("${test}\n");
		tmpl.append("</#if>\n");
		tmpl.append("<#if !test??>\n");
		tmpl.append("test参数不存在\n");
		tmpl.append("</#if>\n");
		System.out.println(TemplateUtils.freemarkerTmpl2Str(tmpl.toString(), Param.getParam("test", "test data")));
	}
}
