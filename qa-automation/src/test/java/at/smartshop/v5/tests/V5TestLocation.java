package at.smartshop.v5.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumer;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CategoryList;
import at.smartshop.pages.CategorySummary;
import at.smartshop.pages.ConsumerSearch;
import at.smartshop.pages.ConsumerSummary;
import at.smartshop.pages.GlobalProduct;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.ProductSummary;
import at.smartshop.tests.TestInfra;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5TestLocation extends TestInfra {

	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private LocationList locationList = new LocationList();
	private LocationSummary locationSummary = new LocationSummary();
	private Dropdown dropdown = new Dropdown();
	private Dropdown dropDown = new Dropdown();
	private Strings string = new Strings();
	private CategoryList categoryList = new CategoryList();
	private CategorySummary categorySummary = new CategorySummary();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private ConsumerSummary consumerSummary = new ConsumerSummary();
	private Numbers numbers = new Numbers();

	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstConsumerSummaryData;
	private Map<String, String> rstConsumerData;

	@Test(description = "142720-SOS-24493-Verify error message is displayed when home commercial image is uploaded in JSON format")
	public void verifyHomeCommercialJSON() {
		try {
			final String CASE_NUM = "142720";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String actualData = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);

			// Selecting location
			locationList.selectLocationName(locationName);

			// upload image-1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.JSON_SALES_CREATION);
			foundation.waitforElement(LocationSummary.TXT_UPLOAD_STATUS, Constants.SHORT_TIME);
			String expectedData = foundation.getText(LocationSummary.TXT_UPLOAD_STATUS);
			CustomisedAssert.assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142723-SOS-24493-Verify error message is displayed when home commercial image is uploaded in Text format")
	public void verifyHomeCommercialText() {
		try {
			final String CASE_NUM = "142723";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String actualData = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);

			// Selecting location
			textBox.enterText(LocationList.TXT_FILTER, locationName);
			locationList.selectLocationName(locationName);

			// upload image-1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_TEXT_PATH);
			foundation.waitforElement(locationSummary.objUploadStatus(actualData), Constants.SHORT_TIME);
			String expectedData = foundation.getText(LocationSummary.TXT_UPLOAD_STATUS);
			CustomisedAssert.assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142724-SOS-24493-Verify error message is displayed when uploaded home commercial image size is more than 2MB")
	public void verifyHomeCommercialImageSize() {
		try {
			final String CASE_NUM = "142724";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String actualData = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);

			// Selecting location
			textBox.enterText(LocationList.TXT_FILTER, locationName);
			locationList.selectLocationName(locationName);

			// upload image-1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_SIZE_MORE);

			// foundation.waitforElement(locationSummary.objUploadStatus(actualData),
			// Constants.SHORT_TIME);
			String expectedData = foundation.getAlertMessage();
			;
			CustomisedAssert.assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "142725-SOS-24493-Verify error message is displayed when uploaded home commercial image pixel length is greater than 1024x520")
	public void verifyHomeCommercialPixelLength() {
		try {
			final String CASE_NUM = "142725";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

			String locationName = propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1,
					FilePath.PROPERTY_CONFIG_FILE);
			String actualData = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);

			// Selecting location
			textBox.enterText(LocationList.TXT_FILTER, locationName);
			locationList.selectLocationName(locationName);

			// upload image-1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_SIZE_MORE);

			foundation.waitforElement(locationSummary.objUploadStatus(actualData), Constants.SHORT_TIME);
			// String expectedData = foundation.getText(LocationSummary.TXT_UPLOAD_STATUS);
			// CustomisedAssert.assertEquals(expectedData, actualData);
			String expectedData = foundation.getAlertMessage();
			// foundation.waitforElement(locationSummary.objUploadStatus(actualData),
			// Constants.SHORT_TIME);
			// String expectedData = foundation.getText(LocationSummary.TXT_UPLOAD_STATUS);
			CustomisedAssert.assertEquals(expectedData, actualData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143112-Edit existing tax category name and verify edits applied to product or not on product summary page")
	public void editTaxCategory() {
		final String CASE_NUM = "143112";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_TAX_CATEGORY),
					requiredData.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143114-QA-19-Edit existing category name and verify edits applied to product or not on product summary page")
	public void editCategories() {
		final String CASE_NUM = "143114";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// update tax categories
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(3));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(4));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredData.get(2));
			categorySummary.updateName(requiredData.get(5));

			// verify edits applied to product or not
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY1), requiredData.get(3));
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY2), requiredData.get(4));
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY3), requiredData.get(5));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredData.get(6), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredData.get(7), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredData.get(8), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(3));
			categorySummary.updateName(requiredData.get(0));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredData.get(4));
			categorySummary.updateName(requiredData.get(1));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredData.get(5));
			categorySummary.updateName(requiredData.get(2));
		}
	}

	@Test(description = "143113-Edit existing deposit category name and verify edits applied to product or not on product summary page")
	public void editDepositCategory() {
		final String CASE_NUM = "143113";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_DEPOSIT_CATEGORY),
					requiredData.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143115-Edit existing INVREASON category name and verify edits applied to product or not on product summary page")
	public void editInvReason() {
		final String CASE_NUM = "143115";
		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// edit invReason code and verify the edits on product page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER,
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(ProductSummary.LBL_REASON_CODE);
			List<String> listReasonCode = dropDown.getAllItems(ProductSummary.DPD_REASON_CODE);
			CustomisedAssert.assertTrue(listReasonCode.contains(requiredData.get(1)));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
			foundation.waitforElement(CategoryList.TXT_SEARCH_CATEGORY, Constants.EXTRA_LONG_TIME);
		}
	}

	@Test(description = "142907-QAA-44-verify daily revenue on location page")
	public void verifyDailyRevenue() {
		try {
			final String CASE_NUM = "142907";
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// verify daily revenue
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			textBox.enterText(LocationList.TXT_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			String dailyRevenue = foundation.getText(locationList.objDailyRevenue(
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE)));
			CustomisedAssert.assertNotEquals(dailyRevenue, requiredData.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143117-QAA19-Edit existing tax category name and verify edits applied to product or not on location summary page")
	public void editTaxCategoryLocation() {
		final String CASE_NUM = "143117";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));

			// verify tax edits on location summary page
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(requiredData.get(2));
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			locationSummary.showHideManageColumn(requiredData.get(3), requiredData.get(4));
			foundation.click(LocationSummary.BTN_APPLY);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, productName);
			CustomisedAssert.assertEquals(locationSummary.getCellData(requiredData.get(5)), requiredData.get(1));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(6), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143118-Edit existing deposit category name and verify edits applied to product or not on location summary page")
	public void editDepositCategoryLocation() {
		final String CASE_NUM = "143118";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(0), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit deposit category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));

			// verify deposit category edits on location summary page
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(requiredData.get(2));
			foundation.click(LocationSummary.TAB_PRODUCTS);
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			locationSummary.showHideManageColumn(requiredData.get(3), requiredData.get(4));
			foundation.click(LocationSummary.BTN_APPLY);
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, productName);
			CustomisedAssert.assertEquals(locationSummary.getCellData(requiredData.get(5)), requiredData.get(1));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(6), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143119-QA-19-Edit existing category name and verify edits applied to product or not on location summary page")
	public void editCategoriesLocation() {
		final String CASE_NUM = "143119";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredDataV5Device = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		try {
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);
			List<String> requiredDataLocationSummary = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredDataV5Device.get(0), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredDataV5Device.get(1), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredDataV5Device.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// update tax categories
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredDataV5Device.get(0));
			categorySummary.updateName(requiredDataV5Device.get(3));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredDataV5Device.get(1));
			categorySummary.updateName(requiredDataV5Device.get(4));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredDataV5Device.get(2));
			categorySummary.updateName(requiredDataV5Device.get(5));

			// -verify categories edits applied on location summary page-
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.TAB_PRODUCTS);
			// select show categories
			foundation.click(LocationSummary.BTN_MANAGE_COLUMNS);
			foundation.threadWait(Constants.ONE_SECOND);
			locationSummary.showHideManageColumn(requiredDataLocationSummary.get(0),
					requiredDataLocationSummary.get(1));
			locationSummary.showHideManageColumn(requiredDataLocationSummary.get(0),
					requiredDataLocationSummary.get(2));
			locationSummary.showHideManageColumn(requiredDataLocationSummary.get(0),
					requiredDataLocationSummary.get(3));
			foundation.click(LocationSummary.BTN_APPLY);
			// verify edits
			textBox.enterText(LocationSummary.TXT_PRODUCT_FILTER, productName);
			CustomisedAssert.assertEquals(locationSummary.getCellData(requiredDataLocationSummary.get(4)),
					requiredDataV5Device.get(3));
			CustomisedAssert.assertEquals(locationSummary.getCellData(requiredDataLocationSummary.get(5)),
					requiredDataV5Device.get(4));
			CustomisedAssert.assertEquals(locationSummary.getCellData(requiredDataLocationSummary.get(6)),
					requiredDataV5Device.get(5));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// -reset data-
			// reset categories on products
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredDataV5Device.get(6), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredDataV5Device.get(6), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredDataV5Device.get(6), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			// reset categories names
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredDataV5Device.get(3));
			categorySummary.updateName(requiredDataV5Device.get(0));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredDataV5Device.get(4));
			categorySummary.updateName(requiredDataV5Device.get(1));
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(requiredDataV5Device.get(5));
			categorySummary.updateName(requiredDataV5Device.get(2));
		}
	}

	@Test(description = "143120-Edit existing INVREASON category name and verify edits applied to product or not on location summary page")
	public void editInvReasonLocation() {
		final String CASE_NUM = "143120";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// edit invReason code and verify the edits on product page
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));

			// -verify categories edits applied on location summary page-
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LocationSummary.LNK_INVENTORY);
			foundation.threadWait(Constants.TWO_SECOND);
			textBox.enterText(LocationSummary.TXT_INVENTORY_FILTER, productName);
			foundation.click(LocationSummary.LBL_REASON_CODE);
			List<String> listReasonCode = foundation.getAttributeValueofListElement(LocationSummary.LIST_REASON_CODE,
					Constants.INNER_TEXT);
			CustomisedAssert.assertTrue(listReasonCode.contains(requiredData.get(1)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143123-Add new tax category and Edit it's name and verify edits applied to product or not on product summary page")
	public void addEditTaxCategory() {
		try {
			final String CASE_NUM = "143123";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			foundation.threadWait(Constants.THREE_SECOND);
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add tax category
			String newTaxCat = string.getRandomCharacter().toUpperCase();
			String editedTaxCat = requiredData.get(1) + newTaxCat;
			categorySummary.addCategory(newTaxCat, requiredData.get(3));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newTaxCat));

			foundation.threadWait(Constants.TWO_SECOND);
			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, newTaxCat, Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newTaxCat);
			categorySummary.updateName(editedTaxCat);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.scrollIntoViewElement(ProductSummary.DPD_TAX_CATEGORY);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_TAX_CATEGORY), editedTaxCat);

			// reset data
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedTaxCat);
			categorySummary.updateName(newTaxCat);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143124-Add new deposit category and Edit it's name and verify edits applied to product or not")
	public void addEditDepositCategory() {
		try {
			final String CASE_NUM = "143124";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to category page
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add deposit category
			String newDepositCat = string.getRandomCharacter().toUpperCase();
			String editedDepositCat = requiredData.get(1) + newDepositCat;
			categorySummary.addCategory(newDepositCat, requiredData.get(3));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newDepositCat));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			textBox.enterText(ProductSummary.TXT_PRICE, "5");
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, newDepositCat, Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newDepositCat);
			categorySummary.updateName(editedDepositCat);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_DEPOSIT_CATEGORY),
					editedDepositCat);

			// reset data
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedDepositCat);
			categorySummary.updateName(newDepositCat);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143125-QA-19-Add new category and Edit it's name and verify edits applied to product or not")
	public void addeditCategories() {
		try {
			final String CASE_NUM = "143125";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(1));

			// add categories
			String newCategory1 = string.getRandomCharacter().toUpperCase();
			String newCategory2 = string.getRandomCharacter().toUpperCase();
			String newCategory3 = string.getRandomCharacter().toUpperCase();
			String editedCategory1 = requiredData.get(0) + newCategory1;
			String editedCategory2 = requiredData.get(0) + newCategory2;
			String editedCategory3 = requiredData.get(0) + newCategory3;
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newCategory1, requiredData.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newCategory2, requiredData.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newCategory3, requiredData.get(1));

			// add categories to products
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, newCategory1, Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, newCategory2, Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, newCategory3, Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// update tax categories
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newCategory1);
			categorySummary.updateName(editedCategory1);
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(newCategory2);
			categorySummary.updateName(editedCategory2);
			foundation.threadWait(Constants.TWO_SECOND);
			categoryList.selectCategory(newCategory3);
			categorySummary.updateName(editedCategory3);

			// verify edits applied to product or not
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY1), editedCategory1);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY2), editedCategory2);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_CATEGORY3), editedCategory3);

			// reset data
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredData.get(2), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredData.get(2), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143126-Add new INVREASON category and Edit it's name and verify edits applied to product or not")
	public void addEditInvReason() {
		try {
			final String CASE_NUM = "143126";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to category page
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add deposit category
			String newInvReason = string.getRandomCharacter().toUpperCase();
			String editedInvReason = requiredData.get(0) + newInvReason;
			categorySummary.addCategory(newInvReason, requiredData.get(1));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newInvReason));

			// edit invReason code and verify the edits on product page
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newInvReason);
			categorySummary.updateName(editedInvReason);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			textBox.enterText(ProductSummary.TXT_LOCATION_SEARCH_FILTER,
					propertyFile.readPropertyFile(Configuration.AUTOMATIONLOCATION1, FilePath.PROPERTY_CONFIG_FILE));
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(ProductSummary.LBL_REASON_CODE);
			List<String> listReasonCode = dropDown.getAllItems(ProductSummary.DPD_REASON_CODE);
			CustomisedAssert.assertTrue(listReasonCode.contains(editedInvReason));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143122-QAA19-Edit existing tax category and verify edits applied to product or not on location page- tax mapping tab")
	public void editTaxCategoryTaxMapping() {
		final String CASE_NUM = "143122";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		// String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// verify tax edits on location summary page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationList.selectLocationName(requiredData.get(0));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			dropdown.selectItem(LocationSummary.DPD_TAX_CATEGORY, requiredData.get(1), Constants.TEXT);
			dropdown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(5), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE_MAPPING);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(2));
			categoryList.selectCategory(requiredData.get(3));
			categorySummary.updateName(requiredData.get(4));

			// verify tax edits on location summary page- tax mapping tab- for saved
			// category
			navigationBar.navigateToMenuItem(menuItem.get(0));
			locationList.selectLocationName(requiredData.get(0));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			textBox.enterText(LocationSummary.TXT_SEARCH_TAX_MAPPING, requiredData.get(2));
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objTaxCategory(requiredData.get(2))));
			// reset data- remove added mapping
			foundation.click(locationSummary.objTaxCategory(requiredData.get(2)));
			foundation.click(LocationSummary.BTN_REMOVE_MAPPING);

			// verify tax edits on location summary page- tax mapping tab- on tax category
			// list
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			List<String> listTaxCategory = dropDown.getAllItems(LocationSummary.DPD_TAX_CATEGORY);
			CustomisedAssert.assertTrue(listTaxCategory.contains(requiredData.get(4)));
			foundation.click(LocationSummary.BTN_CANCEL_MAPPING);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(requiredData.get(2));
			categorySummary.updateName(requiredData.get(1));
			categoryList.selectCategory(requiredData.get(4));
			categorySummary.updateName(requiredData.get(3));
		}
	}

	@Test(description = "143128-QAA19-Add new tax category and Edit it's category name and verify edits applied to product or not on location page - tax mapping tab")

	public void addEditTaxCategoryTaxMapping() {
		try {
			final String CASE_NUM = "143128";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// add tax categories
			navigationBar.navigateToMenuItem(menuItem.get(1));
			String newTaxCategory1 = string.getRandomCharacter().toUpperCase();
			String newTaxCategory2 = string.getRandomCharacter().toUpperCase();
			String editedTaxCategory1 = requiredData.get(2) + newTaxCategory1;
			String editedTaxCategory2 = requiredData.get(2) + newTaxCategory2;
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newTaxCategory1, requiredData.get(4));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newTaxCategory2, requiredData.get(4));

			// save tax mapping on location summary page as precondition
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			dropdown.selectItem(LocationSummary.DPD_TAX_CATEGORY, newTaxCategory1, Constants.TEXT);
			dropdown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE_MAPPING);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newTaxCategory1);
			categorySummary.updateName(editedTaxCategory1);
			categoryList.selectCategory(newTaxCategory2);
			categorySummary.updateName(editedTaxCategory2);

			// verify tax edits on location summary page- tax mapping tab- for saved
			// category
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			textBox.enterText(LocationSummary.TXT_SEARCH_TAX_MAPPING, editedTaxCategory1);
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objTaxCategory(editedTaxCategory1)));
			// reset data- remove added mapping
			foundation.click(locationSummary.objTaxCategory(editedTaxCategory1));
			foundation.click(LocationSummary.BTN_REMOVE_MAPPING);

			// verify tax edits on location summary page- tax mapping tab- on tax category
			// list
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			List<String> listTaxCategory = dropDown.getAllItems(LocationSummary.DPD_TAX_CATEGORY);
			CustomisedAssert.assertTrue(listTaxCategory.contains(editedTaxCategory2));
			foundation.click(LocationSummary.BTN_CANCEL_MAPPING);

			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedTaxCategory1);
			categorySummary.updateName(newTaxCategory1);
			categoryList.selectCategory(editedTaxCategory2);
			categorySummary.updateName(newTaxCategory2);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143116-QAA-19-Edit existing BALREASON category name and verify edits applied on consumer page or not")
	public void editBalReason() {
		final String CASE_NUM = "143116";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
		rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String firstName = rstConsumerSummaryData.get(CNConsumerSummary.FIRST_NAME);
		// String columnName = rstConsumerSearchData.get(CNConsumerSearch.COLUMN_NAME);
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(0));
			categoryList.selectCategory(requiredData.get(0));
			categorySummary.updateName(requiredData.get(1));

			// navigate to consumer summary page and verify edits applied ot not for balance
			// reason
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// clicking consumer id
			foundation.click(consumerSearch.objCell(firstName));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// Map<String, String> consumerTblRecords =
			// consumerSearch.getConsumerRecords(rstConsumerSearchData.get(CNConsumerSearch.LOCATION));
			String balance = textBox.getTextFromInput(ConsumerSummary.TXT_ADJUST_BALANCE);
			// String balance1 = balance.substring(1);
			Double newBalance = Double.parseDouble(balance) + 2;
			// String expectedBalance = "$" + String.valueOf(newBalance);

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, String.valueOf(newBalance));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, requiredData.get(1), Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.refreshPage();
			textBox.enterText(ConsumerSummary.TXT_SEARCH_ACCOUNT_ADJUSTMENT, requiredData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(consumerSummary.objTaxCategory(requiredData.get(1))));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			categoryList.selectCategory(requiredData.get(1));
			categorySummary.updateName(requiredData.get(0));
		}
	}

	@Test(description = "143129-QAA-19-Add new BALREASON category and Edit it's name and verify edits applied on consumer page or not")
	public void addEditBalReason() {
		try {
			final String CASE_NUM = "143129";

			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
			String firstName = rstConsumerSummaryData.get(CNConsumerSummary.FIRST_NAME);
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to new category page
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add balance category
			String newBalCat = string.getRandomCharacter().toUpperCase();
			String editedBalCat = requiredData.get(0) + newBalCat;
			categorySummary.addCategory(newBalCat, requiredData.get(1));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newBalCat));

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(0));
			categoryList.selectCategory(newBalCat);
			categorySummary.updateName(editedBalCat);

			// navigate to consumer summary page and verify edits applied ot not for balance
			// reason
			navigationBar.navigateToMenuItem(menuItem.get(1));
			consumerSearch.enterSearchFields(rstConsumerSearchData.get(CNConsumerSearch.SEARCH_BY),
					rstConsumerSearchData.get(CNConsumerSearch.CONSUMER_ID),
					rstConsumerSearchData.get(CNConsumerSearch.LOCATION),
					rstConsumerSearchData.get(CNConsumerSearch.STATUS));

			// clicking consumer id
			foundation.click(consumerSearch.objCell(firstName));
			foundation.click(ConsumerSearch.BTN_ADJUST);

			// Map<String, String> consumerTblRecords =
			// consumerSearch.getConsumerRecords(rstConsumerSearchData.get(CNConsumerSearch.LOCATION));
			String balance = textBox.getTextFromInput(ConsumerSummary.TXT_ADJUST_BALANCE);
			Double newBalance = Double.parseDouble(balance) + 2;

			// enter new balance with reason
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, String.valueOf(newBalance));
			dropDown.selectItem(ConsumerSummary.DPD_REASON, editedBalCat, Constants.TEXT);
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.refreshPage();
//			textBox.enterText(ConsumerSummary.TXT_SEARCH_ACCOUNT_ADJUSTMENT, editedBalCat);
//			CustomisedAssert.assertTrue(foundation.isDisplayed(consumerSummary.objTaxCategory(editedBalCat)));

			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			categoryList.selectCategory(editedBalCat);
			categorySummary.updateName(newBalCat);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "145706-QAA-227-verify adding negative balance for active account and Close the account.")
	public void verifyNegativeBalClosedAccount() {
		try {
			final String CASE_NUM = "145706";
// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstConsumerData = dataBase.getConsumerData(Queries.CONSUMER, CASE_NUM);
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> balance = Arrays
					.asList(rstConsumerData.get(CNConsumerSummary.ADJUST_BALANCE).split(Constants.DELIMITER_TILD));
			String location = rstConsumerData.get(CNConsumerSearch.LOCATION);
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> status = Arrays
					.asList(rstConsumerData.get(CNConsumerSearch.STATUS).split(Constants.DELIMITER_TILD));
			List<String> searchBy = Arrays
					.asList(rstConsumerData.get(CNConsumerSearch.SEARCH_BY).split(Constants.DELIMITER_TILD));
			String eMail = string.getRandomCharacter() + rstConsumerData.get(CNConsumer.EMAIL);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.click(ConsumerSearch.BTN_CREATE);
			dropDown.selectItem(ConsumerSummary.DPD_LOCATION, rstConsumerData.get(CNConsumer.LOCATION), Constants.TEXT);
			textBox.enterText(ConsumerSummary.TXT_FIRSTNAME, Constants.AUTOMATION + string.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_LASTNAME, Constants.AUTOMATION + string.getRandomCharacter());
			textBox.enterText(ConsumerSummary.TXT_EMAIL, eMail);
			textBox.enterText(ConsumerSummary.TXT_SCANID, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			textBox.enterText(ConsumerSummary.TXT_PIN, rstConsumerData.get(CNConsumer.PIN));
			textBox.enterText(ConsumerSummary.TXT_PHONE, rstConsumerData.get(CNConsumer.PHONE));
			foundation.click(ConsumerSummary.BTN_CREATE);
			foundation.waitforElement(ConsumerSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(ConsumerSummary.BTN_SAVE);

			// Enter fields in Consumer Search Page

			foundation.threadWait(Constants.ONE_SECOND);
			consumerSearch.enterSearchFields(searchBy.get(0), eMail, location, status.get(0));
			consumerSearch.objLocation(rstConsumerData.get(CNConsumerSearch.LOCATION));
			foundation.click(ConsumerSearch.BTN_ACTIONS);
			foundation.click(ConsumerSearch.LBL_BULK_ADD_FUNDS);
			foundation.waitforElement(ConsumerSummary.TXT_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, balance.get(0));
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSearch.BTN_OK, Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.BTN_OK);
			foundation.waitforElementToDisappear(ConsumerSearch.BTN_OK, Constants.SHORT_TIME);
			// Negative Balance
			consumerSearch.enterSearchFields(searchBy.get(0), eMail, location, status.get(0));
			consumerSearch.objLocation(rstConsumerData.get(CNConsumerSearch.LOCATION));
			foundation.click(ConsumerSearch.BTN_ACTIONS);
			foundation.click(ConsumerSearch.LBL_BULK_REMOVE_FUNDS);
			foundation.waitforElement(ConsumerSummary.TXT_ADJUST_BALANCE, Constants.SHORT_TIME);
			textBox.enterText(ConsumerSummary.TXT_ADJUST_BALANCE, balance.get(1));
			foundation.click(ConsumerSummary.BTN_REASON_SAVE);
			foundation.waitforElement(ConsumerSearch.BTN_OK, Constants.SHORT_TIME);
			foundation.click(ConsumerSearch.BTN_OK);

			consumerSearch.enterSearchFields(searchBy.get(1), rstConsumerData.get(CNConsumerSearch.CONSUMER_ID),
					location, status.get(1));
			consumerSearch.objLocation(rstConsumerData.get(CNConsumerSearch.LOCATION));
			foundation.click(ConsumerSearch.BTN_ACTIONS);
			foundation.click(ConsumerSearch.LBL_BULK_PAYOUT);
			foundation.click(ConsumerSearch.BTN_CONFIRM);

			Map<String, String> uiTbl = consumerSearch
					.getConsumerRecords(rstConsumerData.get(CNConsumerSearch.LOCATION));
			String uiBal = uiTbl.get(rstConsumerData.get(CNConsumerSearch.COLUMN_NAME));
			CustomisedAssert.assertEquals(uiBal, balance.get(2));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
