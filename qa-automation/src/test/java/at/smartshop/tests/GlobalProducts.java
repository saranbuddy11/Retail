package at.smartshop.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstGlobalProductChangeData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstLocationListData;
	private Map<String, String> rstNationalAccountData;

	private Map<String, String> rstProductData;
	private Map<String, String> rstOrgSummaryData;

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

			// CustomisedAssert.assertEquals(Double.parseDouble(updatedProductsRecord.get(columnName.get(3))),
			// updatedPrice);

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
			String[] uiData = (foundation.getText(GlobalProduct.TXT_RECORD_COUNT)).split(" ");
			foundation.click(GlobalProduct.BTN_EXPORT);
			// download assertion
			CustomisedAssert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_PROD_SRC));
			foundation.copyFile(FilePath.EXCEL_PROD_SRC, FilePath.EXCEL_PROD_TAR);
			// record count validation
			/*
			 * int excelCount = excel.getExcelRowCount(FilePath.EXCEL_PROD_TAR);
			 * CustomisedAssert.assertEquals(String.valueOf(excelCount), uiData[0]);
			 */

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
			foundation.click(GlobalProduct.BTN_CREATE);

			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());

			textBox.enterText(GlobalProduct.TXT_PRICE, String.valueOf(numbers.generateRandomNumber(0, 9)));
			textBox.enterText(GlobalProduct.LBL_COST, String.valueOf(numbers.generateRandomNumber(0, 9)));
			textBox.enterText(GlobalProduct.TXT_PRODUCTNAME, strings.getRandomCharacter());
			foundation.click(GlobalProduct.BUTTON_SAVE);
			foundation.waitforElement(GlobalProduct.LBL_SAVE_DONE, Constants.TWO_SECOND);
			foundation.click(GlobalProduct.LBL_SAVE_DONE);
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

	@Test(description = "168783-Verify to view the GPC remove system limit in Operator Product Catalog Change "
			+ "168782-Verify to view the GPC remove system limit in Global Product Change for Location(s)")
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

	@Test(description = "167964-Verify to view the Update Global Product Change Messaging in Global Product Change for Location(S)e ")

	public void verifyChangeMessagingInGlobalProductChangeforLocation() {
		try {
			final String CASE_NUM = "167964";

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);

			List<String> menus = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> requireddata = Arrays.asList(rstGlobalProductChangeData
					.get(CNGlobalProductChange.INCREMENT_PRICE).split(Constants.DELIMITER_TILD));
			List<String> promptmsg = Arrays.asList(rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_PRICE)
					.split(Constants.DELIMITER_TILD));

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

			// Navigate to Product Fields to Change to updating the values and verifying the content 
			globalProductChange.productFieldChange(requireddata);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.TXT_PROMPT_MSG));
			String value = foundation.getText(GlobalProductChange.TXT_PROMPT_CONTENT);
			CustomisedAssert.assertTrue(value.contains(rstGlobalProductChangeData.get(CNGlobalProductChange.TOOL_TIP_MESSAGE)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_OK));
			CustomisedAssert.assertTrue(foundation.isDisplayed(GlobalProductChange.BTN_CANCEL));
			foundation.click(GlobalProductChange.BTN_CANCEL);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.click(GlobalProductChange.BTN_OK);
			
			//Navigate to Products>>Global products
			navigationBar.navigateToMenuItem(menus.get(1));
			textBox.enterText(GlobalProduct.TXT_FILTER, rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME));
			foundation.refreshPage();
			
			
			
			

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
