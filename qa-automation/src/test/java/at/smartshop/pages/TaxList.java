package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.generic.DateAndTime;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class TaxList {

	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown=new Dropdown();
	private NavigationBar navigationBar=new NavigationBar();
	private DateAndTime dateAndTime = new DateAndTime();

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
	public static final By TBL_TAX_GRID = By.id("dataGrid");
	public static final By TBL_ROW = By.xpath("//*[@id='dataGrid']/tbody/tr");
	public static final By TBL_ROW_ENDDATE = By.xpath("//td[@aria-describedby='dataGrid_enddate']");

	public void selectDate(String text) {
		try {
			By rowData = By.xpath("//tr//td[@class= 'day  active' and text() = '" + text + "']");
			foundation.click(rowData);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public double getTaxAmount(String taxRate, String price) {
		double tax = Double.parseDouble(taxRate);

		double taxPercent = (double) (tax) / 100;
		double taxAmount = taxPercent * Double.parseDouble(taxRate);
		taxAmount = Math.round(taxAmount * 100.0) / 100.0;
		return taxAmount;

	}
	
	public boolean updateTaxRate(String taxName, String taxRate1,String taxRate2,String taxRate3,String taxRate4) {
		boolean taxRateUpdated=false;
		//select admin>tax, select the tax name and navigate to tax summary page
		navigationBar.navigateToMenuItem("Admin#Tax");
		textBox.enterText(LBL_SEARCH, taxName);
		foundation.click(By.xpath("//tr//td[text() = '" + taxName + "']"));
		
		//get initial row count
		int totalRowIntial=foundation.getSizeofListElement(TBL_ROW);
		
		//add new tax rate/update rate
		foundation.click(BTN_ADDRATE);
		textBox.enterText(TXT_RATE_1, taxRate1);
		textBox.enterText(TXT_RATE_2, taxRate2);
		textBox.enterText(TXT_RATE_3, taxRate3);
		textBox.enterText(TXT_RATE_4, taxRate4);
		selectDate(getCurrentDateForCalender());
		textBox.enterText(TXT_EFFECTIVETIME, Constants.TIME_ZERO);
		foundation.click(BTN_SAVE);
		foundation.waitforElement(TBL_ROW, Constants.SHORT_TIME);
		
		// get final row count- it should be increased by 1 
		// total number of final date rows should be less than 1 of total row count
		int totalRowUpdated = foundation.getSizeofListElement(TBL_ROW);
		int totalEndDate = foundation.getSizeofListElement(TBL_ROW_ENDDATE);
		if (totalRowIntial == (totalRowUpdated + 1) && totalEndDate == totalRowUpdated - 1) {
			taxRateUpdated = true;
		}
		return taxRateUpdated;
	}
	
	public String getCurrentDateForCalender() {
		int date = Integer.parseInt(dateAndTime.getDateAndTime(Constants.REGEX_DD, Constants.TIME_ZONE_INDIA));
		String currentDay;
		if (date < 10) {
			currentDay = String.valueOf(date).substring(0, 1);
		} else {
			currentDay = String.valueOf(date);
		}
		return currentDay;
	}

}
