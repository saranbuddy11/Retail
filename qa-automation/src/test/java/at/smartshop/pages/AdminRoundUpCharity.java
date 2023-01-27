package at.smartshop.pages;

import java.util.List;
import org.openqa.selenium.By;
import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class AdminRoundUpCharity extends Factory {

	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	
	public static final By DPD_CHARITY_ROUNDUP = By.id("roundupcharity");
	public static final By LBL_ROUNDUPCHARITY = By.xpath("//div/h2[text()='Round Up For Charity']");
	public static final By BTN_ADDCHARITY = By.id("addACharityBtn");
	public static final By LBL_ACITIVECHARITY = By.xpath("//div/h3/b[text()='Active Charities']");
	public static final By TXT_SEARCH = By.xpath("//input[@type='text']");
	public static final By TBL_GRID = By.xpath("//table[contains(@class,'table-borderless dataTable')]");
	public static final By SELECT_ROW_CAUSENAME = By.xpath("//tbody[@role='alert']//td/a[contains(@href,'addCharity?')]");
	public static final By LBL_ADD_CHARITY = By.xpath("//h2[text()='Add a Charity']");
	public static final By LBL_EDIT_CHARITY = By.xpath("//h2[text()='Edit Charity']");
	public static final By BTN_CANCEL = By.id("cancelCharityBtn");
	public static final By BTN_SAVE = By.id("saveCharityBtn");
	public static final By TXT_DISPLAYNAME = By.id("displayName");
	public static final By TXT_LOCATION = By.xpath("//label[@for='allLocations']");
	public static final By CLEAR_LOCATION = By.xpath("//span[@class='select2-selection__clear']");
	public static final By SELECT_LOCATION = By.xpath("//select[@id='locationDropdown']/optgroup/option");
	public static final By LBL_LOCATION = By.xpath("//span[@class='select2-selection select2-selection--multiple']//input[@class='select2-search__field']");
	public static final By BTN_SEARCH = By.id("einSearchBtn");
	public static final By CHARITY_ID = By.id("ein");
	public static final By LBL_EIN= By.xpath("//label[@for='einSearch']");
	public static final By LBL_CHARITY_ID= By.xpath("//label[@for='ein']");
	public static final By LBL_CAUSE_NAME= By.xpath("//label[@for='causeName']");
	public static final By LBL_DISPLAYNAME= By.xpath("//label[@for='displayName']");
	public static final By CAUSE_NAME = By.id("causeName");
	public static final By TXT_EIN_SEARCH = By.id("einSearch");
	public static final By LBL_DELETE_CHARITY = By.xpath("//div/p[text()='Delete Charity']");
	public static final By BTN_DELETE_CHARITY = By.xpath("//button[contains(@onclick,'deleteCharity')]");
	public static final By BTN_DELETE_CHARITY_CANCEL = By.xpath("//button[@class='ajs-button ajs-cancel']");
	public static final By BTN_DELETE_CHARITY_DELETE = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By GET_LOCATION = By.xpath("//ul//li[@class='select2-selection__choice']");
	public static final By TBL_HEADER_CHARITY = By.xpath("//table[@id='dt']/thead//th");
	public static final By SELECT_ROW = By.xpath("//tbody[@role='alert']//td");
	public static final By ERROR_DISPLAY_NAME = By.id("displayName-error");
	public static final By ERROR_BTN_SEARCH = By.id("einErrorText");
	public static final By STYLE_DISPLAY_NAME= By.xpath("//textarea[@placeholder='WESTERN PRESBYTERIAN CHURCH']");
	public static final By DISABLE_LOCATION= By.xpath("//span[contains(@class,'select2-container--disabled')]");
	public static final By GET_DPD_LOCATION= By.xpath("//ul[@id='select2-locationDropdown-results']//li//strong");
	public static final By VIEW_LOCATION = By.id("viewLocBtn");
	public static final By GET_VIEW_LOCATION_LOCATIONS= By.xpath("//div[@id='locationModalBody']//p");
	public static final By LBL_VIEW_LOCATION_POPUP= By.xpath("//div//h4[@id='locationsModalHeader']");
	public static final By CLOSE_VIEW_LOCATION_POPUP= By.xpath("//div//button[@id='gpc-UpdateClose']//i");
	public static final By GET_EIN= By.xpath("//tbody[@role='alert']//td[5]");
	public static final By GET_CONTENT_DELETE_CHARITY = By.xpath("//div[@class='ajs-content']//b");
	public static final By BTN_CANCEL_DELETE_CHARITY = By.xpath("//button[@class='ajs-button ajs-cancel']");
	public static final By GET_DISPLAY_NAME= By.xpath("//tbody[@role='alert']//td[3]");
	public static final By LBL_EIN_CHARITY= By.xpath("//div//h5[text()='Enter EIN of the charity you wish to add']");
	public static final By LINKED_TEXT_LOCATION = By.xpath("//p[text()='Locations already associated to a charity will be unavailable for selection.']");
	public static final By DISABLE_TEXT_LOCATION = By.xpath("//ul[@class='select2-results__options select2-results__options--nested']/li[@aria-disabled='true']");
	public static final By DISABLE_ALL_TEXT_LOCATION = By.xpath("//li[contains(@id,'all') and @aria-disabled='true']");
	public static final By TBL_DATA = By.xpath("//tbody//td");
	public static final By ALL_LOCATION = By.xpath("//ul[@class='select2-results__options']");
	public static final By CLICK_LOCATION = By.xpath("//span[@class='select2-selection select2-selection--multiple']");
	public static final By NAME_ERROR= By.xpath("//label[text()='Display Name is required.']");

	public By removeLocation(String location) {
		return By.xpath("//ul//li[text()='"+location+"']/span[@class='select2-selection__choice__remove']"); }

	public By selectLocation(String location) {
		return By.xpath("//ul[@class='select2-results__options select2-results__options--nested']//li[text()='"+location+"']"); }
	
	public By selectAscendingAndDescending(String location) {
		return By.xpath("//thead//th[text()='"+location+"']"); 
		}
	public By getAscendingAndDescending(String sortcolumn) {
		return By.xpath("//thead//th[@class='"+sortcolumn+"']");
	}
	
	
		
	/**
	 * select charity  
	 * @param data
	 */
	public void selectCharity(String data) {
	textBox.enterText(AdminRoundUpCharity.TXT_SEARCH, data);
	foundation.click(AdminRoundUpCharity.SELECT_ROW_CAUSENAME);
	}
	
	/**
	 * verify charity dropdown options 
	 * @param values
	 * @param object
	 */
	public void verifyCharityDropdown(List<String> values, By object) {
		for (int i = 0; i < values.size(); i++) {
			List<String> actual = dropDown.getAllItems(object);
			CustomisedAssert.assertEquals(actual, values);
		}
	}

	/**
	 * verify charity option and check submenu
	 * 
	 * @param equaloption
	 * @param option
	 */
	public void verifyCharityOptions( String option) {
		foundation.waitforElementToBeVisible(DPD_CHARITY_ROUNDUP, Constants.THREE_SECOND);
		dropDown.selectItem(DPD_CHARITY_ROUNDUP, option, Constants.TEXT);
		foundation.waitforElementToBeVisible(OrgSummary.BTN_SAVE, Constants.THREE_SECOND);
		foundation.click(OrgSummary.BTN_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);
	}
	
	/**
	 * verify country Fields as United States and verify roundup charity Field Enable or Disable
	 * 
	 * @param option
	 * @param option1
	 */
	public void verifyCountryAndSelectCharityOption( String option,String option1) {
	foundation.scrollIntoViewElement(OrgSummary.DPD_COUNTRY);
	if (!(OrgSummary.DPD_COUNTRY).equals(option)) {
		dropDown.selectItem(OrgSummary.DPD_COUNTRY,option, Constants.TEXT);}
	foundation.scrollIntoViewElement(OrgSummary.CHK_AGE_VERIFICATION);
	CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.ROUND_UP_CHARITY));
	dropDown.selectItem(AdminRoundUpCharity.DPD_CHARITY_ROUNDUP,option1, Constants.TEXT);
	foundation.click(OrgSummary.BTN_SAVE);
	}
	
	/**
	 * select Location From DropDown
	 * @param location
	 */
	public void selectLocationFromDropDown( String location) {
	foundation.click(AdminRoundUpCharity.LBL_LOCATION);
	foundation.threadWait(Constants.THREE_SECOND);
	textBox.enterText(AdminRoundUpCharity.LBL_LOCATION, location);
	foundation.click(selectLocation(location));
}
	/**
	 * enter EIN And Search
	 * @param EIN
	 */
	public void enterEINAndSearch(String EIN) {
	textBox.enterText(AdminRoundUpCharity.TXT_EIN_SEARCH,EIN);
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_SEARCH));
	foundation.click(AdminRoundUpCharity.BTN_SEARCH);
	foundation.threadWait(Constants.THREE_SECOND);
	
}
	/**
	 * change Header Ascending And Descending For Verify Table Data
	 * @param column
	 * @param ascending
	 * @param descending
	 */
	public void changeHeaderAscendingAndDescendingForVerifyTableData(By choosecol,String column,String ascending,String descending) {
	foundation.click(selectAscendingAndDescending(column));
	CustomisedAssert.assertTrue(foundation.isDisplayed(getAscendingAndDescending(ascending)));
	foundation.threadWait(Constants.THREE_SECOND);
	foundation.click(selectAscendingAndDescending(column));
	CustomisedAssert.assertTrue(foundation.isDisplayed(getAscendingAndDescending(descending)));
	foundation.threadWait(Constants.THREE_SECOND);
}
	/**
	 * create Charity With Field
	 * @param EIN
	 * @param location
	 * @param displayname
	 */
	public void createCharityWithField(String EIN,String location,String displayname) {
	foundation.click(AdminRoundUpCharity.BTN_ADDCHARITY);
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ADD_CHARITY));
	textBox.enterText(AdminRoundUpCharity.TXT_EIN_SEARCH, EIN);
	CustomisedAssert.assertFalse(foundation.isDisplayed(AdminRoundUpCharity.TXT_DISPLAYNAME));
	foundation.click(AdminRoundUpCharity.BTN_SEARCH);
	selectLocationFromDropDown(location);
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.TXT_DISPLAYNAME));
	textBox.enterText(AdminRoundUpCharity.TXT_DISPLAYNAME, displayname);
	foundation.click(AdminRoundUpCharity.BTN_SAVE);
}
	/**
	 * Verify Charity Field
	 */
	public void verifyCharityField() {
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_EIN_CHARITY));
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_EIN));
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_SEARCH));
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_CAUSE_NAME));
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_CHARITY_ID));
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_DISPLAYNAME));
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.TXT_LOCATION));
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_SAVE));
	CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_CANCEL));
}
}
