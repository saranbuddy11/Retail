package at.smartshop.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.generic.CustomisedAssert;
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
import at.smartshop.pages.AssignDeviceToOrg;
import at.smartshop.pages.Commission;
import at.smartshop.pages.DeviceDashboard;
import at.smartshop.pages.DeviceList;
import at.smartshop.pages.DeviceSummary;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.KioskCreate;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PickList;

public class Device extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private DeviceList deviceList = new DeviceList();
	private Excel excel = new Excel();
	private Table table = new Table();
	private Strings string = new Strings();
	private Numbers numbers = new Numbers();
	private Dropdown dropDown = new Dropdown();
	private LocationList locationList = new LocationList();
	private LocationSummary locationSummary = new LocationSummary();
	private DeviceSummary deviceSummary = new DeviceSummary();
	private DeviceDashboard deviceDashboard=new DeviceDashboard();
	
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstDeviceListData;

	@Test(description = "145232-QAA-24-Verify serial number section display on Admin>Device page as operator")
	public void serialNumberDisplayOperatorAdmin() {
		try {
			final String CASE_NUM = "145232";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device and verify serial number section display
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.COLUMN_SERIAL_NUMBER));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Commenting Testcase as per Epic SOS - 25963, Serial Number Column is not
	// displaying now

	/*
	 * @Test(description =
	 * "145233-QAA-24-Verify serial number section display on Admin>Device page as super"
	 * ) public void serialNumberDisplaySuperAdmin() { try { final String CASE_NUM =
	 * "145233"; // Reading test data from DataBase rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * 
	 * // verify daily revenue browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // navigate to admin>device and verify serial number section display
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM)); CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.
	 * COLUMN_SERIAL_NUMBER));
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); } }
	 */

	@Test(description = "145234-QAA-24-Verify serial number section display on Super>Device page")
	public void serialNumberDisplaySuper() {
		try {
			final String CASE_NUM = "145234";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device and verify serial number section display
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.COLUMN_SERIAL_NUMBER));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145235-QAA-24-Verify the sort functionality of serial number column on Admin>Device page as operator")
	public void serialNumberSortOperatorAdmin() {
		try {
			final String CASE_NUM = "145235";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device and verify serial number sort functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			// foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
			// foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER,
			// Constants.ASCENDING);
			// foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
			// foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER,
			// Constants.DESCENDING);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Commenting testcase as per Epic SOS - 25963, Serial Number Column is not
	// displaying now

	/*
	 * @Test(description =
	 * "145238-QAA-24-Verify the search based on serial number field on Admin>Device page as operator"
	 * ) public void serialNumberSortSuperAdmin() { try { final String CASE_NUM =
	 * "145238"; // Reading test data from DataBase rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * 
	 * // verify daily revenue browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // navigate to admin>device and verify serial number sort functionality
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM)); foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
	 * foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER,
	 * Constants.ASCENDING); foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
	 * foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER,
	 * Constants.DESCENDING);
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); } }
	 */

	@Test(description = "145239-QAA-24-Verify the sort functionality of serial number column on Admin>Device page")
	public void serialNumberSortSuper() {
		try {
			final String CASE_NUM = "145239";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device and verify serial number sort functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER, Constants.ASCENDING);
			foundation.click(DeviceList.COLUMN_SERIAL_NUMBER);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.verifySortText(DeviceList.LIST_SERIAL_NUMBER, Constants.DESCENDING);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Commenting as per Epic SOS - 25963, Serial Number Column is not displaying
	// now

	/*
	 * @Test(description =
	 * "145238-QAA-24-Verify the search based on serial number field on Admin>Device page as operator"
	 * ) public void serialNumberSearchOperatorAdmin() { try { final String CASE_NUM
	 * = "145238"; // Reading test data from DataBase rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST,
	 * CASE_NUM);
	 * 
	 * // verify daily revenue browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // navigate to admin>device and verify serial number filter functionality
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM)); textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,
	 * rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER));
	 * foundation.threadWait(Constants.ONE_SECOND);
	 * CustomisedAssert.assertEquals(foundation.getSizeofListElement(DeviceList.
	 * LIST_SERIAL_NUMBER), 1);
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); } }
	 * 
	 * @Test(description =
	 * "145239-QAA-24-Verify the search based on serial number field on Admin>Device page as super"
	 * ) public void serialNumberSearchSuperAdmin() { try { final String CASE_NUM =
	 * "145239"; // Reading test data from DataBase rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST,
	 * CASE_NUM);
	 * 
	 * // verify daily revenue browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // navigate to admin>device and verify serial number filter functionality
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM)); textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,
	 * rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER));
	 * foundation.threadWait(Constants.ONE_SECOND);
	 * CustomisedAssert.assertEquals(foundation.getSizeofListElement(DeviceList.
	 * LIST_SERIAL_NUMBER), 1);
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); } }
	 */

	@Test(description = "145240-QAA-24-Verify the search based on serial number field on Super>Device page")
	public void serialNumberSearchSuper() {
		try {
			final String CASE_NUM = "145240";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER));
			foundation.click(DeviceList.BTN_SUBMIT);
			foundation.threadWait(Constants.ONE_SECOND);
			CustomisedAssert.assertEquals(foundation.getSizeofListElement(DeviceList.LIST_SERIAL_NUMBER), 1);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Commenting as per Epic SOS - 25963, Serial Number Column is not displaying
	// now

	/*
	 * @Test(description =
	 * "145241-QAA-24-Verify for any of the device serial number in device page (Admin>Device) is matching with serial number in device summary page as operator"
	 * ) public void serialNumberMatchOperatorAdmin() { try { final String CASE_NUM
	 * = "145241"; // Reading test data from DataBase rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST,
	 * CASE_NUM); String serialNumberDeviceList =
	 * rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);
	 * 
	 * // verify daily revenue browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // navigate to admin>device and verify serial number filter functionality
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM)); textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,
	 * serialNumberDeviceList); foundation.threadWait(Constants.ONE_SECOND);
	 * CustomisedAssert.assertEquals(foundation.getText(DeviceList.
	 * LIST_SERIAL_NUMBER), serialNumberDeviceList);
	 * foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList
	 * .DEVICE))); CustomisedAssert.assertEquals(foundation.getText(DeviceSummary.
	 * LBL_SERIAL_NUMBER), serialNumberDeviceList);
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); } }
	 * 
	 * @Test(description =
	 * "145693-QAA-24-Verify for any of the device serial number in device page (Admin>Device) is matching with serial number in device summary page as super"
	 * ) public void serialNumberMatchSuperAdmin() { try { final String CASE_NUM =
	 * "145693"; // Reading test data from DataBase rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST,
	 * CASE_NUM); String serialNumberDeviceList =
	 * rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);
	 * 
	 * // verify daily revenue browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // navigate to admin>device and verify serial number filter functionality
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM)); textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,
	 * serialNumberDeviceList); foundation.threadWait(Constants.ONE_SECOND);
	 * CustomisedAssert.assertEquals(foundation.getText(DeviceList.
	 * LIST_SERIAL_NUMBER), serialNumberDeviceList);
	 * foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList
	 * .DEVICE))); CustomisedAssert.assertEquals(foundation.getText(DeviceSummary.
	 * LBL_SERIAL_NUMBER), serialNumberDeviceList);
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); } }
	 */

	@Test(description = "145242-QAA-24-Verify for any of the device serial number in device page (Super>Device) is matching with serial number in device summary page")
	public void serialNumberMatchSuper() {
		try {
			final String CASE_NUM = "145242";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			String serialNumberDeviceList = rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER));
			foundation.click(DeviceList.BTN_SUBMIT);
			foundation.threadWait(Constants.ONE_SECOND);
			CustomisedAssert.assertEquals(foundation.getText(DeviceList.LIST_SERIAL_NUMBER), serialNumberDeviceList);
			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			CustomisedAssert.assertEquals(foundation.getText(DeviceSummary.LBL_SERIAL_NUMBER), serialNumberDeviceList);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Commenting as per Epic SOS - 25963, Serial Number Column is not displaying
	// now

	/*
	 * @Test(description =
	 * "145243-QAA-24-Verify for any device when the serial number is not exist should display n/a in device summary page (Admin>Device) as Operator"
	 * ) public void serialNumberNAOperatorAdmin() { try { final String CASE_NUM =
	 * "145243"; // Reading test data from DataBase rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST,
	 * CASE_NUM); String serialNumberDeviceList =
	 * rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);
	 * 
	 * // verify daily revenue browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // navigate to admin>device and verify serial number filter functionality
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM)); textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,
	 * rstDeviceListData.get(CNDeviceList.DEVICE));
	 * foundation.threadWait(Constants.ONE_SECOND);
	 * CustomisedAssert.assertEquals(foundation.getText(DeviceList.
	 * LIST_SERIAL_NUMBER), " ");
	 * foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList
	 * .DEVICE))); CustomisedAssert.assertEquals(foundation.getText(DeviceSummary.
	 * LBL_SERIAL_NUMBER), serialNumberDeviceList);
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); } }
	 * 
	 * @Test(description =
	 * "145695-QAA-24-Verify for any device when the serial number is not exist should display n/a in device summary page (Admin>Device) as super"
	 * ) public void serialNumberNASuperAdmin() { try { final String CASE_NUM =
	 * "145695"; // Reading test data from DataBase rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST,
	 * CASE_NUM); String serialNumberDeviceList =
	 * rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);
	 * 
	 * // verify daily revenue browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // navigate to admin>device and verify serial number filter functionality
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM)); textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,
	 * rstDeviceListData.get(CNDeviceList.DEVICE));
	 * foundation.threadWait(Constants.ONE_SECOND);
	 * CustomisedAssert.assertEquals(foundation.getText(DeviceList.
	 * LIST_SERIAL_NUMBER), " ");
	 * foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList
	 * .DEVICE))); CustomisedAssert.assertEquals(foundation.getText(DeviceSummary.
	 * LBL_SERIAL_NUMBER), serialNumberDeviceList);
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); } }
	 */

	@Test(description = "145244-QAA-24-Verify for any device when the serial number is not exist should display n/a in device summary page (Super>Device)")
	public void serialNumberNASuper() {
		try {
			final String CASE_NUM = "145244";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			String serialNumberDeviceList = rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, rstDeviceListData.get(CNDeviceList.DEVICE));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(DeviceList.BTN_SUBMIT);
			CustomisedAssert.assertEquals(foundation.getText(DeviceList.LIST_SERIAL_NUMBER), " ");
			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			CustomisedAssert.assertEquals(foundation.getText(DeviceSummary.LBL_SERIAL_NUMBER), serialNumberDeviceList);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	// Commenting testcase as per Epic SOS - 25963, Serial Number Column is not
	// displaying now

	/*
	 * @Test(description =
	 * "145245-QAA-24-Verify the serial number column display in device export file (Admin>Device) as Operator"
	 * ) public void serialNumberExportOperatorAdmin() { try { final String CASE_NUM
	 * = "145245"; // Reading test data from DataBase rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST,
	 * CASE_NUM); String serialNumberDeviceList =
	 * rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);
	 * 
	 * // verify daily revenue browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // navigate to admin>device and verify serial number filter functionality
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM)); textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,
	 * serialNumberDeviceList); foundation.threadWait(Constants.ONE_SECOND);
	 * CustomisedAssert.assertEquals(foundation.getText(DeviceList.
	 * LIST_SERIAL_NUMBER), serialNumberDeviceList);
	 * foundation.click(DeviceList.BTN_EXPORT_DEVICE);
	 * 
	 * // download assertion String[] uiData =
	 * (foundation.getText(DeviceList.TXT_RECORD_COUNT)).split(" ");
	 * CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.
	 * EXCEL_DEVICE_EXPORT_SRC));
	 * foundation.copyFile(FilePath.EXCEL_DEVICE_EXPORT_SRC,
	 * FilePath.EXCEL_DEVICE_EXPORT_TAR); int excelCount =
	 * excel.getExcelRowCount(FilePath.EXCEL_DEVICE_EXPORT_TAR); // record count
	 * validation CustomisedAssert.assertEquals(String.valueOf(excelCount),
	 * uiData[0]); Map<String, String> uidata =
	 * table.getTblSingleRowRecordUI(DeviceList.TBL_GRID, DeviceList.TBL_ROW);
	 * List<String> uiList = new ArrayList<String>(uidata.values());
	 * uiList.removeAll(Arrays.asList("", null)); // excel data validation
	 * CustomisedAssert.assertTrue(excel.verifyExcelData(uiList,
	 * FilePath.EXCEL_DEVICE_EXPORT_TAR, 1)); } catch (Exception exc) {
	 * TestInfra.failWithScreenShot(exc.toString()); } finally { // delete files
	 * foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_SRC);
	 * foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_TAR); } }
	 * 
	 * @Test(description =
	 * "145696-QAA-24-Verify the serial number column display in device export file (Admin>Device) as super"
	 * ) public void serialNumberExportSuperAdmin() { try { final String CASE_NUM =
	 * "145696"; // Reading test data from DataBase rstNavigationMenuData =
	 * dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
	 * rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST,
	 * CASE_NUM); String serialNumberDeviceList =
	 * rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);
	 * 
	 * // verify daily revenue browser.navigateURL(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_URL,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,
	 * FilePath.PROPERTY_CONFIG_FILE),
	 * propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,
	 * FilePath.PROPERTY_CONFIG_FILE)); navigationBar.selectOrganization(
	 * propertyFile.readPropertyFile(Configuration.CURRENT_ORG,
	 * FilePath.PROPERTY_CONFIG_FILE));
	 * 
	 * // navigate to admin>device and verify serial number filter functionality
	 * navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.
	 * MENU_ITEM)); textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,
	 * serialNumberDeviceList); foundation.threadWait(Constants.ONE_SECOND);
	 * CustomisedAssert.assertEquals(foundation.getText(DeviceList.
	 * LIST_SERIAL_NUMBER), serialNumberDeviceList);
	 * foundation.click(DeviceList.BTN_EXPORT_DEVICE);
	 * 
	 * // download assertion String[] uiData =
	 * (foundation.getText(DeviceList.TXT_RECORD_COUNT)).split(" ");
	 * CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.
	 * EXCEL_DEVICE_EXPORT_SRC));
	 * foundation.copyFile(FilePath.EXCEL_DEVICE_EXPORT_SRC,
	 * FilePath.EXCEL_DEVICE_EXPORT_TAR); int excelCount =
	 * excel.getExcelRowCount(FilePath.EXCEL_DEVICE_EXPORT_TAR); // record count
	 * validation CustomisedAssert.assertEquals(String.valueOf(excelCount),
	 * uiData[0]); Map<String, String> uidata =
	 * table.getTblSingleRowRecordUI(DeviceList.TBL_GRID, DeviceList.TBL_ROW);
	 * List<String> uiList = new ArrayList<String>(uidata.values());
	 * uiList.removeAll(Arrays.asList("", null)); // excel data validation
	 * CustomisedAssert.assertTrue(excel.verifyExcelData(uiList,
	 * FilePath.EXCEL_DEVICE_EXPORT_TAR, 1));
	 * 
	 * } catch (Exception exc) { TestInfra.failWithScreenShot(exc.toString()); }
	 * finally { // delete files
	 * foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_SRC);
	 * foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_TAR); } }
	 */

	@Test(description = "145697-QAA-24-Verify the serial number column display in device export file (Super>Device)")
	public void serialNumberExportSuper() {
		try {
			final String CASE_NUM = "145697";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			String serialNumberDeviceList = rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(DeviceList.BTN_SEARCH);
			CustomisedAssert.assertEquals(foundation.getText(DeviceList.LIST_SERIAL_NUMBER), serialNumberDeviceList);
			foundation.click(DeviceList.BTN_EXPORT_DEVICE);

			// download assertion
			String[] uiData = (foundation.getText(DeviceList.TXT_RECORD_COUNT)).split(" ");
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_DEVICE_EXPORT_SRC));
			foundation.copyFile(FilePath.EXCEL_DEVICE_EXPORT_SRC, FilePath.EXCEL_DEVICE_EXPORT_TAR);
			int excelCount = excel.getExcelRowCount(FilePath.EXCEL_DEVICE_EXPORT_TAR);
			// record count validation
			CustomisedAssert.assertEquals(String.valueOf(excelCount), uiData[0]);
			Map<String, String> uidata = table.getTblSingleRowRecordUI(DeviceList.TBL_GRID, DeviceList.TBL_ROW);
			List<String> uiList = new ArrayList<String>(uidata.values());
			uiList.removeAll(Arrays.asList("", null, " "));
			// excel data validation
			CustomisedAssert.assertTrue(excel.verifyExcelData(uiList, FilePath.EXCEL_DEVICE_EXPORT_TAR, 1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_SRC);
			foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_TAR);
		}
	}

	@Test(description = "164070-QAA-81-ADM>Super>Device>Device List")
	public void superDeviceList() {
		try {
			final String CASE_NUM = "164070";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			String serialNumberDeviceList = rstDeviceListData.get(CNDeviceList.SERIAL_NUMBER);

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to admin>device and verify serial number filter functionality
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, serialNumberDeviceList);
			foundation.click(DeviceList.BTN_SUBMIT);
			foundation.threadWait(Constants.ONE_SECOND);
			CustomisedAssert.assertEquals(foundation.getText(DeviceList.LIST_SERIAL_NUMBER), serialNumberDeviceList);
			foundation.click(DeviceList.BTN_EXPORT_DEVICE);

			// download assertion
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_DEVICE_EXPORT_SRC));
			// record count validation
			foundation.click(DeviceList.BTN_COMMISSION);
			foundation.click(Commission.BTN_COMMISSION_CANCEL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_SEARCH_DEVICE));
			foundation.click(DeviceList.BTN_COMMISSION);
			foundation.click(Commission.BTN_COMMISSION_CREATE_NEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(KioskCreate.TITLE_KIOSK_CREATE));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_DEVICE_EXPORT_SRC);
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

			List<String> deviceName = Arrays
					.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			// navigate to admin>device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, rstDeviceListData.get(CNDeviceList.DEVICE));
			foundation.click(DeviceList.BTN_SUBMIT);
			foundation.waitforElement(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)),
					Constants.MEDIUM_TIME);

			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.MEDIUM_TIME);

			dropDown.selectItem(DeviceList.DRP_PICO_DEVICE_TYPE, deviceName.get(0), Constants.TEXT);

			// Verifying Cooler Type is Present
			List<String> coolerType = foundation.getTextofListElement(DeviceSummary.DPD_COOLER_TYPE);
			CustomisedAssert.assertTrue(coolerType.toString().contains(deviceName.get(1)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	//commenting below two testcase as per william , operator and driver user should not have any records and no create New Button
	
	/* @Test(description = "164079-QAA-12-Validate Demo option is displayed in Cooler dropdown when logged in as Operator user")
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
			foundation.click(DeviceList.BTN_SUBMIT);
			foundation.waitforElement(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)),
					Constants.MEDIUM_TIME);

			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.MEDIUM_TIME);

			// Verifying Cooler Type is not Present
			List<String> coolerType = foundation.getTextofListElement(DeviceSummary.DPD_COOLER_TYPE);
			CustomisedAssert
					.assertTrue(!coolerType.toString().contains(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
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
			foundation.click(DeviceList.BTN_SUBMIT);
			foundation.waitforElement(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)),
					Constants.MEDIUM_TIME);

			foundation.click(deviceList.objDeveiceLink(rstDeviceListData.get(CNDeviceList.DEVICE)));
			foundation.waitforElement(DeviceSummary.LBL_DEVICE_SUMMARY, Constants.MEDIUM_TIME);

			// Verifying Cooler Type is not Present
			List<String> coolerType = foundation.getTextofListElement(DeviceSummary.DPD_COOLER_TYPE);
			CustomisedAssert
					.assertTrue(!coolerType.toString().contains(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	} */

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
			// String location = rstDeviceListData.get(CNDeviceList.LOCATION);

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
			dropDown.selectItem(DeviceList.DRP_PICO_DEVICE_TYPE, dbData.get(4), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);

			// Verifying Cooler Type is Present
			List<String> coolerType = foundation.getTextofListElement(DeviceSummary.DPD_COOLER_TYPE);
			CustomisedAssert.assertTrue(coolerType.toString().contains(dbData.get(3)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "164600-QAA-106-Add new device and check if the device is displayed in the Deploy Device List")
	public void verifyAddedDeviceDisplay() {
		try {
			final String CASE_NUM = "164600";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			List<String> dbData = Arrays
					.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			// navigate to admin>device
			navigationBar.navigateToMenuItem(menuItem.get(0));
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

			navigationBar.navigateToMenuItem(menuItem.get(1));
			locationList.selectLocationName(location);

			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.BTN_ADD_PRODUCT_ADD, Constants.SHORT_TIME);

			// searching for newly created kiosk Device
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, device);
			List<String> deviceName = foundation.getTextofListElement(LocationSummary.TBL_DEVICE_LIST);

			CustomisedAssert.assertTrue(deviceName.contains(device));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "164601-QAA-106-Search for specific Device with name and check if search filter is working fine")
	public void verifyDeviceSearchByName() {
		try {
			final String CASE_NUM = "164601";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			String device = rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			// navigate to admin>device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location);

			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.BTN_ADD_PRODUCT_ADD, Constants.SHORT_TIME);

			// searching for newly created kiosk Device
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, device);
			List<String> deviceName = foundation.getTextofListElement(LocationSummary.TBL_DEVICE_LIST);
			for (int i = 0; i < deviceName.size(); i = i + 2)
				CustomisedAssert.assertTrue(deviceName.contains(device));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "164602-QAA-106-Search for specific Device ip and check if search filter is working fine")
	public void verifyDeviceSearchByIp() {
		try {
			final String CASE_NUM = "164602";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			String device = rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);
			String location = rstDeviceListData.get(CNDeviceList.LOCATION);

			// navigate to admin>device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(location);

			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.BTN_ADD_PRODUCT_ADD, Constants.SHORT_TIME);

			// searching for newly created kiosk Device
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, device);
			List<String> deviceName = foundation.getTextofListElement(LocationSummary.TBL_DEVICE_LIST);
			for (int i = 1; i < deviceName.size(); i = i + 2)
				CustomisedAssert.assertTrue(deviceName.get(i).contains(device));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "164603-QAA-106-Search for specific Device name and click on cancel")
	public void verifyNewDeviceIsNotDisplayed() {
		try {
			final String CASE_NUM = "164603";

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
			// String location = rstDeviceListData.get(CNDeviceList.LOCATION);

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
			foundation.waitforElement(KioskCreate.BTN_CANCEL, Constants.SHORT_TIME);
			foundation.click(KioskCreate.BTN_CANCEL);
			foundation.waitforElement(KioskCreate.TXT_DEVICE_LIST, Constants.SHORT_TIME);
			foundation.refreshPage();

			// searching for newly created kiosk Device
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, device);
			foundation.click(DeviceList.BTN_SUBMIT);
			List<String> deviceName = foundation.getTextofListElement(DeviceList.TBL_ROW);
			CustomisedAssert.assertTrue(deviceName.contains(Constants.EMPTY_STRING));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "164604-QAA-106-Search for specific Device name and click on add")
	public void verifyDeviceIsNotDisplayed() {
		try {
			final String CASE_NUM = "164604";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstDeviceListData.get(CNDeviceList.LOCATION));
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);

			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);

			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			List<String> deviceName = foundation.getTextofListElement(LocationSummary.TBL_DEVICE_LIST);
			CustomisedAssert.assertFalse(deviceName.contains(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.click(LocationSummary.BTN_DEVICE_CLOSE);
			foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
			foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
			foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_YES_REMOVE);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);

		}
	}

	@Test(description = "164605-QAA-106-Remove device and check if the device is displayed in Deploy Device List")
	public void verifyDeviceIsDisplayed() {
		try {
			final String CASE_NUM = "164605";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstDeviceListData.get(CNDeviceList.LOCATION));
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);

			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.click(LocationSummary.TBL_DEVICE_LIST);

			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);

			// foundation.click(LocationSummary.BTN_DEVICE_CLOSE);
			foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
			foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
			foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_YES_REMOVE);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);

			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			List<String> deviceName = foundation.getTextofListElement(LocationSummary.TBL_DEVICE_LIST);
			CustomisedAssert.assertTrue(deviceName.contains(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

		}
	}

	@Test(description = "164606-QAA-106-Select multiple device and click on add")
	public void verifyMultipleDeviceDisplayed() {
		try {
			final String CASE_NUM = "164606";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			List<String> deviceName = Arrays
					.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstDeviceListData.get(CNDeviceList.LOCATION));
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);

			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			locationSummary.selectDevice(deviceName.get(0));
			locationSummary.selectDevice(deviceName.get(1));

			foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
			textBox.enterText(LocationSummary.TXT_FIND_DEVICE, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			List<String> deviceNameUI = foundation.getTextofListElement(LocationSummary.TBL_DEVICE_LIST);
			CustomisedAssert.assertFalse(deviceNameUI.contains(deviceName.get(0)));
			CustomisedAssert.assertFalse(deviceNameUI.contains(deviceName.get(1)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			foundation.click(LocationSummary.BTN_DEVICE_CLOSE);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
			foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
			foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_YES_REMOVE);
			if (foundation.isDisplayed(DeviceDashboard.BTN_YES_REMOVE))
				foundation.click(DeviceDashboard.BTN_YES_REMOVE);
			foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
			foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
			foundation.click(DeviceDashboard.BTN_YES_REMOVE);
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);

		}
	}

	@Test(description = "C164607-QAA-106-Verify sorting in the Deploy Device UI")
	public void verifySortingDevice() {
		try {
			final String CASE_NUM = "164607";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			locationList.selectLocationName(rstDeviceListData.get(CNDeviceList.LOCATION));
			foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);

			foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);

			foundation.verifySortText(LocationSummary.TBL_DEVICE_NAME_COLUMN, Constants.ASCENDING);
			foundation.verifySortText(LocationSummary.TBL_DEVICE_NAME_COLUMN, Constants.DESCENDING);
			foundation.click(LocationSummary.BTN_DEVICE_CLOSE);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "165988-QAA-89-Verify Commission page is displayed")
	public void verifyCommissionPage() {
		try {
			final String CASE_NUM = "165988";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			// navigate to admin>device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(KioskCreate.BTN_CREATE, Constants.SHORT_TIME);

			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.BTN_COMMISSION));

			foundation.click(DeviceList.BTN_COMMISSION);
			foundation.waitforElement(Commission.LBL_COMMISSION, Constants.SHORT_TIME);

			CustomisedAssert.assertTrue(foundation.isDisplayed(Commission.BTN_CANCEL));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Commission.BTN_CREATE_NEW));

			foundation.click(Commission.BTN_CANCEL);
			foundation.waitforElement(DeviceList.BTN_COMMISSION, Constants.SHORT_TIME);

			foundation.click(DeviceList.BTN_COMMISSION);
			foundation.waitforElement(Commission.LBL_COMMISSION, Constants.SHORT_TIME);
			foundation.click(Commission.BTN_CREATE_NEW);
			foundation.waitforElement(KioskCreate.LBL_KIOSK_CREATE, Constants.SHORT_TIME);

			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(KioskCreate.BTN_CREATE, Constants.SHORT_TIME);

			foundation.click(DeviceList.BTN_COMMISSION);
			foundation.waitforElement(Commission.LBL_COMMISSION, Constants.SHORT_TIME);
			table.selectRow("KSK_TEST_ELOR");
			// rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			foundation.waitforElement(AssignDeviceToOrg.LBL_ASSIGN, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AssignDeviceToOrg.LBL_ASSIGN));

			foundation.click(AssignDeviceToOrg.BTN_CANCEL);
			foundation.waitforElement(Commission.LBL_COMMISSION, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Commission.LBL_COMMISSION));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "165989-QAA-89-Verify Search, Sorting in Commission page")
	public void verifySearchAndSortingInCommissionPage() {
		try {
			final String CASE_NUM = "165989";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

			// navigate to admin>device
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(KioskCreate.BTN_CREATE, Constants.SHORT_TIME);

			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.BTN_COMMISSION));

			foundation.click(DeviceList.BTN_COMMISSION);
			foundation.waitforElement(Commission.LBL_COMMISSION, Constants.SHORT_TIME);

			List<String> recordCount = Arrays
					.asList(rstDeviceListData.get(CNDeviceList.DEVICE).split(Constants.DELIMITER_TILD));

			dropDown.selectItem(Commission.TXT_SELECT, recordCount.get(0), Constants.VALUE);
			CustomisedAssert
					.assertTrue(table.getTblRowCount(Commission.TBL_ROW) <= Integer.parseInt(recordCount.get(0)));

			dropDown.selectItem(Commission.TXT_SELECT, recordCount.get(1), Constants.VALUE);
			CustomisedAssert
					.assertTrue(table.getTblRowCount(Commission.TBL_ROW) <= Integer.parseInt(recordCount.get(1)));

			foundation.verifySortText(Commission.TBL_COMMISSION, Constants.ASCENDING);
			foundation.click(Commission.TBL_NAME_COLUMN);
			foundation.verifySortText(Commission.TBL_COMMISSION, Constants.DESCENDING);
			foundation.click(Commission.TBL_NAME_COLUMN);

			textBox.enterText(Commission.TXT_SEARCH, rstDeviceListData.get(CNDeviceList.PRODUCT_NAME));
			Map<String, String> tableData = table.getTblSingleRowRecordUI(Commission.TBL_COMMISSION,
					Commission.TBL_ROW);
			CustomisedAssert.assertTrue(tableData.containsValue(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME)));
			textBox.enterText(Commission.TXT_SEARCH, Constants.EMPTY_STRING);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	@Test(description = "197371 -SOS-30517-Verify the Duplicate device is not present on Super>Device page")
	public void verifyDuplicateDeviceNotPresentONDeviceDashboard() {
		try {
			final String CASE_NUM = "197371";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
	
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to super>Device and verify that duplicate device is not present 
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE, rstDeviceListData.get(CNDeviceList.DEVICE));
			foundation.click(DeviceList.BTN_SEARCH);
			foundation.threadWait(Constants.ONE_SECOND);
			//Bug is present as Data is not visible on 100% resolution - https://365retailmarkets.atlassian.net/browse/SOS-29342
			Assert.assertTrue(foundation.getText(DeviceList.TXT_RECORDS_DATA).equals(rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE)));
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * @author sakthir Date: 14-09-2022
	 */
	@Test(description = "197605-SOS-29862-Add option to include 'No MSR' within ADM")
	public void verifyInEncryptMSROptionForNoMSR() {
		   final String CASE_NUM = "197605";
		
		    // Reading test data from DataBase
		    rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		    rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			
		    String menu=rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		    String device=rstDeviceListData.get(CNDeviceList.PRODUCT_NAME);
		    List<String> data = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		try {

			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
					
			// navigate to super>Device and search for device 
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.SUPER_DEVICE));
			foundation.click(DeviceList.TXT_SEARCH_DEVICE);
			textBox.enterText(DeviceList.TXT_SEARCH_DEVICE,device);
			foundation.click(DeviceList.BTN_SEARCH);
			foundation.waitforElementToBeVisible(DeviceList.TXT_TABLE_RECORD, 3);
			foundation.click(DeviceList.TXT_TABLE_RECORD);
			
			//Select freedom pay and enter client id, store id and pay label
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.LBL_DEVICE_SUMMARY));
			foundation.scrollIntoViewElement(DeviceSummary.DPD_SELECT_PAYMENT);
			dropDown.selectItem(DeviceSummary.DPD_SELECT_PAYMENT,data.get(0), Constants.TEXT);
			foundation.click(DeviceSummary.TXT_PAYID);
			textBox.enterText(DeviceSummary.TXT_PAYID, data.get(1));
			deviceSummary.freedomPayConfig(data.get(1));
			foundation.scrollIntoViewElement(DeviceSummary.DPD_MSR);
			foundation.click(DeviceSummary.DPD_MSR);
			dropDown.selectItem(DeviceSummary.DPD_MSR,data.get(2), Constants.TEXT);
			foundation.click(DeviceSummary.BTN_SAVE);
			
			//validating the No MSR option
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceList.TXT_DEVICE_LIST));
			foundation.click(DeviceList.TXT_SEARCH);
			textBox.enterText(DeviceList.TXT_SEARCH, device);
			foundation.click(DeviceList.TBL_DEVICE_NAME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.LBL_DEVICE_SUMMARY));
			CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.DPD_MSR_CHECK).equals(data.get(2)));
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally
		{
			//resetting
			foundation.scrollIntoViewElement(DeviceSummary.DPD_SELECT_PAYMENT);
			dropDown.selectItem(DeviceSummary.DPD_SELECT_PAYMENT,data.get(3), Constants.TEXT);
			foundation.scrollIntoViewElement(DeviceSummary.DPD_MSR);
			foundation.click(DeviceSummary.DPD_MSR);
			dropDown.selectItem(DeviceSummary.DPD_MSR,data.get(4), Constants.TEXT);
			foundation.click(DeviceSummary.BTN_SAVE);
		}
	}

	/**
	 * @author sakthir Date: 19-09-2022
	 */
	@Test(description = "198515-SOS-28841-To Verify the Device Level Rates in Existing Device"
			+"198516-SOS-28842-To Verify the Device Level Rates in New Device"
			+"198514-SOS-28840-To Verify Add Rate Fields is displayed under Device Summary Page")
	public void verifyNewRateFieldsInDeviceSummary() {
		   final String CASE_NUM = "198515";
		
		    // Reading test data from DataBase
		    rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		    rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			
		    List<String> menu= Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		    List<String> device=Arrays
					.asList(rstDeviceListData.get(CNDeviceList.LOCATION).split(Constants.DELIMITER_TILD));
		    List<String> data = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
		
			// navigate to location, select location and get existing device
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(device.get(0));
			foundation.scrollIntoViewElement(LocationSummary.BTN_DEVICE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.DEVICE_NAME));
			String device_name=foundation.getText(LocationSummary.DEVICE_NAME);
			
			//verify the new Fields location level
			locationSummary.verifyRateFields(data.get(2));
			
			//change location level rate values
			locationSummary.enterRateValueForFields(data.get(3),data.get(5));
			foundation.click(LocationSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG));
			
			//navigate to Admin->Device and search for existing device
			navigationBar.navigateToMenuItem(menu.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));
			deviceDashboard.selectDeviceName(device_name);
			
			//verify the new Fields Device level Option as Default as Inherit from location
			deviceSummary.verifyNewFields(data.get(0));
			
			//verify changes rate on device level 
			CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.TXT_ORG_GMR).equals(data.get(9)));
			CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.TXT_ORG_NANOGMR).equals(data.get(9)));
			CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.TXT_ORG_CREDIT).equals(data.get(9)));
			CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.TXT_ORG_NANOCREDIT).equals(data.get(9)));
								
			// navigate to location, select location and add new device
			navigationBar.navigateToMenuItem(menu.get(0));
			locationList.selectLocationName(device.get(0));
			foundation.scrollIntoViewElement(LocationSummary.BTN_DEVICE);
			locationSummary.addDeviceAndVerify(data.get(1));
			
			//verify error msg after three decimal point in location Summary
			dropDown.selectItem(LocationSummary.DPD_GMR, data.get(3), Constants.TEXT);
			foundation.click(LocationSummary.TXT_LOCATION_GMR);
			textBox.enterText(LocationSummary.TXT_LOCATION_GMR, data.get(8));
			dropDown.selectItem(LocationSummary.DPD_NANOGMR, data.get(3), Constants.TEXT);
			foundation.click(LocationSummary.TXT_LOCATION_NANOGMR);
			textBox.enterText(LocationSummary.TXT_LOCATION_NANOGMR, data.get(8));			
			dropDown.selectItem(LocationSummary.DPD_CREDIT, data.get(3), Constants.TEXT);
			foundation.click(LocationSummary.TXT_LOCATION_CREDIT);
			textBox.enterText(LocationSummary.TXT_LOCATION_CREDIT, data.get(8));
			dropDown.selectItem(LocationSummary.DPD_NANOCREDIT, data.get(3), Constants.TEXT);
			foundation.click(LocationSummary.TXT_LOCATION_NANOCREDIT);
			textBox.enterText(LocationSummary.TXT_LOCATION_NANOCREDIT, data.get(8));
			foundation.click(LocationSummary.BTN_SAVE);
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.GMR_ERROR).equals(data.get(10)));
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.NANOGMR_ERROR).equals(data.get(10)));
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.CREDIT_ERROR).equals(data.get(10)));
			CustomisedAssert.assertTrue(foundation.getText(LocationSummary.NANOCREDIT_ERROR).equals(data.get(10)));
			
    		//navigate to Admin->Device and search for new device
			navigationBar.navigateToMenuItem(menu.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));
    		deviceDashboard.selectDeviceName(data.get(1));
					
    		//verify the new Fields Device level Option as Default as Inherit from location
			deviceSummary.verifyNewFields(data.get(0));	
			
			//verify DropDown Options
			deviceSummary.verifyNewFields(data.get(0));
			dropDown.selectItem(DeviceSummary.DPD_GMR, data.get(4), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.DPD_OPTION_GMR).equals(data.get(4)));
			dropDown.selectItem(DeviceSummary.DPD_NANOGMR, data.get(4), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.DPD_OPTION_NANOGMR).equals(data.get(4)));
			dropDown.selectItem(DeviceSummary.DPD_CREDIT, data.get(4), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.DPD_OPTION_CREDIT).equals(data.get(4)));
			dropDown.selectItem(DeviceSummary.DPD_NANOCREDIT, data.get(4), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.getText(DeviceSummary.DPD_OPTION_NANOCREDIT).equals(data.get(4)));
			
		}catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally
	    {
		   //resetting 
		   navigationBar.navigateToMenuItem(menu.get(0));
		   locationList.selectLocationName(device.get(0));
		   locationSummary.enterRateValueForFields(data.get(3),data.get(6));
		   dropDown.selectItem(LocationSummary.DPD_GMR, data.get(2), Constants.TEXT);
		   dropDown.selectItem(LocationSummary.DPD_NANOGMR, data.get(2), Constants.TEXT);
		   dropDown.selectItem(LocationSummary.DPD_CREDIT, data.get(2), Constants.TEXT);
		   dropDown.selectItem(LocationSummary.DPD_NANOCREDIT, data.get(2), Constants.TEXT);
		   foundation.click(LocationSummary.BTN_SAVE);
		   CustomisedAssert.assertTrue(foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG));
		   locationList.selectLocationName(device.get(0));
		   foundation.scrollIntoViewElement(LocationSummary.BTN_DEVICE);
		   locationSummary.removeDevice(data.get(1));
	    }
	}
	
	/**
	 * @author sakthir Date: 28-09-2022
	 */
	@Test(description = "176138-ADM >> Admin >> Device >> Device Summary Page"
			+"205000-SOS-23697:ADM >Device Summary Page> Verify on CC Processor Dropdown verify new Added Option"
			+"205001-SOS-31843:ADM >Device Summary Page>Verify on CC Processor Dropdown verify update existing option"
			+"205002-SOS-31843:ADM >Device Summary Page>Create new Device and Verify on CC Processor Dropdown verify new Added Option"
			+"205003-SOS-31843:ADM >Device Summary Page>Create new Device and Verify on CC Processor Dropdown verify update existing option")
	public void verifyHeaderAndNewAddedOptionAndUpdatedOptionInCCProcessor() {
		   final String CASE_NUM = "176138";
		
		    // Reading test data from DataBase
		    rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		    rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			
		   String menu=rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		   List<String> device=Arrays
					.asList(rstDeviceListData.get(CNDeviceList.LOCATION).split(Constants.DELIMITER_TILD));
		   List<String> data =Arrays
							.asList( rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			
			//navigate to Admin->Device 
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));

            //Click new Device and verify the new Added option in CCProcessor
			foundation.click(DeviceDashboard.BTN_CREATENEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(KioskCreate.TITLE_KIOSK_CREATE));
			
			//verify the Existing newly Added option in CCProcessor
			foundation.scrollIntoViewElement(DeviceSummary.DPD_CLICK_CCPROCESSOR);
			foundation.click(DeviceSummary.DPD_CLICK_CCPROCESSOR);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(DeviceSummary.DPD_OPTION_CCPROCESSOR).contains(device.get(1)));
			
			//verify the Existing updated option in CCProcessor
			CustomisedAssert.assertTrue(foundation.getTextofListElement(DeviceSummary.DPD_OPTION_CCPROCESSOR).contains(device.get(2)));
			
			//navigate to Admin->Device and verify the table header
			navigationBar.navigateToMenuItem(menu);	
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));
			foundation.waitforElementToBeVisible(DeviceDashboard.TABLE_HEADER, 3);
			Map<String, String> uiTableHeaders = table.getTblHeadersDevice(DeviceDashboard.TABLE_HEADER);
			List<String> uiListHeaders = new ArrayList<String>(uiTableHeaders.keySet());
			Collections.sort(uiListHeaders);
			CustomisedAssert.assertTrue(uiListHeaders.equals(data));
			
			//Select Device and verify the new Added option in CCProcessor
			deviceDashboard.selectDeviceName(device.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.LBL_DEVICE_SUMMARY));
			foundation.scrollIntoViewElement(DeviceSummary.DPD_CLICK_CCPROCESSOR);
			foundation.click(DeviceSummary.DPD_CLICK_CCPROCESSOR);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(DeviceSummary.DPD_OPTION_CCPROCESSOR).contains(device.get(1)));
			
			//verify the Existing updated option in CCProcessor
			CustomisedAssert.assertTrue(foundation.getTextofListElement(DeviceSummary.DPD_OPTION_CCPROCESSOR).contains(device.get(2)));
			
			
			}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * @author sakthir Date: 14-10-2022
	 */
	@Test(description = "204917-Verify stock well is displayed in type drop down field of kiosk create page"
			+"204918-Verify stock well option is displayed correctly when it is saved in Kiosk create page & device Summary page"
			+"204919-Verify stock well is displayed in type drop down field of Device Summary page")
	public void verifyTypeDropdownStockwellOptionForNewAndExistingLocationInDeviceSummary() {
		   final String CASE_NUM = "204917";
		
		    // Reading test data from DataBase
		    rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		    rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
			
		   String menu=rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM);
		   List<String> device=Arrays
					.asList(rstDeviceListData.get(CNDeviceList.LOCATION).split(Constants.DELIMITER_TILD));
		  String data =rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION);
		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			
			//navigate to Admin->Device 
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));

            //Click new Device and verify Stock well dropdown option in new location
			foundation.click(DeviceDashboard.BTN_CREATENEW);
			CustomisedAssert.assertTrue(foundation.isDisplayed(KioskCreate.TITLE_KIOSK_CREATE));
			foundation.click(KioskCreate.DPD_TYPE);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(KioskCreate.DPD_OPTION_TYPE).contains(data));
			textBox.enterText(KioskCreate.TXT_NAME,string.getRandomCharacter());
			String value=foundation.getText(KioskCreate.TXT_NAME);
			dropDown.selectItem(KioskCreate.DPD_ORG, device.get(1), Constants.TEXT);
			dropDown.selectItem(KioskCreate.DPD_PROCESSOR, device.get(2), Constants.TEXT);
			textBox.enterText(KioskCreate.TXT_TERMINAL_ID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			foundation.waitforElement(KioskCreate.BTN_SAVE, Constants.SHORT_TIME);
			foundation.scrollIntoViewElement(KioskCreate.BTN_SAVE);
			foundation.click(KioskCreate.BTN_SAVE);
			
			//Click on newly created and verify Stock well dropdown option in new location
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));
			deviceDashboard.selectDeviceName(value);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.LBL_DEVICE_SUMMARY));
			foundation.scrollIntoViewElement(DeviceSummary.DPD_TYPE);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(DeviceSummary.DPD_TYPE).contains(data));
			
			//Click existing Device and verify Stock well dropdown option in existing location
			navigationBar.navigateToMenuItem(menu);
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceDashboard.LBL_ADMIN_DEVICE_DASHBOARD));
			deviceDashboard.selectDeviceName(device.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DeviceSummary.LBL_DEVICE_SUMMARY));
			foundation.scrollIntoViewElement(DeviceSummary.DPD_TYPE);
			CustomisedAssert.assertTrue(foundation.getTextofListElement(DeviceSummary.DPD_TYPE).contains(data));
			
		}catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

}
