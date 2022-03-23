package at.smartshop.tests;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Keys;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNationalAccounts;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.AdminNationalAccounts;
import at.smartshop.pages.CreateNewNationalAccountCategory;
import at.smartshop.pages.CreateNewRule;
import at.smartshop.pages.NationalAccountRules;
import at.smartshop.pages.NationalAccounts;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserRoles;
import at.smartshop.pages.ViewRole;
import at.smartshop.utilities.CurrenyConverter;

@Listeners(at.framework.reportsetup.Listeners.class)
public class NationalAccount extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private UserRoles userRoles = new UserRoles();
	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox();
	private Dropdown dropDown = new Dropdown();
	private ViewRole viewRole = new ViewRole();
	private Table table = new Table();
	private Foundation foundation = new Foundation();
	private AdminNationalAccounts adminNationalAccounts = new AdminNationalAccounts();
	private CurrenyConverter converter = new CurrenyConverter();
	private CreateNewRule createNewRule = new CreateNewRule();
	private NationalAccountRules nationalAccountRules = new NationalAccountRules();
	private NationalAccounts nationalAccounts = new NationalAccounts();
	private Strings strings = new Strings();
	private Numbers numbers = new Numbers();
	private CreateNewNationalAccountCategory createNewNationalAccountsCategory = new CreateNewNationalAccountCategory();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstNationalAccountsData;

	@Test(description = "118214-This test verifies Master National Account Preset Role Permissions")
	public void verifyMasterNationalAccountPermission() {
		try {
			final String CASE_NUM = "118214";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.click(UserList.BTN_MANAGE_ROLES);
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserRoles.BTN_CREATE_NEW_ROLE));
			List<String> requiredData = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			textBox.enterText(UserRoles.TXT_SEARCH_FILTER, requiredData.get(0));
			foundation.click(userRoles.getRowByText(requiredData.get(0)));

			CustomisedAssert.assertTrue(foundation.isDisplayed(UserRoles.LBL_VIEW_ROLE));
			foundation.click(UserRoles.TAB_ADMIN);

			String lblNationalAccountCategories = foundation.getText(ViewRole.LBL_NATIONAL_ACCOUNT_CATEGORIES);
			String lblNationalAccountsLocksRules = foundation.getText(ViewRole.LBL_NATIONAL_ACCOUNT_LOCKS_RULES);

			CustomisedAssert.assertEquals(lblNationalAccountCategories, requiredData.get(1));
			CustomisedAssert.assertEquals(lblNationalAccountsLocksRules, requiredData.get(2));

			viewRole.isAllCheckboxChecked(lblNationalAccountCategories);
			viewRole.isAllCheckboxChecked(lblNationalAccountsLocksRules);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "118194-This test is to validate add National Account functionality")
	public void verifNationalAccAddFunctionality() {

		final String CASE_NUM = "118194";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		final String accountName = Constants.ACCOUNT_NAME + RandomStringUtils.randomAlphabetic(6);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Entering fields in New National Account
			foundation.click(NationalAccounts.BTN_CREATE);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItem(NationalAccounts.DPD_CLIENT_NAME,
					rstNationalAccountsData.get(CNNationalAccounts.CLIENT_NAME), Constants.TEXT);
			foundation.click(NationalAccounts.BTN_SAVE);

			// Selecting Orginization and Location
			foundation.waitforElement(NationalAccounts.DPD_ORGANIZATION, Constants.SHORT_TIME);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(NationalAccounts.DPD_ORGANIZATION,
					rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(NationalAccounts.DPD_LOCATION,
					rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED), Constants.TEXT);
			foundation.click(NationalAccounts.BTN_ADD_NATIONAL_ACCOUNT);
			textBox.enterText(NationalAccounts.TXT_FILTER,
					rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			String org = foundation.getText(NationalAccounts.LBL_ORGINIZATION);
			String location = foundation.getText(NationalAccounts.LBL_LOCATION);

			// Validate the fields Orginazation and location
			CustomisedAssert.assertEquals(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED), org);
			CustomisedAssert.assertEquals(rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED), location);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
			foundation.click(NationalAccounts.ICO_DELETE);
			foundation.click(NationalAccounts.BTN_POP_UP_YES);
		}
	}

	@Test(description = "118188-This test is to validate cancel to National Account functionality")
	public void verifNationalAccCancelFunctionality() {
		try {
			final String CASE_NUM = "118188";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Entering fields in New National Account
			foundation.click(NationalAccounts.BTN_CREATE);
			final String accountName = Constants.ACCOUNT_NAME + RandomStringUtils.randomAlphabetic(6);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItem(NationalAccounts.DPD_CLIENT_NAME,
					rstNationalAccountsData.get(CNNationalAccounts.CLIENT_NAME), Constants.TEXT);
			foundation.click(NationalAccounts.BTN_CANCEL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(NationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
			CustomisedAssert.assertTrue(table.getTblRowCount(NationalAccounts.TBL_ROW) <= 0);

			List<String> nationalAccount = Arrays.asList(rstNationalAccountsData
					.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME).split(Constants.DELIMITER_TILD));
			textBox.enterText(NationalAccounts.TXT_FILTER, nationalAccount.get(1));
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME), nationalAccount.get(1));
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, nationalAccount.get(1) + Constants.ACCOUNT_NAME);
			foundation.click(NationalAccounts.BTN_CANCEL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(NationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, nationalAccount.get(1) + Constants.ACCOUNT_NAME);
			CustomisedAssert.assertTrue(table.getTblRowCount(NationalAccounts.TBL_ROW) <= 0);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120712-This test is to validate  grid columns in National Account:Client List screen for National Account user")
	public void verifGridColumnInNationalAccount() {
		try {
			final String CASE_NUM = "120712";
			Map<String, String> dbData = new HashMap<>();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			// Navigate to National Account
			List<String> nationalAccount = Arrays.asList(rstNationalAccountsData
					.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME).split(Constants.DELIMITER_TILD));
			adminNationalAccounts.clickManageRule(nationalAccount.get(0),
					rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_TITLE));

			// Verifying search functionality
			textBox.enterText(AdminNationalAccounts.TXT_FILTER, nationalAccount.get(1));
			CustomisedAssert.assertTrue(table.getTblRowCount(AdminNationalAccounts.TBL_DATA_ROW) == 1);

			textBox.enterText(AdminNationalAccounts.TXT_FILTER, nationalAccount.get(2));
			CustomisedAssert.assertTrue(table.getTblRowCount(AdminNationalAccounts.TBL_DATA_ROW) == 0);

			// Verifying Ascending order sorting functionality
			foundation.refreshPage();
			List<String> columnNames = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.COLUMN_NAMES).split(Constants.DELIMITER_TILD));
			foundation.click(AdminNationalAccounts.LBL_RULE_NAME_HEADER);
			CustomisedAssert.assertTrue(adminNationalAccounts
					.verifySortAscending(rstNationalAccountsData.get(CNNationalAccounts.GRID_RULE_NAME)));

			// Verifying descending order sorting functionality
			foundation.click(AdminNationalAccounts.LBL_RULE_NAME_HEADER);
			CustomisedAssert.assertTrue(adminNationalAccounts
					.verifySortDescending(rstNationalAccountsData.get(CNNationalAccounts.GRID_RULE_NAME)));

			// Verifying column headers
			foundation.refreshPage();
			for (int i = 0; i < columnNames.size(); i++) {
				dbData.put(columnNames.get(i), columnNames.get(i));
			}

			Map<String, String> uiTableHeaher = table
					.getTblHeadersUI(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_HEADER);
			CustomisedAssert.assertEquals(uiTableHeaher, dbData);

			// Verifying rule type dropdown values
			foundation.refreshPage();
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));

			List<String> ruleType = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE).split(Constants.DELIMITER_TILD));
			CustomisedAssert
					.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(0)));
			CustomisedAssert
					.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(1)));
			CustomisedAssert
					.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(2)));

			// Selecting UPC rule type and rule category
			List<String> ruleCategory = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.RULE_CATEGORY).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(1), Constants.TEXT);
			dropDown.selectItem(AdminNationalAccounts.DPD_NA_CATEGORY, ruleCategory.get(1), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Verifying UPC rule setting in table
			Map<String, String> upcData = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(upcData.get(columnNames.get(1)), ruleType.get(1));
			CustomisedAssert.assertEquals(upcData.get(columnNames.get(2)), ruleCategory.get(1));
			CustomisedAssert.assertEquals(upcData.get(columnNames.get(3)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));

			// Selecting National Category rule type and rule category
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(2), Constants.TEXT);
			dropDown.selectItem(AdminNationalAccounts.DPD_NA_CATEGORY, ruleCategory.get(0), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Verifying national category rule setting in table
			Map<String, String> NAData = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(NAData.get(columnNames.get(1)), ruleType.get(3));
			CustomisedAssert.assertEquals(NAData.get(columnNames.get(3)), ruleCategory.get(0));
			CustomisedAssert.assertEquals(NAData.get(columnNames.get(2)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));

			// getting rule price from UI
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			double uiRulePrice = Double
					.parseDouble((foundation.getTextAttribute(AdminNationalAccounts.TXT_RULE_PRICE, Constants.VALUE)));
			String rulePrice = converter.convertTOCurrency(uiRulePrice);

			// Setting rule to No More Than
			List<String> dbRulePrice = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(1), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Validating price set for rule to No More Than
			Map<String, String> rulePriceNoMoreThan = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(rulePriceNoMoreThan.get(columnNames.get(5)), rulePrice);
			CustomisedAssert.assertEquals(rulePriceNoMoreThan.get(columnNames.get(4)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			CustomisedAssert.assertEquals(rulePriceNoMoreThan.get(columnNames.get(6)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));

			// Setting rule to No Less Than
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(2), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Validating price set for No Less Than
			Map<String, String> rulePriceNoLessThan = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(rulePriceNoLessThan.get(columnNames.get(5)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			CustomisedAssert.assertEquals(rulePriceNoLessThan.get(columnNames.get(4)), rulePrice);
			CustomisedAssert.assertEquals(rulePriceNoLessThan.get(columnNames.get(6)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));

			// Setting rule to Force Exact Price
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(0), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Validating price set for Force Exact Price
			Map<String, String> ruleForceExactPrice = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(ruleForceExactPrice.get(columnNames.get(5)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			CustomisedAssert.assertEquals(ruleForceExactPrice.get(columnNames.get(4)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			CustomisedAssert.assertEquals(ruleForceExactPrice.get(columnNames.get(6)), rulePrice);

			// Setting rule status to Inactive
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			checkBox.unCheck(AdminNationalAccounts.CHK_RULE_STATUS);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			Map<String, String> ruleStatusInActive = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);

			// Validating rule status is Inactive
			List<String> rulesStatus = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.RULE_STATUS).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertEquals(ruleStatusInActive.get(columnNames.get(7)), rulesStatus.get(1));

			// Setting rule status to Active
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			checkBox.check(AdminNationalAccounts.CHK_RULE_STATUS);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Validating rule status is Active
			Map<String, String> ruleStatusActive = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(ruleStatusActive.get(columnNames.get(7)), rulesStatus.get(0));

			// Setting Location tagged
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			foundation.click(AdminNationalAccounts.BTN_LOCATION_CLEAR_RULE);
			dropDown.selectItem(AdminNationalAccounts.DPD_LOCATION_RULE,
					rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Validating Location tagged count
			Map<String, String> locationCount = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(locationCount.get(columnNames.get(8)),
					rstNationalAccountsData.get(CNNationalAccounts.LOCATION_COUNT));

			// Validating Location Add or Remove Location from Rule prompt from table
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.LOCATION_COUNT));
			foundation.waitforElement(AdminNationalAccounts.BTN_CANCEL, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CANCEL));

			foundation.click(AdminNationalAccounts.BTN_CANCEL);
			foundation.click(AdminNationalAccounts.ICO_DELETE);

			// Validating Delete Confirm Prompt
			foundation.waitforElement(AdminNationalAccounts.POP_UP_BTN_CANCEL, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.POP_UP_BTN_CANCEL));
			foundation.click(AdminNationalAccounts.POP_UP_BTN_CANCEL);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120713-This test is to validate grid columns in Master National Account:Client List screen for Master National Account user")
	public void verifyGridColumnInMasterNationalAccount() {
		try {
			final String CASE_NUM = "120713";
			Map<String, String> dbData = new HashMap<>();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			// Navigate to National Account
			List<String> nationalAccount = Arrays.asList(rstNationalAccountsData
					.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME).split(Constants.DELIMITER_TILD));
			adminNationalAccounts.clickManageRule(nationalAccount.get(0),
					rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_TITLE));

			// Verifying search functionality
			textBox.enterText(AdminNationalAccounts.TXT_FILTER, nationalAccount.get(1));
			CustomisedAssert.assertTrue(table.getTblRowCount(AdminNationalAccounts.TBL_DATA_ROW) == 1);

			textBox.enterText(AdminNationalAccounts.TXT_FILTER, nationalAccount.get(2));
			CustomisedAssert.assertTrue(table.getTblRowCount(AdminNationalAccounts.TBL_DATA_ROW) == 0);

			// Verifying Ascending order sorting functionality
			foundation.refreshPage();
			List<String> columnNames = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.COLUMN_NAMES).split(Constants.DELIMITER_TILD));
			foundation.click(AdminNationalAccounts.LBL_RULE_NAME_HEADER);
			CustomisedAssert.assertTrue(adminNationalAccounts
					.verifySortAscending(rstNationalAccountsData.get(CNNationalAccounts.GRID_RULE_NAME)));

			// Verifying descending order sorting functionality
			foundation.click(AdminNationalAccounts.LBL_RULE_NAME_HEADER);
			CustomisedAssert.assertTrue(adminNationalAccounts
					.verifySortDescending(rstNationalAccountsData.get(CNNationalAccounts.GRID_RULE_NAME)));

			// Verifying column headers
			foundation.refreshPage();
			for (int i = 0; i < columnNames.size(); i++) {
				dbData.put(columnNames.get(i), columnNames.get(i));
			}

			Map<String, String> uiTableHeaher = table
					.getTblHeadersUI(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_HEADER);
			CustomisedAssert.assertEquals(uiTableHeaher, dbData);

			// Verifying rule type dropdown values
			foundation.refreshPage();
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));

			List<String> ruleType = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE).split(Constants.DELIMITER_TILD));
			CustomisedAssert
					.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(0)));
			CustomisedAssert
					.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(1)));
			CustomisedAssert
					.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(2)));

			// Selecting UPC rule type and rule category
			List<String> ruleCategory = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.RULE_CATEGORY).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(1), Constants.TEXT);
			dropDown.selectItem(AdminNationalAccounts.DPD_NA_CATEGORY, ruleCategory.get(1), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Verifying UPC rule setting in table
			Map<String, String> upcData = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(upcData.get(columnNames.get(1)), ruleType.get(1));
			CustomisedAssert.assertEquals(upcData.get(columnNames.get(2)), ruleCategory.get(1));
			CustomisedAssert.assertEquals(upcData.get(columnNames.get(3)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));

			// Selecting National Category rule type and rule category
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(2), Constants.TEXT);
			dropDown.selectItem(AdminNationalAccounts.DPD_NA_CATEGORY, ruleCategory.get(0), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Verifying national category rule setting in table
			Map<String, String> NAData = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(NAData.get(columnNames.get(1)), ruleType.get(3));
			CustomisedAssert.assertEquals(NAData.get(columnNames.get(3)), ruleCategory.get(0));
			CustomisedAssert.assertEquals(NAData.get(columnNames.get(2)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));

			// getting rule price from UI
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			double uiRulePrice = Double
					.parseDouble((foundation.getTextAttribute(AdminNationalAccounts.TXT_RULE_PRICE, Constants.VALUE)));
			String rulePrice = converter.convertTOCurrency(uiRulePrice);

			// Setting rule to No More Than
			List<String> dbRulePrice = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(1), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Validating price set for rule to No More Than
			Map<String, String> rulePriceNoMoreThan = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(rulePriceNoMoreThan.get(columnNames.get(5)), rulePrice);
			CustomisedAssert.assertEquals(rulePriceNoMoreThan.get(columnNames.get(4)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			CustomisedAssert.assertEquals(rulePriceNoMoreThan.get(columnNames.get(6)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));

			// Setting rule to No Less Than
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(2), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Validating price set for No Less Than
			Map<String, String> rulePriceNoLessThan = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(rulePriceNoLessThan.get(columnNames.get(5)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			CustomisedAssert.assertEquals(rulePriceNoLessThan.get(columnNames.get(4)), rulePrice);
			CustomisedAssert.assertEquals(rulePriceNoLessThan.get(columnNames.get(6)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));

			// Setting rule to Force Exact Price
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(0), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Validating price set for Force Exact Price
			Map<String, String> ruleForceExactPrice = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(ruleForceExactPrice.get(columnNames.get(5)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			CustomisedAssert.assertEquals(ruleForceExactPrice.get(columnNames.get(4)),
					rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			CustomisedAssert.assertEquals(ruleForceExactPrice.get(columnNames.get(6)), rulePrice);

			// Setting rule status to Inactive
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			checkBox.unCheck(AdminNationalAccounts.CHK_RULE_STATUS);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			Map<String, String> ruleStatusInActive = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);

			// Validating rule status is Inactive
			List<String> rulesStatus = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.RULE_STATUS).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertEquals(ruleStatusInActive.get(columnNames.get(7)), rulesStatus.get(1));

			// Setting rule status to Active
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			checkBox.check(AdminNationalAccounts.CHK_RULE_STATUS);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Validating rule status is Active
			Map<String, String> ruleStatusActive = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(ruleStatusActive.get(columnNames.get(7)), rulesStatus.get(0));

			// Setting Location tagged
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			foundation.click(AdminNationalAccounts.BTN_LOCATION_CLEAR_RULE);
			dropDown.selectItem(AdminNationalAccounts.DPD_LOCATION_RULE,
					rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);

			// Validating Location tagged count
			Map<String, String> locationCount = table.getTblSingleRowRecordUI(AdminNationalAccounts.TBL_DATA_GRID,
					AdminNationalAccounts.TBL_DATA_ROW);
			CustomisedAssert.assertEquals(locationCount.get(columnNames.get(8)),
					rstNationalAccountsData.get(CNNationalAccounts.LOCATION_COUNT));

			// Validating Location Add or Remove Location from Rule prompt from table
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.LOCATION_COUNT));
			foundation.waitforElement(AdminNationalAccounts.BTN_CANCEL, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CANCEL));

			foundation.click(AdminNationalAccounts.BTN_CANCEL);
			foundation.click(AdminNationalAccounts.ICO_DELETE);

			// Validating Delete Confirm Prompt
			foundation.waitforElement(AdminNationalAccounts.POP_UP_BTN_CANCEL, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.POP_UP_BTN_CANCEL));
			foundation.click(AdminNationalAccounts.POP_UP_BTN_CANCEL);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120731-This test This test validates Location Dropdown in the National Account Edit rule screen with NA User")
	public void verifyLocationDropdownNAUser() {
		try {
			final String CASE_NUM = "120731";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));

			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE));
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.selectOrg(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			createNewRule.verifyHintText(rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION));
			createNewRule.selectLocation(rstNationalAccountsData.get(CNNationalAccounts.LOCATION));
			CustomisedAssert.assertFalse(checkBox.isChecked(CreateNewRule.CHCK_AUTO_LOCATION));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120728-This test This test validates Location Dropdown in the National Account Edit rule screen with MNA User")
	public void verifyLocationDropdownMNAUser() {
		try {
			final String CASE_NUM = "120728";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));

			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE));
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.selectOrg(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			createNewRule.verifyHintText(rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION));
			createNewRule.selectLocation(rstNationalAccountsData.get(CNNationalAccounts.LOCATION));
			CustomisedAssert.assertFalse(checkBox.isChecked(CreateNewRule.CHCK_AUTO_LOCATION));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120730-This test validates Orgs Dropdown in the National Account Edit rule screen with NA User")
	public void verifyOrgsDropdownNAUser() {
		try {
			final String CASE_NUM = "120730";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			List<String> locationVaules = adminNationalAccounts.getLocationDetails(
					rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));

			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE));
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);

			createNewRule.verifyHintText(rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION));
			createNewRule.selectOrg(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			List<String> locationDPDValues = createNewRule.getLocationDropdownValues();
			CustomisedAssert.assertTrue(locationVaules.equals(locationDPDValues));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120726-This test validates Orgs Dropdown in the National Account Edit rule screen with MNA User")
	public void verifyOrgsDropdownMNAUser() {
		try {
			final String CASE_NUM = "120726";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			List<String> locationVaules = adminNationalAccounts.getLocationDetails(
					rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));

			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE));
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);

			createNewRule.verifyHintText(rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION));
			createNewRule.selectOrg(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			List<String> locationDPDValues = createNewRule.getLocationDropdownValues();
			CustomisedAssert.assertTrue(locationVaules.equals(locationDPDValues));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120722-This test validates National Account Client Rule List Screen")
	public void verifyClientRuleScreen() {
		try {

			final String CASE_NUM = "120722";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));

			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE));
			nationalAccountRules.clickRulesLink(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			nationalAccountRules.objRuleName(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120723-This test validates the Delete Button and Confirm Delete prompt in Client Rule detail Screen")
	public void verifyDeleteButton() {

		final String CASE_NUM = "120723";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		String ruleName = "";

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.threadWait(Constants.TWO_SECOND);

			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));

			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE));
			ruleName = rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME);
			nationalAccountRules.clickRulesLink(ruleName);

			CustomisedAssert.assertTrue(foundation.isDisplayed(CreateNewRule.BTN_DELETE));
			foundation.click(CreateNewRule.BTN_DELETE);

			foundation.waitforElement(CreateNewRule.DELETE_PROMPT, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreateNewRule.DELETE_PROMPT));
			String title = foundation.getText(CreateNewRule.DELETE_PROMPT_TITLE);
			CustomisedAssert.assertEquals(title, rstNationalAccountsData.get(CNNationalAccounts.PROMPT_TITLE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreateNewRule.BTN_YES));
			CustomisedAssert.assertTrue(foundation.isDisplayed(CreateNewRule.BTN_CANCEL));
			createNewRule.verifyCancelButton();
			createNewRule.verifyYesButton();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.createRule(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED),
					rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED),
					rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE),
					rstNationalAccountsData.get(CNNationalAccounts.RULE_CATEGORY),
					rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE));
			foundation.waitforElement(AdminNationalAccounts.BTN_CREATE_NEW_RULE, Constants.SHORT_TIME);
			foundation.isDisplayed(nationalAccountRules.objRuleName(ruleName));
		}
	}

	@Test(description = "120734-This test validates the Auto Add New Locations Rule check box with MNA")
	public void validateAutoAddNewLocationsMNA() {
		try {
			final String CASE_NUM = "120734";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));

			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE));
			nationalAccountRules.clickRulesLink(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			foundation.click(CreateNewRule.CHCK_AUTO_LOCATION);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120716-Verify Rule List Columns when Master National Account user navigated to National Account : Client Rule list screen")
	public void nationalAccountClientRuleTable() {
		try {
			final String CASE_NUM = "120716";

			Map<String, String> dbData = new HashMap<>();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);
			String ruleName = rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME);
			String gridRuleName = rstNationalAccountsData.get(CNNationalAccounts.GRID_RULE_NAME);
			String accountName = rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Click manage
			adminNationalAccounts.clickManageRule(accountName, gridName);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE));
			table.selectRow(ruleName);
			CustomisedAssert.assertEquals(foundation.getText(AdminNationalAccounts.TXT_PAGE_TITLE),
					rstNationalAccountsData.get(CNNationalAccounts.RULE_PAGE_TITLE));
			foundation.click(AdminNationalAccounts.BTN_CANCEL_RULE);
			foundation.click(AdminNationalAccounts.BTN_NO);

			// Validate table headers
			List<String> columnNames = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.COLUMN_NAMES).split(Constants.DELIMITER_TILD));
			for (int i = 0; i < columnNames.size(); i++) {
				dbData.put(columnNames.get(i), columnNames.get(i));
			}
			Map<String, String> uiTableHeader = table
					.getTblHeadersUI(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_HEADER);
			CustomisedAssert.assertEquals(uiTableHeader, dbData);

			// Verifying Ascending order sorting functionality
			foundation.click(AdminNationalAccounts.LBL_RULE_NAME_HEADER);
			CustomisedAssert.assertTrue(adminNationalAccounts.verifySortAscending(gridRuleName));

			// Verifying descending order sorting functionality
			foundation.click(AdminNationalAccounts.LBL_RULE_NAME_HEADER);
			CustomisedAssert.assertTrue(adminNationalAccounts.verifySortDescending(gridRuleName));

			// Verifying search functionality
			textBox.enterText(AdminNationalAccounts.TXT_FILTER, ruleName);
			CustomisedAssert.assertTrue(table.getTblRowCount(AdminNationalAccounts.TBL_DATA_ROW) == 1);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120717-Verify the functionality of Create New button on the National Account Client rule page with National Account User")
	public void nationalAccountClientRule() {
		try {
			final String CASE_NUM = "120717";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			String nationalAccountName = rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			textBox.enterText(AdminNationalAccounts.TXT_FLITER_INPUT, nationalAccountName);
			foundation.threadWait(Constants.TWO_SECOND);

			// Click manage
			adminNationalAccounts.clickManageRule(nationalAccountName, gridName);
			Boolean createRuleButton = foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			CustomisedAssert.assertTrue(createRuleButton);

			// create new rule
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			Boolean clientRuleName = foundation.isDisplayed(CreateNewRule.TXT_CLIENTRULE_NAME);
			CustomisedAssert.assertTrue(clientRuleName);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120718-Verify the functionality of Create New button on the Master National Account Client rule page with National Account User")
	public void masterNationalAccountClientRule() {
		try {
			final String CASE_NUM = "120718";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			String nationalAccountName = rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(AdminNationalAccounts.TXT_FLITER_INPUT, nationalAccountName);
			foundation.threadWait(Constants.TWO_SECOND);

			// Click manage
			adminNationalAccounts.clickManageRule(nationalAccountName, gridName);
			Boolean createRuleButton = foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			CustomisedAssert.assertTrue(createRuleButton);

			// create new rule
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			Boolean clientRuleName = foundation.isDisplayed(CreateNewRule.TXT_CLIENTRULE_NAME);
			CustomisedAssert.assertTrue(clientRuleName);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120721-Verify the National Accounts:Client Rules List Screen (Rule Name - edit rule) for Master National Account user")
	public void masterNationalAccountSummary() {
		try {
			final String CASE_NUM = "120721";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			String ruleName = rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME);
			String nationalAccountName = rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// filtering rule
			textBox.enterText(AdminNationalAccounts.TXT_FLITER_INPUT, nationalAccountName);
			foundation.threadWait(Constants.TWO_SECOND);
			adminNationalAccounts.clickManageRule(nationalAccountName, gridName);
			nationalAccountRules.clickRulesLink(ruleName);

			// Rule Page Validations
			String rulePageTitle = rstNationalAccountsData.get(CNNationalAccounts.RULE_PAGE_TITLE);
			foundation.waitforElement(createNewRule.objClientRuleName(rulePageTitle), Constants.SHORT_TIME);
			Boolean clientRuleName = foundation.isDisplayed(createNewRule.objClientRuleName(rulePageTitle));
			CustomisedAssert.assertTrue(clientRuleName);
			CustomisedAssert.assertTrue(foundation.isDisplayed(nationalAccountRules.objRulePage(ruleName)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120720-Verify the Prompt screen when Master National Account user delete")

	public void verifyMNADeleteScreen() {
		try {
			final String CASE_NUM = "120720";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading Test data from database
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String organisation = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String ruleType = rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE);
			String ruleName = rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME);
			String ruleCategory = rstNationalAccountsData.get(CNNationalAccounts.RULE_CATEGORY);
			String rulePrice = rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);
			String accountName = rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME);
			List<String> promptTitle = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.PROMPT_TITLE).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			adminNationalAccounts.clickManageRule(accountName, gridName);

			// create new rule
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.createRule(organisation, location, ruleType, ruleCategory, ruleName, rulePrice);
			foundation.waitforElement(NationalAccountRules.TBL_NATIONALACCOUNT_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccountRules.SEARCH_BOX, ruleName);
			foundation.click(NationalAccountRules.ICON_DELETE);

			// delete pop up validations
			foundation.waitforElement(nationalAccountRules.verifyPromptMsg(promptTitle.get(0)), Constants.SHORT_TIME);
			Boolean popupWindow = foundation.isDisplayed(nationalAccountRules.verifyPromptMsg(promptTitle.get(0)));
			CustomisedAssert.assertTrue(popupWindow);
			String actualAlertMsg = foundation.getText(NationalAccounts.TXT_ALERT_CONTENT);
			List<String> actualData = Arrays.asList(actualAlertMsg.split(Constants.NEW_LINE));
			CustomisedAssert.assertEquals(actualData.get(0), promptTitle.get(1));
			CustomisedAssert.assertEquals(actualData.get(2), promptTitle.get(2));

			Boolean popupCancelButton = foundation.isDisplayed(NationalAccountRules.BTN_CANCEL);
			CustomisedAssert.assertTrue(popupCancelButton);

			foundation.click(NationalAccountRules.BTN_CANCEL);
			assertTrue(foundation.isDisplayed(nationalAccountRules.objRuleName(ruleName)));
			foundation.click(NationalAccountRules.ICON_DELETE);
			foundation.waitforElement(nationalAccountRules.verifyPromptMsg(promptTitle.get(0)), Constants.SHORT_TIME);
			Boolean popupYesButton = foundation.isDisplayed(NationalAccountRules.BTN_POPUP_YES);
			CustomisedAssert.assertTrue(popupYesButton);
			foundation.click(NationalAccountRules.BTN_POPUP_YES);
			assertFalse(foundation.isDisplayed(nationalAccountRules.objRuleName(ruleName)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120719-Verify the Prompt screen when National Account user delete")
	public void verifyNationalAccountDeleteScreen() {
		try {
			final String CASE_NUM = "120719";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			String organisation = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String ruleType = rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE);
			String ruleName = rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);
			String ruleCategory = rstNationalAccountsData.get(CNNationalAccounts.RULE_CATEGORY);
			String rulePrice = rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE);
			String nationalAccountName = rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME);
			List<String> promptTitle = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.PROMPT_TITLE).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Click manage button
			adminNationalAccounts.clickManageRule(nationalAccountName, gridName);
			// create new rule
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.createRule(organisation, location, ruleType, ruleCategory, ruleName, rulePrice);
			foundation.waitforElement(NationalAccountRules.TBL_NATIONALACCOUNT_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccountRules.SEARCH_BOX, ruleName);
			foundation.click(NationalAccountRules.ICON_DELETE);

			// delete pop up validations
			foundation.waitforElement(nationalAccountRules.verifyPromptMsg(promptTitle.get(0)), Constants.SHORT_TIME);
			Boolean popupWindow = foundation.isDisplayed(nationalAccountRules.verifyPromptMsg(promptTitle.get(0)));
			CustomisedAssert.assertTrue(popupWindow);
			String actualAlertMsg = foundation.getText(NationalAccounts.TXT_ALERT_CONTENT);
			List<String> actualData = Arrays.asList(actualAlertMsg.split(Constants.NEW_LINE));
			CustomisedAssert.assertEquals(actualData.get(0), promptTitle.get(1));
			CustomisedAssert.assertEquals(actualData.get(2), promptTitle.get(2));
			Boolean popupCancelButton = foundation.isDisplayed(NationalAccountRules.BTN_CANCEL);
			CustomisedAssert.assertTrue(popupCancelButton);
			foundation.click(NationalAccountRules.BTN_CANCEL);
			assertTrue(foundation.isDisplayed(nationalAccountRules.objRuleName(ruleName)));
			foundation.click(NationalAccountRules.ICON_DELETE);
			foundation.waitforElement(nationalAccountRules.verifyPromptMsg(promptTitle.get(0)), Constants.SHORT_TIME);
			Boolean popupYesButton = foundation.isDisplayed(NationalAccountRules.BTN_POPUP_YES);
			CustomisedAssert.assertTrue(popupYesButton);
			foundation.click(NationalAccountRules.BTN_POPUP_YES);
			assertFalse(foundation.isDisplayed(nationalAccountRules.objRuleName(ruleName)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "118193-Verfiy the UI in national account Location grid")
	public void nationalAccountSummary() {

		final String CASE_NUM = "118193";
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		final String accountName = Constants.ACCOUNT_NAME + strings.getRandomCharacter();
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String org = rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_ORG);
			String location = rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION);
			String locationsTagged = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String clientName = rstNationalAccountsData.get(CNNationalAccounts.CLIENT_NAME);
			String promptTitle = rstNationalAccountsData.get(CNNationalAccounts.PROMPT_TITLE);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Entering fields in New National Account
			foundation.click(NationalAccounts.BTN_CREATE);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItem(NationalAccounts.DPD_CLIENT_NAME, clientName, Constants.TEXT);
			foundation.click(NationalAccounts.BTN_SAVE);
			// Selecting Organization and Location
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(NationalAccounts.DPD_ORGANIZATION, org, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(NationalAccounts.DPD_LOCATION, location, Constants.TEXT);
			foundation.click(NationalAccounts.BTN_ADD_NATIONAL_ACCOUNT);

			// Table validations
			nationalAccounts
					.verifyNationalAccountsSummaryColumns(rstLocationSummaryData.get(CNLocationSummary.COLUMN_NAME));
			textBox.enterText(NationalAccounts.TXT_FILTER, location);
			nationalAccounts.verifyNationalAccountSummaryTableBody(location);
			foundation.click(NationalAccounts.ICON_DELETE);

			foundation.waitforElement(NationalAccounts.BTN_POP_UP_YES, Constants.SHORT_TIME);
			Boolean popupWindow = foundation.isDisplayed(NationalAccounts.BTN_POP_UP_YES);
			CustomisedAssert.assertTrue(popupWindow);
			nationalAccounts.verifyDeleteConfirmationMsg(promptTitle);
			Boolean popupYesButton = foundation.isDisplayed(NationalAccounts.BTN_POP_UP_YES);
			CustomisedAssert.assertTrue(popupYesButton);
			foundation.click(NationalAccounts.BTN_Cancel);
			nationalAccounts.verifyNationalAccountSummaryTableBody(locationsTagged);
			foundation.threadWait(Constants.TWO_SECOND);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
			foundation.click(NationalAccounts.ICON_DELETE);
			foundation.waitforElement(NationalAccounts.BTN_POP_UP_YES, Constants.SHORT_TIME);
			foundation.click(NationalAccounts.BTN_POP_UP_YES);
		}
	}

	@Test(description = "118192-This test verifies select location field")
	public void nationalAccountSummaryLocation() {

		final String CASE_NUM = "118192";
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		final String accountName = Constants.ACCOUNT_NAME + strings.getRandomCharacter();

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String org = rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_ORG);
			String location = rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION);
			String clientName = rstNationalAccountsData.get(CNNationalAccounts.CLIENT_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Creating New National Account
			foundation.click(NationalAccounts.BTN_CREATE);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItem(NationalAccounts.DPD_CLIENT_NAME, clientName, Constants.TEXT);
			foundation.click(NationalAccounts.BTN_SAVE);
			foundation.waitforElement(NationalAccounts.DPD_ORGANIZATION, Constants.SHORT_TIME);
			dropDown.selectItem(NationalAccounts.DPD_ORGANIZATION, org, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(NationalAccounts.DPD_LOCATION, location, Constants.TEXT);
			foundation.waitforElement(NationalAccounts.BTN_ADD_NATIONAL_ACCOUNT, Constants.SHORT_TIME);
			foundation.click(NationalAccounts.BTN_ADD_NATIONAL_ACCOUNT);

			// Location field validation
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(NationalAccounts.DPD_ORGANIZATION, org, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			String expectedColour = rstNationalAccountsData.get(CNNationalAccounts.PROMPT_TITLE);
			nationalAccounts.verifyBackgroundColour(location, expectedColour);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
			foundation.click(NationalAccounts.ICON_DELETE);
			foundation.waitforElement(NationalAccounts.BTN_POP_UP_YES, Constants.SHORT_TIME);
			foundation.click(NationalAccounts.BTN_POP_UP_YES);
		}
	}

	@Test(description = "118190-Verify 'Prompt' message if account already exists")
	public void verifyPopup() {

		final String CASE_NUM = "118190";
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String accountName = Constants.ACCOUNT_NAME + strings.getRandomCharacter();
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> promptTitle = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.PROMPT_TITLE).split(Constants.DELIMITER_TILD));
			String clientName = rstNationalAccountsData.get(CNNationalAccounts.CLIENT_NAME);
			String submenu = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(submenu);

			// Precondition
			foundation.click(NationalAccounts.BTN_CREATE);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItem(NationalAccounts.DPD_CLIENT_NAME, clientName, Constants.TEXT);
			foundation.click(NationalAccounts.BTN_SAVE);
			foundation.waitforElement(NationalAccounts.DPD_ORGANIZATION, Constants.SHORT_TIME);

			// Navigating to National Account Page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// creating duplicate account
			foundation.waitforElement(NationalAccounts.BTN_CREATE, Constants.SHORT_TIME);
			foundation.click(NationalAccounts.BTN_CREATE);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItem(NationalAccounts.DPD_CLIENT_NAME, clientName, Constants.TEXT);
			foundation.click(NationalAccounts.BTN_SAVE);

			// Alert message Validations
			foundation.waitforElement(nationalAccounts.nationalAccountDetails(accountName), Constants.SHORT_TIME);
			Boolean status1 = foundation.isDisplayed(nationalAccounts.nationalAccountDetails(accountName));
			CustomisedAssert.assertTrue(status1);
			nationalAccounts.verifyPromptMsg(promptTitle.get(0), promptTitle.get(1));
			String actualAlertMsg = foundation.getText(NationalAccounts.TXT_ALERT_MSG);
			List<String> actualData = Arrays.asList(actualAlertMsg.split(Constants.NEW_LINE));
			String nationalAccount = accountName + ":" + clientName;
			CustomisedAssert.assertEquals(actualData.get(1), nationalAccount);
			CustomisedAssert.assertEquals(actualData.get(3), promptTitle.get(2));
			foundation.click(NationalAccounts.BTN_OK);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
			foundation.click(NationalAccounts.ICON_DELETE);
			foundation.waitforElement(NationalAccounts.BTN_POP_UP_YES, Constants.SHORT_TIME);
			foundation.click(NationalAccounts.BTN_POP_UP_YES);
		}
	}

	@Test(description = "118195-Verfiy 'Search'  option functionality")
	public void nationalAccountsSearchOption() {

		final String CASE_NUM = "118195";
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String accountName = "";
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			String org = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Save a national accounts
			foundation.click(NationalAccounts.BTN_CREATE);
			accountName = Constants.ACCOUNT_NAME + RandomStringUtils.randomAlphabetic(6);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItemByIndex(NationalAccounts.DPD_CLIENT_NAME, 1);
			foundation.click(NationalAccounts.BTN_SAVE);

			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(NationalAccounts.DPD_ORGANIZATION, org, Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(NationalAccounts.DPD_LOCATION, location, Constants.TEXT);

			foundation.click(NationalAccounts.BTN_ADD_NATIONAL_ACCOUNT);
			textBox.enterText(NationalAccounts.TXT_FILTER, org);
			foundation.threadWait(Constants.ONE_SECOND);
			String city = foundation.getText(NationalAccounts.LBL_CITY);
			String State = foundation.getText(NationalAccounts.LBL_STATE);

			// Validate the fields
			CustomisedAssert.assertEquals(foundation.getText(NationalAccounts.LBL_ORGANIZATION), org);
			CustomisedAssert.assertEquals(foundation.getText(NationalAccounts.LBL_LOCATION), location);

			// search with city
			textBox.enterText(NationalAccounts.TXT_FILTER, Keys.CLEAR);
			textBox.enterText(NationalAccounts.TXT_FILTER, city);

			// Validate the fields
			CustomisedAssert.assertEquals(foundation.getText(NationalAccounts.LBL_ORGANIZATION), org);
			CustomisedAssert.assertEquals(foundation.getText(NationalAccounts.LBL_LOCATION), location);

			// search with city
			textBox.enterText(NationalAccounts.TXT_FILTER, Keys.CLEAR);
			textBox.enterText(NationalAccounts.TXT_FILTER, State);

			// Validate the fields
			CustomisedAssert.assertEquals(foundation.getText(NationalAccounts.LBL_ORGANIZATION), org);
			CustomisedAssert.assertEquals(foundation.getText(NationalAccounts.LBL_LOCATION), location);

			// search with state
			textBox.enterText(NationalAccounts.TXT_FILTER, Keys.CLEAR);
			textBox.enterText(NationalAccounts.TXT_FILTER, State);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
			foundation.click(NationalAccounts.ICON_DELETE);
			foundation.click(NationalAccounts.BTN_POPUP_YES);
		}

	}

	@Test(description = "118191-Verify 'Select Org' field in National Account Summary screen")
	public void verifySelectOrgField() {

		final String CASE_NUM = "118191";
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String accountName = "";
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			String defaultLocation = rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION);
			String defaultOrg = rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_ORG);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Save a national accounts
			foundation.click(NationalAccounts.BTN_CREATE);
			accountName = Constants.ACCOUNT_NAME + RandomStringUtils.randomAlphabetic(6);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItemByIndex(NationalAccounts.DPD_CLIENT_NAME, 1);
			foundation.click(NationalAccounts.BTN_SAVE);
			foundation.waitforElement(NationalAccounts.DPD_ORGANIZATION, Constants.SHORT_TIME);

			// Verify hint text of org and location dropdown
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(NationalAccounts.DPD_ORGANIZATION), defaultOrg);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(NationalAccounts.DPD_LOCATION), defaultLocation);

			// Verify if 'Location' dropdown allows selection until the Org is selected
			CustomisedAssert.assertFalse(nationalAccounts.trySelectNonVisibleTextLocation(defaultLocation));
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(NationalAccounts.DPD_LOCATION), defaultLocation);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(NationalAccounts.TXT_FILTER, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
			foundation.click(NationalAccounts.ICON_DELETE);
			foundation.waitforElement(NationalAccounts.BTN_POP_UP_YES, Constants.SHORT_TIME);
			foundation.click(NationalAccounts.BTN_POP_UP_YES);
		}

	}

	@Test(description = "120711-Verify the #Location and Add/Remove Locations Prompt in National Accounts: Client Rules List Screen - Master National Account user")
	public void addRemoveLocationMasterAct() {

		final String CASE_NUM = "120711";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		String ruleName = rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME);
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String org = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			// Click manage
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					gridName);
			foundation.click(adminNationalAccounts.getLocationObj(ruleName));
			foundation.waitforElement(AdminNationalAccounts.BTN_CANCEL_MODAL, Constants.MEDIUM_TIME);

			// Verify title
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_LOCATION_TITLE));

			// validate clear org and clear location functionality
			foundation.click(AdminNationalAccounts.BTN_ORG_CLEAR);
			dropDown.selectItem(AdminNationalAccounts.DPD_ORG_MODAL, org, Constants.TEXT);

			// Verify cancel button functionality
			foundation.click(AdminNationalAccounts.BTN_CANCEL_MODAL);
			foundation.click(adminNationalAccounts.getLocationObj(ruleName));

			// verify selcted org and location displays in dropdown
			dropDown.selectItem(AdminNationalAccounts.DPD_ORG_MODAL, org, Constants.TEXT);
			dropDown.selectItem(AdminNationalAccounts.DPD_LOCATION_MODAL, location, Constants.TEXT);
			foundation.waitforElement(AdminNationalAccounts.DPD_ORG_MODAL, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(AdminNationalAccounts.DPD_ORG_MODAL), org);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(AdminNationalAccounts.DPD_LOCATION_MODAL), location);

			// verify auto add check box and save
			checkBox.check(AdminNationalAccounts.CHK_AUTOADD);
			foundation.click(AdminNationalAccounts.BTN_SAVE_MODAL);
			foundation.threadWait(Constants.TWO_SECOND);
			String locationCount = foundation.getText(adminNationalAccounts.getLocationObj(ruleName));
			CustomisedAssert.assertEquals(locationCount, "1");

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.click(adminNationalAccounts.getLocationObj(ruleName));
			checkBox.check(AdminNationalAccounts.CHK_AUTOADD);
			foundation.click(AdminNationalAccounts.BTN_SAVE_MODAL);
		}
	}

	@Test(description = "120714-Verify the #Location and Add/Remove Locations Prompt in National Accounts: Client Rules List Screen - National Account user")
	public void addRemoveLocationNationalAct() {

		final String CASE_NUM = "120714";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		String ruleName = rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String org = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			// Click manage
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					gridName);
			foundation.click(adminNationalAccounts.getLocationObj(ruleName));
			foundation.waitforElement(AdminNationalAccounts.BTN_CANCEL_MODAL, Constants.MEDIUM_TIME);

			// Verify title
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_LOCATION_TITLE));

			// validate clear org and clear location functionality
			foundation.click(AdminNationalAccounts.BTN_ORG_CLEAR);
			dropDown.selectItem(AdminNationalAccounts.DPD_ORG_MODAL, org, Constants.TEXT);

			// Verify cancel button functionality
			foundation.click(AdminNationalAccounts.BTN_CANCEL_MODAL);
			foundation.click(adminNationalAccounts.getLocationObj(ruleName));

			// verify selcted org and location displays in dropdown
			dropDown.selectItem(AdminNationalAccounts.DPD_ORG_MODAL, org, Constants.TEXT);
			dropDown.selectItem(AdminNationalAccounts.DPD_LOCATION_MODAL, location, Constants.TEXT);
			foundation.waitforElement(AdminNationalAccounts.DPD_ORG_MODAL, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(AdminNationalAccounts.DPD_ORG_MODAL), org);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(AdminNationalAccounts.DPD_LOCATION_MODAL), location);

			// verify auto add check box and save
			checkBox.check(AdminNationalAccounts.CHK_AUTOADD);
			foundation.click(AdminNationalAccounts.BTN_SAVE_MODAL);
			foundation.threadWait(Constants.TWO_SECOND);
			String locationCount = foundation.getText(adminNationalAccounts.getLocationObj(ruleName));
			CustomisedAssert.assertEquals(locationCount, "1");

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.click(adminNationalAccounts.getLocationObj(ruleName));
			checkBox.unCheck(AdminNationalAccounts.CHK_AUTOADD);
			foundation.waitforClikableElement(AdminNationalAccounts.BTN_SAVE_MODAL, Constants.THREE_SECOND);
			foundation.click(AdminNationalAccounts.BTN_SAVE_MODAL);
		}
	}

	@Test(description = "120715-Verify Rule List Columns when National Account user navigated to National Account : Client Rule list screen")
	public void verifyRuleList() {
		try {
			final String CASE_NUM = "120715";

			Map<String, String> dbData = new HashMap<>();
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);
			String ruleName = rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME);
			String nationalAccountName = rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			// Click manage
			adminNationalAccounts.clickManageRule(nationalAccountName, gridName);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE));
			table.selectRow(ruleName);
			CustomisedAssert.assertEquals(foundation.getText(CreateNewRule.TXT_PAGE_TITLE),
					rstNationalAccountsData.get(CNNationalAccounts.RULE_PAGE_TITLE));
			foundation.click(CreateNewRule.BTN_CANCEL);
			foundation.click(CreateNewRule.BTN_NO);

			// Validate table headers
			List<String> columnNames = Arrays.asList(
					rstNationalAccountsData.get(CNNationalAccounts.COLUMN_NAMES).split(Constants.DELIMITER_TILD));
			for (int i = 0; i < columnNames.size(); i++) {
				dbData.put(columnNames.get(i), columnNames.get(i));
			}
			Map<String, String> uiTableHeaher = table
					.getTblHeadersUI(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_HEADER);
			CustomisedAssert.assertEquals(uiTableHeaher, dbData);

			// Verifying Ascending order sorting functionality
			foundation.click(AdminNationalAccounts.LBL_RULE_HEADER);
			CustomisedAssert.assertTrue(adminNationalAccounts.verifySortAscending(gridName));

			// Verifying descending order sorting functionality
			foundation.click(AdminNationalAccounts.LBL_RULE_HEADER);
			CustomisedAssert.assertTrue(adminNationalAccounts.verifySortDescending(gridName));

			// Verifying search functionality
			textBox.enterText(AdminNationalAccounts.TXT_FILTER, ruleName);
			CustomisedAssert.assertEquals(table.getTblRowCount(AdminNationalAccounts.TBL_DATA_ROW), 1);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "120733-Verify Category field in National Accounts:Rule Details Screen - Master National Account user")
	public void verifyCategoryFieldMasterAccount() {

		final String CASE_NUM = "120733";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		String ruleName = "";
		String ruleCategory1 = "";
		String ruleCategory2 = "";
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String organisation = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String ruleType = rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE);
			String rulePrice = rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);

			ruleName = strings.getRandomCharacter();
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.threadWait(Constants.TWO_SECOND);

			// create new national account category
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			ruleCategory1 = createNewNationalAccountsCategory.newNationaAccount();
			ruleCategory2 = createNewNationalAccountsCategory.newNationaAccount();
			foundation.click(CreateNewNationalAccountCategory.BTN_CANCEL_PAGE);

			// validate national account category field
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					gridName);
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.selectOrg(organisation);
			createNewRule.selectLocation(location);
			textBox.enterText(CreateNewRule.TXT_RULE_NAME, ruleName);
			dropDown.selectItem(CreateNewRule.DPD_RULE_TYPE, ruleType, "text");
			foundation.isDisplayed(CreateNewRule.TXT_NATIONAL_CATEGORY);
			dropDown.selectItem(CreateNewRule.DPD_NATIONAL_CATEGORY, ruleCategory1, "text");
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(CreateNewRule.DPD_NATIONAL_CATEGORY), ruleCategory1);
			dropDown.selectItem(CreateNewRule.DPD_NATIONAL_CATEGORY, ruleCategory2, "text");
			textBox.enterText(CreateNewRule.TXT_PRICE, rulePrice);
			foundation.click(CreateNewRule.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			adminNationalAccounts.deleteMangeRule(ruleName);
			foundation.click(AdminNationalAccounts.BTN_CANCEL_MANAGE_RULE);
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			createNewNationalAccountsCategory.deleteNationalAccountCategory(ruleCategory1);
			createNewNationalAccountsCategory.deleteNationalAccountCategory(ruleCategory2);
		}
	}

	@Test(description = "120729-Verify Category field in National Accounts:Rule Details Screen - National Account User")
	public void verifyCategoryFieldNationalAccount() {

		final String CASE_NUM = "120729";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		String ruleName = "";
		String ruleCategory1 = "";
		String ruleCategory2 = "";
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String organisation = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String ruleType = rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE);
			String rulePrice = rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);

			ruleName = strings.getRandomCharacter();
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.threadWait(Constants.TWO_SECOND);

			// create new national account category
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			ruleCategory1 = createNewNationalAccountsCategory.newNationaAccount();
			ruleCategory2 = createNewNationalAccountsCategory.newNationaAccount();
			login.logout();
			login.login(
					propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// validate national account category field
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					gridName);
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.selectOrg(organisation);
			createNewRule.selectLocation(location);
			textBox.enterText(CreateNewRule.TXT_RULE_NAME, ruleName);
			dropDown.selectItem(CreateNewRule.DPD_RULE_TYPE, ruleType, "text");
			foundation.isDisplayed(CreateNewRule.TXT_NATIONAL_CATEGORY);
			dropDown.selectItem(CreateNewRule.DPD_NATIONAL_CATEGORY, ruleCategory1, "text");
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(CreateNewRule.DPD_NATIONAL_CATEGORY), ruleCategory1);
			dropDown.selectItem(CreateNewRule.DPD_NATIONAL_CATEGORY, ruleCategory2, "text");
			textBox.enterText(CreateNewRule.TXT_PRICE, rulePrice);
			foundation.click(CreateNewRule.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			adminNationalAccounts.deleteMangeRule(ruleName);
			login.logout();
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			createNewNationalAccountsCategory.deleteNationalAccountCategory(ruleCategory1);
			createNewNationalAccountsCategory.deleteNationalAccountCategory(ruleCategory2);
		}
	}

	@Test(description = "120737-Verify Master National Account user will able to save the new Rule when user provides the Unique Rule name")
	public void saveNewRuleMasterAccount() {

		final String CASE_NUM = "120737";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		String ruleCategory = "";
		String ruleName = "";

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			String organisation = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String ruleType = rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE);
			String rulePrice = rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);

			ruleName = strings.getRandomCharacter();
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.threadWait(Constants.TWO_SECOND);

			// create new national account category
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			ruleCategory = createNewNationalAccountsCategory.newNationaAccount();
			foundation.click(CreateNewNationalAccountCategory.BTN_CANCEL_PAGE);

			// save new rule
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					gridName);
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.createRule(organisation, location, ruleType, ruleCategory, ruleName, rulePrice);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			adminNationalAccounts.deleteMangeRule(ruleName);
			foundation.click(AdminNationalAccounts.BTN_CANCEL_MANAGE_RULE);
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			createNewNationalAccountsCategory.deleteNationalAccountCategory(ruleCategory);
		}
	}

	@Test(description = "120736-Verify National Account user will able to save the new Rule when user provides the Unique Rule name")
	public void saveNewRuleNationalAccount() {

		final String CASE_NUM = "120736";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		String ruleName = "";
		String ruleCategory = "";
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String organisation = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String ruleType = rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE);
			String rulePrice = rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);

			ruleName = strings.getRandomCharacter();
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.threadWait(Constants.TWO_SECOND);

			// create new national account category
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			ruleCategory = createNewNationalAccountsCategory.newNationaAccount();
			login.logout();
			login.login(
					propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to national account page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			// validate save a rule with unique name
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					gridName);
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.createRule(organisation, location, ruleType, ruleCategory, ruleName, rulePrice);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			adminNationalAccounts.deleteMangeRule(ruleName);
			login.logout();
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			createNewNationalAccountsCategory.deleteNationalAccountCategory(ruleCategory);
		}
	}

	@Test(description = "120739-Verify the Rule name will allows the Alphanumeric values with Master National Account User")
	public void ruleNameAlphaNumericMasterAccount() {

		final String CASE_NUM = "120739";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		String ruleName = " ";
		String ruleCategory = " ";
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			ruleName = strings.getRandomCharacter() + " " + numbers.generateRandomNumber(2, 4);
			String organisation = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String ruleType = rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE);
			String rulePrice = rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.threadWait(Constants.TWO_SECOND);

			// create new national account category
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			ruleCategory = createNewNationalAccountsCategory.newNationaAccount();
			foundation.click(CreateNewNationalAccountCategory.BTN_CANCEL_PAGE);

			// save new rule
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					gridName);
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.createRule(organisation, location, ruleType, ruleCategory, ruleName, rulePrice);
			foundation.threadWait(Constants.THREE_SECOND);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			adminNationalAccounts.deleteMangeRule(ruleName);
			foundation.click(AdminNationalAccounts.BTN_CANCEL_MANAGE_RULE);
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			createNewNationalAccountsCategory.deleteNationalAccountCategory(ruleCategory);
		}
	}

	@Test(description = "120738-Verify the Rule name will allows the Alphanumeric values with National Account User")
	public void ruleNameAlphaNumericNationalAccount() {

		final String CASE_NUM = "120738";
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
		String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		String ruleName = "";
		String ruleCategory = "";

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			String organisation = rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED);
			String location = rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED);
			String ruleType = rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE);
			String rulePrice = rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE);
			String gridName = rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.threadWait(Constants.TWO_SECOND);

			// create new national account category
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			ruleCategory = createNewNationalAccountsCategory.newNationaAccount();
			login.logout();
			login.login(
					propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to national account page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT));

			// validate save a rule with unique name
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
					gridName);
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			ruleName = strings.getRandomCharacter() + " " + numbers.generateRandomNumber(2, 4);
			createNewRule.createRule(organisation, location, ruleType, ruleCategory, ruleName, rulePrice);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			adminNationalAccounts.deleteMangeRule(ruleName);
			login.logout();
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);
			foundation.click(AdminNationalAccounts.BTN_NATIONAL_ACCOUNT_CATEGORY);
			createNewNationalAccountsCategory.deleteNationalAccountCategory(ruleCategory);
		}
	}
}
