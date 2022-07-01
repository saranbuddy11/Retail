package at.smartshop.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

import at.framework.browser.Factory;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class EditPromotion extends Factory {

	private Table table = new Table();
	private Foundation foundation = new Foundation();
	private Dropdown dropDown = new Dropdown();
	private NavigationBar navigationBar = new NavigationBar();
	private CreatePromotions createPromotions = new CreatePromotions();
	private PromotionList promotionList = new PromotionList();

	public static final By DPD_CATEGORY = By.id("categorySelectInput");
	public static final By BTN_NEXT = By.xpath("//button[text()='Next']");
	public static final By BTN_END_PROMO = By.id("disablepromotion");
	public static final By CHK_ACTIVE = By.id("active");
	public static final By BTN_UPDATE = By.id("submitBtn");
	public static final By ICON_CLR_LOCATION = By
			.xpath("//*[@id='location-select']//..//span[@class='select2-selection__clear']");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By PAGE_TITLE = By.id("pagetitle");
	public static final By DPD_ITEM = By.cssSelector("select#itemSelectInput");
	public static final By LBL_PROMPT_TITLE = By.cssSelector("div.ajs-header");
	public static final By DPD_LOCATION = By.xpath("//select[@id='location-select']/..//span//li/input");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By BTN_SAVE_AS = By.xpath("//button[text()='Save As New Promotion']");
	public static final By TXT_PROMOTION_NAME = By.xpath("//input[@placeholder='Promotion Name']");
	public static final By BTN_CONTINUE = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By BTN_OK = By.xpath("//button[text()='OK']");
	public static final By TXT_POPUP_HEADER = By.xpath("//div[@class='ajs-header']");
	public static final By TXT_POPUP_ALERT_MSG = By.xpath("//div[@class='ajs-content']");
	public static final By PRICING = By.xpath("//div[@id='bundlesummary']/b/span[2]");

	public void expirePromotion(String dataGridname, String promoName) {
		if (!foundation
				.isDisplayed(By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + promoName + "']"))) {
			dropDown.selectItem(PromotionList.DPD_STATUS, Constants.ALL, Constants.TEXT);
			foundation.click(PromotionList.BTN_SEARCH);
		}
		table.selectRow(dataGridname, promoName);
		foundation.doubleClick(By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + promoName + "']"));
		foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
		foundation.click(EditPromotion.BTN_END_PROMO);
		foundation.waitforElement(EditPromotion.BTN_CONTINUE, Constants.SHORT_TIME);
		foundation.click(EditPromotion.BTN_CONTINUE);
	}

	public void switchAlert(String action) {
		try {
			Alert alert = getDriver().switchTo().alert();
			if (action.equals("ok"))
				alert.accept();
			else
				alert.dismiss();
		} catch (NoAlertPresentException exc) {
			// Continue
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	/**
	 * Editing the existing Bundle Promotion to change Build Bundle from Category to
	 * Item
	 * 
	 * @param menu
	 * @param promoName
	 * @param statusType
	 * @param promoType
	 * @param value
	 */
	public void editBundlePromotionFromCategoryToItem(String menu, String promoName, String statusType,
			String promoType, String value, String bundleType) {
		navigationBar.navigateToMenuItem(menu);
		CustomisedAssert.assertTrue(foundation.isDisplayed(PromotionList.PAGE_TITLE));
		promotionList.searchPromotionWithType(promoName, statusType, promoType);
		table.selectRow(value, promoName);
		foundation.doubleClick(By.xpath("//td[@aria-describedby='" + value + "'][text()='" + promoName + "']"));
		foundation.waitforElementToBeVisible(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
		foundation.click(CreatePromotions.BTN_NEXT);
		foundation.waitforElementToBeVisible(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
		foundation.click(CreatePromotions.BTN_NEXT);
		createPromotions.selectBuildBundleAsItemAndCheckBox(bundleType);
		foundation.click(CreatePromotions.BTN_NEXT);
		foundation.waitforElementToBeVisible(CreatePromotions.BUNDLE_PROMO_ALERT, Constants.SHORT_TIME);
		foundation.click(CreatePromotions.BTN_EXPIRE);
		foundation.waitforElementToBeVisible(PromotionList.PAGE_TITLE, Constants.SHORT_TIME);
	}
}
