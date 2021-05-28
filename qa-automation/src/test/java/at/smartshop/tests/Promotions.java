package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.PropertyFile;
import at.framework.generic.Strings;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNLocationSummary;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.CreatePromotions;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.NavigationBar;
import at.smartshop.pages.PromotionList;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Promotions extends TestInfra {

	private PropertyFile propertyFile = new PropertyFile();
	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private NavigationBar navigationBar = new NavigationBar();
	private CreatePromotions createPromotions = new CreatePromotions();
	private Strings strings=new Strings(); 
	private Dropdown dropDown = new Dropdown();
	private LocationList locationList=new LocationList();
	private TextBox textBox=new TextBox();

	private Map<String, String> rstNavigationMenuData;
	private Map<String, String> rstLocationData;
	private Map<String, String> rstLocationSummaryData;
	private Map<String, String> rstLocationListData;

	@Test(description = "Verify All option is displayed in Location Dropdown")
	public void verifyPromotions() {
		try {
			final String CASE_NUM = "141771";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);

			String promotionName = strings.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, requiredData.get(0), requiredData.get(1),
					locationName);

			// Validating "All" option in Location field
			String uiData = dropDown.getSelectedItem(CreatePromotions.DPD_LOCATION);
			Assert.assertEquals(uiData, locationName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "C141772-SOS-7520 - Verify Promotion List page display only active location")
	public void verifyPromotionsActiveLocation() {
		try {
			final String CASE_NUM = "141772";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);

			String promotionName =  strings.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);

			List<String> locationDisabled = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));

			String locationDisabled_No = locationDisabled.get(1);
			// Selecting location
			locationList.selectLocationName(locationName);

			foundation.waitforElement(LocationSummary.DPD_DISABLED, 2000);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled_No, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);
			login.logout();

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			createPromotions.newPromotion(promotionType, promotionName, requiredData.get(0), requiredData.get(1),
					locationName);

			// Validating "Active location name in Location field
			String uiData = dropDown.getSelectedItem(CreatePromotions.DPD_LOCATION);
			Assert.assertEquals(uiData, locationName);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "C141773-SOS-7519 - Verify Create Promotion page display only active location when location filter is selected")
	public void verifyPromotionsDisabledLocation() {
		try {
			final String CASE_NUM = "141773";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Reading test data from database
			rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
			rstLocationData = dataBase.getLocationData(Queries.LOCATION, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			
			String promotionName =  strings.getRandomCharacter();
			String promotionType = rstLocationData.get(CNLocation.PROMOTION_TYPE);
			String locationName = rstLocationData.get(CNLocation.LOCATION_NAME);
			String locationList_Dpd =rstLocationListData.get(CNLocationList.DROPDOWN_LOCATION_LIST);
			
			rstLocationSummaryData = dataBase.getLocationSummaryData(Queries.LOCATION_SUMMARY, CASE_NUM);


			List<String> locationDisabled = Arrays.asList(
					rstLocationSummaryData.get(CNLocationSummary.LOCATION_DISABLED).split(Constants.DELIMITER_TILD));
			String locationDisabled_Yes = locationDisabled.get(0);
			String locationDisabled_No  = locationDisabled.get(1);
			// Selecting location
			locationList.selectLocationName(locationName);

			foundation.waitforElement(LocationSummary.DPD_DISABLED, 2000);
			dropDown.selectItem(LocationSummary.DPD_DISABLED, locationDisabled_Yes, Constants.TEXT);
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.click(LocationSummary.POP_UP_BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);
			login.logout();

			List<String> requiredData = Arrays
					.asList(rstLocationData.get(CNLocation.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Select Org,Menu and Menu Item
			login.login(propertyFile.readPropertyFile(Configuration.OPERATOR_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));

			// New Promotion
			foundation.click(PromotionList.BTN_CREATE);
			dropDown.selectItem(CreatePromotions.DPD_PROMO_TYPE, promotionType, Constants.TEXT);
			textBox.enterText(CreatePromotions.TXT_PROMO_NAME, promotionName);
			textBox.enterText(CreatePromotions.TXT_DISPLAY_NAME, requiredData.get(0));
			foundation.click(CreatePromotions.BTN_NEXT);

			textBox.enterText(CreatePromotions.DPD_ORG, requiredData.get(1));
			textBox.enterText(CreatePromotions.DPD_ORG, Keys.ENTER);
			foundation.click(CreatePromotions.TXT_LOCATION);
			textBox.enterText(CreatePromotions.TXT_LOCATION, locationName);

			Assert.assertTrue(foundation.isDisplayed(createPromotions.objLocation(requiredData.get(2))));

			login.logout();
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));
			
			dropDown.selectItem(LocationList.DPD_LOCATION_LIST, locationList_Dpd, Constants.TEXT);
			locationList.selectLocationName(locationName);
			foundation.waitforElement(LocationSummary.DPD_DISABLED, 2000);
			dropDown.selectItem(LocationSummary.DPD_DISABLED,locationDisabled_No, "text");
			foundation.click(LocationSummary.BTN_SAVE);
			foundation.waitforElement(LocationSummary.LBL_SPINNER_MSG, 2000);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
