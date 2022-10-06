package at.smartshop.v5.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.TransactionSearchPage;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.Payments;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5TestAdmin extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Payments payments = new Payments();
	private LocationList locationList = new LocationList();
	private Foundation foundation = new Foundation();
	private LocationSummary locationSummary = new LocationSummary();
	private TransactionSearchPage transactionSearchPage = new TransactionSearchPage();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstLocationSummaryData;

	@Test(description = "178410-ADM - Admin - Transaction - Transaction Search")
	public void verifyAdminTransaction() {
		final String CASE_NUM = "178410";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.ORDER_PAGE).split(Constants.DELIMITER_TILD));
		List<String> Datas = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

		try {

			// Launch v5 device and do transaction
			payments.launchV5DeviceAndDoransaction(rstV5DeviceData.get(CNV5Device.PRODUCT_NAME),
					rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN), requiredData.get(0),
					rstV5DeviceData.get(CNV5Device.PAYMENTS_PAGE));

			// Launch ADM as super user
			browser.launch(Constants.LOCAL, Constants.CHROME);
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Navigate Admin > Transaction
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			transactionSearchPage.verifyTransactionSearch(Datas.get(2), Datas.get(0), Datas.get(1), Datas.get(3));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * update inventory from adm and verify in kiosk from driver login
	 */

	@Test(description = "205036-ADM > Update inventory from adm and verify in kiosk inventory from driver login")
	public void updateInventoryFromAdmAndVerrifyInKioskFromDriverlogin() {
		final String CASE_NUM = "205036";

		// Reading test data from DataBase
		//rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		
		List<String> values = Arrays
				.asList(rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// select Location
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.COUNTRY));

			// Navigating to products tab & update min max values in grid
			locationSummary.updateMinAndMaxValueInProductGrid(values.get(0), values.get(3), values.get(1));
			browser.close();
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {
			//resetting data
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			locationList.selectLocationName(rstLocationSummaryData.get(CNLocationSummary.COUNTRY));
			locationSummary.updateMinAndMaxValueInProductGrid(values.get(0), values.get(3), values.get(2));
			browser.close();		
		}
	}
}
