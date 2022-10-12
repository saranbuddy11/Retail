package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Browser;
import at.framework.browser.Factory;
import at.framework.files.Excel;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

public class AdminNationalAccounts extends Factory {
	private TextBox textBox = new TextBox();
	private Browser browser = new Browser();
	private Excel excel = new Excel();
	private Login login = new Login();
	private NavigationBar navigationBar = new NavigationBar();
	private Table table = new Table();

	public static final By LBL_NATIONAL_ACCOUNT = By.id("page-title");
	public static final By TBL_NATIONAL_ACCOUNT_LIST = By.cssSelector("table#national-account-grid > tbody");
	public static final By LNK_MANAGE_RULES = By.linkText("Manage Rules");
	public static final By TBL_NATIONAL_ACCOUNT_TITLE = By.id(" National Account Rules");
	public static final By TXT_FILTER = By.id("filterType");
	public static final By TBL_NATIONAL_ACCOUNT_HEADER = By.cssSelector("table#dataGrid > thead > tr");
	public static final By DPD_RULE_TYPE = By.id("ruletype");
	public static final By TBL_DATA_GRID = By.id("dataGrid");
	public static final By TBL_DATA_ROW = By.cssSelector("#dataGrid > tbody >tr");
	public static final By DPD_NA_CATEGORY = By.id("upctype");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By DPD_RULE_PRICE = By.id("ruleprice");
	public static final By DPD_LOCATION_RULE = By.id("locdt");
	public static final By BTN_LOCATION_CLEAR_RULE = By
			.xpath("//select[@id='locdt']//..//span[@class='select2-selection__clear']");
	public static final By TXT_RULE_PRICE = By.cssSelector("input#fromprice");
	public static final By LBL_CURRENCY_TYPE = By.cssSelector("#priceLess > dt");
	public static final By CHK_RULE_STATUS = By.id("rulestatus");
	public static final By POP_UP_LOCATION = By.id("successmodel");
	public static final By BTN_CANCEL = By.id("btn-cancel");
	public static final By ICO_DELETE = By.cssSelector("a.fa.fa-trash.icon");
	public static final By POP_UP_BTN_CANCEL = By.cssSelector(".ajs-button.ajs-cancel");
	public static final By LBL_RULE_NAME_HEADER = By.id("dataGrid_name");
	public static final By DPD_ORG = By.id("orgdt");
	public static final By DPD_LOCATION = By.id("loc");
	public static final By CB_AUTO_ADD_LOCATION = By.id("autoselects");
	public static final By TABLE_NATIONAL_ACCOUNT_RULE = By.cssSelector("tbody[role='rowgroup']");
	public static final By LBL_NATIONAL_ACCOUNT_RULE = By.xpath("//div[contains(text(),'National Account Rules')]");
	public static final By BTN_CREATE_NEW_RULE = By.id("createNewBtn");
	public final static String COLUMN_ORG = "aria-describedby=national-account-grid_e8d7b8d747377082bd177c867633060f_locations_child_org";
	public final static String COLUMN_LOCATION = "aria-describedby=national-account-grid_e8d7b8d747377082bd177c867633060f_locations_child_location";
	public static final By NATIONAL_ACCOUNT_DETAILS = By.cssSelector("table[data-path='locations'] > tbody");
	public static final By TEXT_BOX_RULE_NAME = By.cssSelector("input#rulename");
	public static final By TXT_FLITER_INPUT = By.xpath("//input[@placeholder='Contains...']");
	public static final By TXT_PAGE_TITLE = By.xpath("//div[contains(@id,'National Account')]");
	public static final By BTN_NO = By.xpath("//button[text()='NO']");
	public static final By BTN_NATIONAL_ACCOUNT_CATEGORY = By.id("mng-category");
	public static final By LBL_LOCATION_TITLE = By.id("corporatetitle");
	public static final By DPD_ORG_MODAL = By.id("org");
	public static final By DPD_LOCATION_MODAL = By.id("loc");
	public static final By DPD_LOCATION_MODAL_1 = By.id("locs");
	public static final By CHK_AUTOADD = By.id("autoadd-act");
	public static final By BTN_SAVE_MODAL = By.id("btn-save");
	public static final By BTN_ORG_CLEAR = By.cssSelector("span.select2-selection__clear");
	public static final By BTN_LOC_CLEAR = By.id("//select[@id='loc']/..//*[@class='select2-selection__clear']");
	public static final By BTN_CANCEL_MODAL = By.id("btn-cancel");
	public static final By BTN_CANCEL_MANAGE_RULE = By.id("cancel-btn");
	public static final By LBL_RULE_HEADER = By.id("dataGrid_name");
	public static final By LNK_RULE_NAME = By.xpath("//td[@aria-describedby='dataGrid_name']//a");
	public static final By BTN_YES_DELETE_RULE = By.xpath("//button[text()='YES']");
	public static final By BTN_CANCEL_RULE = By.id("cancelBtn");
	public static final By PAGE_TITLE = By.id("National Accounts");
	public static final By NATIONAL_ACC_TITLE = By.cssSelector(".nationalTitle");
	public static final By NATIONAL_CLIENT_LBL = By.cssSelector("dt[class='span2 nationalLabel']");
	public static final By DPD_CLIENT = By.id("nationalclient");
	public static final By NATIONAL_ACCOUNT_INPUT = By.id("name");
	public static final By ADD_NA_BTN = By.id("nationalAccountBtn");
	public static final By NA_SUMMARY_GRID = By.cssSelector(".ui-iggrid-tablebody>tr");
	public static final By BULK_IMPORT_CAT = By.id("cat-blk-imprt");
	public static final By NA_CAT_IMPORT_TITLE = By.cssSelector("li.active");
	public static final By NA_CAT_IMPORT_TEMPLATE = By.cssSelector("a[href*='CategoryTemplate']");
	public static final By BROWSER_BTN = By.name("file");
	public static final By NA_CAT_DROPDOWN = By.id("select2-category-container");
	public static final By IMPORT_BTN = By.id("import-ok");
	public static final By CANCEL_BTN = By.id("import-cancel");
	public static final By SUCCESS_MSG = By.id("success-msg");
	public static final By CATEGORY_SEARCH = By.id("search-box");
	public static final By NA_CAT_GRID = By.cssSelector("td[aria-describedby*='national-category-grid_category']>a");
	public static final By CATEGORY_CHOICE = By.cssSelector("li.select2-selection__choice");
	public static final By CATEGORY_CHOICE_DELETE = By.cssSelector("li.select2-selection__choice>span");
	public static final By UPDATE_CATEGORY = By.id("update-category");
	public static final By RULE_DETAILS_PAGE_TITLE = By.id("National Account - Aramark:AutomationNationalAccount");
	public static final By DELETE_BTN = By.id("deleteBtn");
	public static final By RULE_PAGE_CANCEL_BTN = By.id("cancelBtn");
	public static final By RULE_NAME_TEXT_FIELD = By.id("rulename");
	public static final By NA_SUMMARY_PAGE_TITLE = By.id("National Account Summary");

	private Foundation foundation = new Foundation();

	public void clickManageRule(String nationalAccountName, String gridName) {
		getDriver().findElement(By.xpath("//td[@aria-describedby='" + gridName + "'][text()='" + nationalAccountName
				+ "']//..//td[@aria-describedby='national-account-grid_manageRules']//a")).click();
	}

	public void clickCategory(String category) {
		getDriver().findElement(By.xpath("//li[contains(text(),'" + category + "')]")).click();
	}

	public void deleteCategory(int index) {
		List<WebElement> element = getDriver().findElements(CATEGORY_CHOICE_DELETE);
		for (int i = 0; i < element.size(); i++) {
			if (i == index)
				element.get(index).click();
		}
	}

	public List<String> getColumnValues(String gridName) {
		String text = null;
		List<String> elementsText = new ArrayList<String>();
		try {
			List<WebElement> ListElement = getDriver()
					.findElements(By.xpath("//*[@aria-describedby='" + gridName + "']//a"));
			for (int i = 1; i <= ListElement.size(); i++) {
				text = getDriver()
						.findElement(By.xpath("(//*[@aria-describedby='" + gridName + "']//a)" + "[" + i + "]"))
						.getText();
				elementsText.add(text);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return elementsText;
	}

	public boolean verifySortAscending(String gridName) {
		boolean ascending = false;
		try {
			List<String> listRuleNameAccending = getColumnValues(gridName);
			ascending = listRuleNameAccending.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList())
					.equals(listRuleNameAccending);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return ascending;
	}

	public boolean verifySortDescending(String gridName) {
		boolean descending = false;
		try {
			List<String> listRuleNameDescending = getColumnValues(gridName);
			descending = listRuleNameDescending.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())
					.equals(listRuleNameDescending);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return descending;
	}

	public List<String> getLocationDetails(String nationalAccountName, String orgName) {
		List<String> locationValues = new ArrayList<>();
		try {
			locationValues.clear();
			getDriver().findElement(By.xpath(
					"//td[text()='" + nationalAccountName + "']/..//td[@class='ui-iggrid-expandcolumn']//span//span"))
					.click();
			foundation.waitforElement(NATIONAL_ACCOUNT_DETAILS, 3);
			WebElement locations = getDriver().findElement(NATIONAL_ACCOUNT_DETAILS);
			List<WebElement> rows = locations.findElements(By.tagName("tr"));
			for (int iter = 0; iter < rows.size(); iter++) {
				if (rows.get(iter)
						.findElement(By.cssSelector("table[data-path=locations] > tbody > tr > td[" + COLUMN_ORG + "]"))
						.getText().equals(orgName)) {
					locationValues.add(rows.get(iter)
							.findElement(By.cssSelector(
									"table[data-path=locations] > tbody > tr > td[" + COLUMN_LOCATION + "]"))
							.getText());
				}
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return locationValues;
	}

	public By getLocationObj(String text) {
		return By.xpath("//a[text()='" + text + "']//..//..//td[@aria-describedby='dataGrid_location']//a");
	}

	public void deleteMangeRule(String ruleName) {
		getDriver().findElement(By.xpath("//td[@aria-describedby='dataGrid_name']//a[text()='" + ruleName
				+ "']//..//..//td[@aria-describedby='dataGrid_delete']//a")).click();
		foundation.click(BTN_YES_DELETE_RULE);
	}

	/**
	 * Verifying the Fields in Rule Page
	 */
	public void verifyRulePageDetails() {
		CustomisedAssert.assertTrue(foundation.isDisplayed(RULE_DETAILS_PAGE_TITLE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(DPD_RULE_TYPE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(DPD_NA_CATEGORY));
		CustomisedAssert.assertTrue(foundation.isDisplayed(BTN_SAVE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(DPD_RULE_PRICE));
		CustomisedAssert.assertTrue(foundation.isDisplayed(CHK_RULE_STATUS));
		CustomisedAssert.assertTrue(foundation.isDisplayed(DELETE_BTN));
		CustomisedAssert.assertTrue(foundation.isDisplayed(RULE_PAGE_CANCEL_BTN));
		CustomisedAssert.assertTrue(foundation.isDisplayed(RULE_NAME_TEXT_FIELD));
		CustomisedAssert.assertTrue(foundation.isEnabled(RULE_NAME_TEXT_FIELD));
	}

	/**
	 * Click on Particular National Account Name
	 * 
	 * @param nationalAccountName
	 * @param gridName
	 */
	public void clickNationalAccountName(String nationalAccountName, String gridName) {
		textBox.enterText(TXT_FILTER, nationalAccountName);
		getDriver()
				.findElement(
						By.xpath("//td[@aria-describedby='" + gridName + "'][text()='" + nationalAccountName + "']"))
				.click();
		foundation.waitforElementToBeVisible(NA_SUMMARY_PAGE_TITLE, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(NA_SUMMARY_PAGE_TITLE));
	}

	/**
	 * Launch ADM application with User Credentials and Navigate to Menu
	 * 
	 * @param userName
	 * @param org
	 * @param menu
	 */
	public void launchApplicationWithUserAndNavigateToMenu(String userName, String org, String menu) {
		browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
		login.login(userName,
				propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
		navigationBar.selectOrganization(org);
		navigationBar.navigateToMenuItem(menu);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_NATIONAL_ACCOUNT));
	}

	/**
	 * Clicking on Bulk Import Category and Download the Template of Nationa Account
	 * Category
	 */
	public void clickOnBulkImportCategoryAndDownloadTemplate() {
		foundation.click(BTN_NATIONAL_ACCOUNT_CATEGORY);
		foundation.waitforElementToBeVisible(LBL_NATIONAL_ACCOUNT, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_NATIONAL_ACCOUNT));
		foundation.click(BULK_IMPORT_CAT);
		CustomisedAssert.assertTrue(foundation.isDisplayed(NA_CAT_IMPORT_TITLE));
		foundation.click(NA_CAT_IMPORT_TEMPLATE);
		CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.NATIONAL_CAT_TEMPLATE));
		foundation.threadWait(Constants.SHORT_TIME);
	}

	/**
	 * Edit Template and upload the same to add new Product to Category
	 * 
	 * @param fileName
	 * @param workSheetName
	 * @param iterator
	 * @param cellValue
	 * @param category
	 */
	public void editAndUploadTemplateToAddNewProductToCategory(String fileName, String workSheetName, String iterator,
			String cellValue, String category) {
		excel.writeToExcel(fileName, workSheetName, iterator, cellValue);
		foundation.threadWait(Constants.SHORT_TIME);
		textBox.enterText(BROWSER_BTN, fileName);
		foundation.waitforElementToBeVisible(NA_CAT_DROPDOWN, Constants.SHORT_TIME);
		foundation.scrollIntoViewElement(NA_CAT_DROPDOWN);
		foundation.click(NA_CAT_DROPDOWN);
		clickCategory(category);
		foundation.click(IMPORT_BTN);
		foundation.waitforElementToBeVisible(SUCCESS_MSG, Constants.SHORT_TIME);
		foundation.click(CANCEL_BTN);
		foundation.waitforElementToBeVisible(LBL_NATIONAL_ACCOUNT, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_NATIONAL_ACCOUNT));
	}

	/**
	 * Verify uploaded Product is displayed in Category and Delete the same
	 * 
	 * @param category
	 * @param choice
	 * @param index
	 */
	public void verifyUploadedProductInCategoryAndDelete(String category, String choice, String index) {
		textBox.enterText(CATEGORY_SEARCH, category);
		foundation.click(NA_CAT_GRID);
		foundation.threadWait(Constants.SHORT_TIME);
		String values = foundation.getText(CATEGORY_CHOICE);
		CustomisedAssert.assertTrue(values.contains(choice));
		deleteCategory(Integer.parseInt(index));
		foundation.click(UPDATE_CATEGORY);
		foundation.waitforElementToBeVisible(LBL_NATIONAL_ACCOUNT, Constants.SHORT_TIME);
		CustomisedAssert.assertTrue(foundation.isDisplayed(LBL_NATIONAL_ACCOUNT));
	}

	/**
	 * verify the Search Functionality
	 * 
	 * @param ruleName
	 */
	public void verifySearchFunctionality(String ruleName) {
		textBox.enterText(TXT_FILTER, ruleName);
		CustomisedAssert.assertTrue(table.getTblRowCount(TBL_DATA_ROW) == 1);
		table.selectRow(ruleName);
	}
}
