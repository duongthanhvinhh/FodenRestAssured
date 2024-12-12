package org.foden.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.foden.pojo.PlayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlayListTests {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    String accessToken = "BQDVktWKOPkRxHmCWLJVdzpFm2mv1VpQiRQSFJCoDW8NkYrn5e7fx0aK549E2dIk2pEUU-6tUlaelFdLOHi6VFvY8alG_Oz_Oq36IP0VrlN-nZXlt4YNNLMIwJWt7lOFIuS3JBkirB3fVAh_xUo6InW8f_0iLKkciykU0sAWJdCREexyMvx3_ezwg-kIU4Bf2EO6NYyMg32PKTdHTaA-1gPfU65hScVzbpcGrY4Fg8HQTkdQOcrQrvSwgV_KmQiUnK1kJnumjpg2UU4J5W2T6ndc";

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization", "Bearer " + accessToken)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void createPlayList(){
        PlayList requestPlayList = new PlayList();
        requestPlayList.setName("Foden Like It");
        requestPlayList.setDescription("Top 10 songs that I really like.");
        requestPlayList.setPublic(true);

        PlayList responsePlayList = given(requestSpecification)
                .body(requestPlayList)
                .when()
                .post("/users/31c3hrbpm75afpco7533kex5fhyi/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(201)
                .extract()
                .response()
                .as(PlayList.class);
        assertThat(responsePlayList.getName(), equalTo(requestPlayList.getName()));
        assertThat(responsePlayList.getDescription(), equalTo(requestPlayList.getDescription()));
        assertThat(responsePlayList.getPublic(), equalTo(requestPlayList.getPublic()));

    }

    @Test
    public void getPlayList(){
        PlayList requestPlayList = new PlayList();
        requestPlayList.setName("Foden Like It");
        requestPlayList.setDescription("Top 10 songs that I really like.");
        requestPlayList.setPublic(true);
        PlayList responsePlayList = given(requestSpecification)
                .when()
                .get("/playlists/5ZCEHwn5JnhpXzDAhnYKah")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .as(PlayList.class);
        assertThat(responsePlayList.getName(), equalTo(requestPlayList.getName()));
        assertThat(responsePlayList.getDescription(), equalTo(requestPlayList.getDescription()));
        assertThat(responsePlayList.getPublic(), equalTo(requestPlayList.getPublic()));

    }

    @Test
    public void updatePlayList(){

        PlayList requestPlayList = new PlayList();
        requestPlayList.setName("Top 5 Europe music songs");
        requestPlayList.setDescription("Top 5 music songs last week in Europe.");
        requestPlayList.setPublic(false);
        given(requestSpecification)
                .when()
                .body(requestPlayList)
                .put("/playlists/3pc06TbWbIgUD9tRABoeGR")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void shouldNotBeAbleToCreatePlayListWithoutName(){
//        String payload = "{\n" +
//                "    \"name\": \"\",\n" +
//                "    \"description\": \"Top 10 songs that I really like.\",\n" +
//                "    \"public\": true\n" +
//                "}";
        PlayList requestPlayList = new PlayList();
        requestPlayList.setName("");
        requestPlayList.setDescription("Top 5 music songs last week in Europe.");
        requestPlayList.setPublic(false);
        given(requestSpecification)
                .body(requestPlayList)
                .when()
                .post("/users/31c3hrbpm75afpco7533kex5fhyi/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(400)
                .body("error.status", equalTo(400),
                        "error.message", equalTo("Missing required field: name"));
    }

    @Test
    public void shouldNotBeAbleToCreatePlayListWithExpiredToken(){
//        String payload = "{\n" +
//                "    \"name\": \"New PlayList\",\n" +
//                "    \"description\": \"Top 10 songs that I really like.\",\n" +
//                "    \"public\": true\n" +
//                "}";
        PlayList requestPlayList = new PlayList();
        requestPlayList.setName("Foden PlayList");
        requestPlayList.setDescription("Top 5 music songs last week in Europe.");
        requestPlayList.setPublic(false);
        String expiredToken = "BQDxcdWArcPgYiSjcLud_FIMjc7kK2_3O94q1HdXKEE4aHQpf1nAKSiL0_hOqavh0eAKjhO9-RVGX0hu_CkHzocpaJLL7uxpsXzyDEEUrcdfpfSai9aBaCLSeewIXdRrIbTw_F_wZVb1L7OxUTvqpuqeSZZSVRCQSR3dCU8_aqKunJK1B0wbSwT_4Gu-SY_EoZFGV00-VVlaZsTN_pi6VZaKfKo6h6D80jfwz_WgPV4mYAu_MmKzWlqmowDmeqwPsLPBfT3TOFtMgs7i-R0h7EQU";
        given()
                .baseUri("https://api.spotify.com")
                .basePath("/v1")
                .header("Authorization", "Bearer " + expiredToken)
                .contentType(ContentType.JSON)
                .body(requestPlayList)
                .when()
                .post("/users/31c3hrbpm75afpco7533kex5fhyi/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(401)
                .body("error.status", equalTo(401),
                        "error.message", equalTo("Invalid access token"));
    }
}
