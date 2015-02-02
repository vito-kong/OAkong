package com.dynastech.oa.ui.util;

/**
 * 判断文件类型是否被支持
 *
 */
public class ToolUtils {

	public static boolean isImageAllowRead(String suffix) {

		String[] suffixs = { ".png", ".jpg", ".gif", ".bmp", ".jpeg",};
		for (String s : suffixs) {
			if (s.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOfficeAllowRead(String suffix) {

		String[] suffixs = { ".doc", ".docx", ".xls", ".xlsx", ".pptx", ".ppt",
				".txt", ".pdf" };
		for (String s : suffixs) {
			if (s.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAudioAllowRead(String suffix) {

		String[] suffixs = { ".aac", ".amr", ".mp3", ".wma", ".mod", ".mid", ".ogg" };
		for (String s : suffixs) {
			if (s.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}
	public static boolean isVideoAllowRead(String suffix){
		String[] suffixs = { ".avi", ".mp4", ".mov", ".wmv", ".rm", ".rmvb", ".3gp",
				".mkv", ".flv" };
		for (String s : suffixs) {
			if (s.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return false;
	}
}
