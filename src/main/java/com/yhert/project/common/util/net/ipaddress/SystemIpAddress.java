package com.yhert.project.common.util.net.ipaddress;

import java.io.Serializable;

import com.yhert.project.common.beans.Model;
import com.yhert.project.common.util.StringUtils;

/**
 * IP，运营商，地址的映射关系
 * 
 * @author Ricardo Li 2016年7月30日 下午12:58:14
 *
 */
public class SystemIpAddress extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ip地址
	 */
	private String ip;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 国家编号
	 */
	private String countryId;
	/**
	 * 地区
	 */
	private String area;
	/**
	 * 地区编号
	 */
	private String areaId;
	/**
	 * 省份
	 */
	private String region;
	/**
	 * 省份编号
	 */
	private String regionId;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 城市编号
	 */
	private String cityId;
	/**
	 * 县
	 */
	private String county;
	/**
	 * 县Id
	 */
	private String countyId;
	/**
	 * 运营商
	 */
	private String isp;
	/**
	 * 运营商Id
	 */
	private String ispId;

	public SystemIpAddress() {
		super();
	}

	public SystemIpAddress(String ip, String country, String countryId, String area, String areaId, String region,
			String regionId, String city, String cityId, String county, String countyId, String isp, String ispId) {
		super();
		this.ip = ip;
		this.country = country;
		this.countryId = countryId;
		this.area = area;
		this.areaId = areaId;
		this.region = region;
		this.regionId = regionId;
		this.city = city;
		this.cityId = cityId;
		this.county = county;
		this.countyId = countyId;
		this.isp = isp;
		this.ispId = ispId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getIspId() {
		return ispId;
	}

	public void setIspId(String ispId) {
		this.ispId = ispId;
	}

	/**
	 * 响应拼接好的地址
	 * 
	 * @return 结果
	 */
	public String toAddress() {
		return getAddress(this);
	}

	/**
	 * 获得拼接后的地址信息
	 * 
	 * @param ipAddress
	 *            IP对象
	 * @return 地址
	 */
	public static String getAddress(SystemIpAddress ipAddress) {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(ipAddress.getCountry()))
			sb.append(ipAddress.getCountry());
		if (!StringUtils.isEmpty(ipAddress.getArea()))
			sb.append(ipAddress.getArea());
		if (!StringUtils.isEmpty(ipAddress.getRegion()))
			sb.append(ipAddress.getRegion());
		if (!StringUtils.isEmpty(ipAddress.getCity()))
			sb.append(ipAddress.getCity());
		if (!StringUtils.isEmpty(ipAddress.getCounty()))
			sb.append(ipAddress.getCounty());
		return sb.toString();
	}

}
