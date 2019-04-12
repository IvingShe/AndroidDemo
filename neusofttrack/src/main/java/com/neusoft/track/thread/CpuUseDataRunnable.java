package com.neusoft.track.thread;

import android.content.Context;

import com.neusoft.track.utils.AndroidUtils;


public class CpuUseDataRunnable implements Runnable {
	private Context context;
	private static String currentCpuFreq = "N/A";
	private boolean isRunning = false;

	private static CpuUseDataRunnable instance = null;

	public CpuUseDataRunnable(Context context) {
		this.context = context;
	}

	public static CpuUseDataRunnable getInstance(Context ctx) {
		if (instance == null) {
			instance = new CpuUseDataRunnable(ctx);
		}
		return instance;
	}

	public static String getCurrentCpuFreq() {
		return currentCpuFreq;
	}

	public void run() {
		if ((this.isRunning) || (this.context == null)) {
			return;
		}
		this.isRunning = true;

		int secondsPerTime = 400;
		while (true) {
			currentCpuFreq = AndroidUtils.getCurrentCpuFreq(this.context);
			try {
				Thread.sleep(secondsPerTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
