package com.coolweather.app.model;

public class JsonWeatherInfo {
	boolean success;
	WeatherInfo result;
	String msg;
	
	public WeatherInfo getResult() {
		return result;
	}
	public void setResult(WeatherInfo result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean getSuccess(){
		return success;
	}
	public void setSuccess(boolean success){
		this.success = success;
	}
}
