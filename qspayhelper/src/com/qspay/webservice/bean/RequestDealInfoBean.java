package com.qspay.webservice.bean;

public class RequestDealInfoBean {
	/**
	 * 运营商类型
	 */
	private String serviceType;
	/**
	 * 手机号码
	 */
    private String imsi;
    /**
     * 业务代码
     */
    private String businessCode;
    /**
     * 金额
     */
    private double amount;
    /**
     * 时间参数
     */
    private String launchTime;
    /**
     * imsi+businessCode+launchTime+orderCount+key的MD532位小写加密字符串
     */
    private String sign;
    /**
     * 业务订购次数(默认为：1)
     */
    private int orderCount;
    /**
     * 手机号码归属地
     */
    private String attribution;
    
    
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public String getAttribution() {
		return attribution;
	}
	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

}
