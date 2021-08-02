
package at.framework.reportsetup;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
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
	private Testrail testRail=new Testrail();
	
	public static int passedCount;
	public static int failedCount;
	public static int skippedCount;
	public static Map<String, Integer> resultSet1 = new HashMap<>();
	public static Map<String, Integer> resultSet2 = new HashMap<>();
	public static Map<String, Integer> resultSet3 = new HashMap<>();
	public static Map<String, Integer> resultSet4= new HashMap<>();
	public static Map<String, Integer> resultSet5 = new HashMap<>();
	public static Map<String, Integer> resultSet6 = new HashMap<>();
	public static Map<String, Integer> resultSet7 = new HashMap<>();
	public static Map<String, Integer> resultSet8 = new HashMap<>();
	public static Map<String, Integer> resultSet9 = new HashMap<>();
	public static Map<String, Integer> resultSet10 = new HashMap<>();
	public static Map<String, Integer> resultSet11 = new HashMap<>();
	public static Map<String, Integer> resultSet12 = new HashMap<>();
	public static List<String> classNames=new ArrayList<String>();
	public static List<Map<String, Integer>> listResultSet= new ArrayList<Map<String, Integer>>();
	public static List<Map<String, Integer>> listResultSetFinal=new ArrayList<Map<String, Integer>>();

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
		passedCount++;
		int index=classNames.indexOf(result.getMethod().getRealClass().getSimpleName());
		updateCount(listResultSet.get(index),Constants.PASS, result.getMethod().getRealClass().getSimpleName());
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
		failedCount++;
		int index=classNames.indexOf(result.getMethod().getRealClass().getSimpleName());
		updateCount(listResultSet.get(index),Constants.FAIL, result.getMethod().getRealClass().getSimpleName());
	}

	public void onTestSkipped(ITestResult result) {
		ExtFactory.getInstance().getExtent().log(Status.SKIP,
				"Test Case" + result.getMethod().getMethodName() + "is skipped due to "+ result.getThrowable());
		ExtFactory.getInstance().removeExtentObject();
		skippedCount++;
		int index=classNames.indexOf(result.getMethod().getRealClass().getSimpleName());
		updateCount(listResultSet.get(index),Constants.SKIP, result.getMethod().getRealClass().getSimpleName());
	}

	public void onStart(ITestContext context) {
		try {
			objReportName = new ExtReport("365 Test Automation", "365 Test Results");
			objReport = objReportName.getReporter();		
			listResultSet=Arrays.asList(resultSet1,resultSet2,resultSet3,resultSet4,resultSet5,resultSet6,resultSet7,resultSet8,resultSet9,resultSet10,resultSet11,resultSet12);			
			List<ITestNGMethod> methods = context.getSuite().getAllMethods();
			Set<String> classNamesHash = new HashSet<>();
			for (ITestNGMethod method : methods) {				
				classNamesHash.add(method.getRealClass().getSimpleName());				
			}
			classNames = new ArrayList<String>(classNamesHash);
			for (int i=0;i<classNames.size();i++) {
				initializeMap(listResultSet.get(i));
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void onFinish(ITestContext context) {		
		for (Map<String,Integer> map : listResultSet) {
			if(!map.isEmpty())
			listResultSetFinal.add(map);
		}
		objReport.flush();
	}
	
	public void initializeMap(Map<String, Integer> map) {
		map.put(Constants.PASS, 0);
		map.put(Constants.FAIL, 0);
		map.put(Constants.SKIP, 0);		
	}
	
	public void updateCount(Map<String, Integer> map,String testCaseStatus, String className) {
		map.put(testCaseStatus, map.get(testCaseStatus)+1);
	}
}
