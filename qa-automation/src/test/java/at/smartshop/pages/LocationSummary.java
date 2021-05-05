package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;

public class LocationSummary extends Factory {

	public static final By dpdDisabled= By.id("isdisabled");
	public static final By btnSave= By.id("saveBtn");
	public static final By tabProducts= By.cssSelector("a#loc-products");
	public static final By tblProductsList = By.cssSelector("#productDataGrid > tbody > td");
	public static final By popupBtnSave =  By.id("confirmDisableId");
}
