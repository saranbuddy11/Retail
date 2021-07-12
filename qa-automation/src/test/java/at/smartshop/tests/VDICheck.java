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

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstOrgSummaryData;

	@Test(description = "143021 QAA-36-SOS-18920-Verify Enable VDI checkbox flow in Org Summary")
	public void verifyVDICheckOrg() {
		try {
			final String CASE_NUM = "143021";
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
			foundation.waitforClikableElement(OrgSummary.BTN_VDI_DEL, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			Assert.assertTrue(checkBox.isChkEnabled(OrgSummary.CHK_VDI));

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}
	@Test(description = "143025 QAA-36-Verify when Enable VDI is unchecked, vdi provider dropdown and user key are not displayed in Org summary.")
	public void verifyVDIUnCheckOrg() {
		try {
			final String CASE_NUM = "143025";
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
		

			// Select Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(OrgSummary.CHK_VDI);
			checkBox.unCheck(OrgSummary.CHK_VDI);
			foundation.threadWait(Constants.ONE_SECOND);
			Assert.assertFalse(foundation.isDisplayed(OrgSummary.DPD_VDI_PROVDIER));
			Assert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_USER_KEY));
			
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "143023 QAA-36-SOS-18920 - Verify when vdi is disabled for org then vdi option for location disabled and should see \"Can not enable VDI here until VDI is enabled for the org\"")
	public void verifyVDIUnCheckOrgDisable() {
		try {
			final String CASE_NUM = "143023";
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
			exc.printStackTrace();
			Assert.fail();
		}
	}
	@Test(description = "143024 QAA-36-Verify when Enable VDI is unchecked, vdi provider dropdown and user key are not displayed in Location summary")
	public void verifyVDIUnCheckLoc() {
		try {
			final String CASE_NUM = "143024";
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
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(OrgSummary.CHK_VDI);
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
		
			dropDown.selectItem(OrgSummary.DPD_VDI_PROVDIER, rstOrgSummaryData.get(CNOrgSummary.NAME), Constants.TEXT);
			textBox.enterText(OrgSummary.TXT_USER_KEY, string.getRandomCharacter());
			foundation.click(OrgSummary.BTN_VDI_PLUS);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.waitforElement(OrgSummary.LBL_ORG_LIST, Constants.SHORT_TIME);
			foundation.refreshPage();
			//location
			foundation.waitforElement(LocationSummary.LINK_HOME_PAGE, Constants.SHORT_TIME);
			foundation.click(LocationSummary.LINK_HOME_PAGE);
			locationList.selectLocationName(requiredData);
			foundation.waitforElement(OrgSummary.CHK_VDI, Constants.SHORT_TIME);
			checkBox.check(OrgSummary.CHK_VDI);
			checkBox.unCheck(OrgSummary.CHK_VDI);
			foundation.threadWait(Constants.ONE_SECOND);
			Assert.assertFalse(foundation.isDisplayed(OrgSummary.DPD_VDI_PROVDIER));
			Assert.assertFalse(foundation.isDisplayed(OrgSummary.TXT_USER_KEY));
			
			//resetting test data
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			foundation.waitforElement(OrgSummary.DPD_VDI_PROVDIER, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_VDI_DEL);
			foundation.waitforElement(OrgSummary.BTN_YES, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_YES);
			foundation.waitforElement(OrgSummary.LBL_SPINNER_MSG, Constants.SHORT_TIME);
			foundation.click(OrgSummary.BTN_SAVE);
			
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}
}
