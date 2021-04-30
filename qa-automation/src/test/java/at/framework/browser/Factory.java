package at.framework.browser;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import at.framework.reports.ExtFactory;
import at.smartshop.keys.Constants;
import at.smartshop.testData.TestDataFilesPaths;

public class Factory {

	private Factory instance = null;
	public static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

	public Factory getInstance() {
		try {
			if (instance == null) {
				instance = new Factory();
			}
		} catch (Exception exc) {
			ExtFactory.getInstance().getExtent().log(Status.FAIL, exc);
			Assert.fail(exc.toString());
		}
		return instance;
	}

	public void setDriver(String browser) {
		try {
			DesiredCapabilities capabilities = null;
			if (browser.equals(Constants.CHROME)) {
				capabilities = DesiredCapabilities.chrome();
				ChromeOptions chromeOptions = new ChromeOptions();
				Map<String, Object> chromePrefs = new HashMap<>();
				chromePrefs.put("credientials enable service", false);
				chromeOptions.setExperimentalOption("prefs", chromePrefs);
				chromeOptions.addArguments("--disable-plugins", "--disable-extensions", "--disable.popup.blocking");
				capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				capabilities.setCapability("applicationCacheEnabled", false);
				System.setProperty("webdriver.chrome.driver", TestDataFilesPaths.DRIVER_CHROME);
				webDriver.set(new ChromeDriver());
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public static WebDriver getDriver() {
		return webDriver.get();

	}

	
}
