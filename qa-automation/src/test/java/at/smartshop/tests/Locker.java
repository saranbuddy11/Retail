package at.smartshop.tests;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNLockerSystem;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Configuration;
import at.smartshop.pages.CreateLocker;
import at.smartshop.pages.CreateSystem;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.LockerSystem;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.LockerEquipment;

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

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstLockerSystemData;

	@Test(description = "C135549 - This test validates 'Locker Systems' pickup location type under the Order Ahead Settings")
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

	@Test(description = "This test validates the Has locker with Default option in the Location Summary page")
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

	@Test(description = "This test validates the Has locker with Yes option in the Location Summary page")
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

	@Test(description = "This test validates the Scheduling button in the Locker system page")
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

	@Test(description = "This test validates the functionality of Scheduling button in Locker system page")
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

	@Test(description = "This test validates the Scheduling button in Locker system page when no locker created")
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

	@Test(description = "Validate the Locker Equipment screen (layout when a locker model is tapped).")
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
			Map<String, String> uiTableHeader = table
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

	@Test(description = "Validate the Pre Programed in grid 'Locker Model' Functionality")
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
			Map<String, String> uiData_locker_20 = table.getTblRecordsUI(LockerEquipment.TBL_LOCKER_EQUIPMENT_HEADER,
					LockerEquipment.TBL_ROW_1);// table.getTblRecordsUI();

			assertEquals(uiData_locker_20, dbData_Locker_20);

			Map<String, String> uiData_locker_18 = table.getTblRecordsUI(LockerEquipment.TBL_LOCKER_EQUIPMENT_HEADER,
					LockerEquipment.TBL_ROW_2);
			assertEquals(uiData_locker_18, dbData_Locker_18);

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "Validate the Locker Equipment screen (layout when a no locker model is tapped).")
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

			Map<String, String> uiTableHeader = table
					.getLockerEquipmentTblHeadersUI(LockerEquipment.TBL_LOCKER_EQUIPMENT_HEADER);

			assertEquals(uiTableHeader, dbData);

			// Locker model validation
			Assert.assertFalse(foundation.isDisplayed(LockerEquipment.TXT_MODEL_NAME));

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "Validate Locker Model > 18 Door Master from the grid and views")
	public void validateLockerModel18Door() {
		try {
			final String CASE_NUM = "135547";
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

	@Test(description = "Validate Locker Model > 20 Door Satellite from the grid and views")
	public void validateLockerModel20Door() {
		try {
			final String CASE_NUM = "135546";

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

	@Test(description = "Verify the functionality of 'Copy' icon in the 'Location Locker system dashboard' page")
	public void functaionalityOfCopyIcon() {
		try {
			final String CASE_NUM = "120327";

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
			Thread.sleep(2000);
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

	@Test(description = "Verify the 'Copy' icon in the 'Location Locker system dashboard' page")
	public void verifyCopyIcon() {
		try {
			final String CASE_NUM = "120326";

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
			Thread.sleep(2000);
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

	@Test(description = "Verify the 'Save' button when 'Display' field is blank")
	public void verifySaveWithDisplayBlank() {
		try {
			final String CASE_NUM = "120328";

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
			Thread.sleep(2000);
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

	@Test(description = "Verify the 'Save' button when 'System' field is blank")
	public void verifySaveWithSystemBlank() {
		try {
			final String CASE_NUM = "120329";

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
			Thread.sleep(2000);
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
}
