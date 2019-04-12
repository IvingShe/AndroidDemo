package com.neusoft.track.configfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.util.Xml;
import android.widget.Toast;

import com.neusoft.track.base.LogFileManager;
import com.neusoft.track.base.TrackBase;
import com.neusoft.track.cmread.CMTrack;
import com.neusoft.track.cmread.LogLevel;
import com.neusoft.track.thread.TrackThread;
import com.neusoft.track.utils.AndroidUtils;
import com.neusoft.track.utils.NLog;
import com.neusoft.track.utils.TrackReference;

//get config info from server
public class ConfigFileManager {

	private static String TAG = TrackBase.TAG;

	private static ConfigFileManager mSelf = null;

	private static final int NO_LOG = 0;

	private static final String DATA_CACHE = "dataCache";
	private static final String ONLY_WIFI = "onlyWifi";
	private static final String COLLECT_FLAG = "collectFlag";
	private static NetworkInfo mNetworkInfo;
	private static ConnectivityManager connManager;
	private static final int REQUEST_TIMEOUT = 10 * 1000;// ��������ʱ10����
	private static final int SO_TIMEOUT = 10 * 1000; // ���õȴ����ݳ�ʱʱ��10����

	private int maxLogSize = 5;
	private int openLogLevel = 4;
	private boolean onlyWIFIFlag = false;

	private static Context mContext;
	CallBackForConfigInfo cbForConfigInfo = null;

	public static ConfigFileManager getInstance(Context context) {

		mContext = context;

		if (mSelf == null) {
			mSelf = new ConfigFileManager();
		}
		return mSelf;
	}

	private ConfigFileManager() {
		maxLogSize = TrackReference.getMaxLogSize();
		openLogLevel = TrackReference.getOpenLogLevel();
		onlyWIFIFlag = TrackReference.getOnlyWIFIFlag();
	}

	public void updateConifgInfo(CallBackForConfigInfo cb,boolean isForce){
		// check reference, expired time
//		String configFile = LogFileManager.getInstance().getConfigFilePath();
//		File file = new File(configFile);
		cbForConfigInfo = cb;
//		NLog.d(TAG, "expired time: " + TrackReference.getExpiredTime());
//		NLog.d(TAG, "current time: " + System.currentTimeMillis());
		String expireTime =DateFormat.format("yyyy-MM-dd kk:mm:ss", TrackReference.getExpiredTime()).toString();
		String currentTime = DateFormat.format("yyyy-MM-dd kk:mm:ss", System.currentTimeMillis()).toString();
		NLog.d(TAG, "expired time: " + expireTime);
		NLog.d(TAG, "current time: " + currentTime);
		if(connManager == null)
			connManager =(ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		mNetworkInfo = connManager.getActiveNetworkInfo();
		if (mNetworkInfo != null &&  mNetworkInfo.isConnected() == true)
		{
			if(isForce)
			{
				try{
				TrackThread tt = new TrackThread("update_config");
				tt.start();
				tt.post(new ConfigInfoRunnable());
				}
				catch(OutOfMemoryError error)
				{
					error.printStackTrace();
				}
				return;
			}
			if(TrackReference.getExpiredTime()< System.currentTimeMillis()){
				TrackThread tt = new TrackThread("update_config");
				tt.start();
				tt.post(new ConfigInfoRunnable());
				return;
			}else{
				NLog.d(TAG, "config file exists: isopen:" + (openLogLevel != NO_LOG));
				cbForConfigInfo.logEnableStatus(openLogLevel != NO_LOG);
			}
		}
		else
		{
			NLog.d(TAG, "net connect Invalid,config file exists: isopen:" + (openLogLevel != NO_LOG));
			cbForConfigInfo.logEnableStatus(openLogLevel != NO_LOG);
		}
	}

	public boolean getErrorLogEnabled() {

		return isLogEnabledByLevel(LogLevel.ERROR);
	}

	public boolean getWarnLogEnabled() {

		return isLogEnabledByLevel(LogLevel.WARNING);

	}

	public boolean getInfoLogEnabled() {

		return isLogEnabledByLevel(LogLevel.INFO);
	}

	public boolean getDebugLogEnabled() {

		return isLogEnabledByLevel(LogLevel.DEBUG);
	}

	public boolean isLogEnabledByLevel(String level) {

		int levelInt = Integer.valueOf(level);
		if (levelInt > openLogLevel) {
			return false;
		} else {
			return true;
		}

	}

	public boolean isLogEnabled() {

		return openLogLevel == NO_LOG;

	}

	public int getMaxLogSize() {
		return maxLogSize;
	}

	public boolean getOnlyWIFIFlag() {
		return this.onlyWIFIFlag;
	}

	private class ConfigInfoRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			String VIPPara_UID = TrackReference.getMsisdn();
				
			String VIPPara_IMEI = AndroidUtils.getIMEI(mContext);
			 
			if(VIPPara_UID == null)
			    	VIPPara_UID = "";

			HttpPost httpPost = new HttpPost(TrackBase.UPDATE_CONFIGE_URL
					+ TrackBase.UPDATE_CONFIGE_PARA_UID + VIPPara_UID + TrackBase.UPDATE_CONFIGE_PARA_IMEI + VIPPara_IMEI);
			NLog.d(TAG, "URL:" + TrackBase.UPDATE_CONFIGE_URL
					+ TrackBase.UPDATE_CONFIGE_PARA_UID + VIPPara_UID + TrackBase.UPDATE_CONFIGE_PARA_IMEI + VIPPara_IMEI);

			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					REQUEST_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse;
			HttpEntity httpEntity;

			InputStream inputStream = null;
			try {
				httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8*1000);
				httpResponse = httpClient.execute(httpPost);
				httpEntity = httpResponse.getEntity();
				inputStream = httpEntity.getContent();

				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream));

				String result = "";
				String line = "";

				while (null != (line = bufferedReader.readLine())) {
					result += line.trim();
				}

				NLog.d(TAG, "config info:" + result);

				// result = <?xml version="1.0" encoding="utf-8" ?>
				// <configInfo>
				// <collectFlag>true</collectFlag>
				// <sessionTime>30</sessionTime>
				// <dataCache>5</dataCache>
				// <onlyWifi>true</onlyWifi>
				// <versionLogType>
				// <V1.7.3>0</V1.7.3>
				// <V2.4.1>1</V2.4.1>
				// <V3.0.0>0</V3.0.0>
				// </versionLogType>
				// </configInfo>
				//
				ByteArrayInputStream is = new ByteArrayInputStream(
						result.getBytes("utf-8"));

				parseConfigFile(is, "V"
						+ TrackBase.getInstance().getAppVersion());

				TrackReference.setExpiredTime(System.currentTimeMillis());
			} catch (Exception e) {
				e.printStackTrace();
				NLog.d(TAG, "config info error:" + e.toString()
						+ ", use last config");
				new SimpleConfigInfo().useDefault();

			}catch(Error error ) 
						{
								error.printStackTrace();
								NLog.d(TAG, "config info error:" + error.toString()
										+ ", use last config");
							new SimpleConfigInfo().useDefault();
						}
			finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	private synchronized void parseConfigFile(InputStream is, String appVersion) {

		SimpleConfigInfo sc = new SimpleConfigInfo();
		;
		try {
			XmlPullParser parser = Xml.newPullParser(); // ��android.util.Xml����һ��XmlPullParserʵ��
			parser.setInput(is, "utf-8"); // ���������� ��ָ�����뷽ʽ

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					// sc = new SimpleConfigInfo();
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals(appVersion)) {
						eventType = parser.next();
						sc.logLevel = parser.getText();

					} else if (parser.getName().equals(DATA_CACHE)) {
						eventType = parser.next();
						sc.logSize = parser.getText();

					} else if (parser.getName().equals(ONLY_WIFI)) {
						eventType = parser.next();
						sc.onlyWIFI = "true".equalsIgnoreCase(parser.getText()) ? true
								: false;

					} else if (parser.getName().equalsIgnoreCase(COLLECT_FLAG)) {
						eventType = parser.next();
						sc.isCollectLog = "true".equalsIgnoreCase(parser
								.getText()) ? true : false;

					} else {
						NLog.d(TAG, "parser.getName():" + parser.getName());
					}

					break;
				case XmlPullParser.END_DOCUMENT:
					break;
				}
				eventType = parser.next();
			}

		} catch (Exception e) {
			NLog.d(TAG, "exception:" + e);
			// sc = null;
			e.printStackTrace();
		}
		if (sc != null) {
			sc.save();
			sc = null;
		}
	}

	private class SimpleConfigInfo {

		private String logLevel = LogLevel.NO_LOG;
		private String logSize = "5";
		private boolean onlyWIFI = true;
		private boolean isCollectLog = false;

		public void save() {
			if (!isCollectLog || "".equalsIgnoreCase(logLevel)
					|| "null".equalsIgnoreCase(logLevel)) {
				logLevel = LogLevel.NO_LOG;
			}

			if ("".equalsIgnoreCase(logSize)
					|| "null".equalsIgnoreCase(logSize)) {
				logSize = "5";
			}
			TrackReference.setOpenLogLevel(Integer.valueOf(logLevel));
			TrackReference.setMaxLogSize(Integer.valueOf(logSize));
			TrackReference.setOnlyWIFIFlag(onlyWIFI);

			openLogLevel = Integer.valueOf(logLevel);
			maxLogSize = Integer.valueOf(logSize);
			onlyWIFIFlag = onlyWIFI;

			if (cbForConfigInfo != null) {

				cbForConfigInfo.logEnableStatus(openLogLevel != NO_LOG);
			}
		}

		public void useDefault() {

			openLogLevel = isCollectLog ? Integer.parseInt(logLevel) : NO_LOG;
			maxLogSize = Integer.parseInt(logSize);
			onlyWIFIFlag = onlyWIFI;

			if (cbForConfigInfo != null) {

				cbForConfigInfo.logEnableStatus(openLogLevel != NO_LOG);
			}
		}

	}

}
