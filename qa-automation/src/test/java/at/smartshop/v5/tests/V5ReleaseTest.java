package at.smartshop.v5.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNConsumerSearch;
import at.smartshop.database.columns.CNConsumerSummary;
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
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Order;
import at.smartshop.v5.pages.ProductSearch;

public class V5ReleaseTest extends TestInfra {
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Dropdown dropDown = new Dropdown();
	private Strings string = new Strings();

	private CategoryList categoryList = new CategoryList();
	private CategorySummary categorySummary = new CategorySummary();
	private ConsumerSearch consumerSearch = new ConsumerSearch();
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private GlobalProduct globalProduct = new GlobalProduct();
	private LocationList locationList = new LocationList();
	private LocationSummary locationSummary = new LocationSummary();
	private Order order = new Order();

	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstConsumerSearchData;
	private Map<String, String> rstConsumerSummaryData;

	@Test(description = "143123-Add new tax category and Edit it's name and verify edits applied to product or not on product summary page")
	public void addEditTaxCategory() {
		final String CASE_NUM = "143123";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String newTaxCat = string.getRandomCharacter().toUpperCase();
		String editedTaxCat = requiredData.get(1) + newTaxCat;
		try {
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add tax category
			categorySummary.addCategory(newTaxCat, requiredData.get(3));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newTaxCat));

			foundation.threadWait(Constants.TWO_SECOND);
			// Select Menu and Menu Item and add the tax category to a global product
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			textBox.enterText(ProductSummary.TXT_PRICE, "5");
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, newTaxCat, Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newTaxCat);
			categorySummary.updateName(editedTaxCat);
			navigationBar.navigateToMenuItem(menuItem.get(0));
			globalProduct.selectGlobalProduct(productName);
			CustomisedAssert.assertEquals(dropDown.getSelectedItem(ProductSummary.DPD_TAX_CATEGORY), editedTaxCat);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			foundation.threadWait(Constants.TWO_SECOND);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedTaxCat);
			categorySummary.updateName(newTaxCat);
		}
	}

	@Test(description = "143124-Add new deposit category and Edit it's name and verify edits applied to product or not")
	public void addEditDepositCategory() {
		final String CASE_NUM = "143124";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		String newDepositCat = string.getRandomCharacter().toUpperCase();
		String editedDepositCat = requiredData.get(1) + newDepositCat;
		try {

			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
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
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			dropDown.selectItem(ProductSummary.DPD_DEPOSIT_CATEGORY, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedDepositCat);
			categorySummary.updateName(newDepositCat);
		}
	}

	@Test(description = "143125-QA-19-Add new category and Edit it's name and verify edits applied to product or not")
	public void addeditCategories() {
		final String CASE_NUM = "143125";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		try {
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			List<String> menuItem = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
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
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			dropDown.selectItem(ProductSummary.DPD_CATEGORY1, requiredData.get(2), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY2, requiredData.get(2), Constants.TEXT);
			dropDown.selectItem(ProductSummary.DPD_CATEGORY3, requiredData.get(2), Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);
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
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			foundation.click(ProductSummary.LBL_REASON_CODE);
			List<String> listReasonCode = dropDown.getAllItems(ProductSummary.DPD_REASON_CODE);
			CustomisedAssert.assertTrue(listReasonCode.contains(editedInvReason));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143128-QAA19-Add new tax category and Edit it's category name and verify edits applied to product or not on location page - tax mapping tab")
	public void addEditTaxCategoryTaxMapping() {
		final String CASE_NUM = "143128";

		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		String newTaxCategory1 = string.getRandomCharacter().toUpperCase();
		String newTaxCategory2 = string.getRandomCharacter().toUpperCase();
		String editedTaxCategory1 = requiredData.get(2) + newTaxCategory1;
		String editedTaxCategory2 = requiredData.get(2) + newTaxCategory2;
		try {
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// add tax categories
			navigationBar.navigateToMenuItem(menuItem.get(1));

			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newTaxCategory1, requiredData.get(4));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);
			categorySummary.addCategory(newTaxCategory2, requiredData.get(4));

			// save tax mapping on location summary page as precondition
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(rstV5DeviceData.get(CNV5Device.LOCATION));
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			dropDown.selectItem(LocationSummary.DPD_TAX_CATEGORY, newTaxCategory1, Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(1), Constants.TEXT);
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

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedTaxCategory1);
			categorySummary.updateName(newTaxCategory1);
			categoryList.selectCategory(editedTaxCategory2);
			categorySummary.updateName(newTaxCategory2);
		}
	}

	@Test(description = "143129-QAA-19-Add new BALREASON category and Edit it's name and verify edits applied on consumer page or not")
	public void addEditBalReason() {
		final String CASE_NUM = "143129";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		String newBalCat = string.getRandomCharacter().toUpperCase();
		String editedBalCat = requiredData.get(0) + newBalCat;
		try {
			rstConsumerSearchData = dataBase.getConsumerSearchData(Queries.CONSUMER_SEARCH, CASE_NUM);
			rstConsumerSummaryData = dataBase.getConsumerSummaryData(Queries.CONSUMER_SUMMARY, CASE_NUM);
			String firstName = rstConsumerSummaryData.get(CNConsumerSummary.FIRST_NAME);
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
			// textBox.enterText(ConsumerSummary.TXT_SEARCH_ACCOUNT_ADJUSTMENT,
			// editedBalCat);
			// CustomisedAssert.assertTrue(foundation.isDisplayed(consumerSummary.objTaxCategory(editedBalCat)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			navigationBar.navigateToMenuItem(menuItem.get(0));
			categoryList.selectCategory(editedBalCat);
			categorySummary.updateName(newBalCat);
		}
	}

	@Test(description = "143127-QAA-19-Add new tax rate and Edit it's percentage (tax rate) and verify edits applied to product or not on V5 order page")
	public void addEditTaxRateCategory() {
		final String CASE_NUM = "143127";

		// Reading test data from DataBase
		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		String productName = rstV5DeviceData.get(CNV5Device.PRODUCT_NAME);
		String location = rstV5DeviceData.get(CNV5Device.LOCATION);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menuItem = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));

		String newTaxCat = string.getRandomCharacter().toUpperCase();
		String editedTaxCat = requiredData.get(0) + newTaxCat;
		try {
			List<String> language = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.LANGUAGE).split(Constants.DELIMITER_TILD));
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to new category page
			navigationBar.navigateToMenuItem(menuItem.get(1));
			foundation.click(CategoryList.BTN_CREATE_NEW_CATEGORY);

			// add tax category
			categorySummary.addCategory(newTaxCat, requiredData.get(4));

			// verify newly added category displays in category list page
			CustomisedAssert.assertTrue(categoryList.verifyCategoryExist(newTaxCat));

			// save tax mapping on location summary page as precondition
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(location);
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			foundation.click(LocationSummary.BTN_ADD_MAPPING);
			dropDown.selectItem(LocationSummary.DPD_TAX_CATEGORY, newTaxCat, Constants.TEXT);
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE, requiredData.get(1), Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE_MAPPING);

			// add categories to products
			navigationBar.navigateToMenuItem(menuItem.get(0));
			foundation.threadWait(Constants.THREE_SECOND);
			globalProduct.selectGlobalProduct(productName);
			dropDown.selectItem(ProductSummary.DPD_TAX_CATEGORY, newTaxCat, Constants.TEXT);
			foundation.click(ProductSummary.BTN_SAVE);

			// edit tax category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(newTaxCat);
			categorySummary.updateName(editedTaxCat);

			// verify tax edits on location summary page- tax mapping tab- for saved
			// category
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(location);
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			textBox.enterText(LocationSummary.TXT_SEARCH_TAX_MAPPING, editedTaxCat);
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objTaxCategory(editedTaxCat)));
			foundation.click(locationSummary.objTaxCategory(editedTaxCat));
			dropDown.selectItem(LocationSummary.DPD_TAX_RATE_EDIT, "AutomationTax", Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE_MAPPING);

			// set language and sync machine
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationSummary.kiosklanguageSetting(location, language.get(0), language.get(1));

			// launch v5 application
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			// foundation.click(landingPage.objLanguage(language.get(0)));
			foundation.click(LandingPage.IMG_SEARCH_ICON);
			textBox.enterKeypadText(productName);
			foundation.click(ProductSearch.BTN_PRODUCT);
			CustomisedAssert.assertEquals(foundation.getText(Order.TXT_PRODUCT), productName);
			order.verifyTax(requiredData.get(2));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		} finally {
			// reset data
			// reset- category name
			browser.close();
			browser.launch(Constants.LOCAL, Constants.CHROME);
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// reset mapping
			navigationBar.navigateToMenuItem(menuItem.get(2));
			locationList.selectLocationName(location);
			foundation.click(LocationSummary.TAB_TAX_MAPPING);
			textBox.enterText(LocationSummary.TXT_SEARCH_TAX_MAPPING, editedTaxCat);
			CustomisedAssert.assertTrue(foundation.isDisplayed(locationSummary.objTaxCategory(editedTaxCat)));
			foundation.click(locationSummary.objTaxCategory(editedTaxCat));
			foundation.click(LocationSummary.BTN_REMOVE_MAPPING);
			// reset category
			navigationBar.navigateToMenuItem(menuItem.get(1));
			categoryList.selectCategory(editedTaxCat);
			categorySummary.updateName(newTaxCat);
		}
	}
}
