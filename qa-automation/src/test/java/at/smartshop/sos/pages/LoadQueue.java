package at.smartshop.sos.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.TextBox;

public class LoadQueue extends Factory {

	Dropdown dropDown = new Dropdown();
	TextBox textBox = new TextBox();
	Radio radio = new Radio();
	Foundation foundation = new Foundation();

	public static final By LBL_QUEUE = By.xpath("//div[contains(text(),'Queued Processes')]");
	public static final By TBL_DATA = By.xpath("(//tbody[@class='ui-widget-content ui-iggrid-tablebody ui-ig-record ui-iggrid-record']//tr)[1]");
	public static final By TXT_SEARCH = By.id("queueFilterType");

	public final String SHEET = "Sheet1";
}

