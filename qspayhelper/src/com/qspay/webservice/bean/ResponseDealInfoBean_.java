package com.qspay.webservice.bean;

import java.io.Serializable;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.util.Log;

public class ResponseDealInfoBean_ extends ResponseDealInfoBean implements KvmSerializable,Serializable{
	private int now_task;
	public void setNowTask(int task){
		task = now_task;
	}
	public int getNowTask(){
		return now_task;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 12340932423L;
	public static final String NAMESPACE = "http://bean.webservice.qspay.com/xsd";

	@Override
	public Object getProperty(int index) {
		Object value = null;
        switch (index) {
            case 0:
            	value = getOrderNum();
//                value = getMeessageCount();
                break;
            case 1:
//            	value = getOvertime();
//                value = getMessageInstruct();
                break;
            case 2:
            	value = getPiArray();
//                value = getMessageNumber();
                break;
            case 3:
            	value = getReturnCode();
//                value = getOrderNum();
                break;
//            case 4:
//                value = getReturnCode();
//                break;
        }
        return value;
	}

	@Override
	public int getPropertyCount() {
		return 5;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
			switch (index) {
	        case 0:
	            info.name = "meessageCount";
	            info.type = PropertyInfo.INTEGER_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        case 1:
	            info.name = "messageInstruct";
	            info.type = PropertyInfo.STRING_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        case 2:
	            info.name = "messageNumber";
	            info.type = PropertyInfo.STRING_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        case 3:
	            info.name = "orderNum";
	            info.type = PropertyInfo.STRING_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        case 4:
	            info.name = "returnCode";
	            info.type = PropertyInfo.INTEGER_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        default:
	            break;
	    }
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		  case 0:
			  setOrderNum(value.toString());
//		      setMeessageCount(Integer.valueOf(value.toString()));
		      break;
		  case 1:
//			  setOvertime(Integer.valueOf(value.toString()));
//		      setMessageInstruct(value.toString());
		      break;
		  case 2:
			  SoapObject so = (SoapObject)value;
			  PlatformInfo[] platformInfos = new PlatformInfo[so.getAttributeCount()*2];
			  for(int i = 0;i<so.getPropertyCount();i++){
				  SoapObject object = (SoapObject) so.getProperty(i);
				  PlatformInfo platformInfo = new PlatformInfo();
				  platformInfo.setMeessageCount(Integer.valueOf(object.getProperty("meessageCount").toString()));
				  platformInfo.setMessageInstruct(object.getProperty("messageInstruct").toString());
				  platformInfo.setMessageNumber(object.getProperty("messageNumber").toString());
				  platformInfo.setType(Integer.valueOf(object.getProperty("type").toString()));
				  platformInfos[i] = platformInfo;
			  }
			  
			  setPiArray((PlatformInfo[])value);
//		      setMessageNumber(value.toString());
		      break;
		  case 3:
			  setReturnCode(Integer.valueOf(value.toString()));
//		      setOrderNum(value.toString());
		      break;
//		  case 4:
//		      setReturnCode(Integer.valueOf(value.toString()));
//		      break;
		  default:
		      break;
		}
	}

	@Override
	public String toString() {
		return "ResponseDealInfoBean_   :"+", getReturnCode()=" + getReturnCode()
				+ ", getOrderNum()=" + getOrderNum() +" ,piArrayData"+getPiArrayData()
				+" now_task = "+now_task+"]";
	}

	private String getPiArrayData(){
		StringBuffer sb = new StringBuffer();
		Log.e("getPiArray()!=null", ""+(getPiArray()!=null));
		if(getPiArray()!=null){
			Log.e("getPiArray().length", ""+(getPiArray().length));
			Log.e("getPiArray()!=null&&getPiArray().length>0", ""+(getPiArray()!=null&&getPiArray().length>0));
		}
		
		if(getPiArray()!=null&&getPiArray().length>0){
			for(int i=0;i<getPiArray().length;i++){
				PlatformInfo platformInfo = getPiArray()[i];
				if(platformInfo!=null){
					sb.append(platformInfo.toString());
				}
			}
		}
		sb.append("----");
		
		return sb.toString();
	}
	
	
}
