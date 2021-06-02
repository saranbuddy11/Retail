package at.smartshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import at.framework.browser.Factory;
import at.framework.files.PropertyFile;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class CreatePromotions extends Factory {

	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private TextBox textBox = new TextBox();
	private PropertyFile propertyFile = new PropertyFile();
	
	public static final By DPD_PROMO_TYPE = By.id("promotype");
	public static final By TXT_PROMO_NAME = By.id("name");
	private static final By TXT_DISPLAY_NAME = By.id("displayname");
	public static final By BTN_NEXT = By.id("submitBtn");

	public static final By DPD_LOCATION = By.id("location-select");
	public static final By DPD_ORG = By.xpath("//input[@placeholder='Select Org(s) to include']");
    public static final By SEARCH_ITEM = By.xpath("//input[@placeholder='Search for an Item']");
    public static final By BTN_OK = By.xpath("//button[text()='OK']");
    public static final By MULTI_SELECT_TENDER_TYPES =By.id("tendertypes");
    public static final By DPD_DISCOUNT_TYPE =By.id("discounttype");
    public static final By BTN_CREATE = By.id("submitBtnContainer");
    public static final By TXT_AMOUNT = By.id("amount");

	public void newPromotion(String promoType, String promoName, String displayName, String locationName) {

		dropDown.selectItem(DPD_PROMO_TYPE, promoType, Constants.TEXT);
		textBox.enterText(TXT_PROMO_NAME, promoName);
		textBox.enterText(TXT_DISPLAY_NAME, displayName);
		foundation.click(BTN_NEXT);
		textBox.enterText(DPD_ORG,	propertyFile.readPropertyFile(Configuration.CURRENT_ORG, FilePath.PROPERTY_CONFIG_FILE));		
		textBox.enterText(DPD_ORG, Keys.ENTER);
        dropDown.selectItem(DPD_LOCATION, locationName, Constants.TEXT);

	}

}
