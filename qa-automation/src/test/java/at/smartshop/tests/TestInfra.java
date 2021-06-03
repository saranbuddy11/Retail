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
	
	@AfterSuite
	public void afterSuit() {
		try {			
			ResultSets.connection.close();
		} catch (SQLException exc) {
			Assert.fail(exc.toString());
		}
	}
}
