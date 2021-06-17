package at.smartshop.v5.tests;


import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.Strings;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.LocationSummary;
import at.smartshop.pages.Login;
import at.smartshop.pages.NavigationBar;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.EditAccount;
import at.smartshop.v5.pages.LandingPage;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {

	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private ResultSets dataBase = new ResultSets();
	private LandingPage landingPage = new LandingPage();
	private AccountLogin accountLogin = new AccountLogin();
	private EditAccount editAccount = new EditAccount();
	private LocationList locationList = new LocationList();
	private NavigationBar navigationBar = new NavigationBar();
	private Strings string = new Strings();
	private LocationSummary locationSummary = new LocationSummary();

	private Map<String, String> rstV5DeviceData;
	private Map<String, String> rstLocationListData;
	

	@Test(description = "141874-Kiosk Manage Account > Edit Account > Update Information")
	public void editAccountUpdateInformation() {
		final String CASE_NUM = "141874";

		browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

		rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
		List<String> requiredData = Arrays
				.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

		// login to application
		foundation.click(landingPage.objLanguage(requiredData.get(0)));
		foundation.click(LandingPage.BTN_LOGIN);
		foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
		accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));

		// navigate to edit account and get previous information
		foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
		String firstName = textBox.getTextFromInput(EditAccount.TXT_FIRST_NAME);
		String lastName = textBox.getTextFromInput(EditAccount.TXT_LAST_NAME);
		String emailAddress = textBox.getTextFromInput(EditAccount.TXT_EMAIL_ADDRESS);

		// update information
		foundation.click(EditAccount.BTN_CAMEL_CASE);
		editAccount.updateText(EditAccount.TXT_FIRST_NAME, requiredData.get(1), firstName);
		editAccount.updateText(EditAccount.TXT_LAST_NAME, requiredData.get(2), lastName);
		editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, requiredData.get(3), emailAddress);
		foundation.click(EditAccount.BTN_SAVE);
		assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));

		// navigate back to edit account and verify all data are populating as entered
		// before
		foundation.click(EditAccount.BTN_EDIT_ACCOUNT);
		assertTrue(textBox.getTextFromInput(EditAccount.TXT_FIRST_NAME).equals(requiredData.get(1)));
		assertTrue(textBox.getTextFromInput(EditAccount.TXT_LAST_NAME).equals(requiredData.get(2)));
		assertTrue(textBox.getTextFromInput(EditAccount.TXT_EMAIL_ADDRESS).equals(requiredData.get(3)));

		// reset the data
		foundation.click(EditAccount.BTN_CAMEL_CASE);
		editAccount.updateText(EditAccount.TXT_FIRST_NAME, firstName, requiredData.get(1));
		editAccount.updateText(EditAccount.TXT_LAST_NAME, lastName, requiredData.get(2));
		editAccount.updateText(EditAccount.TXT_EMAIL_ADDRESS, emailAddress, requiredData.get(3));
		foundation.click(EditAccount.BTN_SAVE);
		assertTrue(foundation.isDisplayed(EditAccount.BTN_EDIT_ACCOUNT));
	}

	@Test(description = "C142695-SOS-24493-verify multiple home commercials are displayed on V5 Device when multiple homecommercials are uploaded in Adm")
	public void verifyMultipleHomeCommercial() {
		try {
			final String CASE_NUM = "142695";

			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Reading test data from DataBase
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
			String locationName = rstLocationListData.get(CNLocationList.LOCATION_NAME);
			final String cmrName1 = string.getRandomCharacter();
			final String cmrName2 = string.getRandomCharacter();
			List<String> requiredData =  Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			// Selecting location
			textBox.enterText(LocationList.TXT_FILTER, locationName);
			locationList.selectLocationName(locationName);

			// upload image-1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, 2);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_PATH);
			textBox.enterText(LocationSummary.TXT_ADD_NAME, cmrName1);
			foundation.click(LocationSummary.BTN_ADD);
			// upload image-2
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, 2);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			foundation.click(LocationSummary.BTN_ADD_HOME_COMMERCIAL);
			foundation.click(LocationSummary.TXT_UPLOAD_NEW);
			textBox.enterText(LocationSummary.BTN_UPLOAD_INPUT, FilePath.IMAGE_PNG_PATH);
			textBox.enterText(LocationSummary.TXT_ADD_NAME, cmrName2);
			foundation.click(LocationSummary.BTN_ADD);
			
			foundation.click(LocationSummary.BTN_SYNC);
			foundation.isDisplayed(LocationSummary.LBL_SPINNER_MSG);
			foundation.waitforElement(Login.LBL_USER_NAME, 5);
			login.logout();
			browser.close();
			// launching v5 device
			browser.launch(Constants.REMOTE, Constants.CHROME);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData.get(0)), 10);
			assertTrue(foundation.isDisplayed(landingPage.objImageDisplay(requiredData.get(0))));
			foundation.waitforElement(landingPage.objImageDisplay(requiredData.get(1)), 10);
			assertTrue(foundation.isDisplayed(landingPage.objImageDisplay(requiredData.get(1))));

			// resetting test data
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));

			navigationBar.selectOrganization(
					propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));
			// Selecting location
			textBox.enterText(LocationList.TXT_FILTER, locationName);
			locationList.selectLocationName(locationName);
			//removing cmr1
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, 2);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			textBox.enterText(LocationSummary.TXT_CMR_FILTER, cmrName1);
			foundation.click(locationSummary.objHomeCommercial(cmrName1));
			foundation.waitforElement(LocationSummary.BTN_REMOVE, 5);
			foundation.click(LocationSummary.BTN_REMOVE);
			//removing cmr2
			foundation.waitforElement(LocationSummary.BTN_HOME_COMMERCIAL, 2);
			foundation.click(LocationSummary.BTN_HOME_COMMERCIAL);
			textBox.enterText(LocationSummary.TXT_CMR_FILTER, cmrName2);
			foundation.click(locationSummary.objHomeCommercial(cmrName2));
			foundation.waitforElement(LocationSummary.BTN_REMOVE, 5);
			foundation.click(LocationSummary.BTN_REMOVE);
			foundation.waitforElement(LocationSummary.BTN_SYNC, 5);
			foundation.click(LocationSummary.BTN_SYNC);

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail();
		}
	}
}
