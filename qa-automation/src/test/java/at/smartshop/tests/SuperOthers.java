package at.smartshop.tests;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import at.framework.browser.Factory;
import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.files.PropertyFile;
import at.framework.generic.DateAndTime;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.ContactList;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.DeviceCreate;
import at.smartshop.pages.EditPromotion;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgList;
import at.smartshop.pages.OrgSummary;
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
	private Dropdown dropdown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Strings strings = new Strings();
	private DateAndTime dateAndTime = new DateAndTime();
	private PromotionList promotionList = new PromotionList();
	private EditPromotion editPromotion = new EditPromotion();
	private UserRoles userRoles = new UserRoles();
	private UserList userList = new UserList();
	private CheckBox checkBox = new CheckBox();
	private Table table = new Table();
	private Excel excel = new Excel();
	private DeviceCreate deviceCreate=new DeviceCreate();
	private SpecialService specialService=new SpecialService();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstUserRolesData;

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
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_CONTACT_SRC));
			foundation.deleteFile(FilePath.EXCEL_CONTACT_SRC);
			textBox.enterText(ContactList.TXT_SEARCH_CONTACTS, Constants.TESTING);
			foundation.click(ContactList.BTN_EXPORT);
			foundation.threadWait(Constants.TWO_SECOND);
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_CONTACT_SRC));

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_CONTACT_SRC);
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
			assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			assertTrue(foundation.isDisplayed(DeviceCreate.TITLE_DEVICE_CREATE));
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
			assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			String msrType=deviceCreate.createDevice(deviceName);
			foundation.waitforElementToDisappear(SpecialService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(SpecialService.TXT_SEARCH, deviceName);
			assertTrue(foundation.getTextofListElement(SpecialService.LBL_ROW_DATA).contains(msrType));
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
			assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			textBox.enterText(DeviceCreate.TXT_NAME, deviceName);
			foundation.click(DeviceCreate.BTN_CANCEl);
			textBox.enterText(SpecialService.TXT_SEARCH, deviceName);
			assertTrue(foundation.isDisplayed(SpecialService.LBL_NO_RESULT));
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
			assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify navigation to special service summary page
			foundation.click(SpecialService.BTN_CREATE_NEW);
			String msrType=deviceCreate.createDevice(deviceName);
			foundation.waitforElementToDisappear(SpecialService.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			textBox.enterText(SpecialService.TXT_SEARCH, deviceName);
			assertTrue(foundation.getTextofListElement(SpecialService.LBL_ROW_DATA).contains(msrType));
			foundation.click(specialService.objSplServiceName(deviceName));
			textBox.enterText(DeviceCreate.TXT_NAME, editedDeviceName);
			foundation.click(DeviceCreate.BTN_SAVE);
			textBox.enterText(SpecialService.TXT_SEARCH, editedDeviceName);
			assertTrue(foundation.isDisplayed(specialService.objSplServiceName(editedDeviceName)));
			
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
			assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
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
			assertTrue(foundation.isDisplayed(SpecialService.TITL_SPL_SERVICE_LIST));
			
			//verify navigation to special service summary page
			textBox.enterText(SpecialService.TXT_SEARCH, Constants.AUTO_TEST);
			assertTrue(foundation.isDisplayed(specialService.objSplServiceName(Constants.AUTO_TEST)));
		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} 
	}

}
