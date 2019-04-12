package com.neusoft.track.utils;

import android.content.Context;
import android.widget.Toast;

public class NLog {
	
	private static boolean UI_DEBUG = true;

	private NLog() {

	}

	public static int v(String tag, String msg) {
		if (UI_DEBUG && tag != null && msg != null) {
			return android.util.Log.v(tag, msg);

		} else {
			return 0;
		}
	}

	public static int v(String tag, String msg, Throwable tr) {
		if (UI_DEBUG && tag != null && msg != null) {

			return android.util.Log.v(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int d(String tag, String msg) {
		if (UI_DEBUG && tag != null && msg != null) {

			return android.util.Log.d(tag, msg);
		} else {
			return 0;
		}
	}

	public static int d(String tag, String msg, Throwable tr) {
		if (UI_DEBUG && tag != null && msg != null) {

			return android.util.Log.d(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int i(String tag, String msg) {
		if (UI_DEBUG && tag != null && msg != null) {
			return android.util.Log.i(tag, msg);
		} else {
			return 0;
		}
	}

	public static int i(String tag, String msg, Throwable tr) {
		if (UI_DEBUG && tag != null && msg != null) {

			return android.util.Log.i(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int w(String tag, String msg) {
		if (UI_DEBUG && tag != null && msg != null) {

			return android.util.Log.w(tag, msg);
		} else {
			return 0;
		}
	}

	public static int w(String tag, String msg, Throwable tr) {
		if (UI_DEBUG && tag != null && msg != null) {

			return android.util.Log.w(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int w(String tag, Throwable tr) {
		if (UI_DEBUG) {

			return android.util.Log.w(tag, tr);
		} else {
			return 0;
		}
	}

	public static int e(String tag, String msg) {
		if (UI_DEBUG && tag != null && msg != null) {

			return android.util.Log.e(tag, msg);
		} else {
			return 0;
		}
	}

	public static int e(String tag, String msg, Throwable tr) {
		if (UI_DEBUG && tag != null && msg != null) {

			return android.util.Log.e(tag, msg, tr);
		} else {
			return 0;
		}
	}
	
	public static void showToast(Context context, String text)
	{
		if(UI_DEBUG && context != null)
		{
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		}
	}
	
	public static void setDebugEnable(boolean enable)
	{
		UI_DEBUG = enable;
	}
}
