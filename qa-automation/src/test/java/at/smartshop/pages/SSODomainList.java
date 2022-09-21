package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class SSODomainList extends Factory {

	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox();
	private NavigationBar navigationBar = new NavigationBar();

	public static final By LBL_SSO_DOMAIN = By.id("page-title");
	public static final By BTN_CREATE_NEW = By.id("createNewBtn");
	public static final By RECORDS_FILTER = By.id("ssodomaingrid_editor_dropDownButton");
	public static final By TXT_SEARCH = By.id("search-box");
	public static final By DOMAIN_NAME_GRID = By.id("ssodomaingrid_name");
	public static final By DOMAIN_ADDRESS_GRID = By.id("ssodomaingrid_address");
	public static final By CREATE_SSO_DOMAIN = By.xpath("//li[@class='active']");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By TXT_DOMAIN_NAME = By.id("name");
	public static final By BTN_DELETE = By.id("deleteBtn");
	public static final By POPUP_DELETE_BTN = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By TXT_DOMAIN_ADDRESS = By.id("address");
	public static final By POPUP_HEADER = By.xpath("//div[@class='ajs-header']");
	public static final By DOMAIN_NAME_GRID_ROW = By.xpath("//td[@aria-describedby='ssodomaingrid_name']");

	/**
	 * verify all fields in sso domain list page
	 */
	public void verifyAllFieldsInSSODomainListpage() {
		foundation.waitforElementToBeVisible(LBL_SSO_DOMAIN, Constants.THREE_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_CREATE_NEW));
		CustomisedAssert.assertTrue(foundation.isDisplayed(RECORDS_FILTER));
		foundation.waitforElementToBeVisible(TXT_SEARCH, Constants.THREE_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_SEARCH));
		CustomisedAssert.assertTrue(foundation.isDisplayed(DOMAIN_ADDRESS_GRID));
		CustomisedAssert.assertTrue(foundation.isDisplayed(DOMAIN_NAME_GRID));

	}

	/**
	 * Navigate to create SSO Domain and click on cancel
	 */
	public void navigateoCreateSSOAndClickOnCancel() {
		foundation.waitforElementToBeVisible(BTN_CREATE_NEW, Constants.THREE_SECOND);
		foundation.click(BTN_CREATE_NEW);
		CustomisedAssert.assertTrue(foundation.isDisplayed(CREATE_SSO_DOMAIN));
		foundation.waitforElementToBeVisible(BTN_CANCEL, Constants.TWO_SECOND);
		foundation.click(BTN_CANCEL);
		foundation.waitforElementToBeVisible(LBL_SSO_DOMAIN, Constants.THREE_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_SSO_DOMAIN));

	}

	/**
	 * delete created SSO Domain
	 * @param createdsso
	 */
	public void deleteCreatedSSODomain(String createdsso) {
		foundation.waitforElementToBeVisible(TXT_SEARCH, Constants.THREE_SECOND);
		textBox.enterText(TXT_SEARCH, createdsso);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.doubleClick(DOMAIN_NAME_GRID_ROW);
		foundation.waitforElementToBeVisible(BTN_DELETE, Constants.TWO_SECOND);
		foundation.click(BTN_DELETE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(POPUP_HEADER));
		foundation.click(POPUP_DELETE_BTN);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_SSO_DOMAIN));
	}

	/**
	 * create sso domain
	 * 
	 * @param name
	 * @param address
	 */
	public void createSSODomain(String name, String address) {
		foundation.waitforElementToBeVisible(BTN_CREATE_NEW, Constants.THREE_SECOND);
		foundation.click(BTN_CREATE_NEW);
		foundation.waitforElementToBeVisible(TXT_DOMAIN_ADDRESS, Constants.TWO_SECOND);
		textBox.enterText(TXT_DOMAIN_NAME, name);
		textBox.enterText(TXT_DOMAIN_NAME, address);
		foundation.waitforElementToBeVisible(BTN_SAVE, Constants.TWO_SECOND);
		foundation.click(BTN_SAVE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_SSO_DOMAIN));

	}
}
