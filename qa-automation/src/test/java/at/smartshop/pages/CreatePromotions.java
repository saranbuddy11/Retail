package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class CreatePromotions extends Factory {

	private Foundation foundation = new Foundation();
	private Dropdown dropdown = new Dropdown();
	private TextBox textbox = new TextBox();
	private Table table = new Table();

	public static final By DPD_PROMO_TYPE = By.id("promotype");
	public static final By TXT_PROMO_NAME = By.id("name");
	public static final By TXT_DISPLAY_NAME = By.id("displayname");
	public static final By BTN_NEXT = By.id("submitBtn");
	public static final By DPD_LOCATION = By.id("location-select");
	public static final By DPD_ORG = By.xpath("//input[@placeholder='Select Org(s) to include']");
	public static final By DPD_LOC = By.xpath("//input[@placeholder='Select Location(s) to include']");
	public static final By LBL_CREATE_PROMOTION = By.xpath("//li[text()='Create Promotion']");
	public static final By BTN_END_PROMO = By.id("disablepromotion");
	public static final By BTN_EXPIRE = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By MULTI_SELECT_TENDER_TYPES = By.id("tendertypes");
	public static final By BTN_OK = By.xpath("//button[text()='OK']");
	public static final By LBL_PAGE_TITLE = By.id("pagesubtitle");
	public static final By DPD_DISCOUNT_TIME = By.id("timing");
	public static final By DPD_DISCOUNT_TYPE = By.id("discounttype");
	public static final By BTN_CREATE = By.id("submitBtnContainer");
	public static final By TXT_AMOUNT = By.id("amount");
	public static final By TXT_START_DATE = By.id("startdate");
	public static final By TXT_END_DATE = By.id("enddate");
	public static final By TXT_TIME_START = By.id("timestart");
	public static final By TXT_TIME_END = By.id("timeend");
	public static final By POP_UP_MESSAGES = By.xpath("//div[@class='ajs-dialog']//b");
	public static final By LBL_POPUP_FIELD = By.xpath("//div[@class='alert-details']//b");
	public static final By LBL_POPUP_VALUES = By.xpath("//div[@class='alert-details']");
	public static final By DPD_APPLY_DISCOUNT_TO = By.id("appliesto");
	public static final By TXT_TRANSACTION_MIN = By.id("transmin");
	public static final By CHK_SUNDAY = By.xpath("//div[@id='recurringInput']//dd/input[@id='sunday']");

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

	public void expirePromotion(String dataGridname, String promoName) {

		foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, 2000);
		textbox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promoName);
		foundation.click(PromotionList.BTN_SEARCH);
		table.selectRow(dataGridname, promoName);
		foundation.doubleClick(By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + promoName + "']"));
		foundation.waitforElement(BTN_END_PROMO, 2000);
		foundation.click(BTN_END_PROMO);
		foundation.click(BTN_EXPIRE);
	}

	public List<String> getPopUpData() {

		List<String> popupFieldValues = foundation.getTextofListElement(LBL_POPUP_VALUES);
		List<String> popupFieldArray = new ArrayList<String>();
		List<String> promoValues;
		for (int i = 0; i < popupFieldValues.size(); i++) {
			promoValues = Arrays.asList(popupFieldValues.get(i).split(Constants.NEW_LINE));
			for (int j = 0; j < promoValues.size(); j++) {
				popupFieldArray.add(promoValues.get(j));
			}
		}
		return popupFieldArray;

	}

}
