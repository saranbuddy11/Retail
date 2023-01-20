package at.smartshop.sos.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.files.Excel;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class LoadProduct extends Factory{

	Dropdown dropDown = new Dropdown();
	TextBox textBox = new TextBox();
	Radio radio = new Radio();
	Foundation foundation = new Foundation();
	Excel excel = new Excel();
	

	public static final By LBL_PRODUCT = By.xpath("//li[text()='Load Product Parameters']");
	public static final By DPD_LOCATION = By.xpath("//select[@id='location']/option");
	public static final By BTN_SELECTALL = By.id("btn-all-loc");
	public static final By BTN_SELECTNONE = By.id("btn-none-loc");
	public static final By DPD_DELETE_EXISTING_PRODUCT = By.xpath("//select[@id='delflg']/option");
	public static final By DPD_LOAD_TYPE = By.xpath("//select[@id='load']/option");
	public static final By DPD_UPDATE_ONHAND_QTY = By.xpath("//select[@id='onhandqtyflg']/option");
	public static final By BTN_CHOOSE_FILE = By.id("myfile");
	public static final By BTN_SUBMIT = By.id("submitBtn");
	public static final By LBL_DELETE_POPUP = By.xpath("//h5[text()='Delete Product Database Confirmation WARNING!']");
	public static final By BTN_DELETE = By.id("confirm-delete-btn");
	public static final By DELETE_EXISTING_PRODUCT = By.id("delflg");
	public static final By BTN_CANCEL = By.xpath("//button[@class='btn btn-secondary']");
	public static final By LBL_PRODUCT_ERROR = By.xpath("//li[text()='Product List Errors']");
	public static final By LBL_ORG_FIELD = By.xpath("//dt[text()='Org: ']");
	public static final By LBL_LOCATION_FIELD = By.xpath("//dt[text()='Location:']");
	public static final By GET_ORG_FIELD_VALUE = By.xpath("//dd[text()='AutomationOrg']");
	public static final By LBL_FILE_NAME_FIELD = By.xpath("//dt[text()='File Name:          ']");
	public static final By LBL_LOAD_TYPE_FIELD = By.xpath("//dt[text()='Load Type: ']");
	public static final By LBL_DELETE_EXISTING_PRODUCT_FIELD = By.xpath("//dt[text()='Delete Existing Products?:']");
	public static final By LBL_UPDATE_ON_HAND_QTY_FIELD = By.xpath("//dt[text()='Update OnHandQty:']");
	public static final By SELECTED_LOCATION = By.xpath("//select[@id='location']/option[text()='AutoLocation1']");
	public static final By LBL_LOCATION_MESSAGE = By.xpath("//label[text()='Extend Product to Selected Location(s):']");
	public static final By TXT_DELETE_POPUP = By.xpath("//input[@type='text']");
	public static final By SUCCESS_MESSAGE = By.xpath("//div[contains(@class,'humane')]");
	public static final By LBL_PRODUCT_RESULT = By.xpath("//li[text()='Load Product Results']");
	public static final By TBL_PRODUCT_ERROR_HEADER = By.xpath("//table//tr[@role='row']");
	public static final By TBL_PRODUCT_ERROR_CONTENT = By.xpath("//tbody//tr[@class='odd']//td");
	public static final By TXT_DELETE_CONTENT = By.xpath("//div[@class='modal-body']");
	public static final By BTN_CLOSE = By.xpath("//button[@class='close']");
	
	

	
	public By clickLocation(String location) {
		return By.xpath("//select[@id='location']/option[text()='" + location + "']");
	}
	public By deleteOption(String option) {
		return By.xpath("//select[@id='delflg']/option[text()='" + option + "']");
	}
	
	public final String SHEET = "Sheet1";


	public void verifyProductFields() {
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_ORG_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.GET_ORG_FIELD_VALUE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_LOAD_TYPE_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_LOCATION_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_FILE_NAME_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_DELETE_EXISTING_PRODUCT_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_UPDATE_ON_HAND_QTY_FIELD));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.BTN_SELECTALL));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.BTN_SELECTNONE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.BTN_CHOOSE_FILE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.BTN_SUBMIT));
}
	/**
	 * upload Excel and Select Delete Option
	 * 
	 * @param requiredString
	 * @param deleteExisting
	 */
	public void uploadExcelAndSelectDeleteOption(String requiredString, String deleteExisting) {
	excel.writeToExcel(FilePath.PRODUCT_TEMPLATE,SHEET,"1#1#2",requiredString);
	textBox.enterText(LoadProduct.BTN_CHOOSE_FILE, FilePath.PRODUCT_TEMPLATE);
	if(!foundation.getText(LoadProduct.DELETE_EXISTING_PRODUCT).equals(deleteExisting)) {
	    dropDown.selectItem(LoadProduct.DELETE_EXISTING_PRODUCT, deleteExisting, Constants.TEXT);
	}
	foundation.click(LoadProduct.BTN_SUBMIT);
	foundation.threadWait(Constants.THREE_SECOND);
}
	
	
	public void verifyDeletePopupFunctionalityAndButton() {
	//verify Delete Popup 
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_DELETE_POPUP));
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.TXT_DELETE_POPUP));
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.BTN_DELETE));
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.BTN_CANCEL));
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.BTN_CLOSE));
	
	//verify cancel button
	foundation.click(LoadProduct.BTN_CANCEL);
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT));
	
	//verify Close button
	foundation.click(LoadProduct.BTN_SUBMIT);
	foundation.threadWait(Constants.THREE_SECOND);
	foundation.click(LoadProduct.BTN_CLOSE);
	CustomisedAssert.assertTrue(foundation.isDisplayed(LoadProduct.LBL_PRODUCT));

	//verify the error page
	foundation.click(LoadProduct.BTN_SUBMIT);
	}

}
