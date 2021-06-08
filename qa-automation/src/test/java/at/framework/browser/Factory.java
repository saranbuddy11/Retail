package at.framework.browser;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import com.aventstack.extentreports.Status;

import at.framework.files.PropertyFile;
import at.framework.reportsetup.ExtFactory;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class Factory {

	//private Factory instance = null;

	public static final ThreadLocal<RemoteWebDriver> webDriver = new ThreadLocal<>();
	public PropertyFile propertyFile = new PropertyFile();
//	public Factory getInstance() {
//		try {
//			if (instance == null) {
//				instance = new Factory();
//			}
//		} catch (Exception exc) {
//			ExtFactory.getInstance().getExtent().log(Status.FAIL, exc);
//			Assert.fail(exc.toString());
//		}
//		return instance;
//	}

	public void setDriver(String driver, String browser) {
		try {
			DesiredCapabilities capabilities = null;

			if (driver.equals(Constants.LOCAL)) {
				if (browser.equals(Constants.CHROME)) {
					capabilities = DesiredCapabilities.chrome();
					ChromeOptions chromeOptions = new ChromeOptions();
					Map<String, Object> chromePrefs = new HashMap<>();
					chromePrefs.put("credientials enable service", false);
					chromeOptions.setExperimentalOption("prefs", chromePrefs);
					chromeOptions.addArguments("Zoom 75%", "--disable-plugins", "--disable-extensions",
							"--disable.popup.blocking");
					capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
					capabilities.setCapability("applicationCacheEnabled", false);
					System.setProperty("webdriver.chrome.driver", FilePath.DRIVER_CHROME);
					webDriver.set(new ChromeDriver());
				} else if (browser.equals(Constants.FIREFOX)) {

				} else if (browser.equals(Constants.INTERNET_EXPOLRER)) {

				}
			} else if (driver.equals(Constants.REMOTE)) {

				if (browser.equals(Constants.CHROME)) {
					capabilities = DesiredCapabilities.chrome();
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.setExperimentalOption("useAutomationExtension", false);
					capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

					capabilities.setBrowserName("chrome");
					capabilities.setPlatform(Platform.ANY);
					System.setProperty("webdriver.chrome.driver", FilePath.DRIVER_CHROME);
					chromeOptions.setBinary("/usr/bin/chromium-browser");
					webDriver.set(new RemoteWebDriver(new URL(propertyFile.readPropertyFile(Configuration.HUB_URL, FilePath.PROPERTY_CONFIG_FILE)), capabilities));

				} else if (browser.equals(Constants.FIREFOX)) {

				} else if (browser.equals(Constants.INTERNET_EXPOLRER)) {

				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public static WebDriver getDriver() {
		return webDriver.get();

	}
	

}
