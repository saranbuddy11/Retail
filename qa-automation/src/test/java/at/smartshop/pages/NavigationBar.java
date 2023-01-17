package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.browser.Browser;
import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

public class NavigationBar extends Factory {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private Browser browser = new Browser();
	private Login login = new Login();

	public static final By DPD_ORG = By.className("select2");
	private static final By TXT_ORG = By.xpath("//span/input[@class='select2-search__field']");
	private static final By DPD_SELECT_ORG = By.className("select2-results__option");
	public static final By MENU_SUPER = By.className("//ul[@olre='navigation']//li//a[contains(text(),'Super')]");
	public static final By LOCATION_MENU = By.id("sup-location");
	public static final By PRODUCT_MENU = By.id("drop1");
	public static final By MENU = By.id("drop2");
	public static final By REPORTS_MENU = By.id("supadmin-new-reports");
	public static final By ADMIN_MENU = By.id("drop6");

	public void selectOrganization(String selectText) {
		try {
			foundation.waitforElement(DPD_ORG, Constants.SHORT_TIME);
			foundation.click(DPD_ORG);
			textBox.enterText(TXT_ORG, selectText);
			foundation.click(DPD_SELECT_ORG);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Navigate to Particular Tab / Subtab
	 * 
	 * @param optionNames
	 */
	public void navigateToMenuItem(String optionNames) {
		try {
			List<String> optionName = Arrays.asList(optionNames.split(Constants.DELIMITER_HASH));
			foundation.click(By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0) + "')]"));
			if (optionName.size() == 2) {
				foundation.click(By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0)
						+ "')]//..//ul//li//a[(text()='" + optionName.get(1) + "')]"));
			} else if (optionName.size() > 2) {
				foundation.objectFocus(By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0)
						+ "')]//..//ul//li//a[(text()='" + optionName.get(1) + "')]"));
				foundation.click(By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0)
						+ "')]//..//ul//li//a[(text()='" + optionName.get(1) + "')]//..//ul/li/a[(text()='"
						+ optionName.get(2) + "')]"));
			}
			foundation.WaitForAjax(Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Launching the Browser as Super and Selecting the Org
	 * 
	 * @param org
	 */
	public void launchBrowserAsSuperAndSelectOrg(String org) {
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		selectOrganization(org);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	public void launchBrowserAndSelectOrg(String user, String org) {
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(user, propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		selectOrganization(org);
		foundation.threadWait(Constants.THREE_SECOND);
	}

	public By getMainMenuObj(String mainMenuName) {
		return By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + mainMenuName + "')]");
	}

	public List<String> getSubTabs(String tab) {
		List<String> subTabs = new ArrayList<>();
		List<String> optionName = Arrays.asList(tab.split(Constants.DELIMITER_HASH));
		try {
			foundation.click(By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0) + "')]"));
			subTabs = foundation.getTextofListElement(By.xpath(
					"//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0) + "')]//..//ul//li//a"));
			foundation.click(By.xpath("//ul[@role='navigation']//li//a[contains(text(),'" + optionName.get(0) + "')]"));
			foundation.WaitForAjax(Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return subTabs;
	}

	/**
	 * Launch Browser with Outlook Mail
	 */
	public void launchBrowserWithOutLookMail() {
		foundation.threadWait(Constants.SHORT_TIME);
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.OUTLOOK_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.outLookLogin(propertyFile.readPropertyFile(Configuration.OUTLOOK_USERNAME, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.OUTLOOK_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		foundation.threadWait(Constants.LONG_TIME);
	}

	/**
	 * verify navigation bar are present
	 */
	public void verifyNavigationBarsArePresent() {
		foundation.waitforElementToBeVisible(LOCATION_MENU, 3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LOCATION_MENU));
		foundation.waitforElementToBeVisible(PRODUCT_MENU, 3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(PRODUCT_MENU));
		foundation.waitforElementToBeVisible(MENU, 3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(MENU));
		foundation.waitforElementToBeVisible(REPORTS_MENU, 3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(REPORTS_MENU));
		foundation.waitforElementToBeVisible(ADMIN_MENU, 3);
		CustomisedAssert.assertTrue(foundation.isDisplayed(ADMIN_MENU));
	}
}
