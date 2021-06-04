package at.smartshop.v5.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.AccountLogin;
import at.smartshop.v5.pages.AdminMenu;
import at.smartshop.v5.pages.LandingPage;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {
		
	private Foundation foundation=new Foundation();
	private AccountLogin accountLogin = new AccountLogin();
	
	@Test(description = "C141867 - This test validates the Driver Login and Log Out")
	public void verifyDriverLoginLogout() {
		try {
			final String CASE_NUM = "141867";
					
					browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL , FilePath.PROPERTY_CONFIG_FILE));
					foundation.click(LandingPage.LINK_ENGLISH);
					foundation.doubleClick(LandingPage.IMG_LOGO);
					foundation.doubleClick(LandingPage.IMG_LOGO);
					foundation.click(LandingPage.IMG_LOGO);
					accountLogin.enterPin("1111");
					foundation.click(AdminMenu.BTN_SIGN_IN);
					foundation.isDisplayed(AdminMenu.LINK_DRIVER_LOGOUT);
					foundation.click(AdminMenu.LINK_DRIVER_LOGOUT);					
			
		}catch(Exception exc) {
			Assert.fail(exc.toString());
		}
	}

}
