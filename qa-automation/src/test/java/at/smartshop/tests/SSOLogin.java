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
	private Map<String, String> rstLoginPageData;

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
	@Test(description = "C175498- ADM - Sign In page to utilize Auth0 Login page using finance user")
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
}
