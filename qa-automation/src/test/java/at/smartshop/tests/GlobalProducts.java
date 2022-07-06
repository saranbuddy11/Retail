package at.smartshop.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Point;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNGlobalProductChange;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNationalAccounts;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.database.columns.CNProduct;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.GlobalProductChange;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.ProductSummary;

@Listeners(at.framework.reportsetup.Listeners.class)
public class GlobalProducts extends TestInfra {

	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private TextBox textBox = new TextBox();
	private ProductSummary productSummary = new ProductSummary();
	private Foundation foundation = new Foundation();
	private ResultSets dataBase = new ResultSets();
	private LocationList locationList = new LocationList();
	private Table table = new Table();
	private Excel excel = new Excel();
	private Strings strings = new Strings();
	private GlobalProductChange globalProductChange = new GlobalProductChange();
	private Numbers numbers = new Numbers();
	private Dropdown dropDown = new Dropdown();
	private CheckBox checkBox = new CheckBox();
	private LocationSummary locationSummary = new LocationSummary();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstGlobalProductChangeData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstNationalAccountData;
	private Map<String, String> rstProductData;
	private Map<String, String> rstOrgSummaryData;
	private Map<String, String> rstProductSummaryData;

	@Test(description = "110985-This test to Increment Price value for a product in Global Product Change for Location(s)")
	public void IncrementPriceForProductInGPCLocation() {
		try {
			final String CASE_NUM = "110985";
			double price = 0.00;
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(1));

			// Searching for Product
			String product = rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME);
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			foundation.click(
					globalProduct.getGlobalProduct(rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME)));

			foundation.waitforElementToBeVisible(ProductSummary.TXT_LOCATION_SEARCH_FILTER, Constants.SHORT_TIME);
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER,
					rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
			Map<String, String> productsRecord = productSummary
					.getProductDetails(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
			List<String> columnName = Arrays.asList(
					rstGlobalProductChangeData.get(CNGlobalProductChange.COLUMN_NAME).split(Constants.DELIMITER_TILD));
			price = Double.parseDouble(productsRecord.get(columnName.get(3)));
			navigationBar.navigateToMenuItem(menuItem.get(0));

			foundation.click(globalProductChange
					.objLocation(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME)));
			foundation.click(GlobalProductChange.TAB_PRODUCT);
			textBox.enterText(GlobalProductChange.TXT_PRODUCT_NAME, product);
			foundation.click(GlobalProductChange.BTN_PRODUCT_APPLY);

			foundation.click(globalProductChange
					.objTableRow(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME)));

			foundation.click(GlobalProductChange.BTN_NEXT);

			// increment the price
			foundation.click(GlobalProductChange.BTN_INCREMENT);
			String priceText = foundation.getText(GlobalProductChange.LBL_PRICE);
			String minText = foundation.getText(GlobalProductChange.LBL_MIN);
			String maxText = foundation.getText(GlobalProductChange.LBL_MAX);

			// Validate the fields
			List<String> lblPrice = Arrays.asList(rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_LABEL)
					.split(Constants.DELIMITER_TILD));
			CustomisedAssert.assertEquals(priceText, lblPrice.get(0));
			CustomisedAssert.assertEquals(minText, lblPrice.get(1));
			CustomisedAssert.assertEquals(maxText, lblPrice.get(2));

			double Incrementprice = Double
					.parseDouble(rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_PRICE));
			textBox.enterText(GlobalProductChange.TXT_PRICE, Double.toString(Incrementprice));

			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.waitforElement(GlobalProductChange.BTN_OK, Constants.SHORT_TIME);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.isDisplayed(GlobalProductChange.MSG_SUCCESS);
			foundation.click(GlobalProductChange.REASON_BTNOK);

			// Select Menu and Global product
			foundation.threadWait(Constants.ONE_SECOND);
			navigationBar.navigateToMenuItem(menuItem.get(1));

			// Search and select product
			textBox.enterText(LocationList.TXT_FILTER, product);
			foundation.click(globalProduct.getGlobalProduct(product));
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER,
					rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
			double updatedPrice = price + Incrementprice;

			Map<String, String> updatedProductsRecord = productSummary
					.getProductDetails(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));

			CustomisedAssert.assertEquals(Double.parseDouble(updatedProductsRecord.get(columnName.get(3))),
					updatedPrice);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "116004-This test validates Removed Extended Location")
	public void RemoveLocation() {
		final String CASE_NUM = "116004";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstNationalAccountData = dataBase.getNationalAccountsData(Queries.NATIONAL_ACCOUNTS, CASE_NUM);

		// String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Selecting the Product
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(LocationList.TXT_FILTER, rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME));
			foundation.click(locationList.objGlobalProduct(rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME)));

			// selecting location
			foundation.waitforElement(ProductSummary.BTN_EXTEND, Constants.SHORT_TIME);
			foundation.click(ProductSummary.BTN_EXTEND);
			foundation.threadWait(Constants.SHORT_TIME);
			textBox.enterText(ProductSummary.TXT_FILTER, rstLocationListData.get(CNLocationList.LOCATION_NAME));
			table.selectRow(rstNationalAccountData.get(CNNationalAccounts.GRID_NAME),
					rstLocationListData.get(CNLocationList.LOCATION_NAME));
			foundation.click(ProductSummary.BTN_MODAL_SAVE);

			// Remove selected location
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(ProductSummary.LOATION_NAME);
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.click(ProductSummary.BTN_REMOVE);
			foundation.waitforElement(ProductSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142865-Select AVIFoodSystems org and Verify Global Products exported file is downloaded.")
	public void productExport() {
		try {
			final String CASE_NUM = "142865";

			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(rstOrgSummaryData.get(CNOrgSummary.ORG_NAME));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(1));

			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.THREE_SECOND);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(GlobalProduct.BTN_EXPORT);
			// download assertion
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_PROD_SRC));

			foundation.copyFile(FilePath.EXCEL_PROD_SRC, FilePath.EXCEL_PROD_TAR);
			Map<String, String> uiData = table.getTblHeadersUI(GlobalProduct.TBL_GRID);
			List<String> uiList = new ArrayList<String>(uiData.values());
			// excel headers validation
			CustomisedAssert.assertTrue(excel.verifyExcelData(uiList, FilePath.EXCEL_PROD_TAR, 0));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_PROD_SRC);
			foundation.deleteFile(FilePath.EXCEL_PROD_TAR);
		}

	}

	@Test(description = "142866-Select AVIFoodSystems org and Verify Global Products exported file  record count should match with ui.")
	public void verifyRecordCount() {
		try {
			final String CASE_NUM = "142866";

			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(rstOrgSummaryData.get(CNOrgSummary.ORG_NAME));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(1));

			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.THREE_SECOND);
			navigationBar.navigateToMenuItem(menuItem.get(0));

			foundation.threadWait(Constants.SHORT_TIME);
			boolean fileExists = foundation.isFileExists(FilePath.EXCEL_PROD_SRC);
			if (fileExists == false) {
				foundation.deleteFile(FilePath.EXCEL_PROD_SRC);
			}
//			String[] uiData = (foundation.getText(GlobalProduct.TXT_RECORD_COUNT)).split(" ");
			foundation.click(GlobalProduct.BTN_EXPORT);
			// download assertion
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_PROD_SRC));
			foundation.copyFile(FilePath.EXCEL_PROD_SRC, FilePath.EXCEL_PROD_TAR);
			// record count validation
//			int excelCount = excel.getExcelRowCount(FilePath.EXCEL_PROD_TAR);
			// CustomisedAssert.assertEquals(String.valueOf(excelCount), uiData[0]);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_PROD_SRC);
			foundation.deleteFile(FilePath.EXCEL_PROD_TAR);
		}

	}

	@Test(description = "142868-Select AVIFoodSystems org and Verify Global Products exported file doesnot contains any records when filtered product is not available")
	public void zeroRecords() {
		try {
			final String CASE_NUM = "142868";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			String expectedMsg = rstGlobalProductChangeData.get(CNGlobalProductChange.INFO_MESSAGE);
			String productName = strings.getRandomCharacter();

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(rstOrgSummaryData.get(CNOrgSummary.ORG_NAME));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, productName);
			foundation.threadWait(Constants.SHORT_TIME);
			String actualMsg = foundation.getText(GlobalProduct.TXT_RECORD_COUNT);
			CustomisedAssert.assertEquals(actualMsg, expectedMsg);
			String[] uiData = actualMsg.split(" ");

			boolean fileExistsSrc = foundation.isFileExists(FilePath.EXCEL_PROD_SRC);
			if (fileExistsSrc == false) {
				foundation.deleteFile(FilePath.EXCEL_PROD_SRC);
			}
			foundation.click(GlobalProduct.BTN_EXPORT);
			// download assertion
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_PROD_SRC));
			foundation.copyFile(FilePath.EXCEL_PROD_SRC, FilePath.EXCEL_PROD_TAR);
			int excelCount = excel.getExcelRowCount(FilePath.EXCEL_PROD_TAR);
			// record count validation
			CustomisedAssert.assertEquals(String.valueOf(excelCount), uiData[0]);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_PROD_SRC);
			foundation.deleteFile(FilePath.EXCEL_PROD_TAR);
		}

	}

	@Test(description = "142867-Select AVIFoodSystems org and Verify Global Products exported file records are matching as per the filter applied in ui.")
	public void verifyExportData() {
		try {
			final String CASE_NUM = "142867";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);
			String product = rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME);
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			navigationBar.navigateToMenuItem(menuItem.get(1));

			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA),
					Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.THREE_SECOND);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			foundation.threadWait(Constants.SHORT_TIME);
			boolean fileExists = foundation.isFileExists(FilePath.EXCEL_PROD_SRC);
			if (fileExists == false) {
				foundation.deleteFile(FilePath.EXCEL_PROD_SRC);
			}

			String[] uiData = (foundation.getText(GlobalProduct.TXT_RECORD_COUNT)).split(" ");
			foundation.click(GlobalProduct.BTN_EXPORT);
			foundation.threadWait(Constants.SHORT_TIME);
			// download assertion
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_PROD_SRC));
			foundation.copyFile(FilePath.EXCEL_PROD_SRC, FilePath.EXCEL_PROD_TAR);
			int excelCount = excel.getExcelRowCount(FilePath.EXCEL_PROD_TAR);
			// record count validation
			CustomisedAssert.assertEquals(String.valueOf(excelCount), uiData[0]);
			Map<String, String> uidata = table.getTblSingleRowRecordUI(GlobalProduct.TBL_GRID, GlobalProduct.TBL_ROW);
			List<String> uiList = new ArrayList<String>(uidata.values());
			// excel data validation
			CustomisedAssert.assertTrue(excel.verifyExcelData(uiList, FilePath.EXCEL_PROD_TAR, 1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// delete files
			foundation.deleteFile(FilePath.EXCEL_PROD_SRC);
			foundation.deleteFile(FilePath.EXCEL_PROD_TAR);
		}

	}

	@Test(description = "143342-verify when entered product scancode character length <25 , it should show scancode Ok message.")
	public void verifyScancodelessthan25() {
		try {
			final String CASE_NUM = "143342";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductData = dataBase.getProductData(Queries.PRODUCT, CASE_NUM);
			String expectedData = rstProductData.get(CNProduct.SUCCESS_SCANCODE);
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(GlobalProduct.BTN_CREATE);
			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
			textBox.enterText(GlobalProduct.TXT_SCAN_CODE, strings.getRandomCharacter());
			textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
			foundation.waitforElement(GlobalProduct.LBL_SCANCODE_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(GlobalProduct.LBL_SCANCODE_MSG);

			CustomisedAssert.assertEquals(actualData, expectedData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "143343-verify when entered product scancode character length =25 , it should show scancode Ok message.")
	public void verifyScancodeequals25() {
		try {
			final String CASE_NUM = "143343";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductData = dataBase.getProductData(Queries.PRODUCT, CASE_NUM);
			String scanCode = rstProductData.get(CNProduct.SCANCODE);
			String expectedData = rstProductData.get(CNProduct.SUCCESS_SCANCODE);
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(GlobalProduct.BTN_CREATE);
			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
			textBox.enterText(GlobalProduct.TXT_SCAN_CODE, scanCode);
			textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
			foundation.waitforElement(GlobalProduct.LBL_SCANCODE_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(GlobalProduct.LBL_SCANCODE_MSG);

			CustomisedAssert.assertEquals(actualData, expectedData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "143344-verify when product entered scancode character length >25 , it should show \"Scancode cannot exceed 25 characters!\" message.")
	public void verifyScancodeGreaterthan25() {
		try {
			final String CASE_NUM = "143344";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductData = dataBase.getProductData(Queries.PRODUCT, CASE_NUM);
			String scanCode = rstProductData.get(CNProduct.SCANCODE);
			List<String> expectedError = Arrays
					.asList(rstProductData.get(CNProduct.SCANCODE_ERROR).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(GlobalProduct.BTN_CREATE);
			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
			textBox.enterText(GlobalProduct.TXT_SCAN_CODE, scanCode);
			textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
			foundation.waitforElement(GlobalProduct.LBL_SCANCODE_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(GlobalProduct.LBL_SCANCODE_MSG);
			CustomisedAssert.assertEquals(actualData, expectedError.get(0));
			foundation.click(GlobalProduct.BUTTON_SAVE);
			foundation.waitforElement(GlobalProduct.LBL_ALERT_HEADER, Constants.SHORT_TIME);
			actualData = foundation.getText(GlobalProduct.LBL_ALERT_HEADER);
			CustomisedAssert.assertEquals(actualData, expectedError.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143345-verify when product entered scancode has invalid characters('.','~'), it should show \"Only letters and numbers allowed.\" message.")
	public void verifyScancodeGHasInvalidCharacters() {
		try {
			final String CASE_NUM = "143345";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductData = dataBase.getProductData(Queries.PRODUCT, CASE_NUM);

			List<String> scanCode = Arrays
					.asList(rstProductData.get(CNProduct.SCANCODE).split(Constants.DELIMITER_TILD));
			String expectedScancodeError = rstProductData.get(CNProduct.SCANCODE_ERROR);
			String expectedScancodeSuccess = rstProductData.get(CNProduct.SUCCESS_SCANCODE);
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(GlobalProduct.BTN_CREATE);
			for (int i = 0; i < scanCode.size(); i++) {
				textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
				textBox.enterText(GlobalProduct.TXT_SCAN_CODE, scanCode.get(i));
				textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
				textBox.enterText(GlobalProduct.LBL_COST, String.valueOf(numbers.generateRandomNumber(0, 9)));
				textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
				foundation.threadWait(Constants.TWO_SECOND);
				foundation.waitforElement(GlobalProduct.LBL_SCANCODE_MSG, Constants.EXTRA_LONG_TIME);
				foundation.waitforElement(GlobalProduct.LBL_SCANCODE_ERROR, Constants.EXTRA_LONG_TIME);

				String actualData = foundation.getText(GlobalProduct.LBL_SCANCODE_MSG);

				CustomisedAssert.assertEquals(actualData, expectedScancodeSuccess);
				foundation.threadWait(Constants.ONE_SECOND);
				String actualErrorMsg = foundation.getText(GlobalProduct.LBL_SCANCODE_ERROR);
				CustomisedAssert.assertEquals(actualErrorMsg, expectedScancodeError);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143348-verify when scancode field is empty,it should throw \"Scancode is required.\" & \"Missing scancode!\"")
	public void verifyScancodeMissing() {
		try {
			final String CASE_NUM = "143348";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductData = dataBase.getProductData(Queries.PRODUCT, CASE_NUM);

			List<String> expectedError = Arrays
					.asList(rstProductData.get(CNProduct.SCANCODE_ERROR).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// create a product
			foundation.click(GlobalProduct.BTN_CREATE);
			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
			textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
			textBox.enterText(GlobalProduct.LBL_COST, String.valueOf(numbers.generateRandomNumber(0, 9)));
			foundation.click(GlobalProduct.BUTTON_SAVE);
			foundation.waitforElement(GlobalProduct.POPUP_HEADER, Constants.SHORT_TIME);
			foundation.click(GlobalProduct.SAVE_POPUP_BTN);
			foundation.waitforElement(GlobalProduct.LBL_SCANCODE_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(GlobalProduct.LBL_SCANCODE_ERROR);
			CustomisedAssert.assertEquals(actualData, expectedError.get(0));

			// Verify Scancode required
			foundation.click(GlobalProduct.CANCEL_BTN);
			foundation.click(GlobalProduct.BTN_CREATE);
			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
			textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
			textBox.enterText(GlobalProduct.LBL_COST, String.valueOf(numbers.generateRandomNumber(0, 9)));
			foundation.click(GlobalProduct.BUTTON_SAVE);
			foundation.waitforElement(GlobalProduct.POPUP_HEADER, Constants.SHORT_TIME);
			foundation.click(GlobalProduct.SAVE_POPUP_BTN);
			foundation.waitforElement(GlobalProduct.LBL_SCANCODE_MSG, Constants.SHORT_TIME);
			foundation.click(GlobalProduct.SCANCODE);
			actualData = foundation.getText(GlobalProduct.LBL_SCANCODE_MSG);
			CustomisedAssert.assertEquals(actualData, expectedError.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143346-verify when duplicate scancode is entered, it should show \"Scancode already exists\"")
	public void verifyDuplicateScancode() {
		try {
			final String CASE_NUM = "143346";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductData = dataBase.getProductData(Queries.PRODUCT, CASE_NUM);

			List<String> expectedError = Arrays
					.asList(rstProductData.get(CNProduct.SCANCODE_ERROR).split(Constants.DELIMITER_TILD));

			String scancode = expectedError.get(0).replaceAll("[^0-9.]", "");
			String productName = rstProductData.get(CNProduct.PRODUCT_NAME);
			String scanName = rstProductData.get(CNProduct.SCANCODE);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(GlobalProduct.TXT_FILTER, productName);
			foundation.waitforElement(globalProduct.getExistingScancode(scancode), Constants.SHORT_TIME);
			String existingScancode = foundation.getText(globalProduct.getExistingScancode(scancode));
			foundation.click(GlobalProduct.BTN_CREATE);

			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
			textBox.enterText(GlobalProduct.TXT_SCAN_CODE, existingScancode);
			textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
			textBox.enterText(GlobalProduct.LBL_COST, String.valueOf(numbers.generateRandomNumber(0, 9)));

			textBox.enterText(GlobalProduct.LBL_SHORT_NAME, strings.getRandomCharacter());
			foundation.waitforElement(GlobalProduct.LBL_SCANCODE_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(GlobalProduct.LBL_SCANCODE_MSG);

			CustomisedAssert.assertEquals(actualData, expectedError.get(0));
			foundation.click(GlobalProduct.BUTTON_SAVE);
			foundation.waitforElement(GlobalProduct.LBL_ALERT_HEADER, Constants.SHORT_TIME);
			actualData = foundation.getText(GlobalProduct.LBL_ALERT_HEADER);
			CustomisedAssert.assertEquals(actualData, expectedError.get(1));
			actualData = foundation.getText(GlobalProduct.LBL_ALERT_CONTENT);
			CustomisedAssert.assertEquals(actualData, expectedError.get(0));
			foundation.click(GlobalProduct.LBL_ALERT_OK);
			foundation.refreshPage();

			textBox.enterText(GlobalProduct.TXT_SCAN_CODE, scanName);
			foundation.click(GlobalProduct.BUTTON_ADD);
			textBox.enterText(GlobalProduct.TXT_SCAN_CODE_2, scanName);
			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
			textBox.enterText(GlobalProduct.LBL_SHORT_NAME, strings.getRandomCharacter());
			textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
			textBox.enterText(GlobalProduct.LBL_COST, String.valueOf(numbers.generateRandomNumber(0, 9)));
			actualData = foundation.getText(GlobalProduct.TXT_SCAN_CODE_ERROR);

			CustomisedAssert.assertEquals(actualData, expectedError.get(2));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143347-verify when entered product scancode has invalid characters('.','~') with length >25, it should show \"Scancode cannot exceed 25 characters!\" and \"Only letters and numbers allowed.\" error messages")
	public void verifyInvalidScancode() {
		try {
			final String CASE_NUM = "143347";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstProductData = dataBase.getProductData(Queries.PRODUCT, CASE_NUM);

			List<String> expectedError = Arrays
					.asList(rstProductData.get(CNProduct.SCANCODE_ERROR).split(Constants.DELIMITER_TILD));
			String scanCode = rstProductData.get(CNProduct.SCANCODE);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(GlobalProduct.BTN_CREATE);

			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
			textBox.enterText(GlobalProduct.TXT_SCAN_CODE, scanCode);
			textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
			textBox.enterText(GlobalProduct.LBL_SHORT_NAME, strings.getRandomCharacter());
			foundation.waitforElement(GlobalProduct.LBL_SCANCODE_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(GlobalProduct.LBL_SCANCODE_MSG);

			CustomisedAssert.assertEquals(actualData, expectedError.get(1));
			actualData = foundation.getText(GlobalProduct.LBL_SCANCODE_ERROR);
			CustomisedAssert.assertEquals(actualData, expectedError.get(0));

			foundation.click(GlobalProduct.BUTTON_SAVE);
			foundation.waitforElement(GlobalProduct.LBL_ALERT_HEADER, Constants.SHORT_TIME);
			actualData = foundation.getText(GlobalProduct.LBL_ALERT_HEADER);
			CustomisedAssert.assertEquals(actualData, expectedError.get(2));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "167948 - Verify to view the GPC > New Modal for Successful Submission in Global Product Change for Locations"
			+ "167950 - Verify to view the Update Min field value for a product in Global Product Change for Locations"
			+ "167951 - Verify to view the Update Max field value for a product in Global Product Change for Locations"
			+ "167952 - Verify to view the Update Pick list Action field value for a product in Global Product Change for Locations"
			+ "167953 - Verify to view the Update Loyalty Multiplier field value for a product in Global Product Change for Locations")
	public void verifyGPCForLocation() {
		final String CASE_NUM = "167948";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);

		List<String> location = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> price = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_PRICE).split(Constants.DELIMITER_TILD));
		List<String> title = Arrays
				.asList(rstGlobalProductChangeData.get(CNGlobalProductChange.TITLE).split(Constants.DELIMITER_TILD));
		List<String> product = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> pickList = Arrays.asList(rstGlobalProductChangeData.get(CNGlobalProductChange.PICKLIST_DROPDOWN)
				.split(Constants.DELIMITER_TILD));

		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));

			// Select Global Product Change for Locations and select two Locations
			if (!checkBox.isChecked(GlobalProductChange.GPC_CHECK_BOX))
				checkBox.check(GlobalProductChange.GPC_CHECK_BOX);
			foundation.click(globalProductChange.objLocation(location.get(0)));
			foundation.click(globalProductChange.objLocation(location.get(1)));
			foundation.scrollIntoViewElement(GlobalProductChange.BTN_LOCATION_APPLY);
			foundation.click(GlobalProductChange.BTN_LOCATION_APPLY);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_FILTERED_PRODUCTS));

			// Select Product and update Price, Min value, Max value, PickList and Loyalty
			// Multiplier
			textBox.enterText(GlobalProductChange.TXT_PRODUCT_SEARCH, product.get(0));
			foundation.click(globalProductChange.objTableDataProduct(product.get(0)));
			foundation.click(GlobalProductChange.BTN_NEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_PRODUCT_FIELD_CHANGE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_PRICE));
			textBox.enterText(GlobalProductChange.TXT_PRICE, price.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_PRICE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_MIN));
			textBox.enterText(GlobalProductChange.TXT_MIN, price.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_MIN));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_MAX));
			textBox.enterText(GlobalProductChange.TXT_MAX, price.get(3));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_MAX));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_PICK_LIST));
			dropDown.selectItem(GlobalProductChange.DPD_PICK_LIST, pickList.get(0), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_PICK_LIST));
			dropDown.selectItem(GlobalProductChange.DPD_LOYALTY_MULTIPLIER, price.get(0), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_LOYALTY_MULTIPLIER));
			foundation.scrollIntoViewElement(GlobalProductChange.BTN_SUBMIT);
			foundation.click(GlobalProductChange.BTN_SUBMIT);

			// Verify the Popup's
			foundation.waitforElement(GlobalProductChange.BTN_OK, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_OK));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_CANCEL));
			String header = foundation.getText(GlobalProductChange.POP_UP_HEADER);
			CustomisedAssert.assertEquals(header, title.get(0));

			// Verify Cancel Button
			foundation.click(GlobalProductChange.BTN_CANCEL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_PRODUCT_FIELD_CHANGE));

			// Verify OK Button
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.isDisplayed(GlobalProductChange.MSG_SUCCESS);
			foundation.threadWait(Constants.ONE_SECOND);
			header = foundation.getText(GlobalProductChange.REASONBOX_TITLE);
			CustomisedAssert.assertEquals(header, title.get(1));
			header = foundation.getText(GlobalProductChange.REASONBOX_BODY);
			CustomisedAssert.assertEquals(header, title.get(2));
			foundation.click(GlobalProductChange.REASON_BTNOK);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));

			// Navigate to Global Product to check on updated price, Min value, Max value,
			// Picklist and Loyalty multiplier
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
			textBox.enterText(GlobalProduct.TXT_FILTER, product.get(0));
			foundation.click(globalProduct.selectGlobalProduct(product.get(0), product.get(1)));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.scrollIntoViewElement(GlobalProduct.BTN_EXTEND);
			String value = foundation.getText(globalProduct.selectProductPrice(location.get(0)));
			CustomisedAssert.assertEquals(value, price.get(0) + ".00");
			value = foundation.getText(globalProduct.selectProductMin(location.get(0)));
			CustomisedAssert.assertEquals(value, price.get(0));
			value = foundation.getText(globalProduct.selectProductMax(location.get(0)));
			CustomisedAssert.assertEquals(value, price.get(3));
			value = foundation.getText(globalProduct.selectProductPickList(location.get(0)));
			CustomisedAssert.assertEquals(value, pickList.get(0));
			value = foundation.getText(globalProduct.selectProductLoyaltyMultiplier(location.get(0)));
			CustomisedAssert.assertEquals(value, price.get(0));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Product data
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));
			foundation.click(globalProductChange.objLocation(location.get(0)));
			foundation.click(globalProductChange.objLocation(location.get(1)));
			foundation.scrollIntoViewElement(GlobalProductChange.BTN_LOCATION_APPLY);
			foundation.click(GlobalProductChange.BTN_LOCATION_APPLY);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_FILTERED_PRODUCTS));
			textBox.enterText(GlobalProductChange.TXT_PRODUCT_SEARCH, product.get(0));
			foundation.click(globalProductChange.objTableDataProduct(product.get(0)));
			foundation.click(GlobalProductChange.BTN_NEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_PRODUCT_FIELD_CHANGE));
			dropDown.selectItem(GlobalProductChange.DPD_LOYALTY_MULTIPLIER, price.get(5), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_LOYALTY_MULTIPLIER));
			foundation.scrollIntoViewElement(GlobalProductChange.BTN_SUBMIT);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.isDisplayed(GlobalProductChange.MSG_SUCCESS);
			foundation.click(GlobalProductChange.REASON_BTNOK);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));
			navigationBar.navigateToMenuItem(menus.get(2));
			locationList.selectLocationName(location.get(0));
			foundation.scrollIntoViewElement(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			textBox.enterText(LocationSummary.TXT_SEARCH, product.get(0));
			foundation.scrollIntoViewElement(locationSummary.objectProduct(product.get(0)));
			locationSummary.enterPrice(product.get(2), price.get(1));
			locationSummary.enterMinStock(product.get(2), price.get(2));
			locationSummary.enterMaxStock(product.get(2), price.get(4));
			locationSummary.selectPickList(product.get(2), pickList.get(1));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "167949 - Verify to view the GPC > New Modal for Successful Submission in Operator Product Catalog Change")
	public void verifyGPCForProduct() {
		final String CASE_NUM = "167949";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);

		List<String> location = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> price = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_PRICE).split(Constants.DELIMITER_TILD));
		List<String> title = Arrays
				.asList(rstGlobalProductChangeData.get(CNGlobalProductChange.TITLE).split(Constants.DELIMITER_TILD));
		List<String> product = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));

			// Select Global Product Change for Products and select two Locations
			if (!checkBox.isChecked(GlobalProductChange.GPC_CHECK_BOX))
				checkBox.check(GlobalProductChange.GPC_CHECK_BOX);
			foundation.click(globalProductChange.objLocation(location.get(0)));
			foundation.click(globalProductChange.objLocation(location.get(1)));
			foundation.click(GlobalProductChange.TAB_PRODUCT);
			foundation.scrollIntoViewElement(GlobalProductChange.BTN_PRODUCT_APPLY);
			foundation.click(GlobalProductChange.BTN_PRODUCT_APPLY);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_FILTERED_PRODUCTS));

			// Select Product and update Price
			textBox.enterText(GlobalProductChange.TXT_PRODUCT_SEARCH, product.get(0));
			foundation.click(globalProductChange.objTableDataProduct(product.get(0)));
			foundation.click(GlobalProductChange.BTN_NEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_PRODUCT_FIELD_CHANGE));
			textBox.enterText(GlobalProductChange.TXT_PRICE, price.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_PRICE));
			foundation.click(GlobalProductChange.BTN_SUBMIT);

			// Verify the Popup's
			foundation.waitforElement(GlobalProductChange.BTN_OK, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_OK));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_CANCEL));
			String header = foundation.getText(GlobalProductChange.POP_UP_HEADER);
			CustomisedAssert.assertEquals(header, title.get(0));

			// Verify Cancel Button
			foundation.click(GlobalProductChange.BTN_CANCEL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_PRODUCT_FIELD_CHANGE));

			// Verify OK Button
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.isDisplayed(GlobalProductChange.MSG_SUCCESS);
			foundation.threadWait(Constants.ONE_SECOND);
			header = foundation.getText(GlobalProductChange.REASONBOX_TITLE);
			CustomisedAssert.assertEquals(header, title.get(1));
			header = foundation.getText(GlobalProductChange.REASONBOX_BODY);
			CustomisedAssert.assertEquals(header, title.get(2));
			foundation.click(GlobalProductChange.REASON_BTNOK);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));

			// Navigate to Global Product to check on updated price
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
			textBox.enterText(GlobalProduct.TXT_FILTER, product.get(0));
			foundation.click(globalProduct.selectGlobalProduct(product.get(0), product.get(1)));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.scrollIntoViewElement(GlobalProduct.BTN_EXTEND);
			String productPrice = foundation.getText(globalProduct.selectProductPrice(location.get(0)));
			CustomisedAssert.assertEquals(productPrice, price.get(0) + ".00");

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Resetting Product price
			navigationBar.navigateToMenuItem(menus.get(2));
			locationList.selectLocationName(location.get(0));
			foundation.scrollIntoViewElement(LocationSummary.BTN_DEPLOY_DEVICE);
			foundation.click(LocationSummary.TAB_PRODUCTS);
			textBox.enterText(LocationSummary.TXT_SEARCH, product.get(0));
			locationSummary.enterPrice(product.get(2), price.get(1));
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();
			browser.close();
		}
	}

	@Test(description = "167954 - Verify to view the Update Loyalty Multiplier field value for a product in Operator Product Catalog Change"
			+ "167955 - Verify to view the Update Pick list Action field value for a product in Operator Product Catalog Change"
			+ "167956 - Verify to view the Update Min field value for a product in Operator Product Catalog Change"
			+ "167957 - Verify to view the Update Max field value for a product in Operator Product Catalog Change")
	public void verifyOperatorProductCatalogChange() {
		final String CASE_NUM = "167954";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> price = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_PRICE).split(Constants.DELIMITER_TILD));
		List<String> title = Arrays
				.asList(rstGlobalProductChangeData.get(CNGlobalProductChange.TITLE).split(Constants.DELIMITER_TILD));
		List<String> product = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> pickList = Arrays.asList(rstGlobalProductChangeData.get(CNGlobalProductChange.PICKLIST_DROPDOWN)
				.split(Constants.DELIMITER_TILD));

		try {
			// Select Org & Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			navigationBar.navigateToMenuItem(menus.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));

			// Select Global Product Change for Locations and select two Locations
			if (!checkBox.isChecked(GlobalProductChange.OPC_CHECK_BOX))
				checkBox.check(GlobalProductChange.OPC_CHECK_BOX);
			dropDown.selectItem(GlobalProductChange.DPD_FILTER_BY, product.get(1), Constants.TEXT);
			foundation.scrollIntoViewElement(GlobalProductChange.BTN_PRODUCT_APPLY);
			foundation.click(GlobalProductChange.BTN_PRODUCT_APPLY);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_FILTERED_PRODUCTS));

			// Select Product and update Loyalty Multiplier, PickList, Min value, Max value
			textBox.enterText(GlobalProductChange.TXT_PRODUCT_SEARCH, product.get(0));
			foundation.click(globalProductChange.objTableDataOperatorProduct(product.get(0)));
			foundation.click(GlobalProductChange.BTN_NEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_PRODUCT_FIELD_CHANGE));
			CustomisedAssert.assertTrue(foundation.getBGColor(GlobalProductChange.LBL_UPDATE)
					.equals(rstGlobalProductChangeData.get(CNGlobalProductChange.INFO_MESSAGE)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_OPERATOR_MIN));
			textBox.enterText(GlobalProductChange.TXT_MIN, price.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_MIN));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_OPERATOR_MAX));
			textBox.enterText(GlobalProductChange.TXT_MAX, price.get(3));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_MAX));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_OPERATOR_PICK_LIST));
			dropDown.selectItem(GlobalProductChange.DPD_PICK_LIST, pickList.get(0), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_PICK_LIST));
			dropDown.selectItem(GlobalProductChange.DPD_LOYALTY_MULTIPLIER, price.get(0), Constants.TEXT);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.CHK_PRODUCT_LOYALTY_MULTIPLIER));
			foundation.scrollIntoViewElement(GlobalProductChange.BTN_SUBMIT);
			foundation.click(GlobalProductChange.BTN_SUBMIT);

			// Verify the Popup's
			foundation.waitforElement(GlobalProductChange.BTN_OK, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_OK));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_CANCEL));
			String header = foundation.getText(GlobalProductChange.POP_UP_HEADER);
			CustomisedAssert.assertEquals(header, title.get(0));

			// Verify Cancel Button
			foundation.click(GlobalProductChange.BTN_CANCEL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_PRODUCT_FIELD_CHANGE));

			// Verify OK Button
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.isDisplayed(GlobalProductChange.MSG_SUCCESS);
			foundation.threadWait(Constants.ONE_SECOND);
			header = foundation.getText(GlobalProductChange.REASONBOX_TITLE);
			CustomisedAssert.assertEquals(header, title.get(1));
			header = foundation.getText(GlobalProductChange.REASONBOX_BODY);
			CustomisedAssert.assertEquals(header, title.get(2));
			foundation.click(GlobalProductChange.REASON_BTNOK);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.LBL_GPC));

			// Navigate to Global Product to check on Loyalty Multiplier, Picklist, Min
			// value and Max value
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
			textBox.enterText(GlobalProduct.TXT_FILTER, product.get(0));
			foundation.click(globalProduct.selectGlobalProduct(product.get(0), product.get(2)));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.scrollIntoViewElement(GlobalProduct.DPD_LOYALTY_MULTIPLIER);
			String value = foundation.getTextAttribute(GlobalProduct.INPUT_MIN_STOCK, "value");
			CustomisedAssert.assertEquals(value, price.get(0));
			value = foundation.getTextAttribute(GlobalProduct.INPUT_MAX_STOCK, "value");
			CustomisedAssert.assertEquals(value, price.get(3));
			value = dropDown.getSelectedItem(GlobalProduct.DPD_PICK_LIST);
			CustomisedAssert.assertEquals(value, pickList.get(0));
			value = dropDown.getSelectedItem(GlobalProduct.DPD_LOYALTY_MULTIPLIER);
			CustomisedAssert.assertEquals(value, price.get(0));
			foundation.threadWait(Constants.SHORT_TIME);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {

			// Resetting Product data
			foundation.scrollUp(GlobalProduct.DPD_LOYALTY_MULTIPLIER);
			textBox.enterText(GlobalProduct.INPUT_PRICE, price.get(1));
			textBox.enterText(GlobalProduct.INPUT_MIN_STOCK, price.get(2));
			textBox.enterText(GlobalProduct.INPUT_MAX_STOCK, price.get(2));
			dropDown.selectItem(GlobalProduct.DPD_PICK_LIST, pickList.get(1), Constants.TEXT);
			dropDown.selectItem(GlobalProduct.DPD_LOYALTY_MULTIPLIER, price.get(2), Constants.TEXT);
			foundation.scrollIntoViewElement(GlobalProduct.BTN_SAVE);
			foundation.click(GlobalProduct.BTN_SAVE);
			foundation.threadWait(Constants.TWO_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
			login.logout();
			browser.close();
		}
	}

	@Test(description = "168783-Verify to view the GPC remove system limit in Operator Product Catalog Change"
			+ "168782-Verify to view the GPC remove system limit in Global Product Change for Locations")
	public void verifyLimitInGlobalProductChangeforLocation() {
		try {
			final String CASE_NUM = "168783";

			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.GPC_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			// Select Menu Item & verify the select in Global Product Change for Location(s)
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TXT_HEADER));
			if (checkBox.isChecked(GlobalProductChange.GPC_lOCATION)) {
				foundation.click(GlobalProductChange.SELECT_ALL);
				CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.SELECT_COUNT));
				foundation.click(GlobalProductChange.DESELECT_ALL);
			}

			// verify the select in Operator Product Catalog Change
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TXT_HEADER));
			if (checkBox.isChkEnabled(GlobalProductChange.OPS_LOCATION)) {
				checkBox.check(GlobalProductChange.OPS_LOCATION);
				foundation.threadWait(Constants.SHORT_TIME);
				foundation.click(GlobalProductChange.OPS_SELECT_ALL);
				CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.SELECT_COUNT));
				foundation.click(GlobalProductChange.OPS_DESELECT_ALL);
			}

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "167964-Verify to view the Update Global Product Change Messaging in Global Product Change for Location ")
	public void verifyChangeMessagingInGlobalProductChangeforLocation() {

		final String CASE_NUM = "167964";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requireddata = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_PRICE).split(Constants.DELIMITER_TILD));
		List<String> product = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		try {
			// Select Menu Item & verify the select in Global Product Change for Location(s)
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));
			if (checkBox.isChecked(GlobalProductChange.GPC_lOCATION)) {
				foundation.click(globalProductChange
						.objLocation(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME)));
				foundation.click(GlobalProductChange.BTN_LOCATION_APPLY);
				foundation.threadWait(Constants.TWO_SECOND);
				table.selectRow(rstGlobalProductChangeData.get(CNGlobalProductChange.INFO_MESSAGE));
				foundation.click(GlobalProductChange.BTN_NEXT);
			}

			// Navigate to Product Fields to Change to updating the values and verifying the
			// content
			globalProductChange.productFieldChange(requireddata);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TXT_PROMPT_MSG));
			String value = foundation.getText(GlobalProductChange.TXT_PROMPT_CONTENT);
			CustomisedAssert
					.assertTrue(value.contains(rstGlobalProductChangeData.get(CNGlobalProductChange.TOOL_TIP_MESSAGE)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_OK));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_CANCEL));
			foundation.click(GlobalProductChange.BTN_CANCEL);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.REASON_BTNOK);

			// Navigate to Products>>Global products and verify the min price
			navigationBar.navigateToMenuItem(menus.get(1));
			textBox.enterText(GlobalProduct.TXT_FILTER, product.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(globalProduct.selectGlobalProduct(product.get(0), product.get(1)));
			foundation.refreshPage();
			textBox.enterText(ProductSummary.SEARCH_FILTER,
					rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
			foundation.threadWait(Constants.SHORT_TIME);
			Map<Integer, Map<String, String>> uiTableData = productSummary.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			String innerValue = "";
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Min Stock");
				CustomisedAssert.assertEquals(innerValue, requireddata.get(1));
			}
			uiTableData.clear();

			// verify the table record of max price
			uiTableData = productSummary.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Max Stock");
				CustomisedAssert.assertEquals(innerValue, requireddata.get(2));
			}
			uiTableData.clear();

			// verify the table record of Price
			uiTableData = productSummary.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Price");
				CustomisedAssert.assertEquals(innerValue, requireddata.get(0));
			}
			uiTableData.clear();

			// verify the table record of Pick list action
			uiTableData = productSummary.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Pick List Action");
				CustomisedAssert.assertEquals(innerValue, requireddata.get(3));
			}
			uiTableData.clear();

			// verify the table record of Loyalty Multiplier
			uiTableData = productSummary.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Loyalty Multiplier");
				CustomisedAssert.assertEquals(innerValue, requireddata.get(4));
			}
			uiTableData.clear();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "167963-Verify to view the Update Global Product Change Messaging in Operator Product Catalog Change")
	public void verifyChangeMessagingInOperatorProductCatalogChange() {

		final String CASE_NUM = "167963";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);

		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		List<String> requireddata = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_PRICE).split(Constants.DELIMITER_TILD));
		List<String> resetdata = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.INFO_MESSAGE).split(Constants.DELIMITER_TILD));

		try {
			// Select Menu Item & verify the select in Global Product Change for Location(s)
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menus.get(0));

			// verify the select in Operator Product Catalog Change
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TXT_HEADER));
			if (checkBox.isChkEnabled(GlobalProductChange.OPS_LOCATION)) {
				checkBox.check(GlobalProductChange.OPS_LOCATION);
				foundation.threadWait(Constants.SHORT_TIME);
				textBox.enterText(GlobalProductChange.TXT_PRODUCT_NAME,
						rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME));
				foundation.click(GlobalProductChange.BTN_PRODUCT_APPLY);
				foundation.threadWait(Constants.SHORT_TIME);
				table.selectRow(rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME));
				foundation.click(GlobalProductChange.BTN_NEXT);
			}
			// Entering the datas in updated fields
			globalProductChange.productFieldChangeInopc(requireddata);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.PROMT_OPERATOR_PRODUCT));
			String value = foundation.getText(GlobalProductChange.TXT_PROMPT_CONTENT);
			CustomisedAssert
					.assertTrue(value.contains(rstGlobalProductChangeData.get(CNGlobalProductChange.TOOL_TIP_MESSAGE)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_OK));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_CANCEL));
			foundation.click(GlobalProductChange.BTN_CANCEL);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.REASON_BTNOK);

			// verifying the values in Products >> Global products
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProduct.TXT_GLOBAL_PRODUCT));
			textBox.enterText(GlobalProduct.TXT_FILTER, requireddata.get(0));
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(
					globalProduct.getGlobalProduct(rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME)));
			String action = dropDown.getSelectedItem(ProductSummary.DPD_PICKLIST);
			CustomisedAssert.assertEquals(action, requireddata.get(5));
			String display = dropDown.getSelectedItem(ProductSummary.DPD_DISPLAY);
			CustomisedAssert.assertEquals(display, requireddata.get(6));
			String round = dropDown.getSelectedItem(ProductSummary.DPD_ROUNDING);
			CustomisedAssert.assertEquals(round, requireddata.get(7));
			String loyality = dropDown.getSelectedItem(ProductSummary.DPD_LOYALTY_MULTIPLIER);
			CustomisedAssert.assertEquals(loyality, requireddata.get(8));
			String cate1 = dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY1);
			CustomisedAssert.assertEquals(cate1, requireddata.get(9));
			String category2 = dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY2);
			CustomisedAssert.assertEquals(category2, requireddata.get(10));
			String category3 = dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY3);
			CustomisedAssert.assertEquals(category3, requireddata.get(11));
			String taxcate = dropDown.getSelectedItem(ProductSummary.DPD_TAX_CATEGORY);
			CustomisedAssert.assertEquals(taxcate, requireddata.get(12));
			String deposite = dropDown.getSelectedItem(ProductSummary.DPD_DEPOSIT_CATEGORY);
			CustomisedAssert.assertEquals(deposite, requireddata.get(13));
			CustomisedAssert.assertTrue(textBox.getTextFromInput(ProductSummary.MIN_STOCK).equals(requireddata.get(3)));
			CustomisedAssert
					.assertTrue(textBox.getTextFromInput(ProductSummary.PRODUCT_NAME).equals(requireddata.get(0)));
			CustomisedAssert.assertTrue(textBox.getTextFromInput(ProductSummary.MAX_STOCK).equals(requireddata.get(4)));
			CustomisedAssert
					.assertTrue(textBox.getTextFromInput(ProductSummary.PRICE_FIELD).equals(requireddata.get(2)));
			CustomisedAssert
					.assertTrue(textBox.getTextFromInput(ProductSummary.CASE_COUNT).equals(requireddata.get(14)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			navigationBar.navigateToMenuItem(menus.get(0));

			// verify the select in Operator Product Catalog Change
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TXT_HEADER));
			if (checkBox.isChkEnabled(GlobalProductChange.OPS_LOCATION)) {
				checkBox.check(GlobalProductChange.OPS_LOCATION);
				foundation.threadWait(Constants.SHORT_TIME);
				textBox.enterText(GlobalProductChange.TXT_PRODUCT_NAME,
						rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME));
				foundation.click(GlobalProductChange.BTN_PRODUCT_APPLY);
				foundation.threadWait(Constants.TWO_SECOND);
				table.selectRow(rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME));
				foundation.click(GlobalProductChange.BTN_NEXT);
			}

			globalProductChange.productFieldChangeInopc(resetdata);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.REASON_BTNOK);
			foundation.threadWait(Constants.TWO_SECOND);
		}
	}

	@Test(description = "C167960-Verify to view the GPC History Option")
	public void verifyGPCHistoryOption() {

		final String CASE_NUM = "167960";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);

		List<String> requireddata = Arrays
				.asList(rstGlobalProductChangeData.get(CNGlobalProductChange.TITLE).split(Constants.DELIMITER_TILD));
		List<String> griddata = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.SUCCESS_MESSAGE).split(Constants.DELIMITER_TILD));
		List<String> resetdata = Arrays.asList(
				rstGlobalProductChangeData.get(CNGlobalProductChange.COLUMN_NAME).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu Item & verify the select in Global Product Change
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TXT_HEADER));
			checkBox.isChecked(GlobalProductChange.GPC_lOCATION);
			foundation.click(globalProductChange
					.objLocation(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME)));
			foundation.click(GlobalProductChange.BTN_LOCATION_APPLY);
			foundation.threadWait(Constants.TWO_SECOND);
			table.selectRow(rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME));
			foundation.click(GlobalProductChange.BTN_NEXT);

			// Enter the values in field to changes
			globalProductChange.productFieldChange(requireddata);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.REASON_BTNOK);

			// CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.HISTORY_BTN));
			Point coordinates = foundation.getCoordinates(GlobalProductChange.HISTORY_BTN);
			CustomisedAssert.assertEquals(String.valueOf(coordinates.getX()),
					rstGlobalProductChangeData.get(CNGlobalProductChange.INFO_MESSAGE));
			CustomisedAssert.assertEquals(String.valueOf(coordinates.getY()),
					rstGlobalProductChangeData.get(CNGlobalProductChange.TOOL_TIP_MESSAGE));
			foundation.threadWait(Constants.TWO_SECOND);

			// History button and verify the field
			foundation.click(GlobalProductChange.HISTORY_BTN);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.HISTORY_GPC));
			String text = foundation.getText(GlobalProductChange.HEADER_DATA);
			CustomisedAssert.assertEquals(text, rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_PRICE));
			foundation.threadWait(Constants.THREE_SECOND);

			// verify the field
			Map<Integer, Map<String, String>> uiTableData = globalProductChange.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			String innerValue = "";
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("User");
				CustomisedAssert.assertEquals(innerValue, griddata.get(0));
			}
			uiTableData.clear();

			// verify the table record of max price
			uiTableData = globalProductChange.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("# of Products Updated");
				CustomisedAssert.assertEquals(innerValue, griddata.get(1));
			}
			uiTableData.clear();

			// verify the table record of Price
			uiTableData = globalProductChange.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("# of Locations Updated");
				CustomisedAssert.assertEquals(innerValue, griddata.get(2));
			}
			uiTableData.clear();

			// verify the table record of Pick list action
			uiTableData = globalProductChange.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get("Change Type");
				CustomisedAssert.assertEquals(innerValue, griddata.get(3));
			}
			uiTableData.clear();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TXT_HEADER));
			checkBox.isChecked(GlobalProductChange.GPC_lOCATION);
			foundation.click(globalProductChange
					.objLocation(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME)));
			foundation.click(GlobalProductChange.BTN_LOCATION_APPLY);
			foundation.threadWait(Constants.TWO_SECOND);
			table.selectRow(rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME));
			foundation.click(GlobalProductChange.BTN_NEXT);
			globalProductChange.productFieldChange(resetdata);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(GlobalProductChange.REASON_BTNOK);
			foundation.threadWait(Constants.TWO_SECOND);

		}
	}

	@Test(description = "C181457-ADM > Product > Global Products > Products with a double-quote in name are  editable")
	public void verifyProductsWithDoubleQuoteInNameAreEditable() {

		final String CASE_NUM = "181457";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);

		List<String> productName = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

		try {
			// Login to ADM and select org
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Select Menu and search for double-quote "" product
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			globalProduct.searchProductAndUpdateProductNameInGlobalProducts(productName.get(0), productName.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset the data
			globalProduct.searchProductAndUpdateProductNameInGlobalProducts(productName.get(1), productName.get(0));
		}
	}

	@Test(description = "C197140-Verify whether print group is changing to defualt when the product is updated")
	public void verifyWhetherPrintGroupIsChangingToDefualtWhenTheProductIsUpdated() {
		final String CASE_NUM = "197140";

		// Reading test data from DataBas
		rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLocationListData.get(CNLocationList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));
		List<String> price = Arrays
				.asList(rstProductSummaryData.get(CNProductSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		try {

			// Select Menu and Menu Item
			locationList.navigateMenuAndMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM),
					rstLocationListData.get(CNLocationList.LOCATION_NAME));

			// Navigate to product tab and verify print group
			locationSummary.clickOnProductTabAndEnableThePrintGroup(requiredData.get(0));

			// verify the print group field
			Map<Integer, Map<String, String>> uiTableData = locationSummary.getTblRecordsUI();
			Map<String, String> innerMap = new HashMap<>();
			String innerValue = "";
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get(requiredData.get(1));
				CustomisedAssert.assertEquals(innerValue, requiredData.get(2));
			}
			uiTableData.clear();

			// Navigate to product summary and edit the product
			foundation.waitforElementToBeVisible(LocationSummary.TBL_DATA_GRID, Constants.SHORT_TIME);
			foundation.click(locationSummary.objectProduct(requiredData.get(0)));
			foundation.waitforElementToBeVisible(LocationSummary.LBL_PRODUCT_POPUP, Constants.SHORT_TIME);
			locationSummary.clickOnEditProductAfterUpdatingPriceClickOnSave(price.get(0));

			// Navigate to Location and verify the print group after edit
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.threadWait(Constants.ONE_SECOND);
			locationList.selectLocationName(rstLocationListData.get(CNLocationList.LOCATION_NAME));
			locationSummary.clickOnProductTabAndEnableThePrintGroup(requiredData.get(0));

			// verify the print group field
			uiTableData = locationSummary.getTblRecordsUI();
			for (int i = 0; i < uiTableData.size(); i++) {
				innerMap = uiTableData.get(i);
				innerValue = innerMap.get(requiredData.get(1));
				CustomisedAssert.assertEquals(innerValue, requiredData.get(2));
			}
			uiTableData.clear();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// Navigate to product summary and edit the product
			foundation.waitforElementToBeVisible(LocationSummary.TBL_DATA_GRID, Constants.SHORT_TIME);
			foundation.click(locationSummary.objectProduct(requiredData.get(0)));
			foundation.waitforElementToBeVisible(LocationSummary.LBL_PRODUCT_POPUP, Constants.SHORT_TIME);
			locationSummary.clickOnEditProductAfterUpdatingPriceClickOnSave(price.get(1));
		}
	}

	/**
	 * @author afrosean Date: 17-06-2022
	 */
	@Test(description = "C197162-verify searching of products for large number of locations"
			+ "C197161-verify search of a product With Apostrophe in its name")
	public void verifySearchingOfProductsForLargeNoOfLocations() {
		try {
			final String CASE_NUM = "197162";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> requiredData = Arrays.asList(
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu Item & verify the select in Global Product Change for Location(s)
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TXT_HEADER));
			globalProductChange.selectLocationAndClickOnApply(globalProductChange.objLocation(requiredData.get(2)));
			List<String> page = foundation.getTextofListElement(GlobalProductChange.TABLE_RECORD);
			
			//Select multiple location and verify the grid data
			globalProductChange.selectLocationAndClickOnApply(GlobalProductChange.SELECT_ALL);
			List<String> page1 = foundation.getTextofListElement(GlobalProductChange.TABLE_RECORD);
		    CustomisedAssert.assertTrue(page1.size()>page.size());

			// Navigate to product Tab and search for product
			globalProductChange.searchingProductsInProductsFilterAndVerifyTheDatas(requiredData.get(0));
			String data = foundation.getText(GlobalProductChange.TBL_ROW_DATA);
			CustomisedAssert.assertTrue(data.contains(requiredData.get(0)));

			// Navigate to product Tab and search for product With Apostrophe in its name
			globalProductChange.searchingProductsInProductsFilterAndVerifyTheDatas(requiredData.get(1));
		    data=foundation.getText(GlobalProductChange.TBL_ROW_DATA);
			CustomisedAssert.assertTrue(data.contains(requiredData.get(1)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}