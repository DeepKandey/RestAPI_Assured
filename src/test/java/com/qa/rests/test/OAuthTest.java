package com.qa.rests.test;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.RequestSpecification;

public class OAuthTest {
	@Test
	public void postTweet() {

		// Making request and getting response in BDD format
		/*
		 * Response oAuthresponse1 = RestAssured.given().auth()
		 * .oauth("rcXYPa3ic1bBtwTXsJ2u7G9op",
		 * "wK8FvQeIrmkunpvjLFSjcPCjbQu8HoNbi9816Gf5WEJl1iqeZR",
		 * "832787898-mYoOZUWsUiAPQedQetCDld9FkTDAy1u2z5NSKMzI",
		 * "KBvHxbZaOyfKMmmCVeIzPLPQBuLKoNKZbpb4UP12UG9Wo")
		 * .post("https://api.twitter.com/1.1/statuses/update.json?status=This is my first tweet via API"
		 * );
		 * 
		 * // Status Code System.out.println("Response Code for the tweet:--> " +
		 * oAuthresponse1.getStatusCode());
		 */

		/*----------Sequence wise-------------*/

		// 1. Define Request/Client
		RequestSpecification requestSpecification = RestAssured.given();

		// 2. Define Authentication
		AuthenticationSpecification authenticationSpecification = requestSpecification.auth();

		// 3. Define OAuthRequestSpecification
		RequestSpecification oAuthRequestSpecification = authenticationSpecification.oauth("rcXYPa3ic1bBtwTXsJ2u7G9op",
				"wK8FvQeIrmkunpvjLFSjcPCjbQu8HoNbi9816Gf5WEJl1iqeZR",
				"832787898-mYoOZUWsUiAPQedQetCDld9FkTDAy1u2z5NSKMzI", "KvHxbZaOyfKMmmCVeIzPLPQuLKoNKZbpbUP12UG9Wo");

		// 4. POST the request
		Response oAuthResponse2 = oAuthRequestSpecification.request(Method.POST,
				"https://api.twitter.com/1.1/statuses/update.json?status=This is tweet via API");

		// 5. Status Code for the tweet
		System.out.println("Response Code for the tweet:--> " + oAuthResponse2.getStatusCode());

		// 6. Response Body
		String responseBody = oAuthResponse2.getBody().asString();
		System.out.println(responseBody);

		// 7. Pretty JSON String as response Body
		String jsonResponseBody = oAuthResponse2.getBody().jsonPath().prettify();
		System.out.println(jsonResponseBody);

		// 8. Get tweet id
		JsonPath jsonPathResponse = oAuthResponse2.jsonPath();
		String tweetId = jsonPathResponse.get("id_str");
		System.out.println("My tweet id is--> " + tweetId);

		// 9. Destroy Tweet
		RequestSpecification oAuthRequestSpecificationForDelete = authenticationSpecification.oauth(
				"rcXYPa3ic1bBtwTXsJ2u7G9op", "wK8FvQeIrmkunpvjLFSjcPCjbQu8HoNbi9816Gf5WEJl1iqeZR",
				"832787898-mYoOZUWsUiAPQedQetCDld9FkTDAyu2z5NSKMzI", "KvHxbZaOyfKMmmCVeIzPLPQBuLKoNKZbpbUP12UG9Wo");

		Response oAuthresponseForDelete = oAuthRequestSpecificationForDelete.request(Method.POST,
				"https://api.twitter.com/1.1/statuses/destroy/" + tweetId + ".json");

		// 10. Response code when deleted tweet
		System.out.println("Response Code for deleting tweet:--> " + oAuthresponseForDelete.getStatusCode());
	}
}