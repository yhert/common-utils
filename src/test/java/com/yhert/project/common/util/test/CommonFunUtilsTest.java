package com.yhert.project.common.util.test;

import static org.junit.Assert.assertTrue;

import java.net.UnknownHostException;

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
		String uuid = CommonFunUtils.getUUID();
		System.out.println("uuid:" + uuid);
		assertTrue("uuid生成有误", uuid.length() < 32 || uuid.length() > 0);
	}

	@Test
	public void buildLongUUID() {
		long uuid = CommonFunUtils.getLongUUID();
		System.out.println("longUuid:" + uuid);
		assertTrue("uuid生成有误", uuid > 0);

	}

	@Test
	public void buildTTID() throws UnknownHostException {
		String ttid = CommonFunUtils.getTTID();
		System.out.println("ttid:" + ttid);
		assertTrue("ttid生成有误", ttid.length() < 32 || ttid.length() > 0);
	}

	@Test
	public void dis2lat() {
		double latitude2Distance = CommonFunUtils.latitude2Distance(0.005);
		System.out.println("latitude2Distance:" + latitude2Distance);
		assertTrue("latitude2Distance生成有误", latitude2Distance > 0);
	}
}
