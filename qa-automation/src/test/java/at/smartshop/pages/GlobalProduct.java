package at.smartshop.pages;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;

public class GlobalProduct extends Factory {
	public static final By TXT_FILTER = By.id("filterType");
	public static final By TBL_GRID = By.id("dataGrid");
	public static final By BTN_EXPORT = By.xpath("//button[text()='Export']");
	public static final By TXT_RECORD_COUNT = By.cssSelector("#dataGrid_pager_label");
	public static final By TBL_ROW = By.xpath("//*[@id='dataGrid']/tbody");

	public By getGlobalProduct(String product) {
		return By.xpath("//td[@aria-describedby='dataGrid_name'][text()='" + product + "']");
	}

	public Map<String, String> getTblRecordsUI() {
		Map<String, String> uiTblRowValues = new HashMap<>();
		try {
			int curColumnIndex = 1;
			WebElement tableReports = getDriver().findElement(TBL_GRID);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th"));
			WebElement row = getDriver().findElement(TBL_ROW);

			for (WebElement columnHeader : columnHeaders) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + curColumnIndex + ")"));
				uiTblRowValues.put(columnHeader.getText(), column.getText());
				curColumnIndex++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return uiTblRowValues;
	}

	public boolean verifyExcelData(List<String> uiList, List<String> excelList) {

		try {

			for (String uiCelldata : uiList) {
				Boolean isTest = false;
				for (String cell : excelList) {

					if (uiCelldata.equals(cell)) {
						isTest = true;
						break;
					}
				}
				if (isTest.equals(false)) {
					return false;
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return true;

	}

}
