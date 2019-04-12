package com.neusoft.track;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.neusoft.track.base.LogType;
import com.neusoft.track.cmread.CMTrack;
import com.neusoft.track.utils.AndroidUtils;

public class MainActivity extends Activity {

	
	public static final String BOOKREADER = "3000001";
	public static final String COMMONWEBPAGE = "3000002";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CMTrack.getInstance().init(true);
		
		Button test_msg_btn = (Button) findViewById(R.id.test_msg_btn);
		test_msg_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CMTrack.getInstance().infoLog(LogType.MSG, BOOKREADER, "测试中文BookReader enter!", null, null, null, null);
			}
		});
		Button test_interface_btn= (Button) findViewById(R.id.test_interface_btn);
		test_interface_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CMTrack.getInstance().infoLog(LogType.MSG, BOOKREADER, "测试中文BookReader getChapterInfo2 start!", null, "getChapterInfo2", "contentId = 0000123, chapterId = 0000124", "0");
			}
		});
		Button test_web_btn = (Button) findViewById(R.id.test_web_btn);
		test_web_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CMTrack.getInstance().infoLog(LogType.MSG, COMMONWEBPAGE, "onrefresh()", "http:\\www.baidu.com", null, null, null);
			}
		});
		Button test_caught_btn = (Button) findViewById(R.id.test_caught_btn);
		test_caught_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String a = null;
				try
				{
					a.equals("a");
				}
				catch(Exception e)
				{
					CMTrack.getInstance().infoLog(LogType.CAUGHT, COMMONWEBPAGE, CMTrack.getExceptionStack(e), null, null, null, null);
					e.getStackTrace();
				}
			}
		});
		
		Button test_uncaught_btn = (Button) findViewById(R.id.test_uncaught_btn);
//		test_uncaught_btn.setEnabled(false);
		test_uncaught_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String a = null;
				a.equals("a");
			}
		});
		
		Button test_postfile_btn = (Button) findViewById(R.id.test_postfile_btn);
		test_postfile_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CMTrack.getInstance().offlineSend();
			}
		});
	
	
	Button test_getPhoneInfo_btn = (Button)findViewById(R.id.test_getPhoneInfo_btn);
	test_getPhoneInfo_btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			((Button)v).setText("" + AndroidUtils.getExternalStorageLeftPercent(MainActivity.this));
		}
	});
	
	}

}
