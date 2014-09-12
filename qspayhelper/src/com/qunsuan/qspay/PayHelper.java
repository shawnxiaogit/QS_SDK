package com.qunsuan.qspay;

import java.util.HashMap;

import com.qunsuan.qspayhelper.PayHelper.OnProcessListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class PayHelper {
	
	public static final int PAY_BY_MSG = 111;
	
	public static final int PAY_BY_BANK = 222;
	
	public static final String[] PAY_WAY_ARRAY =  new String[]{"短信","银行卡"};
	
	static HashMap<String,Integer> payWayMap = new HashMap<String,Integer>();
	static int selectPayWay = 0;
	
	//弹出对话框选择支付方式
	public static void showPayWayDialog(final Context context,final String businessCode, final String launchTime, final int orderCount, final float price){
		
		AlertDialog.Builder builder = new  AlertDialog.Builder(context);
		builder.setTitle("选择支付方式");
		builder.setSingleChoiceItems(PAY_WAY_ARRAY, 0, new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0,int arg1) {
//				if(arg1==0){
//					pay(PAY_BY_MSG,businessCode, launchTime, orderCount, price, context);
//				}else if(arg1==1){
//					pay(PAY_BY_MSG,businessCode, launchTime, orderCount, price, context);
//				}
				payWayMap.put("pay_way", arg1);
			}
			
		});
		
		builder.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				selectPayWay = payWayMap.get("pay_way");
				if(selectPayWay==0){
					pay(PAY_BY_MSG,businessCode, launchTime, orderCount, price, context);
				}else if(selectPayWay==1){
					pay(PAY_BY_MSG,businessCode, launchTime, orderCount, price, context);
				}
			}
		});
		
		
		builder.create().show();
	}
	
	public static void pay(int payWay,String businessCode, String launchTime, int orderCount, float price, Context context){
		if(payWay==PAY_BY_MSG){
			com.qunsuan.qspayhelper.PayHelper.pay(businessCode, launchTime, orderCount, price, context);
		}else if(payWay==PAY_BY_BANK){
			//调用银行卡支付
		}
	}

	public static void setOnProcessListener(OnProcessListener onProcessListener) {
		com.qunsuan.qspayhelper.PayHelper.setOnProcessListener(onProcessListener);
	}
}
