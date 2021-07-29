package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;

public class TaxList {

	private Foundation foundation = new Foundation();

	public static final By BTN_NEW = By.cssSelector("button#newBtn");
	public static final By TXT_DESCRIPTION = By.cssSelector("input#description");
	public static final By BTN_SAVE = By.cssSelector("button#saveBtn");
	public static final By BTN_ADDRATE = By.cssSelector("button#addrateBtn");
	public static final By TXT_RATE_1 = By.cssSelector("input#rate");
	public static final By TXT_RATE_2 = By.cssSelector("input#rate2");
	public static final By TXT_RATE_3 = By.cssSelector("input#rate3");
	public static final By TXT_RATE_4 = By.cssSelector("input#rate4");
	public static final By LBL_CALENDER = By.cssSelector("span.add-on.calender");
	public static final By TXT_EFFECTIVETIME = By.cssSelector("input#starttime");
	public static final By LBL_TAXRATE_SAVE = By.cssSelector("a#modalsave");
	public static final By LBL_SEARCH = By.cssSelector("#dt_filter > label > input[type=text]");

	public void selectDate(String text) {
		try {
			By rowData = By.xpath("//tr//td[@class= 'day  active' and text() = '" + text + "']");
			foundation.click(rowData);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public float getTaxAmount(String taxRate) {
		float tax = Float.parseFloat(taxRate);

		float taxAmount = (float) (tax) / 100;
		return taxAmount;

	}

}
