package at.framework.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import at.framework.browser.Factory;
import at.smartshop.keys.Constants;
import at.smartshop.tests.TestInfra;

public class Table extends Factory {

	Foundation foundation = new Foundation();

	public void selectRow(String dataGridname, String product) {
		try {
			By rowData = By.xpath("//td[@aria-describedby='" + dataGridname + "'][text()='" + product + "']");
			foundation.threadWait(Constants.SHORT_TIME);
			foundation.click(rowData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public int getTblRowCount(By object) {
		int tableSize = 0;
		try {
			List<WebElement> tableProductsList = getDriver().findElements(object);
			tableSize = tableProductsList.size();
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return tableSize;
	}

	public void selectRow(String text) {
		try {
			By rowData = By.xpath("//tr//td//a[contains(text(),'" + text + "')]");
			foundation.click(rowData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void selectRowWithoutContains(String text) {
		try {
			By rowData = By.xpath("//tr//td[contains(text(),'" + text + "')]");
			foundation.click(rowData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void selectrow(String text) {
		try {
			By rowData = By.xpath("//tr//span[contains(text(),'" + text + "')]");
			foundation.click(rowData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public Map<String, String> getTblSingleRowRecordUI(By tableGrid, By tableRow) {
		Map<String, String> uiTblRowValues = new HashMap<>();
		try {
			int index = 1;

			WebElement tableReports = getDriver().findElement(tableGrid);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th"));
			WebElement row = getDriver().findElement(tableRow);

			for (WebElement columnHeader : columnHeaders) {
				foundation.threadWait(Constants.ONE_SECOND);
				WebElement column = row.findElement(By.cssSelector("td:nth-child(" + index + ")"));
				uiTblRowValues.put(columnHeader.getText(), column.getText());
				index++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return uiTblRowValues;
	}

	public Map<String, String> getTblHeadersUI(By tableGrid) {
		Map<String, String> uiTblHeaders = new HashMap<>();
		try {
			int index = 1;

			WebElement tableReports = getDriver().findElement(tableGrid);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th"));

			for (WebElement columnHeader : columnHeaders) {
				getDriver().findElement(By.cssSelector("table#dataGrid > thead > tr > th:nth-child(" + index + ")"));
				uiTblHeaders.put(columnHeader.getText(), columnHeader.getText());
				index++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return uiTblHeaders;
	}

	public List<String> getColumnValues(String gridName) {
		String text = null;
		List<String> elementsText = new ArrayList<String>();
		try {
			List<WebElement> ListElement = getDriver()
					.findElements(By.xpath("//*[@aria-describedby='" + gridName + "']//a"));
			for (int i = 1; i <= ListElement.size(); i++) {
				text = getDriver()
						.findElement(By.xpath("(//*[@aria-describedby='" + gridName + "']//a)" + "[" + i + "]"))
						.getText();
				elementsText.add(text);
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return elementsText;
	}

	public void getColumnValues(int columnNumber) {
		int totalRows = foundation.getSizeofListElement(By.xpath("//tr")) - 1;
		for (int i = 1; i <= totalRows; i++) {
			foundation.getTextofListElement(By.xpath("//tr//td[" + columnNumber + "]"));
		}
	}

	public boolean isRowDisplayed(String text) {
		boolean status = false;
		try {
			By rowData = By.xpath("//tr//*[text()='" + text + "']");
			status = foundation.isDisabled(rowData);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return status;
	}

	public Map<String, String> getTblHeadersPickListHistory(By tableGrid) {
		Map<String, String> uiTblHeaders = new HashMap<>();
		try {
			int index = 1;

			WebElement tableReports = getDriver().findElement(tableGrid);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th"));

			for (WebElement columnHeader : columnHeaders) {
				getDriver().findElement(By.cssSelector("table> thead > tr > th:nth-child(" + index + ")"));
				uiTblHeaders.put(columnHeader.getText(), columnHeader.getText());
				index++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return uiTblHeaders;
	}

	public Map<String, String> getTblHeadersDevice(By tableGrid) {
		Map<String, String> uiTblHeaders = new HashMap<>();
		try {
			int index = 1;

			WebElement tableReports = getDriver().findElement(tableGrid);
			List<WebElement> columnHeaders = tableReports.findElements(By.tagName("th"));

			for (WebElement columnHeader : columnHeaders) {
				getDriver().findElement(By.cssSelector("table> thead > tr > th:nth-child(" + index + ")"));
				uiTblHeaders.put(columnHeader.getText(), columnHeader.getText());
				index++;
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return uiTblHeaders;
	}

}
