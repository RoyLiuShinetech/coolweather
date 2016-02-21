package com.coolweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.coolweather.app.R.string;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.JsonCity;
import com.coolweather.app.model.JsonCounty;
import com.coolweather.app.model.JsonModel;
import com.coolweather.app.model.JsonProvince;
import com.coolweather.app.model.JsonWeatherInfo;
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
	
	/*
	 * 解析Json
	 * 	{"success":"1",
	 * "result":{"weaid":"58","days":"2016-02-21","week":"星期日","cityno":"langfang","citynm":"廊坊","cityid":"101090601","temperature":"8℃/-4℃","temperature_curr":"-7℃","humidity":"67%","weather":"多云","weather_icon":"http://api.k780.com:88/upload/weather/d/1.gif","weather_icon1":"","wind":"东南风","winp":"1级","temp_high":"8","temp_low":"-4","temp_curr":"-7","humi_high":"0","humi_low":"0","weatid":"2","weatid1":"","windid":"12","winpid":"201"}}
	 * */
	public static void handleWeatherResponse(Context context, String response){
		try {
			if(!TextUtils.isEmpty(response)){
				Gson gson = new Gson();
				JsonWeatherInfo info = gson.fromJson(response, JsonWeatherInfo.class);
				if(info != null && info.getResult() != null){
					String cityName = info.getResult().getCitynm();
					//String weathreCode =info.get
					String temp1 = info.getResult().getTemp_high();
					String temp2 = info.getResult().getTemp_low();
					String currentDay = info.getResult().getDays();
					String weatherDesp = info.getResult().getWeather();
					Date currentTimeDate = new Date();
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");					
					String publishTimeString = format.format(currentTimeDate);
					saveWeatherInfo(context, cityName,temp1,temp2,weatherDesp,publishTimeString);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/*
	 * 将服务器返回的所以天气信息存储在SharedPreferences文件中
	 * */
	private static void saveWeatherInfo(Context context, String cityName,
			String temp1, String temp2, String weatherDesp,
			String publishTimeString) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", cityName);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("current_date", sdf.format(new Date()));
		editor.putString("publish_time", publishTimeString);
		editor.commit();
	}
	
}
