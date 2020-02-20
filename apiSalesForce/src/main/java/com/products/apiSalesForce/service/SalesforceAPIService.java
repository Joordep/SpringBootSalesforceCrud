package com.products.apiSalesForce.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.products.apiSalesForce.model.AuthenticationResponse;
import com.products.apiSalesForce.model.Inventory;

@Component
public class SalesforceAPIService {

	private static final String QUERY_INVENTORY = "/services/data/v25.0/query?q=select+Id,name, price__c,Quantity__c,description__c+from+Inventory__c";
	private static final String CREATE_UPDATE_REMOVE_INVENTORY_ITEM = "/services/data/v25.0/sobjects/Inventory__c/";

	public AuthenticationResponse login() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

		params.add("username", " joao.martinez@pdcase.com.br");
		params.add("password", " qW$wbs3DF-F6P3P");
		params.add("client_secret", "FDB37E782F3AA50796B5B01A5658EA63F7A47D442CFFDA0CB1FBD1287E60C252");
		params.add("client_id",
				"3MVG9LBJLApeX_PAJCQNs9iz_VT17k_AQPQuVVHD0xrlRjajSVhiyCYfTg9ZN0MJREVHLn3CQepPRK1yva2Z4");
		params.add("grant_type", "password");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);

		RestTemplate restTemplate = new RestTemplate();
		@SuppressWarnings("rawtypes")
		ResponseEntity response = restTemplate.postForEntity("https://login.salesforce.com/services/oauth2/token",
				request, AuthenticationResponse.class);
		return (AuthenticationResponse) response.getBody();
	}

//	public Inventory getAccountData(String accessToken, String instanceUrl) {
//		HttpHeaders headers = getHeaders(accessToken);
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//
//		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
//				headers);
//		RestTemplate restTemplate = new RestTemplate();
//		@SuppressWarnings("rawtypes")
//		ResponseEntity salesforceTestData = restTemplate.exchange(
//				instanceUrl + "/services/data/v25.0/sobjects/Inventory__c/a016g00000HL83fAAD", HttpMethod.GET, request,
//				Inventory.class);
//		return (Inventory) salesforceTestData.getBody();
//	}

	private HttpHeaders getHeaders(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + accessToken);
		return headers;
	}

	public Object getInventoryList(String accessToken, String instanceUrl) {
		HttpHeaders headers = getHeaders(accessToken);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity salesforceTestData = restTemplate.exchange(instanceUrl + QUERY_INVENTORY, HttpMethod.GET,
				request, QueryResultInventory.class);
		return salesforceTestData.getBody();
	}

	public void removeInventoryItemById(String accessToken, String instanceUrl, String id) {
		HttpHeaders headers = getHeaders(accessToken);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params,
				headers);
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.exchange(instanceUrl + CREATE_UPDATE_REMOVE_INVENTORY_ITEM + id, HttpMethod.DELETE, request,
				String.class);

	}

	public void createInventoryItem(String accessToken, String instanceUrl, String payload) {
		HttpHeaders headers = getHeaders(accessToken);
		
		HttpEntity<String> request = new HttpEntity<String>(payload, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(instanceUrl + CREATE_UPDATE_REMOVE_INVENTORY_ITEM, HttpMethod.POST, request,
				Inventory.class);

	}

	public void updateInventoryItem(String accessToken, String instanceUrl, Map<String, String> payload) {
		HttpHeaders headers = getHeaders(accessToken);

		String itemId = payload.get("Id");
		payload.remove("Id");

		try {
			String json = new ObjectMapper().writeValueAsString(payload);

			HttpEntity<String> request = new HttpEntity<String>(json, headers);
			RestTemplate restTemplate = new RestTemplate();

			restTemplate.postForObject(
					instanceUrl + CREATE_UPDATE_REMOVE_INVENTORY_ITEM + itemId + "?_HttpMethod=PATCH", request,
					Inventory.class);

		} catch (JsonProcessingException e) {
			System.out.println(e);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class QueryResult<T> {
		@SuppressWarnings("unused")
		public List<T> records;
	}

	private static class QueryResultInventory extends QueryResult<Inventory> {
	}

}