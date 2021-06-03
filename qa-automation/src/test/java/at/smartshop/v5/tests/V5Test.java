package at.smartshop.v5.tests;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Foundation;
import at.smartshop.database.columns.CNLocation;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.LandingPage;
import at.smartshop.v5.pages.Policy;
import at.smartshop.v5.pages.UserProfile;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {

	private Foundation foundation = new Foundation();
	private AccountLogin accountLogin = new AccountLogin();
	private ResultSets dataBase = new ResultSets();
	private LandingPage landingPage = new LandingPage();

	private Map<String, String> rstV5DeviceData;

	@Test(description = "C141881 - Kiosk Privacy Policy (if applicable)")
	public void verifyPrivacyPolicy() {
		try {
			final String CASE_NUM = "141881";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5DEVICE, CASE_NUM);
			// List<String> requiredData =
			// Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage("English"));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));

			foundation.click(UserProfile.BTN_PRIVACY);
			String title = foundation.getText(Policy.LBL_POLICY_TITLE);
			Assert.assertTrue(title.equals(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA)));

			foundation.click(Policy.BTN_OK);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	@Test(description = "141863 -Kiosk Account Login > Email")
	public void verifyLoginScreen() {
		try {
			final String CASE_NUM = "141863";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5DEVICE, CASE_NUM);
			String requiredData = rstV5DeviceData.get(CNV5Device.REQUIRED_DATA);

			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage(requiredData));
			foundation.click(LandingPage.BTN_LOGIN);
			foundation.click(AccountLogin.BTN_EMAIL_LOGIN);
			accountLogin.login(rstV5DeviceData.get(CNV5Device.EMAIL_ID), rstV5DeviceData.get(CNV5Device.PIN));
			assertTrue(foundation.isDisplayed(UserProfile.BTN_PRIVACY));

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
