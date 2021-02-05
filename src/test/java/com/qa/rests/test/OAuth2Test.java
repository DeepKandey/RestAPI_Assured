package com.qa.rests.test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class OAuth2Test {
  @Test
  public void OAuth2DemoUsingCOOP() {
    /* Create Access Token */
    // 1. Define Request/client and form parameters
    RequestSpecification requestSpecificationForAccessToken =
        RestAssured.given()
            .formParam("client_id", "MYRESTAPIBLOG")
            .formParam("client_secret", "56c85800a3be74436880ff6cbcabf565")
            .formParam("grant_type", "client_credentials");

    // 2. POST the request
    Response responseForAccessToken =
        requestSpecificationForAccessToken.request(
            Method.POST, "http://coop.apps.symfonycasts.com/token");

    // 3. Status Code
    System.out.println(
        "Response Code for request to generate access token:--> "
            + responseForAccessToken.getStatusCode());

    // 4. Response Body
    String responseBodyForAccesToken = responseForAccessToken.getBody().asString();
    System.out.println(responseBodyForAccesToken);

    // 5. Pretty JSON String as response Body
    String jsonResponseBodyForAccessToken = responseForAccessToken.getBody().jsonPath().prettify();
    System.out.println(jsonResponseBodyForAccessToken);

    // 6. Get the access Token
    JsonPath jsonPathResponse = responseForAccessToken.jsonPath();
    String accessToken = jsonPathResponse.get("access_token");
    System.out.println("Access Token--> " + accessToken);

    /* Use generated Access token to execute request */
    // 1. Define Http Request
    RequestSpecification requestSpecification = RestAssured.given();

    // 2. Define Authentication
    AuthenticationSpecification authenticationSpecification = requestSpecification.auth();

    // 3. Define OAuthRequestSpecification
    RequestSpecification oAuth2RequestSpecification =
        authenticationSpecification.oauth2(accessToken);

    // 4. POST the request
    Response oAuth2Response =
        oAuth2RequestSpecification.request(
            Method.POST, "http://coop.apps.symfonycasts.com/api/609/chickens-feed");

    // 5. Status Code
    System.out.println(
        "Response Code for the post request using OAuth2:--> " + oAuth2Response.getStatusCode());

    // 6. Response Body
    String responseBody = oAuth2Response.getBody().asString();
    System.out.println(responseBody);

    // 7. Pretty JSON String as response Body
    String jsonResponseBody = oAuth2Response.getBody().jsonPath().prettify();
    System.out.println(jsonResponseBody);
  }
}
