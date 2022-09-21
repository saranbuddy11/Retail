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
	public static final By TXT_DOMAIN_NAME=By.id("name");
	public static final By TXT_DOMAIN_ADDRESS=By.id("address");

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

	
	public void createSSODomain() {
		foundation.waitforElementToBeVisible(BTN_CREATE_NEW, Constants.THREE_SECOND);
		foundation.click(BTN_CREATE_NEW);
		
		
	}
}
