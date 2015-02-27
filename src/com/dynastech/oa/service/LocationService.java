package com.dynastech.oa.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 百度的定位服务
 */
public class LocationService extends Service {
	private String address;
	private double latitude;
	private double lontitude;
	public LocationClient mLocationClient;
	public MyLocationListenner myListener;
	private float radius;
	private String tag = "LocationService";

	private void setLocationOption() {
		LocationClientOption localLocationClientOption = new LocationClientOption();
		localLocationClientOption
				.setServiceName("com.baidu.location.service_v2.9");
		localLocationClientOption.setPoiExtraInfo(true);
		localLocationClientOption.setAddrType("all");
		localLocationClientOption.setCoorType("bd09ll");
		localLocationClientOption.setScanSpan(3000);
		localLocationClientOption.setPriority(2);
		localLocationClientOption.setPoiNumber(20);
		localLocationClientOption.disableCache(true);
		this.mLocationClient.setLocOption(localLocationClientOption);
	}

	public IBinder onBind(Intent paramIntent) {
		return null;
	}

	public void onCreate() {
		super.onCreate();
		this.myListener = new MyLocationListenner();
		this.mLocationClient = new LocationClient(this);
		this.mLocationClient.registerLocationListener(this.myListener);
		setLocationOption();
		this.mLocationClient.start();
	}

	public void onDestroy() {
		super.onDestroy();
		this.mLocationClient.stop();
	}

	public class MyLocationListenner implements BDLocationListener {
		public MyLocationListenner() {
		}

		public void onReceiveLocation(BDLocation paramBDLocation) {

			if (paramBDLocation != null) {
				LocationService.this.address = paramBDLocation.getAddrStr();
				LocationService.this.latitude = paramBDLocation.getLatitude();
				LocationService.this.lontitude = paramBDLocation.getLongitude();
				LocationService.this.radius = paramBDLocation.getRadius();
				LocationService.this.sendBroadcast(new Intent("location")
						.putExtra("address", LocationService.this.address)
						.putExtra("latitude", LocationService.this.latitude)
						.putExtra("lontitude", LocationService.this.lontitude)
						.putExtra("radius", LocationService.this.radius));
			}

		}

		public void onReceivePoi(BDLocation paramBDLocation) {
		}
	}
}
