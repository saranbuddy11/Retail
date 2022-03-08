package at.smartshop.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.files.Excel;
import at.framework.generic.CustomisedAssert;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;

public class ReportList extends Factory {

	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	private Dropdown dropdown = new Dropdown();
	private NavigationBar navigationBar = new NavigationBar();
	private Excel excel = new Excel();

	private static final By TXT_SEARCH = By.id("Search");
	public static final By DPD_DATE = By.id("reportrange1");
	private static final By BTN_PREVIOUS_MONTH = By.cssSelector("th.prev");
	private static final By SELECT_TODAY = By.xpath(
			"//td[@class='today active start-date active end-date available' or @class='today weekend active start-date active end-date available']");
	private static final By GRID_SCHEDULED_REPORT = By.xpath("//div[@class='ranges']//ul");
	private static final By DPD_DATE_OPTIONS = By.xpath("//div[@class='ranges']//ul//li");
	private static final By DPD_LOCATIONS = By.xpath("//input[@placeholder='Select...']");
	private static final By DPD_LOCATION_LIST = By.cssSelector("span.select2-results > #select2-locdt-results");
	private static final By DPD_CLIENT_AND_NATIONALACCOUNT_LIST = By
			.cssSelector("span.select2-results > #select2-national-account-results");
	private static final By FIRST_LOCATION_FROM_LIST = By
			.cssSelector("span.select2-results > #select2-locdt-results > li");
	public static final By BTN_RUN_REPORT = By.id("run");
	public static final By DPD_GROUP_BY = By.xpath("//select[@id='rpt-group-by']");
	public static final By DPD_ORG = By.cssSelector("#orgdt + span > span > span > ul");
	public static final By DPD_ORG_ON_FILTER = By
			.cssSelector("#org-container > dd > span > span.selection > span > ul > li");
//	public static final By DPD_ORG_ON_FILTER = By.xpath("//input[@placeholder='Select Org(s) to include']");
	public static final By DPD_LOCATION_ON_FILTER = By.xpath("//input[@placeholder='Select Location(s) to include']");
	public static final By DPD_LOC_ON_GROUPFILTER_ = By.cssSelector("#select2-locdt-container");
	public static final By DPD_FILTER_BY_GROUP = By.id("flt-group-by");
	public static final By DPD_FILTER = By.cssSelector("#add-filter-container > span > span.selection > span");
	public final By TO_EXCEL_BUTTON = By.xpath("//button[@id='runexcel']");
	public final By TO_EXCEL_EXPORTBUTTON = By.id("exportButton");
	private static final By NO_DATA_AVAILABLE_IN_TABLE = By.xpath("//td[@class='dataTables_empty']");
	private static final By DPD_LOCATIONS_SECONDTYPE = By.xpath("//span[@title='Select...']");
	private static final By DPD_SERACH_LOCATIONS_SECONDTYPE = By.xpath(
			"//span[@class='select2-container select2-container--default select2-container--open']//span//span//input[@role='searchbox']");
	private static final By DPD_LOCATION_LIST_SECONDTYPE = By
			.xpath("//span[@class='select2-results']//ul[@role='listbox']");

	/*
	 * public void logInToADM() { try { browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); } catch (Exception exc) {
	 * TestInfra.failWithScreenShot(exc.toString()); } }
	 */

//	public void runReport(Map<String, String> rstNavigationMenuData, Map<String, String> rstReportListData) {
//		try {
//			// Select Menu and Menu Item
//			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
//
//			// Select the Report Date range and Location
//			selectReport(rstReportListData.get(CNReportList.REPORT_NAME));
//			selectDate(rstReportListData.get(CNReportList.DATE_RANGE));
//			selectLocation(propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
//			foundation.click(ReportList.BTN_RUN_REPORT);
//
//		} catch (Exception exc) {
//			TestInfra.failWithScreenShot(exc.toString());
//		}
//	}

	public void selectReport(String reportName) {
		try {
			foundation.scrollIntoViewElement(TXT_SEARCH);
			textBox.enterTextOnFocus(TXT_SEARCH, reportName);
//			WebElement object = getDriver()
//					.findElement(By.xpath("//div[@class='currentReport'][starts-with(text(),'" + reportName + "')]"));
			WebElement object = getDriver().findElement(
					By.xpath("//div[@class='checkbox-row']//div[@class='currentReport'][normalize-space()='"
							+ reportName + "']"));
			Actions builder = new Actions(getDriver());
			builder.moveToElement(object).build();
			object.click();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectLocation(String locationName) {
		try {
			foundation.click(DPD_LOCATIONS);
			textBox.enterText(DPD_LOCATIONS, locationName);
			foundation.click(DPD_LOCATION_LIST);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectClientAndNationalAccount(String nationalAccountName) {
		try {
			foundation.click(DPD_LOCATIONS);
			textBox.enterText(DPD_LOCATIONS, nationalAccountName);
			foundation.click(DPD_CLIENT_AND_NATIONALACCOUNT_LIST);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectLocationForSecondTypeDropdown(String locationName) {
		try {
			foundation.click(DPD_LOCATIONS_SECONDTYPE);
			textBox.enterText(DPD_SERACH_LOCATIONS_SECONDTYPE, locationName);
			foundation.click(DPD_LOCATION_LIST_SECONDTYPE);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public String firstLocationName() {
		String text = null;
		try {
			foundation.click(DPD_LOCATIONS);
			List<WebElement> ListElement = getDriver().findElements(FIRST_LOCATION_FROM_LIST);
			text = ListElement.get(1).getText();
			foundation.click(DPD_LOCATIONS);
			ExtFactory.getInstance().getExtent().log(Status.INFO,
					"got the text of Fisrt location Name as " + text + ".");
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return text;
	}

	public void selectOrg(String orgName) {
		try {
			foundation.click(DPD_ORG);
			foundation.click(By.xpath("//ul[@id = 'select2-orgdt-results']/li[text()='" + orgName + "']"));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectFilter(String orgName) {
		try {
			foundation.click(DPD_FILTER);
			foundation.click(By.xpath("//ul[@id='select2-add-filter-select-results']/li[text()='" + orgName + "']"));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectAllOptionOfFilter() {
		try {
			foundation.click(DPD_FILTER);
			By filter = By.xpath("//ul[@id='select2-add-filter-select-results']//li");
			List<WebElement> items = getDriver().findElements(filter);

			for (int i = 1; i < items.size(); i++) {
				List<WebElement> items1 = getDriver().findElements(filter);
				for (int j = 1; j < 2; j++) {
					String itemText = items1.get(1).getText();
					items1.get(1).click();
					if (i == items.size() - 1) {
						break;
					}
					foundation.click(DPD_FILTER);
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectOrgOnFilter(String orgName) {
		try {
			foundation.objectClick(DPD_ORG_ON_FILTER);
			foundation.click(By.xpath("//ul[@id='select2-org-select-results']/li[text()='" + orgName + "']"));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectLocationOnFilter(String locationName) {
		try {
			foundation.objectClick(DPD_LOCATION_ON_FILTER);
//			By Text = By.xpath("//ul[@id='select2-location-select-results']//li[@aria-label='AutomationOrg']//li[text()='" + locationName + "']");
			textBox.enterText(DPD_LOCATION_ON_FILTER, "All");
//			foundation.scrollIntoViewElement(By.xpath("//ul[@id='select2-location-select-results']//li[@aria-label='AutomationOrg']//li[text()='" + locationName + "']"));
			foundation.click(By.xpath("//ul[@id='select2-location-select-results']//li"));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectFilterOption(String filterName, String type) {
		try {
			dropdown.selectItem(DPD_FILTER_BY_GROUP, filterName, type);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectGroupByOption(String filterName, String type) {
		try {
			dropdown.selectItem(DPD_GROUP_BY, filterName, type);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectLocationOnGroupFilter(String orgName) {
		try {
			foundation.click(DPD_LOC_ON_GROUPFILTER_);
			foundation.click(By.xpath("//ul[@id='select2-locdt-results']/li[text()='" + orgName + "']"));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public String getTodaysDate(String reportFormat) {
		String reportDate = null;
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			DateTimeFormatter reqFormat = DateTimeFormatter.ofPattern(reportFormat);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			reportDate = tranDate.format(reqFormat);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return reportDate;
	}

	public void clickOnToExcelButton(By object) {
		try {
			foundation.waitforClikableElement(object, Constants.EXTRA_LONG_TIME);
			foundation.click(object);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyTheFileWithFullName(String reportName, String fileName) {
		try {

			boolean fileExists = foundation.isFileExists(FilePath.reportFilePath(fileName));

			excel.verifyFirstCellData(reportName, FilePath.reportFilePath(fileName), 0);

			if (fileExists == false) {
				foundation.deleteFile(FilePath.reportFilePath(fileName));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyTheFileContainsNameWithDate(String reportName, String formate) {
		try {
			boolean fileExists = foundation.isFileExists(FilePath.reportFilePathWithDate(reportName, formate));

			excel.verifyFirstCellData(reportName, FilePath.reportFilePathWithDate(reportName, formate), 0);

			if (fileExists == false) {
				foundation.deleteFile(FilePath.reportFilePathWithDate(reportName, formate));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyTheFileContainsNameWithDateWithoutSpace(String reportName, String fileName, String formate) {
		try {
			boolean fileExists = foundation
					.isFileExists(FilePath.reportFilePathWithDateWithoutSpace(fileName, formate));

			excel.verifyFirstCellData(reportName, FilePath.reportFilePathWithDateWithoutSpace(fileName, formate), 0);

			if (fileExists == false) {
				foundation.deleteFile(FilePath.reportFilePathWithDateWithoutSpace(fileName, formate));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyTheFileExistanceWithDateAndWithoutDataValidation(String fileName, String formate) {
		try {
			boolean fileExists = foundation
					.isFileExists(FilePath.reportFilePathWithDateWithoutSpace(fileName, formate));

			if (fileExists == false) {
				foundation.deleteFile(FilePath.reportFilePathWithDateWithoutSpace(fileName, formate));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyTheFileContainsNameAsOrgDateAndGMA(String reportName, String orgName, String formate) {
		try {
			boolean fileExists = foundation.isFileExists(FilePath.reportFilePathWithOrgAndGMA(orgName, formate));

			excel.verifyFirstCellData(reportName, FilePath.reportFilePathWithOrgAndGMA(orgName, formate), 0);

			if (fileExists == false) {
				foundation.deleteFile(FilePath.reportFilePathWithOrgAndGMA(orgName, formate));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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

	public void checkForDataAvailabilyInResultTable(By object1, By object2) {
		try {
			if (foundation.isDisplayed(object1)) {
				if (foundation.isDisplayed(object2)) {
					ExtFactory.getInstance().getExtent().log(Status.INFO, "No Data Available in Report Table");
					Assert.fail("Failed Report because No Data Available in Report Table");
				} else {
					ExtFactory.getInstance().getExtent().log(Status.INFO,
							"Report Data Available in the Table, Hence passing the Test case");
				}
			} else {
				ExtFactory.getInstance().getExtent().log(Status.INFO, "No Report Table Available");
				Assert.fail("Failed Report because No Report Table Available");
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void selectCurrentDay() {
		foundation.click(ReportList.DPD_DATE);
		foundation.click(SELECT_TODAY);
	}

	public void selectPriorMonthDate(String requiredDate) {
		foundation.click(ReportList.DPD_DATE);
		foundation.click(BTN_PREVIOUS_MONTH);
		selectLastDate(requiredDate);
	}

	public void selectLastDate(String requiredDate) {
		try {
			List<String> reqDate = Arrays.asList(requiredDate.split(Constants.DELIMITER_HASH));
			WebElement lastMonthDate = getDriver().findElement(
					By.xpath("//table[@class = 'table-condensed']/tbody/tr/td[text()='" + reqDate.get(0) + "']"));
			if (lastMonthDate.isDisplayed()) {
				foundation.click(By.xpath("//table[@class = 'table-condensed']/tbody/tr/td[text()='" + reqDate.get(2)
						+ "'][not(contains(@class , 'off'))]"));
			} else {
				foundation.click(By.xpath("//table[@class = 'table-condensed']/tbody/tr/td[text()='" + reqDate.get(1)
						+ "'][not(contains(@class , 'off'))]"));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportHeaders(String columnNames, List<String> tableHeaders) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				Assert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportData(List<String> tableHeaders, Map<Integer, Map<String, String>> reportsData,
			Map<Integer, Map<String, String>> intialData) {
		try {
			int count = intialData.size();
			for (int counter = 0; counter < count; counter++) {
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					Assert.assertTrue(reportsData.get(counter).get(tableHeaders.get(iter))
							.contains(intialData.get(counter).get(tableHeaders.get(iter))));
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public void verifyReportDataOfFirstRow(List<String> tableHeaders, Map<String, String> reportsData,
			Map<String, String> intialData) {
		try {
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(
						reportsData.get(tableHeaders.get(iter)).contains(intialData.get(tableHeaders.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
