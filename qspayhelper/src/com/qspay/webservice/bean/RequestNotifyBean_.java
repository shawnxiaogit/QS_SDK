package com.qspay.webservice.bean;

import java.io.Serializable;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class RequestNotifyBean_ extends RequestNotifyBean implements
	KvmSerializable,Serializable{

	/**
	 * @Description : serialVersionUID :TODO
	 */
	private static final long serialVersionUID = 142315235423L;
	
	public static final String NAMESPACE = "http://bean.webservice.qspay.com/xsd";

	@Override
	public Object getProperty(int index) {
		Object value = null;
		switch(index){
		case 0:
			value = getImsi();
			break;
		case 1:
			value = getLaunchTime();
			break;
		case 2:
			value = getMessageContent();
			break;
		case 3:
			value = getMessageNumber();
			break;
		case 4:
			value = getServiceType();
			break;
		case 5:
			value = getSign();
			break;
		case 6:
			value = getType();
		}
		return value;
	}

	@Override
	public int getPropertyCount() {
		return 7;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
		switch (index) {
        case 0:
            info.name = "imsi";
            info.type = PropertyInfo.STRING_CLASS;
            info.namespace = NAMESPACE;
            break;
        case 1:
            info.name = "launchTime";
            info.type = PropertyInfo.STRING_CLASS;
            info.namespace = NAMESPACE;
            break;
        case 2:
            info.name = "messageContent";
            info.type = PropertyInfo.STRING_CLASS;
            info.namespace = NAMESPACE;
            break;
        case 3:
            info.name = "messageNumber";
            info.type = PropertyInfo.STRING_CLASS;
            info.namespace = NAMESPACE;
            break;
        case 4:
            info.name = "serviceType";
            info.type = PropertyInfo.STRING_CLASS;
            info.namespace = NAMESPACE;
            break;
        case 5:
            info.name = "sign";
            info.type = PropertyInfo.STRING_CLASS;
            info.namespace = NAMESPACE;
            break;
        case 6:
        	info.name = "type";
            info.type = PropertyInfo.INTEGER_CLASS;
            info.namespace = NAMESPACE;
        default:
            break;
    }
		
	}

	@Override
	public void setProperty(int index, Object value) {
		
		switch(index){
		case 0:
			setImsi(value.toString());
			break;
		case 1:
			setLaunchTime(value.toString());
			break;
		case 2:
			setMessageContent(value.toString());
			break;
		case 3:
			setMessageNumber(value.toString());
			break;
		case 4:
			setServiceType(value.toString());
			break;
		case 5:
			setSign(value.toString());
			break;
		case 6:
			setType(Integer.valueOf(value.toString()));
			break;
		default:
			break;
		}
	}

	@Override
	public String toString() {
		return "RequestNotifyBean_ [getType()=" + getType()
				+ ", getMessageNumber()=" + getMessageNumber() + ", getImsi()="
				+ getImsi() + ", getMessageContent()=" + getMessageContent()
				+ ", getLaunchTime()=" + getLaunchTime() + ", getSign()="
				+ getSign() + ", getServiceType()=" + getServiceType() + "]";
	}
	

}
