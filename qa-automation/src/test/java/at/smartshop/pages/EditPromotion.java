package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.Table;

public class EditPromotion extends Factory {
	private Foundation foundation = new Foundation();
	private Table table = new Table();
	public static final By BTN_END_PROMO = By.id("disablepromotion");
	public static final By BTN_EXPIRE = By.xpath("//button[@class='ajs-button ajs-ok']");

	public void expirePromotion(String dataGridname, String promoName) {
		table.selectRow(dataGridname, promoName);
		foundation.doubleClick(By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + promoName + "']"));
		foundation.waitforElement(BTN_END_PROMO, 2000);
		foundation.click(BTN_END_PROMO);
		foundation.click(BTN_EXPIRE);
	}
}
