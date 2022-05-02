package at.smartshop.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.database.columns.CNSuperList;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.database.columns.CNNationalAccounts;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.Campus;
import at.smartshop.pages.ConsumerRolesList;
import at.smartshop.pages.ContactList;
import at.smartshop.pages.Copy;
import at.smartshop.pages.CorporateAccountList;
import at.smartshop.pages.DICRules;
import at.smartshop.pages.DataSourceManager;
import at.smartshop.pages.DeviceCreate;
import at.smartshop.pages.FinanceList;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.Lookup;
import at.smartshop.pages.LookupType;
import at.smartshop.pages.Middid;
import at.smartshop.pages.NationalAccounts;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgList;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.OrgstrList;
import at.smartshop.pages.PageSet;
import at.smartshop.pages.PrintGroupLists;
import at.smartshop.pages.PromotionList;
import at.smartshop.pages.SequenceNumber;
import at.smartshop.pages.SpecialService;
import at.smartshop.pages.Announcement;
import at.smartshop.pages.AppReferral;
import at.smartshop.pages.Report;

public class SuperOthers extends TestInfra {

	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox();
	private Table table = new Table();
	private Excel excel = new Excel();
	private Strings string = new Strings();
	private Numbers numbers = new Numbers();
	private Dropdown dropDown = new Dropdown();
	private OrgstrList orgstr = new OrgstrList();
	private SpecialService specialService = new SpecialService();
	private Strings strings = new Strings();
	private DeviceCreate deviceCreate = new DeviceCreate();
	private PrintGroupLists printGroupLists = new PrintGroupLists();
	private DICRules dicRules = new DICRules();
	private SequenceNumber sequenceNumber = new SequenceNumber();
	private DataSourceManager dataSourceManager = new DataSourceManager();
	private LookupType lookupType = new LookupType();
	private NationalAccounts nationalAccounts = new NationalAccounts();
	private Campus campus = new Campus();
	private PageSet pageset = new PageSet();
	private AppReferral appReferral = new AppReferral();
	private Lookup lookup = new Lookup();
	private Middid middid = new Middid();
	private Copy copy = new Copy();
	private Report report = new Report();
	private LocationList locationList = new LocationList();
	private LocationSummary locationSummary = new LocationSummary();
	private Announcement announcement = new Announcement();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstDeviceListData;
	private Map<String, String> rstOrgSummaryData;
	private Map<String, String> rstSuperListData;
	private Map<String, String> rstNationalAccountsData;

	@Test(description = "164514-ADM>Super>Contact>Contact List")
	public void superContactList() {
		final String CASE_NUM = "164514";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			boolean fileExists = foundation.isFileExists(FilePath.EXCEL_CONTACT_SRC);
			if (fileExists == false) {
				foundation.deleteFile(FilePath.EXCEL_CONTACT_SRC);
			}
			foundation.click(ContactList.BTN_EXPORT);
			foundation.threadWait(Constants.TWO_SECOND);

			// download assertion
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_CONTACT_SRC));
			foundation.deleteFile(FilePath.EXCEL_CONTACT_SRC);
			textBox.enterText(ContactList.TXT_SEARCH_CONTACTS, Constants.TESTING);
			foundation.click(ContactList.BTN_EXPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_CONTACT_SRC));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_CONTACT_SRC);
		}
	}

	// Consumer Roles Test Scenarios -
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

		} catch (Exception exc) {
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

		} catch (Exception exc) {
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

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the changes
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
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.ERROR_STATUS), expectedData);

		} catch (Exception exc) {
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
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.VALIDATE_CARD_DEFINITION_START_ERROR),
					expectedData);

		} catch (Exception exc) {
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
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.ERROR_STATUS), role_Information_Status);
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.DESCRIPTION_ERROR_STATUS),
					role_Information_Status);
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.VALIDATE_CARD_DEFINITION_START_ERROR),
					card_Definition_Status);
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.VALIDATE_CARD_DEFINITION_END_ERROR),
					card_Definition_Status);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	// Super > Orgstr Test Scenario

	@Test(description = "164571-Enter all the valid details in the fields and click on add")
	public void OrgstrValidDetailsAndSave() {

		final String CASE_NUM = "164571";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();

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
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, dbData.get(1));
			textBox.enterText(OrgstrList.TXT_NAME, device);
			textBox.enterText(OrgstrList.KEY_NAME, device);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			orgstr.acceptPopup(device);
		}

	}

	@Test(description = "164572-Enter all the valid details in the fields and click on cancel")
	public void OrgstrValidDetailsAndCancel() {

		final String CASE_NUM = "164572";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();

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
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, dbData.get(1));
			textBox.enterText(OrgstrList.TXT_NAME, device);
			textBox.enterText(OrgstrList.KEY_NAME, device);

			// Click on Cancel Button
			foundation.waitforElement(OrgstrList.BTN_CANCEL, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_CANCEL);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "164573-Enter special characters in the fields and click on add")
	public void OrgstrSpecialChars() {

		final String CASE_NUM = "164573";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE);

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
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, dbData.get(1));
			textBox.enterText(OrgstrList.TXT_NAME, device);
			textBox.enterText(OrgstrList.KEY_NAME, device);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			orgstr.acceptPopup(device);
		}

	}

	@Test(description = "164574-Enter numeric Values in the fields and click on add")
	public void OrgstrNumericValues() {

		final String CASE_NUM = "164574";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
		final String numeric = String.valueOf(numbers.generateRandomNumber(0, 99999));

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
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, numeric);
			textBox.enterText(OrgstrList.TXT_NAME, numeric);
			textBox.enterText(OrgstrList.KEY_NAME, numeric);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			foundation.waitforElement(OrgstrList.ORG_LIST, Constants.SHORT_TIME);
			textBox.enterText(OrgstrList.ORG_DEVICE_SEARCH, numeric);
			foundation.click(OrgstrList.TBL_DATA);
			foundation.waitforElement(OrgstrList.BTN_REMOVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_REMOVE);
			foundation.alertAccept();
		}

	}

	@Test(description = "164575-Enter Combination of Special Characters and numeric Values in the fields and click on add")
	public void OrgstrCombinationSpecialCharsAndNumericValues() {

		final String CASE_NUM = "164575";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + numbers.generateRandomNumber(0, 99999);

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
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, dbData.get(1));
			textBox.enterText(OrgstrList.TXT_NAME, device);
			textBox.enterText(OrgstrList.KEY_NAME, device);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			orgstr.acceptPopup(device);
		}

	}

	@Test(description = "164576-Enter Blank Values in the fields and click on add")
	public void OrgstrBlankValues() {

		final String CASE_NUM = "164576";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
		String expectedData = rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE);

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
			foundation.click(OrgstrList.BTN_CREATE);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(OrgstrList.ERROR_STATUS), expectedData);
			CustomisedAssert.assertEquals(foundation.getText(OrgstrList.TYPE_ERROR_STATUS), expectedData);
			CustomisedAssert.assertEquals(foundation.getText(OrgstrList.NAME_ERROR_STATUS), expectedData);
			CustomisedAssert.assertEquals(foundation.getText(OrgstrList.KEYSTR_ERROR_STATUS), expectedData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "164577-Enter only Org and leave other fields and click on add")
	public void OrgstrOnlyOrgAndBlankValues() {

		final String CASE_NUM = "164577";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
		String expectedData = rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE);

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
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

			// Validating the error message
			CustomisedAssert.assertEquals(foundation.getText(OrgstrList.TYPE_ERROR_STATUS), expectedData);
			CustomisedAssert.assertEquals(foundation.getText(OrgstrList.NAME_ERROR_STATUS), expectedData);
			CustomisedAssert.assertEquals(foundation.getText(OrgstrList.KEYSTR_ERROR_STATUS), expectedData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "164578-Enter already added data in the fields and click on add")
	public void OrgstrAlreadyAddedData() {

		final String CASE_NUM = "164578";
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

			// Create New
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, dbData.get(1));
			textBox.enterText(OrgstrList.TXT_NAME, dbData.get(2));
			textBox.enterText(OrgstrList.KEY_NAME, dbData.get(2));

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// resetting test data
			foundation.waitforElement(OrgstrList.ORG_LIST, Constants.SHORT_TIME);
			textBox.enterText(OrgstrList.ORG_DEVICE_SEARCH, dbData.get(2));
			foundation.click(OrgstrList.TBL_DATA);
			foundation.waitforElement(OrgstrList.BTN_REMOVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_REMOVE);
			foundation.alertAccept();
		}

	}

	@Test(description = "164103-QAA-78-ADM>Super>Org>Org List")
	public void OrgValidDetailsSaveAndCancel() {

		final String CASE_NUM = "164103";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
		final String orgName = Constants.AUTO_TEST + string.getRandomCharacter();

		List<String> dbData = Arrays
				.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// verify cancel org saving
			foundation.click(OrgList.BTN_CREATE);
			textBox.enterText(OrgSummary.TXT_NAME, orgName);
			foundation.click(OrgSummary.BTN_CANCEL);
			foundation.waitforElement(OrgList.TXT_SEARCH_ORG, Constants.SHORT_TIME);
			textBox.enterText(OrgList.TXT_SEARCH_ORG, orgName);
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgList.LBL_NO_RESULT));

			// verify Create New/save
			foundation.click(OrgList.BTN_CREATE);
			textBox.enterText(OrgSummary.TXT_NAME, orgName);
			dropDown.selectItem(OrgSummary.DPD_SPECIAL_TYPE, dbData.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, dbData.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, dbData.get(2), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_SYSTEM, dbData.get(3), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_PAGESET, dbData.get(4), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_CURRENCY, dbData.get(5), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_OPERATOR, orgName);
			textBox.enterText(OrgSummary.TXT_DISBURSEMENT_EMAIL, dbData.get(6));
			// Click on Save Button
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElementToDisappear(OrgList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(OrgList.TXT_SEARCH_ORG, orgName);
			CustomisedAssert.assertTrue(foundation.getText(OrgList.LBL_FIRST_ORG_NAME).contains(orgName));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Corporate Account Test Scenarios

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

		} catch (Exception exc) {
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

		} catch (Exception exc) {
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

			// Validation for Postal code, Phone Number and Email Address
			// Create New
			foundation.click(CorporateAccountList.BTN_CREATE);

			textBox.enterText(CorporateAccountList.CORPORATE_ZIP, device);
			textBox.enterText(CorporateAccountList.CORPORATE_PHONENUMBER, device);
			textBox.enterText(CorporateAccountList.CORPORATE_CONTACTEMAIL, device);
			textBox.enterText(CorporateAccountList.DISBURSEMENT_EMAIL, device);

			// Click on Save Button
			foundation.waitforElement(CorporateAccountList.SAVE_BTN, Constants.SHORT_TIME);
			foundation.click(CorporateAccountList.SAVE_BTN);

			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.ZIP_ERROR), corporate_ZIP);
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.PHONE_ERROR), corporate_Phone);
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.EMAIL_ERROR), corporate_Email);
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.CRPDISBURSEMENT_ERROR),
					disbursement_Email);

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
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.NAME_ERROR), corporate_Name);
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.CRPOPERATOR_ERROR), financial_Name);
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.CRPDISBURSEMENT_ERROR),
					disbursement_Error_Email);

		} catch (Exception exc) {
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
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.NAME_ERROR), corporate_Name);

			// Validate the error Messages for Checkbox
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.VALIDATE_MESSAGE),
					corporate_Checkbox);

			// change the checkbox
			foundation.click(CorporateAccountList.DISBURSEMENT_DAY);
			foundation.isDisabled(CorporateAccountList.DISABLED_CHECKBOX);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Super > Finance (EFT Disbursement & Disbursement Adjustments) Test Scenarios

	@Test(description = "166631-Validate all links on EFT Disbursement Process Page")
	public void ValidateAllEFTDisbursementLinks() {

		final String CASE_NUM = "166631";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		final String disbursement_Date = rstSuperListData.get(CNSuperList.DISBURSEMENT_DATE);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Validate the heading
			foundation.waitforElement(FinanceList.VALIDATE_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(FinanceList.VALIDATE_HEADING));
			CustomisedAssert.assertTrue(foundation.isDisplayed(FinanceList.DISBURSEMENT_HEADING));
			CustomisedAssert.assertTrue(foundation.isDisplayed(FinanceList.VALIDATE_DATE));
			CustomisedAssert.assertEquals(foundation.getText(FinanceList.VALIDATE_DATE), disbursement_Date);

			// download disbursement report assertion
			foundation.click(FinanceList.DOWNLOAD_DISBURSEMENT_REPORT);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_DISBURSEMENT_EXPORT_SRC));

			// download view Variance report
			foundation.click(FinanceList.VIEW_VARIANCE);
			foundation.click(FinanceList.EXPORT_TOEXCEL);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_VARIANCE_EXPORT_SRC));

			// download CSV Report
			foundation.click(FinanceList.BACK_TO_DISBURSEMENT_PAGE);
			foundation.click(FinanceList.DOWNLOAD_CSV_REPORT);
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_CSV_REPORT_EXPORT_SRC));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_DISBURSEMENT_EXPORT_SRC);
			foundation.deleteFile(FilePath.EXCEL_VARIANCE_EXPORT_SRC);
			foundation.deleteFile(FilePath.EXCEL_CSV_REPORT_EXPORT_SRC);

		}
	}

	@Test(description = "166632-Validates the error messages for mandatory fields for Disbursement Adjustment")
	public void ValidateMandatoryDisbursementAdjustment() {

		final String CASE_NUM = "166632";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> totalExpectedData = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String org_Error = totalExpectedData.get(0);
		String notes_Error = totalExpectedData.get(1);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Validate the heading
			foundation.waitforElement(FinanceList.VALIDATE_DISBURSEMENT_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(FinanceList.VALIDATE_DISBURSEMENT_HEADING));

			// Click on Save btn
			foundation.click(FinanceList.SAVE_BTN);
			CustomisedAssert.assertEquals(foundation.getText(FinanceList.ORG_ERROR), org_Error);
			CustomisedAssert.assertEquals(foundation.getText(FinanceList.NOTES_ERROR), notes_Error);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166633-Enter the information in the fields and click on 'Cancel' and 'Save' button for Disbursement Adjustment Page")
	public void DisbursementAdjustmentAllFields() {

		final String CASE_NUM = "166633";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);
		final String amount = String.valueOf(numbers.generateRandomNumber(0, 999));

		List<String> disbursementPage = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Validate the heading
			foundation.waitforElement(FinanceList.VALIDATE_DISBURSEMENT_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(FinanceList.VALIDATE_DISBURSEMENT_HEADING));

			// Enter the data in fields
			foundation.click(FinanceList.CHOOSE_ORG);
			textBox.enterText(FinanceList.SELECT_ORG, disbursementPage.get(0));
			foundation.click(FinanceList.CLICK_ORG);
			foundation.click(FinanceList.SELECT_LOC);
			textBox.enterText(FinanceList.SELECT_LOC, disbursementPage.get(1));
			foundation.click(FinanceList.ENTER_LOC);
			textBox.enterText(FinanceList.SELECT_NOTES, disbursementPage.get(2));
			textBox.enterText(FinanceList.SELECT_AMOUNT, amount);

			// Click on Cancel button
			foundation.click(FinanceList.CANCEL_BTN);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Validate the heading
			foundation.waitforElement(FinanceList.VALIDATE_DISBURSEMENT_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(FinanceList.VALIDATE_DISBURSEMENT_HEADING));

			// Enter the data in fields
			foundation.click(FinanceList.CHOOSE_ORG);
			textBox.enterText(FinanceList.SELECT_ORG, disbursementPage.get(0));
			foundation.click(FinanceList.CLICK_ORG);
			foundation.click(FinanceList.SELECT_LOC);
			textBox.enterText(FinanceList.SELECT_LOC, disbursementPage.get(1));
			foundation.click(FinanceList.ENTER_LOC);
			textBox.enterText(FinanceList.SELECT_NOTES, disbursementPage.get(2));
			textBox.enterText(FinanceList.SELECT_AMOUNT, amount);

			// Click on Save button
			foundation.click(FinanceList.SAVE_BTN);
			foundation.click(FinanceList.CONFIRM_NO);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166024-ADM>Super>Special Service>Special Service List-Navigation to special service list and device summary")
	public void navigationToSpeacialServiceListAndSummary() {
		final String CASE_NUM = "166024";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));

			// verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceCreate.TITLE_DEVICE_CREATE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "164171-ADM>Super>Special Service>Special Service List-Save device with valid details")
	public void specialServiceSaveWithValidData() {
		final String CASE_NUM = "164171";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String deviceName = strings.getRandomCharacter();

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));

			// verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			String msrType = deviceCreate.createDevice(deviceName);
			foundation.waitforElementToDisappear(SpecialService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(SpecialService.TXT_SEARCH, deviceName);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(SpecialService.LBL_ROW_DATA).contains(msrType));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166013-ADM>Super>Special Service>Special Service List- Cancel device summary")
	public void speacialServicecancelDeviceSummary() {
		final String CASE_NUM = "166013";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String deviceName = strings.getRandomCharacter();

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));

			// verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			textBox.enterText(DeviceCreate.TXT_NAME, deviceName);
			foundation.click(DeviceCreate.BTN_CANCEl);
			textBox.enterText(SpecialService.TXT_SEARCH, deviceName);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.LBL_NO_RESULT));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166015-ADM>Super>Special Service>Special Service List- Edit device summary data")
	public void speacialServiceeditDeviceSummaryData() {
		final String CASE_NUM = "166015";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String deviceName = strings.getRandomCharacter();
		String editedDeviceName = Constants.EDITED + deviceName;

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));

			// verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			String msrType = deviceCreate.createDevice(deviceName);
			foundation.waitforElementToDisappear(SpecialService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(SpecialService.TXT_SEARCH, deviceName);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(SpecialService.LBL_ROW_DATA).contains(msrType));
			foundation.click(specialService.objSplServiceName(deviceName));
			textBox.enterText(DeviceCreate.TXT_NAME, editedDeviceName);
			foundation.click(DeviceCreate.BTN_SAVE);
			textBox.enterText(SpecialService.TXT_SEARCH, editedDeviceName);
			CustomisedAssert.assertTrue(foundation.isDisplayed(specialService.objSplServiceName(editedDeviceName)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166025-ADM>Super>Special Service>Special Service List- Sort columns")
	public void speacialServiceSortColumn() {
		final String CASE_NUM = "166025";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));

			// verify all column sort functionality
			int columnNumber = 1;
			List<WebElement> columnHeaders = Foundation.getDriver().findElements(SpecialService.LST_COLUMN_HEADERS);
			for (WebElement columnHeader : columnHeaders) {
				columnNumber++;
				columnHeader.click();
				foundation.verifySortText(specialService.listColumns(columnNumber), Constants.ASCENDING);
				columnHeader.click();
				foundation.verifySortText(specialService.listColumns(columnNumber), Constants.DESCENDING);
			}

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166897-ADM>Super>Special Service>Special Service List- Search list")
	public void speacialServiceSearchList() {
		final String CASE_NUM = "166897";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));

			// verify navigation to special service summary page
			textBox.enterText(SpecialService.TXT_SEARCH, Constants.AUTO_TEST);
			CustomisedAssert.assertTrue(foundation.isDisplayed(specialService.objSplServiceName(Constants.AUTO_TEST)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "166895-Validate the check and uncheck checkbox of ' Is Snowflake' column")
	public void DSMCheckUncheckCheckbox() {
		final String CASE_NUM = "166895";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		final String super_Name = rstSuperListData.get(CNSuperList.SUPER_NAME);

		List<String> rowdata = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String search_data = rowdata.get(0);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(DataSourceManager.VALIDATE_DSM_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DataSourceManager.VALIDATE_DSM_HEADING), super_Name);

			// search for Report
			textBox.enterText(DataSourceManager.DSM_SEARCH_BOX, search_data);
			CustomisedAssert.assertTrue(checkBox.isChkEnabled(DataSourceManager.DSM_CHECKBOX));
			foundation.click(DataSourceManager.DSM_CHECKBOX);
			foundation.waitforElement(DataSourceManager.DSM_SUCCESS_POPUP, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			CustomisedAssert.assertTrue(checkBox.isChecked(DataSourceManager.DSM_CHECKBOX));
			foundation.click(DataSourceManager.DSM_CHECKBOX);
			foundation.waitforElement(DataSourceManager.DSM_SUCCESS_POPUP, Constants.SHORT_TIME);

		}
	}

	@Test(description = "168041-QAA-281 Validate the error message when click on save button without entering any detail in Name and Type Fields and validate the successfully entered fields.")
	public void PrintGroupsValidateAllFields() {
		final String CASE_NUM = "168041";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		final String printGrpName = rstSuperListData.get(CNSuperList.SUPER_NAME) + string.getRandomCharacter();
		final String printGrpType = rstSuperListData.get(CNSuperList.EFT_DISBURSEMENT);
		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String printGroup_list_Page = validateHeading.get(0);
		String printGroup_Create_Page = validateHeading.get(1);
		String wrong_Printer_Name = validateHeading.get(3);

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String mandatory_Name_Error = errorMessage.get(0);
		String quotes_Error = errorMessage.get(2);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(PrintGroupLists.VALIDATE_PRINT_LIST_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PrintGroupLists.VALIDATE_PRINT_LIST_HEADING),
					printGroup_list_Page);

			// Select Location and click on create New Btn and Validate the error Message
			// for Mandatory Fields
			dropDown.selectItem(PrintGroupLists.DPD_LOCATION,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					Constants.TEXT);
			foundation.click(PrintGroupLists.BTN_CREATENEW);

			foundation.waitforElement(PrintGroupLists.VALIDATE_PRINT_GROUPCREATE_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(PrintGroupLists.VALIDATE_PRINT_GROUPCREATE_HEADING),
					printGroup_Create_Page);
			foundation.click(PrintGroupLists.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(PrintGroupLists.TXT_NAME_ERROR), mandatory_Name_Error);
			textBox.enterText(PrintGroupLists.TXT_NAME, wrong_Printer_Name);
			CustomisedAssert.assertEquals(foundation.getText(PrintGroupLists.TXT_NAME_ERROR), quotes_Error);
			foundation.click(PrintGroupLists.BTN_CANCEL);

			// click on create New Btn & Cancel Btn
			printGroupLists.createPrintGroup(printGrpName, printGrpType);
			foundation.click(PrintGroupLists.BTN_CANCEL);

			// click on create New Btn & Save Btn
			dropDown.selectItem(PrintGroupLists.DPD_LOCATION,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					Constants.TEXT);
			printGroupLists.createPrintGroup(printGrpName, printGrpType);
			foundation.click(PrintGroupLists.BTN_SAVE);
			foundation.waitforElementToDisappear(PrintGroupLists.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			browser.close();

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "168090-QAA-281 Update the changes on Print Summary Page and click on 'Cancel' then 'Save' button")
	public void PrintGroupUpdateFields() {
		final String CASE_NUM = "168090";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> printGrpType = Arrays
				.asList(rstSuperListData.get(CNSuperList.EFT_DISBURSEMENT).split(Constants.DELIMITER_TILD));
		String updated_Printer_Type = printGrpType.get(0);
		String existing_Printer_Type = printGrpType.get(1);

		List<String> printer_Name = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String existing_Printer_Name = printer_Name.get(0);
		String updated_Printer_Name = printer_Name.get(1);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String printGroup_list_Page = validateHeading.get(0);
		String printGroup_Summary_Page = validateHeading.get(1);

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String existing_Name_Error = errorMessage.get(0);
		String result_Data = errorMessage.get(1);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(PrintGroupLists.VALIDATE_PRINT_LIST_HEADING, Constants.TWO_SECOND);
			String heading = foundation.getText(PrintGroupLists.VALIDATE_PRINT_LIST_HEADING);
			CustomisedAssert.assertEquals(heading, printGroup_list_Page);

			// Validating the existing printer name
			dropDown.selectItem(PrintGroupLists.DPD_LOCATION,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE),
					Constants.TEXT);
			foundation.click(PrintGroupLists.BTN_CREATENEW);
			textBox.enterText(PrintGroupLists.TXT_NAME, existing_Printer_Name);
			foundation.click(PrintGroupLists.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(PrintGroupLists.TXT_NAME_ERROR), existing_Name_Error);
			foundation.click(PrintGroupLists.BTN_CANCEL);

			// updating the printer name
			printGroupLists.updatePrintGroup(existing_Printer_Name, result_Data, printGroup_Summary_Page,
					updated_Printer_Name, updated_Printer_Type);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

		// Resetting the data
		printGroupLists.updatePrintGroup(updated_Printer_Name, result_Data, printGroup_Summary_Page,
				existing_Printer_Name, existing_Printer_Type);

	}

	@Test(description = "168124-QAA-301 Validate the error message when click on save button without entering any detail in Name and Type Fields and validate the successfully entered fields."
			+ "168141 - QAA-301 Verify the Alert message for disabling the Active checkbox on DIC Rule Page"
			+ "168142 - QAA-301 Validate the validation of Field Type on DIC Rule Create Page")
	public void DICRulesValidateAllFields() {
		final String CASE_NUM = "168124";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.EFT_DISBURSEMENT).split(Constants.DELIMITER_TILD));
		String dicRule_Page = validateHeading.get(0);
		String dicRule_Create_Page = validateHeading.get(1);

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String mandatory_Error = errorMessage.get(0);
		String quotes_Error = errorMessage.get(1);

		List<String> create_Page_Data = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String invalid_data = create_Page_Data.get(0);
		String type_Field = create_Page_Data.get(1) + string.getRandomCharacter();
		String label_Field = create_Page_Data.get(3) + numbers.generateRandomNumber(0, 4);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(DICRules.TXT_DIC_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(DICRules.TXT_DIC_HEADING), dicRule_Page);
			foundation.click(DICRules.BTN_CREATE_NEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DICRules.TXT_CREATE_HEADING), dicRule_Create_Page);
			foundation.click(PrintGroupLists.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(DICRules.TXT_REQUIRED_ERROR), mandatory_Error);
			CustomisedAssert.assertEquals(foundation.getText(DICRules.TXT_NAME_ERROR), mandatory_Error);
			textBox.enterText(DICRules.TXTBX_NAME, invalid_data);
			CustomisedAssert.assertEquals(foundation.getText(PrintGroupLists.TXT_NAME_ERROR), quotes_Error);
			foundation.click(DICRules.BTN_CANCEL);
			foundation.waitforElement(DICRules.TXT_DIC_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(DICRules.TXT_DIC_HEADING), dicRule_Page);
			// click on create New Btn & Cancel Btn
			dicRules.createDICRule(type_Field, label_Field, create_Page_Data);
			foundation.click(DICRules.BTN_CANCEL);

			// click on create New Btn & Save Btn
			dicRules.createDICRule(type_Field, label_Field, create_Page_Data);
			foundation.click(DICRules.BTN_SAVE);
			foundation.waitforElementToDisappear(PrintGroupLists.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			browser.close();

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "168140-QAA-301 Update the changes on Print Summary Page and click on 'Cancel' then 'Save' button")
	public void DICRulesUpdateFields() {
		final String CASE_NUM = "168140";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.EFT_DISBURSEMENT).split(Constants.DELIMITER_TILD));
		String dicRule_Page = validateHeading.get(0);
		String dicRule_Show_Page = validateHeading.get(1);

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String existing_DIC = errorMessage.get(0);
		String active_disabled_message = errorMessage.get(1);
		String validating_record = errorMessage.get(2);

		List<String> create_Page_Data = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String existing_DICRule = create_Page_Data.get(0);
		String updated_DICRule = create_Page_Data.get(1);
		String existing_seqNbr = create_Page_Data.get(2);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(DICRules.TXT_DIC_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(DICRules.TXT_DIC_HEADING), dicRule_Page);
			foundation.click(DICRules.BTN_CREATE_NEW);
			textBox.enterText(DICRules.TXTBX_NAME, existing_DICRule);
			textBox.enterText(DICRules.TXTBX_SEQNBR, existing_seqNbr);
			foundation.click(DICRules.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(DICRules.TXT_NAME_ERROR), existing_DIC);
			CustomisedAssert.assertEquals(foundation.getText(DICRules.TXTBX_SEQNBR_ERROR), existing_DIC);
			foundation.click(DICRules.BTN_CANCEL);

			// Update the DIC Rule
			dicRules.updateDICRule(existing_DICRule, validating_record, dicRule_Show_Page, updated_DICRule,
					active_disabled_message);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		// Resetting the data
		dicRules.updateDICRule(updated_DICRule, validating_record, dicRule_Show_Page, existing_DICRule,
				active_disabled_message);
	}

	@Test(description = "168450-QAA-284 Validate the error message for Mandatory fields when click on save button without entering any detail in Keystr and description Fields and validate the successfully entered fields."
			+ "QAA-284 Validate the error message for already created Lookup Type on Lookup Create page"
			+ "168451 - QAA-284 Validate the error message for validation of Single and Double quotes on Lookup Type Create Page")
	public void LookupTypeValidateMandatoryFields() {
		final String CASE_NUM = "168450";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String lookupList_Page = validateHeading.get(0);
		String lookup_Create_Page = validateHeading.get(1);

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String mandatory_Error = errorMessage.get(0);
		String quotes_Error = errorMessage.get(1);
		String existing_record_Error = errorMessage.get(2);

		List<String> create_Page_Data = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String invalid_name = create_Page_Data.get(0);
		String existing_name = create_Page_Data.get(1);
		String type_Field = create_Page_Data.get(2) + string.getRandomCharacter();
		String desc_Field = create_Page_Data.get(3);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(LookupType.TXT_LOOKUP_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(LookupType.TXT_LOOKUP_HEADING), lookupList_Page);
			foundation.click(LookupType.BTN_CREATE_NEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LookupType.TXT_CREATE_HEADING), lookup_Create_Page);
			foundation.click(LookupType.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(LookupType.TXT_TYPE_ERROR), mandatory_Error);
			CustomisedAssert.assertEquals(foundation.getText(LookupType.TXT_DESC_ERROR), mandatory_Error);
			textBox.enterText(LookupType.TXTBX_TYPE, invalid_name);
			CustomisedAssert.assertEquals(foundation.getText(LookupType.TXT_TYPE_ERROR), quotes_Error);
			textBox.enterText(LookupType.TXTBX_TYPE, existing_name);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertEquals(foundation.getText(LookupType.TXT_TYPE_ERROR), existing_record_Error);

			// click on create New Btn & Cancel Btn
			lookupType.createLookup(type_Field, desc_Field);
			foundation.click(LookupType.BTN_CANCEL);

			// click on create New Btn & Save Btn
			foundation.click(LookupType.BTN_CREATE_NEW);
			lookupType.createLookup(type_Field, desc_Field);
			foundation.click(LookupType.BTN_SAVE);
			foundation.waitforElementToDisappear(LookupType.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			browser.close();

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "168453-QAA-284 Update the already created Lookup type on Lookup Type Show Page , cancel the changes and then save")
	public void LookupTypeUpdateFields() {
		final String CASE_NUM = "168453";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String lookupList_Page = validateHeading.get(0);
		String lookup_Show_Page = validateHeading.get(1);
		String validating_record = validateHeading.get(2);

		List<String> create_Page_Data = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String existing_lookup = create_Page_Data.get(0);
		String updated_lookup = create_Page_Data.get(1);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(LookupType.TXT_LOOKUP_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(LookupType.TXT_LOOKUP_HEADING), lookupList_Page);

			// Update the Lookup
			lookupType.updateLookup(existing_lookup, validating_record, lookup_Show_Page, updated_lookup);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		// Resetting the data
		lookupType.updateLookup(updated_lookup, validating_record, lookup_Show_Page, existing_lookup);
	}

	@Test(description = "168578-QAA-288 ADM > Super > National Accounts Screen > Validate error message for Mandatory Fields"
			+ "168579 - QAA-288 ADM > Super > National Accounts Screen > Create National Account and add Org and Location National Account Summary Page"
			+ "168581 - QAA-288 ADM > Super > National Accounts Screen > Check National Account button as disabled when selected invalid Org"
			+ "168583 - QAA-288 ADM > Super > National Accounts Screen >Delete the national account from National Account list Page")
	public void NationalAccountValidateMandatoryFields() {
		final String CASE_NUM = "168578";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

		String nationalAccount_dashboard = rstNationalAccountsData.get(CNNationalAccounts.PROMPT_TITLE);
		String nationalAccount_Summary = rstNationalAccountsData.get(CNNationalAccounts.RULE_PAGE_TITLE);

		List<String> errorMessage = Arrays
				.asList(rstNationalAccountsData.get(CNNationalAccounts.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String nationalAccount_Error = errorMessage.get(0);
		String Client_Error = errorMessage.get(1);
		String invalidChars_Error = errorMessage.get(2);
		String existing_record_Error = errorMessage.get(3);

		List<String> create_Page_Data = Arrays.asList(
				rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME).split(Constants.DELIMITER_TILD));
		String invalid_name = create_Page_Data.get(1);
		String existing_name = create_Page_Data.get(0);
		String client_Name = rstNationalAccountsData.get(CNNationalAccounts.CLIENT_NAME);
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

			CustomisedAssert.assertEquals(foundation.getText(NationalAccounts.LBL_NATIONAL_ACCOUNT),
					nationalAccount_dashboard);
			foundation.click(NationalAccounts.BTN_CREATE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(NationalAccounts.LBL_NATIONAL_ACCOUNT_SUMMARY),
					nationalAccount_Summary);
			foundation.click(NationalAccounts.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(NationalAccounts.LBL_NATIONAL_ACCOUNT_ERROR),
					nationalAccount_Error);
			CustomisedAssert.assertEquals(foundation.getText(NationalAccounts.LBL_CLIENT_ERROR), Client_Error);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, invalid_name);
			foundation.click(NationalAccounts.BTN_SAVE);

			String invalidChars = foundation.getText(NationalAccounts.LBL_NATIONAL_ACCOUNT_ERROR);
			CustomisedAssert.assertTrue(invalidChars.contains(invalidChars_Error));

			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, existing_name);
			dropDown.selectItem(NationalAccounts.DPD_CLIENT_NAME,
					rstNationalAccountsData.get(CNNationalAccounts.CLIENT_NAME), Constants.TEXT);
			foundation.click(NationalAccounts.BTN_SAVE);
			String existingError = foundation.getText(NationalAccounts.LBL_EXISTING_ERROR);
			CustomisedAssert.assertTrue(existingError.contains(existing_record_Error));
			foundation.click(NationalAccounts.BTN_ACCEPT_POPUP);
			foundation.click(NationalAccounts.BTN_CANCEL);

			// Creating new national Account and click on cancel button
			nationalAccounts.createNewNationalAccount(accountName, client_Name);
			foundation.click(NationalAccounts.BTN_CANCEL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(NationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
			CustomisedAssert.assertTrue(table.getTblRowCount(NationalAccounts.TBL_ROW) <= 0);

			// Creating new national Account and click on save button
			nationalAccounts.createNewNationalAccount(accountName, client_Name);
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

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		// Resetting the data
		navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
		foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
		textBox.enterText(NationalAccounts.TXT_FILTER, accountName);
		foundation.click(NationalAccounts.ICO_DELETE);
		foundation.click(NationalAccounts.BTN_POP_UP_YES);
	}

	@Test(description = "168582-QAA-288 ADM > Super > National Accounts Screen >Update the national account details"
			+ "168580 - QAA-288 ADM > Super > National Accounts Screen > Validate the error message for existing National Account on Account Summary Page")
	public void NationalAccountUpdateFields() {
		final String CASE_NUM = "168582";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstNationalAccountsData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

		String nationalAccount = rstNationalAccountsData.get(CNNationalAccounts.NATIONAL_ACCOUNT_NAME);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Search for existing National Account
			CustomisedAssert.assertTrue(foundation.isDisplayed(NationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, nationalAccount);
			CustomisedAssert.assertTrue(table.getTblRowCount(NationalAccounts.TBL_ROW) <= 1);
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME), nationalAccount);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, nationalAccount + Constants.ACCOUNT_NAME);
			foundation.click(NationalAccounts.BTN_CANCEL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(NationalAccounts.LBL_NATIONAL_ACCOUNT));
			foundation.waitforElement(NationalAccounts.TBL_BODY, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, nationalAccount + Constants.ACCOUNT_NAME);
			CustomisedAssert.assertTrue(table.getTblRowCount(NationalAccounts.TBL_ROW) <= 0);
			foundation.waitforElement(NationalAccounts.TXT_FILTER, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, nationalAccount);
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME), nationalAccount);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, nationalAccount + Constants.ACCOUNT_NAME);
			foundation.click(NationalAccounts.BTN_SAVE);
			foundation.waitforElementToDisappear(SpecialService.TXT_SPINNER_MSG, Constants.SHORT_TIME);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(NationalAccounts.TXT_FILTER, Constants.SHORT_TIME);
			textBox.enterText(NationalAccounts.TXT_FILTER, nationalAccount + Constants.ACCOUNT_NAME);
			CustomisedAssert.assertTrue(table.getTblRowCount(NationalAccounts.TBL_ROW) <= 1);
			table.selectRow(rstNationalAccountsData.get(CNNationalAccounts.GRID_NAME),
					nationalAccount + Constants.ACCOUNT_NAME);
			textBox.enterText(NationalAccounts.TXT_ACCOUNT_NAME, nationalAccount);
			foundation.click(NationalAccounts.BTN_SAVE);
		}
	}

	@Test(description = "169165-QAA-287 ADM > Super > Campus > Validate error message for Mandatory Fields when Payroll Deduct is On or Off"
			+ "169175 - QAA-287 ADM > Super > Campus > Validation of all Fields for invalid characters and data"
			+ "169167 - QAA-287 ADM > Super > Campus > Create a new Campus with all details including Location and Orgs")
	public void CampusValidateAllFields() {
		final String CASE_NUM = "169165";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		String campusName = strings.getRandomCharacter();

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String campusList_Page = validateHeading.get(0);
		String campusSave_Page = validateHeading.get(1);

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String mandatory_Error = errorMessage.get(0);
		String groupName_Error = errorMessage.get(1);
		String invalidData_Error = errorMessage.get(2);

		List<String> invalidData = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertEquals(foundation.getText(Campus.LBL_CAMPUSLIST_HEADING), campusList_Page);
			foundation.click(Campus.BTN_CREATE_NEW);

			// validation for mandatory fields
			CustomisedAssert.assertTrue(foundation.isDisplayed(Campus.LBL_CAMPUSSAVE_HEADING), campusSave_Page);
			dropDown.selectItem(Campus.DRP_PAYROLL, invalidData.get(0), Constants.TEXT);
			foundation.click(Campus.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(Campus.TXT_PAYROLL_EMAIL_ERROR), mandatory_Error);
			CustomisedAssert.assertEquals(foundation.getText(Campus.TXT_NAME_ERROR), mandatory_Error);
			CustomisedAssert.assertEquals(foundation.getText(Campus.TXT_GROUPNAME_ERROR), groupName_Error);

			// validation for Invalid data
			campus.enterInvalidData(invalidData.get(1), invalidData_Error);
			foundation.click(Campus.BTN_CANCEL);

			// Create New Campus
			foundation.click(Campus.BTN_CREATE_NEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Campus.LBL_CAMPUSSAVE_HEADING), campusSave_Page);
			textBox.enterText(Campus.TXTBX_NAME, campusName);
			foundation.click(Campus.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "169168-QAA-287 ADM > Super > Campus > Update a campus from campus list page and save the changes")
	public void CampusUpdateFields() {
		final String CASE_NUM = "169168";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String campusList_Page = validateHeading.get(0);
		String campusShow_Page = validateHeading.get(1);

		String limitError = rstSuperListData.get(CNSuperList.ERROR_MESSAGE);
		List<String> updatedData = Arrays
				.asList(rstSuperListData.get(CNSuperList.UPDATED_DATA).split(Constants.DELIMITER_TILD));

		List<String> data = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertEquals(foundation.getText(Campus.LBL_CAMPUSLIST_HEADING), campusList_Page);

			// Update the details
			textBox.enterText(Campus.SEARCH_BOX, updatedData.get(0));
			foundation.click(Campus.SELECT_GRID);
			CustomisedAssert.assertEquals(foundation.getText(Campus.LBL_CAMPUSSHOW_HEADING), campusShow_Page);
			dropDown.selectItem(Campus.DRP_PAYCYCLE, data.get(0), Constants.TEXT);
			textBox.enterText(Campus.TXT_GROUP_NAME, data.get(1));
			textBox.enterText(Campus.TXT_SPEND_LIMIT, data.get(2));
			foundation.click(Campus.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(Campus.LBL_LIMIT_ERROR), limitError);
			textBox.enterText(Campus.TXT_SPEND_LIMIT, data.get(3));
			foundation.click(Campus.BTN_ADD);
			textBox.enterText(Campus.TXTBX_NAME, updatedData.get(1));
			foundation.click(Campus.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Reset the details
			textBox.enterText(Campus.SEARCH_BOX, updatedData.get(1));
			foundation.click(Campus.SELECT_GRID);
			textBox.enterText(Campus.TXTBX_NAME, updatedData.get(0));
			foundation.click(Campus.BTN_SAVE);
		}
	}

	@Test(description = "175783-QAA-295 ADM > Super > PageSet > QAA-295 ADM > Super > Pageset > Validate error message for Mandatory Fields on Pageset Create Page"
			+ "175786 - QAA-295 ADM > Super > Pageset > Validation of all Fields for invalid characters and data"
			+ "175784 - QAA-295 ADM > Super > Pageset > Create a new Page Set with selecting Page Def and intents and save the changes"
			+ "176159 - QAA-295 ADM > Super > Pageset > Create a new Page Set with selecting Page Def and intents and cancel the changes"
			+ "176161 - QAA-295 ADM > Super > Pageset > Validate error message for Existing Pageset Name")
	public void PageSetValidateAllFields() {
		final String CASE_NUM = "175783";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String pageSetList_Page = validateHeading.get(0);
		String pageSetCreate_Page = validateHeading.get(1);

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));
		String name_Error = errorMessage.get(0);
		String existing_Name_Error = errorMessage.get(1);

		List<String> data = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String existing_PageSet = data.get(0);
		String pageSet_Name = data.get(0) + string.getRandomCharacter();
		String service_Name = data.get(1);
		String pageSet_Def = data.get(2);
		String pageSet_Intent = data.get(3);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertEquals(foundation.getText(PageSet.LBL_PAGESET_LIST), pageSetList_Page);
			foundation.click(PageSet.BTN_CREATE_NEW);

			// validation for mandatory fields
			CustomisedAssert.assertTrue(foundation.isDisplayed(PageSet.LBL_PAGESET_CREATE), pageSetCreate_Page);
			foundation.click(PageSet.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(PageSet.LBL_PAGESET_ERROR), name_Error);

			// Validation for existing pageset
			textBox.enterText(PageSet.TXTBX_PAGESET, data.get(0));
			foundation.click(PageSet.BTN_SAVE);

			String existingPageset = foundation.getText(PageSet.LBL_PAGESET_ERROR);
			CustomisedAssert.assertTrue(existingPageset.contains(existing_Name_Error));

			// filling the details and click on Cancel button
			pageset.createPageSet(pageSet_Name, service_Name, pageSet_Def, pageSet_Intent, existing_PageSet);
			foundation.click(PageSet.BTN_CANCEL);

			// filling the details and click on Save button
			foundation.click(PageSet.BTN_CREATE_NEW);
			pageset.createPageSet(pageSet_Name, service_Name, pageSet_Def, pageSet_Intent, existing_PageSet);
			foundation.click(PageSet.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "175785 -QAA-295 ADM > Super > Pageset > Update a PageSet from PageSet list page and save the changes"
			+ "176160 - QAA-295 ADM > Super > Pageset > Update a PageSet from PageSet list page and cancel the changes")
	public void PagesetUpdateFields() {
		final String CASE_NUM = "175785";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String pageSetList_Page = validateHeading.get(0);
		String pageSet_Summary_Page = validateHeading.get(1);

		List<String> updatedData = Arrays
				.asList(rstSuperListData.get(CNSuperList.UPDATED_DATA).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertEquals(foundation.getText(PageSet.LBL_PAGESET_LIST), pageSetList_Page);

			// Update the details
			textBox.enterText(PageSet.TXTBX_SEARCHBOX, updatedData.get(0));
			foundation.click(PageSet.SELECT_GRID);
			CustomisedAssert.assertEquals(foundation.getText(PageSet.LBL_PAGESET_SUMMARY), pageSet_Summary_Page);
			textBox.enterText(PageSet.TXTBX_PAGESET, updatedData.get(1));
			foundation.click(PageSet.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Reset the details
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(PageSet.TXTBX_SEARCHBOX, updatedData.get(1));
			foundation.click(PageSet.SELECT_GRID);
			textBox.enterText(PageSet.TXTBX_PAGESET, updatedData.get(0));
			foundation.click(PageSet.BTN_SAVE);
		}
	}

	@Test(description = "176221 - QAA-289 ADM > Super > App Referral > Validate error message for Mandatory Fields on App Referral Create Page"
			+ "176222 - QAA-289 ADM > Super > App Referral > Validate error message for Referral Amount"
			+ "176223 - QAA-289 ADM > Super > App Referral > Create a new App Referral with all Fields and cancel the changes"
			+ "176226 - QAA-289 ADM > Super > App Referral > Create a new App Referral with all Fields and Save the changes"
			+ "176227 - QAA-289 ADM > Super > App Referral >Validate all fields as Disabled when select previously created App Referral and cancel the change")
	public void AppReferralValidation() {
		final String CASE_NUM = "176221";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));

		List<String> errorMessage = Arrays
				.asList(rstSuperListData.get(CNSuperList.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));

		List<String> textbox_data = Arrays
				.asList(rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD).split(Constants.DELIMITER_TILD));
		String newReferral = rstSuperListData.get(CNSuperList.UPDATED_DATA);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_APP_REFERRAL), validateHeading.get(0));
			foundation.click(AppReferral.BTN_CREATE_NEW);

			// validation for mandatory fields
			CustomisedAssert.assertTrue(foundation.isDisplayed(AppReferral.LBL_CREATE_APP_REFERRAL),
					validateHeading.get(1));
			foundation.click(AppReferral.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_LOCATION_ERROR), errorMessage.get(0));
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_REFERRAL_AMOUNT_ERROR),
					errorMessage.get(1));

			// Validation error Message for Referral Amount
			textBox.enterText(AppReferral.TXTBX_REFERRAL_AMOUNT, textbox_data.get(1));
			foundation.click(AppReferral.BTN_SAVE);
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_REFERRAL_AMOUNT_ERROR),
					errorMessage.get(2));

			// filling the details and click on Cancel button
			appReferral.createAppReferral(textbox_data);
			foundation.click(AppReferral.BTN_CANCEL);

			// filling the details and click on Save button
			foundation.click(AppReferral.BTN_CREATE_NEW);
			appReferral.createAppReferral(textbox_data);
			foundation.click(AppReferral.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(AppReferral.BTN_ACCEPT_POPUP);

			// Validating disable data on App Referral Page
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(AppReferral.TXTBX_SEARCH, textbox_data.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(AppReferral.SELECT_GRID);
			CustomisedAssert.assertTrue(foundation.isDisabled(AppReferral.DRP_ORG));
			CustomisedAssert.assertTrue(foundation.isDisabled(AppReferral.DRP_LOCATION));
			CustomisedAssert.assertTrue(foundation.isDisabled(AppReferral.TXTBX_REFERRAL_AMOUNT));
			CustomisedAssert.assertTrue(foundation.isDisabled(AppReferral.DRP_DISABLE));
			foundation.click(AppReferral.BTN_CANCEL);

			// validating existing org and location error
			textBox.enterText(AppReferral.TXTBX_SEARCH, textbox_data.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(AppReferral.SELECT_GRID);
			foundation.click(AppReferral.BTN_COPY_REFERRAL);
			dropDown.selectItem(AppReferral.DRP_LOCATION, textbox_data.get(0), Constants.TEXT);
			foundation.click(AppReferral.BTN_SAVE);
			foundation.waitforElement(AppReferral.LBL_REFERRAL_ERROR, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_REFERRAL_ERROR), errorMessage.get(3));
			CustomisedAssert.assertEquals(foundation.getText(AppReferral.LBL_EXISTING_LOC), errorMessage.get(4));
			foundation.click(AppReferral.BTN_ERROR_CANCEL);

			// copy Referral button and create a new Referral
			dropDown.selectItem(AppReferral.DRP_LOCATION, newReferral, Constants.TEXT);
			foundation.click(AppReferral.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(AppReferral.BTN_ACCEPT_POPUP);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			textBox.enterText(AppReferral.TXTBX_SEARCH, textbox_data.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(AppReferral.SELECT_GRID);
			foundation.click(AppReferral.BTN_END_REFERRAL);
			foundation.click(AppReferral.BTN_EXPIRE_REFERRAL);
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(AppReferral.TXTBX_SEARCH, newReferral);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(AppReferral.SELECT_GRID);
			foundation.click(AppReferral.BTN_END_REFERRAL);
			foundation.click(AppReferral.BTN_EXPIRE_REFERRAL);
		}

	}

	@Test(description = "176228 - QAA-289 ADM > Super > App Referral >Create new App Referral by Copy Referral button on App Referral Page")
	public void CopyButtonAppReferral() {
		final String CASE_NUM = "176228";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		String org = rstSuperListData.get(CNSuperList.DISBURSEMENT_PAGE_RECORD);

		String newReferral_Org = rstSuperListData.get(CNSuperList.UPDATED_DATA);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// search a Referral
			textBox.enterText(AppReferral.TXTBX_SEARCH, org);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(AppReferral.SELECT_GRID);
			foundation.click(AppReferral.BTN_COPY_REFERRAL);
			dropDown.selectItem(AppReferral.DRP_LOCATION, newReferral_Org, Constants.TEXT);
			foundation.click(AppReferral.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(AppReferral.BTN_ACCEPT_POPUP);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		// Resetting the data
		foundation.waitforElement(AppReferral.TXTBX_SEARCH, 10);
		textBox.enterText(AppReferral.TXTBX_SEARCH, newReferral_Org);
		foundation.threadWait(Constants.SHORT_TIME);
		foundation.click(AppReferral.SELECT_GRID);
		foundation.click(AppReferral.BTN_END_REFERRAL);
		foundation.click(AppReferral.BTN_EXPIRE_REFERRAL);
	}

	@Test(description = "164102- Validation of Corporate List and Corporate Summary")
	public void CorporateListValidation() {

		final String CASE_NUM = "164102";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> validations = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		String corporateList = validations.get(0);
		String corporateSummary = validations.get(1);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(CorporateAccountList.VALIDATE_CORPORATE_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CorporateAccountList.VALIDATE_CORPORATE_LIST),
					corporateList);

			foundation.click(CorporateAccountList.NAME_FIRST_RECORD);
			foundation.waitforElement(CorporateAccountList.VALIDATE_CORPORATE_SUMMARY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(CorporateAccountList.VALIDATE_CORPORATE_SUMMARY),
					corporateSummary);
			foundation.click(CorporateAccountList.CANCEL_BTN);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "164726-QAA-296-ADM>Super>Middid, Middid Page and columns Validation and Middid data sorting based on the slected option as Assigned an Not Assigned")
	public void MiddidPageValidation() {
		final String CASE_NUM = "164726";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> MiddidDropDownList = Arrays
				.asList(rstSuperListData.get(CNSuperList.UPDATED_DATA).split(Constants.DELIMITER_TILD));
		String Assigned = MiddidDropDownList.get(0);
		String notAssigned = MiddidDropDownList.get(1);

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// verify navigation to Middid page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Middid.TITL_MIDDID));

			List<String> columnNames = Arrays
					.asList(rstSuperListData.get(CNSuperList.PAGE_ROW_RECORD).split(Constants.DELIMITER_HASH));

			// verify columns of Middid table
			middid.getTableHeaders();
			middid.verifyMiddidHeaders(columnNames);

			// verify Middid table date sorting based selection as Assigned
			dropDown.selectItem(Middid.MIDDID_DATA_SORTING_DD, Assigned, Constants.TEXT);
			CustomisedAssert.assertTrue(middid.isdateAssigned());

			// verify Middid table date sorting based selection as Not Assigned
			dropDown.selectItem(Middid.MIDDID_DATA_SORTING_DD, notAssigned, Constants.TEXT);
			CustomisedAssert.assertFalse(middid.isdateAssigned());

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "164590-QAA-283-ADM>Super>LookUp - Validation for Create new Lookup and cancel the changes and then save, Update the existing Lookup")
	public void LookupCreateNewValidateCancelandSave() {
		final String CASE_NUM = "164590";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> validateHeading = Arrays
				.asList(rstSuperListData.get(CNSuperList.PAGE_ROW_RECORD).split(Constants.DELIMITER_TILD));
		String lookupList_Page = validateHeading.get(0);
		String lookup_Create_Page = validateHeading.get(1);
		String validating_record = validateHeading.get(2);
		String lookup_Show_Page = validateHeading.get(3);

		List<String> create_Page_Data = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));
		String lookup_Type = create_Page_Data.get(0);
		String keystr_Field = (create_Page_Data.get(1) + string.getRandomCharacter()).toUpperCase();
		String desc_Field = create_Page_Data.get(2);
		String update_keystr_Field = create_Page_Data.get(3) + string.getRandomCharacter();

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(Lookup.TXT_LOOKUP_HEADING, Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(foundation.getText(Lookup.TXT_LOOKUP_HEADING), lookupList_Page);

			// click on create New Btn & Cancel Btn
			foundation.click(Lookup.BTN_CREATE_NEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Lookup.TXT_CREATE_HEADING), lookup_Create_Page);
			lookup.createLookup(lookup_Type, keystr_Field, desc_Field);
			foundation.click(Lookup.BTN_CANCEL);

			// click on create New Btn & Save Btn
			foundation.click(Lookup.BTN_CREATE_NEW);
			lookup.createLookup(lookup_Type, keystr_Field, desc_Field);
			foundation.click(Lookup.BTN_SAVE);
			foundation.waitforElementToDisappear(Lookup.TXT_SPINNER_MSG, Constants.SHORT_TIME);

			// Update the Existing Lookup
			lookup.updateLookup(keystr_Field, validating_record, lookup_Show_Page, update_keystr_Field);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176458- QAA-291 ADM > Super > Copy> Verify user can copy Product from location to location, can save, cancel, checkall, uncheckall location")
	public void CopyProduct() {

		final String CASE_NUM = "176458";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		String validateHeading = rstSuperListData.get(CNSuperList.SUPER_NAME);
		String tab = rstSuperListData.get(CNSuperList.UPDATED_DATA);

		List<String> locations = Arrays
				.asList(rstSuperListData.get(CNSuperList.PAGE_ROW_RECORD).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			foundation.waitforElement(Copy.LBL_COPY_PRODUCT, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Copy.LBL_COPY_PRODUCT), validateHeading);
			dropDown.selectItem(Copy.DRP_FROM, locations.get(0), Constants.TEXT);
			dropDown.selectItem(Copy.DRP_TO, locations.get(1), Constants.TEXT);
			foundation.click(Copy.BTN_CHECKALL);
			foundation.click(Copy.BTN_UNCHECKALL);
			foundation.click(Copy.BTN_CANCEL);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			dropDown.selectItem(Copy.DRP_FROM, locations.get(0), Constants.TEXT);
			dropDown.selectItem(Copy.DRP_TO, locations.get(1), Constants.TEXT);
			String product = foundation.getText(Copy.SELECT_PRODUCT);
			foundation.click(Copy.BTN_CHECKALL);
			foundation.click(Copy.BTN_SAVE);
			foundation.waitforElementToDisappear(Copy.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			textBox.enterText(LocationList.TXT_FILTER, locations.get(1));
			locationList.selectLocationName(locations.get(1));
			locationSummary.selectTab(tab);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, product);
			Assert.assertTrue(foundation.getText(LocationSummary.PRODUCT_NAME).equals(product));
			foundation.threadWait(Constants.TWO_SECOND);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176459- QAA-291 ADM > Super > Copy > Verify User can copy category from location to location, can save and cancel")
	public void CopyCategory() {

		final String CASE_NUM = "176459";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		String validateHeading = rstSuperListData.get(CNSuperList.SUPER_NAME);

		List<String> locations = Arrays
				.asList(rstSuperListData.get(CNSuperList.PAGE_ROW_RECORD).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(Copy.LBL_COPY_CATEGORY, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Copy.LBL_COPY_CATEGORY), validateHeading);
			dropDown.selectItem(Copy.DRP_FROM, locations.get(0), Constants.TEXT);
			dropDown.selectItem(Copy.DRP_TO, locations.get(1), Constants.TEXT);
			foundation.click(Copy.BTN_CANCEL);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			dropDown.selectItem(Copy.DRP_FROM, locations.get(0), Constants.TEXT);
			dropDown.selectItem(Copy.DRP_TO, locations.get(1), Constants.TEXT);
			foundation.click(Copy.BTN_SAVE);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176460- QAA-291 ADM > Super > Copy> Verify User will be able to copy Home Commercial from location to location, can save and cancel, checkall, uncheckall locations below in the grid")
	public void CopyHomeCommercial() {

		final String CASE_NUM = "176460";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		String validateHeading = rstSuperListData.get(CNSuperList.SUPER_NAME);
		String tab = rstSuperListData.get(CNSuperList.UPDATED_DATA);

		List<String> locations = Arrays
				.asList(rstSuperListData.get(CNSuperList.PAGE_ROW_RECORD).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			foundation.waitforElement(Copy.LBL_COPY_HOME_COMMERCIAL, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Copy.LBL_COPY_HOME_COMMERCIAL), validateHeading);
			dropDown.selectItem(Copy.DRP_FROM, locations.get(0), Constants.TEXT);
			dropDown.selectItem(Copy.DRP_TO, locations.get(1), Constants.TEXT);
			foundation.click(Copy.BTN_CHECKALL);
			foundation.click(Copy.BTN_UNCHECKALL);
			foundation.click(Copy.BTN_CANCEL);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			dropDown.selectItem(Copy.DRP_FROM, locations.get(0), Constants.TEXT);
			dropDown.selectItem(Copy.DRP_TO, locations.get(1), Constants.TEXT);
			String[] product = foundation.getText(Copy.SELECT_PRODUCTS).split(Constants.DELIMITER_SPACE);
			foundation.click(Copy.BTN_CHECKALL);
			foundation.click(Copy.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			textBox.enterText(LocationList.TXT_FILTER, locations.get(1));
			locationList.selectLocationName(locations.get(1));
			locationSummary.selectTab(tab);
			textBox.enterText(LocationSummary.FILTER_HOME_CMMRCIAL, product[0]);
			Assert.assertTrue(foundation.getText(LocationSummary.HOME_CMMRCIAL_NAME).equals(product[0]));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176461- QAA-291 ADM > Super > Copy> Verify User can copy cart Commercial from location to location, can check and uncheck the locations below in the grid")
	public void CopyCartCommercial() {

		final String CASE_NUM = "176461";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> locations = Arrays
				.asList(rstSuperListData.get(CNSuperList.PAGE_ROW_RECORD).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(Copy.LBL_COPY_CART_COMMERCIAL, Constants.SHORT_TIME);
			dropDown.selectItem(Copy.DRP_FROM, locations.get(0), Constants.TEXT);
			dropDown.selectItem(Copy.DRP_TO, locations.get(1), Constants.TEXT);
			foundation.click(Copy.BTN_CHECKALL);
			foundation.click(Copy.BTN_UNCHECKALL);
			foundation.click(Copy.BTN_CANCEL);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			dropDown.selectItem(Copy.DRP_FROM, locations.get(0), Constants.TEXT);
			dropDown.selectItem(Copy.DRP_TO, locations.get(1), Constants.TEXT);
			foundation.click(Copy.BTN_CHECKALL);
			foundation.click(Copy.BTN_SAVE);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176467- QAA-293 ADM > Super > Report> Verify user can create new Report that can be saved or cancelled.")
	public void CreateNewReport() {

		final String CASE_NUM = "176467";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		List<String> headings = Arrays
				.asList(rstSuperListData.get(CNSuperList.SUPER_NAME).split(Constants.DELIMITER_TILD));

		List<String> record = Arrays
				.asList(rstSuperListData.get(CNSuperList.PAGE_ROW_RECORD).split(Constants.DELIMITER_TILD));
		final String action = String.valueOf(numbers.generateRandomNumber(0, 9999));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Validating the fields and click on cancel button
			foundation.click(Report.BTN_CREATE_NEW);
			CustomisedAssert.assertEquals(foundation.getText(Report.LBL_CREATE_REPORT), headings.get(1));
			textBox.enterText(Report.TXT_NAME, record.get(0) + string.getRandomCharacter());
			textBox.enterText(Report.TXT_ACTION, action);
			textBox.enterText(Report.TXT_FILTER, record.get(1));
			CustomisedAssert.assertEquals(foundation.getText(Report.LBL_ORG), record.get(1));
			foundation.click(Report.CHCKBX_ORG);
			foundation.click(Report.BTN_CANCEL);

			// Validating the fields and click on Save button
			foundation.click(Report.BTN_CREATE_NEW);
			CustomisedAssert.assertEquals(foundation.getText(Report.LBL_CREATE_REPORT), headings.get(1));
			textBox.enterText(Report.TXT_NAME, record.get(0) + string.getRandomCharacter());
			textBox.enterText(Report.TXT_ACTION, action);
			textBox.enterText(Report.TXT_FILTER, record.get(1));
			CustomisedAssert.assertEquals(foundation.getText(Report.LBL_ORG), record.get(1));
			foundation.click(Report.CHCKBX_ORG);
			foundation.click(Report.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "176468- QAA-293 ADM > Super > Report> Verify user can edit Update information that can be saved or cancelled.")
	public void UpdatePreviousReport() {

		final String CASE_NUM = "176468";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		String validateHeading = rstSuperListData.get(CNSuperList.SUPER_NAME);

		List<String> record = Arrays
				.asList(rstSuperListData.get(CNSuperList.PAGE_ROW_RECORD).split(Constants.DELIMITER_TILD));

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Search for old Report, Updating and cancel button
			textBox.enterText(Report.TXT_FILTER_REPORT, record.get(0));
			foundation.click(Report.SELECT_GRID);
			CustomisedAssert.assertEquals(foundation.getText(Report.LBL_REPORT_SUMMARY), validateHeading);
			textBox.enterText(Report.TXT_NAME, record.get(1));
			foundation.click(Report.BTN_CANCEL);

			// Search for old Report, Updating and Save button
			textBox.enterText(Report.TXT_FILTER_REPORT, record.get(0));
			foundation.click(Report.SELECT_GRID);
			CustomisedAssert.assertEquals(foundation.getText(Report.LBL_REPORT_SUMMARY), validateHeading);
			textBox.enterText(Report.TXT_NAME, record.get(1));
			foundation.click(Report.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting the data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(Report.TXT_FILTER_REPORT, record.get(1));
			foundation.click(Report.SELECT_GRID);
			CustomisedAssert.assertEquals(foundation.getText(Report.LBL_REPORT_SUMMARY), validateHeading);
			textBox.enterText(Report.TXT_NAME, record.get(0));
			foundation.click(Report.BTN_SAVE);
		}
	}

	@Test(description = "176510-QAA-290 ADM > Super > Announcement> Verify on the Announcement message page, User will be able to change the color, font size, message and link  and click on save,  it will the Announcement on top of the page.")
	public void ValidateAnnouncement() {

		final String CASE_NUM = "176510";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstSuperListData = dataBase.getSuperListData(Queries.SUPER, CASE_NUM);

		String validateHeading = rstSuperListData.get(CNSuperList.SUPER_NAME);

		List<String> record = Arrays
				.asList(rstSuperListData.get(CNSuperList.PAGE_ROW_RECORD).split(Constants.DELIMITER_TILD));
		List<String> updatedData = Arrays
				.asList(rstSuperListData.get(CNSuperList.UPDATED_DATA).split(Constants.DELIMITER_TILD));		

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Validating the fields and click on Save button
			CustomisedAssert.assertTrue(foundation.isDisplayed(Announcement.LBL_ANNOUNCEMENT_MSG), validateHeading);
			dropDown.selectItem(Announcement.DRP_COLOR, record.get(0), Constants.TEXT);
			dropDown.selectItem(Announcement.DRP_FONT_SIZE, record.get(1), Constants.TEXT);
			textBox.enterText(Announcement.TXT_MSG, record.get(2));
			foundation.click(Announcement.BTN_SAVE);

			// Validating the font size, background color and message
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.getBGColor(Announcement.VALIDATE_BG_COLOR);
			foundation.getFontSize(Announcement.LBL_VALIDATE_FONT);
			String validatingMessage = foundation.getText(Announcement.LBL_VALIDATE_FONT);
			CustomisedAssert.assertTrue(validatingMessage.contains(record.get(2)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			//resetting the data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			dropDown.selectItem(Announcement.DRP_COLOR, updatedData.get(0), Constants.TEXT);
			dropDown.selectItem(Announcement.DRP_FONT_SIZE, updatedData.get(1), Constants.TEXT);
			textBox.enterText(Announcement.TXT_MSG, updatedData.get(2));
			foundation.click(Announcement.BTN_SAVE);
		}
		
	}
}
