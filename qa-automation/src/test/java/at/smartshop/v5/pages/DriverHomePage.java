package at.smartshop.v5.pages;

import java.util.List;
import org.openqa.selenium.By;
import at.framework.ui.Foundation;

public class DriverHomePage {
	
	private Foundation foundation = new Foundation();
	
	public static final By TXT_MENU = By.xpath("//div[text()='Menu']");
	public static final By LINK_LOGOUT = By.xpath("//span[text()='Logout']");
	public static final By LINK_INVENTORY = By.xpath("//span[text()='Inventory']");
	public static final By LINK_CASHOUT = By.id("billacceptorBtn");
	public static final By LBL_CASHOUT = By.xpath("//i[@class='fa fa-fw fa-money']/following::span/following::span[text()='CASH OUT']");
	public static final By LBL_BILL_ACCEPTOR = By.xpath("//span[text()='Bill Acceptor Total']");
	public static final By BTN_CASHOUT = By.cssSelector("button.btn.btn-accept");
	
	public By objNumPad(int number) {
		return By.xpath("//input[@value='"+number+"']");
	}	
	
	public void verifyData(List<String> tabName) {
		for(int iter=0; iter<tabName.size(); iter++) {
			foundation.isDisplayed(By.xpath("//span[text()='"+tabName.get(iter)+"']"));
		}
	}	
	
}
