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
import at.framework.ui.CheckBox;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNOrgSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.LocationSummary;
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

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.AUTO_TEST, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			textBox.enterText(OrgSummary.TXT_DISPLAYNAME, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_ADDRESS, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_CITY, string.getRandomCharacter());
			textBox.enterText(OrgSummary.TXT_ZIP, String.valueOf(numbers.generateRandomNumber(1, 6)));
			dropDown.selectItem(OrgSummary.DPD_STATE, requiredData.get(0), Constants.TEXT);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, requiredData.get(1), Constants.TEXT);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			Assert.assertEquals(actualData, requiredData.get(2));
			Assert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "143244-QAA-17-Verify when VDI options are updated, it should not throw Error 500: Internal Server Error")
	public void verifyUpdatingVDICheck() {
		try {
			final String CASE_NUM = "143244";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

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
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			Assert.assertEquals(actualData, requiredData.get(1));
			Assert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));
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
			Assert.assertEquals(actualData, requiredData.get(1));
			Assert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
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

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.AUTO_TEST, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			//removing image
			foundation.click(OrgSummary.BTN_REMOVE);
			//upload image
			textBox.enterText(OrgSummary.LBL_UPLOAD, FilePath.IMAGE_PATH);
			foundation.waitforElement(OrgSummary.BTN_REMOVE, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			String actualData = foundation.getText(OrgSummary.TXT_SPINNER_MSG);
			Assert.assertEquals(actualData, requiredData);
			Assert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_ERROR_MSG));
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
			
}
