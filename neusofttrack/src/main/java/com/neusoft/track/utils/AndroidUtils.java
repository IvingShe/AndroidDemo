package com.neusoft.track.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

import com.neusoft.track.base.TrackBase;
import com.neusoft.track.pojo.Statition;
import com.neusoft.track.thread.BatteryDataRunnable;

public class AndroidUtils {
	
	private static final String TAG = "TrackLog";
	private static final String SHARED_PREFERENCES_KEY_APPRTDATA = "apprtdata";
	private static final String SHARED_PREFERENCES_KEY_APPCFGDATA = "appcfgdata";
	public static final String DEV_FILE = "/proc/self/net/dev";
	public static final String ETHLINE = "eth0";
	public static final String GPRSLINE = "rmnet0";
	public static final String WIFILINE = "tiwlan0";
	private static File srcfile;
	private static String[][] traffic = { { "0", "0" }, { "0", "0" } };
	public static final int UNSUPPORTED = -1;
	static List<String> listStr = new ArrayList();

	public static final String ALBUM_PATH = Environment
			.getExternalStorageDirectory() + "/";
	public static final int BUFFER_SIZE = 4096;
	
	public static final int SIM_CARD_UNKOWN = 0;
	public static final int SIM_CARD_MOBILE = 1;
	public static final int SIM_CARD_UNICOM = 2;
	public static final int SIM_CARD_CTMOBILE = 3;


	public static String getCurrentCpuFreq(Context context) {
		String result = "N/A";
		if (context == null)
			return "";
		try {
			RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
			String load = reader.readLine();
			String[] toks = load.split(" ");
			long idle1 = Long.parseLong(toks[5]);
			long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
			try {
				Thread.sleep(100L);
			} catch (Throwable localThrowable) {
			}
			reader.seek(0L);
			load = reader.readLine();
			reader.close();
			toks = load.split(" ");
			long idle2 = Long.parseLong(toks[5]);
			long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
			result = Long.toString(100L * (cpu2 - cpu1)
					/ (cpu2 + idle2 - (cpu1 + idle1)));
			return result;
		} catch (Throwable localThrowable1) {
		}
		return result;
	}

	public static String getGpsInfo(Context context) {
		if (context == null) {
			return "";
		}
//		boolean isAllowSdkToGetGps = TrackBase.getInstance().isAllowSdkToGetGps();
//		String coordinate = "0";
//		double lat = 0.0D;
//		double lng = 0.0D;
//		if (isAllowSdkToGetGps) {
//			lat = TrackBase.getInstance().getLocInfo().getLat();
//			lng = TrackBase.getInstance().getLocInfo().getLon();
//			if ((lat == 0.0D) || (lng == 0.0D)) {
//				LocationManager locMan = (LocationManager) context
//						.getSystemService("location");
//				if (locMan.isProviderEnabled("gps")) {
//					String serviceName = "location";
//					LocationManager locationManager = (LocationManager) context
//							.getSystemService(serviceName);
//
//					Criteria criteria = new Criteria();
//					criteria.setAccuracy(1);
//
//					criteria.setAltitudeRequired(false);
//					criteria.setBearingRequired(false);
//					criteria.setCostAllowed(true);
//					criteria.setPowerRequirement(1);
//
//					String provider = locationManager.getBestProvider(criteria,
//							true);
//
//					Location location = locationManager
//							.getLastKnownLocation(provider);
//
//					if (location != null) {
//						lat = location.getLatitude();
//						lng = location.getLongitude();
//						coordinate = lat + "*" + lng;
//					} else {
//						coordinate = "1";
//					}
//				} else {
//					coordinate = "1";
//				}
//			} else {
//				coordinate = lat + "*" + lng;
//			}
//		} else {
//			coordinate = "0";
//		}
		return "";
	}

	public static String getIMEI(Context context) {
		if (context == null) {
			return "";
		}
		String imei = "";
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService("phone");
		 try {
			 imei  = telephonyManager.getDeviceId();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } 
		
		return imei;
	}

	public static String getNetAgent(Context context) {
		if (context == null) {
			return "";
		}
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService("phone");
		String IMSI = telephonyManager.getSubscriberId();
		if (IMSI == null) {
			IMSI = "";
		}
		return IMSI;
	}
	
	public static String getPhoneNumber(Context context)
	{
		if (context == null)
		{
			return "";
		}
		try
		{
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm == null)
				return null;
			else
			{
				return tm.getLine1Number();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String getNetType(Context context)
	{
		if(context == null)
		{
			return null;
		}
		
		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info == null)
		{
			return null;
		}
		if (info.getType() == ConnectivityManager.TYPE_WIFI)
		{
			return "WIFI";
		}
		String extraInfo = info.getExtraInfo();
		if (extraInfo == null) {
			return "UNKNOWN";
			}
		else if ("cmnet".equals(extraInfo))
			{
				return "CMNET";
			}
		else if ("cmwap".equals(extraInfo))
			{
				return "CMWAP";
			}
		else 
			return "UNKNOWN";
	}
	
	public static int getSIMCardType(Context context)
	{
		String imsi = getIMSI(context);
		if (imsi != null && imsi.length() >= 15)
		{
			String mnc = imsi.substring(0, 5);
			if ("46000".equals(mnc) || "46002".equals(mnc) || "46007".equals(mnc))
			{
				//中国移动       
				return SIM_CARD_MOBILE;
			}
			else if ("46001".equals(mnc) || "46006".equals(mnc))
			{
				//中国联通
				return SIM_CARD_UNICOM;
			}
			else if ("46003".equals(mnc) || "46005".equals(mnc))
			{
				//中国电信     
				return SIM_CARD_CTMOBILE;
			}
		}
		return SIM_CARD_UNKOWN;
	}
	
	public static String getIMSI(Context context)
	{
		if (context == null)
		{
			return null;
		}
		String IMSI = null;
		try
		{
			boolean isDualMode = isDualMode();
			if (isDualMode)
			{
				int index = getMainCardIndex(context);

				if (index != -1)
				{
					IMSI = getSubscriberId(index);
				}
			}
			else
			{
				IMSI = getSubscriberId(0);
				if (IMSI == null || IMSI.length() == 0)
				{
					IMSI = getSubscriberId(1);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (null == IMSI)
		{
			try{
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			IMSI = telephonyManager.getSubscriberId();
			}catch(Exception e)
			{
				e.printStackTrace();
				IMSI = null;
			}
		}
		return IMSI;
	}
	
	public static int getMainCardIndex(Context context)
	{
		int index = 0;
		index = getMTK(context);
		if (index == -1)
		{
			index = getSPR(context);
			if (index == -1)
			{
				index = getGaotong();
			}
		}
		if (index == 0 || index == 1)
		{
			return index;
		}
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String IMSI = telephonyManager.getSubscriberId();

		if (IMSI != null && IMSI.equals(getSubscriberId(0)))
		{
			return 0;
		}
		else if (IMSI != null && IMSI.equals(getSubscriberId(1)))
		{
			return 1;
		}
		return -1;
	}
	
	private static int getSPR(Context context)
	{
		try
		{
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			Method method = Class.forName("android.telephony.TelephonyManager").getDeclaredMethod("getDefaultDataPhoneId", new Class[] {});
			method.setAccessible(true);
			Object index = method.invoke(tm, new Object[] {});
			return (Integer) index;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}

	private static int getGaotong()
	{
		try
		{
			Method method = Class.forName("android.telephony.SmsManager").getDeclaredMethod("getDefault", new Class[] {});
			method.setAccessible(true);
			Object param = method.invoke(null, new Object[] {});
			method = Class.forName("android.telephony.SmsManager").getDeclaredMethod("getPreferredSmsSubscription", new Class[] {});
			method.setAccessible(true);
			Object index = method.invoke(param, new Object[] {});
			return (Integer) index;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	private static int getMTK(Context context)
	{
		try
		{
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			Method method = Class.forName("android.telephony.TelephonyManager").getDeclaredMethod("getSmsDefaultSim", new Class[] {});
			method.setAccessible(true);
			Object index = method.invoke(tm, new Object[] {});
			return (Integer) index;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public static boolean isDualMode()
	{
		String imsi1 = getSubscriberId(0);
		String imsi2 = getSubscriberId(1);
		if (imsi1 != null && imsi2 != null)
		{
			return true;
		}
		return false;
	}
	
	private static String getSubscriberId(int cardIndex)
	{
		String name = null;
		name = cardIndex == 1 ? "iphonesubinfo2" : "iphonesubinfo";
		try
		{
			Method method = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", new Class[] { String.class });
			method.setAccessible(true);
			Object param = method.invoke(null, new Object[] { name });
			if ((param == null) && (cardIndex == 1))
				param = method.invoke(null, new Object[] { "iphonesubinfo1" });
			if (param == null)
				return null;
			method = Class.forName("com.android.internal.telephony.IPhoneSubInfo$Stub").getDeclaredMethod("asInterface", new Class[] { IBinder.class });
			method.setAccessible(true);
			Object stubObj = method.invoke(null, new Object[] { param });
			return (String) stubObj.getClass().getMethod("getSubscriberId", new Class[0]).invoke(stubObj, new Object[0]);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static String getOsInfo() {
		String os = Build.VERSION.RELEASE;
		if ((os == null) || (os.equalsIgnoreCase("null")))
			os = "";
		else {
			os = "android" + os;
		}
		return os;
	}

	public static String getPhoneDevBuild() {
		String dev_build = Build.MODEL;
		return dev_build;
	}

	public static Statition getStatition(Context context) {
		try {
			TelephonyManager mTelMan = (TelephonyManager) context
					.getSystemService("phone");
			String operator = mTelMan.getNetworkOperator();

			String mcc = null;
			String mnc = null;
			if ((operator != null) && (operator.length() > 3)
					&& (!operator.equalsIgnoreCase("null"))) {
				mcc = operator.substring(0, 3);
				mnc = operator.substring(3);
			} else {
				mcc = "";
				mnc = "";
			}

			int cid = 0;
			int lac = 0;
			if (mTelMan.getNetworkType() == 4) {
				CdmaCellLocation location = (CdmaCellLocation) mTelMan
						.getCellLocation();
				lac = location.getNetworkId();
				cid = location.getBaseStationId();
			} else {
				GsmCellLocation location = (GsmCellLocation) mTelMan
						.getCellLocation();
				if (location != null) {
					cid = location.getCid();
					lac = location.getLac();
				}
			}
			Statition statition = new Statition();
			statition.setMcc(mcc);
			statition.setMnc(mnc);
			statition.setCid(cid);
			statition.setLac(lac);
			return statition;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String buildStatitionInfo4Behaviour(Context context) {
		if (context == null) {
			return "";
		}
		boolean isAllowSdkToGetStatitionInfo = TrackBase.getInstance().isAllowSdkToGetStatitionInfo();
		StringBuilder statitionInfoBuilder = new StringBuilder();
		if (isAllowSdkToGetStatitionInfo) {
			Statition statition = getStatition(context);
			if (statition == null) {
				return null;
			}
			String mcc = statition.getMcc();
			if (mcc == null) {
				mcc = "";
			}
			String mnc = statition.getMnc();
			if (mnc == null) {
				mnc = "";
			}
			int cid = statition.getCid();
			if (cid < 0) {
				cid = 0;
			}

			int lac = statition.getLac();
			if (lac < 0) {
				lac = 0;
			}
			statitionInfoBuilder.append("&mcc=");
			statitionInfoBuilder.append(mcc);
			statitionInfoBuilder.append("&mnc=");
			statitionInfoBuilder.append(mnc);
			statitionInfoBuilder.append("&cid=");
			statitionInfoBuilder.append(cid);
			statitionInfoBuilder.append("&lac=");
			statitionInfoBuilder.append(lac);
		} else {
			statitionInfoBuilder.append("&mcc=");
			statitionInfoBuilder.append("");
			statitionInfoBuilder.append("&mnc=");
			statitionInfoBuilder.append("");
			statitionInfoBuilder.append("&cid=");
			statitionInfoBuilder.append(0);
			statitionInfoBuilder.append("&lac=");
			statitionInfoBuilder.append(0);
		}
		return statitionInfoBuilder.toString();
	}

	public static String buildStatitionInfo4BAC(Context context) {
		if (context == null) {
			return "";
		}
		boolean isAllowSdkToGetStatitionInfo = TrackBase.getInstance().isAllowSdkToGetStatitionInfo();
		StringBuilder statitionInfoBuilder = new StringBuilder();
		if (isAllowSdkToGetStatitionInfo) {
			Statition statition = getStatition(context);

			if (statition == null) {
				return null;
			}
			String mcc = statition.getMcc();

			if (mcc == null) {
				mcc = "";
			}
			String mnc = statition.getMnc();
			if (mnc == null) {
				mnc = "";
			}
			int cid = statition.getCid();
			if (cid < 0) {
				cid = 0;
			}

			int lac = statition.getLac();
			if (lac < 0) {
				lac = 0;
			}
			statitionInfoBuilder.append(mcc);
			statitionInfoBuilder.append("-");
			statitionInfoBuilder.append(mnc);
			statitionInfoBuilder.append("-");
			statitionInfoBuilder.append(cid);
			statitionInfoBuilder.append("-");
			statitionInfoBuilder.append(lac);
		} else {
			statitionInfoBuilder.append("");
			statitionInfoBuilder.append("-");
			statitionInfoBuilder.append("");
			statitionInfoBuilder.append("-");
			statitionInfoBuilder.append(0);
			statitionInfoBuilder.append("-");
			statitionInfoBuilder.append(0);
		}
		return statitionInfoBuilder.toString();
	}

	/**
	 * 获取运行时内存剩余内存占比, 
	 * for example 20%, return 20
	 * */
	public static int getMemoryLeftPercent(Context context){
		int percent = 0;
		try{
			percent = (int) ((1.0 * getAvailMemory(context)/getTotalMemory(context)) * 100);
		 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return percent;
	}
	
	/**
	 * 获取总的运行时内存
	 * 
	 * */
	public static long getTotalMemory(Context context) {
		if (context == null) {
			return 1;
		}
		String str1 = "/proc/meminfo";

		long initial_memory = 0L;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			String str2 = localBufferedReader.readLine();
            Log.d(TAG, "meminfo:" + str2);
            
			String   arrayOfString[] = str2.replaceAll("\\s+", "").split(":"); //with kb

			int arrayLen = arrayOfString.length;
			if(arrayLen>=2){
				int len = arrayOfString[1].length();
				
				if(len>2){ //remove kb
					initial_memory = Integer.valueOf(arrayOfString[1].substring(0, len-2)).intValue() * 1024;
				}
			}
			localBufferedReader.close();
		} catch (Throwable localThrowable) {
		}
		
		 Log.d(TAG, "Total_memory:" + initial_memory);
		
		return initial_memory;
//		return Formatter.formatFileSize(((Activity) context).getBaseContext(),
//				initial_memory);
	}

	/**
	 * 获取可用运行时内存
	 * */
	public static long getAvailMemory(Context context) {
		if (context == null) {
			return 1;
		}
		ActivityManager am = (ActivityManager) context
				.getSystemService("activity");
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		
		Log.d(TAG, "getAvailMemory:" + mi.availMem);
			
		return mi.availMem;
		
//		return Formatter.formatFileSize(((Activity) context).getBaseContext(),
//				mi.availMem);
	}

	/**
	 * 获取手机内部存储剩余内存占比, 
	 * for example 20%, return 20
	 * */
	public static int getStorageLeftPercent(Context context){
		int percent = 0;
		try{
			percent = (int) ((1.0 * getAvailableInternalMemorySize(context)/getTotalInternalMemorySize(context)) * 100);
		 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return percent;
	}
	
	 /**
     * 获取手机内部剩余存储空间
     * @return
     */
    public static long getAvailableInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();
        Log.d(TAG, "getAvailableInternalMemorySize, data path:" + path.getPath());
        
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        
        return availableBlocks * blockSize;
//        return Formatter.formatFileSize(((Activity) context).getBaseContext(),
//        		availableBlocks * blockSize);
//        return String.valueOf(availableBlocks * blockSize);
    }

    /**
     * 获取手机内部总的存储空间
     * @return
     */
    public static long getTotalInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();
        Log.d(TAG, "getTotalInternalMemorySize, data path:" + path.getPath());
        
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        
        return totalBlocks * blockSize;
//        return Formatter.formatFileSize(((Activity) context).getBaseContext(),
//        		totalBlocks * blockSize);
      
    }

    /**
	 * 获取SDCard剩余存储占比, 
	 * for example 20%, return 20
	 * */
	public static int getExternalStorageLeftPercent(Context context){
		int percent = 0;
		try{
			percent = (int) ((1.0 * getAvailableExternalMemorySize(context)/getTotalExternalMemorySize(context)) * 100);
		 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return percent;
	}
	
    /**
     * 获取SDCARD剩余存储空间
     * @return
     */
    public static long getAvailableExternalMemorySize(Context context) {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            Log.d(TAG, "getAvailableExternalMemorySize, data path:" + path.getPath());
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            
            return availableBlocks * blockSize;
//            return Formatter.formatFileSize(((Activity) context).getBaseContext(),
//            		availableBlocks * blockSize);
            
//            return String.valueOf(availableBlocks * blockSize);
        } else {
            return 1;
        }
    }

    /**
     * 获取SDCARD总的存储空间
     * @return
     */
    public static long getTotalExternalMemorySize(Context context) {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            Log.d(TAG, "getTotalExternalMemorySize, data path:" + path.getPath());
            StatFs stat = new StatFs(path.getPath());
          
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            
            return totalBlocks * blockSize;
//            return Formatter.formatFileSize(((Activity) context).getBaseContext(),
//            		totalBlocks * blockSize);
            
//            return String.valueOf(totalBlocks * blockSize);
        } else {
            return 1;
        }
    }
    
    /**
     * SDCARD是否存
     */
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    public static String getProcessName(Context context){
    	
    	int pid = android.os.Process.myPid();
    	
    if(context != null)
    {
    	try{
    		
    		ActivityManager mActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
    		List<RunningAppProcessInfo> processList = mActivityManager.getRunningAppProcesses();
    		if(processList != null)
    		{
    			for (RunningAppProcessInfo appProcess : processList)
    			{ 
    				if (appProcess.pid == pid) 
    				{ 			
    					return appProcess.processName;  
    				}
    			}
    		}
    	}catch(Exception e)
    	{
    		return null;
    	}
    }
    	return null; 
    	
    }

    public static String getThreadName(Context context){
    	 return Thread.currentThread().getName(); 
    }
	
	
	private static void setTraffic(int type, String rx, String tx) {
		if (type > 1) {
			return;
		}
		traffic[type][0] = rx;
		traffic[type][1] = tx;
	}

	public static String getMacAddress(Context ctx) {
		WifiManager wifi = (WifiManager) ctx.getSystemService("wifi");
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	private static synchronized void setTraffic() {
		if (srcfile == null) {
			srcfile = new File("/proc/self/net/dev");
		}
		FileReader fstream = null;
		BufferedReader in = null;
		try {
			fstream = new FileReader(srcfile);
			in = new BufferedReader(fstream, 128);
			String line;
			while ((line = in.readLine()) != null) {
				String[] segs = line.trim().split(":");
				listStr.add(line);
				if ((segs[0].equals("rmnet0")) || (segs[0].equals("tiwlan0"))
						|| (segs[0].equals("eth0"))) {
					String[] temp = segs[1].trim().split("\\t+|\\s+");
					if (temp.length > 8) {
						setTraffic(0, temp[0], temp[8]);
					}
				}
			}
		} catch (FileNotFoundException localFileNotFoundException) {
			if (fstream != null)
				try {
					fstream.close();
				} catch (Throwable localThrowable) {
				}
			if (in != null)
				try {
					in.close();
				} catch (Throwable localThrowable1) {
				}
		} catch (Throwable localThrowable2) {
			if (fstream != null)
				try {
					fstream.close();
				} catch (Throwable localThrowable3) {
				}
			if (in != null)
				try {
					in.close();
				} catch (Throwable localThrowable4) {
				}
		} finally {
			if (fstream != null)
				try {
					fstream.close();
				} catch (Throwable localThrowable5) {
				}
			if (in != null)
				try {
					in.close();
				} catch (Throwable localThrowable6) {
				}
		}
	}

	public static long getMobileRxBytes() {
		setTraffic();
		try {
			return Long.parseLong(traffic[0][0]);
		} catch (Throwable e) {
		}
		return -1L;
	}

	public static List<String> getStringList() {
		return listStr;
	}

	public static double getTotalTxBytes(Context context) {
		if (context == null) {
			return 0.0D;
		}
		int uid = context.getApplicationContext().getApplicationInfo().uid;
		double l = 0.0D;
		l = TrafficStats.getUidTxBytes(uid) == -1L ? 0.0D : TrafficStats
				.getUidTxBytes(uid) / 1024.0D;
		return l;
	}

	public static double getTotalRxBytes(Context context) {
		if (context == null) {
			return 0.0D;
		}
		int uid = context.getApplicationContext().getApplicationInfo().uid;
		double l = 0.0D;
		l = TrafficStats.getUidTxBytes(uid) == -1L ? 0.0D : TrafficStats
				.getUidRxBytes(uid) / 1024.0D;
		return l;
	}

	public static long getBattery(Context context) {
		if (context == null) {
			return 0L;
		}
		String strBatteryData = BatteryDataRunnable.getDataByKey("battery");

		int res = 0;
		if (strBatteryData != null)
			try {
				res = Integer.parseInt(strBatteryData);
			} catch (Throwable localThrowable) {
			}
		return res;
	}

	public static String getRS(Context context) {
		if (context == null) {
			return "";
		}
		String screen = "0";
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		if (dm != null) {
			int width = dm.widthPixels;
			int height = dm.heightPixels;
			if (width > height)
				screen = height + "*" + width;
			else {
				screen = width + "*" + height;
			}
		}

		return screen;
	}

	public static void saveUUID(Context context, String uuid) {
		try {
			SharedPreferences preferences = context.getSharedPreferences(
					"apprtdata", 0);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("uuid", uuid);
			editor.commit();
		} catch (Throwable t) {
			NLog.i("", " 将uuid保存在手机上出现异常:" + t);
		}
	}

	public static String readUUID(Context context) {
		String uuid = null;
		if (context == null)
			return "";
		try {
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					"apprtdata", 0);
			uuid = sharedPreferences.getString("uuid", "");
		} catch (Throwable t) {
			NLog.i("", " 读取uuid出现异常:" + t);
		}
		return uuid;
	}

	public static Long readToBackTm(Context context) {
		Long lastbacktm = null;
		try {
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					"apprtdata", 0);
			lastbacktm = Long.valueOf(sharedPreferences.getLong("backtm", 0L));
		} catch (Throwable t) {
			NLog.i("", " 读取进入后台的时间出现异常:" + t);
		}
		return lastbacktm;
	}

	public static void saveToBackTm(Context context, Long backtime) {
		try {
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					"apprtdata", 0);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putLong("backtm", backtime.longValue());
			editor.commit();
		} catch (Throwable t) {
			NLog.i("", " 保存进入后台的时间出现异常:" + t);
		}
	}

	public static void saveCfgData(Context context, Map<String, String> cfg) {
		if (cfg == null) {
			return;
		}
		SharedPreferences preferences = context.getSharedPreferences(
				"appcfgdata", 0);

		Iterator it = cfg.keySet().iterator();
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = (String) cfg.get(key);
			editor.putString(key, value);
		}
		editor.commit();
	}

	public static void saveCfgData(Context context, String key, String value) {
		try {
			SharedPreferences preferences = context.getSharedPreferences(
					"appcfgdata", 0);

			SharedPreferences.Editor editor = preferences.edit();

			editor.putString(key, value);

			editor.commit();
		} catch (Throwable t) {
			NLog.i("", " 将服务器配置保存在手机上出现异常:" + t);
		}
	}

	public static Map<String, String> readCfgData(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"appcfgdata", 0);

		Map m = sharedPreferences.getAll();
		return m;
	}

	public static String readCfgData(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"appcfgdata", 0);
		String v = sharedPreferences.getString(key, "");
		return v;
	}

//	public static String requestUUID(Context context, String appkey) {
//		String uuidReqUrl = Track.getROOT_URL() + "track/uuid.php?" + "_at="
//				+ appkey;
//		String uuid = "";
//		try {
//			JSONObject jo = new JSONObject(CommuHandle.sendRequest(uuidReqUrl));
//			String str_uuid = jo.getString("uuid");
//			String status = jo.getString("status");
//			if ((str_uuid.equals("")) && (status.equals("0")))
//				uuid = str_uuid;
//		} catch (Throwable e) {
//		}
//
//		return uuid;
//	}

	public static byte[] saveFile(InputStream bm, String fileName) {
		ByteArrayOutputStream swapStream = null;
		FileOutputStream fo = null;
		try {
			swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while (true) {
				rc = bm.read(buff, 0, 100);
				if (rc <= 0) {
					break;
				}
				swapStream.write(buff, 0, rc);
			}
			fo = new FileOutputStream(ALBUM_PATH + fileName);
			fo.write(swapStream.toByteArray());
		} catch (Throwable localThrowable) {
			try {
				swapStream.close();
				bm.close();
				fo.close();
			} catch (Throwable localThrowable1) {
			}
		} finally {
			try {
				swapStream.close();
				bm.close();
				fo.close();
			} catch (Throwable localThrowable2) {
			}
		}
		return swapStream.toByteArray();
	}

	public static byte[] readFile(String path) {
		FileInputStream inputStream = null;
		ByteArrayOutputStream outStream = null;
		try {
			inputStream = null;
			File file = new File(path);
			inputStream = new FileInputStream(file);
			byte[] temp = new byte[1024];
			int size = 0;
			outStream = new ByteArrayOutputStream();
			while ((size = inputStream.read(temp)) != -1) {
				outStream.write(temp, 0, size);
			}
			Date date = new Date(file.lastModified());
			long da = date.getTime();
			long miao = System.currentTimeMillis();
			if (miao - da > 86400000L) {
				file.delete();
				throw new FileNotFoundException();
			}
		} catch (FileNotFoundException localFileNotFoundException) {
		} catch (Throwable localThrowable1) {
		} finally {
			try {
				outStream.close();
				inputStream.close();
			} catch (Throwable localThrowable3) {
			}
		}
		try {
			outStream.close();
			inputStream.close();
		} catch (Throwable localThrowable4) {
		}
		return outStream.toByteArray();
	}

	public static String readClickurl(String i) {
		FileInputStream inputStream = null;
		ByteArrayOutputStream outStreams = null;
		File file = new File(ALBUM_PATH + i + i);
		try {
			outStreams = new ByteArrayOutputStream();
			inputStream = new FileInputStream(file);
			byte[] temp = new byte[1024];
			int sizes = 0;
			while ((sizes = inputStream.read(temp)) != -1)
				outStreams.write(temp);
		} catch (FileNotFoundException localFileNotFoundException) {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Throwable localThrowable) {
			}
		} catch (Throwable localThrowable1) {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Throwable localThrowable2) {
			}
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Throwable localThrowable3) {
			}
		}
		return outStreams.toString();
	}

	public static String getPhoneDirect(Context context) {
		String direct = null;
		if (context == null)
			return "";
		try {
			DisplayMetrics dm = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(dm);
			int screenWidth = dm.widthPixels;
			int screenHeigh = dm.heightPixels;

			if (screenWidth > screenHeigh)
				direct = "0";
			else
				direct = "1";
		} catch (Throwable e) {
			return "";
		}
		return direct;
	}
	
	public static String getExternalStorageRootPath()
	{
		String externalStorageRootPath = null;

		if (isExternalStorageMounted())
		{
			File externalStorageRoot = Environment.getExternalStorageDirectory();
			externalStorageRootPath = externalStorageRoot.getPath();
		}

		return externalStorageRootPath;
	}

	public static boolean isExternalStorageMounted()
	{
		boolean isExternalStorageMounted = false;

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
		{
			isExternalStorageMounted = true;
		}

		return isExternalStorageMounted;
	}
}
