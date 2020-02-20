package com.products.apiSalesForce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Inventory {

	@JsonProperty("Id")
	private String id;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("price__c")
	private Double priceC;

	@JsonProperty("Quantity__c")
	private Integer quantityC;
	
	@JsonProperty("description__c")
	private String descriptionC;

	public String getDescriptionC() {
		return descriptionC;
	}

	public void setDescriptionC(String descriptionC) {
		this.descriptionC = descriptionC;
	}

	@JsonProperty("Id")
	public String getId() {
		return id;
	}

	@JsonProperty("Id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("Name")
	public String getName() {
		return name;
	}

	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("price__c")
	public Double getPriceC() {
		return priceC;
	}

	@JsonProperty("price__c")
	public void setPriceC(Double priceC) {
		this.priceC = priceC;
	}

	@JsonProperty("Quantity__c")
	public Integer getQuantityC() {
		return quantityC;
	}

	@JsonProperty("Quantity__c")
	public void setQuantityC(Integer quantityC) {
		this.quantityC = quantityC;
	}

}
