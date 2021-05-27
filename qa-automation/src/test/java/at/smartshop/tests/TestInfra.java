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
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.Login;

@Listeners(at.framework.reportsetup.Listeners.class)
public class TestInfra {
	public Browser browser = new Browser();
	public Login login = new Login();
	public PropertyFile propertyFile = new PropertyFile();
	
	@BeforeSuite
	public void beforeSuit() {
		ResultSets.getConnection();
	}
	
	
	@BeforeMethod	
	public void beforeMethod() {
		try {			
			browser.launch(propertyFile.readPropertyFile(Configuration.DRIVER, FilePath.PROPERTY_CONFIG_FILE),propertyFile.readPropertyFile(Configuration.BROWSER, FilePath.PROPERTY_CONFIG_FILE));
			//browser.launch(Constants.LOCAL,Constants.CHROME);
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
	
	@AfterSuite
	public void afterSuit() {
		try {			
			ResultSets.connection.close();
		} catch (SQLException exc) {
			Assert.fail(exc.toString());
		}
	}
}
