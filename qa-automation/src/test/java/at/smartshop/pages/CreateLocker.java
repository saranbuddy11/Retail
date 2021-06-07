package at.smartshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class CreateLocker extends Factory {

    private Foundation foundation = new Foundation();
    private TextBox textBox = new TextBox();
    public static final By DPD_LOCATION = By.xpath("//span[@id='select2-locs-container']");
	public static final By TXT_SYSTEM_NAME = By.id("systemName");
	public static final By TXT_DISPLAY_NAME = By.id("displayName");
	public static final By DPD_LOCKER_MODEL = By.id("tester");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_CREATE_SYSTEM = By.id("systemcreate");
    public static final By TXT_LOCATION = By.cssSelector("input.select2-search__field");
    public static final By LBL_CREATE_SYSTEM = By.xpath("//div[contains(text(),'Create a System')]");
    public static final By LBL_LOCATION_LOCKER_SYSTEM = By.xpath("//div[text()='Location Locker Systems']");
   
    public void verifyLocation(String locationName, String value) {
        try {
            foundation.click(DPD_LOCATION);
            textBox.enterText(TXT_LOCATION, locationName);
            WebElement text = getDriver().findElement(By.xpath("//li[contains(text(),'"+value+"')]"));
            Assert.assertEquals(text.getText(), value);           
        }catch(Exception exc) {
            Assert.fail(exc.toString());
        }
    }
}
