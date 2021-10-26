package at.smartshop.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.DeviceList;
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.KioskCreate;
import at.smartshop.pages.NavigationBar;

public class Device extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private TextBox textBox=new TextBox();
	private DeviceList deviceList=new DeviceList();
	private Excel excel = new Excel();
	private Table table = new Table();
	private Strings string = new Strings();
	private Numbers numbers = new Numbers();
	private Dropdown dropDown = new Dropdown();
	
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
	
	@Test(description = "145241-QAA-24-Verify for any of the device serial number in device page (Admin>Device) is matching with serial number in device summary page as operator")
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
	
	@Test(description = "145693-QAA-24-Verify for any of the device serial number in device page (Admin>Device) is matching with serial number in device summary page as super")
	public void serialNumberMatchSuperAdmin() {
		try {
			final String CASE_NUM = "145693";
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
	
	@Test(description = "145243-QAA-24-Verify for any device when the serial number is not exist should display n/a in device summary page (Admin>Device) as Operator")
	public void serialNumberNAOperatorAdmin() {
		try {
			final String CASE_NUM = "145243";
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
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,rstDeviceListData.get(CNDeviceList.DEVICE));
			foundation.threadWait(Constants.ONE_SECOND);
			assertEquals(foundation.getText(DeviceList.LIST_SERIAL_NUMBER)," ");			
			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			assertEquals(foundation.getText(DeviceSummary.LBL_SERIAL_NUMBER),serialNumberDeviceList);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145695-QAA-24-Verify for any device when the serial number is not exist should display n/a in device summary page (Admin>Device) as super")
	public void serialNumberNASuperAdmin() {
		try {
			final String CASE_NUM = "145695";
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
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,rstDeviceListData.get(CNDeviceList.DEVICE));
			foundation.threadWait(Constants.ONE_SECOND);
			assertEquals(foundation.getText(DeviceList.LIST_SERIAL_NUMBER)," ");			
			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			assertEquals(foundation.getText(DeviceSummary.LBL_SERIAL_NUMBER),serialNumberDeviceList);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145244-QAA-24-Verify for any device when the serial number is not exist should display n/a in device summary page (Super>Device)")
	public void serialNumberNASuper() {
		try {
			final String CASE_NUM = "145244";
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
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,rstDeviceListData.get(CNDeviceList.DEVICE));
			foundation.threadWait(Constants.ONE_SECOND);
			assertEquals(foundation.getText(DeviceList.LIST_SERIAL_NUMBER)," ");			
			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			assertEquals(foundation.getText(DeviceSummary.LBL_SERIAL_NUMBER),serialNumberDeviceList);
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "145245-QAA-24-Verify the serial number column display in device export file (Admin>Device) as Operator")
	public void serialNumberExportOperatorAdmin() {
		try {
			final String CASE_NUM = "145245";
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
			foundation.click(DeviceList.BTN_EXPORT_DEVICE);
			
			// download assertion
			String[] uiData = (foundation.getText(DeviceList.TXT_RECORD_COUNT)).split(" ");
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_DEVICE_EXPORT_SRC));
			foundation.copyFile(FilePath.EXCEL_DEVICE_EXPORT_SRC, FilePath.EXCEL_DEVICE_EXPORT_TAR);
			int excelCount = excel.getExcelRowCount(FilePath.EXCEL_DEVICE_EXPORT_TAR);
			// record count validation
			Assert.assertEquals(String.valueOf(excelCount), uiData[0]);
			Map<String, String> uidata = table.getTblSingleRowRecordUI(DeviceList.TBL_GRID, DeviceList.TBL_ROW);
			List<String> uiList = new ArrayList<String>(uidata.values());
			uiList.removeAll(Arrays.asList("", null));
			// excel data validation
			Assert.assertTrue(excel.verifyExcelData(uiList, FilePath.EXCEL_DEVICE_EXPORT_TAR, 1));
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_SRC);
			foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_TAR);
		}
	}
	
	@Test(description = "145696-QAA-24-Verify the serial number column display in device export file (Admin>Device) as super")
	public void serialNumberExportSuperAdmin() {
		try {
			final String CASE_NUM = "145696";
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
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,serialNumberDeviceList);
			foundation.threadWait(Constants.ONE_SECOND);
			assertEquals(foundation.getText(DeviceList.LIST_SERIAL_NUMBER),serialNumberDeviceList);			
			foundation.click(DeviceList.BTN_EXPORT_DEVICE);
			
			// download assertion
			String[] uiData = (foundation.getText(DeviceList.TXT_RECORD_COUNT)).split(" ");
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_DEVICE_EXPORT_SRC));
			foundation.copyFile(FilePath.EXCEL_DEVICE_EXPORT_SRC, FilePath.EXCEL_DEVICE_EXPORT_TAR);
			int excelCount = excel.getExcelRowCount(FilePath.EXCEL_DEVICE_EXPORT_TAR);
			// record count validation
			Assert.assertEquals(String.valueOf(excelCount), uiData[0]);
			Map<String, String> uidata = table.getTblSingleRowRecordUI(DeviceList.TBL_GRID, DeviceList.TBL_ROW);
			List<String> uiList = new ArrayList<String>(uidata.values());
			uiList.removeAll(Arrays.asList("", null));
			// excel data validation
			Assert.assertTrue(excel.verifyExcelData(uiList, FilePath.EXCEL_DEVICE_EXPORT_TAR, 1));
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_SRC);
			foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_TAR);
		}
	}
	
	@Test(description = "145697-QAA-24-Verify the serial number column display in device export file (Super>Device)")
	public void serialNumberExportSuper() {
		try {
			final String CASE_NUM = "145697";
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
			foundation.click(DeviceList.BTN_EXPORT_DEVICE);
			
			// download assertion
			String[] uiData = (foundation.getText(DeviceList.TXT_RECORD_COUNT)).split(" ");
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_DEVICE_EXPORT_SRC));
			foundation.copyFile(FilePath.EXCEL_DEVICE_EXPORT_SRC, FilePath.EXCEL_DEVICE_EXPORT_TAR);
			int excelCount = excel.getExcelRowCount(FilePath.EXCEL_DEVICE_EXPORT_TAR);
			// record count validation
			Assert.assertEquals(String.valueOf(excelCount), uiData[0]);
			Map<String, String> uidata = table.getTblSingleRowRecordUI(DeviceList.TBL_GRID, DeviceList.TBL_ROW);
			List<String> uiList = new ArrayList<String>(uidata.values());
			uiList.removeAll(Arrays.asList("", null," "));
			// excel data validation
			Assert.assertTrue(excel.verifyExcelData(uiList, FilePath.EXCEL_DEVICE_EXPORT_TAR, 1));
			
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_SRC);
			foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_TAR);
		}
	}
	
	@Test(description = "164078-QAA-12-Validate Demo option is displayed in Cooler dropdown when logged in as Super User")
	public void demoOptionSuperUser() {
		try {
			final String CASE_NUM = "164078";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, rstDeviceListData.get(CNDeviceList.DEVICE));
			foundation.waitforElement(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)),
					Constants.MEDIUM_TIME);

			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.MEDIUM_TIME);

			//Verifying Cooler Type is Present 
			List<String> coolerType = foundation.getTextofListElement(DeviceSummary.DPD_COOLER_TYPE);
			assertTrue(coolerType.toString().contains(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "164079-QAA-12-Validate Demo option is displayed in Cooler dropdown when logged in as Operator user")
	public void demoOptionOperatorUser() {
		try {
			final String CASE_NUM = "164079";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, rstDeviceListData.get(CNDeviceList.DEVICE));
			foundation.waitforElement(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)),
					Constants.MEDIUM_TIME);

			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.MEDIUM_TIME);

			//Verifying Cooler Type is not Present 
			List<String> coolerType = foundation.getTextofListElement(DeviceSummary.DPD_COOLER_TYPE);
			assertTrue(!coolerType.toString().contains(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "164080-QAA-12-Validate Demo option is displayed in Cooler dropdown when logged in as Driver user")
	public void demoOptionDriverUser() {
		try {
			final String CASE_NUM = "164080";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.DRIVER_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, rstDeviceListData.get(CNDeviceList.DEVICE));
			foundation.waitforElement(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)),
					Constants.MEDIUM_TIME);

			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.MEDIUM_TIME);

			//Verifying Cooler Type is not Present 
			List<String> coolerType = foundation.getTextofListElement(DeviceSummary.DPD_COOLER_TYPE);
			assertTrue(!coolerType.toString().contains(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "164081-QAA-12-Validate Demo option is displayed in Cooler dropdown when logged in as Super user")
	public void demoOptionCoolerDropdownSuperUser() {
		try {
			final String CASE_NUM = "164081";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			List<String> dbData = Arrays
					.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			// navigate to admin>device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(KioskCreate.BTN_CREATE, Constants.SHORT_TIME);

			// navigate to create new kiosk Device
			foundation.click(KioskCreate.BTN_CREATE);
			textBox.enterText(KioskCreate.TXT_NAME, device);
			dropDown.selectItem(KioskCreate.DPD_ORG, dbData.get(0), Constants.TEXT);
			dropDown.selectItem(KioskCreate.DPD_PROCESSOR, dbData.get(1), Constants.TEXT);
			textBox.enterText(KioskCreate.TXT_TERMINAL_ID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			foundation.waitforElement(KioskCreate.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(KioskCreate.BTN_SAVE);
			foundation.waitforElement(KioskCreate.TXT_DEVICE_LIST, Constants.SHORT_TIME);
			foundation.refreshPage();
			
			// searching for newly created kiosk Device
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, device);
			foundation.click(deviceList.objDeveiceLink(device));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.MEDIUM_TIME);
			
			// selecting Hardware type as PicoMarket
			dropDown.selectItem(KioskCreate.DPD_HARDWARE_TYPE, dbData.get(2), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			
			// Verifying Cooler Type is Present
			List<String> coolerType = foundation.getTextofListElement(DeviceSummary.DPD_COOLER_TYPE);
			assertTrue(coolerType.toString().contains(dbData.get(3)));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
