package com.dynastech.oa.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * This class is unused. vito kong
 *<li>{@link #NetWorkHelper}</li>
 */
public class NetUtils {

	public static boolean isNetworkAvailable(Context ctx) { // �ж������Ƿ�ͨ��
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return (info != null && info.isConnected());
	}

	public static String currentNetwork(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			if (info.isAvailable() && info.isConnectedOrConnecting()) {
				String netType = info.getTypeName();
				if ("WIFI".equals(netType)) {
					return "WIFI";
				} else {
					return "2G/3G";
				}
			}
		}
		return null;
	}

	/**
	 * Checks if is wifi.
	 * 
	 * @param ctx
	 *            the ctx
	 * @return true, if is wifi
	 */
	public static boolean isWifi(final Context ctx) {
		WifiManager wm = (WifiManager) ctx
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wi = wm.getConnectionInfo();
		if (wi != null
				&& (WifiInfo.getDetailedStateOf(wi.getSupplicantState()) == DetailedState.OBTAINING_IPADDR || WifiInfo
						.getDetailedStateOf(wi.getSupplicantState()) == DetailedState.CONNECTED)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is umts.
	 * 
	 * @param ctx
	 *            the ctx
	 * @return true, if is umts
	 */
	public static boolean isUmts(final Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getNetworkType() >= TelephonyManager.NETWORK_TYPE_UMTS;
	}

	/**
	 * Checks if is edge.
	 * 
	 * @param ctx
	 *            the ctx
	 * @return true, if is edge
	 */
	public static boolean isEdge(final Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EDGE;
	}

}
