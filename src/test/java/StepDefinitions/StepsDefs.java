package StepDefinitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import ResponsePayload.Markets;
import RestCalls.MarketCalls;
import RestCalls.UserCalls;
import io.restassured.response.Response;
import org.junit.Assert;

import RestCalls.TabRequestCalls;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import TestUtils.TabUtils;
import TestUtils.UpdateTabUtils;

public class StepsDefs {

    public static Properties config = null;
    public Response response;
    public String ttm_xUserId;
    TabRequestCalls sendCall;
    String baseURl;
    public String id;
    TabUtils testUtils = new TabUtils();
    UpdateTabUtils updateTabUtils = new UpdateTabUtils();
    MarketCalls marketCalls;
    Markets[] result = null;
    Markets marketsResponsePayload;
    UserCalls userCalls;


    public StepsDefs() throws IOException {
        config = new Properties();
        final String appConfigPath = System.getProperty("appconfig") != null ? System.getProperty("appconfig") : "\\src\\test\\java\\Config\\config.properties";
        FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + appConfigPath);
        config.load(ip);
        baseURl = config.getProperty("baseURL");
    }




    @When("^I create a tab for the user \"(.*?)\" with the following payload$")
    public void i_create_a_tab_for_the_user_with_the_following_payload(String xUserId, DataTable arg1) throws Throwable {
        List<List<String>> data = arg1.raw();

        testUtils.setName(data.get(1).get(0));
        testUtils.setPublic(Boolean.parseBoolean(data.get(1).get(1)));
        testUtils.setDefault(Boolean.parseBoolean(data.get(1).get(2)));
        testUtils.setTabType(data.get(1).get(3));

        sendCall = new TabRequestCalls(baseURl);
        String endpoint = config.getProperty("posttab");
        System.out.println("printing end point..." + endpoint);

        if (xUserId.equalsIgnoreCase("Traffic Manager")) {
            xUserId = config.getProperty("ttm_xUserId");
        } else if (xUserId.equalsIgnoreCase("Broadcast Manager")) {

            xUserId = config.getProperty("btm_hub_xUserId");

        } else if (xUserId.equalsIgnoreCase("UnAuth User")) {

            xUserId = config.getProperty("unauth_userId");

        } else if (xUserId.equalsIgnoreCase("BTMUnAuth User")) {
            xUserId = config.getProperty("btm_UnAuthId");
        }


        if (xUserId.equalsIgnoreCase("NullAuthUser") || xUserId.equalsIgnoreCase("BTMNullAuthUser")) {
            xUserId = config.getProperty("nullauth_userId");

            response = sendCall.postCallWithUnAuthUserId(testUtils, endpoint);
            System.out.println("Printing response........." + response.asString());
        } else {
            response = sendCall.postCallWithUserId(testUtils, endpoint, xUserId);
            System.out.println("Printing response........." + response.asString());

        }

    }


    @Given("^I update tab for the user \"(.*?)\" with the following payload$")
    public void i_update_tab_for_the_user_with_the_following_payload(String xUserId, DataTable arg2) throws Throwable {

        List<List<String>> data = arg2.raw();
        String tabId = response.path("_id");
        System.out.println("printing response update method...." + tabId);
        updateTabUtils.set_id(tabId);

        String createByUserId = response.path("createByUserId");
        updateTabUtils.setCreateByUserId(createByUserId);

        updateTabUtils.setName(data.get(1).get(0));
        updateTabUtils.setPublic(Boolean.parseBoolean(data.get(1).get(1)));
        updateTabUtils.setDefault(Boolean.parseBoolean(data.get(1).get(2)));
        updateTabUtils.setTabType(data.get(1).get(3));

        sendCall = new TabRequestCalls(baseURl);
        String endpoint = config.getProperty("updatetabendpoint");

        if (xUserId.equalsIgnoreCase("Broadcast Manager")) {
            xUserId = config.getProperty("btm_hub_xUserId");
        } else if (xUserId.equalsIgnoreCase("Traffic Manager")) {
            xUserId = config.getProperty("ttm_xUserId");
        } else if (xUserId.equalsIgnoreCase("UnAuth User")) {
            xUserId = config.getProperty("unauth_userId");
        } else if (xUserId.equalsIgnoreCase("BTMUnAuth User")) {
            xUserId = config.getProperty("btm_UnAuthId");
        }

        if (xUserId.equalsIgnoreCase("NullAuthUser") || (xUserId.equalsIgnoreCase("BTMNullAuthUser"))) {
            xUserId = config.getProperty("nullauth_userId");


            response = sendCall.putCallWithInValidUserId(updateTabUtils, endpoint);
            System.out.println("printing response...." + response.asString());
        } else {

            response = sendCall.putCallWithValidUserId(updateTabUtils, endpoint, xUserId);
            System.out.println("printing response........." + response.asString());

        }
    }


    @Given("^I update order of tab for the user \"(.*?)\"$")
    public void i_update_order_of_tab_for_the_user(String xUserId) throws Throwable {
        String tabIds = response.path("_id");

        if (xUserId.equalsIgnoreCase("NullAuthUser") || (xUserId.equalsIgnoreCase("BTMNullAuthUser"))) {
            response = sendCall.updateTabOrder(tabIds, config.getProperty("updatetaborderendpoint"), config.getProperty("nullauth_userId"));
            System.out.println("printing json response..." + response.asString());
        }
    }


    @Then("^I should see response code with \"(.*?)\"$")
    public void i_should_see_response_code_with(String statusCode) throws Throwable {
        Assert.assertEquals(statusCode, Integer.toString(response.getStatusCode()));
    }


    @Then("^I should see valid response code \"(.*?)\" with \"(.*?)\"$")
    public void i_should_see_valid_response_code_with(String responseCode, String responseMessage) throws Throwable {
        if (responseCode.equalsIgnoreCase("200")) {


            Assert.assertEquals(responseCode, Integer.toString(response.getStatusCode()));
            Assert.assertNotNull(response.path("_id"));

        }
    }


    @Then("^I should see below response code \"(.*?)\" with \"(.*?)\"$")
    public void i_should_see_below_response_code_with(String responseCode, String responseMessage) throws Throwable {
        if (responseCode.equalsIgnoreCase("403")) {

            Assert.assertEquals(responseCode, Integer.toString(response.getStatusCode()));
            Assert.assertEquals(responseMessage, response.asString());


        } else if (responseCode.equalsIgnoreCase("400")) {

            Assert.assertEquals(responseCode, Integer.toString(response.getStatusCode()));
            Assert.assertEquals(responseMessage, response.asString());

        }

    }


    @Then("^I should see following details in Retrieve tabs response payload$")
    public void i_should_see_following_details_in_Retrieve_response_payload(DataTable arg1) throws Throwable {

        List<List<String>> data = arg1.raw();
        Assert.assertEquals(data.get(1).get(0), response.path("_id"));
        Assert.assertEquals(data.get(1).get(0), response.path("businessUnitId"));
        Assert.assertEquals(data.get(1).get(0), response.path("name"));
    }


    @When("^I retrieve tabs for the user \"(.*?)\"$")
    public void i_retrieve_tabs_for_the_user(String xUserId) throws Throwable {

        sendCall = new TabRequestCalls(baseURl);

        if (xUserId.equalsIgnoreCase("Traffic Manager")) {
            id = config.getProperty("ttm_xUserId");

        } else if (xUserId.equalsIgnoreCase("Broadcast Manager")) {
            id = config.getProperty("btm_hub_xUserId");

        } else if (xUserId.equalsIgnoreCase("UnAuth User")) {
            id = config.getProperty("unauth_userId");
        } else if (xUserId.equalsIgnoreCase("BTMUnAuth User")) {
            id = config.getProperty("btm_UnAuthId");
        }

        if (xUserId.equalsIgnoreCase("BTMNullAuthUser") || xUserId.equalsIgnoreCase("NullAuthUser")) {
            response = sendCall.getTabUnAuth(config.getProperty("gettabendpoint"));
        } else {
            response = sendCall.getCall(config.getProperty("gettabendpoint"), id);
        }
        System.out.println(response.asString());
    }

    @Given("^I delete tab for the user \"(.*?)\"$")
    public void i_delete_tab_for_the_user(String xUserId) throws Throwable {
        String tabId = response.path("_id");
        sendCall = new TabRequestCalls(baseURl);

        if (xUserId.equalsIgnoreCase("Traffic Manager")) {
            response = sendCall.deleteTab(config.getProperty("deleteendpoint") + tabId, config.getProperty("ttm_xUserId"));
        } else if (xUserId.equalsIgnoreCase("Broadcast Manager")) {
            response = sendCall.deleteTab(config.getProperty("deleteendpoint") + tabId, config.getProperty("btm_hub_xUserId"));
        } else if (xUserId.equalsIgnoreCase("UnAuth User")) {
            response = sendCall.deleteTab(config.getProperty("deleteendpoint") + tabId, config.getProperty("unauth_userId"));
        } else if (xUserId.equalsIgnoreCase("BTMUnAuth User")) {
            response = sendCall.deleteTab(config.getProperty("deleteendpoint") + tabId, config.getProperty("btm_UnAuthId"));
        }

        if (xUserId.equalsIgnoreCase("NullAuthUser") || xUserId.equalsIgnoreCase("BTMNullAuthUser")) {
            response = sendCall.deleteUnAuthTab(config.getProperty("gettabendpoint"));
        }
        System.out.println(response.asString());

    }


    @Then("^I should see response code \"(.*?)\" with status \"(.*?)\"$")
    public void i_should_see_response_code_with_status(String responseCode, String responseStatus) throws Throwable {
        if (responseCode.equalsIgnoreCase("200")) {
            Assert.assertEquals(responseCode, Integer.toString(response.getStatusCode()));
            Assert.assertEquals(responseStatus, response.asString());
        }
    }

    @When("^I download CSV report for \"(.*?)\"$")
    public void i_download_CSV_report_for(String xUserId) throws Throwable {

        String tabId = response.path("_id");
        System.out.println("Printing response in csv report........." + tabId);
        sendCall = new TabRequestCalls(baseURl);


        if (xUserId.equalsIgnoreCase("Traffic Manager")) {

            xUserId = config.getProperty("ttm_xUserId");
        } else if (xUserId.equalsIgnoreCase("Broadcast Manager")) {

            xUserId = config.getProperty("btm_hub_xUserId");

        } else if (xUserId.equalsIgnoreCase("UnAuth User")) {

            xUserId = config.getProperty("unauth_userId");

        } else if (xUserId.equalsIgnoreCase("BTMUnAuth User")) {

            xUserId = config.getProperty("btm_UnAuthId");
        }

        if (xUserId.equalsIgnoreCase("NullAuthUser") || (xUserId.equalsIgnoreCase("BTMNullAuthUser"))) {

            response = sendCall.getCSVUnAuthUser("/api/traffic/v1/export/tab/" + tabId + "/csv");

        } else {
            response = sendCall.getCSV("/api/traffic/v1/export/tab/" + tabId + "/csv", xUserId);

        }

    }

    @When("^I post Export CVS call with the following payload as \"(.*?)\"$")
    public void i_post_Export_CVS_call_with_the_following_payload_as(String xUserId, DataTable arg2) throws Throwable {
        String tabId = response.path("_id");
        System.out.println("printing tabId in post csv method......" + tabId);

        Map<String, Integer> payload1 = new HashMap<String, Integer>();
        List<List<String>> data = arg2.raw();

        payload1.put("pageNum", Integer.parseInt(data.get(1).get(0)));
        payload1.put("pageSize", Integer.parseInt(data.get(1).get(1)));

        sendCall = new TabRequestCalls(baseURl);

        if (xUserId.equalsIgnoreCase("Traffic Manager")) {
            xUserId = config.getProperty("ttm_xUserId");
        } else if (xUserId.equalsIgnoreCase("Broadcast Manager")) {
            xUserId = config.getProperty("btm_hub_xUserId");
        } else if (xUserId.equalsIgnoreCase("UnAuth User")) {
            xUserId = config.getProperty("unauth_userId");
        } else if (xUserId.equalsIgnoreCase("BTMUnAuth User")) {
            xUserId = config.getProperty("btm_UnAuthId");
        }

        if (xUserId.equalsIgnoreCase("NullAuthUser") || (xUserId.equalsIgnoreCase("BTMNullAuthUser"))) {

            response = sendCall.postCSVExportUnAuthUser(payload1, "/api/traffic/v1/export/tab/" + tabId + "/csv");

        } else {
            response = sendCall.postCSVExport(payload1, "/api/traffic/v1/export/tab/" + tabId + "/csv", xUserId);
        }
    }


    @Given("^I logged in as \"(.*?)\"$")
    public void i_logged_in_as(String xUserId) throws Throwable {
        if (xUserId.equalsIgnoreCase("Broadcast Manager")) {
            id = config.getProperty("btm_hub_xUserId");

        }

        else if (xUserId.equalsIgnoreCase("Traffic Manager")) {
            id = config.getProperty("ttm_xUserId");

        }

        else if (xUserId.equalsIgnoreCase("UnAuth User")){
            id = config.getProperty("unauth_userId");
        }

         if (xUserId.equalsIgnoreCase("BTMUnAuth User")) {
            id = config.getProperty("btm_UnAuthId");
        }

        //delete this and run the test
        else if (xUserId.equalsIgnoreCase("BTMNullAuthUser")) {
            id = config.getProperty("btm_NullAuth_userId");
        }

    }

    @When("^I retrieve Markets for the \"(.*?)\"$")
    public void i_retrieve_Markets_for_the(String xUserId) throws Throwable {

        marketCalls = new MarketCalls(baseURl);

        if (xUserId.equalsIgnoreCase("Broadcast Manager") || (xUserId.equalsIgnoreCase("BTMUnAuth User"))) {
            response = marketCalls.getMarketDetails(config.getProperty("getMarketendpoint"), id);
            result = response.as(Markets[].class);

        }


         else if(xUserId.equalsIgnoreCase("BTMNullAuthUser"))

    {
        response = marketCalls.getNullAuthMarketDetails(config.getProperty("getMarketendpoint"));
        System.out.println("storing response as string..." + response.asString());

    }

}



    @Then("^I should see the following details in ExportController CSV response payload:$")
    public void i_should_see_the_following_details_in_ExportController_CSV_response_payload(DataTable arg1) throws Throwable {

        Map<String, String> payload = new HashMap<String, String>();
        List<List<String>> data = arg1.raw();

        payload.put("sid", data.get(1).get(0));
        System.out.println("printing expected sid..."+ payload.get("sid"));
       payload.put("name", data.get(1).get(1));
        System.out.println("printing expected name..."+ payload.get("name"));

        String actual_sId = result[0].getSid();
        System.out.println("printing actual sid..."+ actual_sId);
        String actual_name = result[0].getName();
        System.out.println("printing actual sid..."+ actual_name);

        Assert.assertEquals(actual_sId, payload.get("sid"));
        Assert.assertEquals(actual_name, payload.get("name"));


    }

    @When("^I retrieve User details for the \"(.*?)\"$")
    public void i_retrieve_User_details_for_the(String xUserId) throws Throwable {

        userCalls = new UserCalls(baseURl);
        if(xUserId.equalsIgnoreCase("Broadcast Manager")){
         response =   userCalls.getUserDetails(config.getProperty("getUserdetailsendpoint")+id, xUserId);

        }

         else if(xUserId.equalsIgnoreCase("Traffic Manager")){
            response =   userCalls.getUserDetails(config.getProperty("getUserdetailsendpoint")+id, xUserId);

        }

        else if (xUserId.equalsIgnoreCase("UnAuth User")) {

          response = userCalls.getUserDetails(config.getProperty("getUserdetailsendpoint")+id, xUserId);


    }





    }

}





