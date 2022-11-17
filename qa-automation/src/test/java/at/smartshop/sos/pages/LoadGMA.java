package at.smartshop.sos.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Radio;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class LoadGMA extends Factory {

	Dropdown dropDown = new Dropdown();
	TextBox textBox = new TextBox();
	Radio radio = new Radio();
	Foundation foundation = new Foundation();

	public static final By BTN_SUBMIT = By.id("submitBtn");
	public static final By LBL_SUCCESS = By.xpath("//td[contains(text(),'Number of Users successfully added')]");
	private static final By DPD_PIN_VALUE = By.id("pinvalueopt");
	private static final By DPD_START_BALANCE = By.id("startbalopt");
	private static final By DPD_LOCATION = By.id("location");
	private static final By TXT_FILE_NAME = By.name("myfile");
	private static final By RADIO_YES = By.id("delete-y");
	private static final By RADIO_NO = By.id("delete-n");
	private static final By LBL_RADIO_YES = By.xpath("//label[text()='Yes']");
	public static final By BTN_CONFIRM = By.xpath("//button[contains(text(),'CONFIRM')]");
	public static final By LBL_ERROR_MSG = By.xpath("//p[text()='Input file had errors. The users listed below were not loaded.']");
	public static final By LBL_TITLE_ERROR_PAGE= By.xpath("//li[text()='Load GMA User Results']");
	public static final By LBL_SUBTITLE_ERROR_PAGE = By.xpath("//h3[text()='User Load Error Results']");

	public final String SHEET = "Sheet1";

	public void gMAUser(String location, String pinValue, String startBalance, String filePath,
			String deleteProductStatus) {
		try {
			dropDown.selectItem(DPD_LOCATION, location, Constants.TEXT);
			dropDown.selectItem(DPD_PIN_VALUE, pinValue, Constants.TEXT);
			dropDown.selectItem(DPD_START_BALANCE, startBalance, Constants.TEXT);
			textBox.enterText(TXT_FILE_NAME, filePath);
			if (deleteProductStatus.equalsIgnoreCase(foundation.getText(LBL_RADIO_YES))) {
				radio.set(RADIO_YES);
				foundation.click(BTN_SUBMIT);
				foundation.click(BTN_CONFIRM);
			} else {
				radio.set(RADIO_NO);
				foundation.click(BTN_SUBMIT);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}
}