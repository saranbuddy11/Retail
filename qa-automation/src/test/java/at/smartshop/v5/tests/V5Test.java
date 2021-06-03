package at.smartshop.v5.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;

@Listeners(at.framework.reportsetup.Listeners.class)
public class V5Test extends TestInfra {
		
	private Foundation foundation=new Foundation();
	
	@Test(description = "Testing V5 test case execution")
	public void verifyLockerSystemsPickupLocation() {
		try {
			//browser.launch(propertyFile.readPropertyFile(Configuration.DRIVER, FilePath.PROPERTY_CONFIG_FILE),propertyFile.readPropertyFile(Configuration.BROWSER, FilePath.PROPERTY_CONFIG_FILE));
			browser.navigateURL(
					propertyFile.readPropertyFile(Configuration.V5_APP_URL , FilePath.PROPERTY_CONFIG_FILE));
			By createAccount= By.id("create-account-id");
			By english= By.xpath("//button[text()='English']");
			foundation.click(english);
			foundation.click(createAccount);
			Thread.sleep(10000);
			
		} catch (Exception exc) {
			Assert.fail();
		}

	}

}
