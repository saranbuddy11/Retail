package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNDeviceList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgSummary;
import at.smartshop.pages.OrgstrList;
import at.smartshop.pages.ProductSummary;

@Listeners(at.framework.reportsetup.Listeners.class)

public class Organization extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private OrgstrList orgstr = new OrgstrList();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private Strings string = new Strings();
	private Numbers numbers = new Numbers();
	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstDeviceListData;

	@Test(description = "164571-Enter all the valid details in the fields and click on add")
	public void OrgstrValidDetailsAndSave() {

		final String CASE_NUM = "164571";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, dbData.get(1));
			textBox.enterText(OrgstrList.TXT_NAME, device);
			textBox.enterText(OrgstrList.KEY_NAME, device);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {

			// resetting test data
			orgstr.acceptPopup(device);
		}

	}

	@Test(description = "164572-Enter all the valid details in the fields and click on cancel")
	public void OrgstrValidDetailsAndCancel() {

		final String CASE_NUM = "164572";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + string.getRandomCharacter();

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, dbData.get(1));
			textBox.enterText(OrgstrList.TXT_NAME, device);
			textBox.enterText(OrgstrList.KEY_NAME, device);

			// Click on Cancel Button
			foundation.waitforElement(OrgstrList.BTN_CANCEL, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_CANCEL);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "164573-Enter special characters in the fields and click on add")
	public void OrgstrSpecialChars() {

		final String CASE_NUM = "164573";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE);

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, dbData.get(1));
			textBox.enterText(OrgstrList.TXT_NAME, device);
			textBox.enterText(OrgstrList.KEY_NAME, device);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {

			// resetting test data
			orgstr.acceptPopup(device);
		}

	}

	@Test(description = "164574-Enter numeric Values in the fields and click on add")
	public void OrgstrNumericValues() {

		final String CASE_NUM = "164574";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
		final String numeric = String.valueOf(numbers.generateRandomNumber(0, 99999));

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, numeric);
			textBox.enterText(OrgstrList.TXT_NAME, numeric);
			textBox.enterText(OrgstrList.KEY_NAME, numeric);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {

			// resetting test data
			foundation.waitforElement(OrgstrList.ORG_LIST, Constants.SHORT_TIME);
			textBox.enterText(OrgstrList.ORG_DEVICE_SEARCH, numeric);
			foundation.click(OrgstrList.TBL_DATA);
			foundation.waitforElement(OrgstrList.BTN_REMOVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_REMOVE);
			foundation.alertAccept();
		}

	}

	@Test(description = "164575-Enter Combination of Special Characters and numeric Values in the fields and click on add")
	public void OrgstrCombinationSpecialCharsAndNumericValues() {

		final String CASE_NUM = "164575";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));

		final String device = rstDeviceListData.get(CNDeviceList.DEVICE) + numbers.generateRandomNumber(0, 99999);

		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, dbData.get(1));
			textBox.enterText(OrgstrList.TXT_NAME, device);
			textBox.enterText(OrgstrList.KEY_NAME, device);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {

			// resetting test data
			orgstr.acceptPopup(device);
		}

	}

	@Test(description = "164576-Enter Blank Values in the fields and click on add")
	public void OrgstrBlankValues() {

		final String CASE_NUM = "164576";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
		String expectedData = rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE);


		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(OrgstrList.BTN_CREATE);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);
			Assert.assertEquals(foundation.getText(OrgstrList.ERROR_STATUS), expectedData);
			Assert.assertEquals(foundation.getText(OrgstrList.TYPE_ERROR_STATUS), expectedData);
			Assert.assertEquals(foundation.getText(OrgstrList.NAME_ERROR_STATUS), expectedData);
			Assert.assertEquals(foundation.getText(OrgstrList.KEYSTR_ERROR_STATUS), expectedData);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "164577-Enter only Org and leave other fields and click on add")
	public void OrgstrOnlyOrgAndBlankValues() {

		final String CASE_NUM = "164577";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
		String expectedData = rstDeviceListData.get(CNDeviceList.ERROR_MESSAGE);

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));


		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

			// Validating the error message
			Assert.assertEquals(foundation.getText(OrgstrList.TYPE_ERROR_STATUS), expectedData);
			Assert.assertEquals(foundation.getText(OrgstrList.NAME_ERROR_STATUS), expectedData);
			Assert.assertEquals(foundation.getText(OrgstrList.KEYSTR_ERROR_STATUS), expectedData);


		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}

	}

	@Test(description = "164578-Enter already added data in the fields and click on add")
	public void OrgstrAlreadyAddedData() {

		final String CASE_NUM = "164578";
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstDeviceListData = dataBase.getDeviceListData(Queries.DEVICE_LIST, CASE_NUM);
		

		List<String> dbData = Arrays
				.asList(rstDeviceListData.get(CNDeviceList.PRODUCT_NAME).split(Constants.DELIMITER_TILD));


		try {

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// Create New
			foundation.click(OrgstrList.BTN_CREATE);
			dropDown.selectItem(OrgstrList.ORGSTR_ORG, dbData.get(0), Constants.TEXT);
			textBox.enterText(OrgstrList.CAT_NAME, dbData.get(1));
			textBox.enterText(OrgstrList.TXT_NAME, dbData.get(2));
			textBox.enterText(OrgstrList.KEY_NAME, dbData.get(2));

			// Click on Save Button
			foundation.waitforElement(OrgstrList.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_SAVE);

		} catch (Throwable exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {

			// resetting test data
			foundation.waitforElement(OrgstrList.ORG_LIST, Constants.SHORT_TIME);
			textBox.enterText(OrgstrList.ORG_DEVICE_SEARCH, dbData.get(2));
			foundation.click(OrgstrList.TBL_DATA);
			foundation.waitforElement(OrgstrList.BTN_REMOVE, Constants.SHORT_TIME);
			foundation.click(OrgstrList.BTN_REMOVE);
			foundation.alertAccept();
		}

	}

}
