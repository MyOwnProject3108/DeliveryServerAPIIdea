package RestCalls;

import io.restassured.RestAssured;
import io.restassured.config.Config;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;


/**
 * Created by Faiyyaz.Shaik on 10/25/2017.
 */
    public class UserCalls {

        public Response response;
        public static Properties Config = null;


    public UserCalls(String baseURL) throws IOException {

        Config = new Properties();
        final String appConfigPath =System.getProperty("appconfig") != null ?System.getProperty("appconfig"):"//src//test//java//Config//config.properties";
        FileInputStream ip= new FileInputStream(System.getProperty("user.dir")+appConfigPath);
        Config.load(ip);
        RestAssured.baseURI = baseURL;

    }


    public Response getUserDetails(String getUserdetailsendpoint, String xUserId) {

        response = given().log().all().header("X-User-Id", xUserId).contentType(ContentType.JSON).when().get(getUserdetailsendpoint).then()
                .log().all().extract().response();
        String jsonAsString = response.asString();
        System.out.println("printing response jsonAsString..." + jsonAsString);

        return response;
    }

    public Response getUnAuthUserDetails(String getUserdetailsendpoint) {

        response = given().log().all().when().get(getUserdetailsendpoint).then().log().all().extract().response();
        String jsonAsString = response.asString();
        System.out.println("printing response jsonAsString...." + jsonAsString);

        return response;
    }

}


