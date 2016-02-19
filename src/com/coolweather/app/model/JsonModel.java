package com.coolweather.app.model;

import java.util.List;

public class JsonModel {
	private List<JsonProvince> provinceList;
	public List<JsonProvince> getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List<JsonProvince> provinceList) {
		this.provinceList = provinceList;
	}
	
	private List<JsonCity> cityList;
	public List<JsonCity> getCityList() {
		return cityList;
	}
	public void setCityList(List<JsonCity> cityList) {
		this.cityList = cityList;
	}
	
	private List<JsonCounty> countyList;
	public List<JsonCounty> getCountyList() {
		return countyList;
	}
	public void setCountyList(List<JsonCounty> countyList) {
		this.countyList = countyList;
	}
}
