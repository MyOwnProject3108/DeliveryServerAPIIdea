package TestRunner;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions (features = "src/test/resources",glue = "StepDefinitions",
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/extentreports/report.html"},
        tags= {"@InvalidUserRetrieveCSV" })

public class Runner {

    @AfterClass
    public static void setup() throws IOException {
        Reporter.loadXMLConfig(new File("src//test//resources//extent-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));

        Reporter.setTestRunnerOutput("Sample test runner output message <br />");
        Reporter.addStepLog("Step Log message goes here");
        Reporter.addScenarioLog("Add scenario log");

    }


}

