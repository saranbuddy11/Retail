package at.smartshop.pages;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import at.framework.browser.Browser;
import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

public class PromotionList extends Factory {
	private Foundation foundation = new Foundation();
	private TextBox textbox = new TextBox();
	private EditPromotion editPromotion = new EditPromotion();
	private NavigationBar navigationBar = new NavigationBar();
	public Browser browser = new Browser();
	public Login login = new Login();
	private Dropdown dropdown = new Dropdown();
	private LocationList locationList = new LocationList();

	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By PAGE_TITLE = By.xpath("//li[text()='Promotion List']");
	public static final By LBL_START_DATE_HEADER = By.id("hierarchicalGrid_startDate");
	public static final By LBL_START_DATE_LIST = By.xpath("//td[@aria-describedby='hierarchicalGrid_startDate']");
	public static final By BTN_OK = By.xpath("//button[text()='OK']");
	public static final By TXT_SEARCH_PROMONAME = By.id("search");
	public static final By BTN_SEARCH = By.id("searchbtn");
	public static final By TBL_COLUMN_NAME = By.xpath("//td[@aria-describedby='hierarchicalGrid_name']");
	public static final By CALENDER_DATE_RANGE = By.id("daterange");
	public static final By DPD_LOCATION = By.id("loc-dropdown");
	public static final By DPD_STATUS = By.id("status");
	public static final By BASIC_PROMOTION_TITLE = By.xpath("//div[text()='Enter Promotion Basics']");
	public static final By CHOOSE_PROMOTION_FILTER = By.xpath("//div[text()='Choose Promotion Filters']");
	public static final By PROMOTION_DETAILS = By.xpath("//div[text()='Promotion Details']");
	
	public static final By DPD_PROMOTYPE = By.id("promotype");
	public static final By LBL_SEARCH = By.xpath("//input[@id='search']//..//..//dt");
	public static final By LBL_CALENDER_DATE_RANGE = By.xpath("//input[@id='startdate']//..//..//dt");
	public static final By LBL_LOCATION = By.xpath("//select[@id='loc-dropdown']//..//..//dt");
	public static final By LBL_STATUS = By.xpath("//select[@id='status']//..//..//dt");
	public static final By LBL_PROMOTYPE = By.xpath("//select[@id='promotype']//..//..//dt");
	public static final By LBL_ORG_NAME = By.xpath("//td[contains(@aria-describedby,'promotionOrgLevel_child_org')]");
	public static final By LINK_EXPAND = By.xpath("//span[@title='Expand Row']");
	public static final By LBL_LOCATION_NAME = By
			.xpath("//td[contains(@aria-describedby,'promotionLocationLevel_child_name')]");
	public static final By DRP_STATUS = By.xpath("//select[@id='status']");
	public static final By NAME_PROMOTION = By.id("name");
	public static final By DISPLAY_PROMOTION = By.id("displayname");
	public static final By NEXT = By.id("submitBtn");
	public static final By ARROY_RIGHT = By.id("singleSelectLtoR");
	public static final By BUY_PRICE = By.xpath("//div[@id='bundlesummary']/span");

	public void clickSelectedRow(String dataGridname, String promoName) {
		foundation.doubleClick(By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + promoName + "']"));
	}

	public void searchPromotion(String promoName, String statusType) {
		foundation.waitforElementToBeVisible(TXT_SEARCH_PROMONAME, Constants.EXTRA_LONG_TIME);
		textbox.enterText(TXT_SEARCH_PROMONAME, promoName);
		dropdown.selectItem(DRP_STATUS, statusType, Constants.TEXT);
		foundation.click(BTN_SEARCH);
	}

	public void verifyPromotionName(String promotionName) {
		try {
			List<WebElement> promotionData = getDriver().findElements(TBL_COLUMN_NAME);
			for (int iter = 0; iter < promotionData.size(); iter++) {
				if (promotionData.get(iter).getText().equals(promotionName)) {
					ExtFactory.getInstance().getExtent().log(Status.INFO,
							"Promotion created Suucessfully:  " + promotionName);
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void expirePromotion(String menuItem, String promotionName, String statusType, String gridName) {
		try {
			foundation.threadWait(Constants.SHORT_TIME);
			// browser.navigateURL(propertyFile.readPropertyFile(Configuration.DASHBOARD_URL,
			// FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			foundation.threadWait(Constants.TWO_SECOND);
			searchPromotion(promotionName, statusType);
			foundation.threadWait(Constants.THREE_SECOND);
			//assertTrue(foundation.getText(TBL_COLUMN_NAME).equals(promotionName));
			editPromotion.expirePromotion(gridName, promotionName);
			foundation.waitforElement(PAGE_TITLE, Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Expire the multiple promotion in promotion list page
	 * 
	 * @param menuItem
	 * @param promotionName
	 * @param gridName
	 */
	public void expireMultiplePromotion(String menuItem, String promotionName, String gridName) {
		try {
			foundation.threadWait(Constants.TWO_SECOND);
			navigationBar.navigateToMenuItem(menuItem);
			foundation.waitforElementToBeVisible(TXT_SEARCH_PROMONAME, Constants.EXTRA_LONG_TIME);
			textbox.enterText(TXT_SEARCH_PROMONAME, promotionName);
			foundation.click(BTN_SEARCH);
			assertTrue(foundation.getText(TBL_COLUMN_NAME).equals(promotionName));
			editPromotion.expirePromotion(gridName, promotionName);
			foundation.waitforElement(PAGE_TITLE, Constants.SHORT_TIME);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Launch browser,navigate to menu and click on create promotion in promotion
	 * page
	 * 
	 * @param menu
	 */

	public void navigateMenuAndCreatePromo(String menu) {
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
		navigationBar.selectOrganization(
				propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.navigateToMenuItem(menu);
		CustomisedAssert.assertTrue(foundation.isDisplayed(PAGE_TITLE));
		foundation.click(BTN_CREATE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(CreatePromotions.LBL_CREATE_PROMOTION));
	}

	/**
	 * Navigate to Location and Do full Sync
	 * 
	 * @param menu
	 * @param location
	 */
	public void navigateToLocationAndFullSync(String menu, String location) {
		navigationBar.navigateToMenuItem(menu);
		locationList.selectLocationName(location);
		foundation.click(LocationSummary.BTN_FULL_SYNC);
		foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.click(LocationSummary.BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
	}
}
