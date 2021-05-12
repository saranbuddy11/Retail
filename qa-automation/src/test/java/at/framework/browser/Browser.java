package at.framework.browser;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import at.framework.files.PropertyFile;
import at.smartshop.keys.KeysConfiguration;
import at.smartshop.testData.TestDataFilesPaths;

public class Browser extends Factory {
	PropertyFile propertyFile = new PropertyFile();

	public void launch() {
	try {
			setDriver(propertyFile.readPropertyFile(KeysConfiguration.BROWSER,TestDataFilesPaths.PROPERTY_CONFIG_FILE));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void close() {
		try {
			WebDriver browser = getDriver();
			browser.close();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void navigateURL(String url) {
		try {
		getDriver().get(url);
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		} catch (Exception exc) {
		Assert.fail(exc.toString());
		}
	}
}