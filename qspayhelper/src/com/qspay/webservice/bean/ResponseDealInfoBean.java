/**
 * 
 */
package com.qspay.webservice.bean;

/**
 * @author WuPing
 *
 */
public class ResponseDealInfoBean {
	/**
	 * 短信通道信息
	 */
	private PlatformInfo[] piArray;
	/**
	 * 返回码
	 */
	private int returnCode;
	public PlatformInfo[] getPiArray() {
		return piArray;
	}
	public void setPiArray(PlatformInfo[] piArray) {
		this.piArray = piArray;
	}
	/**
	 * 订单号
	 */
	private String orderNum;

	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public static class PlatformInfo {
		/**
		 * 短信类型
		 */
		public static final int TYPE_MSG = 1;
		/**
		 * 代码类型
		 */
		public static final int TYPE_CODE = 2;
		/**
		 * 短信号码
		 */
		private String messageNumber;
		/**
		 * 短信指令
		 */
		private String messageInstruct;
		/**
		 * 短信条数
		 */
		private int meessageCount;
		/**
		 * 短信扣费类型：1/2  短信/代码
		 */
		private int type;
		/**
		 * 超时时间
		 */
		private int overtime;
		public int getOvertime() {
			return overtime;
		}
		public void setOvertime(int overtime) {
			this.overtime = overtime;
		}
		public String getMessageNumber() {
			return messageNumber;
		}
		public void setMessageNumber(String messageNumber) {
			this.messageNumber = messageNumber;
		}
		public String getMessageInstruct() {
			return messageInstruct;
		}
		public void setMessageInstruct(String messageInstruct) {
			this.messageInstruct = messageInstruct;
		}
		public int getMeessageCount() {
			return meessageCount;
		}
		public void setMeessageCount(int meessageCount) {
			this.meessageCount = meessageCount;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		@Override
		public String toString() {
			return "PlatformInfo [messageNumber=" + messageNumber
					+ ", messageInstruct=" + messageInstruct
					+ ", meessageCount=" + meessageCount + ", type=" + type
					+ ", overtime=" + overtime + "]";
		}
		
	}
	@Override
	public String toString() {
		return "ResponseDealInfoBean [returnCode=" + returnCode + ", orderNum="
				+ orderNum + "]";
	}
	
}
