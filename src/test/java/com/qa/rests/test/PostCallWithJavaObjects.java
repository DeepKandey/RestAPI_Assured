package com.qa.rests.test;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.qa.rest.objects.CustomerResponseFailure;
import com.qa.rest.objects.CustomerResponseSuccess;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class PostCallWithJavaObjects {
	@Test
	public void createCustomerTest() {
		// 1.Define the base URI
		RestAssured.baseURI = "http://restapi.demoqa.com/customer";

		// 2. Define the request/client
		RequestSpecification httpRequest = RestAssured.given();

		HashMap<Object,Object> additionalDetails = new HashMap<Object,Object>();
		additionalDetails.put("FirstName", "Deepak");
		additionalDetails.put("LastName", "Rai");
		additionalDetails.put("UserName", "test@19");
		additionalDetails.put("Password", "test123");
		additionalDetails.put("Email", "Deep#@gmail.com");
		
		// 3.Create a json object with all the fields
		JSONObject jsonRequest = new JSONObject(additionalDetails);
//		jsonRequest.put("FirstName", "Deepak");
//		jsonRequest.put("LastName", "Rai");
//		jsonRequest.put("UserName", "test@19");
//		jsonRequest.put("Password", "test123");
//		jsonRequest.put("Email", "Deep#@gmail.com");

		// 4.Add header
		httpRequest.header("Content-Type", "application/json");

		// 5.Add json payload to the body of the request
		httpRequest.body(jsonRequest.toJSONString());

		// 6.Execute the request and get the response
		Response httpResponse = httpRequest.post("/register");

		// 7.Get the response body
		String responseBody = httpResponse.getBody().asString();
		System.out.println("Response Body is--->" + responseBody);

		// 8.Deserialization the response into CustomerResponse class
		if (httpResponse.statusCode() == 201) {
			CustomerResponseSuccess customerResponse = httpResponse.as(CustomerResponseSuccess.class);
			System.out.println(customerResponse.SuccessCode);
			System.out.println(customerResponse.Message);
			Assert.assertEquals("OPERATION_SUCCESS", customerResponse.SuccessCode);
			Assert.assertEquals("Operation completed successfully", customerResponse.Message);
		} else if (httpResponse.statusCode() == 200) {
			CustomerResponseFailure customerResponse = httpResponse.as(CustomerResponseFailure.class);
			System.out.println(customerResponse.FaultId);
			System.out.println(customerResponse.fault);
			Assert.assertEquals("User already exists", customerResponse.FaultId);
			Assert.assertEquals("FAULT_USER_ALREADY_EXISTS", customerResponse.fault);
		}
	}
}