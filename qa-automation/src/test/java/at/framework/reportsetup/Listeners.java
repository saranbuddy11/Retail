
package at.framework.reportsetup;



import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.testrail.Testrail;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class Listeners implements ITestListener {

	static ExtentReports objReport;
	ExtReport objReportName;
	ExtentTest test;
	Testrail testRail=new Testrail();

	public void onTestStart(ITestResult result) {		
		test = objReport.createTest("["+result.getMethod().getRealClass().getName()+"]["+result.getMethod().getMethodName()+"]"+" - "+result.getMethod().getDescription());
		ExtFactory.getInstance().setExtent(test);
	}

	public void onTestSuccess(ITestResult result) {
		ExtFactory.getInstance().getExtent().log(Status.PASS,
				" method[" + result.getMethod().getMethodName() + "] is passed");
		if(TestInfra.updateTestRail.equals(Constants.YES)) {
		String testCaseId=result.getMethod().getDescription().split("-")[0];		
		testRail.testRailPassResult(testCaseId);
		}
	}

	public void onTestFailure(ITestResult result) {		
		ExtFactory.getInstance().getExtent().log(Status.FAIL, "Test Case" + result.getMethod().getMethodName() + " is failed due to " +result.getThrowable());
		if(TestInfra.updateTestRail.equals(Constants.YES)) {
		String testCaseId=result.getMethod().getDescription().split("-")[0];
		testRail.testRailFailResult(testCaseId ,"Exception is " +result.getThrowable());
		}
		
		WebDriver driver = Factory.getDriver();		
		String testmethodName = result.getMethod().getMethodName();

		try {					
			ExtFactory.getInstance().getExtent().addScreenCaptureFromPath(objReportName.getScreenshot(testmethodName,driver),
					result.getMethod().getMethodName());
			ExtFactory.getInstance().removeExtentObject();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	public void onTestSkipped(ITestResult result) {
		ExtFactory.getInstance().getExtent().log(Status.SKIP,
				"Test Case" + result.getMethod().getMethodName() + "is skipped");
		ExtFactory.getInstance().removeExtentObject();
	}

	public void onStart(ITestContext context) {
		try {
			objReportName = new ExtReport("365 Test Automation", "365 Test Results");
			objReport = objReportName.getReporter();			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void onFinish(ITestContext context) {
		objReport.flush();
	}

}
