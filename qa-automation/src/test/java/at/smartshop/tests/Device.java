package at.smartshop.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.DeviceList;
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.NavigationBar;

public class Device extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private TextBox textBox=new TextBox();
	private DeviceList deviceList=new DeviceList();
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstDeviceListData;

	@Test(description = "145232-QAA-24-Verify serial number section display on Admin>Device page as operator")
	public void serialNumberDisplayOperatorAdmin() {
		try {
			final String CASE_NUM = "145232";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number section display
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			assertTrue(foundation.isDisplayed(DeviceList.COLUMN_SERIAL_NUMBER));
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145233-QAA-24-Verify serial number section display on Admin>Device page as super")
	public void serialNumberDisplaySuperAdmin() {
		try {
			final String CASE_NUM = "145233";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number section display
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			assertTrue(foundation.isDisplayed(DeviceList.COLUMN_SERIAL_NUMBER));
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145234-QAA-24-Verify serial number section display on Super>Device page")
	public void serialNumberDisplaySuper() {
		try {
			final String CASE_NUM = "145234";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number section display
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			assertTrue(foundation.isDisplayed(DeviceList.COLUMN_SERIAL_NUMBER));
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145235-QAA-24-Verify the sort functionality of serial number column on Admin>Device page as operator")
	public void serialNumberSortOperatorAdmin() {
		try {
			final String CASE_NUM = "145235";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number sort functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
			foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER, Constants.ASCENDING);
			foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
			foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER, Constants.DESCENDING);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145238-QAA-24-Verify the search based on serial number field on Admin>Device page as operator")
	public void serialNumberSortSuperAdmin() {
		try {
			final String CASE_NUM = "145238";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number sort functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
			foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER, Constants.ASCENDING);
			foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
			foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER, Constants.DESCENDING);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145239-QAA-24-Verify the sort functionality of serial number column on Super>Device page")
	public void serialNumberSortSuper() {
		try {
			final String CASE_NUM = "145239";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number sort functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
			foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER, Constants.ASCENDING);
			foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
			foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER, Constants.DESCENDING);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145238-QAA-24-Verify the search based on serial number field on Admin>Device page as operator")
	public void serialNumberSearchOperatorAdmin() {
		try {
			final String CASE_NUM = "145238";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER));
			foundation.threadWait(Constants.ONE_SECOND);
			assertEquals(foundation.getSizeofListElement(DeviceList.LIST_SERIAL_NUMBER), 1);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145239-QAA-24-Verify the search based on serial number field on Admin>Device page as super")
	public void serialNumberSearchSuperAdmin() {
		try {
			final String CASE_NUM = "145239";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER));
			foundation.threadWait(Constants.ONE_SECOND);
			assertEquals(foundation.getSizeofListElement(DeviceList.LIST_SERIAL_NUMBER), 1);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145240-QAA-24-Verify the search based on serial number field on Super>Device page")
	public void serialNumberSearchSuper() {
		try {
			final String CASE_NUM = "145240";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER));
			foundation.threadWait(Constants.ONE_SECOND);
			assertEquals(foundation.getSizeofListElement(DeviceList.LIST_SERIAL_NUMBER), 1);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145241-QAA-24-Verify for any of the device serial number in device page (Admin>Device) is matching with serial number in device summary page")
	public void serialNumberMatchOperatorAdmin() {
		try {
			final String CASE_NUM = "145241";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			String serialNumberDeviceList=rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,serialNumberDeviceList);
			foundation.threadWait(Constants.ONE_SECOND);
			assertEquals(foundation.getText(DeviceList.LIST_SERIAL_NUMBER),serialNumberDeviceList);			
			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			assertEquals(foundation.getText(DeviceSummary.LBL_SERIAL_NUMBER),serialNumberDeviceList);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145242-QAA-24-Verify for any of the device serial number in device page (Super>Device) is matching with serial number in device summary page")
	public void serialNumberMatchSuper() {
		try {
			final String CASE_NUM = "145242";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			String serialNumberDeviceList=rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);
			
			//verify daily revenue			
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
							propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER));
			foundation.threadWait(Constants.ONE_SECOND);
			assertEquals(foundation.getText(DeviceList.LIST_SERIAL_NUMBER),serialNumberDeviceList);			
			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			assertEquals(foundation.getText(DeviceSummary.LBL_SERIAL_NUMBER),serialNumberDeviceList);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
