package at.smartshop.tests;

import at.framework.browser.Browser;
import at.framework.generic.PropertyFile;
import at.smartshop.pages.Login;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestInfra {
	Browser browser = new Browser();
	Login login = new Login();
	PropertyFile propertyFile = new PropertyFile();
	
	@BeforeTest
	public void beforeTest() {
		try {
			browser.launch();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@AfterTest
	public void AfterTest() {
		browser.close();
	}
}
