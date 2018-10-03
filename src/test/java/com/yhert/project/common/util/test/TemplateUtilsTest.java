package com.yhert.project.common.util.test;

import static org.junit.Assert.assertTrue;

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
		StringBuilder tmplr = new StringBuilder();
		tmplr.append("===============================");
		tmplr.append("测试模板：");
		tmplr.append("test datas");

		String rr = TemplateUtils.freemarkerFtl2Str("templateUtils/temp.ftl", Param.getParam("test", "test data"));
		System.out.println(rr);
		assertTrue("freemarker模板测试未通过", tmplr.toString().replaceAll("\n", "").equals(rr.replaceAll("\n", "")));

		StringBuilder tmpl = new StringBuilder();
		tmpl.append("===============================测试模板：<#if test??>${test}</#if><#if !test??>test参数不存在</#if>s");
		String r = TemplateUtils.freemarkerTmpl2Str(tmpl.toString(), Param.getParam("test", "test data"));
		System.out.println(r);

		assertTrue("freemarker模板测试未通过", tmplr.toString().replaceAll("\n", "").equals(r.replaceAll("\n", "")));
	}
}
