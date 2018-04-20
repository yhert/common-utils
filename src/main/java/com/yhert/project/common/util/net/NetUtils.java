package com.yhert.project.common.util.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import com.yhert.project.common.excp.NetException;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.net.ipaddress.Ip2Address;
import com.yhert.project.common.util.net.ipaddress.SystemIpAddress;
import com.yhert.project.common.util.net.ipaddress.TaobaoIp2AddressImpl;

/**
 * 网络Ip与地址相关工具
 * 
 * @author Ricardo Li 2016年7月30日 下午3:24:28
 *
 */
public class NetUtils {

	/**
	 * 通过域名获得IP地址
	 * 
	 * @param domain
	 *            域名
	 * @return 返回的IP地址
	 */
	public static String getIpByDomain(String domain) {
		String[] ips = getIpsByDomain(domain);
		if (ips.length <= 0) {
			throw new NetException("通过域名[" + domain + "]获得不到IP");
		} else {
			return ips[0];
		}
	}

	/**
	 * 通过域名获得IP地址
	 * 
	 * @param domain
	 *            域名
	 * @return 返回的IP地址
	 */
	public static String[] getIpsByDomain(String domain) {
		try {
			InetAddress[] inetAddresses = Inet4Address.getAllByName(domain);
			Set<String> ipset = new HashSet<>();
			for (InetAddress inetAddress : inetAddresses) {
				String ip = inetAddress.getHostAddress();
				if (!StringUtils.isEmpty(ip)) {
					ipset.add(ip);
				}
			}
			return ipset.toArray(new String[ipset.size()]);
		} catch (Exception e) {
			throw new NetException(e);
		}
	}

	/**
	 * 获得本地Mac地址
	 * 
	 * @return IP对应的mac地址
	 */
	public static Map<String, String> getLocalMac() {
		try {
			// 获取网卡，获取地址
			Enumeration<NetworkInterface> ns = NetworkInterface.getNetworkInterfaces();
			Map<String, String> macMap = new HashMap<>();
			while (ns.hasMoreElements()) {
				NetworkInterface networkInterface = ns.nextElement();
				if (null != networkInterface) {
					byte[] mac = networkInterface.getHardwareAddress();
					if (null != mac) {
						StringBuffer sb = new StringBuffer("");
						for (int i = 0; i < mac.length; i++) {
							if (i != 0) {
								sb.append("-");
							}
							// 字节转换为整数
							int temp = mac[i] & 0xff;
							String str = Integer.toHexString(temp);
							if (str.length() == 1) {
								sb.append("0" + str);
							} else {
								sb.append(str);
							}
						}
						String macStr = sb.toString().toUpperCase();
						Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
						while (addresses.hasMoreElements()) {
							InetAddress address = addresses.nextElement();
							String ip = address.getHostAddress();
							if (!StringUtils.isEmpty(ip) && isIpAddress(ip)) {
								macMap.put(ip, macStr);
							}
						}
					}
				}
			}
			return macMap;
		} catch (Exception e) {
			throw new NetException("获取本地MAC地址失败", e);
		}
	}

	/**
	 * 获得本地IP
	 * 
	 * @return 本地IP
	 */
	public static String[] getLocalIp() {
		try {
			Enumeration<NetworkInterface> ns = NetworkInterface.getNetworkInterfaces();
			Set<String> ipSet = new HashSet<>();
			while (ns.hasMoreElements()) {
				NetworkInterface networkInterface = ns.nextElement();
				if (null != networkInterface) {
					Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						InetAddress address = addresses.nextElement();
						String ip = address.getHostAddress();
						if (!StringUtils.isEmpty(ip)) {
							ipSet.add(ip);
						}
					}
				}
			}
			return ipSet.toArray(new String[ipSet.size()]);
		} catch (Exception e) {
			throw new NetException("获取本地MAC地址失败", e);
		}
	}

	/**
	 * 获得本地的Mac地址
	 * 
	 * @param ip
	 *            符合此IP
	 * @return Mac字符串
	 */
	public static String getLocalMac(String ip) {
		try {
			Map<String, String> map = getLocalMac();
			for (Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().startsWith(ip)) {
					return entry.getValue();
				}
			}
			return null;
		} catch (Exception e) {
			if (e instanceof NetException) {
				throw (NetException) e;
			} else {
				throw new NetException("获取本地MAC地址失败", e);
			}
		}
	}

	/**
	 * 判断是否是ip地址的字段
	 */
	private static final Pattern isIp = Pattern.compile(
			"^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$");

	/**
	 * 判断是否为IP地址
	 * 
	 * @param ip
	 *            IP
	 * @return IP地址
	 */
	public static boolean isIpAddress(String ip) {
		return isIp.matcher(ip).find();
	}

	/**
	 * ip到地址映射器
	 */
	private static Ip2Address ip2Address = null;

	public static Ip2Address getIp2Address() {
		return ip2Address;
	}

	public static void setIp2Address(Ip2Address ip2Address) {
		NetUtils.ip2Address = ip2Address;
	}

	/**
	 * 通过Ip获得相关信息
	 * 
	 * @param ip
	 *            需要查询的IP
	 * @return 地址信息
	 */
	public static SystemIpAddress getAddressByIp(String ip) {
		if (ip2Address == null) {
			ip2Address = new TaobaoIp2AddressImpl();
		}
		return ip2Address.getAddressByIp(ip);
	}
}
