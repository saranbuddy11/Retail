package at.smartshop.tests;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerRolesList;
import at.smartshop.pages.NavigationBar;

public class ConsumerRoles extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private Strings string = new Strings();
	private Numbers numbers = new Numbers();
	private ConsumerRolesList consumerRolesList = new ConsumerRolesList();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstDeviceListData;

	@Test(description = "165178-Enter all the valid details in the fields and click on save")
	public void ConsumerRolesValidDetails() {

		final String CASE_NUM = "165178";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();
		final String numeric = String.valueOf(numbers.generateRandomNumber(0, 999));

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CONSUMER_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CONSUMER_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(ConsumerRolesList.BTN_CREATE);
			textBox.enterText(ConsumerRolesList.ROLE_NAME, device);
			textBox.enterText(ConsumerRolesList.DESCRIPTION, device);
			textBox.enterText(ConsumerRolesList.STARTS_WITH, numeric);
			textBox.enterText(ConsumerRolesList.ENDS_WITH, numeric);
			textBox.enterText(ConsumerRolesList.LENGTH, numeric);
			dropDown.selectItem(ConsumerRolesList.SELECT_ORG, dbData.get(0), Constants.TEXT);
			dropDown.selectItem(ConsumerRolesList.SELECT_LOCATION, dbData.get(1), Constants.TEXT);

			// Click on Save Button
			foundation.waitforElement(ConsumerRolesList.BTN_SUBMIT, Constants.SHORT_TIME);
			foundation.click(ConsumerRolesList.BTN_SUBMIT);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			foundation.waitforElement(ConsumerRolesList.ROLE_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(ConsumerRolesList.ROLE_SEARCH, device);
			foundation.click(ConsumerRolesList.DELETE_DATA);
			foundation.waitforElement(ConsumerRolesList.ACCPT_POPUP, Constants.SHORT_TIME);
			foundation.click(ConsumerRolesList.ACCPT_POPUP);
		}

	}

	@Test(description = "165181-Enter all the valid details in the fields and click on cancel")
	public void ConsumerRolesCancelValidDetails() {

		final String CASE_NUM = "165181";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CONSUMER_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CONSUMER_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(ConsumerRolesList.BTN_CREATE);
			textBox.enterText(ConsumerRolesList.ROLE_NAME, device);
			textBox.enterText(ConsumerRolesList.DESCRIPTION, device);
			textBox.enterText(ConsumerRolesList.STARTS_WITH, device);
			textBox.enterText(ConsumerRolesList.ENDS_WITH, device);
			textBox.enterText(ConsumerRolesList.LENGTH, device);
			dropDown.selectItem(ConsumerRolesList.SELECT_ORG, dbData.get(0), Constants.TEXT);
			dropDown.selectItem(ConsumerRolesList.SELECT_LOCATION, dbData.get(1), Constants.TEXT);

			// Click on Save Button
			foundation.click(ConsumerRolesList.BTN_CANCEL);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "165182- Update the details in the fields and click on save")
	public void ConsumerRolesUpdateDetails() {

		final String CASE_NUM = "165182";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE);
		final String updatedData = rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CONSUMER_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CONSUMER_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New Role
			textBox.enterText(ConsumerRolesList.ROLE_SEARCH, device);
			foundation.click(ConsumerRolesList.TBL_DATA);
			foundation.waitforElement(ConsumerRolesList.VALIDATE_TITLE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerRolesList.ROLE_NAME, updatedData);

			// Click on Save Button
			foundation.waitforElement(ConsumerRolesList.BTN_SUBMIT, Constants.SHORT_TIME);
			foundation.click(ConsumerRolesList.BTN_SUBMIT);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			//Resetting the changes
			foundation.waitforElement(ConsumerRolesList.ROLE_SEARCH, Constants.SHORT_TIME);
			textBox.enterText(ConsumerRolesList.ROLE_SEARCH, updatedData);
			foundation.click(ConsumerRolesList.TBL_DATA);
			foundation.waitforElement(ConsumerRolesList.VALIDATE_TITLE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerRolesList.ROLE_NAME, device);
			// Click on Save Button
			foundation.waitforElement(ConsumerRolesList.BTN_SUBMIT, Constants.SHORT_TIME);
			foundation.click(ConsumerRolesList.BTN_SUBMIT);
			foundation.waitforElement(ConsumerRolesList.Validate_CREATE_BUTTON, Constants.SHORT_TIME);
		}
	}

	@Test(description = "165187-Validate Error message when entered already added data in the fields")
	public void ConsumerRolesValidateErrorMesage() {

		final String CASE_NUM = "165187";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		String expectedData = rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE);
		final String device = rstDeviceListData.get(CNDeviceList.DEVICE);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CONSUMER_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CONSUMER_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(ConsumerRolesList.BTN_CREATE);
			textBox.enterText(ConsumerRolesList.ROLE_NAME, device);

			// Click on Save Button
			foundation.click(ConsumerRolesList.BTN_SUBMIT);

			// Validate the error message
			foundation.waitforElement(ConsumerRolesList.ERROR_STATUS, Constants.SHORT_TIME);
			Assert.assertEquals(foundation.getText(ConsumerRolesList.ERROR_STATUS), expectedData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "165188-Enter only Role Information details and leave Card Definition details blank then Validate the error message")
	public void ConsumerRolesValidateCardDefinitionError() {

		final String CASE_NUM = "165188";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		String expectedData = rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE);
		final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CONSUMER_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CONSUMER_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(ConsumerRolesList.BTN_CREATE);
			textBox.enterText(ConsumerRolesList.ROLE_NAME, device);
			textBox.enterText(ConsumerRolesList.DESCRIPTION, device);

			// Click on Save Button
			foundation.click(ConsumerRolesList.BTN_SUBMIT);

			// Validate the error message
			Assert.assertEquals(foundation.getText(ConsumerRolesList.VALIDATE_CARD_DEFINITION_START_ERROR),
					expectedData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "165222-Validate the error message when leave all fields blank")
	public void ConsumerRolesValidateBlankFields() {

		final String CASE_NUM = "165222";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> totalExpectedData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String role_Information_Status = totalExpectedData.get(0);
		String card_Definition_Status = totalExpectedData.get(1);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CONSUMER_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CONSUMER_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(ConsumerRolesList.BTN_CREATE);

			// Click on Save Button
			foundation.click(ConsumerRolesList.BTN_SUBMIT);
			foundation.waitforElement(ConsumerRolesList.BTN_SUBMIT, Constants.SHORT_TIME);

			// Validate the error message
			Assert.assertEquals(foundation.getText(ConsumerRolesList.ERROR_STATUS), role_Information_Status);
			Assert.assertEquals(foundation.getText(ConsumerRolesList.DESCRIPTION_ERROR_STATUS),
					role_Information_Status);
			Assert.assertEquals(foundation.getText(ConsumerRolesList.VALIDATE_CARD_DEFINITION_START_ERROR),
					card_Definition_Status);
			Assert.assertEquals(foundation.getText(ConsumerRolesList.VALIDATE_CARD_DEFINITION_END_ERROR),
					card_Definition_Status);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

}
