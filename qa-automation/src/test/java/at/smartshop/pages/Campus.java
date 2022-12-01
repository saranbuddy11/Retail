package at.smartshop.pages;

import java.time.Month;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class Campus extends Factory {
	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox();
	private Dropdown dropdown = new Dropdown();

	public static final By LBL_CAMPUSLIST_HEADING = By.id("Campus List");
	public static final By BTN_CREATE_NEW = By.id("newBtn");
	public static final By LBL_CAMPUSSAVE_HEADING = By.id("Campus Save");
	public static final By LBL_CAMPUSSHOW_HEADING = By.id("Campus Show");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By DRP_PAYROLL = By.id("payrolldeduct");
	public static final By TXT_PAYROLL_EMAIL_ERROR = By.id("payrolldeductemail-error");
	public static final By TXT_NAME_ERROR = By.id("name-error");
	public static final By TXT_GROUPNAME_ERROR = By.id("groupname0-error");
	public static final By TXTBX_NAME = By.id("name");
	public static final By TXTBX_LEFT = By.id("mainform");
	public static final By ERRORBOX = By.className("error");
	public static final By SEARCH_BOX = By.xpath("//input[@aria-controls='dt']");
	public static final By SELECT_GRID = By.xpath("//td[@class=' sorting_1']");
	public static final By DRP_PAYCYCLE = By.id("frequency0");
	public static final By TXT_GROUP_NAME = By.id("groupname0");
	public static final By TXT_SPEND_LIMIT = By.id("spendlimit0");
	public static final By LBL_LIMIT_ERROR = By.id("spendlimit0-error");
	public static final By BTN_ADD_TO_CAMPUS = By.id("addLocation");
	public static final By BTN_ADD = By.xpath("//button[@class='btn-mini pull-right']//i");
	public static final By USE_PAYROLL_DEDUCT = By.id("payrolldeductstreamlinemode");
	public static final By PD_COMPLETE_PURCHASE = By.id("payrolldeductautocomplete");
	public static final By ALLOW_FOR_OFFLINE_PD = By.id("allowofflinepd");
	public static final By START_DATE_PICKER = By.xpath("//input[@name='payrolldeductstartdate']");
	public static final By DPD_PAY_CYCLE=By.id("frequency1");
	public static final By GROUP_NAME=By.id("groupname1");
	public static final By SPEED_LIMIT=By.id("spendlimit1");
	public static final By PDE_DATE_PICKER_NEXT = By
			.xpath("/html/body/div[5]/div[1]/table/thead/tr[1]/th[3]");
	
	public By objectPdeCalendarMonth(String month) {
		return By.xpath("/html/body/div[5]/div[1]/table/thead/tr[1]/th[2][contains(text(),'" + month +"')]");
	}
	
	public By objectPdeCalendarDay(String day) {
		return By.xpath("/html/body/div[5]/div[1]/table/tbody/tr/td[text()='"+day +"' and @class='day  active']");
	}

	private Foundation foundation = new Foundation();

	public void enterInvalidData(String value, String error) {
		WebElement textboxLeft = getDriver().findElement(TXTBX_LEFT);
		List<WebElement> textboxes = textboxLeft.findElements(By.className("validstr"));
		List<WebElement> errors = textboxLeft.findElements(By.className("error"));
		int Size = textboxes.size();
		int errorsize = errors.size();
		for (int i = 0; i < Size; i++) {
			textboxes.get(i).sendKeys(value);
			foundation.click(BTN_SAVE);
			for (int j = 0; j < errorsize; j++) {
				errors.get(j).getText().equals(error);
			}
		}
	}

	public static String getMonthName(int monthIndex) {
		if (monthIndex > 12) {
			monthIndex = monthIndex - 12;
		}
		Month name = Month.of(monthIndex);
		String result = name.toString().toLowerCase();
		String output = result.substring(0, 1).toUpperCase() + result.substring(1);
		return output;
	}

	/**
	 * Select Date in pde date Picker
	 * @param value
	 */
	public void selectPdeDateInDatePicker(String value) {
		String dateArray[] = value.split("/");
		String date = dateArray[1].replaceAll(Constants.REMOVE_LEADING_ZERO, "");
		int month = Integer.parseInt(dateArray[0]);
		String monthName = getMonthName(month);
		foundation.threadWait(Constants.ONE_SECOND);
		if (foundation.isDisplayed(objectPdeCalendarMonth(monthName))) {
			foundation.click(objectPdeCalendarDay(date));
		} else {
			foundation.click(PDE_DATE_PICKER_NEXT);
			foundation.waitforElement(objectPdeCalendarMonth(monthName), Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(objectPdeCalendarMonth(monthName)));
			foundation.click(objectPdeCalendarDay(date));
		}
	}
	
	/**
	 * search with created campus and adjust pde balance
	 * @param campusname
	 * @param onandoff
	 * @param currentDate
	 * @param dpdpaycycle
	 * @param groupname
	 * @param speedlimit
	 */
	public void searchWithCreatedCampusAndAdjustPDEBalanc(String campusname,String onandoff, String currentDate,String dpdpaycycle,String groupname,String speedlimit) {
		CustomisedAssert.assertTrue(foundation.isDisplayed(Campus.SEARCH_BOX));
		textBox.enterText(Campus.SEARCH_BOX, campusname);
		foundation.threadWait(Constants.THREE_SECOND);
		CustomisedAssert.assertTrue(foundation.isDisplayed(Campus.SELECT_GRID));
		foundation.click(Campus.SELECT_GRID);
		foundation.waitforElementToBeVisible(Campus.LBL_CAMPUSSHOW_HEADING, Constants.THREE_SECOND);
		String text = foundation.getText(Campus.DRP_PAYROLL);
		CustomisedAssert.assertTrue(text.contains(onandoff));
		foundation.waitforElementToBeVisible(Campus.PD_COMPLETE_PURCHASE, Constants.THREE_SECOND);
		checkBox.isChecked(Campus.PD_COMPLETE_PURCHASE);
		foundation.waitforElementToBeVisible(Campus.USE_PAYROLL_DEDUCT, Constants.THREE_SECOND);
		checkBox.isChecked(Campus.USE_PAYROLL_DEDUCT);
		foundation.waitforElementToBeVisible(Campus.ALLOW_FOR_OFFLINE_PD, Constants.THREE_SECOND);
		checkBox.isChecked(Campus.ALLOW_FOR_OFFLINE_PD);
		foundation.click(Campus.START_DATE_PICKER);
		selectPdeDateInDatePicker(currentDate);
		dropdown.selectItem(Campus.DPD_PAY_CYCLE, dpdpaycycle, Constants.TEXT);
		textBox.enterText(Campus.GROUP_NAME, groupname);
		textBox.enterText(Campus.SPEED_LIMIT, speedlimit);
		foundation.waitforElementToBeVisible(Campus.BTN_SAVE, Constants.THREE_SECOND);
		foundation.click(Campus.BTN_SAVE);
		foundation.threadWait(Constants.THREE_SECOND);
	}
}
