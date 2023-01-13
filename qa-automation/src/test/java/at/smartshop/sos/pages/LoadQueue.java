package at.smartshop.sos.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
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
	public static final By TBL_HEADER = By.xpath("//thead//th//span[@class='ui-iggrid-headertext']");
	public static final By LBL_SHOW_RECORD = By.xpath("//div[@class='ui-iggrid-results']");
	public static final By LBL_RECORDS_COUNT = By.id("queueListGrid_pager_label");
	public static final By LBL_PAGE_NAVIGATION = By.xpath("//div[@class='ui-iggrid-paging']");
	public static final By CLICK_TYPE= By.xpath("//td[@aria-describedby='queueListGrid_type']");
	public static final By DPD_SHOW_RECORD = By.xpath("/span[@id='queueListGrid_editor']");
	public static final By TBL_REQUIRED_DATA = By.xpath("(//tbody[@class='ui-widget-content ui-iggrid-tablebody ui-ig-record ui-iggrid-record']//tr)[1]//td");
	public static final By GET_TIME = By.xpath("//td[@aria-describedby='queueListGrid_time']");
	public static final By PAGE_NAVIGATION = By.xpath("//li[@title='Page 1']");
	public static final By SHOW_COUNT = By.xpath("//div[@class='ui-icon ui-icon-triangle-1-s ui-igedit-buttonimage']");
	public static final By DPD_SHOW_COUNT = By.id("queueListGrid_editorEditingInput");

	public final String SHEET = "Sheet1";
	
	
}

