package at.smartshop.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNationalAccounts;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Configuration;
import at.smartshop.pages.AdminNationalAccounts;
import at.smartshop.pages.CreateNewRule;
import at.smartshop.pages.NationalAccounts;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserRoles;
import at.smartshop.pages.ViewRole;
import at.smartshop.utilities.CurrenyConverter;

@Listeners(at.framework.reportsetup.Listeners.class)
public class NationalAccount extends TestInfra{
		private ResultSets dataBase = new ResultSets();
		private NavigationBar navigationBar = new NavigationBar();
		private UserRoles userRoles = new UserRoles();
		private TextBox textBox = new TextBox();
		private CheckBox checkbox = new CheckBox();
		private Dropdown dropDown = new Dropdown();
		private ViewRole viewRole = new ViewRole();
		private Table table = new Table();
		private Foundation foundation = new Foundation();
		private AdminNationalAccounts adminNationalAccounts = new AdminNationalAccounts();
		private CurrenyConverter converter = new CurrenyConverter();
		private CreateNewRule createNewRule=new CreateNewRule();
		
		private Map<String, String> rstNavigationMenuData;
		private Map<String, String> rstLocationSummaryData;
		private Map<String, String> rstNationalAccountsData;
		
	@Test(description = "This test verifies Master National Account PresetRole Permissions")
	public void verifyMasterNationalAccountPermission() {
		try {
			final String CASE_NUM = "118214";
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData =dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData =dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			
			//Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			
			foundation.click(UserList.BTN_MANAGE_ROLES);
			assertTrue(foundation.isDisplayed(UserRoles.BTN_CREATE_NEW_ROLE, "Create New Role"));
			
			textBox.enterText(UserRoles.TXT_SEARCH_FILTER, "Master National Account");
			List<String> requiredData = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			userRoles.selectRowByText(requiredData.get(0));
			
			assertTrue(foundation.isDisplayed(UserRoles.LBL_VIEW_ROLE, "View Role Screen"));
			foundation.click(UserRoles.TAB_ADMIN);
			
			String lblNationalAccountCategories = foundation.getText(ViewRole.LBL_NATIONAL_ACCOUNT_CATEGORIES);
			String lblNationalAccountsLocksRules = foundation.getText(ViewRole.LBL_NATIONAL_ACCOUNT_LOCKS_RULES);
			
			assertEquals(lblNationalAccountCategories, requiredData.get(1));
			assertEquals(lblNationalAccountsLocksRules, requiredData.get(2));
			
			viewRole.isAllCheckboxChecked(lblNationalAccountCategories);
			viewRole.isAllCheckboxChecked(lblNationalAccountsLocksRules);

			} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(description = "This test is to validate add National Account functionality")
	public void verifNationalAccAddFunctionality() {
		try {
			final String CASE_NUM = "118194";
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData =dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData =dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			
			//Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			
			//Entering fields in New National Account
			foundation.click(NationalAccounts.BTN_CREATE);
			final String accountName = "test"+RandomStringUtils.randomAlphabetic(6);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItem(NationalAccounts.DPD_CLIENT_NAME, rstNationalAccountsData.get(CNNationalAccounts.CLIENT_NAME), Constants.TEXT);
			foundation.click(NationalAccounts.BTN_SAVE);
			
			//Selecting Orginization and Location
			foundation.waitforElement(NationalAccounts.DPD_ORGINIZATION, 2000);
			dropDown.selectItem(NationalAccounts.DPD_ORGINIZATION, rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED), Constants.TEXT);
			dropDown.selectItem(NationalAccounts.DPD_LOCATION, rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED), Constants.TEXT);			
			
			foundation.click(NationalAccounts.BTN_ADD_NATIONAL_ACCOUNT);
			textBox.enterText(NationalAccounts.TXT_FILTER, rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			
			String org = foundation.getText(NationalAccounts.LBL_ORGINIZATION);
			String location = foundation.getText(NationalAccounts.LBL_LOCATION);
			
			//Validate the fields Orginazation and location
			assertEquals(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED), org);
			assertEquals(rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED), location);
			
			//Resetting Data by Deleting created National Product
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(NationalAccounts.TBL_BODY, 2000);
			
			textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
			foundation.click(NationalAccounts.ICO_DELETE);
			
			foundation.click(NationalAccounts.BTN_POP_UP_YES);
			
			
			} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(description = "This test is to validate cancel to National Account functionality")
	public void verifNationalAccCancelFunctionality() {
		try {
			final String CASE_NUM = "118188";
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData =dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData =dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			
			//Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			
			//Entering fields in New National Account
			foundation.click(NationalAccounts.BTN_CREATE);
			final String accountName = "test"+RandomStringUtils.randomAlphabetic(6);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, accountName);
			dropDown.selectItem(NationalAccounts.DPD_CLIENT_NAME, rstNationalAccountsData.get(CNNationalAccounts.CLIENT_NAME), Constants.TEXT);
			foundation.click(NationalAccounts.BTN_CANCEL);
			
			assertTrue(foundation.isDisplayed(NationalAccounts.LBL_NATIONAL_ACCOUNT, "National Account Page"));
			
			foundation.waitforElement(NationalAccounts.TBL_BODY, 2000);
			textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
			Assert.assertTrue(table.getTblRowCount(NationalAccounts.TBL_ROW)<= 0);
			
			List<String> nationalAccount = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME).split(Constants.DELIMITER_TILD));
			textBox.enterText(NationalAccounts.TXT_FILTER, nationalAccount.get(1));
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME), nationalAccount.get(1));
			
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, nationalAccount.get(1)+"test");
			foundation.click(NationalAccounts.BTN_CANCEL);
			
			assertTrue(foundation.isDisplayed(NationalAccounts.LBL_NATIONAL_ACCOUNT, "National Account Page"));
			
			foundation.waitforElement(NationalAccounts.TBL_BODY, 2000);
			textBox.enterText(NationalAccounts.TXT_FILTER, nationalAccount.get(1)+"test");
			Assert.assertTrue(table.getTblRowCount(NationalAccounts.TBL_ROW)<= 0);
			
			} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "This test is to validate Validate grid columns in National Account:Client List screen for National Account user")
	public void verifGridColumnInNationalAccount() {
		try {
			final String CASE_NUM = "120712";
			Map<String, String> dbData = new HashMap<>();
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
            login.login(propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
            
            //Reading data from database
            rstNavigationMenuData =dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData =dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			
			//Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT, "National Accounts"));	
			
			//Navigate to National Account
	        List<String> nationalAccount = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME).split(Constants.DELIMITER_TILD));
			adminNationalAccounts.clickManageRule(nationalAccount.get(0),rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
	        Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_TITLE, "National Accounts table id"));
	        
	        //Verifying search functionality
	        textBox.enterText(AdminNationalAccounts.TXT_FILTER,nationalAccount.get(1));
	        Assert.assertTrue(table.getTblRowCount(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_RULE_LIST)== 1);	
	        
	        textBox.enterText(AdminNationalAccounts.TXT_FILTER,nationalAccount.get(2));
	        Assert.assertTrue(table.getTblRowCount(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_RULE_LIST)== 0);	
	        
	        //Verifying Ascending order sorting functionality
	        foundation.refreshPage(); 
	        List<String> columnNames = Arrays.asList(rstNationalAccountsData.get(CNNationalAccounts.COLUMN_NAMES).split(Constants.DELIMITER_TILD));
	        foundation.click(AdminNationalAccounts.LBL_RULE_NAME_HEADER);
            Assert.assertTrue(adminNationalAccounts.verifySortAscending(rstNationalAccountsData.get(CNNationalAccounts.GRID_RULE_NAME)));
			
            //Verifying descending order sorting functionality
            foundation.click(AdminNationalAccounts.LBL_RULE_NAME_HEADER);
			Assert.assertTrue(adminNationalAccounts.verifySortDescending(rstNationalAccountsData.get(CNNationalAccounts.GRID_RULE_NAME)));
	        
			//Verifying column headers
			foundation.refreshPage(); 
	        for (int i=0;i<columnNames.size();i++) {
	        		dbData.put(columnNames.get(i), columnNames.get(i));
	        }
	        
	        Map<String, String> uiTableHeaher = table.getTblHeadersUI(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_HEADER);
			assertEquals(uiTableHeaher, dbData);
			
			//Verifying rule type dropdown values
			foundation.refreshPage(); 
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));

			List<String> ruleType = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(0)));
			Assert.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(1)));
			Assert.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(2)));
		
			//Selecting UPC rule type and rule category
			List<String> ruleCategory = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_CATEGORY).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(1), Constants.TEXT);
			dropDown.selectItem(AdminNationalAccounts.DPD_NA_CATEGORY, ruleCategory.get(1), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Verifying UPC rule setting in table
			Map<String, String> upcData = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(upcData.get(Constants.RULETYPE),ruleType.get(1));
			Assert.assertEquals(upcData.get(Constants.UPCS),ruleCategory.get(1));
			Assert.assertEquals(upcData.get(Constants.NATIONAL_CATEGORY),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			
			//Selecting National Category rule type and rule category
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(2), Constants.TEXT);
			dropDown.selectItem(AdminNationalAccounts.DPD_NA_CATEGORY, ruleCategory.get(0), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Verifying national category rule setting in table
			Map<String, String> NAData = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(NAData.get(Constants.RULETYPE),ruleType.get(3));
			Assert.assertEquals(NAData.get(Constants.NATIONAL_CATEGORY),ruleCategory.get(0));
			Assert.assertEquals(NAData.get(Constants.UPCS),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			
			//getting rule price from UI
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			double uiRulePrice = Double.parseDouble((foundation.getTextAttribute(AdminNationalAccounts.TXT_RULE_PRICE)));	
			String rulePrice=converter.convertTOCurrency(uiRulePrice);

			//Setting rule to No More Than
			List<String> dbRulePrice= Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(1), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Validating price set for rule to No More Than
			Map<String, String> rulePriceNoMoreThan = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(rulePriceNoMoreThan.get(Constants.MAX_PRICE),rulePrice);
			Assert.assertEquals(rulePriceNoMoreThan.get(Constants.MIN_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			Assert.assertEquals(rulePriceNoMoreThan.get(Constants.EXACT_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			
			//Setting rule to No Less Than
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(2), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Validating price set for No Less Than
			Map<String, String> rulePriceNoLessThan = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(rulePriceNoLessThan.get(Constants.MAX_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			Assert.assertEquals(rulePriceNoLessThan.get(Constants.MIN_PRICE),rulePrice);
			Assert.assertEquals(rulePriceNoLessThan.get(Constants.EXACT_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			
			//Setting rule to Force Exact Price
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(0), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Validating price set for Force Exact Price
			Map<String, String> ruleForceExactPrice = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(ruleForceExactPrice.get(Constants.MAX_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			Assert.assertEquals(ruleForceExactPrice.get(Constants.MIN_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			Assert.assertEquals(ruleForceExactPrice.get(Constants.EXACT_PRICE),rulePrice);
			
			//Setting rule status to Inactive
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			checkbox.unCheck(AdminNationalAccounts. CHK_RULE_STATUS);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			Map<String, String> ruleStatusInActive = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			
			//Validating rule status is Inactive
			List<String> rulesStatus= Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_STATUS).split(Constants.DELIMITER_TILD));
			Assert.assertEquals(ruleStatusInActive.get(Constants.RULE_STATUS),rulesStatus.get(1));
			
			//Setting rule status to Active
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			checkbox.check(AdminNationalAccounts.CHK_RULE_STATUS);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Validating rule status is Active
			Map<String, String> ruleStatusActive = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(ruleStatusActive.get(Constants.RULE_STATUS),rulesStatus.get(0));

			//Setting Location tagged
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			foundation.click(AdminNationalAccounts.BTN_LOCATION_CLEAR_RULE);
			dropDown.selectItem(AdminNationalAccounts.DPD_LOCATION_RULE, rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED),Constants.TEXT );
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Validating Location tagged count
			Map<String, String> locationCount = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(locationCount.get(Constants.LOCATION),rstNationalAccountsData.get(CNNationalAccounts.LOCATION_COUNT));
			
			//Validating Location Add or Remove Location from Rule prompt from table
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.LOCATION_COUNT));
			foundation.waitforElement(AdminNationalAccounts.BTN_CANCEL, 2000);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CANCEL, "Add or Remove Location from Rule prompt window"));
			
			foundation.click(AdminNationalAccounts.BTN_CANCEL);
			foundation.click(AdminNationalAccounts.ICO_DELETE);
			
			//Validating Delete Confirm Prompt
			foundation.waitforElement(AdminNationalAccounts.POP_UP_BTN_CANCEL, 2000);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.POP_UP_BTN_CANCEL, "Delete confirmation window"));
			foundation.click(AdminNationalAccounts.POP_UP_BTN_CANCEL);
			} catch (Exception exc) {
			Assert.fail();
		}
	}
	
	@Test(description = "This test is to validate grid columns in Master National Account:Client List screen for Master National Account user")
	public void verifGridColumnInMasterNationalAccount() {
		try {
			final String CASE_NUM = "120713";
			Map<String, String> dbData = new HashMap<>();
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
            login.login(propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
            
          //Reading data from database
            rstNavigationMenuData =dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData =dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			
			//Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT, "National Accounts"));	
			
			//Navigate to National Account
	        List<String> nationalAccount = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME).split(Constants.DELIMITER_TILD));
			adminNationalAccounts.clickManageRule(nationalAccount.get(0),rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
	        Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_TITLE, "National Accounts table id"));
	        
	        //Verifying search functionality
	        textBox.enterText(AdminNationalAccounts.TXT_FILTER,nationalAccount.get(1));
	        Assert.assertTrue(table.getTblRowCount(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_RULE_LIST)== 1);	
	        
	        textBox.enterText(AdminNationalAccounts.TXT_FILTER,nationalAccount.get(2));
	        Assert.assertTrue(table.getTblRowCount(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_RULE_LIST)== 0);	
	        
	        //Verifying Ascending order sorting functionality
	        foundation.refreshPage(); 
	        List<String> columnNames = Arrays.asList(rstNationalAccountsData.get(CNNationalAccounts.COLUMN_NAMES).split(Constants.DELIMITER_TILD));
	        foundation.click(AdminNationalAccounts.LBL_RULE_NAME_HEADER);
            Assert.assertTrue(adminNationalAccounts.verifySortAscending(rstNationalAccountsData.get(CNNationalAccounts.GRID_RULE_NAME)));
			
            //Verifying descending order sorting functionality
            foundation.click(AdminNationalAccounts.LBL_RULE_NAME_HEADER);
			Assert.assertTrue(adminNationalAccounts.verifySortDescending(rstNationalAccountsData.get(CNNationalAccounts.GRID_RULE_NAME)));
	        
			//Verifying column headers
			foundation.refreshPage(); 
	        for (int i=0;i<columnNames.size();i++) {
	        		dbData.put(columnNames.get(i), columnNames.get(i));
	        }
	        
	        Map<String, String> uiTableHeaher = table.getTblHeadersUI(AdminNationalAccounts.TBL_NATIONAL_ACCOUNT_HEADER);
			assertEquals(uiTableHeaher, dbData);
			
			//Verifying rule type dropdown values
			foundation.refreshPage(); 
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));

			List<String> ruleType = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(0)));
			Assert.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(1)));
			Assert.assertTrue(dropDown.verifyItemPresent(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(2)));
		
			//Selecting UPC rule type and rule category
			List<String> ruleCategory = Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_CATEGORY).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(1), Constants.TEXT);
			dropDown.selectItem(AdminNationalAccounts.DPD_NA_CATEGORY, ruleCategory.get(1), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Verifying UPC rule setting in table
			Map<String, String> upcData = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(upcData.get(Constants.RULETYPE),ruleType.get(1));
			Assert.assertEquals(upcData.get(Constants.UPCS),ruleCategory.get(1));
			Assert.assertEquals(upcData.get(Constants.NATIONAL_CATEGORY),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			
			//Selecting National Category rule type and rule category
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_TYPE, ruleType.get(2), Constants.TEXT);
			dropDown.selectItem(AdminNationalAccounts.DPD_NA_CATEGORY, ruleCategory.get(0), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Verifying national category rule setting in table
			Map<String, String> NAData = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(NAData.get(Constants.RULETYPE),ruleType.get(3));
			Assert.assertEquals(NAData.get(Constants.NATIONAL_CATEGORY),ruleCategory.get(0));
			Assert.assertEquals(NAData.get(Constants.UPCS),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			
			//getting rule price from UI
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			double uiRulePrice = Double.parseDouble((foundation.getTextAttribute(AdminNationalAccounts.TXT_RULE_PRICE)));	
			String rulePrice=converter.convertTOCurrency(uiRulePrice);

			//Setting rule to No More Than
			List<String> dbRulePrice= Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(1), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Validating price set for rule to No More Than
			Map<String, String> rulePriceNoMoreThan = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(rulePriceNoMoreThan.get(Constants.MAX_PRICE),rulePrice);
			Assert.assertEquals(rulePriceNoMoreThan.get(Constants.MIN_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			Assert.assertEquals(rulePriceNoMoreThan.get(Constants.EXACT_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			
			//Setting rule to No Less Than
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(2), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Validating price set for No Less Than
			Map<String, String> rulePriceNoLessThan = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(rulePriceNoLessThan.get(Constants.MAX_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			Assert.assertEquals(rulePriceNoLessThan.get(Constants.MIN_PRICE),rulePrice);
			Assert.assertEquals(rulePriceNoLessThan.get(Constants.EXACT_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			
			//Setting rule to Force Exact Price
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			dropDown.selectItem(AdminNationalAccounts.DPD_RULE_PRICE, dbRulePrice.get(0), Constants.TEXT);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Validating price set for Force Exact Price
			Map<String, String> ruleForceExactPrice = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(ruleForceExactPrice.get(Constants.MAX_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			Assert.assertEquals(ruleForceExactPrice.get(Constants.MIN_PRICE),rstNationalAccountsData.get(CNNationalAccounts.MIN_MAX_EXACT_PRICE));
			Assert.assertEquals(ruleForceExactPrice.get(Constants.EXACT_PRICE),rulePrice);
			
			//Setting rule status to Inactive
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			checkbox.unCheck(AdminNationalAccounts. CHK_RULE_STATUS);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			Map<String, String> ruleStatusInActive = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			
			//Validating rule status is Inactive
			List<String> rulesStatus= Arrays
					.asList(rstNationalAccountsData.get(CNNationalAccounts.RULE_STATUS).split(Constants.DELIMITER_TILD));
			Assert.assertEquals(ruleStatusInActive.get(Constants.RULE_STATUS),rulesStatus.get(1));
			
			//Setting rule status to Active
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			checkbox.check(AdminNationalAccounts.CHK_RULE_STATUS);
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Validating rule status is Active
			Map<String, String> ruleStatusActive = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(ruleStatusActive.get(Constants.RULE_STATUS),rulesStatus.get(0));

			//Setting Location tagged
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			foundation.click(AdminNationalAccounts.BTN_LOCATION_CLEAR_RULE);
			dropDown.selectItem(AdminNationalAccounts.DPD_LOCATION_RULE, rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED),Constants.TEXT );
			foundation.click(AdminNationalAccounts.BTN_SAVE);
			
			//Validating Location tagged count
			Map<String, String> locationCount = table.getTblRecordsUI(AdminNationalAccounts.TBL_DATA_GRID,AdminNationalAccounts.TBL_DATA_ROW);
			Assert.assertEquals(locationCount.get(Constants.LOCATION),rstNationalAccountsData.get(CNNationalAccounts.LOCATION_COUNT));
			
			//Validating Location Add or Remove Location from Rule prompt from table
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.LOCATION_COUNT));
			foundation.waitforElement(AdminNationalAccounts.BTN_CANCEL, 2000);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CANCEL, "Add or Remove Location from Rule prompt window"));
			
			foundation.click(AdminNationalAccounts.BTN_CANCEL);
			foundation.click(AdminNationalAccounts.ICO_DELETE);
			
			//Validating Delete Confirm Prompt
			foundation.waitforElement(AdminNationalAccounts.POP_UP_BTN_CANCEL, 2000);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.POP_UP_BTN_CANCEL, "Delete confirmation window"));
			foundation.click(AdminNationalAccounts.POP_UP_BTN_CANCEL);
			} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "This test This test validates Location Dropdown in the National Account Edit rule screen with NA User")
	public void verifyLocationDropdownNAUser() {
		try {
			final String CASE_NUM = "120731";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),	
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT, "National Accounts"));
			

			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
																		rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
		
			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, 5);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, "National Account Rule Lablel"));
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE, "Create New Button"));
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.selectOrg(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			createNewRule.verifyHintText(rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION));
			createNewRule.selectLocation(rstNationalAccountsData.get(CNNationalAccounts.LOCATION));
			createNewRule.verifyAutoAddNewLocationRuleStatus();			
			
		}catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "This test This test validates Location Dropdown in the National Account Edit rule screen with MNA User")
	public void verifyLocationDropdownMNAUser() {
		try {
			final String CASE_NUM = "120728";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),	
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT, "National Accounts"));
			

			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
																		rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
		
			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, 5);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, "National Account Rule Lablel"));
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE, "Create New Button"));
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.selectOrg(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			createNewRule.verifyHintText(rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION));
			createNewRule.selectLocation(rstNationalAccountsData.get(CNNationalAccounts.LOCATION));
			createNewRule.verifyAutoAddNewLocationRuleStatus();			
			
		}catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "This test validates Orgs Dropdown in the National Account Edit rule screen with NA User")
	public void verifyOrgsDropdownNAUser(){
		try {			
			final String CASE_NUM="120730";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),	
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
	
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT, "National Accounts"));
			
			
			List<String> locationVaules= adminNationalAccounts.getLocationDetails(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME), 
																														rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
																		rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
		
			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, 5);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, "National Account Rule Lablel"));
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE, "Create New Button"));
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			
			createNewRule.verifyHintText(rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION));
			createNewRule.selectOrg(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			List<String> locationDPDValues = createNewRule.getLocationDropdownValues();
			Assert.assertTrue(locationVaules.equals(locationDPDValues));

			
		}catch (Exception exc) {
			Assert.fail();
		}
	}
	
	@Test(description = "This test validates Orgs Dropdown in the National Account Edit rule screen with MNA User")
	public void verifyOrgsDropdownMNAUser(){
		try {			
			final String CASE_NUM="120726";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),	
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
	
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT, "National Accounts"));
			
			
			List<String> locationVaules= adminNationalAccounts.getLocationDetails(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME), 
																														rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
																		rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
		
			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, 5);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, "National Account Rule Lablel"));
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.BTN_CREATE_NEW_RULE, "Create New Button"));
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			
			createNewRule.verifyHintText(rstNationalAccountsData.get(CNNationalAccounts.DEFAULT_DROPDOWN_LOCATION));
			createNewRule.selectOrg(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED));
			List<String> locationDPDValues = createNewRule.getLocationDropdownValues();
			Assert.assertTrue(locationVaules.equals(locationDPDValues));

			
		}catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "This test validates National Account Client Rule List Screen")
	public void verifyClientRuleScreen() {
		try {
		
			final String CASE_NUM="120722";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),	
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT, "National Accounts"));
			
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
																		rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
			
			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, 5);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, "National Account Rule Lablel"));
			adminNationalAccounts.selectRuleName(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			adminNationalAccounts.verifyRuleName(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));			
		
			
		}catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "This test validates the Delete Button and Confirm Delete prompt in Client Rule detail Screen")
	public void verifyDeleteButton() {
		try {
			
			final String CASE_NUM = "120723";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),	
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT, "National Accounts"));
			
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
																		rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
			
			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, 5);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, "National Account Rule Lablel"));
			adminNationalAccounts.selectRuleName(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME));
			
			assertTrue(foundation.isDisplayed(CreateNewRule.BTN_DELETE, "Delete Button"));
			foundation.click(CreateNewRule.BTN_DELETE);
			
			createNewRule.verifyDeletePromptTitle(rstNationalAccountsData.get(CNNationalAccounts.PROMPT_TITLE));
			createNewRule.verifyDeletePromptButtons();
			createNewRule.clickOnCancelButton();
			createNewRule.clickOnYesButton();
			foundation.click(AdminNationalAccounts.BTN_CREATE_NEW_RULE);
			createNewRule.createRule(rstNationalAccountsData.get(CNNationalAccounts.ORG_ASSIGNED), 
													rstNationalAccountsData.get(CNNationalAccounts.LOCATION_TAGGED), 
													rstNationalAccountsData.get(CNNationalAccounts.RULE_TYPE), 
													rstNationalAccountsData.get(CNNationalAccounts.RULE_CATEGORY), 
													rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME), 
													rstNationalAccountsData.get(CNNationalAccounts.RULE_PRICE));
			
		}catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "This test validates the Auto Add New Locations Rule check box with MNA")
	public void validateAutoAddNewLocationsMNA() {
		try {
			final String CASE_NUM = "120734";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER, FilePath.PROPERTY_CONFIG_FILE),	
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);
			
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT, "National Accounts"));
			
			adminNationalAccounts.clickManageRule(rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME),
																		rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME));
			
			foundation.waitforElement(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, 5);
			assertTrue(foundation.isDisplayed(AdminNationalAccounts.LBL_NATIONAL_ACCOUNT_RULE, "National Account Rule Lablel"));
			adminNationalAccounts.selectRuleName(rstNationalAccountsData.get(CNNationalAccounts.RULE_NAME));
			foundation.click(CreateNewRule.CHCK_AUTO_LOCATION);
					
		}catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
