package com.yhert.project.common.util.net.ipaddress;

import java.io.Serializable;

import com.yhert.project.common.util.StringUtils;

/**
 * IP，运营商，地址的映射关系
 * 
 * @author Ricardo Li 2016年7月30日 下午12:58:14
 *
 */
public class SystemIpAddress implements Serializable {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((countryId == null) ? 0 : countryId.hashCode());
		result = prime * result + ((county == null) ? 0 : county.hashCode());
		result = prime * result + ((countyId == null) ? 0 : countyId.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((isp == null) ? 0 : isp.hashCode());
		result = prime * result + ((ispId == null) ? 0 : ispId.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((regionId == null) ? 0 : regionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemIpAddress other = (SystemIpAddress) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (cityId == null) {
			if (other.cityId != null)
				return false;
		} else if (!cityId.equals(other.cityId))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (countryId == null) {
			if (other.countryId != null)
				return false;
		} else if (!countryId.equals(other.countryId))
			return false;
		if (county == null) {
			if (other.county != null)
				return false;
		} else if (!county.equals(other.county))
			return false;
		if (countyId == null) {
			if (other.countyId != null)
				return false;
		} else if (!countyId.equals(other.countyId))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (isp == null) {
			if (other.isp != null)
				return false;
		} else if (!isp.equals(other.isp))
			return false;
		if (ispId == null) {
			if (other.ispId != null)
				return false;
		} else if (!ispId.equals(other.ispId))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SystemIpAddress [ip=" + ip + ", country=" + country + ", countryId=" + countryId + ", area=" + area
				+ ", areaId=" + areaId + ", region=" + region + ", regionId=" + regionId + ", city=" + city
				+ ", cityId=" + cityId + ", county=" + county + ", countyId=" + countyId + ", isp=" + isp + ", ispId="
				+ ispId + "]";
	}

}
