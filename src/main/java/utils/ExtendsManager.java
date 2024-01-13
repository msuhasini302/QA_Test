package utils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class ExtendsManager{
	

	    private static ExtentReports extent;
	    private static ExtentTest test;

	    public static ExtentReports getInstance() {
	        if (extent == null) {
	            extent = new ExtentReports();
	            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report.html"); 
	            // Specify the report file path
	            extent.attachReporter(htmlReporter);
	        }
	        return extent;
	    }

	    public static ExtentTest createTest(String testName) {
	        test = extent.createTest(testName);
	        return test;
	    }
	}