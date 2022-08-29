package at.smartshop.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class ConsumerRolesList extends Factory{
	
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Dropdown dropdown = new Dropdown();
	
	
	public static final By BTN_CREATE = By.id("newBtn");
	public static final By ROLE_NAME = By.id("name");
	public static final By DESCRIPTION = By.id("description");
	public static final By STARTS_WITH = By.id("carddefinitionstart");
	public static final By ENDS_WITH = By.id("carddefinitionend");
	public static final By CONSUMER_ROLES=By.xpath("//div[contains(@style,'font-weight:700;')]");
	public static final By LENGTH = By.id("carddefinitionlength");
	public static final By SELECT_ORG = By.xpath("//select[@id='org-dropdown']");
	public static final By SELECT_ORGANISATION=By.xpath("//input[@placeholder='Select Org(s)']");
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
	
	
	/**
	 * create consumer in consumer roles
	 * @param name
	 * @param description
	 * @param start
	 * @param end
	 * @param lenght
	 */
	public void createConsumerInConsumerRoles(List<String> values) {
		foundation.waitforElementToBeVisible(BTN_CREATE, 3);
		foundation.click(BTN_CREATE);
		foundation.waitforElementToBeVisible(ROLE_NAME, 3);
		textBox.enterText(ROLE_NAME, values.get(0));
		textBox.enterText(DESCRIPTION, values.get(1));
		foundation.waitforElementToBeVisible(STARTS_WITH, 3);
		textBox.enterText(STARTS_WITH, values.get(2));
		textBox.enterText(ENDS_WITH, values.get(3));
		foundation.waitforElementToBeVisible(LENGTH, 3);
		textBox.enterText(LENGTH, values.get(4));
		dropdown.selectItem(SELECT_ORG, values.get(5), Constants.TEXT);
		foundation.waitforElementToBeVisible(SELECT_LOCATION, 3);
		dropdown.selectItem(SELECT_LOCATION, values.get(6), Constants.TEXT);
		
	}
}
