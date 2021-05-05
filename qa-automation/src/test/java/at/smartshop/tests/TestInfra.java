package at.smartshop.tests;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import at.framework.browser.Browser;
import at.framework.database.DataBase;
import at.framework.generic.PropertyFile;
import at.smartshop.pages.Login;

public class TestInfra {
	Browser browser = new Browser();
	Login login = new Login();
	PropertyFile propertyFile = new PropertyFile();
	
	@BeforeSuite
	public void beforeSuit() {
		DataBase.getConnection();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		try {
			browser.launch();
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
			DataBase.connection.close();
		} catch (SQLException exc) {
			Assert.fail(exc.toString());
		}
	}
}
