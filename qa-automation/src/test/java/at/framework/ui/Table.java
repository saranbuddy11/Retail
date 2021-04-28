package at.framework.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;

public class Table extends Factory{
	
	Foundation foundation=new Foundation();
	
	public void selectRow(String dataGridname, String product) {
		try {
		By rowData = By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + product + "']");
		foundation.click(rowData);
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public int getTblRowCount(By object) {
		int tableSize = 0;
		try {
			List<WebElement> tableProductsList = getDriver().findElements(object);
			tableSize = tableProductsList.size();
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return tableSize;
	}
	
	public void selectRow(String text) {
		try {
			By rowData = By.xpath("//tr//*[text()='"+text+"']");
			foundation.click(rowData);
		}
		catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public Map<String, String> getTblRecordsUI(By tableGrid, By tableRow) {
		Map<String, String> uiTblRowValues = new HashMap<>();
		try {
			int index = 1;

			WebElement tableReports = getDriver().findElement(tableGrid);
			List<WebElement> headers = tableReports.findElements(By.tagName("th"));
			WebElement row = getDriver().findElement(tableRow);

			for (WebElement header : headers) {
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + index + ")"));
				uiTblRowValues.put(header.getText(), column.getText());
				index++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return uiTblRowValues;
	}
	
	public Map<String, String> getTblHeadersUI(By tableGrid) {
		Map<String, String> uiTblHeaders = new HashMap<>();
		try {
			int index = 1;

			WebElement tableReports = getDriver().findElement(tableGrid);
			List<WebElement> headers = tableReports.findElements(By.tagName("th"));
			
			for (WebElement header : headers) {
				getDriver().findElement(By.cssSelector("table#dataGrid > thead > tr > th:nth-child(" + index + ")"));
				uiTblHeaders.put(header.getText(), header.getText());
				index++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return uiTblHeaders;
	}

}
