package org.foden.tests;

import io.restassured.config.LogConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basic {


    @Test
    public void validate_get_all_workspaces_successfully() {
        given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .log().all()
//                .log().headers()
//                .log().cookies()
//                .log().parameters()
//                .log().params()
                .when()
                .get("/workspaces")
                .then()
                .log().all()
//                .log().body()
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "Foden Automation"),
                        "workspaces.visibility", hasItems("personal", "personal"),
                        "workspaces.id", hasItem("8f69ea94-97a7-463d-afe2-b4f055b9b702"),
                        "workspaces[1].name", equalTo("Foden Automation"),
                        "workspaces.size()", equalTo(2),
                        "workspaces[1]", hasEntry("name", "Foden Automation"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[1].name", allOf(startsWith("Fod") ,containsString("o")));
    }

    @Test
    public void extract_response() {
        Response response = given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response();
        System.out.println("Response = " + response.asString());
    }
    @Test
    public void extract_single_value_from_response() {
        Response response = given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response();
        System.out.println("The last workspace is " + response.path("workspaces[1].name"));
    }
    @Test
    public void extract_single_value_from_response_using_jsonpath_firstway() {
        Response response = given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response();
        JsonPath jsonPath = new JsonPath(response.asString());
        System.out.println("The last workspace is " + jsonPath.getString("workspaces[1].name"));
    }

    @Test
    public void extract_single_value_from_response_using_jsonpath_secondway() {
        String response = given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response().asString();
        String workspaceName = JsonPath.from(response).getString("workspaces[1].name");
        System.out.println("Workspace name is " + workspaceName);
    }

    @Test
    public void extract_single_value_from_response_using_jsonpath_thirdway() {
        String workspaceName = given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .path("workspaces[1].name");
        System.out.println("Workspace name is " + workspaceName);
    }

    @Test
    public void log_only_if_error() {
        given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .log().ifError() //mean only logs response when api return error code like 400/401/403/422/500... not meaning a failed in assertion
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "Foden Automation"),
                        "workspaces.visibility", hasItems("personal", "personal"),
                        "workspaces.id", hasItem("8f69ea94-97a7-463d-afe2-b4f055b9b702"),
                        "workspaces[1].name", equalTo("Foden Automation"),
                        "workspaces.size()", equalTo(2),
                        "workspaces[1]", hasEntry("name", "Foden Automation"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[1].name", allOf(startsWith("Fod") ,containsString("o")));
    }

    @Test
    public void log_only_if_validations_failed() {
        given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .log().ifValidationFails()
                .when()
                .get("/workspaces")
                .then()
                .log().ifValidationFails()
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "Foden Automation"),
                        "workspaces.visibility", hasItems("personal", "personal"),
                        "workspaces.id", hasItem("8f69ea94-97a7-463d-afe2-b4f055b9b702"),
                        "workspaces[1].name", equalTo("Foden Automation"),
                        "workspaces.size()", equalTo(2),
                        "workspaces[1]", hasEntry("name", "Foden Automation"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[1].name", allOf(startsWith("Fod") ,containsString("o")));
    }

    @Test
    public void log_only_if_validations_failed_using_config() {
        given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "Foden Automation"),
                        "workspaces.visibility", hasItems("personal", "personal"),
                        "workspaces.id", hasItem("8f69ea94-97a7-463d-afe2-b4f055b9b702"),
                        "workspaces[1].name", equalTo("Foden Automation"),
                        "workspaces.size()", equalTo(2),
                        "workspaces[1]", hasEntry("name", "Foden Automation"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[1].name", allOf(startsWith("Fod") ,containsString("o")));
    }

    @Test
    public void not_include_blacklist_header_in_logs() { //Do not print out the api key or secret key to the logs
        given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .config(config.logConfig(LogConfig.logConfig().blacklistHeader("x-api-key")))
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "Foden Automation"),
                        "workspaces.visibility", hasItems("personal", "personal"),
                        "workspaces.id", hasItem("8f69ea94-97a7-463d-afe2-b4f055b9b702"),
                        "workspaces[1].name", equalTo("Foden Automation"),
                        "workspaces.size()", equalTo(2),
                        "workspaces[1]", hasEntry("name", "Foden Automation"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[1].name", allOf(startsWith("Fod") ,containsString("o")));
    }

    @Test
    public void not_include_blacklist_headers_in_logs() {
        Set<String> headers = new HashSet<>();
        headers.add("x-api-key");
        headers.add("secret-id");
        given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers)))
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "Foden Automation"),
                        "workspaces.visibility", hasItems("personal", "personal"),
                        "workspaces.id", hasItem("8f69ea94-97a7-463d-afe2-b4f055b9b702"),
                        "workspaces[1].name", equalTo("Foden Automation"),
                        "workspaces.size()", equalTo(2),
                        "workspaces[1]", hasEntry("name", "Foden Automation"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[1].name", allOf(startsWith("Fod") ,containsString("o")));
    }

    @Test
    public void multiple_headers_using_Headers() {
        Header apiKey = new Header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal");
        Header accept = new Header("Accept", "*/*");
        Headers headers =  new Headers(apiKey, accept);
        given()
                .baseUri("https://api.getpostman.com")
                .headers(headers)
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void multiple_headers_using_Map() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal");
        headers.put("Accept", "*/*");
        given()
                .baseUri("https://api.getpostman.com")
                .headers(headers)
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void multiple_header_values() {
        given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .header("X-End-User", "sdagsdga", "sgdfgasdgds", "dfgfsdfgsdg")
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void verify_response_headers() {
        given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .header("X-End-User", "sdagsdga", "sgdfgasdgds", "dfgfsdfgsdg")
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .headers("Connection", "keep-alive", "Server", "nginx");
    }

    @Test
    public void extract_response_headers_firstway() {
        Headers extractedHeaders = given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .header("X-End-User", "sdagsdga", "sgdfgasdgds", "dfgfsdfgsdg")
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .headers();
        System.out.println("Value of header x-frame-options = " + extractedHeaders.getValue("x-frame-options"));
    }

    @Test
    public void extract_response_headers_secondway() {
        Headers extractedHeaders = given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .header("X-End-User", "sdagsdga", "sgdfgasdgds", "dfgfsdfgsdg")
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .headers();
        for (Header header : extractedHeaders){
            System.out.println("Value of " + header.getName() + " is " + header.getValue());
        }
    }

    @Test
    public void extract_multiple_values_of_a_header() {
        Headers extractedHeaders = given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key", "PMAK-675527ed5663a20001f0c97e-somethingnotreal")
                .header("X-End-User", "sdagsdga", "sgdfgasdgds", "dfgfsdfgsdg")
                .log().all()
                .when()
                .get("/workspaces")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .headers();
        List<String> xEndUserHeaderValues = extractedHeaders.getValues("X-End-User");
        for (String xEndUserHeaderValue : xEndUserHeaderValues){
            System.out.println(xEndUserHeaderValue);
        }
    }
}
