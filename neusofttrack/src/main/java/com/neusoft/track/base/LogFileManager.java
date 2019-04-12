package com.neusoft.track.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;

import com.neusoft.track.http.PostFile;
import com.neusoft.track.utils.AndroidUtils;
import com.neusoft.track.utils.GZip;
import com.neusoft.track.utils.NLog;

public class LogFileManager {
	
	private int MAX_DIVIDER_SIZE = (int) (1 * 1024 * 1024);
	private int MAX_CACHE_MEMORY = (int) (5 * 1024 * 1024);
	
	private Context mContext;
	private String mLogDirPath;
//	private String mConfigPath;
	private File mCacheFile = null;
	private static LogFileManager mInstance;
	private File mSendingFile = null;
	private static final String mCharset = "UTF-8";
	
	protected LogFileManager(Context context)
	{
		mInstance = this;
		mContext = context;
//		mConfigPath = AndroidUtils.getExternalStorageRootPath() + "/" + mContext.getPackageName().hashCode() + "/config.xml";
		mLogDirPath = AndroidUtils.getExternalStorageRootPath() + "/" + mContext.getPackageName().hashCode() + "/log/";
	}
	
	public static LogFileManager getInstance()
	{
		return mInstance;
	}
	

	public void writeToTrackLog(String log)
	{
		if (!(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)))
		{
			return;
		}
		File dir = new File(mLogDirPath);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		if(mCacheFile == null)
		{
			mCacheFile = createCacheFile();
		}
		
		if(mCacheFile.length() >= MAX_DIVIDER_SIZE)
		{
			//瑙﹀彂涓婁紶
			//offlineSend();
			managerCacheFiles(dir);
			mCacheFile = createCacheFile();
		}
		
		if (!mCacheFile.exists())
		{
			try
			{
				mCacheFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		NLog.i("zxc_0210011","LogRunnable login success,write file start , CurrentTime : " + System.currentTimeMillis() + " mCachefile " + mCacheFile.getName());
		writeBom(mCacheFile);
		BufferedWriter output;
		try
		{
			output = new BufferedWriter(new FileWriter(mCacheFile, true), 512);
			output.write(log);
			output.close();
			mCacheFile.setLastModified(System.currentTimeMillis());
			NLog.i("zxc_0210011","LogRunnable login success,write file end , CurrentTime : " + System.currentTimeMillis() + " mCachefile " + mCacheFile.getName());
		}
		catch (IOException e)
		{

			e.printStackTrace();
		}
		if(mCacheFile.length() >= MAX_DIVIDER_SIZE)
		{
			//瑙﹀彂涓婁紶
			//offlineSend();
			managerCacheFiles(dir);
			mCacheFile = createCacheFile();
		}

	}
	
	private File createCacheFile()
	{
		File cacheFile = getLastedCache();
		if(cacheFile != null && cacheFile.length() >= MAX_DIVIDER_SIZE)
		{
			cacheFile = null;
		}
		if(cacheFile == null)
		{
			cacheFile = new File(mLogDirPath + System.currentTimeMillis() + ".log");
			int i = 0;
			while(cacheFile.exists())
			{
				cacheFile = new File(mLogDirPath + System.currentTimeMillis() + (i++) + ".log");
			}
			NLog.i("", "zxc createCacheFile()  cacheFile = " + cacheFile.getPath());
		}
		return cacheFile;
	}
	
	private File getLastedCache()
	{
		File logDir = new File(mLogDirPath);
		if (!logDir.exists() || !logDir.isDirectory())
		{
			return null;
		}
		File[] files = logDir.listFiles();
		if(files != null && files.length > 0)
		{
			File temp = null;
			for (int i = 0; i < files.length; i++)
			{
				if (files[i].getName().endsWith(".log") && (temp == null || temp.lastModified() < files[i].lastModified()))
				{
					temp = files[i];
				}
			}
			if(temp != null && mSendingFile!= null && mSendingFile.equals(temp))
			{
				temp = null;
			}
			return temp;
		}
		return null;
	}
	
	private File getOldestCache()
	{
		File logDir = new File(mLogDirPath);
		if (!logDir.exists() || !logDir.isDirectory())
		{
			return null;
		}
		File[] files = logDir.listFiles();
		if(files != null && files.length > 0)
		{
			File temp = null;
			for (int i = 0; i < files.length; i++)
			{
				if (files[i].getName().endsWith(".log") && (temp == null || temp.lastModified() > files[i].lastModified()))
				{
					temp = files[i];
				}
			}
			return temp;
		}
		return null;
	}
	
	private synchronized void managerCacheFiles(File file)
	{
		if (!file.exists() || !file.isDirectory())
		{
			return;
		}
		File[] files = file.listFiles();
		long fileLength = caculateFileLenght(file);
		if (fileLength >= MAX_CACHE_MEMORY)
		{
			File temp = null;
			for (int i = 0; i < files.length; i++)
			{
				for (int j = i + 1; j <= files.length - 1; j++)
				{
					if (files[i].lastModified() > files[j].lastModified())
					{
						temp = files[i];
						files[i] = files[j];
						files[j] = temp;
					}
				}
			}
			for (int i = 0; i < files.length - 1; i++)//鑷冲皯淇濈暀涓�涓枃浠�
			{
				long size = caculateFileLenght(files[i]);
				files[i].delete();
				fileLength -= size;
				NLog.i(TrackBase.TAG, "zxc deleteFile files[i] = " + files[i].getPath());
				if(fileLength < MAX_CACHE_MEMORY)
				{
					break;
				}
			}
		}
	}
	
	private long caculateFileLenght(File file)
	{
		long fileLength = 0;
		if(!file.exists())
		{
			return 0;
		}
		if(file.isDirectory())
		{
			File[] files = file.listFiles();
			for(File f : files)
			{
				fileLength += caculateFileLenght(f);
			}
		}
		else
		{
			fileLength += file.length();
		}
		return fileLength;
	}

	/**
	 * 璋冪敤璇ユ柟娉曟椂锛屽簲娉ㄦ剰涓嶈兘鍑虹幇鍚屼竴鏃堕棿鏈変笂浼犲悓涓�鏂囦欢鐨勬儏鍐�
	 */
	public void sendCache()
	{
		boolean result = false;
		File sendingFile = getOldestCache();
		mSendingFile = sendingFile;
		mCacheFile = null;
		if(sendingFile != null && sendingFile.exists())
		{
			try
			{
				File zipFile = GZip.doCompressFile(sendingFile);
				if(zipFile != null)
				{
					result = PostFile.doPostFile(zipFile.getAbsolutePath());
				}
				zipFile.delete();
			}
			catch(Exception e){
				NLog.e(TrackBase.TAG, "post log file error: "+e.getMessage());
			}
		}
		if(result)
		{
			try
			{
				NLog.i(TrackBase.TAG, "sendCache() success");
				if(sendingFile != null && sendingFile.exists())
				{
					sendingFile.delete();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			NLog.i(TrackBase.TAG, "zxc sendCache() failed");
		}
		mSendingFile = null;
	}
	
//	public String getConfigFilePath(){
//		
//		return mConfigPath;
//	}
	
	public void setMaxCacheMemory(int max_cache_memory)
	{
		MAX_CACHE_MEMORY = max_cache_memory;
	}
	
	private static void writeBom(File file)
	{
		if(file == null || file.isDirectory() || file.length() > 0)
		{
			return;
		}
		try {
			if(!file.exists())
			{
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(-17);
			fos.write(-69);
			fos.write(-65);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
