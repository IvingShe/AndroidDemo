package com.neusoft.track.thread;

import android.os.Looper;

public class MyHandlerThread extends Thread {

	private Looper mLooper;

	public MyHandlerThread(String name) {
		super(name);
	}

	public MyHandlerThread(String name, int priority) {
		super(name);
	}
	


	@Override
	public void run() {
		Looper.prepare();
		synchronized (this)
		{
			//...
			mLooper = Looper.myLooper();
			try
			{
				notify();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		Looper.loop();
	}

	public Looper getLooper() {
		if (!isAlive()) {
			return null;
		}

		// If the thread has been started, wait until the looper has been
		// created.
		synchronized (this) {
			while (isAlive() && mLooper == null) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}
		return mLooper;
	}

	public boolean quit() {
		Looper looper = getLooper();
		if (looper != null) {
			looper.quit();
			return true;
		}
		return false;
	}

}
