package com.neusoft.track.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

import com.neusoft.track.utils.AndroidUtils;

public class UUIDUtil {

	public static String getUUID(Context context) {
		if (!(android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED))) {
			return null;
		}
		String uuid = null;
		String dir = AndroidUtils.getExternalStorageRootPath() + "/"
				+ context.getPackageName().hashCode() + "/";
		String file = dir + "uuid";
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File uuidFile = new File(file);
		if (uuidFile.exists() && uuidFile.length() > 0) {
			int lenght = (int) uuidFile.length();
			byte[] data = new byte[lenght];
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(uuidFile);
				fis.read(data);
				uuid = new String(data);
			} catch (Exception e) {
				uuid = null;
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					fis = null;
				}
			}
		}
		if (uuid == null || uuid.length() == 0) {
			uuid = makeIdentity();
			if (uuid != null) {
				FileOutputStream fos = null;
				if (uuidFile.exists()) {
					uuidFile.delete();
				}
				try {
					uuidFile.createNewFile();
					fos = new FileOutputStream(uuidFile);
					fos.write(uuid.getBytes());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.flush();
							fos.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			}
		}
		return uuid;
	}

	public static String makeIdentity() {
		String identity = java.util.UUID.randomUUID().toString();
		return identity;
	}
}
