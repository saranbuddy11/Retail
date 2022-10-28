package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class AdminRoundUpCharity extends Factory {

	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private CheckBox checkBox = new CheckBox();
	private TextBox textBox = new TextBox();
	
	public static final By DPD_CHARITY_ROUNDUP = By.id("roundupcharity");
	public static final By LBL_ROUNDUPCHARITY = By.xpath("//div/h2[text()='Round Up For Charity']");
	public static final By BTN_ADDCHARITY = By.id("addACharityBtn");
	public static final By LBL_ACITIVECHARITY = By.xpath("//div/h3/b[text()='Active Charities']");
	public static final By TXT_SEARCH = By.xpath("//input[@type='text']");
	public static final By TBL_GRID = By.xpath("//table[contains(@class,'table-borderless dataTable')]");
	public static final By SELECT_ROW = By.xpath("//tbody[@role='alert']//td/a[contains(@href,'addCharity?')]");
	public static final By LBL_ADDCHARITY = By.xpath("//h2[text()='Add a Charity']");
	public static final By LBL_EDITCHARITY = By.xpath("//h2[text()='Edit Charity']");
	public static final By BTN_CANCEL = By.id("cancelCharityBtn");
	public static final By BTN_SAVE = By.id("saveCharityBtn");
	public static final By TXT_DISPLAYNAME = By.id("displayName");
	public static final By CLEAR_LOCATION = By.xpath("//span[@class='select2-selection__clear']");
	public static final By SELECT_LOCATION = By.xpath("//select[@id='locationDropdown']/optgroup/option");
	public static final By LBL_LOCATION = By.xpath("//span[@class='select2-selection select2-selection--multiple']//input[@class='select2-search__field']");
	public static final By BTN_SEARCH = By.id("einSearchBtn");
	public static final By CHARITY_ID = By.id("ein");
	public static final By CAUSE_NAME = By.id("causeName");
	public static final By TXT_EIN_SEARCH = By.id("einSearch");
	public static final By LBL_DELECT_CHARITY = By.xpath("//div/p[text()='Delete Charity']");
	public static final By BTN_DELECT_CHARITY = By.xpath("//button[contains(@onclick,'deleteCharity')]");
	public static final By BTN_DELECT_CHARITY_CANCEL = By.xpath("//button[@class='ajs-button ajs-cancel']");
	public static final By BTN_DELECT_CHARITY_DELECT = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By GET_LOCATION = By.xpath("//ul//li[@class='select2-selection__choice']");
	public static final By TBL_HEADER_CHARITY = By.xpath("//table[@id='dt']/thead//th");

	public By removeLocation(String location) {
		return By.xpath("//ul//li[text()='"+location+"']/span[@class='select2-selection__choice__remove']"); }

	public By selectLocation(String location) {
		return By.xpath("//ul[@class='select2-results__options select2-results__options--nested']//li[text()='"+location+"']"); }
	
	
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
	foundation.threadWait(3);
	textBox.enterText(AdminRoundUpCharity.LBL_LOCATION, location);
	foundation.click(selectLocation(location));
}
}
