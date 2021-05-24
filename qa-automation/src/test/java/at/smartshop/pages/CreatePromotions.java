package at.smartshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import at.framework.browser.Factory;
import at.framework.files.PropertyFile;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.FilePath;

public class CreatePromotions extends Factory {

	private Foundation foundation = new Foundation();
	private Dropdown dropdown = new Dropdown();
	private TextBox textbox = new TextBox();
	private PropertyFile propertyFile = new PropertyFile();

	private static final By DPD_PROMO_TYPE = By.id("promotype");
	private static final By TXT_PROMO_NAME = By.id("name");
	private static final By TXT_DISPLAY_NAME = By.id("displayname");
	private static final By BTN_NEXT = By.id("submitBtn");

	public static final By DPD_LOCATION = By.id("location-select");
	private static final By DPD_ORG = By.xpath("//input[@placeholder='Select Org(s) to include']");
	private static final By DPD_LOC = By.xpath("//input[@placeholder='Select Location(s) to include']");

	public void newPromotion(String PromoType, String PromoName, String DisplayName) {

		dropdown.selectItem(DPD_PROMO_TYPE, PromoType, "text");
		textbox.enterText(TXT_PROMO_NAME, PromoName);
		textbox.enterText(TXT_DISPLAY_NAME, DisplayName);
		foundation.click(BTN_NEXT);
		textbox.enterText(DPD_ORG,
				propertyFile.readPropertyFile(Configuration.RNOUS_ORG, FilePath.PROPERTY_CONFIG_FILE));

		getDriver().findElement(DPD_ORG).sendKeys(Keys.ENTER);
		getDriver().findElement(DPD_LOC).click();
		getDriver().findElement(DPD_LOC).sendKeys(Keys.ENTER);

	}

}
