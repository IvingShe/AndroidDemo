package com.neusoft.track.base;

import java.util.Map;

public interface TrackApi{
	
	/**
	 * writeLog: 通用log接口
	 */
	public void writeLog(Map<String, String> values);
	
	/**
	 * 上传日志接口
	 */
	public void offlineSend();
	
	/**
	 * 设置日志开关
	 * @param enable
	 */
	public void setLogEnable(boolean enable);
	
	/**
	 * 设置获取GPS权限
	 * @param allowed
	 */
	public void switchAllowSdkToGetGps(boolean allowed);
	
	/**
	 * 设置获取地理信息权限
	 * @param allowed
	 */
	public void switchAllowSdkToGetStatitionInfo(boolean allowed);
	
	/**
	 * 销毁日志进程
	 */
	public void onAppDestroy();
	
	/**
	 * 设置地理信息
	 */
	public void setLocation(String location);
	
	/**
	 * 设置手机号码
	 */
	public void setPhoneNumber(String phoneNumber);
	

}
