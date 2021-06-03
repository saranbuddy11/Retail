package at.smartshop.v5.tests;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
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
import at.smartshop.v5.pages.LandingPage;

public class HomeScreen extends TestInfra {
	private Foundation foundation = new Foundation();
	private LandingPage landingPage = new LandingPage();
	private ResultSets dataBase = new ResultSets();

	private Map<String, String> rstV5DeviceData;

	@Test(description = "Kiosk 'Default' Landing UI > Language Selection")
	public void verifyLanguageSelection() {
		try {
			final String CASE_NUM = "141857";
			rstV5DeviceData = dataBase.getV5DeviceData(Queries.V5DEVICE, CASE_NUM);
			List<String> actualData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.ACTUAL_DATA).split(Constants.DELIMITER_TILD));
			List<String> requiredData = Arrays
					.asList(rstV5DeviceData.get(CNV5Device.REQUIRED_DATA).split(Constants.DELIMITER_TILD));
			browser.navigateURL(propertyFile.readPropertyFile(Configuration.V5_APP_URL, FilePath.PROPERTY_CONFIG_FILE));

			foundation.click(landingPage.objLanguage(requiredData.get(0)));

			String actualLanguage = foundation.getText(LandingPage.LBL_HEADER);
			assertEquals(actualLanguage, actualData.get(0));
			Thread.sleep(5000);

			foundation.click(landingPage.objLanguage(requiredData.get(1)));
			actualLanguage = foundation.getText(LandingPage.LBL_HEADER);
			assertEquals(actualLanguage, actualData.get(1));

		} catch (Exception exc) {
			Assert.fail();
		}
	}
}
