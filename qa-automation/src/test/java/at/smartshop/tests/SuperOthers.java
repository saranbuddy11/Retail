package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.DateAndTime;
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
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ConsumerRolesList;
import at.smartshop.pages.ContactList;
import at.smartshop.pages.CorporateAccountList;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.DataSourceManager;
import at.smartshop.pages.DeviceCreate;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.FinanceList;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgList;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.OrgstrList;
import at.smartshop.pages.PromotionList;
import at.smartshop.pages.SpecialService;
import at.smartshop.pages.UserList;
import at.smartshop.pages.UserRoles;

public class SuperOthers extends TestInfra {

	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private CreatePromotions createPromotions = new CreatePromotions();
	private LocationList locationList = new LocationList();
	private TextBox textBox = new TextBox();
	private DateAndTime dateAndTime = new DateAndTime();
	private PromotionList promotionList = new PromotionList();
	private EditPromotion editPromotion = new EditPromotion();
	private UserRoles userRoles = new UserRoles();
	private UserList userList = new UserList();
	private CheckBox checkBox = new CheckBox();
	private Table table = new Table();
	private Excel excel = new Excel();
	private Strings string = new Strings();
	private Numbers numbers = new Numbers();
	private Dropdown dropDown = new Dropdown();
	private OrgstrList orgstr = new OrgstrList();
	private ConsumerRolesList consumerRolesList = new ConsumerRolesList();
	private SpecialService specialService=new SpecialService();
	private Strings strings=new Strings();
	private DeviceCreate deviceCreate=new DeviceCreate();
	

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstUserRolesData;
	private Map<String, String> rstDeviceListData;
	private Map<String, String> rstOrgSummaryData;
	private Map<String, String> rstSuperListData;

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

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_CONTACT_SRC);
		}
	}
	
	//Consumer Roles Test Scenarios - 
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
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.ERROR_STATUS), expectedData);

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
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.VALIDATE_CARD_DEFINITION_START_ERROR),
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
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.ERROR_STATUS), role_Information_Status);
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.DESCRIPTION_ERROR_STATUS),
					role_Information_Status);
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.VALIDATE_CARD_DEFINITION_START_ERROR),
					card_Definition_Status);
			CustomisedAssert.assertEquals(foundation.getText(ConsumerRolesList.VALIDATE_CARD_DEFINITION_END_ERROR),
					card_Definition_Status);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}
	
	//Super > Orgstr Test Scenario
	
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

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {

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

		} catch (Throwable exc) {
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

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {

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

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {

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

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {

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

		} catch (Throwable exc) {
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


		} catch (Throwable exc) {
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

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {

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
			foundation.waitforElementToDisappear(OrgList.TXT_SPINNER_MSG,Constants.SHORT_TIME);
			textBox.enterText(OrgList.TXT_SEARCH_ORG, orgName);
			CustomisedAssert.assertTrue(foundation.getText(OrgList.LBL_FIRST_ORG_NAME).contains(orgName));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

    //Corporate Account Test Scenarios
	
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

			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.ZIP_ERROR), corporate_ZIP);
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.PHONE_ERROR), corporate_Phone);
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.EMAIL_ERROR), corporate_Email);
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.CRPDISBURSEMENT_ERROR), disbursement_Email);

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
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.NAME_ERROR), corporate_Name);

			// Validate the error Messages for Checkbox
			CustomisedAssert.assertEquals(foundation.getText(CorporateAccountList.VALIDATE_MESSAGE), corporate_Checkbox);

			// change the checkbox
			foundation.click(CorporateAccountList.DISBURSEMENT_DAY);
			foundation.isDisabled(CorporateAccountList.DISABLED_CHECKBOX);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	//Super > Finance (EFT Disbursement & Disbursement Adjustments) Test Scenarios
	
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

		} catch (Throwable exc) {
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

		} catch (Throwable exc) {
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
		} catch (Throwable exc) {
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

			//verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceCreate.TITLE_DEVICE_CREATE));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} 
	}
	
	@Test(description = "164171-ADM>Super>Special Service>Special Service List-Save device with valid details")
	public void specialServiceSaveWithValidData() {
		final String CASE_NUM = "164171";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String deviceName=strings.getRandomCharacter();

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			//verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			String msrType=deviceCreate.createDevice(deviceName);
			foundation.waitforElementToDisappear(SpecialService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(SpecialService.TXT_SEARCH, deviceName);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(SpecialService.LBL_ROW_DATA).contains(msrType));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} 
	}
	
	@Test(description = "166013-ADM>Super>Special Service>Special Service List- Cancel device summary")
	public void speacialServicecancelDeviceSummary() {
		final String CASE_NUM = "166013";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String deviceName=strings.getRandomCharacter();

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			//verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			textBox.enterText(DeviceCreate.TXT_NAME, deviceName);
			foundation.click(DeviceCreate.BTN_CANCEl);
			textBox.enterText(SpecialService.TXT_SEARCH, deviceName);
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.LBL_NO_RESULT));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} 
	}
	
	@Test(description = "166015-ADM>Super>Special Service>Special Service List- Edit device summary data")
	public void speacialServiceeditDeviceSummaryData() {
		final String CASE_NUM = "166015";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String deviceName=strings.getRandomCharacter();
		String editedDeviceName=Constants.EDITED+deviceName;

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			//verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			String msrType=deviceCreate.createDevice(deviceName);
			foundation.waitforElementToDisappear(SpecialService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(SpecialService.TXT_SEARCH, deviceName);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(SpecialService.LBL_ROW_DATA).contains(msrType));
			foundation.click(specialService.objSplServiceName(deviceName));
			textBox.enterText(DeviceCreate.TXT_NAME, editedDeviceName);
			foundation.click(DeviceCreate.BTN_SAVE);
			textBox.enterText(SpecialService.TXT_SEARCH, editedDeviceName);
			CustomisedAssert.assertTrue(foundation.isDisplayed(specialService.objSplServiceName(editedDeviceName)));
			
		} catch (Throwable exc) {
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

			//verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify all column sort functionality
			int columnNumber=1;
			List<WebElement> columnHeaders = Foundation.getDriver().findElements(SpecialService.LST_COLUMN_HEADERS);
			for (WebElement columnHeader : columnHeaders) {
				columnNumber++;
				columnHeader.click();
				foundation.verifySortText(specialService.listColumns(columnNumber), Constants.ASCENDING);
				columnHeader.click();
				foundation.verifySortText(specialService.listColumns(columnNumber), Constants.DESCENDING);
			}
			
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} 
	}
	
	@Test(description = "166897-ADM>Super>Special Service>Special Service List- Search list")
	public void speacialServiceSearchList() {
		final String CASE_NUM = "166897";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String deviceName=strings.getRandomCharacter();

		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			//verify navigation to special service list page
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify navigation to special service summary page
			textBox.enterText(SpecialService.TXT_SEARCH, Constants.AUTO_TEST);
			CustomisedAssert.assertTrue(foundation.isDisplayed(specialService.objSplServiceName(Constants.AUTO_TEST)));
		} catch (Throwable exc) {
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
			CustomisedAssert.assertTrue(foundation.isDisplayed(DataSourceManager.VALIDATE_DSM_HEADING),super_Name);
			
			//search for Report 
			textBox.enterText(DataSourceManager.DSM_SEARCH_BOX, search_data);
			CustomisedAssert.assertTrue(checkBox.isChkEnabled(DataSourceManager.DSM_CHECKBOX));
			foundation.click(DataSourceManager.DSM_CHECKBOX);
			foundation.waitforElement(DataSourceManager.DSM_SUCCESS_POPUP, Constants.SHORT_TIME);
		
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			CustomisedAssert.assertTrue(checkBox.isChecked(DataSourceManager.DSM_CHECKBOX));
			foundation.click(DataSourceManager.DSM_CHECKBOX);
			foundation.waitforElement(DataSourceManager.DSM_SUCCESS_POPUP, Constants.SHORT_TIME);

		}
	}

}