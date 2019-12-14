package com.qa.rests.test;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.rest.objects.JIRAObjects.ObjectsForJIRATask.CreateJIRATask;
import com.qa.rest.objects.JIRAObjects.ObjectsForJIRATask.Fields;
import com.qa.rest.objects.JIRAObjects.ObjectsForJIRATask.IssueType;
import com.qa.rest.objects.JIRAObjects.ObjectsForJIRATask.Project;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateJIRATaskViaAPI {
	@SuppressWarnings("unchecked")
	@Test
	public void createJsessionId() throws JsonProcessingException {

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

		// 8. Create payload
		Project project = new Project("AL");
		String summary = "Hey, Task create using REST API";
		String description = "Creating of an issue using project keys and issue type names using the REST API";
		IssueType issuetype = new IssueType("Task");
		Fields fields = new Fields(project, summary, description, issuetype);
		CreateJIRATask createJIRATask = new CreateJIRATask(fields);

		// Used objectMapper to convert POJO class into JSON string(Serialization)
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonRequestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createJIRATask);
		System.out.println("JSON Request Body: " + jsonRequestBody);

		// 9. Creating issue
		Response issueResponse = RestAssured.given().contentType(ContentType.JSON).cookie("JSESSIONID", JsessionId)
				.body(jsonRequestBody).post("http://localhost:8080/rest/api/2/issue");

		System.out.println("Response Body for creating issue on JIRA: " + issueResponse.body().jsonPath().prettify());
	}
} // End of class CreateJIRATaskViaAPI