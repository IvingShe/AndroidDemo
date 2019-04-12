package com.neusoft.track.cmread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;

import com.neusoft.track.base.LogType;
import com.neusoft.track.base.TrackBase;
import com.neusoft.track.utils.AndroidUtils;

public class CMTrack extends TrackBase implements CMTrackApi {

	@SuppressLint("SimpleDateFormat")
	DateFormat mFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
	public String userId = null;
	public String imei = null;
	public String msisdn = null;
	public String userAgent = null;
	public String channelId = null;
	public String loginType = null;

	public static CMTrack getInstance() {
		return (CMTrack) mInstance;
	}

	public CMTrack(Context context) {
		super(context);
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public void clearUserId() {
		this.userId = null;
	}

	@Override
	public void setIMEI(String iemi) {
		this.imei = iemi;
	}


	public void setMSISDN(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getMSISDN() {
		return msisdn;
	}
	
	@Override
	public void setAPPVersion(String appVer) {
		this.appVersion = appVer;
	}

	@Override
	public void setUserAgent(String ua) {
		this.userAgent = ua;
	}

	@Override
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Override
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Override
	public void errorLog(String logType, String messageID, String message,
			String webUrl, String interfaceName, String requireParam,
			String returnCode) {
		commonLog(LogLevel.ERROR, logType, messageID, message, webUrl, interfaceName, requireParam, returnCode);
	}

	@Override
	public void warnLog(String logType, String messageID, String message,
			String webUrl, String interfaceName, String requireParam,
			String returnCode) {
		commonLog(LogLevel.WARNING, logType, messageID, message, webUrl, interfaceName, requireParam, returnCode);
	}

	@Override
	public void debugLog(String logType, String messageID, String message,
			String webUrl, String interfaceName, String requireParam,
			String returnCode) {
		commonLog(LogLevel.DEBUG, logType, messageID, message, webUrl, interfaceName, requireParam, returnCode);
	}

	@Override
	public void infoLog(String logType, String messageID, String message,
			String webUrl, String interfaceName, String requireParam,
			String returnCode) {
		commonLog(LogLevel.INFO, logType, messageID, message, webUrl, interfaceName, requireParam, returnCode);
	}

	@Override
	public void eWebMsgLog(String messageID, String message, String webUrl) {
		webLog(LogLevel.DEBUG, messageID, message, webUrl);
	}

	@Override
	public void wWebMsgLog(String messageID, String message, String webUrl) {
		webLog(LogLevel.WARNING, messageID, message, webUrl);
	}

	@Override
	public void dWebMsgLog(String messageID, String message, String webUrl) {
		webLog(LogLevel.DEBUG, messageID, message, webUrl);
	}

	@Override
	public void iWebMsgLog(String messageID, String message, String webUrl) {
		webLog(LogLevel.INFO, messageID, message, webUrl);
	}

	@Override
	public void eItfMsgLog(String messageID, String message,
			String interfaceName, String requireParam, String returnCode) {
		interfaceLog(LogLevel.ERROR, messageID, message, interfaceName,
				requireParam, returnCode);
	}

	@Override
	public void wItfMsgbLog(String messageID, String message,
			String interfaceName, String requireParam, String returnCode) {
		interfaceLog(LogLevel.WARNING, messageID, message, interfaceName,
				requireParam, returnCode);
	}

	@Override
	public void dItfMsgLog(String messageID, String message,
			String interfaceName, String requireParam, String returnCode) {
		interfaceLog(LogLevel.DEBUG, messageID, message, interfaceName,
				requireParam, returnCode);
	}

	@Override
	public void iItfMsgLog(String messageID, String message,
			String interfaceName, String requireParam, String returnCode) {
		interfaceLog(LogLevel.INFO, messageID, message, interfaceName,
				requireParam, returnCode);
	}

	@Override
	public void eCaughtLog(String messageID, Throwable e) {
		caughtLog(LogLevel.ERROR, messageID, e);
	}

	@Override
	public void wCaughtLog(String messageID, Throwable e) {
		caughtLog(LogLevel.WARNING, messageID, e);
	}

	@Override
	public void dCaughtLog(String messageID, Throwable e) {
		caughtLog(LogLevel.DEBUG, messageID, e);
	}

	@Override
	public void iCaughtLog(String messageID, Throwable e) {
		caughtLog(LogLevel.INFO, messageID, e);
	}

	private void caughtLog(String logLevel, String messageID, Throwable e) {
		commonLog(logLevel, LogType.CAUGHT, messageID, TrackBase.getExceptionStack(e), null, null, null, null);
	}

	private void interfaceLog(String logLevel, String messageID,
			String message, String interfaceName, String requireParam,
			String returnCode) {
		commonLog(logLevel, LogType.MSG, messageID, message, null, interfaceName, requireParam, returnCode);
	}

	private void webLog(String logLevel, String messageID, String message,
			String webUrl) {
		commonLog(logLevel,  LogType.MSG, messageID, message, webUrl, null, null, null);
	}
	

	private void commonLog(String logLevel, String logType, String messageID,
			String message, String webUrl, String interfaceName,
			String requireParam, String returnCode) {
		if (!isLogEnable(logLevel)) {
			return;
		}
		writeLog(createLogInfo(logLevel, logType, messageID, message, webUrl,
				interfaceName, requireParam, returnCode));
	}

	@Override
	protected Map<String, String> createUncaugthLogInfo(String stackInfo) {
		return createLogInfo(LogLevel.ERROR, LogType.UNCAUGHT, ""+stackInfo.hashCode(),
				stackInfo, null, null, null, null);
	}

	/**
	 * 璁剧疆鏃ュ織瀛楁淇℃伅
	 * @param logLevel
	 * @param logType
	 * @param messageId
	 * @param message
	 * @param webUrl
	 * @param interfaceName
	 * @param requireParam
	 * @param returnCode
	 * @return
	 */
	private Map<String, String> createLogInfo(String logLevel, String logType,
			String messageId, String message, String webUrl,
			String interfaceName, String requireParam, String returnCode) {
		Map<String, String> values = new HashMap<String, String>();
		String time = mFormatter.format(new Date());
		values.put(Parameter.logId, (imei == null ? "" : imei) + time);
		values.put(Parameter.logLevel, logLevel + "");
		values.put(Parameter.logType, logType + "");
		values.put(Parameter.logTime, time);
		values.put(Parameter.os, os);
		values.put(Parameter.osVersion, osVersion);
		values.put(Parameter.appVersion, getAppVersion());
		values.put(Parameter.messageId, messageId);
		values.put(Parameter.userId, msisdn);
		values.put(Parameter.signalStrength, signalStrength);
		values.put(Parameter.webUrl, webUrl);
		values.put(Parameter.message, message);
		values.put(Parameter.brand, brand);
		values.put(Parameter.model, model);
		values.put(Parameter.screenWidth, screenWidth);
		values.put(Parameter.screenHeight, screenHeight);
		values.put(Parameter.userAgent, userAgent);
		values.put(Parameter.resolution, resolution);
		values.put(Parameter.channelId, channelId);
		values.put(Parameter.imei, (imei == null ? AndroidUtils.getIMEI(mContext) : imei) );
		values.put(Parameter.interfaceName, interfaceName);
		values.put(Parameter.requireParam, requireParam);
		values.put(Parameter.returnCode, returnCode);
		values.put(Parameter.msisdn, msisdn);
		values.put(Parameter.loginType, loginType);
		values.put(Parameter.terminalId, terminalId);
		return values;
	}

	/**
	 * 濡傛灉鍒涘缓log淇℃伅锛屾湁鑰楁椂鐨勫鐞嗭紝濡傝幏鍙朿pu鎯呭喌锛屽彲浠ュ湪璇ユ柟娉曚腑澶勭悊
	 */
	@Override
	protected void onPrepareLog(Map<String, String> values) {

		values.put(Parameter.phoneNumber, getPhoneNumber());
		values.put(Parameter.networkInfo, getNetType());
		values.put(Parameter.simCardType, getSIMCardType()+ "");
		values.put(Parameter.location, getLocation());
		values.put(Parameter.cpuUtility,getCurrentCpuFreq());
		values.put(Parameter.memoryLeft, getMemoryLeftPercent());
		values.put(Parameter.StorageLeft, getStorageLeftPercent());
		values.put(Parameter.ExternaStorageLeft, getExternalStorageLeftPercent());
		values.put(Parameter.processName, getPocessName());
		values.put(Parameter.threadName, getThreadName());
		values.put(Parameter.powerLevel, getBatteryLevel() + "");
	}

	// <--鍒涘缓瀛楁澶勭悊--end

	/**
	 * 璁剧疆闇�瑕佺殑瀛楁
	 */
	@Override
	protected void initParameters() {
		addParameter(Parameter.logId);
		addParameter(Parameter.logLevel);
		addParameter(Parameter.logType);
		addParameter(Parameter.logTime);
		addParameter(Parameter.os);
		addParameter(Parameter.osVersion);
		addParameter(Parameter.appVersion);
		addParameter(Parameter.messageId);
		addParameter(Parameter.userId);
		addParameter(Parameter.phoneNumber);
		addParameter(Parameter.networkInfo);
		addParameter(Parameter.signalStrength);
		addParameter(Parameter.simCardType);
		addParameter(Parameter.webUrl);
		addParameter(Parameter.message);
		addParameter(Parameter.location);
		addParameter(Parameter.terminalId);
		addParameter(Parameter.brand);
		addParameter(Parameter.model);
		addParameter(Parameter.screenWidth);
		addParameter(Parameter.screenHeight);
		addParameter(Parameter.userAgent);
		addParameter(Parameter.resolution);
		addParameter(Parameter.channelId);
		addParameter(Parameter.imei);
		addParameter(Parameter.interfaceName);
		addParameter(Parameter.requireParam);
		addParameter(Parameter.returnCode);
		addParameter(Parameter.cpuUtility);
		addParameter(Parameter.memoryLeft);
		addParameter(Parameter.StorageLeft);
		addParameter(Parameter.ExternaStorageLeft);
		addParameter(Parameter.processName);
		addParameter(Parameter.threadName);
		addParameter(Parameter.powerLevel);
		addParameter(Parameter.msisdn);
		addParameter(Parameter.loginType);
	}



}
