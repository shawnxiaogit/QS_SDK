package com.qunsuan.qspayhelper;

import com.qspay.webservice.constant.Constant;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SMSSender {
	private static final String TAG = "com.qunsuan.qspayhelper.SMSSender";
	public static void SendSMS(String number, String message, int count, Context context){
		L.i(TAG, "Sending SMS ...");
		L.i(TAG, "  number:"+number+" message:"+message+" count:"+count);
		SmsManager sms = SmsManager.getDefault();   
		
		//发送短信的广播
		Intent itSend = new Intent(SMSReceiver.SMS_SEND_ACTION);
		PendingIntent mSendPI = PendingIntent.getBroadcast(  
				context,  
				(int) System.currentTimeMillis(), itSend,  
				PendingIntent.FLAG_UPDATE_CURRENT);  
		//接受短信的广播
		Intent itDeliver = new Intent(SMSReceiver.SMS_DELIVERED_ACTION);
		PendingIntent mDeliverPI = PendingIntent.getBroadcast(  
				context,  
				(int) System.currentTimeMillis(), itDeliver,  
				PendingIntent.FLAG_UPDATE_CURRENT);  
		
		
        
        Toast.makeText(context, "正在支付中，请稍候...", Toast.LENGTH_SHORT)
		.show();
        for (int i = 0; i < count; i++){
	        sms.sendTextMessage(number,null,message,mSendPI,mDeliverPI);
	        L.i(TAG, "SMS-"+i+" has been sent.");
//	        insertMsg(context,number,message);
//	        PayHelper.sendNotifyToServer("恭喜您成功支付成功，本条1元。客服021-20230309【上海群算信息技术有限公司】", "2013-08-14 20:22:08", context);
		} 
//        PayHelper.getOnProcessListener().onComplete();
	}
	
	
	private static void insertMsg(Context context,String number,String text){
		 /**将发送的短信插入数据库**/  
        ContentValues values = new ContentValues();  
        //发送时间  
        values.put("date", System.currentTimeMillis());  
        //阅读状态  
        values.put("read", 0);  
        //1为收 2为发  
        values.put("type", 2);  
        //送达号码  
        values.put("address", number);  
        //送达内容  
        values.put("body", text);  
        //插入短信库  
        context.getContentResolver().insert(Uri.parse("content://sms"),values); 
	}
}
