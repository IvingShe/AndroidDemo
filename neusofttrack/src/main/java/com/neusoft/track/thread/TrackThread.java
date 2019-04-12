package com.neusoft.track.thread;

import android.os.Handler;

public class TrackThread extends MyHandlerThread{
	
	private Handler mHandler;
	
	public TrackThread(String name) {
		super(name);
	}

	public TrackThread(String name, int priority) {
		super(name);
	}
	
	public void post(Runnable runnable)
	{
		if(!isAlive())
		{
			return;
		}
		getHandler().post(runnable);
	}
	
	public Handler getHandler()
	{
		if(mHandler == null)
		{
			mHandler = new Handler(getLooper());
		}
		return mHandler;
	}
	
	@Override
	public boolean quit() {
		mHandler = null;
		return super.quit();
		
	}
}
