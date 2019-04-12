package com.neusoft.track.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.neusoft.track.utils.NLog;

public class BatteryDataRunnable implements Runnable {
	
	private static Map<String, String> bunders = new ConcurrentHashMap();
	public static final String KEY_BATTERY = "battery";
	private boolean isRunning = false;

	private static BatteryDataRunnable instance = null;

	private static BroadcastReceiver mBatteryInfoReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			try {
				String action = intent.getAction();
				if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
					int level = intent.getIntExtra("level", 0);
					BatteryDataRunnable.bunders.put("battery",
							String.valueOf(level));
				}
			} catch (Throwable e) {
				NLog.i("", e.toString());
			}
		}
	};

	private Context ctx = null;

	public static BatteryDataRunnable getInstance(Context ctx) {
		if (instance == null) {
			instance = new BatteryDataRunnable(ctx);
		}
		return instance;
	}

	public static String getDataByKey(String key) {
		String get_key = null;
		try {
			if (bunders.containsKey(key))
				get_key = (String) bunders.get(key);
		} catch (Throwable e) {
			NLog.i("", e.toString());
			return "";
		}
		return get_key;
	}

	private BatteryDataRunnable(Context context) {
		this.ctx = context;
	}

	public synchronized boolean registerBatteryReceiver() {
		if (this.ctx == null) {
			NLog.i("", "registerBatteryReceiver nothing by null");
			return false;
		}
		try {
			this.ctx.registerReceiver(mBatteryInfoReceiver, new IntentFilter(
					"android.intent.action.BATTERY_CHANGED"));
			NLog.i("", "registerBatteryReceiver success");
		} catch (Throwable e) {
			NLog.i("", e.toString());
			NLog.i("", "registerBatteryReceiver fail");
			return false;
		}
		return true;
	}

	public synchronized boolean unRegisterBatteryReceiver() {
		if (this.ctx == null) {
			NLog.i("", "unRegisterBatteryReceiver nothing");
			return false;
		}
		try {
			this.ctx.unregisterReceiver(mBatteryInfoReceiver);
			NLog.i("", "unRegisterBatteryReceiver success");
		} catch (Throwable e) {
			NLog.i("", e.toString());
			NLog.i("", "unRegisterBatteryReceiver fail");
			return false;
		}
		return true;
	}

	public void run() {
		if (this.isRunning) {
			return;
		}
		this.isRunning = true;

		int secondsPerTime = 30000;

		int interval = 60000;

		int times = 1;
		while (true)
			try {
				NLog.i("", times + " times to register");

				registerBatteryReceiver();

				Thread.sleep(secondsPerTime);
			} catch (Throwable e) {
				NLog.i("", e.toString());
				try {
					NLog.i("", times + " times to unregister");

					unRegisterBatteryReceiver();

					Thread.sleep(interval);
				} catch (Throwable e2) {
					NLog.i("", e2.toString());

					times++;
				} finally {
					times++;
				}
			} finally {
				try {
					NLog.i("", times + " times to unregister");

					unRegisterBatteryReceiver();

					Thread.sleep(interval);
				} catch (Throwable e) {
					NLog.i("", e.toString());
				} finally {
					times++;
				}
			}
	}

}
