package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class OrgList {
	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();
	
	public static final By TXT_SEARCH_ORG = By.xpath("//*[@id='dt_filter']//input");
	public static final By LBL_ORG_LIST = By.id("Org List");
	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By LBL_NO_RESULT = By.xpath("//td[text()='No matching records found']");
	public static final By LBL_FIRST_ORG_NAME = By.xpath("(//div[@id='dt_wrapper']//td)[1]");
	public static final By TXT_SPINNER_MSG = By.xpath("//div[@class='humane humane-libnotify-info']");

	public void selectOrg(String organization) {
		textBox.enterText(TXT_SEARCH_ORG, organization);
		foundation.click(By.xpath("//td[@class=' sorting_1']//span[text()='"+organization+"']"));
	}
}
