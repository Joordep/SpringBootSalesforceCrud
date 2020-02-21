package com.products.apiSalesForce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.products.apiSalesForce.model.AuthenticationResponse;
import com.products.apiSalesForce.service.SalesforceAPIService;


@RestController
public class SalesforceAPIController {

	@Autowired
	private SalesforceAPIService salesforceAPIService;
	
	@RequestMapping("/getInventoryList")
	public Object getInventoryList() {
		AuthenticationResponse authenticationResponse = salesforceAPIService.login();
		
		return salesforceAPIService.getInventoryList(authenticationResponse.getAccess_token(),
				authenticationResponse.getInstance_url());		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteInventoryItemById")
	public HttpEntity<Void> deleteInventoryItemById(String id) {
		AuthenticationResponse authenticationResponse = salesforceAPIService.login();
		salesforceAPIService.removeInventoryItemById(authenticationResponse.getAccess_token(),
				authenticationResponse.getInstance_url(), id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/createInventoryItem")
	public HttpEntity<Void> createInventoryItem(@RequestBody String payload) {
		AuthenticationResponse authenticationResponse = salesforceAPIService.login();
		
		salesforceAPIService.createInventoryItem(authenticationResponse.getAccess_token(),
				authenticationResponse.getInstance_url(), payload);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/updateInventoryItem")
	public HttpEntity<Void> updateInventoryItem(@RequestBody Map<String,String> payload) {
		AuthenticationResponse authenticationResponse = salesforceAPIService.login();
		
		salesforceAPIService.updateInventoryItem(authenticationResponse.getAccess_token(),
				authenticationResponse.getInstance_url(), payload);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
