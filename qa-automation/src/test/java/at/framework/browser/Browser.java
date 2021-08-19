package at.framework.browser;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import at.framework.files.PropertyFile;
import at.smartshop.tests.TestInfra;

public class Browser extends Factory {
	PropertyFile propertyFile = new PropertyFile();

	public void launch(String driver,String browser) {
	try {		
		setDriver(driver,browser);		
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void close() {
		try {
			WebDriver browser = getDriver();
			browser.quit();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void navigateURL(String url) {
		try {
		getDriver().get(url);
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		} catch (Exception exc) {
		TestInfra.failWithScreenShot(exc.toString());
		}
	}
}