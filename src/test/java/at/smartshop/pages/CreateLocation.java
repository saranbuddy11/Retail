package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Browser;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class CreateLocation {
	private Foundation foundation = new Foundation();
	public Browser browser = new Browser();
	public Login login = new Login();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();

	public static final By INPUT_NAME = By.id("name");
	public static final By DPD_TIME_ZONE = By.id("timezone");
	public static final By DPD_TYPE = By.xpath("//*[@id='type-id']");
	public static final By DATE_PICKER_TOP_OFF = By.xpath("//input[@name='topoffsubsidystartdate' and @id='date']");
	public static final By DATE_PICKER_ROLL_OVER = By.xpath("//input[@name='rolloversubsidydate' and @id='date']");
	public static final By LBL_CREATE = By.id("Location Create");

	/**
	 * Verify the Create Field in Location Creation Page
	 * 
	 * @param name
	 * @param time
	 * @param type
	 */
	public void verifyCreateField(String name, String time, String type) {
		foundation.click(LocationList.BTN_CREATE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_CREATE));
		textBox.enterText(INPUT_NAME, name);
		dropDown.selectItem(DPD_TIME_ZONE, time, Constants.VALUE);
		dropDown.selectItem(DPD_TYPE, type, Constants.VALUE);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_MULTI_TAX_REPORT));
		CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_GMA_SUBSIDY));
	}
}
