package at.smartshop.sos.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.TextBox;

public class LoadProduct extends Factory{

	Dropdown dropDown = new Dropdown();
	TextBox textBox = new TextBox();
	Radio radio = new Radio();
	Foundation foundation = new Foundation();

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
	public static final By TXT_DELETE = By.id("delete-typed-input");
	public static final By BTN_DELETE = By.id("confirm-delete-btn");
	public static final By BTN_CANCEL = By.className("btn btn-secondary");
	public static final By LBL_PRODUCT_ERROR = By.xpath("//li[text()='Product List Errors']");
	
	
	public By clickLocation(String text) {
		return By.xpath("//select[@id='location']/option[text()='" + text + "']");
	}
	
	public final String SHEET = "Sheet1";
}
