package org.foden.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.config;
import static org.foden.api.Route.*;
import static org.foden.api.TokenManager.getToken;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI_API)
                .setBasePath(BASE_PATH)
                .addHeader("Authorization", "Bearer " + getToken())
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build()
                .config(config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName("Authorization")));
    }

    public static RequestSpecification getAccountRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI_ACCOUNTS)
                .setContentType(ContentType.URLENC)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
