package at.smartshop.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.testng.Assert;
import at.framework.browser.Factory;
import at.framework.generic.DateAndTime;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;


public class ReportList extends Factory {
	TextBox textBox = new TextBox();
	Foundation foundation = new Foundation();
	DateAndTime dateAndTime = new DateAndTime();
	
	private static final By txtSearch = By.id("Search");
	private static final By dpdDate = By.id("reportrange1");
	private static final By gridScheduledReport = By.id("scheduled-report-grid_editor_list");
	private static final By dpdDateOptions = By.cssSelector("#scheduled-report-grid_editor_list + div > div.ranges > ul > li");
	private static final By dpdDateGrid = By.cssSelector("#scheduled-report-grid_editor_list + div > div.ranges > ul");
	private static final By dpdLocations = By.cssSelector("div.span12.m-0 > span > span.selection > span > ul > li > input");
	private static final By dpdLocationList = By.cssSelector("span.select2-results > #select2-locdt-results");
	public static final By btnRunReport = By.id("run");
	
	public void selectReport(String reportName) {
		try {
			textBox.enterText(txtSearch, reportName);
			WebElement object = getDriver().findElement(By.xpath("//div[@class='currentReport'][contains(text(),'"+reportName+"')]"));
			Actions builder = new Actions(getDriver());
			builder.moveToElement(object).build();
			object.click();
		} catch (Exception exc) {
		Assert.fail(exc.toString());
	}
	}

	public void selectDate(String optionName) {
		try {
			foundation.click(dpdDate);
			WebElement editerGrid = getDriver().findElement(gridScheduledReport);
			foundation.waitforElement(dpdDateOptions, 30000);
			List<WebElement> dateOptions = editerGrid.findElements(dpdDateOptions);
				for (WebElement dateOption : dateOptions) {
					if (dateOption.getText().equals(optionName)) {
						foundation.waitforElement(dpdDateGrid, 30000);
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
			foundation.click(dpdLocations);
			textBox.enterText(dpdLocations, locationName);
			foundation.click(dpdLocationList);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

}
