package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;


public class ConsumerMoveHistory extends Factory {
	public static final By TXT_FILTER = By.xpath("//*[@id='movehistorydt_filter']//input");
	public static final By TBL_MOVE_HISTORY = By.id("movehistorydt");
	public static final By TBL_HISTORY_DATA = By.xpath("//*[@id='movehistorydt']//td");

}
