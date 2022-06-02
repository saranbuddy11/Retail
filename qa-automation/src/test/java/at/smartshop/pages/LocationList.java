package at.smartshop.pages;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import at.framework.browser.Browser;
import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class LocationList extends Factory {

	private Foundation foundation = new Foundation();
	public Browser browser = new Browser();
	public Login login = new Login();
	private TextBox textBox = new TextBox();
	private NavigationBar navigationBar = new NavigationBar();

	public static final By TXT_FILTER = By.id("filterType");
	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By DPD_LOCATION_LIST = By.id("filtervalues");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");
	public static final By TXT_RECORD_UPDATE_MSG = By.xpath("//div[@class='humane ']");
	public static final By TXT_SPINNER_ERROR_MSG = By.xpath("//div[@class='humane humane-libnotify-error']");
	public static final By LINK_LOCATION_LIST = By.xpath("//td[@aria-describedby='dataGrid_table_namelink']//a");
	public static final By LINK_HOME_PAGE = By.xpath("//a[@id='sup-location']");
	public static final By LBL_LOCATION_LIST = By.xpath("//li[text()='Location List']");

	/**
	 * Selecting the Location Name
	 * @param locationName
	 */
	public void selectLocationName(String locationName) {
		foundation.waitforElement(getlocationElement(locationName), Constants.SHORT_TIME);
		textBox.enterText(TXT_FILTER, locationName);
		foundation.threadWait(Constants.THREE_SECOND);
		foundation.click(By.xpath("//a[text()='" + locationName + "']"));
	}

	public By objGlobalProduct(String product) {
		return By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");
	}

	public By getlocationElement(String locationName) {
		return By.xpath("//a[text()='" + locationName + "']");
	}

	public By objDailyRevenue(String locationName) {
		return By.xpath("//a[text()='" + locationName + "']//..//..//*[@aria-describedby='dataGrid_table_revenue']");
	}

	public void syncDevice(String menu, String location) {
		navigationBar.navigateToMenuItem(menu);
		selectLocationName(location);
		foundation.click(LocationSummary.BTN_SYNC);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.click(LocationSummary.BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		login.logout();
		browser.close();
	}

	public void deployDevice(String location, String device) {
		selectLocationName(location);
		foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
		foundation.click(LocationSummary.BTN_DEPLOY_DEVICE);
		foundation.waitforElement(LocationSummary.TXT_FIND_DEVICE, Constants.SHORT_TIME);
		textBox.enterText(LocationSummary.TXT_FIND_DEVICE, device);
		foundation.click(LocationSummary.TBL_DEVICE_LIST);
		foundation.click(LocationSummary.BTN_ADD_PRODUCT_ADD);
		foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
		foundation.refreshPage();
		foundation.click(LocationSummary.BTN_SYNC);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.click(LocationSummary.BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
		login.logout();
		browser.close();
	}

	public void removeDevice(String menu, String location) {
		navigationBar.navigateToMenuItem(menu);
		selectLocationName(location);
		foundation.objectFocus(LocationSummary.BTN_DEPLOY_DEVICE);
		foundation.click(LocationSummary.TBL_DEPLOYED_DEVICE_LIST);
		foundation.waitforElement(DeviceDashboard.BTN_LIVE_CONNECTION_STATUS, Constants.SHORT_TIME);
		foundation.click(DeviceDashboard.BTN_REMOVE_DEVICE);
		foundation.waitforElement(DeviceDashboard.BTN_YES_REMOVE, Constants.SHORT_TIME);
		foundation.click(DeviceDashboard.BTN_YES_REMOVE);
		foundation.waitforElement(LocationSummary.BTN_DEPLOY_DEVICE, Constants.SHORT_TIME);
		foundation.click(LocationSummary.BTN_SYNC);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.click(LocationSummary.BTN_SAVE);
		foundation.waitforElement(LocationList.TXT_SPINNER_MSG, Constants.SHORT_TIME);
		foundation.waitforElementToDisappear(LocationList.TXT_SPINNER_MSG, Constants.EXTRA_LONG_TIME);
	}

	public void verifyData(List<String> uiListHeaders, List<String> uidata, Map<String, String> expectedValues) {
		try {
			int count = uiListHeaders.size();
			for (int iter = 0; iter < count; iter++) {
				CustomisedAssert.assertTrue(uidata.get(iter).contains(expectedValues.get(uiListHeaders.get(iter))));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

}
