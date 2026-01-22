package com.order.dto;

import java.util.List;

public class OrderEvent {
	
	 private int orderId;
	    private int userId;
	    private String eventType;
	    public String getEventType() {
			return eventType;
		}
		public void setEventType(String eventType) {
			this.eventType = eventType;
		}
		private List<CartItemDTO> items;
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
		public List<CartItemDTO> getItems() {
			return items;
		}
		public void setItems(List<CartItemDTO> items) {
			this.items = items;
		}
	    

}
