/**
 * 
 */
package com.qspay.webservice.bean;

/**
 * @author WuPing
 *
 */
public class RequestNotifyBean {
	/**
	 * IMSI
	 */
	private String imsi;
	private String messageContent;
	private String launchTime;
	/**
     * imsi+messageContent+launchTime+messageNumber+key的MD532位小写加密字符串
     */
    private String sign;
    
    private String messageNumber;
    /**
	 * 运营商类型
	 */
    private int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMessageNumber() {
		return messageNumber;
	}
	public void setMessageNumber(String messageNumber) {
		this.messageNumber = messageNumber;
	}
	private String serviceType;
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getLaunchTime() {
		return launchTime;
	}
	public void setLaunchTime(String launchTime) {
		this.launchTime = launchTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

}
