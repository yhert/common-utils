package com.yhert.project.common.util.test;

import org.junit.Test;

import com.yhert.project.common.util.CommonFunUtils;

/**
 * commonFun测试方法
 * 
 * @author Ricardo Li 2017年3月22日 下午1:53:20
 *
 */
public class CommonFunUtilsTest {

	@Test
	public void buildUUID() {
		System.out.println(CommonFunUtils.getUUID());
	}

	@Test
	public void buildLongUUID() {
		System.out.println(CommonFunUtils.getLongUUID());
	}

	@Test
	public void dis2lat() {
		System.out.println(CommonFunUtils.latitude2Distance(0.005));
	}
}
