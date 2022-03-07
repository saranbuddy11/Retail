package at.smartshop.pages;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.database.mssql.Queries;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Numbers;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNSuperList;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class DICRules {

	public static final By TXT_DIC_HEADING= By.id("DIC Rules");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By TXT_CREATE_HEADING = By.id("DIC Rule Create");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By TXT_REQUIRED_ERROR = By.id("type-error");
	public static final By TXT_NAME_ERROR = By.id("name-error");
	public static final By CHCKBX_REQUIRED = By.id("required");
	public static final By CHCKBX_ACTIVE = By.id("active");
	public static final By TXT_ACTIVE_DISABLED = By.id("disabletext");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By TXTBX_TYPE = By.id("type");
	public static final By TXTBX_NAME = By.id("name");
	public static final By TXTBX_DESC = By.id("description");
	public static final By TXTBX_LABEL = By.id("label");
	public static final By LBL_FIELD_TYPE = By.id("fieldtype");
	public static final By LBL_CHOICE_TYPE = By.id("choicetype");
	public static final By TXTBX_CHOICES = By.id("choices");
	public static final By TXTBX_PLACEHLDR = By.id("placeholder");
	public static final By TXTBX_MAX_LENGTH = By.id("maxlength");
	public static final By TXTBX_SIZE = By.id("size");
	public static final By TXTBX_DEFAULT_VALUE = By.id("defaultvalue");
	public static final By TXT_SEARCH_FILTER = By.xpath("//div[@id='dt_filter']//input");
	public static final By TXTBX_SEQNBR = By.id("seqnbr");
	public static final By TXTBX_SEQNBR_ERROR = By.id("seqnbr-error");
	public static final By TXT_DIC_SHOW_HEADING = By.id("DIC Rule");
	public static final By TXT_DISABLED = By.id("disabletext");
	
	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private CheckBox checkBox=new CheckBox();
	private Numbers numbers=new Numbers();
	private Table table = new Table();
	
	
	public void createDICRule(String type_Field, String label_Field,List<String> create_Page_Data) {
		int placeholder = numbers.generateRandomNumber(12, 1234);
		try {
			foundation.waitforElement(BTN_CREATE_NEW, Constants.TWO_SECOND);
			foundation.click(BTN_CREATE_NEW);
			textBox.enterText(TXTBX_TYPE, type_Field);
			textBox.enterText(TXTBX_NAME, type_Field);
			textBox.enterText(TXTBX_DESC, create_Page_Data.get(2));
			textBox.enterText(TXTBX_LABEL, label_Field);
			dropDown.selectItem(LBL_FIELD_TYPE,create_Page_Data.get(4), Constants.TEXT);
			dropDown.selectItem(LBL_CHOICE_TYPE,create_Page_Data.get(5), Constants.TEXT);
			textBox.enterText(TXTBX_CHOICES, create_Page_Data.get(6));
			By reqChck = By.xpath("//input[@id='required']");		
			assertTrue(checkBox.isChkEnabled(reqChck));
			foundation.click(reqChck);
			textBox.enterText(TXTBX_PLACEHLDR, ""+ placeholder);
			textBox.enterText(TXTBX_MAX_LENGTH,""+ placeholder);
			textBox.enterText(TXTBX_SIZE, ""+ placeholder);
			textBox.enterText(TXTBX_DEFAULT_VALUE, ""+ placeholder);
	
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateDICRule(String existing_DICRule, String validating_record,String dicRule_Show_Page,String updated_DICRule,String active_disabled_message) {
		try {
			textBox.enterText(TXT_SEARCH_FILTER, existing_DICRule);
			
			String actualData = foundation.getText(By.xpath("//td[text()='"+existing_DICRule+"']"));
			CustomisedAssert.assertEquals(actualData, existing_DICRule);
			
			String resultText = foundation.getText(PrintGroupLists.LBL_INFO);
			CustomisedAssert.assertTrue(resultText.contains(validating_record));
			
			//Update the details
			table.selectRow(existing_DICRule);
			CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_DIC_SHOW_HEADING),dicRule_Show_Page);
			textBox.enterText(TXTBX_NAME, updated_DICRule);
			
			By activeChck = By.xpath("//input[@id='active']");
			if(checkBox.isChecked(activeChck) == true){
			foundation.click(activeChck);
			CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_DISABLED),active_disabled_message);
			foundation.click(BTN_SAVE);
			foundation.alertAccept();
			}else{
		    foundation.click(activeChck);
			foundation.click(BTN_SAVE);
			}
			foundation.waitforElementToDisappear(PrintGroupLists.TXT_SPINNER_MSG,Constants.SHORT_TIME);
	
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	public By getrecords(String result) {
		return By.xpath("//td[text()='" + result + "']");
	}
	
	
}
