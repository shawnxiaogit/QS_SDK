package com.qspay.webservice.bean;

import java.io.Serializable;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 通知通道失败实体类
 * @author shawn
 *
 */
public class RequestFaieldChannelBean implements KvmSerializable,Serializable{
	
	
	private String serviceType;
	private String messageInstruct;
	private String imsi;
	public static final String NAMESPACE = "http://bean.webservice.qspay.com/xsd";

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getMessageInstruct() {
		return messageInstruct;
	}

	public void setMessageInstruct(String messageInstruct) {
		this.messageInstruct = messageInstruct;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	private static final long serialVersionUID = 1197286553586492497L;

	@Override
	public Object getProperty(int index) {
		Object value = null;
		switch(index){
		case 0:{
			value = getServiceType();
		}break;
		case 1:{
			value = getMessageInstruct();
		}break;
		case 2:{
			value = getImsi();
		}break;
		}
		return value;
	}

	@Override
	public int getPropertyCount() {
		return 3;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch(index){
		case 0:{
			info.name = "serviceType";
			info.type = PropertyInfo.STRING_CLASS;
			info.namespace = NAMESPACE;
		}break;
		case 1:{
			info.name = "messageInstruct";
			info.type = PropertyInfo.STRING_CLASS;
			info.namespace = NAMESPACE;
		}break;
		case 2:{
			info.name = "imsi";
			info.type = PropertyInfo.STRING_CLASS;
			info.namespace = NAMESPACE;
		}break;
		default:
			break;
		}
		
	}

	@Override
	public void setProperty(int index, Object object) {
		switch(index){
		case 0:{
			setServiceType(object.toString());
		}break;
		case 1:{
			setMessageInstruct(object.toString());
		}break;
		case 2:{
			setImsi(object.toString());
		}break;
		}
	}

	@Override
	public String toString() {
		return "RequestFaieldChannelBean [serviceType=" + serviceType
				+ ", messageInstruct=" + messageInstruct + ", imsi=" + imsi
				+ "]";
	}
	
}
