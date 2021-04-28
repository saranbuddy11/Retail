package at.smartshop.pages;

import org.openqa.selenium.By;
import at.framework.browser.Factory;

public class LocationSummary extends Factory {

	public By dpdDisabled= By.id("isdisabled");
	public By btnSave= By.id("saveBtn");
	public By tabProducts= By.cssSelector("a#loc-products");
	public By tblProductsList = By.cssSelector("#productDataGrid > tbody > td");
	public By popupBtnSave =  By.id("confirmDisableId");
}
