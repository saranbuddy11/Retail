package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class PrintGroupLists {
	
	public static final By DPD_LOCATION = By.xpath("//select[@id='createurl']");
	public static final By BTN_CREATENEW = By.xpath("//button[text()='Create New']");
	public static final By TXT_NAME = By.cssSelector("input#name");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By TXT_SPINNER_MSG = By.className("humane");
	public static final By VALIDATE_PRINT_LIST_HEADING = By.id("Print Groups List");
	public static final By VALIDATE_PRINT_GROUPCREATE_HEADING = By.id("Print Group Create");
	public static final By TXT_NAME_ERROR = By.id("name-error");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By TXT_TYPE = By.id("type"); 
	public static final By TXT_SEARCH_FILTER = By.xpath("//input[@aria-controls='dt']");
	public static final By SELECT_GRID = By.xpath("//td[@class=' sorting_1']");
	public static final By LBL_INFO = By.xpath("//div[@id='dt_info']");
	public static final By LBL_LOCATION = By.cssSelector("#dt > tbody > tr.odd > td:nth-child(4)");
	public static final By VALIDATE_PRINTER_SUMMARY = By.id("Printer Summary");
	
	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private PropertyFile propertyFile = new PropertyFile();
	
	public void createPrintGroup(String printer_Name, String printer_Type) {
		try {
			foundation.waitforElement(BTN_CREATENEW, Constants.SHORT_TIME);
			foundation.click(BTN_CREATENEW);
			textBox.enterText(TXT_NAME, printer_Name);
			dropDown.selectItem(TXT_TYPE,printer_Type, Constants.TEXT);
	
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	public void updatePrintGroup(String existing_Printer_Name, String result_Data, String printGroup_Summary_Page, String updated_Printer_Name, String updated_Printer_Type) {
		try {
			dropDown.selectItem(DPD_LOCATION, propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE), Constants.TEXT);
			textBox.enterText(TXT_SEARCH_FILTER, existing_Printer_Name);
			String actualData = foundation.getText(SELECT_GRID);
			CustomisedAssert.assertEquals(actualData, existing_Printer_Name);
			
			String resultText = foundation.getText(LBL_INFO);
			CustomisedAssert.assertTrue(resultText.contains(result_Data));
			
			//Update the details
			foundation.click(SELECT_GRID);
			CustomisedAssert.assertTrue(foundation.isDisplayed(VALIDATE_PRINTER_SUMMARY),printGroup_Summary_Page);
			textBox.enterText(TXT_NAME, updated_Printer_Name);
			dropDown.selectItem(TXT_TYPE,updated_Printer_Type, Constants.TEXT);
			foundation.click(BTN_SAVE);
			foundation.waitforElementToDisappear(TXT_SPINNER_MSG,Constants.SHORT_TIME);
	
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

}
