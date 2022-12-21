package at.smartshop.sos.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.TextBox;

public class LoadProductPricing extends Factory{

	Dropdown dropDown = new Dropdown();
	TextBox textBox = new TextBox();
	Radio radio = new Radio();
	Foundation foundation = new Foundation();

	
	public static final By BTN_CHOOSE_FILE = By.id("myfile");
	public static final By TXT_LOCATION_NUMBER= By.id("locnumber");
	public static final By BTN_SUBMIT= By.id("submitBtn");
	public static final By LBL_UPDATE_PARAMETER= By.xpath("//div//ul[@class='breadcrumb']");
	public static final By BTN_OK= By.id("ok-btn");
	public static final By TXT_MSG= By.xpath("//div//p");
	
	public final String SHEET = "Sheet1";
	


}
