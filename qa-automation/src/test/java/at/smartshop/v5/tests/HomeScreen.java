package at.smartshop.v5.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.FilePath;
import at.smartshop.tests.TestInfra;
import at.smartshop.v5.pages.LandingPage;

public class HomeScreen extends TestInfra {
	private Foundation foundation = new Foundation();
	private LandingPage landingPage = new LandingPage();

	@Test(description = "Kiosk 'Default' Landing UI > Language Selection")
	public void verifyLanguageSelection() {
		try {
			
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));
			foundation.click(landingPage.objLanguage("English"));
			String expected_Langauge="Scan/Select Products or Login";
			String actualLanguage = foundation.getText(LandingPage.LBL_HEADER);
			assertEquals(actualLanguage,expected_Langauge);
			Thread.sleep(10000);
			String expected_Langauge2="Skannaa/valitse tuotteet tai kirjaudu sis‰‰n";
			foundation.click(landingPage.objLanguage("Finnish"));
			 actualLanguage = foundation.getText(LandingPage.LBL_HEADER);
			assertEquals(actualLanguage,expected_Langauge2);
			

		} catch (Exception exc) {
			Assert.fail();
		}
	}
}
