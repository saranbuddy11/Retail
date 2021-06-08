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
	public static final By PAGE_TITLE = By.id("pagetitle");
	public static final By CHK_ACTIVE = By.id("active");
	public static final By DPD_ITEM = By.cssSelector("select#itemSelectInput");
	public static final By LBL_PROMPT_TITLE = By.cssSelector("div.ajs-header");
	public static final By BTN_SAVE_CHANGES = By.cssSelector("button.ajs-button.ajs-ok");
	public static final By BTN_SAVE = By.xpath("//button[text()='Save']");
	public static final By DPD_LOCATION = By.xpath("//select[@id='location-select']/..//span//li/input");
	public static final By BTN_CANCEL = By.id("cancelBtn");
	
	public void expirePromotion(String dataGridname,String promoName){
        table.selectRow(dataGridname,promoName);       
        foundation.doubleClick(By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + promoName + "']"));
        foundation.waitforElement(EditPromotion.BTN_END_PROMO, 2000);
        foundation.click(EditPromotion.BTN_END_PROMO);
        foundation.click(EditPromotion.BTN_EXPIRE);
}	

}