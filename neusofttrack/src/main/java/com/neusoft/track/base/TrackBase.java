package com.neusoft.track.base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.neusoft.track.configfile.CallBackForConfigInfo;
import com.neusoft.track.configfile.ConfigFileManager;
import com.neusoft.track.pojo.LocInfo;
import com.neusoft.track.thread.LogRunnable;
import com.neusoft.track.thread.SendLogRunnable;
import com.neusoft.track.thread.TrackThread;
import com.neusoft.track.utils.AndroidUtils;
import com.neusoft.track.utils.NLog;
import com.neusoft.track.utils.TrackReference;


public abstract class TrackBase implements TrackApi, UncaughtExceptionHandler {

    public static final String TAG = "TrackBase";
	public static final String UNCAUGHT_ID = "00000000";
	public static final String baseURL = "http://record.cmread.com:8080/";
	public static final String getConfigURL = "http://record.cmread.com:8080/";
	public static final String POST_URL = "";
	public static final String UPDATE_CONFIGE_URL = getConfigURL +"ubiquitous.datacollector.web/getxmlconfig.action";
	public static final String UPDATE_CONFIGE_PARA_UID = "?phonenumber=";
	public static final String UPDATE_CONFIGE_PARA_IMEI = "&imei=";
//	http://10.10.57.55:8080/ubiquitous.datacollector.web/execute.action
	
	@SuppressLint("SdCardPath")
	private List<String> mParameters = new ArrayList<String>();

	protected static TrackBase mInstance;
	protected Context mContext;
	private TrackThread mTrackPrepareThread;
	private TrackThread mTrackThread;
	
	private LogFileManager mLogFileManager;
	private LocInfo mLocInfo = new LocInfo();
	
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private boolean isLogEnable = true;
	private boolean isConfigLogEnable = true;
	private boolean isGetGpsAllowed = true;
	private boolean isGetStatitionAllowed = false;
	
	//浠ヤ笅鍙傛暟鍊兼槸闈欐�佺殑
	protected String os = null;
	protected String osVersion = null;
	protected String brand = null;
	protected String model = null;
	protected String screenWidth = null;
	protected String screenHeight = null;
	protected String resolution = null;
	protected String appVersion = null;
	protected String terminalId;
	protected String phoneNumber;
	protected String location;
	//
	protected String signalStrength = null;
	protected int powerLevel = 0;
	private boolean isInited = false;
	private Timer mGetConfigTimer;
	private long mDelayTime = 1000*60*10;

	protected TrackBase(Context context) {
		if (mInstance != null) {
			mInstance.onAppDestroy();
			mInstance = null;
		}
		mInstance = this;
		mContext = context.getApplicationContext();
	}
	
	public void init(boolean logEnable)
	{
		isLogEnable = logEnable;
		if(logEnable)
		{
			appVersion = getVersion();
			initTeminalInfo();
			initParameters();
			mLogFileManager = new LogFileManager(mContext);

			TrackReference.load(mContext);
			ConfigFileManager.getInstance(mContext).updateConifgInfo(cbForConfigInfo,false);
		}
		else
		{
			//...
		}
		//NLog.showToast(mContext, isLogEnable? "日志opened" : "日志closed！"  + System.currentTimeMillis() );
	}
	

	private CallBackForConfigInfo cbForConfigInfo = new CallBackForConfigInfo(){

		@Override
		public void logEnableStatus(boolean isEnabled) {
			if(isLogEnable)
			{
				// TODO Auto-generated method stub
				if(isEnabled){ 
					initLogThread();
				}
				isConfigLogEnable = isEnabled;
				NLog.d(TAG, "configenable: " + isConfigLogEnable);
			}
		//	NLog.showToast(mContext, (isLogEnable && isConfigLogEnable) ? "日志监控正常" : "日志监控不可用！"  + System.currentTimeMillis() );
		}
		
	};
	
	public void reUpdateConfig()
	{
		isInited = false;
		ConfigFileManager.getInstance(mContext).updateConifgInfo(reUpadteConfigInfo,true);
	}
	
	private CallBackForConfigInfo reUpadteConfigInfo = new CallBackForConfigInfo() {
		
		@Override
		public void logEnableStatus(boolean isEnabled) {
			
			if(isLogEnable)
			{
				// TODO Auto-generated method stub
				if(isEnabled){ 
					mLogFileManager.setMaxCacheMemory(ConfigFileManager.getInstance(mContext).getMaxLogSize() * 1024 *1024);					
					isInited = true;
				}
				isConfigLogEnable = isEnabled;
				NLog.d(TAG, "configenable: " + isConfigLogEnable);
			}
			
		}
	};
	private void initLogThread()
	{
		if(!isInited && isConfigLogEnable && isLogEnable)
		{
			mLogFileManager.setMaxCacheMemory(ConfigFileManager.getInstance(mContext).getMaxLogSize() * 1024 *1024);
			mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
			Thread.setDefaultUncaughtExceptionHandler(this);
			listenTelephonySignal();
			mTrackThread = new TrackThread("thread_track");
			mTrackThread.start();
			mTrackPrepareThread = new TrackThread("thread_track_prepare");
			mTrackPrepareThread.start();
			registerBatteryReceiver();
			isInited = true;
			startTimer();
		}
		
	}
	
	public String getAppVersion()
	{
		return appVersion;
	}
	
	private String getVersion() {
		try 
		{
			PackageManager manager = mContext.getPackageManager();
			PackageInfo info = manager.getPackageInfo(
					mContext.getPackageName(), 0);
			String version = info.versionName;
			NLog.d(TAG, "version:" + version);
			return version;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static TrackBase getInstance() {
		return mInstance;
	}
	
	protected void addParameter(String parameter) {
		if (mParameters == null) {
			return;
		}
		if (!mParameters.contains(parameter)) {
			mParameters.add(parameter);
		}
	}
	
	@Override
	public void writeLog(final Map<String, String> values){
		
		if(mTrackPrepareThread != null)
		{
			mTrackPrepareThread.post(new Runnable() {
				
				@Override
				public void run() {
					if(mTrackThread != null)
					{
						onPrepareLog(values);
						mTrackThread.post(new LogRunnable(mParameters, values));
					}
				}
			});
		}
		
	}
	

	@Override
	public void offlineSend() {
		
		if(ConfigFileManager.getInstance(mContext).getOnlyWIFIFlag() &&  (!"WIFI".equalsIgnoreCase((AndroidUtils.getNetType(mContext))))){
			
			NLog.d(TAG, "only wifi upload log, but now is not wift mode");
			return;
		}
		
		if(isLogEnable && isConfigLogEnable && isInited)
		{
			if(mTrackPrepareThread != null)
			{
				mTrackPrepareThread.post(new Runnable() {
					
					@Override
					public void run() {
						if(mTrackThread != null && mLogFileManager != null)
						{
							mTrackThread.post(new SendLogRunnable());
						}
					}
				});
			}
		}
	}
	
	@Override
	public void switchAllowSdkToGetGps(boolean allowed) {
		this.isGetGpsAllowed = allowed;
	}

	@Override
	public void switchAllowSdkToGetStatitionInfo(boolean allowed) {
		this.isGetStatitionAllowed = allowed;
	}

	@Override
	public void setLogEnable(boolean enable) {
		this.isLogEnable = enable;
	}
	
	@Override
	public void setLocation(String location)
	{
		this.location = location;
		TrackReference.setLocation(location);
	}
	
	@Override
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	
	protected abstract void onPrepareLog(Map<String, String> values);
	
	protected abstract Map<String, String> createUncaugthLogInfo(String stackInfo);
	
	/**
	* 设置日志所需字段，该接口为抽象方法，不同app，所需字段可能不同，需重写该
	*方法， 设置所需字段
	*/
	protected abstract void initParameters();

	protected  void initDefaultParameters() {

	}

	public boolean isAllowSdkToGetStatitionInfo() {
		return isGetStatitionAllowed;
	}

	public boolean isLogEnable(String logLevel) {
		return isLogEnable && isConfigLogEnable && isInited && ConfigFileManager.getInstance(mContext).isLogEnabledByLevel(logLevel);
	}

	public boolean isAllowSdkToGetGps() {
		return isGetGpsAllowed;
	}

	public void setLatitudeAndLongitude(double latitude, double longitude) {
		mLocInfo.setLat(latitude);
		mLocInfo.setLon(longitude);
  	}

	public LocInfo getLocInfo() {
		return mLocInfo;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		
		if(handleException(ex))
		{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(mDefaultHandler != null)
		{
			mDefaultHandler.uncaughtException(thread, ex);
		} 
//		android.os.Process.killProcess(android.os.Process.myPid());
//		System.exit(1);
	}
	
	public boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		String stackInfo = getExceptionStack(ex);
		writeLog(createUncaugthLogInfo(stackInfo));
		return true;
	}
	
	public static String getExceptionStack(Throwable ex) {
		
		StringBuffer sb = new StringBuffer();
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		return sb.toString();
	}
	
	private void initTeminalInfo() {
		os = "Android";
		osVersion = os + android.os.Build.VERSION.RELEASE;
		brand = android.os.Build.BRAND;
		model = android.os.Build.MODEL;
		getDisplayScreenResolution();
		terminalId = UUIDUtil.getUUID(mContext);
	}



	private void getDisplayScreenResolution() {
		int ver = Build.VERSION.SDK_INT;
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Service.WINDOW_SERVICE);
		android.view.Display display = wm.getDefaultDisplay();
		display.getMetrics(dm);
//		screenWidth = dm.widthPixels + "";
//		if (ver < 13) {
//			screenHeight = dm.heightPixels + "";
//		} else if (ver == 13) {
//			try {
//				Method mt = display.getClass().getMethod("getRealHeight");
//				screenHeight = (Integer) mt.invoke(display) + "";
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else if (ver > 13) {
//			try {
//				Method mt = display.getClass().getMethod("getRawHeight");
//				screenHeight = (Integer) mt.invoke(display) + "";
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		if(screenWidth == null || screenHeight == null)
		{
			screenWidth = dm.widthPixels + "";
			screenHeight = dm.heightPixels + "";
		}
		resolution = screenWidth + "*" + screenHeight;
	}

	private void listenTelephonySignal() {
		final TelephonyManager telmanager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		PhoneStateListener listen = new PhoneStateListener() {
			public void onSignalStrengthsChanged(SignalStrength signalStrength) {
				TrackBase.this.signalStrength = signalStrength.getGsmSignalStrength() + "";
			}
		};
		telmanager.listen(listen, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
	}
	
	private BroadcastReceiver mBatteryInfoReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			try {
				String action = intent.getAction();
				if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
					int level = intent.getIntExtra("level", 0);
					powerLevel = level;
				}
			} catch (Throwable e) {
				NLog.i("", e.toString());
			}
		}
	};
	
	public int getBatteryLevel()
	{
		return powerLevel;
	}
	
	public String getMemoryLeftPercent()
	{
		return String.valueOf(AndroidUtils.getMemoryLeftPercent(mContext));
	}
	
	public String getStorageLeftPercent()
	{
		return String.valueOf(AndroidUtils.getStorageLeftPercent(mContext));
	}
	
	public String getExternalStorageLeftPercent()
	{
		return String.valueOf(AndroidUtils.getExternalStorageLeftPercent(mContext));
	}
	
	public String getPocessName(){
		return AndroidUtils.getProcessName(mContext);
	}
	
	public String getThreadName(){
		return AndroidUtils.getThreadName(mContext);
	}
	
	
	public String getCurrentCpuFreq()
	{
		return AndroidUtils.getCurrentCpuFreq(mContext);
	}
	
	public String getLocation()
	{
		return location == null ? AndroidUtils.getGpsInfo(mContext): location;
	}
	
	public String getPhoneNumber()
	{
		
		String phoneNum = phoneNumber == null ? AndroidUtils.getPhoneNumber(mContext) : phoneNumber;
		NLog.d(TAG, "phoneNum:" + phoneNum);
		
		return phoneNum;
	}
	
	public String getNetType()
	{
		return AndroidUtils.getNetType(mContext);
	}
	
	public int getSIMCardType()
	{
		return AndroidUtils.getSIMCardType(mContext);
	}
	
	public synchronized boolean registerBatteryReceiver() {
		if (this.mContext == null) {
			NLog.i(TAG, "registerBatteryReceiver nothing by null");
			return false;
		}
		try {
			this.mContext.registerReceiver(mBatteryInfoReceiver, new IntentFilter(
					"android.intent.action.BATTERY_CHANGED"));
			NLog.i(TAG, "registerBatteryReceiver success");
		} catch (Throwable e) {
			NLog.i(TAG, e.toString());
			NLog.i(TAG, "registerBatteryReceiver fail");
			return false;
		}
		return true;
	}

	public synchronized boolean unRegisterBatteryReceiver() {
		if (this.mContext == null) {
			NLog.i(TAG, "unRegisterBatteryReceiver nothing");
			return false;
		}
		try {
			if(mBatteryInfoReceiver != null)
			{
				this.mContext.unregisterReceiver(mBatteryInfoReceiver);
			}
			mBatteryInfoReceiver = null;
			NLog.i(TAG, "unRegisterBatteryReceiver success");
		} catch (Throwable e) {
			NLog.i(TAG, e.toString());
			NLog.i(TAG, "unRegisterBatteryReceiver fail");
			return false;
		}
		return true;
	}
	
	@Override
	public void onAppDestroy() {
		if (mTrackThread != null) {
			mTrackThread.quit();
			mTrackThread = null;
		}
		if(mTrackPrepareThread != null)
		{
			mTrackPrepareThread.quit();
			mTrackPrepareThread = null;
		}
		if (mParameters != null) {
//			mParameters.clear();
			mParameters = null;
		}
		unRegisterBatteryReceiver();
		if (mInstance == this) {
			mInstance = null;
		}
		if(mGetConfigTimer != null)
		{
			mGetConfigTimer.cancel();
			mGetConfigTimer.purge();
			mGetConfigTimer = null;
		}
		mLocInfo = null;
		mLogFileManager = null;
		mLogFileManager = null;
		isInited = false;
		mContext = null;
	}

	public static void setDebugEnable(boolean enable)
	{
		NLog.setDebugEnable(enable);
	}
	
	private void startTimer()  //add by zhoukun 
	   {
		   if(mGetConfigTimer != null)
		   {
			   mGetConfigTimer.cancel();
			   mGetConfigTimer.purge();
			   mGetConfigTimer = null;
		   }
		   mGetConfigTimer = new Timer();
		   mGetConfigTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				reUpdateConfig();
			}
		}, mDelayTime,mDelayTime);
	   }
}
