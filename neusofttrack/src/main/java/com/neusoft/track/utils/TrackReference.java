package com.neusoft.track.utils;

import com.neusoft.track.cmread.LogLevel;

import android.content.Context;
import android.content.SharedPreferences;

public class TrackReference {

	protected static SharedPreferences mPreferences;
	
	protected static SharedPreferences.Editor mEditor;

	private static final String PREFERENCE_FILE_NAME = "tracklib";
	
	private static String PHONE_NUM = "phoneNum";
	private static String LOCATION = "location";
	private static String EXPIRED_TIME = "expiredTime";
	private static String OPEN_LOG_LEVEL = "openLogLevel";
	private static String MAX_LOG_SIZE = "maxLogSize";
	private static String ONLY_WIFI_FLAG = "only_wifi_flag"; 
	private static String MSISDN = "msisdn";
	
	public static SharedPreferences getMPreferences()
	{
		return mPreferences;
	}

	public static void load(Context a)
	{
		try
		{
			mPreferences = a.getSharedPreferences(PREFERENCE_FILE_NAME, 0);
			mEditor = mPreferences.edit();	
		}
		catch (Exception e)
		{
		}
	}
	
	public static boolean save()
	{
		if (mEditor == null)
		{
			return false;
		}

		return mEditor.commit();
	}
	
//	public static void setPhoneNum(String phoneNum)
//	{
//		if (mEditor == null)
//		{
//			return;
//		}
//		mEditor.putString(PHONE_NUM, phoneNum);
//		save();
//	}
//
//	public static String getPhoneNum()
//	{
//		if (mPreferences == null)
//		{
//			return null;
//		}
//		return mPreferences.getString(PHONE_NUM, "");
//		
//	}

	public static void setLocation(String location)
	{
		if (mEditor == null)
		{
			return;
		}
		mEditor.putString(LOCATION, location);
		save();
	}

	public static String getLocation()
	{
		if (mPreferences == null)
		{
			return null;
		}
		return mPreferences.getString(LOCATION, "");
		
	}

	
	public static void setExpiredTime(long updateTime)
	{
		if (mEditor == null)
		{
			return;
		}
		long expiredTime = updateTime + 1000 * 60 * 60 * 2; //2Сʱ
		
	//	long expiredTime = updateTime + 1000 * 60 * 10; //5 minute
		
		mEditor.putLong(EXPIRED_TIME, expiredTime);
		save();
	}

	public static long getExpiredTime()
	{
		if (mPreferences == null)
		{
			return 0;
		}
		return mPreferences.getLong(EXPIRED_TIME, 0L);
		
	}
	
	public static void setOpenLogLevel(int logLevel)
	{
		if (mEditor == null)
		{
			return;
		}

		mEditor.putInt(OPEN_LOG_LEVEL, logLevel);
		save();
	}

	public static int getOpenLogLevel()
	{
		if (mPreferences == null)
		{
			return 0;
		}
		return mPreferences.getInt(OPEN_LOG_LEVEL, Integer.valueOf(LogLevel.NO_LOG));
		
	}
	
	public static void setMaxLogSize(int maxSize)
	{
		if (mEditor == null)
		{
			return;
		}

		mEditor.putInt(MAX_LOG_SIZE, maxSize);
		save();
	}

	public static int getMaxLogSize()
	{
		if (mPreferences == null)
		{
			return 0;
		}
		return mPreferences.getInt(MAX_LOG_SIZE, 5); //5MB
		
	}
	
	public static void setOnlyWIFIFlag(boolean flag)
	{
		if (mEditor == null)
		{
			return;
		}

		mEditor.putBoolean(ONLY_WIFI_FLAG, flag);
		save();
	}
	
	public static boolean getOnlyWIFIFlag()
	{
		if (mPreferences == null)
		{
			return true;
		}
		return mPreferences.getBoolean(ONLY_WIFI_FLAG, true);
		
	}
	
	public static void setMsisdn(String msisdn)
	{
		mEditor.putString(MSISDN,msisdn);
		save();
	}
	public static String getMsisdn()
	{
		if (mPreferences == null)
		{
			return "";
		}
		return mPreferences.getString(MSISDN, "");
	}
	
}
