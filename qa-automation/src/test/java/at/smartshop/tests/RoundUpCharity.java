package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNRoundUpCharity;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.AdminRoundUpCharity;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgList;
import at.smartshop.pages.OrgSummary;

@Listeners(at.framework.reportsetup.Listeners.class)
public class RoundUpCharity extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private AdminRoundUpCharity adminRoundUpCharity = new AdminRoundUpCharity();
	private OrgList orgList = new OrgList();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstRoundUpCharityData;

	/**
	 * @author afrosean Date:14-10-2022
	 */
	@Test(description = "204839-To Verify 'Charity Round-Up' Dropdown"
			+ "204842-To Verify Admin menu when 'Disabled' Option is selected under Charity Round-Up"
			+ "204841-To Verify Admin menu when 'Enabled' Option is selected under Charity Round-Up"
			+ "204840-To Verify 'Charity Round-Up' Dropdown default option")
	public void verifyCharityRoundUpDropdown() {
		final String CASE_NUM = "204839";
				// Reading test data from DataBase
				rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
				rstRoundUpCharityData = dataBase.getRoundUpCharity(Queries.ROUNDUP_CHARITY, CASE_NUM);
		
				List<String> menu = Arrays
						.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
				List<String> dropDownData = Arrays
						.asList(rstRoundUpCharityData.get(CNRoundUpCharity.NAME).split(Constants.DELIMITER_TILD));
        try {		
			// Select Menu and Menu Item
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORGANIZATION, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to super>RNous Org
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));

			// verify "Charity Roundup field" dropDown
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, rstRoundUpCharityData.get(CNRoundUpCharity.DISPLAY_NAME), Constants.TEXT);
			String value = dropDown.getSelectedItem(OrgSummary.DPD_COUNTRY);
			if (value.equals(rstRoundUpCharityData.get(CNRoundUpCharity.DISPLAY_NAME))) {
				adminRoundUpCharity.verifyCharityDropdown(dropDownData, AdminRoundUpCharity.DPD_CHARITY_ROUNDUP);
			}

			// Select roundup dropDown value
			adminRoundUpCharity.verifyCharityOptions(dropDownData.get(0));

			// Navigate to Admin tab and verify RoundupCharity Sub Tab is not present
			List<String> tabNames = navigationBar.getSubTabs(menu.get(1));
			CustomisedAssert
					.assertFalse(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));

			// Select roundup dropDown value and validate default value
			navigationBar.navigateToMenuItem(menu.get(0));
			String item = dropDown.getSelectedItem(AdminRoundUpCharity.DPD_CHARITY_ROUNDUP);
			CustomisedAssert.assertEquals(item, dropDownData.get(0));
			adminRoundUpCharity.verifyCharityOptions(dropDownData.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgList.LBL_ORG_LIST));

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			tabNames = navigationBar.getSubTabs(menu.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));
			foundation.threadWait(3);
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}finally {
			navigationBar.navigateToMenuItem(menu.get(0));
			dropDown.selectItem(AdminRoundUpCharity.DPD_CHARITY_ROUNDUP, dropDownData.get(0), Constants.TEXT);
			foundation.threadWait(3);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY,rstRoundUpCharityData.get(CNRoundUpCharity.REQUIRED_OPTIONS), Constants.TEXT);
			CustomisedAssert.assertTrue(dropDown.getSelectedItem(OrgSummary.DPD_COUNTRY).equals(rstRoundUpCharityData.get(CNRoundUpCharity.REQUIRED_OPTIONS)));
			foundation.waitforElementToBeVisible(OrgSummary.BTN_SAVE, Constants.THREE_SECOND);
			foundation.click(OrgSummary.BTN_SAVE);
			foundation.threadWait(3);
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgList.LBL_ORG_LIST));
		}
	}

	/**
	 * @author afrosean Date:17-10-2022
	 */
	@Test(description = "204860-To Verify 'Round Up Charity' Option is displayed by creating new Org with Country as 'United States'"
			+ "204861-To Verify 'Round Up Charity' Option is not displayed by creating new Org with Country other than 'United States'")
	public void verifyRoundUpCharityOptionByCreatingNewOrgWithCountryUS() {
		try {
			final String CASE_NUM = "204860";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> datas = Arrays.asList(
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORGANIZATION, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to super>RNous Org
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgList.LBL_ORG_LIST));

			// verify disable roundup charity dropDown
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.ROUND_UP_CHARITY));

			// verify round up charity dropDown and default value
			orgList.verifyRoundupCharityAndDefaultCharityDropdown(datas.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.ROUND_UP_CHARITY));
			String item = dropDown.getSelectedItem(AdminRoundUpCharity.DPD_CHARITY_ROUNDUP);
			CustomisedAssert.assertEquals(item, datas.get(0));
			
			//Change country other than 'us' and verify rounupcharity
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgList.LBL_ORG_CREATE));
			dropDown.selectItem(OrgSummary.DPD_COUNTRY, datas.get(2), Constants.TEXT);
			foundation.waitforElementToBeVisible(OrgSummary.ROUND_UP_CHARITY, Constants.THREE_SECOND);
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.ROUND_UP_CHARITY));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * @author sakthir Date:21-10-2022
	 */
	@Test(description = "204837-SOS-31133:To Verify Round Up Charity settings under Org summary page when \"United States\" country is selected"
			+ "204838-SOS-31133:To Verify Round Up Charity settings under Org summary page when any country other than United States is selected")
	public void verifyUnitedStatesAndOtherCountryForRoundUpCharityField() {
		final String CASE_NUM = "204837";
			
		    // Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> data = Arrays.asList(
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

			try {
			// Select Menu and Menu Item
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORGANIZATION, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to super>RNous Org
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));
			
			//verify country Fields as United States 
			foundation.scrollIntoViewElement(OrgSummary.DPD_COUNTRY);
			if (!(OrgSummary.DPD_COUNTRY).equals(data.get(0))) {
				dropDown.selectItem(OrgSummary.DPD_COUNTRY,data.get(0), Constants.TEXT);
				}

			// verify roundup charity Field
			foundation.scrollIntoViewElement(OrgSummary.CHK_AGE_VERIFICATION);
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.ROUND_UP_CHARITY));

			//select other country and verify charity field not displayed
			foundation.scrollIntoViewElement(OrgSummary.DPD_COUNTRY);
			dropDown.selectItem(OrgSummary.DPD_COUNTRY,data.get(1), Constants.TEXT);
			foundation.scrollIntoViewElement(OrgSummary.CHK_AGE_VERIFICATION);
			CustomisedAssert.assertFalse(foundation.isDisplayed(OrgSummary.ROUND_UP_CHARITY));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	/**
	 * @author sakthir Date:25-10-2022
	 */
	@Test(description = "204843-SOS-32197:To Verify \"Round Up for Charity\" page"
			+ "204844-SOS-32198:To Verify Search functionality under \"Round Up for Charity\" page")
	public void verifyRoundUpCharityFunctionalityAndTableUsingSearch() {
		final String CASE_NUM = "204843";
			
		    // Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> data = Arrays.asList(
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));

			try {
			// Select Menu and Menu Item
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

//			// navigate to super>automation Org
//			navigationBar.navigateToMenuItem(menu.get(0));
//			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));
//			
//			//verify country Fields as United States and verify roundup charity Field Enable
//			adminRoundUpCharity.verifyCountryAndSelectCharityOption(data.get(0),data.get(1));
//			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgList.LBL_ORG_LIST));
			
			//Navigate to Admin->Round Up Charity and verify page
			navigationBar.navigateToMenuItem(menu.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ROUNDUPCHARITY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ACITIVECHARITY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_ADDCHARITY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.TXT_SEARCH));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.TBL_GRID));
			
			//search and verify grid
			textBox.enterText(AdminRoundUpCharity.TXT_SEARCH, data.get(4));
			CustomisedAssert.assertTrue(foundation.getText(AdminRoundUpCharity.SELECT_ROW_CAUSENAME).equals(data.get(4)));
			}
			catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
			}
//			finally {
//				//resetting
//				navigationBar.navigateToMenuItem(menu.get(0));
//				CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));
//				adminRoundUpCharity.verifyCountryAndSelectCharityOption(data.get(0),data.get(2));
//			}
	}
	
	/**
	 * SOS-32204
	 * @author sakthir 
	 * Date:26-10-2022
	 */
	@Test(description = "204863-Verify save button functionality while creating the charity"
			+"204864-Verify save button functionality while Editing the charity"
			+"204865-Verify cancel button functionality while creating the charity"
			+"204866-Verify cancel button functionality while Editing the charity")
	public void verifySaveAndCancelButtonFunctionalityForOperator() {
		final String CASE_NUM = "204863";
			
		    // Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstRoundUpCharityData = dataBase.getRoundUpCharity(Queries.ROUNDUP_CHARITY, CASE_NUM);

			List<String> menu = Arrays.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> data = Arrays.asList(
					rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION).split(Constants.DELIMITER_TILD));
			List<String> location = Arrays
					.asList(rstRoundUpCharityData.get(CNRoundUpCharity.NAME).split(Constants.DELIMITER_TILD));

			try {
//			// Select Menu and Menu Item
//				navigationBar.launchBrowserAsSuperAndSelectOrg(
//						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
//
//			// navigate to super>automation Org
//			navigationBar.navigateToMenuItem(menu.get(0));
//			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));
//			
//			//verify country Fields as United States and verify roundup charity Field Enable
//			adminRoundUpCharity.verifyCountryAndSelectCharityOption(data.get(0),data.get(1));
//			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgList.LBL_ORG_LIST));
//			foundation.waitforElement(AdminRoundUpCharity.LBL_ROUNDUPCHARITY,3);
//			login.logout();
			
			// login as Operator
			navigationBar.launchBrowserAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			//Navigate to Admin->Round Up Charity and verify page
			navigationBar.navigateToMenuItem(menu.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ROUNDUPCHARITY));
			
			//Click on Add Charity and enter fields and click on save
			foundation.click(AdminRoundUpCharity.BTN_ADDCHARITY);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ADD_CHARITY));
			textBox.enterText(AdminRoundUpCharity.TXT_EIN_SEARCH, data.get(11));
			CustomisedAssert.assertFalse(foundation.isDisplayed(AdminRoundUpCharity.TXT_DISPLAYNAME));
			foundation.click(AdminRoundUpCharity.BTN_SEARCH);
			adminRoundUpCharity.selectLocationFromDropDown(data.get(12));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.TXT_DISPLAYNAME));
			textBox.enterText(AdminRoundUpCharity.TXT_DISPLAYNAME, data.get(10));
    		CustomisedAssert.assertTrue(foundation.getText(AdminRoundUpCharity.GET_LOCATION).equals(data.get(13)));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_SAVE));
			foundation.click(AdminRoundUpCharity.BTN_SAVE);
			textBox.enterText(AdminRoundUpCharity.TXT_SEARCH, data.get(11));
			CustomisedAssert.assertEquals(foundation.getText(AdminRoundUpCharity.SELECT_ROW_CAUSENAME), data.get(16));
			
			//Click on Add Charity and Click and verify Cancel
			foundation.click(AdminRoundUpCharity.BTN_ADDCHARITY);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ADD_CHARITY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_CANCEL));
			foundation.click(AdminRoundUpCharity.BTN_CANCEL);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ROUNDUPCHARITY));
					
			//search and Click existing on charity 
			adminRoundUpCharity.selectCharity(data.get(4));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_EDIT_CHARITY));
			CustomisedAssert.assertTrue(foundation.getTextofListElement(AdminRoundUpCharity.GET_LOCATION).equals(location));
			
			// remove single location and Click and verify save button
			List<String> Location=foundation.getTextofListElement(AdminRoundUpCharity.GET_LOCATION);
			foundation.threadWait(5);
			foundation.click(adminRoundUpCharity.removeLocation(data.get(5)));
			textBox.enterText(AdminRoundUpCharity.TXT_DISPLAYNAME, data.get(10));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_SAVE));
			foundation.click(AdminRoundUpCharity.BTN_SAVE);
			foundation.waitforElement(AdminRoundUpCharity.LBL_ROUNDUPCHARITY,3);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ROUNDUPCHARITY));
			foundation.threadWait(5);
			textBox.enterText(AdminRoundUpCharity.TXT_SEARCH, data.get(4));
			CustomisedAssert.assertTrue(foundation.getTextofListElement(AdminRoundUpCharity.SELECT_ROW).contains(data.get(10)));
			
			//verify the location and Add location click on save
			adminRoundUpCharity.selectCharity(data.get(4));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_EDIT_CHARITY));
			CustomisedAssert.assertTrue(foundation.getText(AdminRoundUpCharity.GET_LOCATION).equals(Location.get(1)));
			adminRoundUpCharity.selectLocationFromDropDown(data.get(7));
			CustomisedAssert.assertTrue(foundation.getTextofListElement(AdminRoundUpCharity.GET_LOCATION).contains(data.get(15)));
			foundation.click(AdminRoundUpCharity.BTN_SAVE);
			
			//remove all location and Add location click on save
			adminRoundUpCharity.selectCharity(data.get(4));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_EDIT_CHARITY));
			foundation.click(AdminRoundUpCharity.CLEAR_LOCATION);
			adminRoundUpCharity.selectLocationFromDropDown(data.get(9));
     		CustomisedAssert.assertTrue(foundation.getText(AdminRoundUpCharity.GET_LOCATION).equals(data.get(14)));
			foundation.click(AdminRoundUpCharity.BTN_SAVE);
			
			//search and Click existing on charity and Click and verify Cancel
			adminRoundUpCharity.selectCharity(data.get(4));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_EDIT_CHARITY));
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_CANCEL));
			foundation.click(AdminRoundUpCharity.BTN_CANCEL);
			foundation.waitforElement(AdminRoundUpCharity.LBL_ROUNDUPCHARITY,3);
			CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ROUNDUPCHARITY));
			
			}catch (Exception exc) {
				TestInfra.failWithScreenShot(exc.toString());
		}
				finally {
				//resetting
				navigationBar.navigateToMenuItem(menu.get(1));
				CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ROUNDUPCHARITY));
				textBox.enterText(AdminRoundUpCharity.TXT_SEARCH, data.get(11));
				foundation.click(AdminRoundUpCharity.BTN_DELECT_CHARITY);
				CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_DELECT_CHARITY));
				CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_DELECT_CHARITY_CANCEL));
				CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.BTN_DELECT_CHARITY_DELECT));
				foundation.click(AdminRoundUpCharity.BTN_DELECT_CHARITY_DELECT);
				foundation.waitforElementToBeVisible(AdminRoundUpCharity.TXT_SEARCH, 3);
				foundation.threadWait(5);
				adminRoundUpCharity.selectCharity(data.get(4));
				CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_EDIT_CHARITY));
				foundation.click(adminRoundUpCharity.removeLocation(data.get(9)));
				adminRoundUpCharity.selectLocationFromDropDown(data.get(5));
				adminRoundUpCharity.selectLocationFromDropDown(data.get(6));
				textBox.enterText(AdminRoundUpCharity.TXT_DISPLAYNAME, data.get(3));
				CustomisedAssert.assertTrue(foundation.getTextofListElement(AdminRoundUpCharity.GET_LOCATION).equals(location));
				foundation.click(AdminRoundUpCharity.BTN_SAVE);
				foundation.waitforElement(AdminRoundUpCharity.LBL_ROUNDUPCHARITY,3);
				CustomisedAssert.assertTrue(foundation.isDisplayed(AdminRoundUpCharity.LBL_ROUNDUPCHARITY));
				CustomisedAssert.assertTrue(foundation.getTextofListElement(AdminRoundUpCharity.SELECT_ROW).contains(data.get(3)));
//				login.logout();
//				navigationBar.launchBrowserAsSuperAndSelectOrg(
//						propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
//				navigationBar.navigateToMenuItem(menu.get(0));
//				CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));
//				adminRoundUpCharity.verifyCountryAndSelectCharityOption(data.get(0),data.get(2));
			}
	}
	
	
}
