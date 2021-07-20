package at.smartshop.pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import at.framework.browser.Factory;
import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class PromotionList extends Factory {
	private Foundation foundation = new Foundation();
	private TextBox textbox = new TextBox();
	
	public static final By BTN_CREATE = By.xpath("//button[text()='Create New']");
	public static final By PAGE_TITLE = By.xpath("//li[text()='Promotion List']");
	public static final By LBL_START_DATE_HEADER = By.id("hierarchicalGrid_datestart");
	public static final By LBL_START_DATE_LIST = By.xpath("//td[@aria-describedby='hierarchicalGrid_datestart']");
	public static final By BTN_OK = By.xpath("//button[text()='OK']");
	public static final By TXT_SEARCH_PROMONAME = By.id("search");
	public static final By BTN_SEARCH = By.id("searchbtn");
	public static final By TBL_COLUMN_NAME = By.xpath("//td[@aria-describedby='hierarchicalGrid_name']");
	public static final By CALENDER_DATE_RANGE = By.id("daterange");
	public static final By DPD_LOCATION = By.id("loc-dropdown");
	public static final By DPD_STATUS = By.id("status");
	public static final By DPD_PROMOTYPE = By.id("promotype");
	public static final By LBL_SEARCH = By.xpath("//input[@id='search']//..//..//dt");
	public static final By LBL_CALENDER_DATE_RANGE = By.xpath("//input[@id='startdate']//..//..//dt");
	public static final By LBL_LOCATION = By.xpath("//select[@id='loc-dropdown']//..//..//dt");
	public static final By LBL_STATUS = By.xpath("//select[@id='status']//..//..//dt");
	public static final By LBL_PROMOTYPE = By.xpath("//select[@id='promotype']//..//..//dt");
	public static final By LBL_ORG_NAME = By.xpath("//td[contains(@aria-describedby,'orgs_child_orgname')]");
	public static final By LINK_EXPAND = By.xpath("//span[@title='Expand Row']");
	public static final By LBL_LOCATION_NAME = By.xpath("//td[contains(@aria-describedby,'locations_child_locationname')]");

	public void clickSelectedRow(String dataGridname, String promoName) {
		foundation.doubleClick(By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + promoName + "']"));
	}

	public void searchPromotion(String promoName) {
		foundation.waitforElement(PromotionList.TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
		textbox.enterText(PromotionList.TXT_SEARCH_PROMONAME, promoName);
		foundation.click(PromotionList.BTN_SEARCH);
	}

	public void verifyPromotionName(String promotionName) {
		try {
			List<WebElement> promotionData = getDriver().findElements(TBL_COLUMN_NAME);
			for (int iter = 0; iter < promotionData.size(); iter++) {
				if (promotionData.get(iter).getText().equals(promotionName)) {
					ExtFactory.getInstance().getExtent().log(Status.INFO,
							"Promotion created Suucessfully:  " + promotionName);
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void expirePromotion(String promotionName,String gridName) {
		try {
			searchPromotion(promotionName);
			Assert.assertTrue(foundation.getText(TBL_COLUMN_NAME).equals(promotionName));
			clickSelectedRow(gridName, promotionName);
			foundation.waitforElement(CreatePromotions.BTN_NEXT, Constants.SHORT_TIME);
			foundation.waitforElement(EditPromotion.BTN_END_PROMO, Constants.SHORT_TIME);
			foundation.click(EditPromotion.BTN_END_PROMO);
			foundation.click(EditPromotion.BTN_CONTINUE);
			foundation.waitforElement(TXT_SEARCH_PROMONAME, Constants.SHORT_TIME);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
}
