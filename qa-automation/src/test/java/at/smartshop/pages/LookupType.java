package at.smartshop.pages;

import static org.testng.Assert.assertTrue;

import java.util.List;

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
import at.smartshop.keys.Constants;

public class LookupType extends Factory{

	public static final By TXT_LOOKUP_HEADING= By.id("Lookup Type List");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By TXT_CREATE_HEADING = By.id("Lookup Type Create");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By TXT_TYPE_ERROR = By.id("type-error");
	public static final By TXT_DESC_ERROR = By.id("description-error");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By TXTBX_TYPE = By.id("type");
	public static final By TXTBX_DESC = By.id("description");
	public static final By TXT_SPINNER_MSG = By.className("humane");
	public static final By TXT_GRID = By.xpath("//td[@class=' sorting_1']//span");
	public static final By TXT_RECORD = By.id("dt_info");
	public static final By TXT_SHOW_HEADING = By.id("Lookup Type Show");
	public static final By LBL_INFO = By.xpath("//div[@id='dt_info']");
	public static final By TXT_SEARCH_FILTER = By.xpath("//div[@id='dt_filter']//input");
	
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Table table = new Table();
	
	public void createLookup(String type_Field, String desc_Field) {
		try {
			textBox.enterText(TXTBX_TYPE, type_Field);
			textBox.enterText(TXTBX_DESC, desc_Field);	
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateLookup(String existing_lookup, String validating_record,String lookup_Show_Page,String updated_lookup) {
		try {
			textBox.enterText(LookupType.TXT_SEARCH_FILTER,existing_lookup);
			String actualData = foundation.getText(By.xpath("//span[text()='"+existing_lookup+"']"));
			CustomisedAssert.assertEquals(actualData, existing_lookup);
			
			String resultText = foundation.getText(LBL_INFO);
			CustomisedAssert.assertTrue(resultText.contains(validating_record));
			
			//Update the details
			table.selectRow(existing_lookup);
			CustomisedAssert.assertTrue(foundation.isDisplayed(TXT_SHOW_HEADING),lookup_Show_Page);
			textBox.enterText(TXTBX_TYPE, updated_lookup);
			foundation.click(LookupType.BTN_SAVE);
			foundation.waitforElementToDisappear(TXT_SPINNER_MSG,Constants.SHORT_TIME);
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
}
