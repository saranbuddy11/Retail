package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;

public class ConsumerRolesList extends Factory{
	
	public static final By BTN_CREATE = By.id("newBtn");
	public static final By ROLE_NAME = By.id("name");
	public static final By DESCRIPTION = By.id("description");
	public static final By STARTS_WITH = By.id("carddefinitionstart");
	public static final By ENDS_WITH = By.id("carddefinitionend");
	public static final By LENGTH = By.id("carddefinitionlength");
	public static final By SELECT_ORG = By.xpath("//select[@id='org-dropdown']");
	public static final By SELECT_LOCATION = By.xpath("//select[@id='loc-dropdown']");
	public static final By BTN_SUBMIT= By.id("submitBtn");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	public static final By ERROR_STATUS = By.id("name-error");
	public static final By DESCRIPTION_ERROR_STATUS = By.id("description-error");
	public static final By ROLE_SEARCH = By.id("filterType");
	public static final By TBL_DATA = By.xpath("//td[text()='Do Not Delete']");
	public static final By VALIDATE_TITLE = By.id("mkashowrole-pagetitle");
	public static final By VALIDATE_CARD_DEFINITION_START_ERROR = By.id("carddefinitionstart-error");
	public static final By VALIDATE_CARD_DEFINITION_END_ERROR = By.id("carddefinitionend-error");
	public static final By DELETE_DATA = By.xpath("//a[@class='fa fa-trash icon']");
	public static final By ACCPT_POPUP = By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By Validate_CREATE_BUTTON = By.id("newBtn");
}
