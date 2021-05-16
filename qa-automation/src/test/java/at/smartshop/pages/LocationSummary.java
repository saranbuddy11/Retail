package at.smartshop.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;

public class LocationSummary extends Factory {
	
    private Foundation foundation = new Foundation();
    private Dropdown dropDown=new Dropdown();

	public static final By DPD_DISABLED= By.id("isdisabled");
	public static final By BTN_SAVE= By.id("saveBtn");
	public static final By TBL_PRODUCTS= By.cssSelector("a#loc-products");
	//public static final By TBL_PRODUCTS_LIST = By.cssSelector("#productDataGrid > tbody");
	public static final By POP_UP_BTN_SAVE =  By.id("confirmDisableId");
	public static final By BTN_LOCATION_SETTINGS =  By.id("toggleinfo");
	public static final By DPD_HAS_LOCKER =  By.id("haslocker");
	public static final By DPD_HAS_ORDER_AHEAD =  By.id("hasonlineordering");
	public static final By DPD_HAS_PICK_UP_LOCATIONS =  By.id("haspickuplocations");
	public static final By LNK_PICK_UP_LOCATION =  By.id("pickupLocationToggle");
	public static final By LBL_LOCKER_PICK_UP_TITLE =  By.xpath("//*[@id='lockersystempickuptitle']/i");
	public static final By LNK_LOCKER_NAME=  By.xpath("//*[@id='pickuplockersystems']/div/a");
	public static final By TXT_SYSTEM_NAME=  By.id("systemName");
	public static final By LBL_SHELF_LIFE=  By.xpath("//*[@id='pickuplockersystems']/div/span");
    public static final By BUTTON_LOCATION_INFO = By.cssSelector("button#toggleinfo");
    public static final By DPD_RETRIEVE_ACCOUNT = By.cssSelector("select#retrieveaccount");
    public static final  By FIELD_RETRIEVE_CHECKBOX = By.cssSelector("div#enableRetrieveAccountOptions");
    private static final By TXT_ERR_MSG = By.cssSelector("dd.error-txt");
    private static final By TXT_HAS_LOCKERS = By.xpath("//dt[text()='Has Lockers']");
    private static final By LBL_LOCATION_SUMMARY = By.cssSelector("li[id='Location Summary']");
    public static final By TAB_PRODUCTS = By.id("loc-products");
    public static final By TXT_SEARCH = By.id("productFilterType");
    public static final By LBL_TAX_CATEGORY= By.xpath("(//td[@aria-describedby='productDataGrid_taxcat'])[2]");
    private static final By BTN_MANAGE_COLUMNS = By.id("manageProductGridColumnButton");
    private static final By BTN_SHOW = By.xpath("//span[text()='Taxcat']//..//a[text()='Show']");
    private static final By BTN_APPLY = By.id("productDataGrid_hiding_modalDialog_footer_buttonok_lbl");

    public void validateErrorMessage(String Message) {
        try {                           
            String errMsg = foundation.getText(TXT_ERR_MSG);
            Assert.assertEquals(errMsg, Message);
            ExtFactory.getInstance().getExtent().log(Status.INFO, "Validated the Error Message for Retrieve Account");
        }catch(Exception exc) {
            Assert.fail(exc.toString());
        }
    }
    
    public void verifyHasLockerField(String defaultValue) {
        try {
            foundation.waitforElement(LBL_LOCATION_SUMMARY, 5);
            Assert.assertTrue(foundation.isDisplayed(TXT_HAS_LOCKERS));
            String value = dropDown.getSelectedItem(DPD_HAS_LOCKER);
            Assert.assertEquals(value, defaultValue);
            ExtFactory.getInstance().getExtent().log(Status.INFO, "Validated the has Locker default Value"+ defaultValue);
        }catch(Exception exc) {
            Assert.fail(exc.toString());
        }
    }
    
    public void showTaxCategory() {
        try {
        foundation.click(BTN_MANAGE_COLUMNS);
        foundation.click(BTN_SHOW);
        }
        catch (Exception exc) { 
        	Assert.fail(exc.toString());
        }
        foundation.click(BTN_APPLY);
    }
    
	public int getRowCount() {
		List<WebElement> columnHeaders = null;
		try {
			WebElement tableReports = getDriver().findElement(By.cssSelector("#productDataGrid > tbody"));
			columnHeaders = tableReports.findElements(By.tagName("tr"));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return (columnHeaders.size());
	}
}
