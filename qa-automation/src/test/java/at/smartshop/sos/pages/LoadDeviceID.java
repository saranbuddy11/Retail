package at.smartshop.sos.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.TextBox;

public class LoadDeviceID extends Factory{

	Dropdown dropDown = new Dropdown();
	TextBox textBox = new TextBox();
	Radio radio = new Radio();
	Foundation foundation = new Foundation();

	public static final By LBL_MIDDID = By.xpath("//li[text()='Load Middid Parameters']");
	public static final By DPD_PROCESSOR= By.xpath("//select[@id='processor']");
	public static final By BTN_CHOOSE_FILE = By.id("myfile");
	public static final By BTN_SUBMIT = By.id("submitBtn");
	public static final By LBL_ERROR_MSG = By.xpath("//li[text()='Middid List Errors']");
	public static final By TBL_ERROR_DATA = By.xpath("//tbody[@role='alert']//tr");
	public static final By TXT_SEARCH = By.xpath("//input[@type='text']");
	
	
	public By clickLocation(String text) {
		return By.xpath("//select[@id='processor']/option[text()='" + text + "']");
	}
	
	public final String SHEET = "Sheet1";
}
