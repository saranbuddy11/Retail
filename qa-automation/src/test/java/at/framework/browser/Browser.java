package at.framework.browser;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.files.PropertyFile;
import at.framework.reportsetup.ExtFactory;

public class Browser extends Factory {
	PropertyFile propertyFile = new PropertyFile();

	public void launch(String driver, String browser) {
		try {
			setDriver(driver, browser);
			if (ExtFactory.getInstance().getExtent() != null)
				ExtFactory.getInstance().getExtent().log(Status.INFO, "[" + browser + " ]launched the browser");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void close() {
		try {
			WebDriver browser = getDriver();
			browser.quit();
			if (ExtFactory.getInstance().getExtent() != null)
				ExtFactory.getInstance().getExtent().log(Status.INFO, "closed the browser");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void navigateURL(String url) {
		try {
			getDriver().get(url);
			getDriver().manage().window().maximize();
			getDriver().manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			if (ExtFactory.getInstance().getExtent() != null)
				ExtFactory.getInstance().getExtent().log(Status.INFO, "navigated to url [" + url + " ]");
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}