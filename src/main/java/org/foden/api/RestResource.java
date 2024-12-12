package org.foden.api;

import io.restassured.response.Response;
import org.foden.pojo.PlayList;

import static io.restassured.RestAssured.given;
import static org.foden.api.SpecBuilder.getRequestSpec;
import static org.foden.api.SpecBuilder.getResponseSpec;

public class RestResource {

    public static Response post(String path ,Object request){
        return given(getRequestSpec())
                .body(request)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response post(String token, String path ,Object request){
        return given(getRequestSpec())
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response get(String path){
        return given(getRequestSpec())
                .when()
                .get(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response update(String path, Object request){
        return given(getRequestSpec())
                .when()
                .body(request)
                .put(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }
}
