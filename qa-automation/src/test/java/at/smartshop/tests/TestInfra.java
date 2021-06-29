package at.smartshop.tests;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import at.framework.browser.Browser;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.reportsetup.ExtReport;
import at.framework.triggeremail.SendReport;
import at.smartshop.keys.Constants;
import at.smartshop.pages.Login;

@Listeners(at.framework.reportsetup.Listeners.class)
public class TestInfra {
	public Browser browser = new Browser();
	public Login login = new Login();
	public PropertyFile propertyFile = new PropertyFile();
	private SendReport sendReport=new SendReport();
	
	@BeforeSuite
	public void beforeSuit() {
		ResultSets.getConnection();
	}
	
	
	@Parameters({"driver", "browser"})
	@BeforeMethod	
	public void beforeMethod(String drivers, String browsers) {			
		try {			
			browser.launch(drivers,browsers);
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
			ResultSets.connection.close();	
			if(sendEmail.equals(Constants.YES)) {				
				sendReport.triggerMail(ExtReport.reportFullPath,at.framework.reportsetup.Listeners.passedCount,at.framework.reportsetup.Listeners.failedCount,at.framework.reportsetup.Listeners.skippedCount);
				}
		} catch (SQLException exc) {
			Assert.fail(exc.toString());
		}
	}

}
