package at.smartshop.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.files.Excel;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNReportList;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;

public class ReportList extends Factory {

	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private Excel excel = new Excel();

	private static final By TXT_SEARCH = By.id("Search");
	public static final By DPD_DATE = By.id("reportrange1");
	private static final By GRID_SCHEDULED_REPORT = By.xpath("//div[@class='ranges']//ul");
	private static final By DPD_DATE_OPTIONS = By.xpath("//div[@class='ranges']//ul//li");
//	private static final By DPD_LOCATIONS = By.cssSelector("div.span12.m-0 > span > span.selection > span");
	private static final By DPD_LOCATIONS = By.xpath("//input[@placeholder='Select...']");
	private static final By DPD_LOCATION_LIST = By.cssSelector("span.select2-results > #select2-locdt-results");
	private static final By FIRST_LOCATION_FROM_LIST = By.cssSelector("span.select2-results > #select2-locdt-results > li");
	public static final By BTN_RUN_REPORT = By.id("run");
	public static final By DPD_GROUP_BY = By.id("rpt-group-by");
	public static final By DPD_ORG = By.cssSelector("#orgdt + span > span > span > ul");
	public final By TO_EXCEL_BUTTON = By.id("runexcel");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	private static final By DPD_LOCATIONS_SECONDTYPE = By.xpath("//span[@title='Select...']");
	private static final By DPD_SERACH_LOCATIONS_SECONDTYPE = By.xpath("//span[@class='select2-container select2-container--default select2-container--open']//span//span//input[@role='searchbox']");
	private static final By DPD_LOCATION_LIST_SECONDTYPE  = By.xpath("//span[@class='select2-results']//ul[@role='listbox']");

		
	/*
	 * public void logInToADM() { try { browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); } catch (Exception exc) {
	 * Assert.fail(exc.toString()); } }
	 */

	public void runReport(Map<String, String> rstNavigationMenuData, Map<String, String> rstReportListData) {
		try {
			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Select the Report Date range and Location
			selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
			selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
			selectLocation(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ReportList.BTN_RUN_REPORT);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void selectReport(String reportName) {
		try {
			textBox.enterText(TXT_SEARCH, reportName);
			WebElement object = getDriver()
					.findElement(By.xpath("//div[@class='currentReport'][contains(text(),'" + reportName + "')]"));
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
	
	public void selectLocationForSecondTypeDropdown(String locationName) {
		try {
			foundation.click(DPD_LOCATIONS_SECONDTYPE);
			textBox.enterText(DPD_SERACH_LOCATIONS_SECONDTYPE, locationName);
			foundation.click(DPD_LOCATION_LIST_SECONDTYPE);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	
	public String firstLocationName() {
			String text = null;
			try {
				foundation.click(DPD_LOCATIONS);
				List<WebElement> ListElement = getDriver().findElements(FIRST_LOCATION_FROM_LIST);
				 text = ListElement.get(1).getText();
				 foundation.click(DPD_LOCATIONS);
				ExtFactory.getInstance().getExtent().log(Status.INFO, "got the text of Fisrt location Name as " + text + ".");
			} catch (Exception exc) {
				Assert.fail(exc.toString());
			}
			return text;
		}
	
	public void selectOrg(String orgName) {
		try {
			foundation.click(DPD_ORG);
			foundation.click(By.xpath("//ul[@id = 'select2-orgdt-results']/li[text()='" + orgName + "']"));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}

	}

	public String getTodaysDate(String reportFormat) {
		String reportDate = null;
		try {
//			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			DateTimeFormatter reqFormat = DateTimeFormatter.ofPattern(reportFormat);
			LocalDateTime tranDate = LocalDateTime.now();
//			String transDate = tranDate.format(dateFormat);
			reportDate = tranDate.format(reqFormat);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return reportDate;
	}

	public void clickOnToExcelButton(By object) {
		try {
			foundation.waitforClikableElement(object, Constants.EXTRA_LONG_TIME);
			foundation.click(object);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyTheFileWithFullName(String reportName, String fileName) {
		try {

			boolean fileExists = foundation.isFileExists(FilePath.reportFilePath(fileName));

//			List<String> ReportName = Arrays.asList(reportName.split(Constants.DELIMITER_HASH));
//			System.out.println(FilePath.reportFilePath(fileName));

			excel.verifyFirstCellData(reportName, FilePath.reportFilePath(fileName), 0);

			if (fileExists == false) {
				foundation.deleteFile(FilePath.reportFilePath(fileName));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyTheFileContainsNameWithDate(String reportName, String fileName) {
		try {
			boolean fileExists = foundation.isFileExists(FilePath.reportFilePathWithDate(reportName, fileName));

//			List<String> ReportName = Arrays.asList(reportName.split(Constants.DELIMITER_HASH));
//			excel.verifyExcelData(ReportName, FilePath.reportFilePathWithDate(reportName, fileName), 0);

			excel.verifyFirstCellData(reportName, FilePath.reportFilePathWithDate(reportName, fileName), 0);

			if (fileExists == false) {
				foundation.deleteFile(FilePath.reportFilePathWithDate(reportName, fileName));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public boolean isElementPresent(By object) {
		boolean present = false;
		try {
			getDriver().findElement(object);
			present = true;
		} catch (NoSuchElementException e) {
			present = false;
		}
		return present;
	}

	public void checkForDataAvailabilyInResultTable() {
		try {
//			foundation.waitforElement(NO_DATA_AVAILABLE_IN_TABLE, 5);
//			System.out.println("Waited for element***********");
			
//		   noDataAvailableInTable = getDriver().findElement(NO_DATA_AVAILABLE_IN_TABLE);
			if(foundation.isDisplayed(By.xpath("//tbody//tr[@class='odd'][1]"))){
				
				if(foundation.isDisplayed(NO_DATA_AVAILABLE_IN_TABLE)) {
					System.out.println("***NOOOOOOOO data available ***********");
					Assert.fail("Failed************1");
				}else {
					System.out.println("***data available ***********");
				}
				
			}else if(foundation.isDisplayed(By.cssSelector("#dataGrid > tbody > tr:nth-child(1)"))){
				if(foundation.isDisplayed(NO_DATA_AVAILABLE_IN_TABLE)) {
					System.out.println("***NOOOOOOOO data available ***********");
					Assert.fail("Failed************2");
				}else {
					System.out.println("***data available ***********");
				}		
			}
			else if(foundation.isDisplayed(By.cssSelector("#dataGridPayrollDeductDetails > tbody > tr:nth-child(1)"))){
				if(foundation.isDisplayed(NO_DATA_AVAILABLE_IN_TABLE)) {
					System.out.println("***NOOOOOOOO data available ***********");
					Assert.fail("Failed************2");
				}else {
					System.out.println("***data available ***********");
				}		
			}else if(foundation.isDisplayed(By.cssSelector("#payrollGrid > tbody > tr:nth-child(1) "))){
				if(foundation.isDisplayed(NO_DATA_AVAILABLE_IN_TABLE)) {
					System.out.println("***NOOOOOOOO data available ***********");
					Assert.fail("Failed************2");
				}else {
					System.out.println("***data available ***********");
				}		
			}else if(foundation.isDisplayed( By.xpath("//span[@class='ui-icon ui-iggrid-expandbutton ui-icon-plus']"))){
				System.out.println("*** data available ***********");
				ExtFactory.getInstance().getExtent().log(Status.INFO, "waited for element [ " + NO_DATA_AVAILABLE_IN_TABLE + " ] and the object is visible");
			}else {
				System.out.println("***NOOOOOOOO data available ***********");
				Assert.fail("Failed************3");
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
