package RestCalls;

import TestUtils.TabUtils;
import TestUtils.UpdateTabUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

/**
 * Created by Faiyyaz.Shaik on 10/16/2017.
 */
public class TabRequestCalls {

    public Response response;
    public static Properties Config = null;

    public TabRequestCalls(String baseURL) throws IOException
    {
        Config = new Properties();
        final String appConfigPath =System.getProperty("appconfig") != null ?System.getProperty("appconfig"):"//src//test//java//Config//config.properties";
        FileInputStream ip= new FileInputStream(System.getProperty("user.dir")+appConfigPath);
        Config.load(ip);
        RestAssured.baseURI = baseURL;
        RestAssured.urlEncodingEnabled=false;

    }


    public Response postCallWithUserId(TabUtils testUtils, String endpoint, String xUserId){
        response = given().log().all().header("X-User-Id", xUserId).contentType(ContentType.JSON).body(testUtils).when().post(endpoint).then().extract().response();
        String jsonAsString = response.asString();
        System.out.println("printing json as String......."+ jsonAsString);
        return response;


    }

    public Response getCall(String endpoint, String xUserId){
        System.out.println("xuserid...." + xUserId);
        System.out.println("RestAssured.baseURI+endpoint: "+RestAssured.baseURI+endpoint);
        response = given()
                .log()
                .all()
                .header("X-User-Id", xUserId)
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint)
                .then()
                .log()
                .all()
                .extract()
                .response();
        String jsonAsString = response.asString();
        System.out.println("Printing jsonAsString........"+ jsonAsString);
        return response;

    }

    public Response getTabUnAuth(String endpoint) {
        System.out.println("Inside get method....");
        response = given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();

        String jsonAsString = response.asString();
        System.out.println("Printing jsonAsString........"+ jsonAsString);
        return response;

    }



    public Response deleteTab(String endpoint, String xUserId){
        response = given().log().all().header("X-User-Id", xUserId).contentType(ContentType.JSON).when().delete(endpoint).then().log().all().extract().response();
        String jsonResponse = response.asString();
        System.out.println("printing json response..." + jsonResponse);
        return response;
    }

    public Response postCallWithUnAuthUserId(TabUtils testUtils, String endpoint) {

        response = given().log().all().contentType(ContentType.JSON).body(testUtils).when().post(endpoint).then().extract().response();
        String jsonAsString = response.asString();
        System.out.println("Printing json response....." + jsonAsString);

        return response;
    }

    public Response deleteUnAuthTab(String endpoint) {
        response = given().log().all().contentType(ContentType.JSON).when().delete(endpoint).then().log().all().extract().response();
        String jsonAsString = response.asString();
        System.out.println("Printing jsonAsString......" + jsonAsString);
        return response;

    }

    public Response putCallWithValidUserId(UpdateTabUtils updateTabUtils, String endpoint, String xUserId) {
        response = given().log().all().header("X-User-Id", xUserId).contentType(ContentType.JSON).body(updateTabUtils).when().put(endpoint).then().log().all().extract().response();
        String jsonAsString = response.asString();
        System.out.println("printing jsonAsString...." + jsonAsString);
        return response;
    }

    public Response putCallWithInValidUserId(UpdateTabUtils updateTabUtils, String endpoint) {
        response = given().log().all().contentType(ContentType.JSON).body(updateTabUtils).when().put(endpoint).then().log().all().extract().response();
        String jsonAsString = response.asString();
        System.out.println("printing jsonAsString....." + jsonAsString);
        return response;
    }

    public Response updateTabOrder(String tabIds, String endpoint, String xuserId) {
        response = given().log().all().contentType(ContentType.JSON).header("X-User-Id", xuserId).body(tabIds).when().put(endpoint).then().log().all().extract().response();
        String jsonAsString = response.asString();
        System.out.println("printing jsonAsString" + jsonAsString);

        return response;
    }


    public Response getCSV(String endpoint, String xUserId) {

       response =  given().log().all().header("X-User-Id", xUserId).contentType(ContentType.JSON).when().get(endpoint)
                .then().log().all().extract().response();
        String jsonAsString = response.asString();
        System.out.println("Printing jsonAsString........"+ jsonAsString);
        return response;
    }

    public Response getCSVUnAuthUser(String endpoint) {
        response = given().log().all().contentType(ContentType.JSON).when().get(endpoint).then().log().all().extract().response();

        String jsonAsString = response.asString();
        System.out.println("Printing jsonAsString........"+ jsonAsString);
        return response;




    }

    public Response postCSVExport(Map<String, Integer> payload1, String endpoint, String xUserId) {
       response =  given().log().all().header("X-User-Id", xUserId).contentType(ContentType.JSON).body(payload1).when().post(endpoint).then().log().all()
                .extract().response();
        String jsonAsString = response.asString();
        System.out.println("printing jsonAsString...." + jsonAsString);

        return response;
    }

    public Response postCSVExportUnAuthUser(Map<String, Integer> payload1,String endpoint){
        response = given().log().all().contentType(ContentType.JSON).body(payload1).when().post(endpoint).then().log().all().extract().response();
        return response;
    }
}


