package at.smartshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class CreatePromotions extends Factory {

	private Foundation foundation = new Foundation();
	private Dropdown dropdown = new Dropdown();
	private TextBox textbox = new TextBox();


	public static final By DPD_PROMO_TYPE = By.id("promotype");
	public static final By TXT_PROMO_NAME = By.id("name");
	public static final By TXT_DISPLAY_NAME = By.id("displayname");
	public static final By BTN_NEXT = By.id("submitBtn");

	public static final By DPD_LOCATION = By.id("location-select");
	public static final By DPD_ORG = By.xpath("//input[@placeholder='Select Org(s) to include']");
	public static final By TXT_LOCATION = By.xpath("//input[@placeholder='Select Location(s) to include']");

	public void newPromotion(String promoType, String promoName, String displayName, String orgName,
            String locationName) {

        dropdown.selectItem(DPD_PROMO_TYPE, promoType, Constants.TEXT);
        textbox.enterText(TXT_PROMO_NAME, promoName);
        textbox.enterText(TXT_DISPLAY_NAME, displayName);
        foundation.click(BTN_NEXT);
        textbox.enterText(DPD_ORG, orgName);
        textbox.enterText(DPD_ORG, Keys.ENTER);
        dropdown.selectItem(DPD_LOCATION, locationName, Constants.TEXT);
    }
	
	public By objLocation(String value) {
        return By.xpath("//li[contains(text(),'" + value + "')]");
    }

}
