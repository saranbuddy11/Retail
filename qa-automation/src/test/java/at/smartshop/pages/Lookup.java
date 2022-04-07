package at.smartshop.pages;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Numbers;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNationalAccounts;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class Lookup extends Factory{

	public static final By TXT_LOOKUP_HEADING= By.id("Lookup List");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By TXT_CREATE_HEADING = By.id("Lookup Create");
	public static final By BTN_SAVE = By.id("saveBtn");
//	public static final By TXT_TYPE_ERROR = By.id("type-error");
//	public static final By TXT_DESC_ERROR = By.id("description-error");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By TXTBX_TYPE = By.id("type");
	public static final By TXTBX_KEYSTR = By.id("keystr");
	public static final By TXTBX_NAME = By.id("name");
	
	public static final By TXT_SPINNER_MSG = By.className("humane");
//	public static final By TXT_GRID = By.xpath("//td[@class=' sorting_1']//span");
//	public static final By TXT_RECORD = By.id("dt_info");
	public static final By TXT_SHOW_HEADING = By.id("Lookup Show");
	public static final By LBL_INFO = By.xpath("//div[@id='dt_info']");
	public static final By TXT_SEARCH_FILTER = By.xpath("//div[@id='dt_filter']//input");
	
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Table table = new Table();
	private Dropdown dropDown = new Dropdown();
	
	public void createLookup(String lookup_Type, String keystr_Field, String desc_Field) {
		try {
			dropDown.selectItem(TXTBX_TYPE, lookup_Type, Constants.TEXT);
			textBox.enterText(TXTBX_KEYSTR, keystr_Field);
			textBox.enterText(TXTBX_NAME, desc_Field);	
		}catch (Throwable exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
	}

	public void updateLookup(String existing_lookup, String validating_record,String lookup_Show_Page,String updated_lookup) {
		try {
			textBox.enterText(Lookup.TXT_SEARCH_FILTER,existing_lookup);
			String actualData = foundation.getText(By.xpath("//td[normalize-space()='"+existing_lookup+"']"));
			
			CustomisedAssert.assertEquals(actualData, existing_lookup);
			
			String resultText = foundation.getText(LBL_INFO);
			CustomisedAssert.assertTrue(resultText.contains(validating_record));

			//Update the details
			table.selectRow(existing_lookup);
			CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_SHOW_HEADING),lookup_Show_Page);
			textBox.enterText(TXTBX_KEYSTR, updated_lookup);
			foundation.click(Lookup.BTN_SAVE);
			foundation.waitforElementToDisappear(TXT_SPINNER_MSG,Constants.SHORT_TIME);
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
}
