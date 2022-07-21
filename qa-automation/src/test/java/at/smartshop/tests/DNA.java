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
	private TextBox textBox = new TextBox();

	private Map<String, String> rstAdminDNAData;
	private Map<String, String> rstNavigationMenuData;

	@Test(description = "177520 - verify the Enabling of DNA option" + "177526 - verifying the saving of DNA")
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
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin tab and verify DNA Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(0));
			CustomisedAssert.assertTrue(tabNames.contains(requiredData.get(2)));
			navigationBar.navigateToMenuItem(menus.get(1));

			// Validating the Is-Disabled combo box defaults
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.HEADER_DNA));
			String text = foundation.getText(DNADetails.HEADER_DNA);
			CustomisedAssert.assertEquals(text, requiredData.get(3));
			text = foundation.getText(DNADetails.LOCATION_NAME);
			CustomisedAssert.assertEquals(text, rstAdminDNAData.get(CNAdminDNA.LOCATION_NAME));
			foundation.waitforElementToBeVisible(DNADetails.IS_DISABLED_COMBO_BOX, Constants.SHORT_TIME);
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

			// Validating the error message while saving without values in the fields
			foundation.click(DNADetails.BTN_SAVE);
			foundation.waitforElementToDisappear(DNADetails.ERROR_MESSAGE, Constants.SHORT_TIME);
			text = foundation.getOutLineColor(DNADetails.GREEN_MAX_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(5));
			text = foundation.getOutLineColor(DNADetails.YELLOW_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(5));
			text = foundation.getOutLineColor(DNADetails.YELLOW_MAX_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(5));
			text = foundation.getOutLineColor(DNADetails.RED_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(5));

			// Verify Calories Tab
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.CALORIES_TAB));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.BTN_SAVE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.TEXT_APPLY_LOCATION));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	@Test(description = "177521 - Verifying the color ranges of caloric for min and max"
			+ "177522 - Verifying the configuration of DNA to single location"
			+ "177523 - Verifying the configuration of DNA to all Location"
			+ "177524 - Verifying the auto pupulated ranges for the caloric ranges"
			+ "177525 - Verifying the Access icon for the caloric ranges")
	public void verifyCaloriesFieldsDNAConfigurationAndCaloricRanges() {
		final String CASE_NUM = "177521";

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
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Navigate to Admin tab and verify DNA Sub Tab is present or not
			List<String> tabNames = navigationBar.getSubTabs(menus.get(0));
			CustomisedAssert.assertTrue(tabNames.contains(requiredData.get(2)));
			navigationBar.navigateToMenuItem(menus.get(1));

			// Validating the Is-Disabled combo box and Access Icon
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.HEADER_DNA));
			foundation.click(DNADetails.ACCESS_ICON);
			String text = foundation.getText(DNADetails.TOOL_TIP_TEXT);
			foundation.threadWait(Constants.SHORT_TIME);
			CustomisedAssert.assertEquals(text, rstNavigationMenuData.get(CNNavigationMenu.REQUIRED_OPTION));
			foundation.click(DNADetails.IS_DISABLED_COMBO_BOX);
			CustomisedAssert.assertFalse(foundation.isDisplayed(DNADetails.TOOL_TIP_TEXT));
			foundation.waitforElementToBeVisible(DNADetails.DD_ISDISABLED, Constants.SHORT_TIME);
			text = dropDown.getSelectedItem(DNADetails.DD_ISDISABLED);
			CustomisedAssert.assertEquals(text, requiredData.get(1));

			// Verify Calories Field and its color's
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.CALORIES_TAB));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.BTN_SAVE));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.TEXT_APPLY_LOCATION));
			text = foundation.getStyleUsingPseudoElemet(requiredData.get(5), requiredData.get(6), requiredData.get(21));
			CustomisedAssert.assertEquals(text, requiredData.get(7));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.GREEN_MIN_FIELD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.GREEN_MAX_FIELD));
			text = foundation.getStyleUsingPseudoElemet(requiredData.get(8), requiredData.get(6), requiredData.get(21));
			CustomisedAssert.assertEquals(text, requiredData.get(9));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.YELLOW_MIN_FIELD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.YELLOW_MAX_FIELD));
			text = foundation.getStyleUsingPseudoElemet(requiredData.get(10), requiredData.get(6),
					requiredData.get(21));
			CustomisedAssert.assertEquals(text, requiredData.get(11));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.RED_MIN_FIELD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.RED_MAX_FIELD));

			// Verify Editable Fields
			text = foundation.getBGColor(DNADetails.GREEN_MAX_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(12));
			text = foundation.getBGColor(DNADetails.YELLOW_MAX_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(12));
			text = foundation.getBGColor(DNADetails.YELLOW_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(12));
			text = foundation.getBGColor(DNADetails.RED_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(12));

			// Verify Non-Editable Fields
			text = foundation.getBGColor(DNADetails.GREEN_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(13));
			text = foundation.getBGColor(DNADetails.RED_MAX_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(13));

			// Verify Default Value in the fields
			text = foundation.getAttributeValue(DNADetails.GREEN_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(14));
			text = foundation.getAttributeValue(DNADetails.RED_MAX_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(15));

			// Setting the values for the fields and validating auto populated values in
			// Yellow min, Red min
			textBox.enterText(DNADetails.GREEN_MAX_FIELD, requiredData.get(16));
			foundation.click(DNADetails.IS_DISABLED_COMBO_BOX);
			text = foundation.getAttributeValue(DNADetails.YELLOW_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(19));
			textBox.enterText(DNADetails.YELLOW_MAX_FIELD, requiredData.get(17));
			foundation.click(DNADetails.IS_DISABLED_COMBO_BOX);
			text = foundation.getAttributeValue(DNADetails.RED_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(20));
			foundation.click(DNADetails.BTN_SAVE);
			foundation.waitforElementToBeVisible(DNADetails.POPUP_SUCCESS, Constants.SHORT_TIME);
			text = foundation.getText(DNADetails.POPUP_CONTENT);
			CustomisedAssert.assertEquals(text, requiredData.get(18));
			foundation.click(DNADetails.BTN_OK);
			navigationBar.navigateToMenuItem(menus.get(2));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));

			// Verifying Entered Values are reflected properly or not
			navigationBar.navigateToMenuItem(menus.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(DNADetails.HEADER_DNA));
			foundation.waitforElementToBeVisible(DNADetails.DD_ISDISABLED, Constants.SHORT_TIME);
			text = foundation.getAttributeValue(DNADetails.GREEN_MAX_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(16));
			text = foundation.getAttributeValue(DNADetails.YELLOW_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(19));
			text = foundation.getAttributeValue(DNADetails.YELLOW_MAX_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(17));
			text = foundation.getAttributeValue(DNADetails.RED_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(20));

			// Applying DNA confirguration to all Locations
			foundation.click(DNADetails.TEXT_APPLY_LOCATION);
			foundation.waitforElementToBeVisible(DNADetails.POPUP_SUCCESS, Constants.SHORT_TIME);
			foundation.click(DNADetails.BTN_OK);
			foundation.waitforElementToBeVisible(DNADetails.DD_ISDISABLED, Constants.SHORT_TIME);

			// Verifying the values in other location - AutoLocation3
			foundation.scrollIntoViewElement(DNADetails.LOCATION_LIST);
			foundation.threadWait(Constants.THREE_SECOND);
			foundation.click(DNADetails.LOCATION_LIST);
			foundation.waitforElementToBeVisible(DNADetails.DD_ISDISABLED, Constants.SHORT_TIME);
			text = foundation.getAttributeValue(DNADetails.GREEN_MAX_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(16));
			text = foundation.getAttributeValue(DNADetails.YELLOW_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(19));
			text = foundation.getAttributeValue(DNADetails.YELLOW_MAX_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(17));
			text = foundation.getAttributeValue(DNADetails.RED_MIN_FIELD);
			CustomisedAssert.assertEquals(text, requiredData.get(20));
			foundation.click(DNADetails.IS_DISABLED_COMBO_BOX);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
