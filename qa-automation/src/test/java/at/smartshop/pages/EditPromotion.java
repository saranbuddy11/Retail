package at.smartshop.pages;

import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.Table;

public class EditPromotion extends Factory {
	private Foundation foundation = new Foundation();
	private Table table = new Table();
	
	
	public static final By BTN_END_PROMO =By.id("disablepromotion");
	public static final By BTN_EXPIRE =By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By BTN_UPDATE =By.id("submitBtn");
	public static final By BTN_SAVE_AS =By.xpath("//button[text()='Save As New Promotion']");
	public static final By TXT_PROMOTION_NAME =By.xpath("//input[@placeholder='Promotion Name']");
	public static final By BTN_CONTINUE =By.xpath("//button[@class='ajs-button ajs-ok']");
	public static final By CHK_ACTIVE =By.id("active");
	public static final By BTN_OK=By.xpath("//button[text()='OK']");
	public static final By TXT_POPUP_HEADER=By.xpath("//div[@class='ajs-header']");
	public static final By TXT_POPUP_ALERT_MSG=By.xpath("//div[@class='ajs-content']");
	public static final By BTN_CANCEL=By.id("cancelBtn");
	public static final By DPD_CATEGORY=By.id("categorySelectInput");
	public static final By BTN_NEXT =By.xpath("//button[text()='Next']");
	
	
	public void expirePromotion(String dataGridname, String promoName) {

		table.selectRow(dataGridname, promoName);
		foundation.doubleClick(By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + promoName + "']"));
		foundation.waitforElement(BTN_END_PROMO, 2000);
		foundation.click(BTN_END_PROMO);
		foundation.click(BTN_EXPIRE);
	}

}
