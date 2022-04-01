package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class Middid {
	public static final By TITL_MIDDID = By.id("Middid");
	public static final By TXT_SEARCH = By.xpath("//div[@id='dt_filter']//input");
	public static final By LBL_ROW_DATA = By.xpath("//tbody//tr//td");
	public static final By LBL_ROW_DATA_DATE_ASSIGNED = By.xpath("//tbody//tr//td[7]");
	public static final By LBL_NO_RESULT = By.xpath("//td[text()='No matching records found']");
	public static final By LST_COLUMN_HEADERS = By.xpath("//thead//th");

	public List<String> tableHeaders = new ArrayList<>();

	public void getTableHeaders() {
		List<WebElement> columnHeaders = Foundation.getDriver().findElements(LST_COLUMN_HEADERS);
		for (WebElement columnHeader : columnHeaders) {
			tableHeaders.add(columnHeader.getText());
		}
	}

	public void verifyMiddidHeaders(List<String> columnNames) {
		try {
			System.out.println("tableHeaders :"+ tableHeaders);
			System.out.println("columnNames :"+ columnNames);
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				CustomisedAssert.assertTrue(tableHeaders.get(iter).equals(columnNames.get(iter)));
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

}
