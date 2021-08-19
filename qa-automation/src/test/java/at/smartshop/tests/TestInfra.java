package at.smartshop.tests;

import java.net.InetAddress;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;

import at.framework.browser.Browser;
import at.framework.browser.Factory;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.reportsetup.ExtFactory;
import at.framework.reportsetup.ExtReport;
import at.framework.reportsetup.SendReport;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.Login;

@Listeners(at.framework.reportsetup.Listeners.class)
public class TestInfra {
	public Browser browser = new Browser();
	public Login login = new Login();
	public PropertyFile propertyFile = new PropertyFile();
	public FilePath filePath=new FilePath();
	private SendReport sendReport=new SendReport();
	public static String HOST = "";
	
	public static String updateTestRail="";
	
	@Parameters({"environment","UpdateTestRail"})
	@BeforeSuite
	public void beforeSuit(String environment,String testRail) {
		try {
		ResultSets.getConnection();
		filePath.setEnvironment(environment);
		updateTestRail=testRail;
		HOST=InetAddress.getLocalHost().getHostName();
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Parameters({ "driver", "browser" })
	@BeforeMethod
	public void beforeMethod(String drivers, String browsers) {
		try {
			browser.launch(drivers, browsers);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@AfterMethod
	public void afterMethod() {
		try {
			browser.close();
		} catch (Exception exc) {
			Assert.fail(exc.toString());

		}

	}
	
	@Parameters({"SendEmail"})
	@AfterSuite
	public void afterSuit(String sendEmail) {
		try {					
			if(sendEmail.equals(Constants.YES)) {
				sendReport.triggerMail(ExtReport.reportFullPath);
				}
			ResultSets.connection.close();
		} catch (SQLException exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public static void failWithScreenShot(String exc) {
		try {
		String screenshot = at.framework.reportsetup.Listeners.objReportName.getScreenshot(Factory.getDriver());
		String sysPath=FilePath.FILE+HOST+screenshot.split(Constants.DELIMITER_COLON)[1];
		ExtFactory.getInstance().getExtent().addScreenCaptureFromPath(sysPath);
		ExtFactory.getInstance().getExtent().log(Status.FAIL, "Failed due to "+exc.toString());
		Assert.fail(exc);
		}
		catch (Exception e) {
			Assert.fail("Failed due to "+exc.toString()+" could not capture the screenshot due to "+e);
		}
	}

}
