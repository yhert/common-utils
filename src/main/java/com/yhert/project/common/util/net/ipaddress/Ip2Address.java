package com.yhert.project.common.util.net.ipaddress;

/**
 * 
 * @author Ricardo Li 2017年10月16日 下午2:08:14
 *
 */
public interface Ip2Address {
	/**
	 * 通过Ip获得相关信息
	 * 
	 * @param ip
	 *            需要查询的IP
	 * @return 地址信息
	 */
	SystemIpAddress getAddressByIp(String ip);
}
