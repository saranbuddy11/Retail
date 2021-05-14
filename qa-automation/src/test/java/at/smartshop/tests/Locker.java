package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNLockerSystem;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Configuration;
import at.smartshop.pages.CreateLocker;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.LockerSystem;
import at.smartshop.pages.NavigationBar;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Locker extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	private Foundation foundation = new Foundation();
	private LocationList locationList = new LocationList();
	private LockerSystem lockerSystem = new LockerSystem();
	
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
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(LocationList.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocaionName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_HAS_ORDER_AHEAD, rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST,2000);
			
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			
			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationList.selectLocaionName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS); 
			dropDown.selectItem(LocationSummary.DPD_HAS_PICK_UP_LOCATIONS, rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			
			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);
			foundation.isDisplayed(LocationSummary.LBL_LOCKER_PICK_UP_TITLE,"Text in Italic");
			
			List<String> Name = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.NAME).split(Constants.DELIMITER_TILD));
			
			String lockerName=foundation.getText(LocationSummary.LNK_LOCKER_NAME);
			Assert.assertTrue(lockerName.equals(Name.get(0)));
			
			String shelfLife=foundation.getText(LocationSummary.LBL_SHELF_LIFE);
			Assert.assertTrue(shelfLife.equals(Name.get(1)));
			
			foundation.click(LocationSummary.LNK_LOCKER_NAME);
			String systemName=foundation.getTextAttribute(LocationSummary.TXT_SYSTEM_NAME);
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
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocaionName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_HAS_ORDER_AHEAD, rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST,2000);
			
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			
			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocaionName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS); 
						
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS); 
			dropDown.selectItem(LocationSummary.DPD_HAS_PICK_UP_LOCATIONS, rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
						
			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);
			foundation.isDisplayed(LocationSummary.LBL_LOCKER_PICK_UP_TITLE,"Text in Italic");
			
			List<String> Name = Arrays.asList(rstLocationSummaryData.get(CNLocationSummary.NAME).split(Constants.DELIMITER_TILD));
			
			String lockerName=foundation.getText(LocationSummary.LNK_LOCKER_NAME);
			Assert.assertTrue(lockerName.equals(Name.get(0)));
			
			foundation.click(LocationSummary.LNK_LOCKER_NAME);
			foundation.waitforElement(LocationSummary.TXT_SYSTEM_NAME,2000);
			
			dropDown.selectItem(CreateLocker.DPD_LOCATION, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME), Constants.TEXT);
			textBox.enterText(CreateLocker.TXT_SYSTEM_NAME,rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME));
			textBox.enterText(CreateLocker.TXT_DISPLAY_NAME,rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME));
			dropDown.selectItem(CreateLocker.DPD_LOCKER_MODEL, rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL), Constants.TEXT);
			foundation.click(CreateLocker.BTN_SAVE);
			foundation.waitforElement(LockerSystem.BTN_CREATE_SYSTEM, 2000);
			
			navigationBar.navigateToMenuItem(menuItem.get(0));
			// Searching for Product
			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocaionName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
									
			foundation.click(LocationSummary.BTN_LOCATION_SETTINGS); 
			dropDown.selectItem(LocationSummary.DPD_HAS_PICK_UP_LOCATIONS, rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
									
			foundation.click(LocationSummary.LNK_PICK_UP_LOCATION);
			Assert.assertTrue(lockerName.equals(Name.get(0)));
			
			navigationBar.navigateToMenuItem(menuItem.get(1));
			
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME)); 
			lockerSystem.copyDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),Constants.DELETE); 
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
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			lockerSystem.expandLocationLocker(rstLocationListData.get(CNLocationList.LOCATION_NAME)); 
			foundation.isDisplayed(LockerSystem.ICO_SIBLING_DELETE, "Next to Copy icon Delete button");
			
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			
			lockerSystem.expandLocationLocker(rstLocationListData.get(CNLocationList.LOCATION_NAME)); 
			foundation.isDisplayed(LockerSystem.ICO_SIBLING_DELETE, "Next to Copy icon Delete button");
			
			
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
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME)); 
	
			lockerSystem.copyDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),Constants.DELETE); 
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);
			
			List<String> popUpMessage = Arrays.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_YES_DELETE, "Delete button"));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_CANCEL, "Cancel button"));
			
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_TITLE).equals(popUpMessage.get(0)));
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_MESSAGE).equals(popUpMessage.get(1)));
			
			foundation.click(LockerSystem.BTN_CANCEL);

			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME)); 
			
			lockerSystem.copyDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),Constants.DELETE); 
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);
			
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_YES_DELETE, "Delete button"));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_CANCEL, "Cancel button"));
			
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
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME)); 
	
			lockerSystem.copyDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),Constants.DELETE); 
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);
			
			List<String> popUpMessage = Arrays.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_YES_DELETE, "Delete button"));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_CANCEL, "Cancel button"));
			
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_TITLE).equals(popUpMessage.get(0)));
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_MESSAGE).equals(popUpMessage.get(1)));
			
			foundation.click(LockerSystem.BTN_CANCEL);
			
			Assert.assertTrue(foundation.isDisplayed(lockerSystem.objSchedule(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME)),"locker system"));

			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME)); 
			
			lockerSystem.copyDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),Constants.DELETE); 
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);
			
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_YES_DELETE, "Delete button"));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_CANCEL, "Cancel button"));
			
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_TITLE).equals(popUpMessage.get(0)));
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_MESSAGE).equals(popUpMessage.get(1)));
			
			foundation.click(LockerSystem.BTN_CANCEL);
			
			Assert.assertTrue(foundation.isDisplayed(lockerSystem.objSchedule(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME)),"locker system"));
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
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
			
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(0));
	
			textBox.enterText(LocationList.TXT_FILTER, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			locationList.selectLocaionName(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			dropDown.selectItem(LocationSummary.DPD_HAS_LOCKER, rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_HAS_ORDER_AHEAD, rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.DPD_LOCATION_LIST,2000);
			
			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
			
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CreateLocker.BTN_CREATE_SYSTEM);
			
			dropDown.selectItem(CreateLocker.DPD_LOCATION, rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME), Constants.TEXT);
			textBox.enterText(CreateLocker.TXT_SYSTEM_NAME,rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME));
			textBox.enterText(CreateLocker.TXT_DISPLAY_NAME,rstLockerSystemData.get(CNLockerSystem.DISPLAY_NAME));
			dropDown.selectItem(CreateLocker.DPD_LOCKER_MODEL, rstLockerSystemData.get(CNLockerSystem.LOCKER_MODEL), Constants.TEXT);
			foundation.click(CreateLocker.BTN_SAVE);
			foundation.waitforElement(LockerSystem.BTN_CREATE_SYSTEM, 2000);
			
			lockerSystem.expandLocationLocker(rstLockerSystemData.get(CNLockerSystem.LOCATION_NAME));
			foundation.isDisplayed(LockerSystem.ICO_SIBLING_DELETE, "Delete icon");
			
			lockerSystem.copyDeleteSystem(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME),Constants.DELETE); 
			foundation.waitforElement(LockerSystem.BTN_YES_DELETE, 2000);
			
			List<String> popUpMessage = Arrays.asList(rstLockerSystemData.get(CNLockerSystem.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_YES_DELETE, "Delete button"));
			Assert.assertTrue(foundation.isDisplayed(LockerSystem.BTN_CANCEL, "Cancel button"));
			
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_TITLE).equals(popUpMessage.get(0)));
			Assert.assertTrue(foundation.getText(LockerSystem.POP_UP_MESSAGE).equals(popUpMessage.get(1)));
			
			foundation.click(LockerSystem.BTN_YES_DELETE);
			foundation.waitforElement(LockerSystem.MSG_DELETE_SUCCESS, 2000);
			
			Assert.assertFalse(foundation.isDisplayed(lockerSystem.objSchedule(rstLockerSystemData.get(CNLockerSystem.SYSTEM_NAME)),"locker system is not"));
			
		} catch (Exception exc) {
			Assert.fail();
		}

	}
	
}
