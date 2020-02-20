package com.products.apiSalesForce.model;

public class InventoryResponse {
	private String Name;
	private Double price__c; 
	private int Quantity__c;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Double getPrice__c() {
		return price__c;
	}
	public void setPrice__c(Double price__c) {
		this.price__c = price__c;
	}
	public int getQuantity__c() {
		return Quantity__c;
	}
	public void setQuantity__c(int quantity__c) {
		Quantity__c = quantity__c;
	}
	
	
}
