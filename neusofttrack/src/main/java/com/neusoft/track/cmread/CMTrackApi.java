package com.neusoft.track.cmread;



public interface CMTrackApi {

	/**
	 * errorLog：写入日志级别为“1”的日志
	 * @param logType：日志类型：1、未捕获。2、捕获。3、消息
	 * @param messageID:消息Id，通常由业务模块号+ 模块内消息代号组成; 由app定义！
	 * @param message：当日志类型为异常时，该字段保存的是异常栈；当日志类型为消息时，该字段保存消息内容
	 * @param webUrl:访问URL时产生的日志会使用该字段保存所访问的Url
	 * @param interfaceName:调用接口时，使用该字段存储接口名
	 * @param requireParam:调用接口时，请求的参数
	 * @param returnCode:调用接口时，使用该字段记录返回码
	 */
	public void errorLog(String logType, String messageID, String message, String webUrl, String interfaceName, String requireParam, String returnCode);
	public void warnLog(String logType, String messageID, String message, String webUrl, String interfaceName, String requireParam, String returnCode);
	public void debugLog(String logType, String messageID, String message, String webUrl, String interfaceName, String requireParam, String returnCode);
	public void infoLog(String logType, String messageID, String message, String webUrl, String interfaceName, String requireParam, String returnCode);
	
	/**
	 * 网页加载相关日志；
	 * @param messageID
	 * @param message
	 * @param webUrl
	 */
	public void eWebMsgLog(String messageID, String message, String webUrl);
	public void wWebMsgLog(String messageID, String message, String webUrl);
	public void dWebMsgLog(String messageID, String message, String webUrl);
	public void iWebMsgLog(String messageID, String message, String webUrl);
	
	public void eItfMsgLog(String messageID, String message, String interfaceName, String requireParam, String returnCode);
	public void wItfMsgbLog(String messageID, String message, String interfaceName, String requireParam, String returnCode);
	public void dItfMsgLog(String messageID, String message, String interfaceName, String requireParam, String returnCode);
	public void iItfMsgLog(String messageID, String message, String interfaceName, String requireParam, String returnCode);
	
	public void eCaughtLog(String messageID, Throwable e);
	public void wCaughtLog(String messageID, Throwable e);
	public void dCaughtLog(String messageID, Throwable e);
	public void iCaughtLog(String messageID, Throwable e);
	
	/**
	 * 设置版本号；app不需调用，由sdk自动获取
	 * @param appVer
	 */
	public void setAPPVersion(String appVer);
	/**
	 * 设置用户id
	 * @param userID
	 */
	public void setUserId(String userID);
	/**
	 * 清除用户id
	 */
	public void clearUserId();
	/**
	 * 设置ua参数
	 * @param ua
	 */
	public void setUserAgent(String ua);
	/**
	 * 设置终端版本发布渠道号
	 * @param channelId
	 */
	public void setChannelId(String channelId);
	/**
	 * 设置登录类型
	 * @param loginType
	 */
	public void setLoginType(String loginType);
	/**
	 * 设置 IMEI
	 * @param iemi
	 */
	public void setIMEI(String iemi);
	/**
	 * @param msisdn:鉴权返回的、用来标识用户的阅读号
	 */
	public void setMSISDN(String msisdn);
}
