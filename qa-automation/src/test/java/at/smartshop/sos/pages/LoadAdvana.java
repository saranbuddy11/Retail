package at.smartshop.sos.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.TextBox;

public class LoadAdvana extends Factory {

	Dropdown dropDown = new Dropdown();
	TextBox textBox = new TextBox();
	Radio radio = new Radio();
	Foundation foundation = new Foundation();

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
	public static final By SUCESS_MSG= By.xpath("//div[@id='process-status']/p/b");
	
	
	public final String SHEET = "Sheet1";
}

