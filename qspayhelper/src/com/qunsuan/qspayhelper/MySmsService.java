package com.qunsuan.qspayhelper;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * 
 * @Descriptio 接收到开机广播后启动的服务
 * 				用来动态注册接受短信的广播
 * @author Shawn
 * @Time 2013-9-3  上午10:06:52
 */
public class MySmsService extends Service {
	
	private static final String TAG = "MySmsService";

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		L.e(TAG, "onCreate()");
		IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		intentFilter.setPriority(Integer.MAX_VALUE);
		SMSReceiver receiver = new SMSReceiver();
		registerReceiver(receiver, intentFilter);
	}

}
