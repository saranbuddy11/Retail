package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.browser.Factory;

public class NationalAccounts extends Factory {

	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By TXT_ACCOUNT_NAME = By.id("name");
	public static final By DPD_CLIENT_NAME = By.id("nationalclient");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By BTN_CANCEL = By.xpath("//button[text()='Cancel']");
	public static final By BTN_OK = By.xpath("//button[text()='OK']");
	public static final By TXT_FILTER = By.id("filterType");
	public static final By TBL_BODY = By.cssSelector("#dataGrid > tbody");
	public static final By BTN_POP_UP_YES = By.xpath("//button[text()='YES']");
	public static final By LBL_NATIONAL_ACCOUNT = By.id("National Accounts");
	public static final By TBL_ROW = By.cssSelector("#dataGrid > tbody >tr");
	public static final By LBL_ORGINIZATION = By.cssSelector("td[aria-describedby=nationalAccountsSummaryGrid_org]");
	public static final By LBL_LOCATION = By.cssSelector("td[aria-describedby=nationalAccountsSummaryGrid_location]");
	public static final By ICO_DELETE = By.cssSelector("td[aria-describedby=dataGrid_delete] > a");
	
	public static final By DPD_ORGINIZATION = By.id("org");
	public static final By DPD_LOCATION = By.id("locs");
	public static final By BTN_ADD_NATIONAL_ACCOUNT = By.id("nationalAccountBtn");

	public By nationalAccountDetails(String accountName) {
		By element = null;
		try {
			element = By.xpath("//span[contains(text(),'"+accountName+"')]");
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
		return element;

	}


}
