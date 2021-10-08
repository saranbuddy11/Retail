package at.smartshop.v5.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class Order {

	private Foundation foundation = new Foundation();

	public static final By BTN_CANCEL_ORDER = By.xpath("//button[text()='Cancel Order']");
	public static final By LBL_ORDER_CANCELLED = By.xpath("//span[text()='Transaction Cancelled']");
	public static final By LBL_PRODUCT_PRICE = By.className("product-price");
	public static final By LBL_SUB_TOTAL = By.xpath("//*[@class='total subtotal']//*[@class='total-value']");
	public static final By LBL_VAT_VALUE = By.xpath("//*[@class='total total-tax']//*[@class='total-value']");
	public static final By LBL_BALANCE_DUE = By.xpath("//*[@class='total grand-total']//*[@class='total-value']");
	public static final By LBL_TAX = By.xpath("//*[@class='total total-tax']//*[@class='total-label']");
	public static final By LBL_DEPOSIT = By.xpath("//*[@class='total']//*[@class='total-value']");
	public static final By TXT_HEADER = By.xpath("//div[@class='user-bar']/h1");
	public static final By TXT_PRODUCT = By.xpath("//div[@id='cartContainer']//div[@class='product-name']");
	public static final By POP_UP_TIMEOUT_YES = By.id("time-out-btn-yes-id");
	public static final By POP_UP_TIMEOUT_NO = By.id("time-out-btn-no-id");
	public static final By LBL_YOUR_ORDER = By.xpath("//h1[text()='Your Order']");
	public static final By POP_UP_LBL_ORDER_TIMEOUT = By.xpath("//h1[text()='Order Timeout']");
	public static final By POP_UP_LBL_ORDER_TIMEOUT_MSG = By.xpath("//h1[text()='Do you need more time?']");
	public static final By POP_UP_LBL_ORDER_TIMEOUT_SPANISH = By
			.xpath("//h1[text()='Tiempo de espera de pedido finalizado']");
	public static final By LBL_EMAIL = By.xpath("//h3[text()='Email']//..");
	public static final By LBL_TAX_1 = By.xpath("//div[text()='Tax 1:']//..//div");
	public static final By LBL_TAX_2 = By.xpath("//div[text()='Tax 2:']//..//div");
	public static final By LBL_TAX_3 = By.xpath("//div[text()='Tax 3:']//..//div");
	public static final By LBL_TAX_4 = By.xpath("//div[text()='Tax 4:']//..//div");

	public By objText(String text) {
		return By.xpath("//*[text()='" + text + "']");
	}

	public void verifyOrderPageLanguage(String order) {

		List<String> orderPageData = Arrays.asList(order.split(Constants.DELIMITER_TILD));
		Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(0))));
		Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(1))));
		Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(2))));
		Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(3))));
		Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(4))));
		Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(5))));
		Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(6))));
		foundation.objectFocus(objText(orderPageData.get(7)));
		Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(7))));
		Assert.assertTrue(foundation.isDisplayed(objText(orderPageData.get(8))));
	}

	public void verifyVAT(String taxRate) {

		Double tax = Double.valueOf(taxRate);
		String uiSubTotal = foundation.getText(LBL_SUB_TOTAL).replace("$", Constants.EMPTY_STRING);
		String uiVat = foundation.getText(LBL_VAT_VALUE).replace("$", Constants.EMPTY_STRING);
		String uiBalanceDue = foundation.getText(LBL_BALANCE_DUE).replace("$", Constants.EMPTY_STRING);

		String VATValue = foundation.getText(LBL_TAX).replaceAll("[A-Z%@]", " ");
		Assert.assertTrue(taxRate.equals(VATValue.trim()));

		Double taxAmount = 1 + (tax / 100);
		double totalProductCost = Double.parseDouble(uiSubTotal) - Double.parseDouble(uiVat);
		double balanceDue = (totalProductCost) * (taxAmount);
		balanceDue = Math.round(balanceDue * 100.0) / 100.0;
		Assert.assertTrue(uiBalanceDue.contains(String.valueOf(balanceDue)));

	}

	public void verifyTax(String taxRate) {
		String uiSubTotal = foundation.getText(LBL_SUB_TOTAL).replace("$", Constants.EMPTY_STRING);
		String uiTax = foundation.getText(LBL_VAT_VALUE).replace("$", Constants.EMPTY_STRING);

		double calculatedTax = Double.parseDouble(uiSubTotal) * (Double.valueOf(taxRate) / 100);
		double expectedTaxWithRoundUp = Math.round(calculatedTax * 100.0) / 100.0;

		Assert.assertEquals(Double.parseDouble(uiTax), expectedTaxWithRoundUp);
	}
	
	public String getSubtotal() {		
		String uiSubTotal = foundation.getText(LBL_BALANCE_DUE).replace("$", Constants.EMPTY_STRING);
		return uiSubTotal;
	}
}
