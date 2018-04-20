package com.yhert.project.common.util.bean.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {
	
	@Test
	public void regexTest() throws Exception {
//		String url = "/enwe/fw%e/sdf{fwe}fe w/fbg";
//		Pattern pattern = Pattern.compile("(\\{\\w+\\})");
//		Pattern pattern = Pattern.compile("([\\w% ]+)");
		
		String url = "/enwe/fw%e/sdf_fewfwe_fe w/fbg_fwew%32fs2fewf%34sefwf/";
		Pattern pattern = Pattern.compile("^/enwe/fw%e/sdf([\\w% ]+)fe w/fbg([\\w% ]+)/$");
		Matcher matcher = pattern.matcher(url);
		while (matcher.find()) {
			int count = matcher.groupCount();
			for (int i = 0; i < count; i++) {
				String group = matcher.group(i + 1);
				System.out.println(group);
			}
		}
	}
	
	@Test
	public void regexTest1() throws Exception {
		Pattern pattern = Pattern.compile("^/fs$");
		System.out.println(pattern.matcher("/fs").find());
	}
	
}
