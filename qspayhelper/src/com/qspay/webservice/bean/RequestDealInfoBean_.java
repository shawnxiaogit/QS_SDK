package com.qspay.webservice.bean;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class RequestDealInfoBean_  extends RequestDealInfoBean implements KvmSerializable{

	public static final String NAMESPACE = "http://bean.webservice.qspay.com/xsd";
	
	@Override
	public Object getProperty(int index) {
		Object value = null;
        switch (index) {
            case 0:
                value = getAmount();
                break;
            case 1:
                value = getAttribution();
                break;
            case 2:
                value = getBusinessCode();
                break;
            case 3:
                value = getImsi();
                break;
            case 4:
                value = getLaunchTime();
                break;
            case 5:
                value = getOrderCount();
                break;
            case 6:
                value = getServiceType();
                break;
            case 7:
                value = getSign();
                break;
        }
        return value;
	}

	@Override
	public int getPropertyCount() {
		return 8;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
			switch (index) {
	        case 0:
	            info.name = "amount";
	            info.type = MarshalDouble.class;
	            info.namespace = NAMESPACE;
	            break;
	        case 1:
	            info.name = "attribution";
	            info.type = PropertyInfo.STRING_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        case 2:
	            info.name = "businessCode";
	            info.type = PropertyInfo.STRING_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        case 3:
	            info.name = "imsi";
	            info.type = PropertyInfo.STRING_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        case 4:
	            info.name = "launchTime";
	            info.type = PropertyInfo.STRING_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        case 5:
	            info.name = "orderCount";
	            info.type = PropertyInfo.INTEGER_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        case 6:
	            info.name = "serviceType";
	            info.type = PropertyInfo.STRING_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        case 7:
	            info.name = "sign";
	            info.type = PropertyInfo.STRING_CLASS;
	            info.namespace = NAMESPACE;
	            break;
	        default:
	            break;
	    }
	}

	@Override
	public void setProperty(int index, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "RequestDealInfoBean_ [getServiceType()=" + getServiceType()
				+ ", getImsi()=" + getImsi() + ", getBusinessCode()="
				+ getBusinessCode() + ", getAmount()=" + getAmount()
				+ ", getLaunchTime()=" + getLaunchTime() + ", getSign()="
				+ getSign() + ", getOrderCount()=" + getOrderCount()
				+ ", getAttribution()=" + getAttribution() + "]";
	}
	
}
