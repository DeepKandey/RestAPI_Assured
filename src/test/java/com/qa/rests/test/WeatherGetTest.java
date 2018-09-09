package com.qa.rests.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.util.TestBase;
import com.qa.util.TestUtil;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherGetTest extends TestBase {

	@BeforeMethod
	public void setUp() {
		init();
	}

	@DataProvider
	public Object[][] getData() {
		Object[][] weatherData = TestUtil.getExcelData("C:/Users/deepa/Downloads/APITestData.xlsx", "WeatherInfo");
		return weatherData;
	}

	@Test(dataProvider="getData")
	public void getWeatherDetailsWithCorrectCityNameTest(String city, String HTTPMethod, String humidity,
			String temperature, String weatherdescription, String windspeed, String winddirectiondegree) {

		// 1.Define the base URL
		RestAssured.baseURI = prop.getProperty("ServiceUrl");

		// 2.Define Http Request
		RequestSpecification httpRequest = RestAssured.given();

		// 3.make a request/ execute this request
		Response httpResponse = httpRequest.request(Method.GET, "/" + city);

		// 4.get the response body
		String responseBody = httpResponse.getBody().asString();
		System.out.println("Response Body is-->" + responseBody);
		Assert.assertEquals(responseBody.contains(city), true);

		// 5.get the status code and validate it.
		int statusCode = httpResponse.getStatusCode();
		System.out.println("Status Code is --->" + statusCode);
		Assert.assertEquals(statusCode, TestUtil.RESPONSE_CODE_200);

		// 6.get the status line
		System.out.println("the status line is--->" + httpResponse.getStatusLine());

		// get reponse header
		Headers headers = httpResponse.getHeaders();
		System.out.println(headers);
		System.out.println("Content type from response object-->" + httpResponse.getHeader("Content-Type"));
		System.out.println("Content type from header object-->" + headers.get("Content-Type"));
		String content_length = httpResponse.getHeader("Content-Length");
		System.out.println("Content length from header object-->" + content_length);

		// get the key value by using JSONPath
		JsonPath jsonPathValue = httpResponse.jsonPath();
		String cityName = jsonPathValue.get("City");
		System.out.println("the value of city is -->" + cityName);
	}

	@Test(enabled=false)
	public void getWeatherDetailsWithWrongCityNameTest() {

		// 1.Define the base URL
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city/New";

		// 2.Define Http Request
		RequestSpecification httpRequest = RestAssured.given();

		// 3.make a request/ execute this request
		Response httpResponse = httpRequest.request(Method.GET, "/test123");

		// 4.get the response body
		String responseBody = httpResponse.getBody().asString();
		System.out.println("Response Body is-->" + responseBody);
		Assert.assertEquals(responseBody.contains("internal error"), true);

		// 5.get the status code and validate it.
		int statusCode = httpResponse.getStatusCode();
		System.out.println("Status Code is --->" + statusCode);
		Assert.assertEquals(statusCode, 400);

		// 6.get the status line
		System.out.println("the status line is--->" + httpResponse.getStatusLine());

		// get reponse header
		Headers headers = httpResponse.getHeaders();
		System.out.println(headers);

	}
}
