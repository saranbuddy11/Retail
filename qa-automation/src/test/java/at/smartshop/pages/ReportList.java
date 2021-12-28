package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class ReportList extends Factory {
	
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();

	private static final By TXT_SEARCH = By.id("Search");
	public static final By DPD_DATE = By.id("reportrange1");
	private static final By GRID_SCHEDULED_REPORT = By.xpath("//div[@class='ranges']//ul");
	private static final By DPD_DATE_OPTIONS = By.xpath("//div[@class='ranges']//ul//li");
	private static final By DPD_LOCATIONS = By.xpath("//input[@placeholder='Select...']");
	private static final By DPD_LOCATION_LIST = By.cssSelector("span.select2-results > #select2-locdt-results");
	public static final By BTN_RUN_REPORT = By.id("run");
	public static final By DPD_GROUP_BY = By.id("rpt-group-by");
	public static final By DPD_ORG = By.cssSelector("#orgdt + span > span > span > ul");

	public void selectReport(String reportName) {
		try {
			foundation.scrollIntoViewElement(TXT_SEARCH);
//			foundation.objectFocus(TXT_SEARCH);
			textBox.enterTextOnFocus(TXT_SEARCH, reportName);
			WebElement object = getDriver().findElement(By.xpath("//div[@class='currentReport'][contains(text(),'" + reportName + "')]"));
			Actions builder = new Actions(getDriver());
			builder.moveToElement(object).build();
			object.click();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void selectDate(String optionName) {
		try {
			foundation.waitforElement(DPD_DATE, 1);
			foundation.click(DPD_DATE);
			WebElement editerGrid = getDriver().findElement(GRID_SCHEDULED_REPORT);
			foundation.waitforElement(DPD_DATE_OPTIONS, Constants.EXTRA_LONG_TIME);
			List<WebElement> dateOptions = editerGrid.findElements(DPD_DATE_OPTIONS);			
			for (WebElement dateOption : dateOptions) {
				if (dateOption.getText().equals(optionName)) {
				foundation.waitforElement(GRID_SCHEDULED_REPORT, Constants.EXTRA_LONG_TIME);					
					dateOption.click();
					break;
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void selectLocation(String locationName) {
		try {
			foundation.click(DPD_LOCATIONS);
			textBox.enterText(DPD_LOCATIONS, locationName);
			foundation.click(DPD_LOCATION_LIST);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}
	
	public void selectOrg(String orgName) {
		try {
			foundation.click(DPD_ORG);
			foundation.click(By.xpath("//ul[@id = 'select2-orgdt-results']/li[text()='" + orgName +"']"));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

}
