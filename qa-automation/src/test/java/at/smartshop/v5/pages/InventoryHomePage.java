package at.smartshop.v5.pages;

import java.util.List;
import org.openqa.selenium.By;
import at.framework.ui.Foundation;

public class InventoryHomePage {
	
	private Foundation foundation = new Foundation();
	
	public static final By TXT_SELECT_ACTION = By.cssSelector("div.subtitle");
	public static final By TXT_LOCATION = By.cssSelector("div[class='pure-u-1 title']");
	public static final By BTN_BACK = By.xpath("//button[text()='BACK']");
	public static final By BTN_LOGOUT = By.xpath("//button[text()='LOGOUT']");
	
	public void verifyInventoryOptions(List<String> options) {
			for(int iter=0; iter<options.size(); iter++) {
				foundation.isDisplayed(By.xpath("//div[text()='"+options.get(iter)+"']"));			
		}	
	}
}
