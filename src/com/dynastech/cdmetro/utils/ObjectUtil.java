package com.dynastech.cdmetro.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;

public class ObjectUtil {

	public boolean saveObject(Serializable ser, String file, Context ctx) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = ctx.openFileOutput(file, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 读取对象
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String file, Context ctx) {
		// if (!isExistDataCache(file, ctx))
		// return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = ctx.openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable) ois.readObject();
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			e.printStackTrace();
			// 反序列化失败 - 删除缓存文件
			// if (e instanceof InvalidClassException) {
			// File data = ctx.getFileStreamPath(file);
			// data.delete();
			// }
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
			}
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	private boolean isExistDataCache(String cachefile, Context ctx) {
		boolean exist = false;
		File data = ctx.getFileStreamPath(cachefile);
		if (data.exists())
			exist = true;
		return exist;
	}

}
