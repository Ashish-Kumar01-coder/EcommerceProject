package com.notificationservice.dto;

public class OrderEvent {
	
	 private Integer orderId;
	    private Integer userId;
	    private String eventType;
		public Integer getOrderId() {
			return orderId;
		}
		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public String getEventType() {
			return eventType;
		}
		public void setEventType(String eventType) {
			this.eventType = eventType;
		}
	    
	    

}
