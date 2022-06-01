package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;

public class DNADetails extends Factory {

	public static final By HEADER_DNA = By.cssSelector("div.span12 > ul > li");
	public static final By LOCATION_NAME = By.id("locationame");
	public static final By IS_DISABLED_COMBO_BOX = By.cssSelector("div.selectdisabled > span");
	public static final By DD_ISDISABLED = By.id("isdisabled");
	public static final By CALORIES_TAB = By.id("calories-maintab");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By TEXT_APPLY_LOCATION = By.id("applyloc");
	public static final By GREEN_MIN_FIELD = By.id("calorimin");
	public static final By GREEN_MAX_FIELD = By.id("calorimax");
	public static final By YELLOW_MIN_FIELD = By.id("cyellowmin");
	public static final By YELLOW_MAX_FIELD = By.id("cyellowmax");
	public static final By RED_MIN_FIELD = By.id("credmin");
	public static final By RED_MAX_FIELD = By.id("credmax");
	public static final By POPUP_SUCCESS = By.className("ajs-header");
	public static final By POPUP_CONTENT = By.className("ajs-content");
	public static final By BTN_OK = By.className("ajs-ok");
	public static final By LOCATION_LIST = By.xpath("//li[@data-path='5']");
	public static final By ACCESS_ICON = By.className("ion-information-circled");
	public static final By TOOL_TIP_TEXT = By.className("dna-headerinner");
	public static final By ERROR_MESSAGE = By.className("humane humane-libnotify-error");
}
