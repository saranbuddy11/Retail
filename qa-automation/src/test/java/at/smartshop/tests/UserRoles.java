package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNUserRoles;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserSummary;

@Listeners(at.framework.reportsetup.Listeners.class)
public class UserRoles extends TestInfra {

	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	private Table table = new Table();
	private Strings string = new Strings();
	private Foundation foundation = new Foundation();
	private ResultSets dataBase = new ResultSets();
	private UserList userList = new UserList();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstUserRolesData;
	private Map<String, String> rstConsumerSearchData;

	@Test(description = "118208-This test is to validate 'Client' drop down field for Super and Master National Account user")
	public void verifClientDropdownField() {
		try {

			final String CASE_NUM = "118208";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.BTN_MANAGE_ROLES));
			List<String> lblRowRecord = Arrays
					.asList(rstUserRolesData.get(CNUserRoles.ROW_RECORD).split(Constants.DELIMITER_TILD));
			textBox.enterText(UserList.TXT_FILTER, lblRowRecord.get(0));
			table.selectRow(lblRowRecord.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserSummary.DPD_CLIENT));

			List<String> clientdropDownList = Arrays
					.asList(rstUserRolesData.get(CNUserRoles.CLIENT_DROPDOWN).split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(0)));
			CustomisedAssert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(1)));
			CustomisedAssert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(2)));

			login.logout();
			login.login(
					propertyFile.readPropertyFile(Configuration.MASTER_NATIONAL_ACCOUNT_USER,
							FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(1));

			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.BTN_MANAGE_ROLES));
			textBox.enterText(UserList.TXT_FILTER, lblRowRecord.get(1));
			table.selectRow(lblRowRecord.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserSummary.DPD_CLIENT));
			CustomisedAssert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(0)));
			CustomisedAssert.assertTrue(dropDown.verifyItemPresent(UserSummary.DPD_CLIENT, clientdropDownList.get(1)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166000-Enter all the valid details in the fields and click on Cancel button then Save button")
	public void UsersAndRolesCancelAndSaveValidDetails() {

		final String CASE_NUM = "166000";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);

		final String device = rstUserRolesData.get(CNUserRoles.ROLE_NAME) + string.getRandomCharacter();
		final String rowData = rstUserRolesData.get(CNUserRoles.ROW_RECORD);

		List<String> dropdownData = Arrays
				.asList(rstUserRolesData.get(CNUserRoles.CLIENT_DROPDOWN).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(UserList.CREATE_NEW_ROLE);
			foundation.click(UserList.DRP_SELECT_ROLE);
			textBox.enterText(UserList.FIRST_NAME_FIELD, device);
			textBox.enterText(UserList.LAST_NAME_FIELD, device);
			textBox.enterText(UserList.EMAIL_ADDRESS_FIELD, device + rowData);
			foundation.click(UserList.GENERATE_PIN);
			dropDown.selectItem(UserList.SELECT_LOCATION, dropdownData.get(0), Constants.TEXT);
			foundation.click(UserList.CLICK_OUTSIDE);
			foundation.click(UserList.SELECT_CLIENT);
			textBox.enterText(UserList.ENTER_CLIENT, dropdownData.get(1));
			foundation.click(UserList.CLICK_CLIENT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(UserList.SELECT_NATIONAL_ACCOUNT);
			textBox.enterText(UserList.SELECT_NATIONAL_ACCOUNT, dropdownData.get(2));
			foundation.click(UserList.CLICK_NATIONAL_ACCOUNT);

			// Click on cancel Button
			foundation.click(UserList.CANCEL_USER);
			foundation.click(UserList.CONFIRM_CANCEL);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.CREATE_NEW_ROLE));

			// Create New
			foundation.click(UserList.CREATE_NEW_ROLE);
			foundation.click(UserList.DRP_SELECT_ROLE);
			textBox.enterText(UserList.FIRST_NAME_FIELD, device);
			textBox.enterText(UserList.LAST_NAME_FIELD, device);
			textBox.enterText(UserList.EMAIL_ADDRESS_FIELD, device + rowData);
			foundation.click(UserList.GENERATE_PIN);
			dropDown.selectItem(UserList.SELECT_LOCATION, dropdownData.get(0), Constants.TEXT);
			foundation.click(UserList.CLICK_OUTSIDE);
			foundation.click(UserList.SELECT_CLIENT);
			textBox.enterText(UserList.ENTER_CLIENT, dropdownData.get(1));
			foundation.click(UserList.CLICK_CLIENT);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(UserList.SELECT_NATIONAL_ACCOUNT);
			textBox.enterText(UserList.SELECT_NATIONAL_ACCOUNT, dropdownData.get(2));
			foundation.click(UserList.CLICK_NATIONAL_ACCOUNT);

			// Click on Save Button
			foundation.click(UserList.SAVE_USER);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.waitforElement(UserList.DISABLE_USER, Constants.SHORT_TIME);
			foundation.click(UserList.DISABLE_USER);
			foundation.click(UserList.CONFIRM_DISABLE);
		}
	}

	@Test(description = "166005-Update the previous User details in the fields and click on Cancel button and then Save button")
	public void UpdateUsersAndRolesCancelAndSaveDetails() {

		final String CASE_NUM = "166005";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);

		final String device = rstUserRolesData.get(CNUserRoles.ROLE_NAME);
		List<String> updatedData = Arrays
				.asList(rstUserRolesData.get(CNUserRoles.ROW_RECORD).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Search for already created User
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.SEARCH_FILTER));
			textBox.enterText(UserList.SEARCH_FILTER, updatedData.get(0));
			foundation.click(UserList.TBL_DATA);
			textBox.enterText(UserList.FIRST_NAME_FIELD, updatedData.get(1));
			textBox.enterText(UserList.LAST_NAME_FIELD, updatedData.get(1));

			// Click on CANCEL Button
			foundation.click(UserList.CANCEL_USER);
			foundation.click(UserList.CONFIRM_CANCEL);

			// Search for already created User
			foundation.waitforElement(UserList.SEARCH_FILTER, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.CREATE_NEW));
			textBox.enterText(UserList.SEARCH_FILTER, updatedData.get(0));
			foundation.click(UserList.TBL_DATA);
			textBox.enterText(UserList.FIRST_NAME_FIELD, updatedData.get(1));
			textBox.enterText(UserList.LAST_NAME_FIELD, updatedData.get(1));

			// Click on Update User Button
			foundation.click(UserList.SAVE_USER);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// resetting test data
			foundation.waitforElement(UserList.SEARCH_FILTER, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.SEARCH_FILTER));
			// textBox.enterText(UserList.SEARCH_FILTER, updatedData.get(1));
			foundation.click(UserList.TBL_DATA);
			textBox.enterText(UserList.FIRST_NAME_FIELD, device);
			textBox.enterText(UserList.LAST_NAME_FIELD, device);
			foundation.click(UserList.SAVE_USER);
			foundation.waitforElement(UserList.SEARCH_FILTER, Constants.SHORT_TIME);
		}
	}

	@Test(description = "166020 - Update the User details on Manage User Roles")
	public void UpdateManageUserRolesDetails() {

		final String CASE_NUM = "166020";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);

		final String device = rstUserRolesData.get(CNUserRoles.ROLE_NAME);

		List<String> security = Arrays
				.asList(rstUserRolesData.get(CNUserRoles.ROW_RECORD).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Search for already created User
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.SEARCH_FILTER));
			textBox.enterText(UserList.SEARCH_FILTER, device);
			foundation.click(UserList.TBL_DATA);

			// Check for Manage User Roles and click cancel
			foundation.click(UserList.MANAGE_USER_ROLES);
			dropDown.selectItem(UserList.SECURITY_ROLE, security.get(0), Constants.TEXT);
			foundation.click(UserList.EXPIRATION_DATE);
			foundation.click(UserList.SELECT_EXPIRATION_DATE);
			foundation.click(UserList.SEND_NOTIFICATION);
			foundation.click(UserList.CANCEL_USER_ROLE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.SEARCH_FILTER));

			// Check for Manage User Roles and click save
			textBox.enterText(UserList.SEARCH_FILTER, device);
			foundation.click(UserList.TBL_DATA);
			foundation.click(UserList.MANAGE_USER_ROLES);
			dropDown.selectItem(UserList.SECURITY_ROLE, security.get(0), Constants.TEXT);
			foundation.click(UserList.EXPIRATION_DATE);
			foundation.click(UserList.SELECT_EXPIRATION_DATE);
			foundation.click(UserList.SEND_NOTIFICATION);
			foundation.click(UserList.ADD_ROLE_USER_BTN);
			foundation.threadWait(Constants.TWO_SECOND);
			// Navigate to User Summary Tab
			foundation.click(UserList.USER_SUMMARY);
			foundation.threadWait(Constants.TWO_SECOND);

			// Click on Update User Button
			foundation.click(UserList.SAVE_USER);
			foundation.waitforElement(UserList.SEARCH_FILTER, Constants.MEDIUM_TIME);
			foundation.waitforElementToDisappear(UserList.TXT_SPINNER_MSG, Constants.MEDIUM_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(UserList.SEARCH_FILTER, device);
			foundation.click(UserList.TBL_DATA);
			foundation.click(UserList.MANAGE_USER_ROLES);
			foundation.click(UserList.REMOVE_USER_ROLES);
			foundation.click(UserList.CONFIRM_DISABLE);
		}
	}

	@Test(description = "166021 - Update the User details on Manage Password Tab")
	public void UpdateManagePasswordTabDetails() {

		final String CASE_NUM = "166021";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);

		final String device = rstUserRolesData.get(CNUserRoles.ROLE_NAME);
		final String updatedData = rstUserRolesData.get(CNUserRoles.ROW_RECORD);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Search for already created User
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.SEARCH_FILTER));
			textBox.enterText(UserList.SEARCH_FILTER, device);
			foundation.click(UserList.TBL_DATA);

			// Check for Manage Password button and click cancel button
			foundation.click(UserList.MANAGE_PASSWORD);
			textBox.enterText(UserList.PASSWORD_TXT, updatedData);
			textBox.enterText(UserList.CNFRM_PASSWORD_TXT, updatedData);
			foundation.click(UserList.CANCEL_USER_PASSWORD);

			// Check for Manage Password button and click save button
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.SEARCH_FILTER));
			textBox.enterText(UserList.SEARCH_FILTER, device);
			foundation.click(UserList.TBL_DATA);
			foundation.click(UserList.MANAGE_PASSWORD);
			textBox.enterText(UserList.PASSWORD_TXT, updatedData);
			textBox.enterText(UserList.CNFRM_PASSWORD_TXT, updatedData);
			foundation.click(UserList.SAVE_PASSWORD_BTN);

			// Navigate to User Summary Tab
			foundation.click(UserList.USER_SUMMARY);

			// Click on Update User Button
			foundation.click(UserList.SAVE_USER);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166010- Validate the error messages for Mandatory field and invalid Email address.")
	public void UsersAndRolesValidateError() {

		final String CASE_NUM = "166010";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);

		final String rowData = rstUserRolesData.get(CNUserRoles.ROW_RECORD);

		List<String> errorValidation = Arrays
				.asList(rstUserRolesData.get(CNUserRoles.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String error_Status = errorValidation.get(0);
		String invalid_Email = errorValidation.get(1);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New User button
			foundation.click(UserList.CREATE_NEW_ROLE);
			foundation.click(UserList.DRP_SELECT_ROLE);
			foundation.click(UserList.SAVE_USER);

			// Validate the error message
			CustomisedAssert.assertEquals(foundation.getText(UserList.FIRST_NAME_ERROR), error_Status);
			CustomisedAssert.assertEquals(foundation.getText(UserList.LAST_NAME_ERROR), error_Status);
			// foundation.scroll();
			CustomisedAssert.assertEquals(foundation.getText(UserList.EMAIL_ERROR), error_Status);
			CustomisedAssert.assertEquals(foundation.getText(UserList.LOCATION_ERROR), error_Status);
			foundation.click(UserList.CANCEL_USER);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(UserList.CREATE_NEW_ROLE));

			// Create New
			foundation.click(UserList.CREATE_NEW_ROLE);
			foundation.click(UserList.DRP_SELECT_ROLE);
			textBox.enterText(UserList.EMAIL_ADDRESS_FIELD, rowData);
			foundation.click(UserList.SAVE_USER);
			foundation.waitforElement(UserList.EMAIL_ERROR, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(UserList.EMAIL_ERROR), invalid_Email);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}
	/**
	 * @author afrosean
	 * Date:21-09-2022
	 */

	@Test(description = "204699-SOS-verify copy user contains duplicate values")
	public void copyADMUser() {

		final String CASE_NUM = "204699";

		// reading data from database
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		List<String> inputs = Arrays
				.asList(rstConsumerSearchData.get(CNConsumerSearch.ACTIONS).split(Constants.DELIMITER_TILD));

		try {
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate to menu item -> user and roles
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// search user and validate
			userList.searchAndSelectUser(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME));
			CustomisedAssert.assertTrue(foundation.getText(UserList.EDIT_USERS)
					.contains(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME)));

			// Click on copy user and enter all mandatory fields
			userList.clickOnCopyUserAndEnterAllMandatoryFields(inputs.get(0), inputs.get(1), inputs.get(2),
					inputs.get(3), inputs.get(4));

			// Search user and verify copy user fields
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			userList.searchUserAndVerifyCopyUser(rstConsumerSearchData.get(CNConsumerSearch.FIRST_NAME));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
