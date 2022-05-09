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
import at.smartshop.database.columns.CNAdminDNA;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Constants;
import at.smartshop.pages.DNADetails;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.NavigationBar;

@Listeners(at.framework.reportsetup.Listeners.class)

public class DNA extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private Dropdown dropDown = new Dropdown();

	private Map<String, String> rstAdminDNAData;
	private Map<String, String> rstNavigationMenuData;

	@Test(description = "177520 - verify the Enabling of DNA option")
	public void verifyEnablingDNAOption() {
		final String CASE_NUM = "177520";

		// Reading test data from database
		rstAdminDNAData = dataBase.getAdminDNAData(Queries.ADMIN_DNA, CASE_NUM);
		rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstAdminDNAData.get(CNAdminDNA.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> menus = Arrays
				.asList(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM).split(Constants.DELIMITER_TILD));
		try {
			// Select Menu
			navigationBar.launchBrowserAsSuperAndSelectOrg(rstAdminDNAData.get(CNAdminDNA.ORG_NAME));
			foundation.threadWait(Constants.THREE_SECOND);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin tab and verify DNA Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(0));
			CustomisedAssert.assertEquals(tabNames.get(14), requiredData.get(2));
			navigationBar.navigateToMenuItem(menus.get(1));

			// Validating the Is-Disabled combo box defaults
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.HEADER_DNA));
			String text = foundation.getText(DNADetails.HEADER_DNA);
			CustomisedAssert.assertEquals(text, requiredData.get(3));
			text = foundation.getText(DNADetails.LOCATION_NAME);
			CustomisedAssert.assertEquals(text, rstAdminDNAData.get(CNAdminDNA.LOCATION_NAME));
			foundation.threadWait(Constants.THREE_SECOND);
			text = foundation.getText(DNADetails.IS_DISABLED_COMBO_BOX);
			CustomisedAssert.assertEquals(text, requiredData.get(4));
			List<String> options = dropDown.getAllItems(DNADetails.DD_ISDISABLED);
			CustomisedAssert.assertEquals(options.get(0), requiredData.get(0));
			CustomisedAssert.assertEquals(options.get(1), requiredData.get(1));
			text = dropDown.getSelectedItem(DNADetails.DD_ISDISABLED);
			CustomisedAssert.assertEquals(text, requiredData.get(0));

			// Check IsDisabled box is set to No
			dropDown.selectItem(DNADetails.DD_ISDISABLED, requiredData.get(1), Constants.TEXT);
			foundation.threadWait(Constants.TWO_SECOND);
			text = dropDown.getSelectedItem(DNADetails.DD_ISDISABLED);
			CustomisedAssert.assertEquals(text, requiredData.get(1));

			// Verify Calories Tab
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.CALORIES_TAB));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.BTN_SAVE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.TEXT_APPLY_LOCATION));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
