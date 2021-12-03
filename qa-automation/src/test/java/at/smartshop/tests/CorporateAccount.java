package at.smartshop.tests;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.CorporateAccountList;

@Listeners(at.framework.reportsetup.Listeners.class)

public class CorporateAccount extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private CorporateAccountList corporateAccountList = new CorporateAccountList();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Strings string = new Strings();
	private Numbers numbers = new Numbers();
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstDeviceListData;


	@Test(description = "165277-Enter all the valid details in the fields and click on Cancel and then Save button")
	public void CorporateCancelAndSaveValidDetails() {

		final String CASE_NUM = "165277";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();
		final String numeric = String.valueOf(numbers.generateRandomNumber(0, 999999999));

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

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
			foundation.click(CorporateAccountList.BTN_CREATE);
			textBox.enterText(CorporateAccountList.CORPORATE_NAME, device);
			textBox.enterText(CorporateAccountList.CORPORATE_ADDRESS, device);
			textBox.enterText(CorporateAccountList.CORPORATE_ZIP, numeric);
			textBox.enterText(CorporateAccountList.CORPORATE_CITY, device);
			dropDown.selectItem(CorporateAccountList.CORPORATE_STATE, dbData.get(0), Constants.TEXT);
			textBox.enterText(CorporateAccountList.CORPORATE_CONTACT, device);
			textBox.enterText(CorporateAccountList.CORPORATE_PHONENUMBER, numeric);
			textBox.enterText(CorporateAccountList.CORPORATE_CONTACTEMAIL, dbData.get(1));
			textBox.enterText(CorporateAccountList.CORPORATE_NOTES, device);
			textBox.enterText(CorporateAccountList.FINANCIAL_NAME, device);
			textBox.enterText(CorporateAccountList.DISBURSEMENT_EMAIL, dbData.get(1));
			textBox.enterText(CorporateAccountList.FINANCIAL_EMAIL, dbData.get(1));

			// Click on Cancel Button
			foundation.waitforElement(CorporateAccountList.CANCEL_BTN, Constants.SHORT_TIME);
			foundation.click(CorporateAccountList.CANCEL_BTN);
			foundation.click(CorporateAccountList.CONFIRM_DELETE);

			// Search for recently created Corporate Accounts
			foundation.refreshPage();
			foundation.waitforElement(CorporateAccountList.SEARCH_FILTER, Constants.MEDIUM_TIME);

			// Create New and save the account
			foundation.click(CorporateAccountList.BTN_CREATE);
			textBox.enterText(CorporateAccountList.CORPORATE_NAME, device);
			textBox.enterText(CorporateAccountList.CORPORATE_ADDRESS, device);
			textBox.enterText(CorporateAccountList.CORPORATE_ZIP, numeric);
			textBox.enterText(CorporateAccountList.CORPORATE_CITY, device);
			dropDown.selectItem(CorporateAccountList.CORPORATE_STATE, dbData.get(0), Constants.TEXT);
			textBox.enterText(CorporateAccountList.CORPORATE_CONTACT, device);
			textBox.enterText(CorporateAccountList.CORPORATE_PHONENUMBER, numeric);
			textBox.enterText(CorporateAccountList.CORPORATE_CONTACTEMAIL, dbData.get(1));
			textBox.enterText(CorporateAccountList.CORPORATE_NOTES, device);
			textBox.enterText(CorporateAccountList.FINANCIAL_NAME, device);
			textBox.enterText(CorporateAccountList.DISBURSEMENT_EMAIL, dbData.get(1));
			textBox.enterText(CorporateAccountList.FINANCIAL_EMAIL, dbData.get(1));

			// Click on Save Button
			foundation.waitforElement(CorporateAccountList.SAVE_BTN, Constants.SHORT_TIME);
			foundation.click(CorporateAccountList.SAVE_BTN);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Search for recently created Corporate Accounts
			foundation.waitforElement(CorporateAccountList.SEARCH_FILTER, Constants.SHORT_TIME);
			textBox.enterText(CorporateAccountList.SEARCH_FILTER, device);
			foundation.click(CorporateAccountList.DELETE_ACCOUNT);
			foundation.click(CorporateAccountList.CONFIRM_DELETE);
		}
	}

	@Test(description = "165278-Update the previous details in the fields and click on Cancel button and then Save button")
	public void CorporateUpdateFieldsValidation() {

		final String CASE_NUM = "165278";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Search for Previously added Corporate Accounts, do the changes and cancel
			textBox.enterText(CorporateAccountList.SEARCH_FILTER, dbData.get(0));
			foundation.click(CorporateAccountList.TBL_DATA);

			// do the changes in the fields
			textBox.enterText(CorporateAccountList.CORPORATE_NAME, dbData.get(1));
			textBox.enterText(CorporateAccountList.FINANCIAL_NAME, dbData.get(1));

			// Click on Cancel Button
			foundation.waitforElement(CorporateAccountList.CANCEL_BTN, Constants.SHORT_TIME);
			foundation.click(CorporateAccountList.CANCEL_BTN);
			foundation.click(CorporateAccountList.CONFIRM_DELETE);

			// Search for Previously added Corporate Accounts
			foundation.waitforElement(CorporateAccountList.SEARCH_FILTER, Constants.SHORT_TIME);
			textBox.enterText(CorporateAccountList.SEARCH_FILTER, dbData.get(0));
			foundation.click(CorporateAccountList.TBL_DATA);

			// do the changes in the fields and save
			textBox.enterText(CorporateAccountList.CORPORATE_NAME, dbData.get(1));
			textBox.enterText(CorporateAccountList.FINANCIAL_NAME, dbData.get(1));

			// Click on Save Button
			foundation.waitforElement(CorporateAccountList.SAVE_BTN, Constants.SHORT_TIME);
			foundation.click(CorporateAccountList.SAVE_BTN);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Search for recently updated Corporate Accounts
			foundation.waitforElement(CorporateAccountList.SEARCH_FILTER, Constants.SHORT_TIME);
			textBox.enterText(CorporateAccountList.SEARCH_FILTER, dbData.get(1));
			foundation.click(CorporateAccountList.TBL_DATA);
			textBox.enterText(CorporateAccountList.CORPORATE_NAME, dbData.get(0));
			textBox.enterText(CorporateAccountList.FINANCIAL_NAME, dbData.get(0));

			// Click on Save Button
			foundation.waitforElement(CorporateAccountList.SAVE_BTN, Constants.SHORT_TIME);
			foundation.click(CorporateAccountList.SAVE_BTN);
		}
	}

	@Test(description = "165279-Validation for invalid value in Postal code, Phone number and email fields and Maandatory Fields error message")
	public void CorporateInvalidDetailsValidation() {

		final String CASE_NUM = "165279";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> totalExpectedData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String corporate_ZIP = totalExpectedData.get(0);
		String corporate_Phone = totalExpectedData.get(0);
		String corporate_Email = totalExpectedData.get(1) + Constants.DELIMITER_DOT;
		String disbursement_Email = totalExpectedData.get(1) + totalExpectedData.get(2);
		String corporate_Name = totalExpectedData.get(3) + Constants.DELIMITER_DOT;
		String financial_Name = totalExpectedData.get(3) + totalExpectedData.get(4);
		String disbursement_Error_Email = totalExpectedData.get(5);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			//Validation for Postal code, Phone Number and Email Address
			// Create New
			foundation.click(CorporateAccountList.BTN_CREATE);

			textBox.enterText(CorporateAccountList.CORPORATE_ZIP, device);
			textBox.enterText(CorporateAccountList.CORPORATE_PHONENUMBER, device);
			textBox.enterText(CorporateAccountList.CORPORATE_CONTACTEMAIL, device);
			textBox.enterText(CorporateAccountList.DISBURSEMENT_EMAIL, device);

			// Click on Save Button
			foundation.waitforElement(CorporateAccountList.SAVE_BTN, Constants.SHORT_TIME);
			foundation.click(CorporateAccountList.SAVE_BTN);

			Assert.assertEquals(foundation.getText(CorporateAccountList.ZIP_ERROR), corporate_ZIP);
			Assert.assertEquals(foundation.getText(CorporateAccountList.PHONE_ERROR), corporate_Phone);
			Assert.assertEquals(foundation.getText(CorporateAccountList.EMAIL_ERROR), corporate_Email);
			Assert.assertEquals(foundation.getText(CorporateAccountList.CRPDISBURSEMENT_ERROR), disbursement_Email);

			// Click on Cancel Button
			foundation.waitforElement(CorporateAccountList.CANCEL_BTN, Constants.SHORT_TIME);
			foundation.click(CorporateAccountList.CANCEL_BTN);
			foundation.click(CorporateAccountList.CONFIRM_DELETE);
			foundation.waitforElement(CorporateAccountList.SEARCH_FILTER, Constants.SHORT_TIME);

			// Validation for Mandatory Fields Error 
			// Create New
			foundation.click(CorporateAccountList.BTN_CREATE);

			// Click on Save Button
			foundation.click(CorporateAccountList.SAVE_BTN);

			// Validate the error Messages
			Assert.assertEquals(foundation.getText(CorporateAccountList.NAME_ERROR), corporate_Name);
			Assert.assertEquals(foundation.getText(CorporateAccountList.CRPOPERATOR_ERROR), financial_Name);
			Assert.assertEquals(foundation.getText(CorporateAccountList.CRPDISBURSEMENT_ERROR),
					disbursement_Error_Email);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "165282-Validation for already entered name and Disbursement Checkbox")
	public void CorporateAlreadyEnteredErrorValidation() {

		final String CASE_NUM = "165282";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE);

		List<String> totalExpectedData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String corporate_Name = totalExpectedData.get(0);
		String corporate_Checkbox = totalExpectedData.get(1);

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
			foundation.click(CorporateAccountList.BTN_CREATE);
			textBox.enterText(CorporateAccountList.CORPORATE_NAME, device);

			// Click on Save Button
			foundation.click(CorporateAccountList.SAVE_BTN);

			// Validate the error Messages of Already entered Name
			Assert.assertEquals(foundation.getText(CorporateAccountList.NAME_ERROR), corporate_Name);

			// Validate the error Messages for Checkbox
			Assert.assertEquals(foundation.getText(CorporateAccountList.VALIDATE_MESSAGE), corporate_Checkbox);

			// change the checkbox
			foundation.click(CorporateAccountList.DISBURSEMENT_DAY);
			foundation.isDisabled(CorporateAccountList.DISABLED_CHECKBOX);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

}
