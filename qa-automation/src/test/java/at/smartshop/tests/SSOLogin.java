package at.smartshop.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.CheckBox;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNLoginPage;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.LocationList;
import at.smartshop.pages.Login;
import at.smartshop.pages.NavigationBar;

@Listeners(at.framework.reportsetup.Listeners.class)
public class SSOLogin extends TestInfra {

	private ResultSets dataBase = new ResultSets();
	private Foundation foundation = new Foundation();
	private CheckBox checkBox = new CheckBox();
	private TextBox textBox = new TextBox();

	private Map<String, String> rstLoginPageData;

	/**
	 * Description: Validating Single Sign On for ADM application using Operator User
	 *  Author: Afrose Created on: 12th May 2022 Parameters: Username and
	 * Password Comments: Username is Passed from Config file and Password stored in
	 * DB
	 */
	@Test(description = "C175498- ADM - Sign In page to utilize Auth0 Login page using operator user")
	public void verifyLoginPageAsOperatorUser() {
		final String CASE_NUM = "175498";

		// Reading test data from database
		rstLoginPageData = dataBase.getLoginPageData(Queries.LOGIN_PAGE, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> password = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.PASSWORD).split(Constants.DELIMITER_TILD));

		try {
			// Launch the browser, Signin & verify the content in Microsoft page
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.ssoLogin(
					propertyFile.readPropertyFile(Configuration.SSO_USERNAME_OPERATOR, FilePath.PROPERTY_CONFIG_FILE),
					password.get(0));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TXT_FORGET_PWD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TXT_SIGNIN_ANOTHER_ACC));
			foundation.waitforElement(Login.SSO_BTN_SIGNIN, Constants.SHORT_TIME);
			foundation.click(Login.SSO_BTN_SIGNIN);

			// validate the content in stay signed in page
			foundation.waitforElement(Login.TITLE_HEADER, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TITLE_HEADER));
			String text = foundation.getText(Login.TITLE_HEADER);
			CustomisedAssert.assertEquals(text, requiredData.get(0));
			foundation.waitforElement(Login.SSO_BTN_SIGNIN, 5);
			text = foundation.getText(Login.TXT_DESCRIPTION);
			CustomisedAssert.assertTrue(text.contains(requiredData.get(1)));
			foundation.waitforElement(Login.CHECKBOX_FIELD, 5);
			text = foundation.getText(Login.SHOW_MSG);
			CustomisedAssert.assertTrue(text.contains(requiredData.get(2)));

			// verify the check box in Don't show this again
			checkBox.check(Login.CHECKBOX_FIELD);
			foundation.waitforElementToBeVisible(Login.BTN_NO, Constants.SHORT_TIME);
			checkBox.unCheck(Login.CHECKBOX_FIELD);
			foundation.click(Login.BTN_YES);

			// Landing into home page and verify
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			login.ssoLogout();

			// Login without Microsoft authenticated email id's.
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElementToBeVisible(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			login.logout();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Description: Validating Single Sign On for ADM application using Finance User
	 * Author: Afrose Created on: 12th May 2022 Parameters: Username and Password
	 * Comments: Username is Passed from Config file and Password stored in DB
	 */
	@Test(description = "C175498- ADM - Sign In page to utilize Auth0 Login page using finance user")
	public void verifyLoginPageAsFinanceUser() {
		final String CASE_NUM = "175498";

		// Reading test data from database
		rstLoginPageData = dataBase.getLoginPageData(Queries.LOGIN_PAGE, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> password = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.PASSWORD).split(Constants.DELIMITER_TILD));

		try {
			// Launch the browser, Signin & verify the content in Microsoft page
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.ssoLogin(
					propertyFile.readPropertyFile(Configuration.SSO_USERNAME_FINANCE, FilePath.PROPERTY_CONFIG_FILE),
					password.get(1));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TXT_FORGET_PWD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TXT_SIGNIN_ANOTHER_ACC));
			foundation.waitforElement(Login.SSO_BTN_SIGNIN, 5);
			foundation.click(Login.SSO_BTN_SIGNIN);

			// validate the content in stay signed in page
			foundation.waitforElement(Login.TITLE_HEADER, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TITLE_HEADER));
			String text = foundation.getText(Login.TITLE_HEADER);
			CustomisedAssert.assertEquals(text, requiredData.get(0));
			foundation.waitforElement(Login.SSO_BTN_SIGNIN, 5);
			text = foundation.getText(Login.TXT_DESCRIPTION);
			CustomisedAssert.assertTrue(text.contains(requiredData.get(1)));
			foundation.waitforElement(Login.CHECKBOX_FIELD, 5);
			text = foundation.getText(Login.SHOW_MSG);
			CustomisedAssert.assertTrue(text.contains(requiredData.get(2)));

			// verify the check box in Don't show this again
			checkBox.check(Login.CHECKBOX_FIELD);
			foundation.waitforElement(Login.BTN_NO, 5);
			checkBox.unCheck(Login.CHECKBOX_FIELD);
			foundation.click(Login.BTN_YES);

			// Landing into home page and verify
			foundation.waitforElement(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			login.ssoLogout();

			// Login without Microsoft authenticated email id's.
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			login.logout();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Description: Validating Single Sign On for ADM application using super User
	 * Author: Afrose Created on: 12th May 2022 Parameters: Username and Password
	 * Comments: Username is Passed from Config file and Password stored in DB
	 */
	@Test(description = "C175498- ADM - Sign In page to utilize Auth0 Login page using super user")
	public void verifyLoginPageAsSuperUser() {
		final String CASE_NUM = "175498";

		// Reading test data from database
		rstLoginPageData = dataBase.getLoginPageData(Queries.LOGIN_PAGE, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> password = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.PASSWORD).split(Constants.DELIMITER_TILD));

		try {
			// Launch the browser, Signin & verify the content in Microsoft page
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.ssoLogin(
					propertyFile.readPropertyFile(Configuration.SSO_USERNAME_SUPER, FilePath.PROPERTY_CONFIG_FILE),
					password.get(3));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TXT_FORGET_PWD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TXT_SIGNIN_ANOTHER_ACC));
			foundation.waitforElement(Login.SSO_BTN_SIGNIN, 5);
			foundation.click(Login.SSO_BTN_SIGNIN);

			// validate the content in stay signed in page
			foundation.waitforElement(Login.TITLE_HEADER, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TITLE_HEADER));
			String text = foundation.getText(Login.TITLE_HEADER);
			CustomisedAssert.assertEquals(text, requiredData.get(0));
			foundation.waitforElement(Login.SSO_BTN_SIGNIN, 5);
			text = foundation.getText(Login.TXT_DESCRIPTION);
			CustomisedAssert.assertTrue(text.contains(requiredData.get(1)));
			foundation.waitforElement(Login.CHECKBOX_FIELD, 5);
			text = foundation.getText(Login.SHOW_MSG);
			CustomisedAssert.assertTrue(text.contains(requiredData.get(2)));

			// verify the check box in Don't show this again
			checkBox.check(Login.CHECKBOX_FIELD);
			foundation.waitforElement(Login.BTN_NO, 5);
			checkBox.unCheck(Login.CHECKBOX_FIELD);
			foundation.click(Login.BTN_YES);

			// Landing into home page and verify
			foundation.waitforElement(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			login.ssoLogout();

			// Login without Microsoft authenticated email id's.
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			login.logout();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Description: Validating Single Sign On for ADM application using Driver User
	 * Author: Afrose Created on: 12th May 2022 Parameters: Username and Password
	 * Comments: Username is Passed from Config file and Password stored in DB
	 */
	@Test(description = "C175498- ADM - Sign In page to utilize Auth0 Login page using Driver user")
	public void verifyLoginPageAsDriverUser() {
		final String CASE_NUM = "175498";

		// Reading test data from database
		rstLoginPageData = dataBase.getLoginPageData(Queries.LOGIN_PAGE, CASE_NUM);

		List<String> requiredData = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> password = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.PASSWORD).split(Constants.DELIMITER_TILD));

		try {
			// Launch the browser, Signin & verify the content in Microsoft page
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.ssoLogin(
					propertyFile.readPropertyFile(Configuration.SSO_USERNAME_DRIVER, FilePath.PROPERTY_CONFIG_FILE),
					password.get(2));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TXT_FORGET_PWD));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TXT_SIGNIN_ANOTHER_ACC));
			foundation.waitforElement(Login.SSO_BTN_SIGNIN, 5);
			foundation.click(Login.SSO_BTN_SIGNIN);

			// validate the content in stay signed in page
			foundation.waitforElement(Login.TITLE_HEADER, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.TITLE_HEADER));
			String text = foundation.getText(Login.TITLE_HEADER);
			CustomisedAssert.assertEquals(text, requiredData.get(0));
			foundation.waitforElement(Login.SSO_BTN_SIGNIN, 5);
			text = foundation.getText(Login.TXT_DESCRIPTION);
			CustomisedAssert.assertTrue(text.contains(requiredData.get(1)));
			foundation.waitforElement(Login.CHECKBOX_FIELD, 5);
			text = foundation.getText(Login.SHOW_MSG);
			CustomisedAssert.assertTrue(text.contains(requiredData.get(2)));

			// verify the check box in Don't show this again
			checkBox.check(Login.CHECKBOX_FIELD);
			foundation.waitforElement(Login.BTN_NO, 5);
			checkBox.unCheck(Login.CHECKBOX_FIELD);
			foundation.click(Login.BTN_YES);

			// Landing into home page and verify
			foundation.waitforElement(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			login.ssoLogout();

			// Login without Microsoft authenticated email id's.
			login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER, FilePath.PROPERTY_CONFIG_FILE),
					propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD, FilePath.PROPERTY_CONFIG_FILE));
			foundation.waitforElement(LocationList.LBL_LOCATION_LIST, Constants.SHORT_TIME);
			CustomisedAssert.assertTrue(foundation.isDisplayed(LocationList.LBL_LOCATION_LIST));
			login.logout();

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Description: Validating Single Sign On for ADM application using Incorrect
	 * username and password Author: Afrose Created on: 13th May 2022
	 * Parameters: correct Username,Incorrect Password,and incorrect username
	 * Comments:Username is Passed from Config file and Password stored in DB
	 */
	@Test(description = "C175497- ADM - Sign In page Negative Test cases")
	public void verifyLoginPageWithIcorrectCredentials() {
		final String CASE_NUM = "175497";

		// Reading test data from database
		rstLoginPageData = dataBase.getLoginPageData(Queries.LOGIN_PAGE, CASE_NUM);

		List<String> errormsg = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
		List<String> password = Arrays
				.asList(rstLoginPageData.get(CNLoginPage.PASSWORD).split(Constants.DELIMITER_TILD));

		try {
			// Launch the browser and enter incorrect username
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(Login.BTN_SIGN_IN);
			foundation.waitforElementToBeVisible(Login.TXT_EMAIL, Constants.SHORT_TIME);
			textBox.enterText(Login.TXT_EMAIL,
					propertyFile.readPropertyFile(Configuration.SSO_INCORRECT_USERNAME, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(Login.BTN_SIGN_IN);
			foundation.waitforElementToBeVisible(Login.SSO_BTN_SIGNIN, Constants.SHORT_TIME);
			foundation.click(Login.SSO_BTN_SIGNIN);

			// verify the error message while enter wrong username
			String text = foundation.getText(Login.USERNAME_ERROR);
			CustomisedAssert.assertTrue(text.contains(errormsg.get(0)));

			// Launch the browser and enter correct username
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(Login.BTN_SIGN_IN);
			foundation.waitforElementToBeVisible(Login.TXT_EMAIL, Constants.SHORT_TIME);
			textBox.enterText(Login.TXT_EMAIL,
					propertyFile.readPropertyFile(Configuration.SSO_USERNAME_DRIVER, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(Login.BTN_SIGN_IN);
			foundation.waitforElementToBeVisible(Login.TXT_SSO_PASSWORD, Constants.SHORT_TIME);
			textBox.enterText(Login.TXT_SSO_PASSWORD, password.get(0));
			foundation.waitforElementToBeVisible(Login.SSO_BTN_SIGNIN, Constants.SHORT_TIME);
			foundation.click(Login.SSO_BTN_SIGNIN);

			// verify the error message while enter wrong password
			text = foundation.getText(Login.PASSWORD_ERROR);
			CustomisedAssert.assertTrue(text.contains(errormsg.get(1)));

			// Launch the browser with correct credentials but not registered in microsoft
			// account and verify error message
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.CURRENT_URL, FilePath.PROPERTY_CONFIG_FILE));
			login.ssoLogin(propertyFile.readPropertyFile(Configuration.SSO_USERNAME, FilePath.PROPERTY_CONFIG_FILE),
					password.get(1));
			foundation.waitforElementToBeVisible(Login.SSO_BTN_SIGNIN, Constants.TWO_SECOND);
			foundation.click(Login.SSO_BTN_SIGNIN);
			foundation.waitforElementToBeVisible(Login.BTN_NO, Constants.TWO_SECOND);
			foundation.click(Login.BTN_YES);
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.BTN_SIGN_IN));
			CustomisedAssert.assertTrue(foundation.isDisplayed(Login.ERROR_MSG));

		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

}
