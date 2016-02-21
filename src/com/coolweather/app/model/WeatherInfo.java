package com.coolweather.app.model;

public class WeatherInfo {	
	String days;
	String citynm;
	String weather;
	String temp_low;
	String temp_high;
	
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getCitynm() {
		return citynm;
	}
	public void setCitynm(String citynm) {
		this.citynm = citynm;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getTemp_high() {
		return temp_high;
	}
	public void setTemp_high(String temp_high) {
		this.temp_high = temp_high;
	}
	public String getTemp_low() {
		return temp_low;
	}
	public void setTemp_low(String temp_low) {
		this.temp_low = temp_low;
	}
}
