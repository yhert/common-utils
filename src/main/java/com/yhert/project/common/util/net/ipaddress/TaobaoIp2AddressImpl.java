package com.yhert.project.common.util.net.ipaddress;

import java.net.URLEncoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.yhert.project.common.excp.NetException;
import com.yhert.project.common.util.BeanUtils;
import com.yhert.project.common.util.CommonFunUtils;
import com.yhert.project.common.util.TemplateUtils;
import com.yhert.project.common.util.net.HttpUtils;
import com.yhert.project.common.util.net.NetUtils;

/**
 * 调用淘宝的Ip到地址的映射接口
 * 
 * @author Ricardo Li 2017年10月16日 下午2:14:20
 *
 */
public class TaobaoIp2AddressImpl implements Ip2Address {
	/**
	 * IP地址查询物理地址的API
	 */
	public static final String IP_ADDRESS_API = "http://ip.taobao.com/service/getIpInfo.php?ip=@[ip]";

	/**
	 * 通过Ip获得相关信息
	 * 
	 * @param ip
	 *            需要查询的IP
	 * @return 地址信息
	 */
	@Override
	public SystemIpAddress getAddressByIp(String ip) {
		try {
			CommonFunUtils.nullException(ip, "地址不能为空");
			if (!NetUtils.isIpAddress(ip)) {
				ip = NetUtils.getIpByDomain(ip);
			}
			if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
				SystemIpAddress systemIpAddress = new SystemIpAddress();
				systemIpAddress.setIp(ip);
				systemIpAddress.setCountry("本地IP");
				systemIpAddress.setIsp("本地");
				return systemIpAddress;
			}
			String ipUrl = URLEncoder.encode(ip, "utf-8");
			String ipU = TemplateUtils.templatedeal(IP_ADDRESS_API, (key) -> {
				if ("ip".equals(key)) {
					return ipUrl;
				} else {
					return null;
				}
			});
			String ipQueryResult = HttpUtils.get(ipU, null);
			if (CommonFunUtils.isNe(ipQueryResult)) {
				throw new NetException("获得IP地址的物理位置失败：ipQueryResult为null\n" + ip);
			}
			JsonNode jsonNode = BeanUtils.createJsonNode(ipQueryResult);
			if (jsonNode.findValue("code").asInt() != 0) {
				throw new NetException("获得IP地址的物理位置失败：" + jsonNode.findValue("data").asText() + "\n" + ip);
			} else {
				JsonNode dataNode = jsonNode.findValue("data");
				SystemIpAddress systemIpAddress = new SystemIpAddress();
				systemIpAddress.setIp(ip);
				dataNode.findValue("areasss");
				systemIpAddress.setArea(dataNode.findValue("area").asText());
				systemIpAddress.setAreaId(dataNode.findValue("area_id").asText());
				systemIpAddress.setCity(dataNode.findValue("city").asText());
				systemIpAddress.setCityId(dataNode.findValue("city_id").asText());
				systemIpAddress.setCountry(dataNode.findValue("country").asText());
				systemIpAddress.setCountryId(dataNode.findValue("country_id").asText());
				systemIpAddress.setCounty(dataNode.findValue("county").asText());
				systemIpAddress.setCountyId(dataNode.findValue("county_id").asText());
				systemIpAddress.setIsp(dataNode.findValue("isp").asText());
				systemIpAddress.setIspId(dataNode.findValue("isp_id").asText());
				systemIpAddress.setRegion(dataNode.findValue("region").asText());
				systemIpAddress.setRegionId(dataNode.findValue("region_id").asText());
				return systemIpAddress;
			}
		} catch (Exception e) {
			if (e instanceof NetException) {
				throw (NetException) e;
			} else {
				throw new NetException("获得IP地址的物理位置失败。Ip:" + ip, e);
			}
		}
	}
}
