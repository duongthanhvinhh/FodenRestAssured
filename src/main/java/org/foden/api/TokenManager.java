package org.foden.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.foden.api.SpecBuilder.getResponseSpec;

public class TokenManager {
    private static String accessToken;
    private static Instant expiryTime;

    public static String getToken(){
        try{
            if (accessToken == null || Instant.now().isAfter(expiryTime)){
                System.out.println("Renewing token...");
                Response response = renewToken();
                accessToken = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiryTime = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                System.out.println("Token is good to use.");
            }


        }catch (Exception e){
            throw new RuntimeException("ABORT!!! Failed to get token.");
        }
        return accessToken;
    }

    private static Response renewToken(){
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("client_id", "b6e51ba10aa646e49d01abe4f20ee8de");
        formParams.put("client_secret", "6bdade38d74a49c0ab20e83b61d3bece");
        formParams.put("grant_type", "refresh_token");
        formParams.put("refresh_token", "AQDTxHVmldSRdR2xybIyQ_E_sad3L01hPoHQ_V2JV3yTlGDLhRAdqYZ8AIl9Wju6-KtZHr6QDf_dRGT_mkQxM35UK12oFjE8hr3l0Xs0Bcwa5ZRX3aQfTpsPN6Ok6amSPww");
        Response response = given()
                .baseUri("https://accounts.spotify.com")
                .contentType(ContentType.URLENC)
                .formParams(formParams)
                .log().all()
                .when()
                .post("/api/token")
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
        if (response.statusCode() != 200){
            throw new RuntimeException("ABORT !!! Failed to renew access token.");
        }
        return response;
    }
}
