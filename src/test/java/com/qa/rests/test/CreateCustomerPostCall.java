package com.qa.rests.test;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateCustomerPostCall {

	@Test
	public void createCustomerTest() {
		// 1.Define the base URI
		RestAssured.baseURI = "http://restapi.demoqa.com/customer";

		// 2. Define the http request
		RequestSpecification httpRequest = RestAssured.given();

		// 3.Create a json object with all the fields
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("FirstName", "Deepak");
		jsonRequest.put("LastName", "Rai");
		jsonRequest.put("UserName", "test190");
		jsonRequest.put("Password", "test123");
		jsonRequest.put("Email", "Deep@gmail.com");

		// 4.Add header
		httpRequest.header("Content-Type", "application/json");

		// 5.Add json payload to the body of the request
		httpRequest.body(jsonRequest.toJSONString());

		// 6.Execute the request and get the response
		Response httpResponse = httpRequest.request(Method.POST, "/register");
		// Response httpResponse = httpRequest.post("/register");

		// 7.Get the response body
		String responseBody = httpResponse.getBody().asString();
		System.out.println("Response Body is--->" + responseBody);

		// 8.Get the headers
		Headers headers = httpResponse.getHeaders();
		System.out.println("headers are--->" + headers);

		// 9.Get the status code
		int statusCode = httpResponse.getStatusCode();
		System.out.println("Status Code is--->" + statusCode);

		// 10.Get the Status Line
		String statusLine = httpResponse.getStatusLine();
		System.out.println("Status Line is --->" + statusLine);

	}
}
