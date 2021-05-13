package at.smartshop.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;

public class LocationSummary extends Factory {

	private TextBox textBox = new TextBox();
	private Foundation foundation = new Foundation();

	public static final By DPD_DISABLED = By.id("isdisabled");
	public static final By BTN_SAVE = By.id("saveBtn");
	public static final By BTN_MANAGE_COLUMNS = By.id("saveBtn");
	public static final By DLG_COLUMN_CHOOSER = By.id("productDataGrid_hiding_modalDialog_content");
	public static final By DLG_COLUMN_CHOOSER_OPTIONS = By
			.cssSelector("#productDataGrid_hiding_modalDialog_content > ul");
	public static final By TBL_PRODUCTS = By.cssSelector("a#loc-products");
	public static final By TBL_PRODUCTS_GRID = By.cssSelector("a#loc-products > tbody");
	public static final By TBL_PRODUCTS_LIST = By.cssSelector("#productDataGrid > tbody > td");
	public static final By TAB_CONTAINER_GRID = By.cssSelector("#tabcontainer > ul");
	public static final By TXT_PRODUCT_FILTER = By.id("productFilterType");
	public static final By POP_UP_BTN_SAVE = By.id("confirmDisableId");

	public List<String> getProductsHeaders() {
		List<String> tableHeaders = new ArrayList<>();
		try {
			WebElement tableProducts = getDriver().findElement(TBL_PRODUCTS);
			List<WebElement> columnHeaders = tableProducts.findElements(By.cssSelector("thead > tr > th"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return tableHeaders;
	}

	public void selectTab(String tabName) {
		try {
			boolean flag = false;
			WebElement tabContainer = getDriver().findElement(TAB_CONTAINER_GRID);
			List<WebElement> tabs = tabContainer.findElements(By.tagName("li > a"));
			for (WebElement tab : tabs) {
				if (tab.getText().equals(tabName)) {
					foundation.click((By) tab);
					// tab.click();
					flag = true;
					break;
				}
			}
			if (!flag) {
				Assert.fail();
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void manageColumn(String columnNames) {
		try {
			foundation.click(BTN_MANAGE_COLUMNS);
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			int columnCount = columnName.size();
			WebElement columnsOptions = getDriver().findElement(DLG_COLUMN_CHOOSER_OPTIONS);
			for (int count = 0; count < columnCount; count++) {
				List<WebElement> optionNames = columnsOptions.findElements(By.tagName("li > span > span"));
				for (WebElement optionName : optionNames) {
					if (textBox.getText((By) optionName).equals(columnName.get(count))) {
					//if (optionName.getText().equals(columnName.get(count))) {
						foundation.click((By) optionName);
						//optionName.click();
					}
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public String getProductsCellValue(String recordValue, String columnName) {
		Map<Integer, Map<String, String>> productsData = new LinkedHashMap<>();
		int recordCount = 0;
		String columnValue = Constants.EMPTY_STRING;
		try {
			List<String> tableHeaders = getProductsHeaders();
			WebElement tableProductsGrid = getDriver().findElement(TBL_PRODUCTS_GRID);
			List<WebElement> rows = tableProductsGrid.findElements(By.tagName("tr"));
			textBox.enterText(TXT_PRODUCT_FILTER, recordValue);
			for (WebElement row : rows) {
				Map<String, String> productsRecord = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					productsRecord.put(tableHeaders.get(columnCount - 1), column.getText());
				}
				productsData.put(recordCount, productsRecord);
				recordCount++;
			}
			columnValue = productsData.get(recordCount).get(columnName);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return columnValue;
	}
}
