package RestCalls;

import ResponsePayload.Markets;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

/**
 * Created by Faiyyaz.Shaik on 10/20/2017.
 */
public class MarketCalls {

    public Response response;
    public static Properties Config = null;

    public MarketCalls(String baseURL) throws IOException {

        Config = new Properties();
        final String appConfigPath =System.getProperty("appconfig") != null ?System.getProperty("appconfig"):"//src//test//java//Config//config.properties";
        FileInputStream ip= new FileInputStream(System.getProperty("user.dir")+appConfigPath);
        Config.load(ip);
        RestAssured.baseURI = baseURL;

    }

    public Response getMarketDetails(String getMarketendpoint, String xUserId) {
        response = given().log().all().header("X-User-Id", xUserId).contentType(ContentType.JSON).when().get(getMarketendpoint).then()
                .log().all().extract().response();

        String jsonAsString = response.asString();
        System.out.println("response as jsonAsString...."+ jsonAsString);

         return response;
    }



    public Response getNullAuthMarketDetails(String getMarketendpoint){
        response = given().log().all().contentType(ContentType.JSON).when().get(getMarketendpoint).then().log().all().extract().response();
        System.out.println("response as jsonAsString...."+ response.asString());

        return response;
    }
}
