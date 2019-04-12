package com.neusoft.track.thread;

import com.neusoft.track.base.LogFileManager;
import com.neusoft.track.base.TrackBase;
import com.neusoft.track.utils.NLog;

public class SendLogRunnable  implements Runnable{

	
	public SendLogRunnable() {
		
	}
	
	@Override
	public void run() {
		
		NLog.i(TrackBase.TAG, "zxc SendLogR unnable.run() start");
		if(LogFileManager.getInstance() != null)
		{
			LogFileManager.getInstance().sendCache();
		}
		NLog.i(TrackBase.TAG, "zxc SendLogRunnable.run() end");
	}

}

