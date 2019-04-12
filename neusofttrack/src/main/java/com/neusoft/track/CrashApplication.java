package com.neusoft.track;


import android.app.Application;

import com.neusoft.track.cmread.CMTrack;

public class CrashApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		CMTrack track = new CMTrack(getApplicationContext());
		track.setChannelId("000000");
		track.setIMEI("123456789012345");
		track.setLogEnable(true);
		track.setLoginType("1");
		track.setMSISDN("111111111111111111111");
		track.setUserAgent("test_ua_1");
		track.setUserId("222222222222222222222");
	}
}
