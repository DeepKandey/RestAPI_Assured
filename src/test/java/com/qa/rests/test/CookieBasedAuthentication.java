package com.qa.rests.test;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CookieBasedAuthentication {

	@Test
	public void createJsessionId() {

		// 1. Create Rest Client
		RequestSpecification restClient = RestAssured.given();
		// 2. Add content tyep in header
		restClient.contentType(ContentType.JSON);

		// 3. Add Payload
		JSONObject json = new JSONObject();
		json.put("username", "Deepakggsipu");
		json.put("password", "jaishree@2024");
		restClient.body(json);

		// 4. Make the POST request
		Response response = restClient.post("http://localhost:8080/rest/auth/1/session");

		// 5. Get the status Code
		System.out.println("Status Code: " + response.getStatusCode());

		// 6. Get the body
		System.out.println("JSON Response Body: " + response.body().jsonPath().prettify());

		// 7. Get the cookies
		System.out.println("Response Cookies: " + response.getCookies());
		String JsessionId = response.getCookies().get("JSESSIONID");

		// 8. Creating issue
		Response issueResponse = RestAssured.given().contentType(ContentType.JSON).cookie("JSESSIONID", JsessionId)
				.body("{\r\n" + "    \"fields\": {\r\n" + "       \"project\":\r\n" + "       {\r\n"
						+ "          \"key\": \"AL\"\r\n" + "       },\r\n"
						+ "       \"summary\": \"REST ye merry gentlemen.\",\r\n"
						+ "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\r\n"
						+ "       \"issuetype\": {\r\n" + "          \"name\": \"Task\"\r\n" + "       }\r\n"
						+ "   }\r\n" + "}")
				.post("http://localhost:8080/rest/api/2/issue");

		System.out.println("Response Body for creating issue on JIRA: " + issueResponse.body().jsonPath().prettify());
	}
}