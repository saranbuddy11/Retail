package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
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
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgSummary;

@Listeners(at.framework.reportsetup.Listeners.class)
public class VDICheck extends TestInfra {
	
	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private TextBox textBox = new TextBox();
	private CheckBox checkBox = new CheckBox();
	private Dropdown dropDown = new Dropdown();
	private Foundation foundation = new Foundation();
	private Strings string = new Strings();
	private OrgSummary orgSummary = new OrgSummary();
	private LocationList locationList = new LocationList();
	private LocationSummary locationSummary = new LocationSummary();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstOrgSummaryData;

	@Test(description = "143021-QAA-36-SOS-18920-Verify Enable VDI checkbox flow in Org Summary")
	public void verifyVDICheckOrg() {
		try {
			final String CASE_NUM = "143021";
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(OrgSummary.CHK_VDI);
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			String actualDpd = dropDown.getSelectedItem(OrgSummary.DPD_VDI_PROVDIER);
			Assert.assertEquals(actualDpd, requiredData.get(0));
			Assert.assertTrue(foundation.isDisplayed(OrgSummary.TXT_USER_KEY));
			dropDown.selectItem(OrgSummary.DPD_VDI_PROVDIER, rstOrgSummaryData.get(CNOrgSummary.NAME), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(OrgSummary.BTN_VDI_PLUS);
			Assert.assertFalse(checkBox.isChkEnabled(OrgSummary.CHK_VDI));
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_NO, Constants.SHORT_TIME);
			String popup_Header = foundation.getText(OrgSummary.LBL_POPUP_HEADER);
			Assert.assertEquals(popup_Header, requiredData.get(1));
			String popup_Msg = foundation.getText(OrgSummary.LBL_POPUP_MSG);
			Assert.assertEquals(popup_Msg, requiredData.get(2));
			foundation.click(OrgSummary.BTN_NO);
			Assert.assertTrue(foundation.isDisplayed(orgSummary.objVDI(rstOrgSummaryData.get(CNOrgSummary.NAME))));
			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			foundation.waitforClikableElement(OrgSummary.BTN_VDI_DEL, Constants.LONG_TIME);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(checkBox.isChkEnabled(OrgSummary.CHK_VDI));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "143025-QAA-36-Verify when Enable VDI is unchecked, vdi provider dropdown and user key are not displayed in Org summary.")
	public void verifyVDIUnCheckOrg() {
		try {
			final String CASE_NUM = "143025";
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(OrgSummary.CHK_VDI);
			checkBox.unCheck(OrgSummary.CHK_VDI);
			foundation.threadWait(Constants.ONE_SECOND);
			Assert.assertFalse(foundation.isDisplayed(OrgSummary.DPD_VDI_PROVDIER));
			Assert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_USER_KEY));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "143023-QAA-36-SOS-18920 - Verify when vdi is disabled for org then vdi option for location disabled and should see 'Can not enable VDI here until VDI is enabled for the org'")
	public void verifyVDIUnCheckOrgDisable() {
		try {
			final String CASE_NUM = "143023";
			browser.navigateURL(	propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.unCheck(OrgSummary.CHK_VDI);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.LBL_ORG_LIST, Constants.SHORT_TIME);
			foundation.refreshPage();
			foundation.waitforElement(LocationSummary.LINK_HOME_PAGE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LINK_HOME_PAGE);
			locationList.selectLocationName(requiredData.get(0));
			foundation.waitforElement(orgSummary.objVDI(requiredData.get(1)), Constants.SHORT_TIME);
			Assert.assertTrue(foundation.isDisplayed(orgSummary.objVDI(requiredData.get(1))));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "143024-QAA-36-Verify when Enable VDI is unchecked, vdi provider dropdown and user key are not displayed in Location summary")
	public void verifyVDIUnCheckLoc() {
		try {
			final String CASE_NUM = "143024";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			String requiredData = rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(OrgSummary.CHK_VDI);
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);

			dropDown.selectItem(OrgSummary.DPD_VDI_PROVDIER, rstOrgSummaryData.get(CNOrgSummary.NAME), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(OrgSummary.BTN_VDI_PLUS);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(OrgSummary.LBL_ORG_LIST, Constants.SHORT_TIME);

			// location summary page
			foundation.waitforElement(LocationSummary.LINK_HOME_PAGE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LINK_HOME_PAGE);
			locationList.selectLocationName(requiredData);
			foundation.waitforElement(LocationSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(LocationSummary.CHK_VDI);
			checkBox.unCheck(LocationSummary.CHK_VDI);
			foundation.threadWait(Constants.ONE_SECOND);
			Assert.assertFalse(foundation.isDisplayed(LocationSummary.DPD_VDI_PROVDIER));
			Assert.assertFalse(foundation.isDisplayed(LocationSummary.TXT_USER_KEY));

			// resetting test data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_SAVE);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "143022-QAA-36-SOS-18920 - Verify Enable VDI in Location Summary")
	public void verifyVDICheckLoc() {
		try {
			final String CASE_NUM = "143022";
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

			dropDown.selectItem(OrgSummary.DPD_VDI_PROVDIER, rstOrgSummaryData.get(CNOrgSummary.NAME), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(OrgSummary.BTN_VDI_PLUS);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(OrgSummary.LBL_ORG_LIST, Constants.SHORT_TIME);
			
			// location summary page
			foundation.waitforElement(LocationSummary.LINK_HOME_PAGE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LINK_HOME_PAGE);
			locationList.selectLocationName(requiredData.get(0));
			foundation.waitforElement(LocationSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(LocationSummary.CHK_VDI);

			foundation.waitforElement(LocationSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			String actualDpd = dropDown.getSelectedItem(LocationSummary.DPD_VDI_PROVDIER);
			Assert.assertEquals(actualDpd, requiredData.get(1));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_USER_KEY));
			dropDown.selectItem(LocationSummary.DPD_VDI_PROVDIER, rstOrgSummaryData.get(CNOrgSummary.NAME),
					Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(LocationSummary.BTN_VDI_PLUS);
			Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_VDI));
			foundation.click(LocationSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_NO, Constants.SHORT_TIME);
			String popup_Header = foundation.getText(OrgSummary.LBL_POPUP_HEADER);
			Assert.assertEquals(popup_Header, requiredData.get(2));
			String popup_Msg = foundation.getText(OrgSummary.LBL_POPUP_MSG);
			Assert.assertEquals(popup_Msg, requiredData.get(3));
			foundation.click(LocationSummary.BTN_NO);
			Assert.assertTrue(foundation.isDisplayed(orgSummary.objVDI(rstOrgSummaryData.get(CNOrgSummary.NAME))));
			foundation.waitforElement(LocationSummary.CHK_VDI, Constants.SHORT_TIME);
			foundation.waitforClikableElement(LocationSummary.BTN_VDI_DEL, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_VDI_DEL);
			foundation.waitforElement(LocationSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(checkBox.isChkEnabled(LocationSummary.CHK_VDI));
			locationSummary.selectTab(requiredData.get(4));
			textBox.enterText(LocationSummary.TXT_SEARCH, requiredData.get(0));
			// price validation
			String isReadOnly = locationSummary.getTextAttribute(locationSummary.objProductPrice(requiredData.get(5)),Constants.ATTRIBUTE_READ);
			Assert.assertFalse(Boolean.parseBoolean(isReadOnly));

			// resetting test data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_SAVE);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "143150-QAA-36-SOS-18920 - Verify Enable VDI in Location Summary")
	public void verifyVDICheckLocPrice() {
		try {
			final String CASE_NUM = "143150";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(OrgSummary.CHK_VDI);
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);

			dropDown.selectItem(OrgSummary.DPD_VDI_PROVDIER, rstOrgSummaryData.get(CNOrgSummary.NAME), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(OrgSummary.BTN_VDI_PLUS);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(OrgSummary.LBL_ORG_LIST, Constants.SHORT_TIME);
			
			// location summary page
			foundation.waitforElement(LocationSummary.LINK_HOME_PAGE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LINK_HOME_PAGE);
			locationList.selectLocationName(requiredData.get(0));
			foundation.waitforElement(LocationSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(LocationSummary.CHK_VDI);
			foundation.waitforElement(LocationSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			String actualDpd = dropDown.getSelectedItem(LocationSummary.DPD_VDI_PROVDIER);
			Assert.assertEquals(actualDpd, requiredData.get(1));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_USER_KEY));
			dropDown.selectItem(LocationSummary.DPD_VDI_PROVDIER, rstOrgSummaryData.get(CNOrgSummary.NAME),Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(LocationSummary.BTN_VDI_PLUS);
			Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_VDI));
			foundation.click(LocationSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_NO, Constants.SHORT_TIME);
			String popup_Header = foundation.getText(OrgSummary.LBL_POPUP_HEADER);
			Assert.assertEquals(popup_Header, requiredData.get(2));
			String popup_Msg = foundation.getText(OrgSummary.LBL_POPUP_MSG);
			Assert.assertEquals(popup_Msg, requiredData.get(3));
			foundation.click(LocationSummary.BTN_NO);
			Assert.assertTrue(foundation.isDisplayed(orgSummary.objVDI(rstOrgSummaryData.get(CNOrgSummary.NAME))));
			foundation.threadWait(Constants.ONE_SECOND);
			foundation.waitforElement(LocationSummary.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(requiredData.get(0));
			foundation.waitforElement(LocationSummary.CHK_VDI, Constants.SHORT_TIME);
			locationSummary.selectTab(requiredData.get(4));
			textBox.enterText(LocationSummary.TXT_SEARCH, requiredData.get(0));
			// price validation
			String isReadOnly = locationSummary.getTextAttribute(locationSummary.objProductPrice(requiredData.get(5)),Constants.ATTRIBUTE_READ);
			Assert.assertTrue(Boolean.parseBoolean(isReadOnly));
			// resetting test data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_SAVE);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "143151-QAA-36-SOS-18920-Verify already selected VDI option should not display in Vdi provider Dropdown in Org Summary")
	public void verifyVDIDropDownValue() {
		try {
			final String CASE_NUM = "143151";
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);

			// Select Menu and Menu Item
			navigationBar.selectOrganization(propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
            //org Summary Page
   			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(OrgSummary.CHK_VDI);
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			dropDown.selectItem(OrgSummary.DPD_VDI_PROVDIER, rstOrgSummaryData.get(CNOrgSummary.NAME), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(OrgSummary.BTN_VDI_PLUS);
               //VDI Provider   drop down validations
			orgSummary.verifyDPDValue(rstOrgSummaryData.get(CNOrgSummary.NAME));
			
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "143152-QAA-36-SOS-18920 - Verify Added multiple vdi providers in org summary is displayed in VDI Providers dropdown in Location Summary")
	public void verifyMultipleVDI() {
		try {
			final String CASE_NUM = "143152";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstOrgSummaryData = dataBase.getOrgSummaryData(Queries.ORG_SUMMARY, CASE_NUM);
			List<String> requiredData = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			List<String> vdiProvider = Arrays
					.asList(rstOrgSummaryData.get(CNOrgSummary.NAME).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(OrgSummary.CHK_VDI);
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);

			dropDown.selectItem(OrgSummary.DPD_VDI_PROVDIER, vdiProvider.get(0), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(OrgSummary.BTN_VDI_PLUS);

			dropDown.selectItem(OrgSummary.DPD_VDI_PROVDIER, vdiProvider.get(1), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(OrgSummary.BTN_VDI_PLUS);

			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(OrgSummary.LBL_ORG_LIST, Constants.SHORT_TIME);

			// location summary page
			foundation.waitforElement(LocationSummary.LINK_HOME_PAGE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LINK_HOME_PAGE);
			locationList.selectLocationName(requiredData.get(0));
			foundation.waitforElement(LocationSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(LocationSummary.CHK_VDI);

			foundation.waitforElement(LocationSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			String actualDpd = dropDown.getSelectedItem(LocationSummary.DPD_VDI_PROVDIER);
			Assert.assertEquals(actualDpd, requiredData.get(1));
			Assert.assertTrue(foundation.isDisplayed(LocationSummary.TXT_USER_KEY));

			Assert.assertTrue(locationSummary.verifyDPDValue(vdiProvider.get(0)));
			Assert.assertTrue(locationSummary.verifyDPDValue(vdiProvider.get(1)));
			// first vdi provider
			dropDown.selectItem(LocationSummary.DPD_VDI_PROVDIER, vdiProvider.get(0), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(LocationSummary.BTN_VDI_PLUS);
			Assert.assertFalse(checkBox.isChkEnabled(LocationSummary.CHK_VDI));
			foundation.click(LocationSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_NO, Constants.SHORT_TIME);
			String popup_Header = foundation.getText(OrgSummary.LBL_POPUP_HEADER);
			Assert.assertEquals(popup_Header, requiredData.get(2));
			String popup_Msg = foundation.getText(OrgSummary.LBL_POPUP_MSG);
			Assert.assertEquals(popup_Msg, requiredData.get(3));
			foundation.click(LocationSummary.BTN_NO);
			Assert.assertTrue(foundation.isDisplayed(orgSummary.objVDI(vdiProvider.get(0))));
			foundation.threadWait(Constants.ONE_SECOND);
			Assert.assertFalse(locationSummary.verifyDPDValue(vdiProvider.get(0)));
			// second Vdi Provider
			dropDown.selectItem(LocationSummary.DPD_VDI_PROVDIER, vdiProvider.get(1), Constants.TEXT);
			textBox.enterText(LocationSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(LocationSummary.BTN_VDI_PLUS);
			Assert.assertTrue(foundation.isDisplayed(orgSummary.objVDI(vdiProvider.get(1))));
			foundation.waitforElement(LocationSummary.BTN_SAVE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(LocationList.TXT_FILTER, Constants.SHORT_TIME);
			locationList.selectLocationName(requiredData.get(0));
			foundation.waitforElement(LocationSummary.CHK_VDI, Constants.SHORT_TIME);
			locationSummary.selectTab(requiredData.get(4));
			textBox.enterText(LocationSummary.TXT_SEARCH, requiredData.get(0));
			// resetting test data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.threadWait(Constants.TWO_SECOND);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.TXT_SPINNER_MSG, Constants.SHORT_TIME);
			login.logout();

		} catch (Exception exc) {

			Assert.fail(exc.toString());
		}
	}
}
