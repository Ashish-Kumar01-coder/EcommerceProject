package com.paymentservice.dto;

public class OrderEvent {
	
	 private String eventType; // ORDER_CREATED, PAYMENT_SUCCESS, PAYMENT_FAILED
	    private int orderId;
	    private int userId;
	    private double amount;
		public String getEventType() {
			return eventType;
		}
		public void setEventType(String eventType) {
			this.eventType = eventType;
		}
		public int getOrderId() {
			return orderId;
		}
		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
	    
	    
	    
	    

}
