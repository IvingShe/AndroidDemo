package com.neusoft.track.thread;

import java.util.List;
import java.util.Map;

import com.neusoft.track.base.LogFileManager;
import com.neusoft.track.cmread.Parameter;
import com.neusoft.track.utils.NLog;

public class LogRunnable implements Runnable{

	protected List<String> mParameters;
	private Map<String, String> mValues;
	
	public LogRunnable(List<String> parameters, Map<String, String> values)
	{
		mParameters = parameters;
		mValues = values;
	}
	
	@Override
	public void run() {
		NLog.i("", "zxc Runnable.run() start");
		if(mValues != null && mValues.get(Parameter.messageId).equals("0230008"))
		{
			NLog.i("zhoukun_0230008","LogRunnable login success,Runnable.run() start , CurrentTime : " + System.currentTimeMillis() + "messageId= " + Parameter.messageId);
		}
		if(mParameters == null || mValues == null)
		{
			return;
		}
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{");
		boolean needAddDot = false;
		String value = null;
		for(String parameter : mParameters)
		{
			if(needAddDot)
			{
				jsonBuilder.append(",");
			}
			else
			{
				needAddDot = true;
			}
			//key
			jsonBuilder.append("\"");
			jsonBuilder.append(parameter);
			jsonBuilder.append("\"");
			jsonBuilder.append(":");
			//value
			jsonBuilder.append("\"");
			value = mValues.get(parameter);
			if(value != null){
				value = value.replaceAll("[{}\"]", " ");
			}
			jsonBuilder.append(value == null ? "" : value);
			jsonBuilder.append("\"");
		}
		jsonBuilder.append("}");
		String log = jsonBuilder.toString();
		if(LogFileManager.getInstance() != null)
		{
			LogFileManager.getInstance().writeToTrackLog(log);
		}
		NLog.i("", "zxc Runnable.run() end "+ " values = "+mValues);
	}
	
}
