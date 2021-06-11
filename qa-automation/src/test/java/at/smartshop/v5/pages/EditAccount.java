package at.smartshop.v5.pages;

import org.openqa.selenium.By;

import at.framework.ui.Foundation;
import at.framework.ui.TextBox;

public class EditAccount {
	private Foundation foundation=new Foundation();
	private TextBox textBox=new TextBox();
	
	public static final By BTN_EDIT_ACCOUNT=By.xpath("//*[text()='Edit Account']");
	public static final By TXT_FIRST_NAME=By.id("editaccount_firstName");
	public static final By TXT_LAST_NAME=By.id("editaccount_lastName");
	public static final By BTN_EDIT_NEXT=By.id("pin-reset-btn-go-id");
	public static final By TXT_EMAIL_ADDRESS=By.id("editaccount_emailAddress");
	public static final By BTN_CHANGE_PIN=By.xpath("//*[text()='Change PIN']");
	public static final By BTN_SAVE=By.id("editaccount-btn-go-id");
	public static final By BTN_SAVE_PIN=By.id("pin-reset-btn-go-id");
	public static final By BTN_CAMEL_CASE=By.xpath("//div[text()='abc']");
	public static final By BTN_DELETE=By.xpath("//*[text()='Del']");
	
	public void updateText(By obj,String text,String priviousText) {
		foundation.click(obj);		
		for(int i=0;i<=priviousText.length();i++) {
			foundation.click(BTN_DELETE);
		}		
		foundation.click(obj);
		textBox.enterKeypadText(text);	
	}
	

}
