package com.coolweather.app.util;

import android.database.Cursor;
import android.text.TextUtils;

import com.coolweather.app.R.string;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.JsonCity;
import com.coolweather.app.model.JsonCounty;
import com.coolweather.app.model.JsonModel;
import com.coolweather.app.model.JsonProvince;
import com.coolweather.app.model.Province;
import com.google.gson.Gson;

public class Utility {

	public static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
		if(!TextUtils.isEmpty(response)){
			Gson gson = new Gson();
			//获取全部数据
			JsonModel apps = gson.fromJson(response, JsonModel.class);
			for (JsonProvince province : apps.getProvinceList()) {
				//创建Province
				Province p = new Province();
				p.setProvinceCode(province.getName());
				p.setProvinceName(province.getName());				
				coolWeatherDB.saveProvince(p);
				
				coolWeatherDB.saveCities(p, apps.getCityList(), apps.getCountyList());
			}
			return true;
		}		
		return false;
	}
	
	public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId){
		if(!TextUtils.isEmpty(response)){
			Gson gson = new Gson();
			JsonModel apps = gson.fromJson(response, JsonModel.class);
			for (JsonCity city : apps.getCityList()) {
				City c = new City();
				c.setCityCode(city.getCityName());
				c.setCityName(city.getCityName());
				c.setProvinceId(provinceId);
				coolWeatherDB.saveCity(c);				
			}
			return true;
		}
		return false;
	}
	
	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId) {
		if(!TextUtils.isEmpty(response)){
			Gson gson = new Gson();
			JsonModel apps = gson.fromJson(response, JsonModel.class);
			for (JsonCounty county : apps.getCountyList()) {
				County c = new County();
				c.setCountyCode(county.getCountyName());
				c.setCountyName(county.getCountyName());
				c.setCityId(cityId);
				coolWeatherDB.saveCounty(c);
			}
			return true;
		}
		return false;
	}
	
}
