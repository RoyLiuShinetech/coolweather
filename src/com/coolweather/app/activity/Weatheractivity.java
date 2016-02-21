package com.coolweather.app.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.coolweather.app.R;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Weatheractivity extends Activity {
	
	LinearLayout weatherInfoLayout;
	TextView cityNameText;
	TextView publishText;
	TextView weatherDespText;
	TextView temp1Text;
	TextView temp2Text;
	TextView currentDateText;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.weather_layout);
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);
		weatherDespText = (TextView) findViewById(R.id.weather_desp);
		temp1Text = (TextView) findViewById(R.id.temp1);
		temp2Text = (TextView) findViewById(R.id.temp2);
		currentDateText = (TextView) findViewById(R.id.current_date);
		String countyCode = getIntent().getStringExtra("county_code");
		
		if(!TextUtils.isEmpty(countyCode)){
			publishText.setText("同步中...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			try {
				queryWeatherCode(countyCode);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			showWeather();
		}
	}

	private void showWeather() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		cityNameText.setText(prefs.getString("city_name", ""));
		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("今天" + prefs.getString("publish_time", "" + "发布"));
		currentDateText.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
	}

	/*
	 * 查询县级代号所对应的天气代号
	 * */
	private void queryWeatherCode(String countyCode) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String address = "http://api.k780.com:88/?app=weather.today&weaid=1&appkey=17519&sign=2aba27cf3f807367f3e57a9d2391d6f3&format=json";
		queryFromServer(address,countyCode);
	};
	
	private void queryWeatherInfo(String weatherCode) throws UnsupportedEncodingException{
		String address = "http://api.k780.com:88/?app=weather.today&weaid=1&appkey=17519&sign=2aba27cf3f807367f3e57a9d2391d6f3&format=json";
		queryFromServer(address, weatherCode);
	}
	
	private void queryFromServer(final String address, final String type) throws UnsupportedEncodingException {		
		HttpUtil.sendHttpRequest(URLDecoder.decode(address,"UTF-8"), new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				if(!TextUtils.isEmpty(response)){
					Utility.handleWeatherResponse(Weatheractivity.this, response);
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							showWeather();
						}
					});
				}
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						publishText.setText("同步失败");
					}
				});
			}
		});
	}

}
