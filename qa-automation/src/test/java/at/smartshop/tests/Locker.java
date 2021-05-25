package at.smartshop.tests;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNLockerSystem;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNUserRoles;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreateLocker;
import at.smartshop.pages.CreateSystem;
import at.smartshop.pages.EditSystem;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.LockerEquipment;
import at.smartshop.pages.LockerSystem;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.UserList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Locker extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	private Foundation foundation = new Foundation();
	private LocationList locationList = new LocationList();
	private LockerSystem lockerSystem = new LockerSystem();
	private LocationSummary locationSummary = new LocationSummary();
	private CreateLocker createLocker = new CreateLocker();
	private Table table = new Table();
	private LockerEquipment lockerEquipment = new LockerEquipment();
	private CreateSystem newLockerSysytem = new CreateSystem();
	private UserList userList = new UserList();
	private Strings strings = new Strings();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstLockerSystemData;
	private Map<String, String> rstUserRolesData;

	@Test(description = "135549-This test validates 'Locker Systems' pickup location type under the Order Ahead Settings")
	public void verifyLockerSystemsPickupLocation() {
		try {
			final String CASE_NUM = "135549";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(LocationList.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER,
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_HAS_ORDER_AHEAD,
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, 2000);

			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			dropDown.selectItem(LocationSummary.DPD_HAS_PICK_UP_LOCATIONS,
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);

			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);
			foundation.isDisplayed(LocationSummary.LBL_LOCKER_PICK_UP_TITLE);

			List<String> Name = Arrays
					.asList(rstLocationSummaryData.get(CNLocationSummary.NAME).split(Constants.DELIMITER_TILD));

			String lockerName = foundation.getText(LocationSummary.LNK_LOCKER_NAME);
			Assert.assertTrue(lockerName.equals(Name.get(0)));

			String shelfLife = foundation.getText(LocationSummary.LBL_SHELF_LIFE);
			Assert.assertTrue(shelfLife.equals(Name.get(1)));

			foundation.click(LocationSummary.LNK_LOCKER_NAME);
			String systemName = foundation.getTextAttribute(LocationSummary.TXT_SYSTEM_NAME);
			Assert.assertTrue(systemName.equals(Name.get(0)));

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "C135550 - This test validates 'Locker Systems' pickup location type under the Order Ahead Settings when there are NO locker systems created")
	public void verifyNoLockerSystemsPickupLocation() {
		try {
			final String CASE_NUM = "135550";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocationName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER,
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_HAS_ORDER_AHEAD,
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, 2000);

			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocationName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));

			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);

			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			dropDown.selectItem(LocationSummary.DPD_HAS_PICK_UP_LOCATIONS,
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);

			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);
			foundation.isDisplayed(LocationSummary.LBL_LOCKER_PICK_UP_TITLE);

			List<String> Name = Arrays
					.asList(rstLocationSummaryData.get(CNLocationSummary.NAME).split(Constants.DELIMITER_TILD));

			String lockerName = foundation.getText(LocationSummary.LNK_LOCKER_NAME);
			Assert.assertTrue(lockerName.equals(Name.get(0)));

			foundation.click(LocationSummary.LNK_LOCKER_NAME);
			foundation.waitforElement(LocationSummary.TXT_SYSTEM_NAME, 2000);

			dropDown.selectItem(CreateLocker.DPD_LOCATION, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME),
					Constants.TEXT);
			textBox.enterText(CreateLocker.TXT_SYSTEM_NAME, rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME));
			textBox.enterText(CreateLocker.TXT_DISPLAY_NAME, rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME));
			dropDown.selectItem(CreateLocker.DPD_LOCKER_MODEL, rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL),
					Constants.TEXT);
			foundation.click(CreateLocker.BTN_SAVE);
			foundation.waitforElement(LockerSystem.BTN_CREATE_SYSTEM, 2000);

			navigationBar.navigateToMenuItem(menuItem.get(0));
			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocationName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));

			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			dropDown.selectItem(LocationSummary.DPD_HAS_PICK_UP_LOCATIONS,
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);

			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);
			Assert.assertTrue(lockerName.equals(Name.get(0)));

			navigationBar.navigateToMenuItem(menuItem.get(1));

			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.click(lockerSystem.copyORDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),
					Constants.DELETE));
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);

			foundation.click(LockerSystem.BTN_YES_DELETE);
			foundation.waitforElement(LockerSystem.MSG_DELETE_SUCCESS, 2000);

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "C120330 - This test validates the Delete icon in the Location Locker")
	public void verifyLockerDeleteIcon() {
		try {
			final String CASE_NUM = "120330";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			lockerSystem.expandLocationLocker(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.isDisplayed(LockerSystem.ICO_SIBLING_DELETE);

			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			lockerSystem.expandLocationLocker(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.isDisplayed(LockerSystem.ICO_SIBLING_DELETE);

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "C120331 - This test validates functionality of Delete' icon in the Location Locker")
	public void verifyDeletePopupMessage() {
		try {
			final String CASE_NUM = "120331";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));

			foundation.click(lockerSystem.copyORDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),
					Constants.DELETE));
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);

			List<String> popUpMessage = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_YES_DELETE));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_CANCEL));

			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_TITLE).equals(popUpMessage.get(0)));
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_MESSAGE).equals(popUpMessage.get(1)));

			foundation.click(LockerSystem.BTN_CANCEL);

			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));

			foundation.click(lockerSystem.copyORDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),
					Constants.DELETE));
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);

			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_YES_DELETE));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_CANCEL));

			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_TITLE).equals(popUpMessage.get(0)));
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_MESSAGE).equals(popUpMessage.get(1)));

			foundation.click(LockerSystem.BTN_CANCEL);
		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "C120332 - This test validates the functionality of Cancel button in the warning popup.")
	public void verifyLockerSystemNotDeleted() {
		try {
			final String CASE_NUM = "120332";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));

			foundation.click(lockerSystem.copyORDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),
					Constants.DELETE));
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);

			List<String> popUpMessage = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_YES_DELETE));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_CANCEL));

			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_TITLE).equals(popUpMessage.get(0)));
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_MESSAGE).equals(popUpMessage.get(1)));

			foundation.click(LockerSystem.BTN_CANCEL);

			Assert.assertTrue(foundation
					.isDisplayed(lockerSystem.objSchedule(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME))));

			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));

			foundation.click(lockerSystem.copyORDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),
					Constants.DELETE));
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);

			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_YES_DELETE));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_CANCEL));

			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_TITLE).equals(popUpMessage.get(0)));
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_MESSAGE).equals(popUpMessage.get(1)));

			foundation.click(LockerSystem.BTN_CANCEL);

			Assert.assertTrue(foundation
					.isDisplayed(lockerSystem.objSchedule(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME))));
		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "C120333 - This test validates the functionality of Yes, Delete button in the warning popup.")
	public void verifyDeleteLockerSystems() {
		try {
			final String CASE_NUM = "120333";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocationName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER,
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_HAS_ORDER_AHEAD,
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, 2000);

			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CreateLocker.BTN_CREATE_SYSTEM);

			dropDown.selectItem(CreateLocker.DPD_LOCATION, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME),
					Constants.TEXT);
			textBox.enterText(CreateLocker.TXT_SYSTEM_NAME, rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME));
			textBox.enterText(CreateLocker.TXT_DISPLAY_NAME, rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME));
			dropDown.selectItem(CreateLocker.DPD_LOCKER_MODEL, rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL),
					Constants.TEXT);
			foundation.click(CreateLocker.BTN_SAVE);
			foundation.waitforElement(LockerSystem.BTN_CREATE_SYSTEM, 2000);

			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.isDisplayed(LockerSystem.ICO_SIBLING_DELETE);

			foundation.click(lockerSystem.copyORDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),
					Constants.DELETE));
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);

			List<String> popUpMessage = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_YES_DELETE));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_CANCEL));

			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_TITLE).equals(popUpMessage.get(0)));
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_MESSAGE).equals(popUpMessage.get(1)));

			foundation.click(LockerSystem.BTN_YES_DELETE);
			foundation.waitforElement(LockerSystem.MSG_DELETE_SUCCESS, 2000);

			Assert.assertFalse(foundation
					.isDisplayed(lockerSystem.objSchedule(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME))));

		} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "135540-This test validates the Has locker with Default option in the Location Summary page")
	public void verifyHasLockerWithNo() {
		try {
			final String CASE_NUM = "135540";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			List<String> requiredData = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			locationSummary.verifyHasLockerField(requiredData.get(1));
			foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
			dropDown.selectItem(LocationSummary.DPD_HAS_PICK_UP_LOCATIONS, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);
			Assert.assertFalse(foundation.isDisplayed(LocationSummary.LBL_LOCKER_PICK_UP_TITLE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_LOCATION_LOCKER_SYSTEM));
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_CREATE_SYSTEM));
			createLocker.verifyLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstLocationListData.get(CNLocationList.INFO_MESSAGE));

			login.logout();
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
			dropDown.selectItem(LocationSummary.DPD_HAS_PICK_UP_LOCATIONS, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);
			Assert.assertFalse(foundation.isDisplayed(LocationSummary.LBL_LOCKER_PICK_UP_TITLE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_LOCATION_LOCKER_SYSTEM));
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_CREATE_SYSTEM));
			createLocker.verifyLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstLocationListData.get(CNLocationList.INFO_MESSAGE));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "135541-This test validates the Has locker with Yes option in the Location Summary page")
	public void verifyHasLockerWithYes() {
		try {
			final String CASE_NUM = "135541";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			List<String> requiredData = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);

			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCKER_PICK_UP_TITLE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_LOCATION_LOCKER_SYSTEM));
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_CREATE_SYSTEM));
			createLocker.verifyLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstLocationListData.get(CNLocationList.LOCATION_NAME));

			login.logout();
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));

			foundation.click(LocationSummary.BUTTON_LOCATION_INFO);
			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_LOCKER_PICK_UP_TITLE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_LOCATION_LOCKER_SYSTEM));
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_CREATE_SYSTEM));
			createLocker.verifyLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstLocationListData.get(CNLocationList.LOCATION_NAME));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "120320-This test validates the Scheduling button in the Locker system page")
	public void verifySchedulingButton() {
		try {
			final String CASE_NUM = "120320";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.LBL_PAGE_TITLE));

			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.waitforElement(LockerSystem.BTN_SCHEDULING, 2);
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_SCHEDULING));

			lockerSystem.verifyLocationColumns(rstLockerSystemData.get(CNLockerSystem.COLUMN_NAMES));
			login.logout();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.LBL_PAGE_TITLE));

			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.waitforElement(LockerSystem.BTN_SCHEDULING, 2);
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_SCHEDULING));

			lockerSystem.verifyLocationColumns(rstLockerSystemData.get(CNLockerSystem.COLUMN_NAMES));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "120321-This test validates the functionality of Scheduling button in Locker system page")
	public void verifySchedulingButtonFunctionality() {
		try {
			final String CASE_NUM = "120321";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.LBL_PAGE_TITLE));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.waitforElement(LockerSystem.BTN_SCHEDULING, 2);
			foundation.click(LockerSystem.BTN_SCHEDULING);
			Assert.assertTrue(foundation.getText(LockerSystem.LBL_CUBBY_SCHEDULING_SCREEN)
					.contains(rstLockerSystemData.get(CNLockerSystem.PAGE_TITLE)));

			login.logout();
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.LBL_PAGE_TITLE));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.waitforElement(LockerSystem.BTN_SCHEDULING, 2);
			foundation.click(LockerSystem.BTN_SCHEDULING);
			Assert.assertTrue(foundation.getText(LockerSystem.LBL_CUBBY_SCHEDULING_SCREEN)
					.contains(rstLockerSystemData.get(CNLockerSystem.PAGE_TITLE)));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "120322-This test validates the Scheduling button in Locker system page when no locker created")
	public void verifySchedulingButtonWithNoLocker() {
		try {
			final String CASE_NUM = "120322";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.LBL_PAGE_TITLE));
			Assert.assertTrue(foundation.getSizeofListElement(
					lockerSystem.objExpandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME))) == 0);

			login.logout();
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.LBL_PAGE_TITLE));
			Assert.assertTrue(foundation.getSizeofListElement(
					lockerSystem.objExpandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME))) == 0);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "135544-Validate the Locker Equipment screen (layout when a locker model is tapped).")
	public void LockerEquipment() {
		try {
			final String CASE_NUM = "135544";

			Map<String, String> dbData = new HashMap<>();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			String lockerModelName = rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			Assert.assertTrue(foundation.isDisplayed(at.smartshop.pages.LockerEquipment.TXT_PAGE_TITLE));

			// table header validations
			List<String> columnNames = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.COLUMN_NAMES).split(Constants.DELIMITER_TILD));
			for (int i = 0; i < columnNames.size(); i++) {
				dbData.put(columnNames.get(i), columnNames.get(i));
			}
			Map<String, String> uiTableHeader = lockerEquipment
					.getLockerEquipmentTblHeadersUI(LockerEquipment.TBL_LOCKER_EQUIPMENT_HEADER);

			assertEquals(uiTableHeader, dbData);

			// Locker model validation
			foundation.click(LockerEquipment.LINK_MODEL);
			String modelName = foundation.getText(LockerEquipment.TXT_MODEL_NAME);
			Assert.assertEquals(modelName, lockerModelName);

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "135545-Validate the Pre Programed in grid 'Locker Model' Functionality")
	public void LockerEquipmentTable() {
		try {
			final String CASE_NUM = "135545";

			Map<String, String> dbData_Locker_20 = new HashMap<>();
			Map<String, String> dbData_Locker_18 = new HashMap<>();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			Assert.assertTrue(foundation.isDisplayed(LockerEquipment.TXT_PAGE_TITLE));

			List<String> requiredData = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_HASH));

			List<String> requiredData_Locker_20 = Arrays.asList(requiredData.get(0).split(Constants.DELIMITER_TILD));

			List<String> requiredData_Locker_18 = Arrays.asList(requiredData.get(1).split(Constants.DELIMITER_TILD));

			List<String> columnNames = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.COLUMN_NAMES).split(Constants.DELIMITER_TILD));

			// Add db data to Array list
			// locker system 20
			dbData_Locker_20.put(columnNames.get(0), requiredData_Locker_20.get(0));
			dbData_Locker_20.put(columnNames.get(1), requiredData_Locker_20.get(1));
			dbData_Locker_20.put(columnNames.get(2), requiredData_Locker_20.get(2));
			dbData_Locker_20.put(columnNames.get(3), requiredData_Locker_20.get(3));
			dbData_Locker_20.put(columnNames.get(4), requiredData_Locker_20.get(4));
			dbData_Locker_20.put(columnNames.get(5), requiredData_Locker_20.get(5));
			// locker system 18
			dbData_Locker_18.put(columnNames.get(0), requiredData_Locker_18.get(0));
			dbData_Locker_18.put(columnNames.get(1), requiredData_Locker_18.get(1));
			dbData_Locker_18.put(columnNames.get(2), requiredData_Locker_18.get(2));
			dbData_Locker_18.put(columnNames.get(3), requiredData_Locker_18.get(3));
			dbData_Locker_18.put(columnNames.get(4), requiredData_Locker_18.get(4));
			dbData_Locker_18.put(columnNames.get(5), requiredData_Locker_18.get(5));

			// Table Validations
			Map<String, String> uiData_locker_20 = table
					.getTblSingleRowRecordUI(LockerEquipment.TBL_LOCKER_EQUIPMENT_HEADER, LockerEquipment.TBL_ROW_1);// table.getTblRecordsUI();

			assertEquals(uiData_locker_20, dbData_Locker_20);

			Map<String, String> uiData_locker_18 = table
					.getTblSingleRowRecordUI(LockerEquipment.TBL_LOCKER_EQUIPMENT_HEADER, LockerEquipment.TBL_ROW_2);
			assertEquals(uiData_locker_18, dbData_Locker_18);

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "135543-Validate the Locker Equipment screen (layout when a no locker model is tapped).")
	public void LockerEquipmentSystem() {
		try {
			final String CASE_NUM = "135543";

			Map<String, String> dbData = new HashMap<>();

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			Assert.assertTrue(foundation.isDisplayed(LockerEquipment.TXT_PAGE_TITLE));

			// table header validations
			List<String> columnNames = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.COLUMN_NAMES).split(Constants.DELIMITER_TILD));
			for (int i = 0; i < columnNames.size(); i++) {
				dbData.put(columnNames.get(i), columnNames.get(i));
			}

			Map<String, String> uiTableHeader = lockerEquipment
					.getLockerEquipmentTblHeadersUI(LockerEquipment.TBL_LOCKER_EQUIPMENT_HEADER);

			assertEquals(uiTableHeader, dbData);

			// Locker model validation
			Assert.assertFalse(foundation.isDisplayed(LockerEquipment.TXT_MODEL_NAME));

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "135547-Validate Locker Model > 18 Door Master from the grid and views")
	public void validateLockerModel18Door() {
		try {
			final String CASE_NUM = "135547";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);

			assertTrue(foundation.isDisplayed(LockerEquipment.LOCKER_EQUIPMENT_GRID));
			assertTrue(foundation
					.isDisplayed(lockerEquipment.objLockerModel(rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL))));
			foundation.click(lockerEquipment.objLockerModel(rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL)));
			assertEquals(foundation.getText(LockerEquipment.LBL_18_MODEL_TITLE),
					rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL_TITLE));
			foundation.isDisplayed(LockerEquipment.LBL_18_MODEL_IMG);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "135546-Validate Locker Model > 20 Door Satellite from the grid and views")
	public void validateLockerModel20Door() {
		try {
			final String CASE_NUM = "135546";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);

			assertTrue(foundation.isDisplayed(LockerEquipment.LOCKER_EQUIPMENT_GRID));
			assertTrue(foundation
					.isDisplayed(lockerEquipment.objLockerModel(rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL))));
			foundation.click(lockerEquipment.objLockerModel(rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL)));
			assertEquals(foundation.getText(LockerEquipment.LBL_20_MODEL_TITLE),
					rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL_TITLE));
			foundation.isDisplayed(LockerEquipment.LBL_20_MODEL_IMG);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "120327-Verify the functionality of 'Copy' icon in the 'Location Locker system dashboard' page")
	public void functaionalityOfCopyIcon() {
		try {
			final String CASE_NUM = "120327";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			String locationName = rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME);
			String systemName = rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME);
			String displayName = rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME);
			String lockerModel = rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);

			// create new system
			foundation.threadWait(2000);
			boolean isSystemExist = foundation.isDisplayed(lockerSystem.objExpandLocationLocker(locationName));
			if (isSystemExist == false) {
				foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
				newLockerSysytem.createNewSystem(locationName, systemName, displayName, lockerModel);
			}

			// validate copy functionality
			foundation.click(lockerSystem.objExpandLocationLocker(locationName));
			foundation.click(lockerSystem.copyORDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),
					Constants.COPY));
			assertEquals(dropDown.getSelectedItem(CreateSystem.DPD_LOCATION), locationName);
			assertTrue(foundation.isDisplayed(CreateSystem.LBL_CREATE_SYSTEM));
			assertEquals(textBox.getTextFromInput(CreateSystem.TXT_SYSTEM_NAME), "");
			assertEquals(textBox.getTextFromInput(CreateSystem.TXT_DISPLAY_NAME), "");
			assertEquals(dropDown.getSelectedItem(CreateSystem.DPD_EDIT_LOCKER_MODEL), lockerModel);
			assertFalse(foundation.isEnabled(CreateSystem.BTN_SAVE));

			// cleanupdata
			foundation.click(CreateSystem.BTN_CANCEL);
			lockerSystem.deleteSystem(locationName, systemName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "120326-Verify the 'Copy' icon in the 'Location Locker system dashboard' page")
	public void verifyCopyIcon() {
		try {
			final String CASE_NUM = "120326";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			String locationName = rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME);
			String systemName = rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME);
			String displayName = rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME);
			String lockerModel = rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);

			// create new system
			foundation.threadWait(2000);
			boolean isSystemExist = foundation.isDisplayed(lockerSystem.objExpandLocationLocker(locationName));
			if (isSystemExist == false) {
				foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
				newLockerSysytem.createNewSystem(locationName, systemName, displayName, lockerModel);
			}

			// validate copy functionality
			foundation.click(lockerSystem.objExpandLocationLocker(locationName));
			foundation.waitforElement(LockerSystem.ICO_SIBLING_COPY, 500);
			assertTrue(foundation.isDisplayed(LockerSystem.ICO_SIBLING_COPY));

			// cleanup data
			foundation.click(lockerSystem.copyORDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),
					Constants.DELETE));
			foundation.click(LockerSystem.BTN_YES_DELETE);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "120328-Verify the 'Save' button when 'Display' field is blank")
	public void verifySaveWithDisplayBlank() {
		try {
			final String CASE_NUM = "120328";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			String locationName = rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME);
			String systemName = rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME);
			String displayName = rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME);
			String lockerModel = rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);

			// create new system
			foundation.threadWait(2000);
			boolean isSystemExist = foundation.isDisplayed(lockerSystem.objExpandLocationLocker(locationName));
			if (isSystemExist == false) {
				foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
				newLockerSysytem.createNewSystem(locationName, systemName, displayName, lockerModel);
			}

			// validate copy functionality
			foundation.click(lockerSystem.objExpandLocationLocker(locationName));
			foundation.click(lockerSystem.copyORDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),
					Constants.COPY));
			assertEquals(dropDown.getSelectedItem(CreateSystem.DPD_LOCATION), locationName);
			assertEquals(textBox.getTextFromInput(CreateSystem.TXT_DISPLAY_NAME), "");
			assertEquals(dropDown.getSelectedItem(CreateSystem.DPD_EDIT_LOCKER_MODEL), lockerModel);
			assertFalse(foundation.isEnabled(CreateSystem.BTN_SAVE));

			// cleanupdata
			foundation.click(CreateSystem.BTN_CANCEL);
			lockerSystem.deleteSystem(locationName, systemName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "120329-Verify the 'Save' button when 'System' field is blank")
	public void verifySaveWithSystemBlank() {
		try {
			final String CASE_NUM = "120329";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			String locationName = rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME);
			String systemName = rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME);
			String displayName = rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME);
			String lockerModel = rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);

			// create new system
			foundation.threadWait(2000);
			boolean isSystemExist = foundation.isDisplayed(lockerSystem.objExpandLocationLocker(locationName));
			if (isSystemExist == false) {
				foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
				newLockerSysytem.createNewSystem(locationName, systemName, displayName, lockerModel);
			}

			// validate copy functionality
			foundation.click(lockerSystem.objExpandLocationLocker(locationName));
			foundation.click(lockerSystem.copyORDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),
					Constants.COPY));
			assertEquals(dropDown.getSelectedItem(CreateSystem.DPD_LOCATION), locationName);
			assertEquals(textBox.getTextFromInput(CreateSystem.TXT_SYSTEM_NAME), "");
			assertEquals(dropDown.getSelectedItem(CreateSystem.DPD_EDIT_LOCKER_MODEL), lockerModel);
			assertFalse(foundation.isEnabled(CreateSystem.BTN_SAVE));

			// cleanupdata
			foundation.click(CreateSystem.BTN_CANCEL);
			lockerSystem.deleteSystem(locationName, systemName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "135740-Verify the 'Location' dropdown in Edit a system screen for Locker settings enabled in Location Summary - Operator")
	public void locationDropdownEditSystemOperator() {
		try {
			final String CASE_NUM = "135740";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			String systemName = rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME);
			String displayName = rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME);
			String lockerModel = rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL);
			String defaultLocation = rstLockerSystemData.get(CNLockerSystem.DEFAULT_LOCATION);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> locations = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME).split(Constants.DELIMITER_TILD));
			List<String> menuItems = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> hasLocker = Arrays
					.asList(rstLocationSummaryData.get(CNLocationSummary.HAS_LOCKERS).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menuItems.get(0));
			locationList.selectLocationName(locations.get(0));
			locationSummary.updateLockerSettings(hasLocker.get(0));
			locationList.selectLocationName(locations.get(1));
			locationSummary.updateLockerSettings(hasLocker.get(1));
			login.logout();

			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItems.get(1));
			boolean isSystemExist = foundation.isDisplayed(lockerSystem.objExpandLocationLocker(locations.get(0)));
			if (isSystemExist == false) {
				foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
				newLockerSysytem.createNewSystem(locations.get(0), systemName, displayName, lockerModel);
			}
			foundation.threadWait(2000);
			foundation.click(lockerSystem.objExpandLocationLocker(locations.get(0)));
			foundation.click(lockerSystem.objSystemName(systemName));
			assertTrue(dropDown.verifyItemPresent(CreateSystem.DPD_LOCATION, defaultLocation));
			assertTrue(dropDown.verifyItemPresent(CreateSystem.DPD_LOCATION, locations.get(0)));
			assertTrue(dropDown.verifyItemPresent(CreateSystem.DPD_LOCATION, defaultLocation));
			assertFalse(dropDown.verifyItemPresent(CreateSystem.DPD_LOCATION, locations.get(1)));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "This test validates the Functionality of an Create System button")
	public void verifyCreateSystemButtonFunctionality() {
		try {
			final String CASE_NUM = "135731";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			Assert.assertTrue(foundation.isDisplayed(LockerSystem.LBL_PAGE_TITLE));
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			assertTrue(foundation.isDisplayed(CreateLocker.LBL_CREATE_SYSTEM));

			Assert.assertTrue(foundation.getTextAttribute(CreateLocker.TXT_SYSTEM_NAME).isEmpty());
			Assert.assertTrue(foundation.getTextAttribute(CreateLocker.TXT_DISPLAY_NAME).isEmpty());

			login.logout();
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.LBL_PAGE_TITLE));
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			assertTrue(foundation.isDisplayed(CreateLocker.LBL_CREATE_SYSTEM));

			Assert.assertTrue(foundation.getTextAttribute(CreateLocker.TXT_SYSTEM_NAME).isEmpty());
			Assert.assertTrue(foundation.getTextAttribute(CreateLocker.TXT_DISPLAY_NAME).isEmpty());

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "C135751 - SOS-22234- Verify the System and Display Name fields in Create a System screen - Super")
	public void verifySuperSystemAndDisplayFields() {
		try {
			final String CASE_NUM = "135751";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			assertTrue(foundation.isDisplayed(CreateSystem.LBL_PAGE_TITLE));

			assertTrue(foundation.isDisplayed(CreateSystem.DPD_LOCATION_SIBLING));

			List<String> systemName = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME).split(Constants.DELIMITER_TILD));
			List<String> displayName = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME).split(Constants.DELIMITER_TILD));
			List<String> errorMessage = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));

			// Verifying invalid system name
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, systemName.get(0));
			foundation.click(CreateSystem.TXT_DISPLAY_NAME);
			String errorMessageUI = foundation.getText(CreateSystem.LBL_SYSTEM_ERROR);
			assertTrue(errorMessageUI.equals(errorMessage.get(0)));

			// Verifying alphanumeric characters for system name
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, systemName.get(1));
			foundation.click(CreateSystem.TXT_DISPLAY_NAME);
			Assert.assertFalse(foundation.isDisplayed(CreateSystem.LBL_SYSTEM_ERROR));

			// Creating System with duplicate system name
			newLockerSysytem.createNewSystem(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME), systemName.get(2),
					displayName.get(2), rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL));
			assertTrue(foundation.isDisplayed(CreateSystem.MSG_UNIQUE_SYSTEM_NAME));

			// Verifying the System Name is unique
			String duplicateSystem = foundation.getText(CreateSystem.MSG_UNIQUE_SYSTEM_NAME);
			assertTrue(duplicateSystem.equals(errorMessage.get(1) + systemName.get(2)));

			// Verifying maximum 250 characters
			List<String> maxSystemName = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.TEST_DATA).split(Constants.DELIMITER_TILD));
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, maxSystemName.get(0));
			errorMessageUI = foundation.getText(CreateSystem.LBL_SYSTEM_ERROR);
			assertTrue(!errorMessageUI.equals(errorMessage.get(2)));

			// Verifying greater than maximum 250 characters.
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, maxSystemName.get(1));
			foundation.click(CreateSystem.TXT_DISPLAY_NAME);
			errorMessageUI = foundation.getText(CreateSystem.LBL_SYSTEM_ERROR);
			assertTrue(errorMessageUI.equals(errorMessage.get(2)));

			// Verifying mandatory system name field
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, Keys.TAB);
			errorMessageUI = foundation.getText(CreateSystem.LBL_SYSTEM_ERROR);
			assertTrue(errorMessageUI.equals(errorMessage.get(3)));

			// Verifying invalid display name
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, displayName.get(0));
			foundation.click(CreateSystem.TXT_SYSTEM_NAME);
			String displayErrorMessage = foundation.getText(CreateSystem.LBL_DISPLAY_ERROR);
			assertTrue(displayErrorMessage.equals(errorMessage.get(4)));

			// Verifying alphanumeric characters for Display name
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, displayName.get(1));
			foundation.click(CreateSystem.TXT_SYSTEM_NAME);
			Assert.assertFalse(foundation.isDisplayed(CreateSystem.LBL_DISPLAY_ERROR));

			// Verifying maximum 250 characters
			List<String> maxDisplayName = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.TEST_DATA).split(Constants.DELIMITER_TILD));
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, maxDisplayName.get(0));
			errorMessageUI = foundation.getText(CreateSystem.LBL_DISPLAY_ERROR);
			assertTrue(!errorMessageUI.equals(errorMessage.get(2)));

			// Verifying greater than maximum 250 characters
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, maxDisplayName.get(1));
			foundation.click(CreateSystem.TXT_SYSTEM_NAME);
			errorMessageUI = foundation.getText(CreateSystem.LBL_DISPLAY_ERROR);
			assertTrue(errorMessageUI.equals(errorMessage.get(2)));

			// Verifying mandatory display name field
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, Keys.TAB);
			errorMessageUI = foundation.getText(CreateSystem.LBL_DISPLAY_ERROR);
			assertTrue(errorMessageUI.equals(errorMessage.get(5)));

			// Creating system with duplicate display Name
			newLockerSysytem.createNewSystem(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME), systemName.get(1),
					displayName.get(2), rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL));

			// Verifying the display Name is not unique
			assertTrue(foundation.isDisplayed(
					lockerSystem.objExpandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME))));

			// Navigating to location summary page
			navigationBar.navigateToMenuItem(menuItem.get(1));

			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocationName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA),
					Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_HAS_ORDER_AHEAD,
					rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, 2000);

			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocationName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));

			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			dropDown.selectItem(LocationSummary.DPD_HAS_PICK_UP_LOCATIONS,
					rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA), Constants.TEXT);
			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);

			// verifying the created locker system is displayed as an option for pick up
			// location
			String lockerName = foundation.getText(LocationSummary.LNK_LOCKER_NAME);
			Assert.assertTrue(lockerName.equals(displayName.get(2)));

			// resetting the Data
			navigationBar.navigateToMenuItem(menuItem.get(0));

			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.click(lockerSystem.copyORDeleteSystem(systemName.get(1), Constants.DELETE));
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);

			foundation.click(LockerSystem.BTN_YES_DELETE);
			foundation.waitforElement(LockerSystem.MSG_DELETE_SUCCESS, 2000);

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "C135756 - SOS-22234- Verify the System and Display Name fields in Create a System screen(Copy button) - Super")
	public void verifySuperCopySystemAndDisplayFields() {
		try {
			final String CASE_NUM = "135756";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> systemName = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME).split(Constants.DELIMITER_TILD));
			List<String> displayName = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME).split(Constants.DELIMITER_TILD));
			List<String> errorMessage = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.ERROR_MESSAGE).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(menuItem.get(0));

			// Creating New System to work on copy operation
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			newLockerSysytem.createNewSystem(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME), systemName.get(1),
					displayName.get(1), rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.click(lockerSystem.copyORDeleteSystem(systemName.get(1), Constants.COPY));

			assertTrue(foundation.isDisplayed(CreateSystem.LBL_PAGE_TITLE));

			assertTrue(foundation.isDisplayed(CreateSystem.DPD_LOCATION_SIBLING));

			// Verifying invalid system name
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, systemName.get(0));
			foundation.click(CreateSystem.TXT_DISPLAY_NAME);
			String errorMessageUI = foundation.getText(CreateSystem.LBL_SYSTEM_ERROR);
			assertTrue(errorMessageUI.equals(errorMessage.get(0)));

			// Verifying alphanumeric characters for system name
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, systemName.get(1));
			foundation.click(CreateSystem.TXT_DISPLAY_NAME);
			Assert.assertFalse(foundation.isDisplayed(CreateSystem.LBL_SYSTEM_ERROR));

			// Creating System with duplicate system name
			dropDown.selectItem(CreateSystem.DPD_LOCATION, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME),
					Constants.TEXT);
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, systemName.get(1));
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, displayName.get(2));
			dropDown.selectItem(CreateSystem.DPD_EDIT_LOCKER_MODEL,
					rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL), Constants.TEXT);
			foundation.click(CreateSystem.TXT_TIMER);
			foundation.click(CreateSystem.BTN_SAVE);
			foundation.waitforElement(CreateSystem.MSG_UNIQUE_SYSTEM_NAME, 2000);

			// Verifying the System Name is unique
			assertTrue(foundation.isDisplayed(CreateSystem.MSG_UNIQUE_SYSTEM_NAME));
			String duplicateSystem = foundation.getText(CreateSystem.MSG_UNIQUE_SYSTEM_NAME);
			assertTrue(duplicateSystem.equals(errorMessage.get(1) + systemName.get(1)));

			// Verifying maximum 250 characters.
			List<String> maxSystemName = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.TEST_DATA).split(Constants.DELIMITER_TILD));
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, maxSystemName.get(0));
			errorMessageUI = foundation.getText(CreateSystem.LBL_SYSTEM_ERROR);
			assertTrue(!errorMessageUI.equals(errorMessage.get(2)));

			// Verifying greater than maximum 250 characters.
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, maxSystemName.get(1));
			foundation.click(CreateSystem.TXT_DISPLAY_NAME);
			errorMessageUI = foundation.getText(CreateSystem.LBL_SYSTEM_ERROR);
			assertTrue(errorMessageUI.equals(errorMessage.get(2)));

			// Verifying mandatory system name field
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, Keys.TAB);
			errorMessageUI = foundation.getText(CreateSystem.LBL_SYSTEM_ERROR);
			assertTrue(errorMessageUI.equals(errorMessage.get(3)));

			// Verifying invalid display name
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, displayName.get(0));
			foundation.click(CreateSystem.TXT_SYSTEM_NAME);
			String displayErrorMessage = foundation.getText(CreateSystem.LBL_DISPLAY_ERROR);
			assertTrue(displayErrorMessage.equals(errorMessage.get(4)));

			// Verifying alphanumeric characters for Display name
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, displayName.get(1));
			foundation.click(CreateSystem.TXT_SYSTEM_NAME);
			Assert.assertFalse(foundation.isDisplayed(CreateSystem.LBL_DISPLAY_ERROR));

			// Verifying maximum 250 characters.
			List<String> maxDisplayName = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.TEST_DATA).split(Constants.DELIMITER_TILD));
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, maxDisplayName.get(0));
			errorMessageUI = foundation.getText(CreateSystem.LBL_DISPLAY_ERROR);
			assertTrue(!errorMessageUI.equals(errorMessage.get(2)));

			// Verifying greater than maximum 250 characters.
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, maxDisplayName.get(1));
			foundation.click(CreateSystem.TXT_SYSTEM_NAME);
			errorMessageUI = foundation.getText(CreateSystem.LBL_DISPLAY_ERROR);
			assertTrue(errorMessageUI.equals(errorMessage.get(2)));

			// Verifying mandatory display name field
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, Keys.TAB);
			errorMessageUI = foundation.getText(CreateSystem.LBL_DISPLAY_ERROR);
			assertTrue(errorMessageUI.equals(errorMessage.get(5)));

			// Creating system with duplicate display Name
			dropDown.selectItem(CreateSystem.DPD_LOCATION, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME),
					Constants.TEXT);
			textBox.enterText(CreateSystem.TXT_SYSTEM_NAME, systemName.get(3));
			textBox.enterText(CreateSystem.TXT_DISPLAY_NAME, displayName.get(3));
			dropDown.selectItem(CreateSystem.DPD_EDIT_LOCKER_MODEL,
					rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL), Constants.TEXT);
			foundation.click(CreateSystem.TXT_TIMER);
			foundation.click(CreateSystem.BTN_SAVE);
			foundation.waitforElement(
					lockerSystem.objExpandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME)), 2000);

			// Verifying the display Name is not unique
			assertTrue(foundation.isDisplayed(
					lockerSystem.objExpandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME))));

			// Verifying the display Name is not unique
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.click(lockerSystem.copyORDeleteSystem(systemName.get(1), Constants.DELETE));
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);

			foundation.click(LockerSystem.BTN_YES_DELETE);
			foundation.waitforElement(LockerSystem.MSG_DELETE_SUCCESS, 2000);

			// Navigating to location summary and set Has Order Ahead
			navigationBar.navigateToMenuItem(menuItem.get(1));

			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocationName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA),
					Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_HAS_ORDER_AHEAD,
					rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST, 2000);

			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocationName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));

			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS);
			dropDown.selectItem(LocationSummary.DPD_HAS_PICK_UP_LOCATIONS,
					rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA), Constants.TEXT);
			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);

			// verifying the created locker system is displayed as an option for pick up
			// location
			String lockerName = foundation.getText(LocationSummary.LNK_LOCKER_NAME);
			Assert.assertTrue(lockerName.equals(displayName.get(3)));

			// resetting the Data
			navigationBar.navigateToMenuItem(menuItem.get(0));

			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.click(lockerSystem.copyORDeleteSystem(systemName.get(3), Constants.DELETE));
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);

			foundation.click(LockerSystem.BTN_YES_DELETE);
			foundation.waitforElement(LockerSystem.MSG_DELETE_SUCCESS, 2000);

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "Verify the 'Location' dropdown in Create a system screen for Locker settings disabled in Location Summary - Super")
	public void verifyDisabledLocationSuper() {
		try {
			final String CASE_NUM = "135737";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			// precondition
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			List<String> requiredData = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);

			// navigating to Locker system
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_LOCATION_LOCKER_SYSTEM));
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_CREATE_SYSTEM));

			createLocker.verifyLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA));

			// resetting test data
			foundation.click(LocationList.LINK_HOME_PAGE);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "Verify the 'Location' dropdown in Create a system screen for Locker settings disabled in Location Summary - Operator")
	public void verifyDisabledLocationOperator() {
		try {
			final String CASE_NUM = "135739";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			// precondition
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			List<String> requiredData = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);
			foundation.threadWait(2000);
			login.logout();

			// login as operator
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_LOCATION_LOCKER_SYSTEM));
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_CREATE_SYSTEM));

			createLocker.verifyLocation(rstLocationListData.get(CNLocationList.LOCATION_NAME),
					rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA));
			login.logout();

			// resetting test data
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);
			foundation.threadWait(2000);
			login.logout();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "Verify the 'Location' dropdown in Create a system screen for Locker settings enabled in Location Summary - Super")
	public void verifyEnabledLocationSuper() {
		try {
			final String CASE_NUM = "135738";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);

			List<String> navigationData = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			List<String> locationName = Arrays
					.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));

			List<String> lockerSystem_Required_Data = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// precondition
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName.get(0));
			List<String> requiredData = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);

			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);
			// precondition2

			navigationBar.navigateToMenuItem(navigationData.get(1));
			textBox.enterText(UserList.TXT_FILTER, rstUserRolesData.get(CNUserRoles.ROLE_NAME));
			foundation.click(userList.objRoleName(rstUserRolesData.get(CNUserRoles.ROLE_NAME)));

			foundation.waitforElement(UserList.TXT_SEARCH_LOC, 2000);
			dropDown.selectItem(UserList.TXT_SEARCH_LOC, locationName.get(0), Constants.TEXT);

			foundation.click(UserList.BTN_UPDATE_USER);

			foundation.waitforElement(UserList.TXT_SPINNER_MSG, 2000);
			navigationBar.navigateToMenuItem(navigationData.get(0));
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_LOCATION_LOCKER_SYSTEM));
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_CREATE_SYSTEM));
			// validations
			createLocker.verifyLocation(locationName.get(0), lockerSystem_Required_Data.get(0));

			foundation.refreshPage();

			createLocker.verifyLocation(locationName.get(1), lockerSystem_Required_Data.get(1));

			// resetting test data

			foundation.click(LocationList.LINK_HOME_PAGE);

			locationList.selectLocationName(locationName.get(0));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);
			navigationBar.navigateToMenuItem(navigationData.get(1));
			textBox.enterText(UserList.TXT_FILTER, rstUserRolesData.get(CNUserRoles.ROLE_NAME));
			foundation.click(userList.objRoleName(rstUserRolesData.get(CNUserRoles.ROLE_NAME)));

			foundation.waitforElement(UserList.TXT_SEARCH_LOC, 2000);
			dropDown.selectItem(UserList.TXT_SEARCH_LOC, rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST),
					Constants.TEXT);

			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.threadWait(2000);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "Verify the 'Location' dropdown in Create a system screen for Locker settings enabled in Location Summary - Operator")
	public void verifyEnabledLocationOperator() {
		try {
			final String CASE_NUM = "135736";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			rstUserRolesData = dataBase.getUserRolesData(Queries.USER_ROLES, CASE_NUM);

			List<String> navigationData = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			List<String> locationName = Arrays
					.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));

			List<String> lockerSystem_Required_Data = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// precondition
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName.get(0));
			List<String> requiredData = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);

			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);

			// precondition2
			navigationBar.navigateToMenuItem(navigationData.get(1));
			textBox.enterText(UserList.TXT_FILTER, rstUserRolesData.get(CNUserRoles.ROLE_NAME));
			foundation.click(userList.objRoleName(rstUserRolesData.get(CNUserRoles.ROLE_NAME)));

			foundation.waitforElement(UserList.TXT_SEARCH_LOC, 2000);
			dropDown.selectItem(UserList.TXT_SEARCH_LOC, locationName.get(0), Constants.TEXT);

			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.waitforElement(UserList.TXT_SPINNER_MSG, 2000);
			foundation.threadWait(2000);
			login.logout();

			// login as operator role

			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(navigationData.get(0));
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_LOCATION_LOCKER_SYSTEM));
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			Assert.assertTrue(foundation.isDisplayed(CreateLocker.LBL_CREATE_SYSTEM));
			// validations
			createLocker.verifyLocation(locationName.get(0), lockerSystem_Required_Data.get(0));
			foundation.refreshPage();

			createLocker.verifyLocation(locationName.get(1), lockerSystem_Required_Data.get(1));

			login.logout();

			// resetting test data
			foundation.threadWait(2000);

			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			locationList.selectLocationName(locationName.get(0));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);
			navigationBar.navigateToMenuItem(navigationData.get(1));
			textBox.enterText(UserList.TXT_FILTER, rstUserRolesData.get(CNUserRoles.ROLE_NAME));
			foundation.click(userList.objRoleName(rstUserRolesData.get(CNUserRoles.ROLE_NAME)));

			foundation.waitforElement(UserList.TXT_SEARCH_LOC, 2000);
			dropDown.selectItem(UserList.TXT_SEARCH_LOC, rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST),
					Constants.TEXT);

			foundation.click(UserList.BTN_UPDATE_USER);
			foundation.threadWait(2000);
			login.logout();

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "Verify the 'Location' dropdown in Create a system(Copy button) screen for Locker settings disabled in Location Summary - Super")
	public void verifyCopyIconSuper() {
		try {
			final String CASE_NUM = "135747";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			String locationName = rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME);
			final String systemName = Constants.ACCOUNT_NAME + strings.getRandomCharacter();
			final String displayName = Constants.ACCOUNT_NAME + strings.getRandomCharacter();
			String lockerModel = rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL);

			List<String> locationListName = Arrays
					.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));
			List<String> lockerSystem_Required_Data = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			locationList.selectLocationName(locationListName.get(0));
			List<String> requiredData = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);

			// create new system
			foundation.waitforElement(LockerSystem.BTN_CREATE_SYSTEM, 2000);
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			newLockerSysytem.createNewSystem(locationName, systemName, displayName, lockerModel);

			// precondition
			foundation.click(LocationList.LINK_HOME_PAGE);
			locationList.selectLocationName(locationListName.get(0));

			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);

			// validate copy screen location dropdown
			navigationBar.navigateToMenuItem(menuItem);
			foundation.waitforElement(lockerSystem.objExpandLocationLocker(locationName), 2000);
			foundation.click(lockerSystem.objExpandLocationLocker(locationName));
			foundation.waitforElement(LockerSystem.ICO_SIBLING_COPY, 500);
			assertTrue(foundation.isDisplayed(LockerSystem.ICO_SIBLING_COPY));
			foundation.click(lockerSystem.copyORDeleteSystem(systemName, Constants.COPY));

			createLocker.verifyLocation(locationListName.get(0), lockerSystem_Required_Data.get(0));
			foundation.refreshPage();
			createLocker.verifyLocation(locationListName.get(1), lockerSystem_Required_Data.get(1));

			// resetting test data
			foundation.click(LockerSystem.BTN_CANCEL);
			foundation.waitforElement(lockerSystem.objExpandLocationLocker(locationName), 2000);
			foundation.click(lockerSystem.objExpandLocationLocker(locationName));
			foundation.click(lockerSystem.copyORDeleteSystem(systemName, Constants.DELETE));
			foundation.click(LockerSystem.BTN_YES_DELETE);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "Verify the 'Location' dropdown in Create a system(Copy button) screen for Locker settings disabled in Location Summary - Operator")
	public void verifyCopyIconOperator() {
		try {
			final String CASE_NUM = "135748";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			String menuItem = rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
			String locationName = rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME);
			final String systemName = Constants.ACCOUNT_NAME + strings.getRandomCharacter();
			final String displayName = Constants.ACCOUNT_NAME + strings.getRandomCharacter();
			String lockerModel = rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL);

			List<String> locationListName = Arrays
					.asList(rstLocationListData.get(CNLocationList.LOCATION_NAME).split(Constants.DELIMITER_TILD));
			List<String> lockerSystem_Required_Data = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			locationList.selectLocationName(locationListName.get(0));
			List<String> requiredData = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(0), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);
			login.logout();

			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem);

			// create new system
			foundation.waitforElement(LockerSystem.BTN_CREATE_SYSTEM, 2000);
			foundation.click(LockerSystem.BTN_CREATE_SYSTEM);
			newLockerSysytem.createNewSystem(locationName, systemName, displayName, lockerModel);
			foundation.threadWait(1000);
			login.logout();

			// precondition
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			locationList.selectLocationName(locationListName.get(0));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);
			login.logout();

			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// validate copy screen location dropdown
			navigationBar.navigateToMenuItem(menuItem);
			foundation.waitforElement(lockerSystem.objExpandLocationLocker(locationName), 2000);
			foundation.click(lockerSystem.objExpandLocationLocker(locationName));
			foundation.waitforElement(LockerSystem.ICO_SIBLING_COPY, 500);
			assertTrue(foundation.isDisplayed(LockerSystem.ICO_SIBLING_COPY));
			foundation.click(lockerSystem.copyORDeleteSystem(systemName, Constants.COPY));

			createLocker.verifyLocation(locationListName.get(0), lockerSystem_Required_Data.get(0));
			foundation.refreshPage();
			createLocker.verifyLocation(locationListName.get(1), lockerSystem_Required_Data.get(1));

			// resetting test data
			foundation.click(LockerSystem.BTN_CANCEL);
			foundation.waitforElement(lockerSystem.objExpandLocationLocker(locationName), 2000);
			foundation.click(lockerSystem.objExpandLocationLocker(locationName));
			foundation.click(lockerSystem.copyORDeleteSystem(systemName, Constants.DELETE));
			foundation.click(LockerSystem.BTN_YES_DELETE);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "This test validates the Edit functionality in Locker System page")
	public void verifyEditFunctionality() {
		try {
			final String CASE_NUM = "135715";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			Assert.assertTrue(foundation.isDisplayed(LockerSystem.LBL_PAGE_TITLE));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.waitforElement(LockerSystem.BTN_SCHEDULING, 2);
			foundation.click(lockerSystem.objSystemName(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME)));
			foundation.waitforElement(EditSystem.LBL_PAGE_TITLE, 2);

			String randomText = strings.getRandomCharacter();
			textBox.enterText(EditSystem.TXT_SYSTEM_NAME, "");
			textBox.enterText(EditSystem.TXT_SYSTEM_NAME, randomText);
			textBox.enterText(EditSystem.TXT_DISPLAY_NAME, "");
			textBox.enterText(EditSystem.TXT_DISPLAY_NAME, randomText);
			textBox.enterText(EditSystem.TXT_SHELF_TIMER, "");
			textBox.enterText(EditSystem.TXT_SHELF_TIMER, requiredData.get(1));
			foundation.click(EditSystem.BTN_SAVE);

			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.click(lockerSystem.objSystemName(randomText));
			foundation.waitforElement(EditSystem.LBL_PAGE_TITLE, 2);

			Assert.assertEquals(textBox.getTextFromInput(EditSystem.TXT_SYSTEM_NAME), randomText);
			Assert.assertEquals(textBox.getTextFromInput(EditSystem.TXT_DISPLAY_NAME), randomText);
			Assert.assertEquals(textBox.getTextFromInput(EditSystem.TXT_SHELF_TIMER), requiredData.get(1));

			// reset data
			textBox.enterText(EditSystem.TXT_SYSTEM_NAME, "");
			textBox.enterText(EditSystem.TXT_SYSTEM_NAME, rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME));
			textBox.enterText(EditSystem.TXT_DISPLAY_NAME, "");
			textBox.enterText(EditSystem.TXT_DISPLAY_NAME, rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME));
			textBox.enterText(EditSystem.TXT_SHELF_TIMER, "");
			textBox.enterText(EditSystem.TXT_SHELF_TIMER, requiredData.get(0));
			foundation.click(EditSystem.BTN_SAVE);
			foundation.isDisplayed(LockerSystem.LBL_PAGE_TITLE);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "This test validates the Create System button in Locker System page")
	public void verifyCreateSystemButton() {
		try {
			final String CASE_NUM = "135730";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLockerSystemData = dataBase.getLockerSystemData(Queries.LOCKER_SYSTEM, CASE_NUM);

			String requiredData = rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA);

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(LockerSystem.LBL_PAGE_TITLE, 3);
			foundation.isDisplayed(LockerSystem.BTN_CREATE_SYSTEM);
			Assert.assertTrue(foundation.getBGColor(LockerSystem.BTN_CREATE_SYSTEM).equals(requiredData));

			login.logout();
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(LockerSystem.LBL_PAGE_TITLE, 3);
			foundation.isDisplayed(LockerSystem.BTN_CREATE_SYSTEM);
			Assert.assertTrue(foundation.getBGColor(LockerSystem.BTN_CREATE_SYSTEM).equals(requiredData));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

}
