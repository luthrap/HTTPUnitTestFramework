package Sanity;

import categories.SanityTest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.get;

@Category(SanityTest.class)
public class StatusCodeTest extends InitTest{

    private Map<Integer,Map<String,String>> testData;

    @BeforeClass
    public static void setupClass(){
        reports = new ExtentReports(outputDir + File.separatorChar + "StatusCode.html");
    }
    @Before
    public void setupTest()
    {
        testData = jsonData.get("StatusCode");
    }

    @Test
    public void test() throws NumberFormatException
    {
        for(Map.Entry<Integer,Map<String,String>> eachTestData : testData.entrySet())
        {
            String endPoint = eachTestData.getValue().get("endpoint");
            tests = startTest("Validate StatusCode for the endPoint : " + endPoint);
            tests.setDescription("EndPoint : " + endPoint);
            long responseCode = -1;
            try{
                 responseCode = Long.parseLong(eachTestData.getValue().get("expectedStatusCode"));
            }catch(NumberFormatException nan){

            }
            Response response = get(endPoint);
            long actualStatusCode = (long)response.getStatusCode();
            String stepName = "Expected Status Code : " + responseCode + "," + " Actual Status Code : " + actualStatusCode;
            if(responseCode == actualStatusCode){
                tests.log(LogStatus.PASS,"Verify Status Code",stepName);
            }else if(responseCode ==-1){
                tests.log(LogStatus.WARNING,"Verify Status Code","Unknown Expected Status Code Defined, Actual Status Code : " + actualStatusCode);
            }else {
                tests.log(LogStatus.FAIL, "Verify Status Code", stepName);
            }
            endTest(tests);
        }
    }

    @AfterClass
    public static void tearDownClass(){
        reports.flush();
    }
}
