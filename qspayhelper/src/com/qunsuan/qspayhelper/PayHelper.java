package com.qunsuan.qspayhelper;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.telephony.TelephonyManager;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qspay.webservice.bean.MarshalDouble;
import com.qspay.webservice.bean.RequestDealInfoBean_;
import com.qspay.webservice.bean.RequestFaieldChannelBean;
import com.qspay.webservice.bean.RequestNotifyBean_;
import com.qspay.webservice.bean.ResponseDealInfoBean.PlatformInfo;
import com.qspay.webservice.bean.ResponseDealInfoBean_;
import com.qspay.webservice.constant.Constant;

public class PayHelper {

	private static Context mContext;
	private static final String TAG = "com.qunsuan.qspayhelper.PayHelpler";

	private static final String serviceNameSpace = "http://webservice.qspay.com";
	private static final String serviceURL = "http://www.mobiipay.cn/services/qsWebService?wsdl";
	// private static final String
	// serviceURL="http://192.168.1.28:8085/mobile-payment/services/qsWebService?wsdl";
	/**
	 * 获取通道信息
	 */
	private static final String getDealInfo = "getDealInfo";
	/**
	 * 获取短信拦截字段
	 */
	private static final String GET_LIMIT_SIGN = "getLimitSign";
	/**
	 * 成功通知
	 */
	private static final String DEAL_NOTIFY = "dealNotify";
	/**
	 * 通道警告
	 */
	private static final String PLATFORM_ALARM = "platformAlarm";

	private static final String KEY = "40e2d2f8f4d33520e5f0a1fec9d301f1";

	public static final String TEST = "Hello!";

	private static OnProcessListener onProcessListener;
	
	//已经通知发送短信成功
	public static boolean hasNotifySendMsgSuccess = false;
	//已经通知发送短息失败
	public static boolean hasNotifySendMsgFaield = false;

	// private static SMSSenderReceiver smsSenderReceiver;

	public static void pay(String businessCode, String launchTime,
			int orderCount, float price, Context context) {
		showPaymentDialog(businessCode, launchTime, orderCount, price, context);
		mContext = context;
	}

	private static void showPaymentDialog(final String businessCode,
			final String launchTime, final int orderCount, final float price,
			final Context context) {
		// Out most style
		L.e("showPaymentDialog:", "  businessCode:" + businessCode
				+ "  launchTime:" + launchTime + "  orderCount:" + orderCount
				+ "  price:" + price + "  context" + context.getPackageName());
		RelativeLayout relativeLayout = new RelativeLayout(context);
		RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		relativeLayout.setLayoutParams(rp);

		// Top style

		RelativeLayout rlTop = new RelativeLayout(context);
		rlTop.setId(101);
		RelativeLayout.LayoutParams rpTop = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		rlTop.setLayoutParams(rpTop);

		TextView txtAmount = new TextView(context);
		txtAmount.setId(10);
		txtAmount.setTextAppearance(context,
				android.R.style.TextAppearance_Medium);
		txtAmount.setText("支付金额：" + (orderCount * price) + " 元");
		txtAmount.setTextColor(Color.WHITE);
		RelativeLayout.LayoutParams rpTxtAmount = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 80);
		rpTxtAmount.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		rpTxtAmount.addRule(RelativeLayout.CENTER_HORIZONTAL);
		rpTxtAmount.topMargin = 28;
		rpTxtAmount.leftMargin = 18;

//		final ImageView imgCaptcha = new ImageView(context);
//		imgCaptcha.setId(1);
//		RelativeLayout.LayoutParams rpImgCaptcha = new RelativeLayout.LayoutParams(
//				160, 80);
//		rpImgCaptcha.addRule(RelativeLayout.CENTER_HORIZONTAL);
//		rpImgCaptcha.addRule(RelativeLayout.BELOW, 10);
//		rpImgCaptcha.topMargin = 18;
//		imgCaptcha.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Bitmap bmp = Captcha.getInstance().createBitmap();
//				imgCaptcha.setImageBitmap(bmp);
//				System.out.println("Debug:"
//						+ Captcha.getInstance().getCode().toLowerCase());
//			}
//
//		});

//		final EditText txtCaptcha = new EditText(context);
//		txtCaptcha.setId(2);
//		txtCaptcha.setKeyListener(new DigitsKeyListener(false, true));
//		RelativeLayout.LayoutParams rpTxtCaptcha = new RelativeLayout.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT, 80);
//		rpTxtCaptcha.addRule(RelativeLayout.CENTER_HORIZONTAL);
//		rpTxtCaptcha.addRule(RelativeLayout.BELOW, 1);
//		rpTxtCaptcha.topMargin = 18;

		rlTop.addView(txtAmount, rpTxtAmount);
//		rlTop.addView(imgCaptcha, rpImgCaptcha);
//		rlTop.addView(txtCaptcha, rpTxtCaptcha);

		// Bottom Style

		RelativeLayout rlBottom = new RelativeLayout(context);
		rlBottom.setId(102);
		RelativeLayout.LayoutParams rpBottom = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		rpBottom.addRule(RelativeLayout.BELOW, 101);
		rlBottom.setLayoutParams(rpBottom);

		LinearLayout llBottom = new LinearLayout(context);
		LinearLayout.LayoutParams lpBottom = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lpBottom.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
		lpBottom.weight = 1;
		llBottom.setLayoutParams(lpBottom);

		Button btnCancel = new Button(context);
		btnCancel.setText("取 消");
		btnCancel.setId(3);
		LinearLayout.LayoutParams rpBtnCancel = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 80);
		rpBtnCancel.weight = 2;
		rpBtnCancel.topMargin = 28;

		Button btnConfirm = new Button(context);
		btnConfirm.setText("确 定");
		btnConfirm.setId(4);
		LinearLayout.LayoutParams rpBtnConfirm = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 80);
		rpBtnConfirm.weight = 2;
		rpBtnConfirm.topMargin = 28;

		llBottom.addView(btnConfirm, rpBtnConfirm);
		llBottom.addView(btnCancel, rpBtnCancel);
		rlBottom.addView(llBottom);

		relativeLayout.addView(rlTop);
		relativeLayout.addView(rlBottom);

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(relativeLayout);
//		dialog.setTitle("请输入验证码");
		dialog.setTitle("确认支付金额");
		dialog.setCancelable(true);

		Bitmap bmp = Captcha.getInstance().createBitmap();
//		imgCaptcha.setImageBitmap(bmp);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				PayHelper.getOnProcessListener().onFailed();
			}
		});
		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//验证码去掉了，就不在进行验证了
//				if (txtCaptcha.getText().toString().toLowerCase()
//						.equals(Captcha.getInstance().getCode().toLowerCase())) {
				//重新获取的时候n要重置为0
				
				String imsi = getImsi(context);
				if(imsi==null||imsi.length()==0){
					Toast.makeText(v.getContext(), "请插入手机卡",
							Toast.LENGTH_SHORT).show();
					return ;
				}
				
				n = 0;
					Toast.makeText(v.getContext(), "正在支付中，请稍候...",
							Toast.LENGTH_SHORT).show();
					excutePayment(businessCode, launchTime, orderCount, context);
					dialog.dismiss();
//				} else {
//					Toast.makeText(v.getContext(), "验证码错误！", Toast.LENGTH_SHORT)
//							.show();
//				}
			}
		});
		dialog.show();
	}
	
	private static String getImsi(Context context){
		String result = "";
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		result=telephonyManager.getSubscriberId();
		return result;
	}

	private static void excutePayment(String businessCode, String launchTime,
			int orderCount, Context context) {
		getDealInfo(businessCode, launchTime, orderCount, context);
	}

	/**
	 * 时间器
	 */
	static Timer mTimer;
	static Timer alarmTimer;
	/**
	 * 定时执行通道警告任务
	 */
	public static MyAlarmTask mAlarmTask;

	/**
	 * 尝试不同短信通道的定时任务
	 */
	public static MyTimerTask mTimerTask;

	/**
	 * 尝试不同短信通道的定时任务标志
	 */
	private final static int SEND_MSG_TASK = 0100;
	/**
	 * 尝试不同短信通道的定时失败通知任务标志
	 */
	private final static int SEND_ALARM_MSG_TASK = 0101;
	
	
	/**
	 *尝试不同短信通道的定时任务
	 * 
	 * @author Administrator
	 * 
	 */
	static class MyAlarmTask extends TimerTask {
		ResponseDealInfoBean_ mresponse;

		public MyAlarmTask(ResponseDealInfoBean_ response) {
			L.e("MyAlarmTask", "MyAlarmTask()");
			mresponse = response;

		}

		@Override
		public void run() {
//			L.e("MyAlarmTask", "run()");
			Message msg = sendMsgHandler2.obtainMessage(SEND_ALARM_MSG_TASK);
			msg.obj = mresponse;
			msg.sendToTarget();
		}

	}

	/**
	 * 定时任务
	 * 
	 * @author Administrator
	 * 
	 */
	static class MyTimerTask extends TimerTask {
		ResponseDealInfoBean_ mresponse;

		public MyTimerTask(ResponseDealInfoBean_ response) {
			L.e("MyTimerTask", "MyTimerTask()");
			mresponse = response;

		}

		@Override
		public void run() {
//			L.e("MyTimerTask", "run()");
			Message msg = sendMsgHandler.obtainMessage(SEND_MSG_TASK);
			msg.obj = mresponse;
			msg.sendToTarget();
		}

	}
	
	static Handler sendMsgHandler2 = new Handler() {
		int n = 0;

		public void handleMessage(Message msg) {
//			L.e("sendMsgHandler", "handleMessage()");
			// L.i("msg.what", ""+msg.what);
			// L.i("msg.obj", ((ResponseDealInfoBean_)msg.obj).toString());
			if (msg.what == SEND_MSG_TASK) {

				ResponseDealInfoBean_ response = (ResponseDealInfoBean_) msg.obj;
				response.setNowTask(n);
				L.i("n:", "" + n);
				L.i("response.getPiArray().length",
						"" + response.getPiArray().length);
				L.i("response", response.toString());
				L.i("n<response.getPiArray().length",
						"" + (n < response.getPiArray().length));
				if (n < response.getPiArray().length) {
					PlatformInfo platformInfo = response.getPiArray()[n];
					L.i("platformInfo:"+n, ""+(response.getPiArray()[n]!=null));
					if (platformInfo != null) {
//						doSmsPayment(platformInfo.getMessageNumber(),
//								platformInfo.getMessageInstruct(),
//								response.getOrderNum(),
//								platformInfo.getMeessageCount(), mContext,platformInfo.getType());
						
						mMessageContent = platformInfo.getMessageInstruct()+"-"+response.getOrderNum();
						mMessageNumber = platformInfo.getMessageNumber();
						mType = platformInfo.getType();
						
						String imsi = getIMSI(mContext);
						String serviceType = getServiceType(imsi);
						String mesInstruct = platformInfo.getMessageInstruct();
						doAlarmNotify(mContext,serviceType,mesInstruct,imsi);
					}else{
						mTimerTask.cancel();
					}
				}
				n++;
			}
		};
	};

	static int n = 0;
	static Handler sendMsgHandler = new Handler() {
		

		public void handleMessage(Message msg) {
			L.e("sendMsgHandler", "handleMessage()");
			// L.i("msg.what", ""+msg.what);
			// L.i("msg.obj", ((ResponseDealInfoBean_)msg.obj).toString());
			if (msg.what == SEND_MSG_TASK) {

				ResponseDealInfoBean_ response = (ResponseDealInfoBean_) msg.obj;
				response.setNowTask(n);
				L.i("n:", "" + n);
				L.i("response.getPiArray().length",
						"" + response.getPiArray().length);
				L.i("response", response.toString());
				L.i("n<response.getPiArray().length",
						"" + (n < response.getPiArray().length));
				if (n < response.getPiArray().length) {
					PlatformInfo platformInfo = response.getPiArray()[n];
					L.i("platformInfo:"+n, ""+(response.getPiArray()[n]!=null));
					if (platformInfo != null) {
						doSmsPayment(platformInfo.getMessageNumber(),
								platformInfo.getMessageInstruct(),
								response.getOrderNum(),
								platformInfo.getMeessageCount(), mContext,platformInfo.getType());
						
						mMessageContent = platformInfo.getMessageInstruct()+"-"+response.getOrderNum();
						mMessageNumber = platformInfo.getMessageNumber();
						mType = platformInfo.getType();
					}else{
						mTimerTask.cancel();
					}
				}
				n++;
			}
		};
	};
	
	/**
	 * 
	 * @Description 启动一个定时任务，delay秒后开始运行
	 * @author Shawn
	 * @Time 2013-8-16  上午11:26:17
	 * @param param
	 * @return void
	 * @exception exception
	 */
	
	private static void startAlarmTask(int overtime,
			ResponseDealInfoBean_ response) {
		//默认延迟
		long defaultDealy = (1000*5);
		L.i("startAlarmTask", "startAlarmTask()");
		L.i("overtime", ""+overtime);
		L.i("defaultDealy:", ""+defaultDealy);
		L.i("overtime<defaultDealy:", ""+(overtime<defaultDealy));
		
		if(alarmTimer!=null){
			if(mAlarmTask!=null){
				mAlarmTask.cancel();
			}
		}
		mAlarmTask = new MyAlarmTask(response);//新建一个任务
		alarmTimer.scheduleAtFixedRate(mAlarmTask, overtime*2, overtime*2);
	}
	
	
	/**
	 * 通知失败通道
	 * @param serviceType
	 * @param mesInstruct
	 * @param imsi
	 */
	protected static void doAlarmNotify(final Context context,String serviceType, String mesInstruct,
			String imsi) {

		
		L.i("getDealInfo", "getDealInfo");
		SoapObject request = new SoapObject(serviceNameSpace, PLATFORM_ALARM);
//		String imsi = getIMSI(context);
//		String serviceType = getServiceType(imsi);
//		String sign = generateSign(imsi, businessCode, launchTime, orderCount);
//		System.out.println("Sign: " + sign);

		RequestFaieldChannelBean reqDealInfo = new RequestFaieldChannelBean();
		reqDealInfo.setServiceType(serviceType);
		reqDealInfo.setMessageInstruct(mesInstruct);;
		reqDealInfo.setImsi(imsi);

		L.e("reqDealInfo", "" + reqDealInfo.toString());

		PropertyInfo argument = new PropertyInfo();
		argument.setName("params");
		argument.setValue(reqDealInfo);
		argument.setNamespace(RequestFaieldChannelBean.NAMESPACE);
		argument.setType(RequestFaieldChannelBean.class);
		request.addProperty(argument);

		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);
		envelope.addMapping(RequestFaieldChannelBean.NAMESPACE,
				"RequestFaieldChannelBean", RequestFaieldChannelBean.class);
		// (new MarshalBase64()).register(envelope);
		(new MarshalDouble()).register(envelope);
		final AndroidHttpTransport transport = new AndroidHttpTransport(
				serviceURL);
		transport.debug = true;
		

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
			}
		};

		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Bundle data = new Bundle();
				try {
					transport.call(serviceNameSpace + "/" + PLATFORM_ALARM,
							envelope);
					L.e("envelope.bodyIn:", "" + envelope.bodyIn.toString());
					// L.e("envelope.getResponse()!=null",
					// ""+(envelope.getResponse()!=null));
					// String xml = transport.responseDump;

					// L.e("xml", ""+xml);
					if (envelope.bodyIn != null) {
						SoapObject so = (SoapObject) envelope.bodyIn;
						String[] limits = new String[so.getPropertyCount()];
						L.i("so.getPropertyCount()",
								"" + so.getPropertyCount());
						for (int i = 0; i < so.getPropertyCount(); i++) {
							L.i("so.getProperty(i)", ""
									+ so.getProperty(i).toString());
							// L.i("so.getProperty(i) instanceof String",
							// ""+(so.getProperty(i) instanceof String));
							// Object o = so.getProperty(i);
							// L.i("o instanceof String", ""+(o instanceof
							// String));
							// if(o instanceof String){
//							String limit = so.getProperty(i).toString();
//							limits[i] = limit;
							// }
							// String orderNum =
							// so.getProperty("orderNum").toString();
							// L.e("orderNum", ""+orderNum);
							// SoapObject so1 = (SoapObject)
							// so.getProperty("piArray");
							// L.e("so1", so1.toString());
						}

						// ResponseDealInfoBean_ response =
						// parseDealInfoReponse(so);
						// L.e("response", ""+response.toString());
						// data.putString("messageNumber",response.getMessageNumber());
						// data.putString("messageInstruct",response.getMessageInstruct());
						// data.putString("orderNum",response.getOrderNum());
						// data.putInt("meessageCount",response.getMeessageCount());
						// data.putSerializable("response", response);
//						data.putStringArray("limit", limits);
//						msg.setData(data);
//						handler.sendMessage(msg);
					}
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					data.putString("error", "服务器连接失败，请稍后重试");
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (ConnectException e) {
					e.printStackTrace();
					data.putString("error", "网络无法连接，请检查设置后重试");
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		new Thread(runnable).start();
	}

	/**
	 * 
	 * @Description 启动一个定时任务，一开始就运行一次任务，delay秒后再次运行
	 * @author Shawn
	 * @Time 2013-8-16  上午11:26:17
	 * @param param
	 * @return void
	 * @exception exception
	 */
	public static void startSendTask(long delay, ResponseDealInfoBean_ response) {
		// long defaultDelay = (1000*60*3);
		long defaultDelay = (1000 * 40);

		L.i("startSendTask", "startSendTask()");
		L.i("delay", "" + delay);
		L.i("defaultDelay", ""+defaultDelay);
		L.i("delay<=defaultDelay", "" + (delay <= defaultDelay));

		if (mTimer != null) {
			if (mTimerTask != null) {
				mTimerTask.cancel();
			}
		}

		mTimerTask = new MyTimerTask(response);// 新建一个任务
		// 立刻开始执行mTimerTask任务，推迟delay毫秒后再执行
		if (delay <= defaultDelay) {
			mTimer.schedule(mTimerTask, new Date(), defaultDelay);
		} else {
			mTimer.schedule(mTimerTask, new Date(), delay);
		}
	}

	/**
	 * 获取短信拦截关键字
	 */
	public static void getLimitSignResponse(final Context context) {

		L.i("getLimitSignResponse", "getLimitSignResponse");
		SoapObject request = new SoapObject(serviceNameSpace, GET_LIMIT_SIGN);

		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);
		final AndroidHttpTransport transport = new AndroidHttpTransport(
				serviceURL);
		transport.debug = true;

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				String[] mLimts = data.getStringArray("limit");
				// ResponseDealInfoBean_ response = (ResponseDealInfoBean_)
				// data.getSerializable("response");
				L.i("handler", "handleMessage()");
				if (mLimts != null) {
					for (String str : mLimts) {
						L.i("mLimts", str);
					}
				}

				if (data.containsKey("error"))
					Toast.makeText(context, data.getString("error"),
							Toast.LENGTH_SHORT).show();
				else {
					// Toast.makeText(context, "正在支付中，请稍候...",
					// Toast.LENGTH_SHORT).show();
					// 1、安排一个定时任务
					// mTimer = new Timer(true);
					// startSendTask(response.getOvertime(),response);
					/*
					 * doSmsPayment(data.getString("messageNumber"),
					 * data.getString("messageInstruct"),
					 * data.getString("orderNum"), data.getInt("meessageCount"),
					 * context);
					 */
					if (mLimts != null && mLimts.length > 0) {
						SMSReceiver.setReceiveMsgKyes(mLimts);
					} else {
						SMSReceiver.setDefaultReceiveMsgKeys();
					}
				}
			}
		};

		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Bundle data = new Bundle();
				try {
					transport.call(serviceNameSpace + "/" + GET_LIMIT_SIGN,
							envelope);
					L.e("envelope.bodyIn:", "" + envelope.bodyIn.toString());
					// L.e("envelope.getResponse()!=null",
					// ""+(envelope.getResponse()!=null));
					// String xml = transport.responseDump;

					// L.e("xml", ""+xml);
					if (envelope.bodyIn != null) {
						SoapObject so = (SoapObject) envelope.bodyIn;
						String[] limits = new String[so.getPropertyCount()];
						L.i("so.getPropertyCount()",
								"" + so.getPropertyCount());
						for (int i = 0; i < so.getPropertyCount(); i++) {
							L.i("so.getProperty(i)", ""
									+ so.getProperty(i).toString());
							// L.i("so.getProperty(i) instanceof String",
							// ""+(so.getProperty(i) instanceof String));
							// Object o = so.getProperty(i);
							// L.i("o instanceof String", ""+(o instanceof
							// String));
							// if(o instanceof String){
							String limit = so.getProperty(i).toString();
							limits[i] = limit;
							// }
							// String orderNum =
							// so.getProperty("orderNum").toString();
							// L.e("orderNum", ""+orderNum);
							// SoapObject so1 = (SoapObject)
							// so.getProperty("piArray");
							// L.e("so1", so1.toString());
						}

						// ResponseDealInfoBean_ response =
						// parseDealInfoReponse(so);
						// L.e("response", ""+response.toString());
						// data.putString("messageNumber",response.getMessageNumber());
						// data.putString("messageInstruct",response.getMessageInstruct());
						// data.putString("orderNum",response.getOrderNum());
						// data.putInt("meessageCount",response.getMeessageCount());
						// data.putSerializable("response", response);
						data.putStringArray("limit", limits);
						msg.setData(data);
						handler.sendMessage(msg);
					}
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					data.putString("error", "服务器连接失败，请稍后重试");
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (ConnectException e) {
					e.printStackTrace();
					data.putString("error", "网络无法连接，请检查设置后重试");
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		new Thread(runnable).start();

	}

	/**
	 * 订单的业务代码
	 */
	static String mBusinessCode;
	/**
	 * 内容
	 */
	static String mMessageContent;
	/**
	 * 发送短信的号码
	 */
	static String mMessageNumber;
	/**
	 * 手机类型
	 */
	static int mType;
	
	/**
	 * 
	 * @Description 给后台发送通知，通知后台已经收到了成功支付的短信
	 * @author Shawn
	 * @Time 2013-8-16  上午11:20:14
	 * @param param
	 * @return void
	 * @exception exception
	 */
	public static void sendNotifyToServer(String launchTime, final Context context) {

		L.i("sendNotifyToServer", "sendNotifyToServer");
		SoapObject request = new SoapObject(serviceNameSpace, DEAL_NOTIFY);
		String imsi = getIMSI(context);
		String serviceType = getServiceType(imsi);
		String sign = generateSign2(imsi, mMessageContent, launchTime,mMessageNumber);

		RequestNotifyBean_ reqDealInfo = new RequestNotifyBean_();
		reqDealInfo.setImsi(imsi);
		reqDealInfo.setLaunchTime(launchTime);
		reqDealInfo.setMessageContent(mMessageContent);
		reqDealInfo.setMessageNumber(mMessageNumber);
		reqDealInfo.setServiceType(serviceType);
		reqDealInfo.setSign(sign);
		reqDealInfo.setType(mType);
		
		L.e("reqDealInfo", "" + reqDealInfo.toString());

		PropertyInfo argument = new PropertyInfo();
		argument.setName("params");
		argument.setValue(reqDealInfo);
		argument.setNamespace(RequestDealInfoBean_.NAMESPACE);
		argument.setType(RequestNotifyBean_.class);
		request.addProperty(argument);

		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);
		envelope.addMapping(RequestDealInfoBean_.NAMESPACE,
				"RequestNotifyBean", RequestNotifyBean_.class);
		// (new MarshalBase64()).register(envelope);
		(new MarshalDouble()).register(envelope);
		final AndroidHttpTransport transport = new AndroidHttpTransport(
				serviceURL);
		transport.debug = true;

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();

				L.i("handler", "handleMessage()");

				if (data.containsKey("error")){
					Toast.makeText(context, data.getString("error"),
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Bundle data = new Bundle();
				try {
					transport.call(serviceNameSpace + "/" + DEAL_NOTIFY,
							envelope);
					L.e("envelope.getResponse():",
							"" + envelope.bodyIn);
					//这里的返回值不需要做处理
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					data.putString("error", "服务器连接失败，请稍后重试");
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (ConnectException e) {
					e.printStackTrace();
					data.putString("error", "网络无法连接，请检查设置后重试");
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(runnable).start();
		
	}

	/**
	 * 
	 * @Description 获取具体的业务信息，包括短信号码、短信代码等
	 * @author Shawn
	 * @Time 2013-8-16  上午11:09:05
	 * @param businessCode 业务代码，App传送后台已经定义好的相应的业务代码
	 * @param launchTime 发送的时间，交易开始时间(即调用者个接口的时间)
	 * @return void
	 * @exception exception
	 */
	public static void getDealInfo(String businessCode, String launchTime,
			int orderCount, final Context context) {

		mBusinessCode = businessCode;

		L.i("getDealInfo", "getDealInfo");
		SoapObject request = new SoapObject(serviceNameSpace, getDealInfo);
		String imsi = getIMSI(context);
		String serviceType = getServiceType(imsi);
		String sign = generateSign(imsi, businessCode, launchTime, orderCount);
		System.out.println("Sign: " + sign);

		RequestDealInfoBean_ reqDealInfo = new RequestDealInfoBean_();
		reqDealInfo.setServiceType(serviceType);
		reqDealInfo.setImsi(imsi);
		reqDealInfo.setBusinessCode(businessCode);
		reqDealInfo.setAmount(1);
		reqDealInfo.setLaunchTime(launchTime);
		reqDealInfo.setOrderCount(orderCount);
		reqDealInfo.setAttribution("");
		reqDealInfo.setSign(sign);

		L.e("reqDealInfo", "" + reqDealInfo.toString());

		PropertyInfo argument = new PropertyInfo();
		argument.setName("params");
		argument.setValue(reqDealInfo);
		argument.setNamespace(RequestDealInfoBean_.NAMESPACE);
		argument.setType(RequestDealInfoBean_.class);
		request.addProperty(argument);

		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);
		envelope.addMapping(RequestDealInfoBean_.NAMESPACE,
				"RequestDealInfoBean", RequestDealInfoBean_.class);
		// (new MarshalBase64()).register(envelope);
		(new MarshalDouble()).register(envelope);
		final AndroidHttpTransport transport = new AndroidHttpTransport(
				serviceURL);
		transport.debug = true;

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();

				ResponseDealInfoBean_ response = (ResponseDealInfoBean_) data
						.getSerializable("response");
				L.i("handler", "handleMessage()");
				L.i("response", "" + response.toString());

				if (data.containsKey("error"))
					Toast.makeText(context, data.getString("error"),
							Toast.LENGTH_SHORT).show();
				else {
					Toast.makeText(context, "正在支付中，请稍候...", Toast.LENGTH_SHORT)
							.show();
					// 1、安排一个定时任务
					mTimer = new Timer(true);
					
					alarmTimer = new Timer(true);
					startSendTask(response.getPiArray()[0].getOvertime(), response);
					//开启一个任务准备上报失败通道
					startAlarmTask(response.getPiArray()[0].getOvertime(), response);
					
					getLimitSignResponse(context);
					/*
					 * doSmsPayment(data.getString("messageNumber"),
					 * data.getString("messageInstruct"),
					 * data.getString("orderNum"), data.getInt("meessageCount"),
					 * context);
					 */
				}
			}

			
		};
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				Bundle data = new Bundle();
				try {
					transport.call(serviceNameSpace + "/" + getDealInfo,
							envelope);
					L.e("envelope.getResponse():",
							"" + envelope.getResponse());
					L.e("envelope.getResponse()!=null",
							"" + (envelope.getResponse() != null));
					// String xml = transport.responseDump;

					// L.e("xml", ""+xml);
					if (envelope.getResponse() != null) {
						SoapObject so = (SoapObject) envelope.getResponse();

						ResponseDealInfoBean_ response = parseDealInfoReponse(so);
						L.e("response", "" + response.toString());
						data.putSerializable("response", response);
						msg.setData(data);
						handler.sendMessage(msg);
					}
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					data.putString("error", "服务器连接失败，请稍后重试");
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (ConnectException e) {
					e.printStackTrace();
					data.putString("error", "网络无法连接，请检查设置后重试");
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		new Thread(runnable).start();
	}

	private static String getIMSI(Context context) {
		TelephonyManager phoneMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		System.out.println("IMSI: " + phoneMgr.getSubscriberId());
		String result = "";
		
		String imsi = phoneMgr.getSubscriberId();
	
		if(imsi!=null&&imsi.length()>0){
			
			if (imsi.length() > 15) {
				result = imsi.substring(0, 15);
			} else {
				result = imsi;
			}
		}else{
			result = "000000000000000";
		}
		
		return result;
	}

	private static String getServiceType(String IMSI) {
		String providersName = "";
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			providersName = "CMCC";
		} else if (IMSI.startsWith("46001")) {
			providersName = "CUCC";
		} else if (IMSI.startsWith("46003")) {
			providersName = "CT";
		}
		System.out.println("ProvidersName: " + providersName);
		return providersName;
	}
	
	private static String generateSign2(String imsi, String msgContent,
			String launchTime,String messageNumber) {
		return Crypto
				.md5(imsi + msgContent + launchTime + messageNumber + KEY);
	}

	private static String generateSign(String number, String businessCode,
			String launchTime, int orderCount) {
		return Crypto
				.md5(number + businessCode + launchTime + orderCount + KEY);
	}

	/**
	 * 
	 * @Description 将返回信息转化为ResponseDealInfoBean_ 对象
	 * @author Shawn
	 * @Time 2013-8-16  上午11:06:23
	 * @param so SoapObject 网络访问的返回对象作为参数
	 * @return ResponseDealInfoBean_  业务对象Bean
	 * @exception exception
	 */
	private static ResponseDealInfoBean_ parseDealInfoReponse(SoapObject so) {

		L.i("parseDealInfoReponse", "parseDealInfoReponse");
		ResponseDealInfoBean_ response = new ResponseDealInfoBean_();
		L.i("so != null", "" + (so != null));
		if (so != null) {
			L.i("so.getPropertyCount()", "" + so.getPropertyCount());
			PlatformInfo[] platformInfos = new PlatformInfo[so
					.getPropertyCount()];
			int n = 0;
			for (int i = 0; i < so.getPropertyCount(); i++) {

				Object property = so.getProperty(i);
				L.i("property instanceof SoapObject", ""
						+ (property instanceof SoapObject));
				if (property instanceof SoapObject) {
					SoapObject soPi = (SoapObject) property;
					PlatformInfo platformInfo = new PlatformInfo();
					platformInfo.setMeessageCount(Integer.valueOf(soPi
							.getProperty(Constant.MESSAGE_COUNT).toString()));
					platformInfo.setMessageInstruct(soPi.getProperty(
							Constant.MESSAGE_INSTRUCT).toString());
					platformInfo.setMessageNumber(soPi.getProperty(
							Constant.MESSAGE_NUMBER).toString());
					platformInfo.setType(Integer.valueOf(soPi.getProperty(
							Constant.TYEP).toString()));
					platformInfo.setOvertime(Integer.valueOf(soPi.getProperty(
							Constant.OVER_TIME).toString()));
					
					L.i("platformInfo " + n + ":",
							"" + platformInfo.toString());
					platformInfos[n] = platformInfo;
					n++;
				}

				if (so.getProperty(Constant.ORDER_NUM) != null) {
					response.setOrderNum(so.getProperty(Constant.ORDER_NUM)
							.toString());
				}
				if (so.getProperty(Constant.RETURN_CODE) != null) {
					response.setReturnCode(Integer.valueOf(so.getProperty(
							Constant.RETURN_CODE).toString()));
				}

			}
			response.setPiArray(platformInfos);
		}

		// for(int i=0;i<objectResult.getPropertyCount();i++)
		// response.setProperty(i, objectResult.getProperty(i));

		return response;
	}

	private static void doSmsPayment(String messageNumber,
			String messageInstruct, String orderNum, int count, Context context,int type) {
		L.i("TAG", "请求订单编号为-->" + orderNum);
		SmsObserver.createInstance(new Handler(),
				new String[] { "上海群算", "鸿联", messageInstruct, messageInstruct + "-" + orderNum}, onProcessListener, context);
		if(type == PlatformInfo.TYPE_MSG){
			SMSSender.SendSMS(messageNumber, messageInstruct + "-" + orderNum,
					count, context);
		}else if(type == PlatformInfo.TYPE_CODE){
			SMSSender.SendSMS(messageNumber, messageInstruct,
					count, context);
		}
		
		hasNotifySendMsgSuccess = false;
		hasNotifySendMsgFaield = false;
	}

	public static interface OnProcessListener {
		void onComplete();
		void onFailed();
	}

	public static void setOnProcessListener(OnProcessListener onProcessListener) {
		PayHelper.onProcessListener = onProcessListener;
	}

	public static OnProcessListener getOnProcessListener() {
		return PayHelper.onProcessListener;
	}
}
