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
import at.smartshop.pages.AgeVerificationDetails;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.OrgSummary;

@Listeners(at.framework.reportsetup.Listeners.class)
public class RoundUpCharity extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private NavigationBar navigationBar = new NavigationBar();
	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private AdminRoundUpCharity adminRoundUpCharity = new AdminRoundUpCharity();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstRoundUpCharityData;

	
	/**
	 * @author afrosean
	 * Date:14-10-2022
	 */
	@Test(description = "204839-To Verify 'Charity Round-Up' Dropdown"
			+ "204842-To Verify Admin menu when 'Disabled' Option is selected under Charity Round-Up"
			+ "204841-To Verify Admin menu when 'Enabled' Option is selected under Charity Round-Up"
			+ "204840-To Verify 'Charity Round-Up' Dropdown default option")
	public void verifyCharityRoundUpDropdown() {
		try {
			final String CASE_NUM = "204839";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstRoundUpCharityData = dataBase.getRoundUpCharity(Queries.ROUNDUP_CHARITY, CASE_NUM);

			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> dropDownData = Arrays
					.asList(rstRoundUpCharityData.get(CNRoundUpCharity.NAME).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to super>automation Org
			navigationBar.navigateToMenuItem(menu.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(OrgSummary.LBL_ORG_SUMMARY));

			// verify "Charity Roundup field" dropDown
			String value = dropDown.getSelectedItem(OrgSummary.DPD_COUNTRY);
			if (value.equals(rstRoundUpCharityData.get(CNRoundUpCharity.DISPLAY_NAME))) {
				adminRoundUpCharity.verifyCharityDropdown(dropDownData, AdminRoundUpCharity.DPD_CHARITY_ROUNDUP);
			}

			// Select roundup dropDown value
			adminRoundUpCharity.verifyCharityOptions(dropDownData.get(0));

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menu.get(1));
			CustomisedAssert
					.assertFalse(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));

			// Select roundup dropDown value and validate default value
			navigationBar.navigateToMenuItem(menu.get(0));
			String item = dropDown.getSelectedItem(AdminRoundUpCharity.DPD_CHARITY_ROUNDUP);
			CustomisedAssert.assertEquals(item, dropDownData.get(0));
			adminRoundUpCharity.verifyCharityOptions(dropDownData.get(1));

			// Navigate to Admin tab and verify Age Verification Sub Tab is present or not
			tabNames = navigationBar.getSubTabs(menu.get(1));
			CustomisedAssert.assertTrue(tabNames.contains(rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION)));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * @author afrosean
	 * Date:17-10-2022
	 */
	@Test(description="")
	public void verify() {
		try {
			final String CASE_NUM = "204839";
			// Reading test data from DataBase
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstRoundUpCharityData = dataBase.getRoundUpCharity(Queries.ROUNDUP_CHARITY, CASE_NUM);

			List<String> menu = Arrays
					.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
			List<String> dropDownData = Arrays
					.asList(rstRoundUpCharityData.get(CNRoundUpCharity.NAME).split(Constants.DELIMITER_TILD));

			// Select Menu and Menu Item
			navigationBar.launchBrowserAsSuperAndSelectOrg(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));

			// navigate to super>automation Org
			navigationBar.navigateToMenuItem(menu.get(0));
		}
		catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
