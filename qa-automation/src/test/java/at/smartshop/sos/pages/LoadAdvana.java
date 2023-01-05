package at.smartshop.sos.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.files.Excel;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class LoadAdvana extends Factory {

	Dropdown dropDown = new Dropdown();
	TextBox textBox = new TextBox();
	Radio radio = new Radio();
	Foundation foundation = new Foundation();
    Excel excel = new Excel();
	
	public static final By LBL_ADVANA = By.xpath("//ul[contains(text(),'Advana')]");
	public static final By DPD_ACTION= By.id("action");
	public static final By BTN_IMAGE_FILE = By.id("image");
	public static final By BTN_CHOOSE_FILE = By.id("file");
	public static final By TXT_NAME = By.id("commercialName");
	public static final By DPD_ADVANA_COMMERCIAL= By.id("advanaCommercial");
	public static final By SELECT_START_DATE= By.id("startdate");
	public static final By SELECT_END_DATE= By.id("enddate");
	public static final By BTN_CANCEL= By.id("cancelBtn");
	public static final By BTN_SAVE= By.id("saveBtn");
	public static final By GET_MSG= By.xpath("//div[@id='process-status']/p/b");

	
	
	public final String SHEET = "Sheet1";
	
	/**
	 * Add Home Commercial and Add image and location file with commercial name
	 * 
	 * @param action
	 * @param commercialName
	 * @param iterator
	 * @param requiredstring
	 * @param option
	 * @param date
	 * @param msg
	 * 
	 */
	public void addHomeCommercial(String action,String commercialName,String iterator, String requiredString,String option,String date) {
	dropDown.selectItem(LoadAdvana.DPD_ACTION,action, Constants.TEXT);
	textBox.enterText(LoadAdvana.TXT_NAME,commercialName);
	textBox.enterText(LoadAdvana.BTN_IMAGE_FILE, FilePath.IMAGE_PNG_PATH);
	
	excel.writeToExcel(FilePath.HOME_COMMERCIAL_TEMPLATE,SHEET,iterator, requiredString);
	textBox.enterText(LoadAdvana.BTN_CHOOSE_FILE, FilePath.HOME_COMMERCIAL_TEMPLATE);
	if(!foundation.getText(LoadAdvana.DPD_ADVANA_COMMERCIAL).equals(option)) {
		dropDown.selectItem(LoadAdvana.DPD_ADVANA_COMMERCIAL, option, Constants.TEXT);
	}
	foundation.click(LoadAdvana.SELECT_START_DATE);
	foundation.threadWait(3);
	textBox.enterText(LoadAdvana.SELECT_START_DATE, date);
	foundation.threadWait(3);
	textBox.enterText(LoadAdvana.SELECT_END_DATE, date);
	foundation.click(LoadAdvana.BTN_SAVE);
	foundation.threadWait(5);
}
	
	/**
	 * Add image and location file with commercial name
	 * 
	 * @param action
	 * @param iterator
	 * @param requiredstring
	 * @param msg
	 * 
	 */
	public void removeHomeCommercial(String action,String iterator, String requiredString,String msg) {
	dropDown.selectItem(LoadAdvana.DPD_ACTION,action, Constants.TEXT);
	textBox.enterText(LoadAdvana.BTN_IMAGE_FILE, FilePath.IMAGE_PNG_PATH);
	excel.writeToExcel(FilePath.HOME_COMMERCIAL_TEMPLATE,SHEET,iterator, requiredString);
	textBox.enterText(LoadAdvana.BTN_CHOOSE_FILE, FilePath.HOME_COMMERCIAL_TEMPLATE);
	foundation.click(LoadAdvana.BTN_SAVE);
	foundation.threadWait(5);
    CustomisedAssert.assertTrue(foundation.getText(LoadAdvana.GET_MSG).contains(msg));
    }

}

