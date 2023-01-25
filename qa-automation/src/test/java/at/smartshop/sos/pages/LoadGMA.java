package at.smartshop.sos.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class LoadGMA extends Factory {

	Dropdown dropDown = new Dropdown();
	TextBox textBox = new TextBox();
	Radio radio = new Radio();
	Foundation foundation = new Foundation();

	public static final By BTN_SUBMIT = By.id("submitBtn");
	public static final By LBL_SUCCESS = By.xpath("//td[contains(text(),'Number of Users successfully added')]");
	public static final By DPD_PIN_VALUE = By.id("pinvalueopt");
	public static final By DPD_START_BALANCE = By.id("startbalopt");
	public static final By DPD_LOCATION = By.id("location");
	public static final By TXT_FILE_NAME = By.name("myfile");
	public static final By RADIO_YES = By.id("delete-y");
	public static final By RADIO_NO = By.id("delete-n");
	public static final By LBL_RADIO_YES = By.xpath("//label[text()='Yes']");
	public static final By BTN_CONFIRM = By.xpath("//button[contains(text(),'CONFIRM')]");
	public static final By LBL_ERROR_MSG = By.xpath("//p[text()='Input file had errors. The users listed below were not loaded.']");
	public static final By LBL_TITLE_ERROR_PAGE= By.xpath("//li[text()='Load GMA User Results']");
	public static final By LBL_SUBTITLE_ERROR_PAGE = By.xpath("//h3[text()='User Load Error Results']");
	public static final By LBL_TITLE_GMA= By.xpath("//li[text()='Load GMA User Parameters']");
	public static final By DPD_LOAD_TYPE= By.id("load");
	public static final By LBL_ORG_FIELD = By.xpath("//dt[text()='Org: ']");
	public static final By LBL_LOCATION_FIELD = By.xpath("//dt[text()='Location:']");
	public static final By GET_ORG_FIELD_VALUE = By.xpath("//dd[text()='AutomationOrg']");
	public static final By LBL_FILE_NAME_FIELD = By.xpath("//dt[text()='File Name:']");
	public static final By LBL_LOAD_TYPE_FIELD = By.xpath("//dt[text()='Load Type: ']");
	public static final By LBL_DELETE_EXISTING_FIELD = By.xpath("//dt[text()='Delete Existing:']");
	public static final By LBL_START_BALANCE_FIELD = By.xpath("//dt[text()='Start Balance Options:']");
	public static final By LBL_PIN_VALUE_FIELD = By.xpath("//dt[text()='Pin Value Options:']");
	public static final By TXT_DELETE_CONTENT = By.xpath("//div[@class='ajs-content']//div");
	public static final By BTN_CANCEL = By.xpath("//button[@class='ajs-button ajs-cancel']");
	public static final By BTN_CLOSE = By.xpath("//button[@class='ajs-close']");
	public static final By DPD_DELETE_EXISTING= By.xpath("//label[@class='radio-inline']");
	public static final By DEFAULT_LOCATION = By.id("select2-location-container");
	public static final By DPD_LOCATION_OPTIONS= By.xpath("//select[@id='location']//option");
	public static final By DPD_PIN_VALUE_OPTIONS= By.xpath("//select[@id='pinvalueopt']/option");
	public static final By DPD_START_BALANCE_OPTIONS= By.xpath("//select[@id='startbalopt']/option");
	public static final By DEFAULT_PINVALUE = By.id("select2-pinvalueopt-container");
	public static final By DEFAULT_START_BALANCE = By.id("select2-startbalopt-container");
	public static final By DEFAULT_DELETE_EXISTING= By.xpath("//input[@id='delete-n' and @checked='checked']");
	public static final By LBL_RADIO_YES_WARNING= By.xpath("//label[text()='Warning: This action will delete all users and cannot be undone']");
	public static final By CONFIRM_WARNING_CONTENT= 
			By.xpath("//div[text()='Warning: This action will delete all users and cannot be undone. Please confirm that deleting all existing users for this location is the desired action.']");
	public static final By TXT_ERROR_MESSAGE= By.xpath("//div[contains(@class,'humane humane-libnotify-error')]");
	
	public final String SHEET = "Sheet1";

	public void gMAUser(String location, String pinValue, String startBalance, String filePath,
			String deleteProductStatus) {
		try {
			dropDown.selectItem(DPD_LOCATION, location, Constants.TEXT);
			dropDown.selectItem(DPD_PIN_VALUE, pinValue, Constants.TEXT);
			dropDown.selectItem(DPD_START_BALANCE, startBalance, Constants.TEXT);
			textBox.enterText(TXT_FILE_NAME, filePath);
			if (deleteProductStatus.equalsIgnoreCase(foundation.getText(LBL_RADIO_YES))) {
				radio.set(RADIO_YES);
				foundation.click(BTN_SUBMIT);
				foundation.click(BTN_CONFIRM);
			} else {
				radio.set(RADIO_NO);
				foundation.click(BTN_SUBMIT);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}
	
	public void verifyProductFields() {
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_TITLE_GMA));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_ORG_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.GET_ORG_FIELD_VALUE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_LOAD_TYPE_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_LOCATION_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_FILE_NAME_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_DELETE_EXISTING_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_PIN_VALUE_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_START_BALANCE_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.TXT_FILE_NAME));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.BTN_SUBMIT));
}
	
	public void verifyConfirmPopupFunctionality() {
	//verify Confirm PopUp
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_RADIO_YES_WARNING));
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.BTN_CLOSE));
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.BTN_CANCEL));
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadGMA.BTN_CONFIRM));
	
	//Verify Close button functionality
	foundation.click(LoadGMA.BTN_CLOSE);
	foundation.threadWait(Constants.SHORT_TIME);
	
	//Verify Cancel button functionality
	foundation.click(LoadGMA.BTN_SUBMIT);
	foundation.threadWait(Constants.SHORT_TIME);
	foundation.click(LoadGMA.BTN_CANCEL);
		
	//Verify Confirm button functionality
	foundation.click(LoadGMA.BTN_SUBMIT);
	foundation.threadWait(Constants.SHORT_TIME);
	foundation.click(LoadGMA.BTN_CONFIRM);
	
}
}