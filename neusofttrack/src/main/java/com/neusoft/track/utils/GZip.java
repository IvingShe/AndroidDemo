package com.neusoft.track.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZip {
	public static ByteArrayOutputStream gzipCompressWriter(InputStream is,
			String charset) throws IOException {
		BufferedReader in = null;

		ByteArrayOutputStream baos = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedReader(new InputStreamReader(is, charset));
			baos = new ByteArrayOutputStream();
			out = new BufferedOutputStream(new GZIPOutputStream(baos));
			int c;
			while ((c = in.read()) != -1) {
				out.write(String.valueOf((char) c).getBytes(charset));
			}
			in.close();
			out.close();
		} catch (Throwable t) {
			NLog.i("GZip", t.toString() + "GZip异常");
		}
		return baos;
	}

	public static ByteArrayOutputStream gzipCompressReader(InputStream is,
			String charset) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPInputStream gis = new GZIPInputStream(is);

		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = gis.read(buffer)) > 0) {
			baos.write(buffer, 0, len);
		}

		baos.close();
		gis.close();
		return baos;
	}

	public static File doCompressFile(File orgFile) {

		if(orgFile == null)
		{
			return null;
		}
		NLog.i("", "zxc GZip.doCompressFil() orgFile = " + orgFile.getPath() + " size = " + orgFile.length());
		File tempFile = null;
		GZIPOutputStream out = null;
		FileInputStream in = null;
		try {
			String outFilePath = orgFile.getAbsolutePath() + ".gz";
			try {
				tempFile = new File(outFilePath);
				if (tempFile.exists()) {
					tempFile.delete();
				}
				tempFile.createNewFile();
				out = new GZIPOutputStream(new FileOutputStream(tempFile));
			} catch (Exception e) {
				tempFile = null;
			}
			if (tempFile != null) {
				try {
					in = new FileInputStream(orgFile);
				} catch (FileNotFoundException e) {
					tempFile = null;
				}
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// in.close();
				// out.finish();
				// out.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			tempFile = null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.finish();
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (tempFile == null || !tempFile.exists()) {
			return null;
		}
		NLog.i("", "zxc GZip.doCompressFil() tempFile = " + tempFile.getPath() + " size = " + tempFile.length());
		return tempFile;

	}

}
