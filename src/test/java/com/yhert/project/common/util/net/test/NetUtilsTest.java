package com.yhert.project.common.util.net.test;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.yhert.project.common.util.net.NetUtils;

/**
 * 测试网络工具模块
 * 
 * @author Ricardo Li 2017年3月16日 下午10:04:59
 *
 */
public class NetUtilsTest {
	@Test
	public void getIpByDomain() {
		System.out.println(NetUtils.isIpAddress("192.168.1.1"));
		System.out.println(ArrayUtils.toString(NetUtils.getIpByDomain("www.baidu.com")));
		System.out.println("通过域名获得IP功能正常");
	}

	@Test
	public void getAddressByIp() {
		System.out.println(NetUtils.getAddressByIp("www.baidu.com"));
		System.out.println("通过IP获得地址功能正常");
	}

	@Test
	public void getLocalMac() {
		System.out.println(NetUtils.getLocalMac());
		System.out.println("获得本地Mac功能正常");
	}
}
