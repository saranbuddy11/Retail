package at.smartshop.v5.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.database.columns.CNV5Device;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AdminMenu;
import at.smartshop.v5.pages.LandingPage;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {
			
	private Foundation foundation=new Foundation();
	private TextBox textBox = new TextBox();
	private AdminMenu adminMenu = new AdminMenu();
	private ResultSets dataBase = new ResultSets();
	private LandingPage landingPage = new LandingPage();
	
	private Map<String, String> rstV5DeviceData;
	
	
	@Test(description = "C141867 - This test validates the Driver Login and Log Out")
	public void verifyDriverLoginLogout() {
		try {
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(LandingPage.LINK_ENGLISH);
			foundation.doubleClick(LandingPage.IMG_LOGO);
			foundation.doubleClick(LandingPage.IMG_LOGO);
			foundation.click(LandingPage.IMG_LOGO);
			String pin = propertyFile.readPropertyFile(Configuration.V5_DRIVER_PIN, FilePath.PROPERTY_CONFIG_FILE);
			textBox.enterPin(pin);
			foundation.click(AdminMenu.BTN_SIGN_IN);
			foundation.isDisplayed(AdminMenu.LINK_DRIVER_LOGOUT);
			foundation.click(AdminMenu.LINK_DRIVER_LOGOUT);

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	@Test(description = "C141868-Thsi test validates the Inventory options")
	public void verifyInventoryOptions() {
		try {
			final String CASE_NUM= "141868";
			
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5Device, CASE_NUM);
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL , FilePath.PROPERTY_CONFIG_FILE));
			String language = rstV5DeviceData.get(CNV5Device.ACTUAL_DATA);
			foundation.click(landingPage.objLanguage(language));
			foundation.doubleClick(LandingPage.IMG_LOGO);
			foundation.doubleClick(LandingPage.IMG_LOGO);
			foundation.click(LandingPage.IMG_LOGO);
			String pin =  propertyFile.readPropertyFile(Configuration.V5_DRIVER_PIN, FilePath.PROPERTY_CONFIG_FILE);
			textBox.enterPin(pin);
			foundation.click(AdminMenu.BTN_SIGN_IN);
			foundation.isDisplayed(AdminMenu.LINK_DRIVER_LOGOUT);
			foundation.click(AdminMenu.LINK_INVENTORY);
			List<String> optionNames = Arrays.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD)); 
			adminMenu.verifyOptions(optionNames);			
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
	}

}
