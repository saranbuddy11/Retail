package at.smartshop.tests;

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
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgSummary;

@Listeners(at.framework.reportsetup.Listeners.class)
public class OrganizationSummary extends TestInfra {
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox();
	private Dropdown dropDown = new Dropdown();
	private Foundation foundation = new Foundation();
	private Strings string = new Strings();
	private Numbers numbers = new Numbers();
	private OrgSummary orgSummary = new OrgSummary();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstOrgSummaryData;

	@Test(description = "143231-QAA-17-Verify when Address options are updated in OrgSummary, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingAddress() {
		try {
			final String CASE_NUM = "143231";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			textBox.enterText(OrgSummary.TXT_DISPLAYNAME, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_ADDRESS, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_CITY, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_ZIP, String.valueOf(numbers.generateRandomNumber(1, 99999)));
			dropDown.selectItem(OrgSummary.DPD_STATE, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(2));
			//CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143244-QAA-17-Verify when VDI options are updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingVDICheck() {
		final String CASE_NUM = "143244";
		
		// Reading test data from DataBase
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		String actualData="";
		try {
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(OrgSummary.CHK_VDI);
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			// enable VDI
			dropDown.selectItem(OrgSummary.DPD_VDI_PROVDIER, requiredData.get(0), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(OrgSummary.BTN_VDI_PLUS);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(1));
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		finally {
			// disable vdi
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(1));
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));
		}
	}

	@Test(description = "143245-QAA-17-Verify when receipt image option is updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingReceiptImg() {
		try {
			final String CASE_NUM = "143245";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			String requiredData = rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA);
			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			// removing image
			foundation.click(OrgSummary.BTN_REMOVE);
			// upload image
			textBox.enterText(OrgSummary.LBL_UPLOAD, FilePath.IMAGE_PNG_PATH);
			foundation.waitforElement(OrgSummary.BTN_REMOVE, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData);
		

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143246-QAA-17-Verify when USG Data Feed option is updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingUSGData() {
		try {
			final String CASE_NUM = "143246";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			// disable usg
			dropDown.selectItem(OrgSummary.DPD_USG_DATA, requiredData.get(1), Constants.TEXT);
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_USG_ID));
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(2));
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));

			// Enable USG Feed
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			dropDown.selectItem(OrgSummary.DPD_USG_DATA, requiredData.get(0), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_USG_ID, string.getRandomCharacter());
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(2));
			//CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143260-QAA-17-Verify when funding options are updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingFundingOptions() {
		try {
			final String CASE_NUM = "143260";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			// check funding options
			checkBox.check(orgSummary.objVDI(requiredData.get(0)));
			checkBox.check(orgSummary.objVDI(requiredData.get(1)));
			checkBox.check(orgSummary.objVDI(requiredData.get(2)));
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(3));
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));
			// uncheck funding options
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			checkBox.unCheck(orgSummary.objVDI(requiredData.get(0)));
			checkBox.unCheck(orgSummary.objVDI(requiredData.get(1)));
			checkBox.unCheck(orgSummary.objVDI(requiredData.get(2)));
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(3));
			//CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143261-QAA-17-Verify when Inventory options are updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingInventoryOptions() {
		try {
			final String CASE_NUM = "143261";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			String requiredData = rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA);

			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			// check Inventory options
			checkBox.check(OrgSummary.CHK_SHOW_HISTORY);
			checkBox.check(OrgSummary.CHK_SHOW_CHECKLIST_COUNT);
			checkBox.check(OrgSummary.CHK_SHOW_CHECKLIST_REVIEW);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData);
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));
			// uncheck Inventory options
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			checkBox.unCheck(OrgSummary.CHK_SHOW_HISTORY);
			checkBox.unCheck(OrgSummary.CHK_SHOW_CHECKLIST_COUNT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData);
			//CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143262-QAA-17-Verify when Report options is updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingReportOptions() {
		try {
			final String CASE_NUM = "143262";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			String requiredData = rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA);
			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			// reports check
			checkBox.check(OrgSummary.CHK_REPORT_VIEW);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData);
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));
			// uncheck Inventory options
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			checkBox.unCheck(OrgSummary.CHK_REPORT_VIEW);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData);
			//CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143263-QAA-17-Verify when GMA/CREDIT Rate  options is updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingGMACREDITOptions() {
		try {
			final String CASE_NUM = "143263";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			String requiredData = rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA);
			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(OrgSummary.TXT_GMA_RATE, String.valueOf(numbers.generateRandomNumber(1, 10)));
			textBox.enterText(OrgSummary.TXT_CREDIT_RATE, String.valueOf(numbers.generateRandomNumber(1, 10)));
			textBox.enterText(OrgSummary.TXT_NANO_GMA_RATE, String.valueOf(numbers.generateRandomNumber(1, 10)));
			textBox.enterText(OrgSummary.TXT_NANO_CREDIT_RATE, String.valueOf(numbers.generateRandomNumber(1, 10)));
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData);

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143267-QAA-17-Verify when financial options are updated in OrgSummary, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingFinancialOptions() {
		try {
			final String CASE_NUM = "143267";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String operatorName = rstOrgSummaryData.get(CNOrgSummary.NAME);
			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(OrgSummary.TXT_OPERATOR, operatorName);
			checkBox.check(OrgSummary.CHK_DISBURSEMENT);
			textBox.enterText(OrgSummary.TXT_DISBURSEMENT_EMAIL, requiredData.get(0));
			textBox.enterText(OrgSummary.TXT_DISBURSEMENT_EMAIL_CC, requiredData.get(1));
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(2));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143266-QAA-17-Verify when Policy options is updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingPoliciesOptions() {
		try {
			final String CASE_NUM = "143266";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			String expectedData = rstOrgSummaryData.get(CNOrgSummary.NAME);
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			for (int i = 0; i < requiredData.size(); i++) {
				navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
				dropDown.selectItem(OrgSummary.DPD_POLICIES, requiredData.get(i), Constants.TEXT);
				foundation.click(OrgSummary.BTN_SAVE);
				foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
				String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
				CustomisedAssert.assertEquals(actualData, expectedData);
			}

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143265-QAA-17-Verify when Sensor options are updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingSensorOptions() {
		try {
			final String CASE_NUM = "143265";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			// enable sensors
			foundation.click(OrgSummary.LBL_SENSOR_TYPE);
			foundation.click(OrgSummary.CHK_FREEZER);
			foundation.click(OrgSummary.CHK_COOLER);
			foundation.click(OrgSummary.LBL_SENSOR_TYPE);
			String actualFreezer = foundation.getText(OrgSummary.LBL_FREEZER);
			String actualCooler = foundation.getText(OrgSummary.LBL_COOLER);
			CustomisedAssert.assertEquals(actualFreezer, requiredData.get(0));
			CustomisedAssert.assertEquals(actualCooler, requiredData.get(1));
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);

			CustomisedAssert.assertEquals(actualData, requiredData.get(2));
			// disable sensors
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.click(OrgSummary.LBL_SENSOR_TYPE);
			foundation.click(OrgSummary.CHK_FREEZER);
			foundation.click(OrgSummary.CHK_COOLER);
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.LBL_COOLER));
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.LBL_FREEZER));
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(2));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143264-QAA-17-Verify when tax and GMA Account options are updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingGMAAccountOptions() {
		try {
			final String CASE_NUM = "143264";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			dropDown.selectItem(OrgSummary.DPD_TAX_METHOD, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_GMA_ACCOUNT, requiredData.get(3), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_CROSS_ORG_ACCOUNT, requiredData.get(3), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(4));
			// disable gma account
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			dropDown.selectItem(OrgSummary.DPD_GMA_ACCOUNT, requiredData.get(2), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(4));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "143274-QAA-17-Verify when All options are updated in OrgSummary, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingAllOptions() {
		try {
			final String CASE_NUM = "143274";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			String orgName = rstOrgSummaryData.get(CNOrgSummary.ORG_NAME);
			// Select Menu and Menu Item
			navigationBar.selectOrganization(orgName);
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			textBox.enterText(OrgSummary.TXT_SAGE_1, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			textBox.enterText(OrgSummary.TXT_SAGE_2, String.valueOf(numbers.generateRandomNumber(0, 99999)));
			textBox.enterText(OrgSummary.TXT_CONTACT, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_CONTACT_EMAIL,requiredData.get(12));
			textBox.enterText(OrgSummary.TXT_CONTACT_PHONE, requiredData.get(4));
			textBox.enterText(OrgSummary.TXT_RECEIPT_HEADER, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_RECEIPT_FOOTER, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_IMG_URL, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_CANTEENID, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_OPERATOR_TAX_ID, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_MONTHLY_FEE, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_MONTH_PF, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_LOCKER_MONTHLY_FEE, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_SUB_FEE, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_MIN_SUB_FEE, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_MAX_SUB_FEE, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_DEVICE_COUNT_FEE, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_CC_OFFLINE_THRESHOLD, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_CC_MAXVALUE, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_LOCKER_FEE_CAP, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_LOCKER_MONTHLY_FEE, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_LOCKERFLAT_FEE, String.valueOf(numbers.generateRandomNumber(0, 99)));
			textBox.enterText(OrgSummary.TXT_LOCKERPERCENT_FEE, String.valueOf(numbers.generateRandomNumber(0, 99)));
			checkBox.check(OrgSummary.CHK_REPORT_VIEW);
			dropDown.selectItem(OrgSummary.DPD_SPECIAL_TYPE, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_SUB_TYPE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_SUB_TYPE, requiredData.get(1), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_METHOD, requiredData.get(5), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_TAX_SYSTEM, requiredData.get(6), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_SYSTEM, requiredData.get(2), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_PAGESET, requiredData.get(3), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_CURRENCY, requiredData.get(9), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_HAS_LIGHTSPEED, requiredData.get(8), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_HAS_PROMOS, requiredData.get(8), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_HAS_SEND_SNACK, requiredData.get(8), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_HAS_DIFF, requiredData.get(8), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_HAS_GUEST_PASS, requiredData.get(8), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_HAS_DATAWARE_HOUSE, requiredData.get(8), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_HAS_CONSUMER_ENGAGEMENT, requiredData.get(10), Constants.TEXT);

			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			CustomisedAssert.assertEquals(actualData, requiredData.get(11));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
