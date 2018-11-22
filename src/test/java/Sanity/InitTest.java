package Sanity;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import config.Constants;
import org.junit.BeforeClass;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;
import static utils.JsonUtil.getJsonDataFromDirectory;
import static utils.StringUtil.isEmptyString;
import static config.Constants.*;

public abstract class InitTest{

    protected static ExtentReports reports;
    protected ExtentTest tests;
    protected static Map<String,Map<Integer,Map<String,String>>> jsonData = new HashMap<String,Map<Integer,Map<String,String>>>();
    protected static String outputDir;
    private static boolean isSetupDone = false;

    @BeforeClass
    public static void setup(){
        System.out.println("Setup Called");
        if(isSetupDone){
            return;
        }
        String userFilePath = System.getProperty("jsonDirectory");
        if(isEmptyString(userFilePath)){
            userFilePath = "src/test/resources";
        }

        jsonData.putAll(getJsonDataFromDirectory(userFilePath));
        isSetupDone = true;

        initOutputDirectory();
        startReporting();
    }

    private static void startReporting(){

    }

    private static void initOutputDirectory(){
        System.out.println("Init Called");
        File file = new File(Constants.OUTPUT_DIR);
        if(!file.exists() || (file.exists() && !file.isDirectory())){
            file.mkdir();
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String timeStamp = dateFormatter.format(now);
        outputDir = Constants.OUTPUT_DIR + File.separatorChar + timeStamp;
        file = new File(outputDir);
        if(file.exists()){
            file.delete();
        }
        file.mkdir();
    }

    protected ExtentTest startTest(String testName){
        return reports.startTest(testName);
    }

    protected void endTest(ExtentTest test){
        reports.endTest(test);
    }
}