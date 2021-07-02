package at.smartshop.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.generic.Strings;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNGlobalProductChange;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.GlobalProductChange;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.NavigationBar;
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

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstGlobalProductChangeData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstLocationListData;

	@Test(description = "This test to Increment Price value for a product in Global Product Change for Location(s)")
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
			assertEquals(priceText, lblPrice.get(0));
			assertEquals(minText, lblPrice.get(1));
			assertEquals(maxText, lblPrice.get(2));

			double Incrementprice = Double
					.parseDouble(rstGlobalProductChangeData.get(CNGlobalProductChange.INCREMENT_PRICE));
			textBox.enterText(GlobalProductChange.TXT_PRICE, Double.toString(Incrementprice));

			foundation.click(GlobalProductChange.BTN_SUBMIT);
			foundation.waitforElement(GlobalProductChange.BTN_OK, 2);
			foundation.click(GlobalProductChange.BTN_OK);
			foundation.isDisplayed(GlobalProductChange.MSG_SUCCESS);

			// Select Menu and Global product
			navigationBar.navigateToMenuItem(menuItem.get(1));

			// Search and select product
			textBox.enterText(LocationList.TXT_FILTER, product);
			foundation.click(globalProduct.getGlobalProduct(product));
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER,
					rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));
			double updatedPrice = price + Incrementprice;

			Map<String, String> updatedProductsRecord = productSummary
					.getProductDetails(rstGlobalProductChangeData.get(CNGlobalProductChange.LOCATION_NAME));

			assertEquals(Double.parseDouble(updatedProductsRecord.get(columnName.get(3))), updatedPrice);

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "This test validates Removed Extended Location")
	public void RemoveLocation() {
		try {
			final String CASE_NUM = "116004";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);

			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Selecting the Product
			foundation.threadWait(2000);
			textBox.enterText(LocationList.TXT_FILTER, rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME));
			foundation.click(locationList.objGlobalProduct(rstLocationSummaryData.get(CNLocationSummary.PRODUCT_NAME)));

			// selecting location
			foundation.click(productSummary.getLocationNamePath(locationName));

			// Remove selected location
			foundation.waitforElement(ProductSummary.BTN_REMOVE, 2);
			foundation.click(ProductSummary.BTN_REMOVE);

			// Validations
			foundation.waitforElement(ProductSummary.TXT_SPINNER_MSG, 3);
			assertTrue(foundation.getSizeofListElement(productSummary.getLocationNamePath(locationName)) == 0);

			// resetting test data
			foundation.waitforElement(ProductSummary.BTN_EXTEND, 2);
			foundation.click(ProductSummary.BTN_EXTEND);
			textBox.enterText(ProductSummary.TXT_FILTER, locationName);
			table.selectRow(Constants.PRODUCT_DATAGRID, locationName);

			foundation.click(ProductSummary.BTN_MODAL_SAVE);
			assertTrue(foundation.getSizeofListElement(productSummary.getLocationNamePath(locationName)) == 1);

		} catch (Exception exc) {
			Assert.fail();
		}
	}

	@Test(description = "Select AVIFoodSystems org and Verify Global Products exported file is downloaded.")
	public void productExport() {
		try {
			final String CASE_NUM = "142865";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.AVI_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.threadWait(Constants.LONG_TIME);

			foundation.click(GlobalProduct.BTN_EXPORT);
			// download assertion
			Assert.assertTrue(excel.isFileDownloaded("C:\\Users\\ajaybabur\\Downloads\\products.xlsx"));
			Map<String, String> uiData = table.getTblHeadersUI(GlobalProduct.TBL_GRID);
			List<String> uiList = new ArrayList<String>(uiData.values());
			// excel headers validation
			Assert.assertTrue(excel.verifyExcelData(uiList, "C:\\Users\\ajaybabur\\Downloads\\products.xlsx", 0));

			File productsfile = new File("C:\\Users\\ajaybabur\\Downloads\\products.xlsx");
			productsfile.delete();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "Select AVIFoodSystems org and Verify Global Products exported file  record count should match with ui.")
	public void verifyRecordCount() {
		try {
			final String CASE_NUM = "142866";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.AVI_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.threadWait(Constants.LONG_TIME);
			String[] uiData = (foundation.getText(GlobalProduct.TXT_RECORD_COUNT)).split(" ");
			foundation.click(GlobalProduct.BTN_EXPORT);
			// download assertion
			Assert.assertTrue(excel.isFileDownloaded("C:\\Users\\ajaybabur\\Downloads\\products.xlsx"));
			// record count validation
			int excelCount = excel.getExcelRowCount("C:\\Users\\ajaybabur\\Downloads\\products.xlsx");
			Assert.assertEquals(String.valueOf(excelCount), uiData[0]);
			File productsfile = new File("C:\\Users\\ajaybabur\\Downloads\\products.xlsx");
			productsfile.delete();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "Select AVIFoodSystems org and Verify Global Products exported file doesnot contains any records when filtered product is not available")
	public void zeroRecords() {
		try {
			final String CASE_NUM = "142868";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName = strings.getRandomCharacter();
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.AVI_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, productName);
			foundation.threadWait(Constants.SHORT_TIME);
			String[] uiData = (foundation.getText(GlobalProduct.TXT_RECORD_COUNT)).split(" ");

			foundation.click(GlobalProduct.BTN_EXPORT);
			// download assertion
			System.out.println(FilePath.EXCEL_PROD_SRC);
			System.out.println(FilePath.EXCEL_PROD_TAR);

			// File file = new File(home+"/Downloads/" + fileName + ".txt");
			Assert.assertTrue(excel.isFileDownloaded(FilePath.EXCEL_PROD_SRC));
			excel.copyFile(FilePath.EXCEL_PROD_SRC, FilePath.EXCEL_PROD_TAR);
			int excelCount = excel.getExcelRowCount(FilePath.EXCEL_PROD_TAR);
			// record count validation
			Assert.assertEquals(String.valueOf(excelCount), uiData[0]);
			File productsfile = new File(FilePath.EXCEL_PROD_TAR);
			productsfile.delete();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

	@Test(description = "Select AVIFoodSystems org and Verify Global Products exported file records are matching as per the filter applied in ui.Select AVIFoodSystems org and Verify Global Products exported file records are matching as per the filter applied in ui.")
	public void verifyExportData() {
		try {
			final String CASE_NUM = "142867";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstGlobalProductChangeData = dataBase.getGlobalProductChangeData(Queries.GLOBAL_PRODUCT_CHANGE, CASE_NUM);
			String product = rstGlobalProductChangeData.get(CNGlobalProductChange.PRODUCT_NAME);
			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.AVI_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.threadWait(Constants.MEDIUM_TIME);
			textBox.enterText(GlobalProduct.TXT_FILTER, product);
			foundation.threadWait(Constants.SHORT_TIME);
			String[] uiData = (foundation.getText(GlobalProduct.TXT_RECORD_COUNT)).split(" ");

			foundation.click(GlobalProduct.BTN_EXPORT);
			foundation.threadWait(Constants.SHORT_TIME);
			// download assertion
			Assert.assertTrue(excel.isFileDownloaded("C:\\Users\\ajaybabur\\Downloads\\products.xlsx"));
			int excelCount = excel.getExcelRowCount("C:\\Users\\ajaybabur\\Downloads\\products.xlsx");
			// record count validation
			Assert.assertEquals(String.valueOf(excelCount), uiData[0]);
			Map<String, String> uidata = table.getTblSingleRowRecordUI(GlobalProduct.TBL_GRID, GlobalProduct.TBL_ROW);
			List<String> uiList = new ArrayList<String>(uidata.values());
			// excel data validation
			Assert.assertTrue(excel.verifyExcelData(uiList, "C:\\Users\\ajaybabur\\Downloads\\products.xlsx", 1));

			File productsfile = new File("C:\\Users\\ajaybabur\\Downloads\\products.xlsx");
			productsfile.delete();

		} catch (Exception exc) {
			Assert.fail();
		}

	}

}
