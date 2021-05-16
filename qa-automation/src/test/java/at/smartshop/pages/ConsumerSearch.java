package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class ConsumerSearch extends Factory{
	private TextBox textBox = new TextBox();
	private Dropdown dropdown = new Dropdown();
	private Foundation foundation = new Foundation();
	
	private static final By DPD_LOCATION = By.id("loc-dropdown");
	private static final By DPD_STATUS = By.id("isdisabled");
	private static final By DPD_SEARCH_BY = By.id("searchBy");
	private static final By TXT_SEARCH = By.id("search");
	private static final By BTN_GO = By.id("findBtn");
	public static final By TBL_CONSUMERS = By.id("consumerdt");
	public static final By TXT_BALANCE=By.xpath("//table//tbody//tr[@class='odd']//td[5]");
	public static final By TXT_CONSUMER_NAME=By.xpath("//table//tbody//tr[@class='odd']//td[3]//a");
	public static final By BTN_ADJUST=By.xpath("//a[text()='Adjust']");
	public static final By TXT_BALANCE_NUM = By.id("balNum");

	public void enterSearchFields(String searchBy, String search, String locationName, String status) {
		try {
			dropdown.selectItem(DPD_SEARCH_BY, searchBy, Constants.TEXT);
			textBox.enterText(TXT_SEARCH, search);
			dropdown.selectItem(DPD_LOCATION, locationName, Constants.TEXT);
			dropdown.selectItem(DPD_STATUS, status, Constants.TEXT);
			foundation.click(BTN_GO);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

	public void clickTableCell(String consumerName) {
		try {
			By consumerFirstName = By.xpath("//table[@id='consumerdt']//tbody//tr//td//a[text()='"+consumerName+"']");
			foundation.click(consumerFirstName);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
