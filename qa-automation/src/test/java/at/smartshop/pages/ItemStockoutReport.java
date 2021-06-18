package at.smartshop.pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class ItemStockoutReport extends Factory {

	private Foundation foundation = new Foundation();

	private static final By TBL_ITEM_STOCKOUT = By.id("summarydt");
	private static final By LBL_REPORT_NAME = By.cssSelector("#summarydt > caption");
	private static final By TBL_ITEM_STOCKOUT_GRID = By.cssSelector("#summarydt > tbody");
	private static final By TBL_ITEM_STOCKOUT_DETAILS = By.cssSelector("table[aria-describedby='detaildt_info']");
	private static final By TBL_ITEM_STOCKOUT_DETAILS_GRID = By
			.cssSelector("table[aria-describedby='detaildt_info'] > tbody");
	public static final By TXT_PRODUCT_FILTER = By.cssSelector("input[placeholder='  - Enter Product Description -']");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> itemStockoutDetailsHeaders = new ArrayList<>();
	private int recordCount;
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportsDetailsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialDetailsData = new LinkedHashMap<>();

	public void getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_ITEM_STOCKOUT_GRID);
			WebElement tableReports = getDriver().findElement(TBL_ITEM_STOCKOUT);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				tableHeaders.add(columnHeader.getText());
			}
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < tableHeaders.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					uiTblRowValues.put(tableHeaders.get(columnCount - 1), column.getText());
				}
				reportsData.put(recordCount, uiTblRowValues);
				recordCount++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void getItemStockoutDetails() {
		try {
			int recordCount = 0;
			itemStockoutDetailsHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_ITEM_STOCKOUT_DETAILS_GRID);
			WebElement tableReports = getDriver().findElement(TBL_ITEM_STOCKOUT_DETAILS);
			List<WebElement> columnHeaders = tableReports.findElements(By.cssSelector("thead > tr > th"));
			List<WebElement> rows = tableReportsList.findElements(By.tagName("tr"));
			for (WebElement columnHeader : columnHeaders) {
				itemStockoutDetailsHeaders.add(columnHeader.getText());
			}
			for (WebElement row : rows) {
				Map<String, String> uiTblRowValues = new LinkedHashMap<>();
				for (int columnCount = 1; columnCount < itemStockoutDetailsHeaders.size() + 1; columnCount++) {
					WebElement column = row.findElement(By.cssSelector("td:nth-child(" + columnCount + ")"));
					uiTblRowValues.put(itemStockoutDetailsHeaders.get(columnCount - 1), column.getText());
				}
				reportsDetailsData.put(recordCount, uiTblRowValues);
				recordCount++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void getRequiredRecord(String scancode) {
		try {
			for (int rowCount = 0; rowCount < intialData.size(); rowCount++) {
				if (intialData.get(rowCount).get(tableHeaders.get(2)).equals(scancode)) {
					recordCount = rowCount;
					break;
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyReportName(String reportName) {
		try {
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateData(String values, String invValue, String stockoutTime) {
		try {
			List<String> value = Arrays.asList(values.split(Constants.DELIMITER_HASH));
			for (int count = 0; count < value.size(); count++) {
				intialData.get(recordCount).put(tableHeaders.get(count), value.get(count));
//				intialData.get(recordCount).put(tableHeaders.get(1), value.get(1));
//				intialData.get(recordCount).put(tableHeaders.get(2), value.get(2));
//				intialData.get(recordCount).put(tableHeaders.get(3), value.get(3));
//				intialData.get(recordCount).put(tableHeaders.get(4), value.get(4));
			}
			intialData.get(recordCount).put(tableHeaders.get(5), invValue);
			intialData.get(recordCount).put(tableHeaders.get(6), stockoutTime);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public int getHeaderCount(String headerName) {
		int headerCount = 0;
		try {
			for (headerCount = 0; headerCount < tableHeaders.size(); headerCount++) {
				if (tableHeaders.get(headerCount).equals(headerName)) {
					break;
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return headerCount + 1;
	}

	public void navigateToProductsEvents(String headerNames, String locationName, String scancode) {
		try {
			List<String> headerName = Arrays.asList(headerNames.split(Constants.DELIMITER_HASH));
			int locationCount = getHeaderCount(headerName.get(0));
			int scancodeCount = getHeaderCount(headerName.get(1));
			foundation.click(By.xpath("//table[@id = 'summarydt']/tbody/tr/td[" + scancodeCount + "][text()='"
					+ scancode + "']//..//td[" + locationCount + "]/a[text()='" + locationName + "']"));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public int getReportDetailsRecord(String stockout, String reason) {
		int rowCount = 0;
		boolean flag = false;
		for (rowCount = 0; rowCount < intialDetailsData.size(); rowCount++) {
			if (intialDetailsData.get(rowCount).get(itemStockoutDetailsHeaders.get(0)).equals(stockout)
					&& intialDetailsData.get(rowCount).get(itemStockoutDetailsHeaders.get(2)).equals(reason)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			Assert.fail();
		}
		return rowCount;
	}

	public void verifyReportHeaders(List<String> headers, String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < headers.size(); iter++) {
				Assert.assertTrue(headers.get(iter).equals(columnName.get(iter)));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyReportData() {
		try {
			int count = intialData.size();
			for (int counter = 0; counter < count; counter++) {
				for (int iter = 0; iter < tableHeaders.size(); iter++) {
					Assert.assertTrue(reportsData.get(counter).get(tableHeaders.get(iter))
							.contains(intialData.get(counter).get(tableHeaders.get(iter))));
				}
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateDetailsData(String stockoutTime, String values, String reason) {
		try {
			int rowCount = getReportDetailsRecord(stockoutTime, reason);
			List<String> value = Arrays.asList(values.split(Constants.DELIMITER_HASH));
			intialDetailsData.get(rowCount).put(tableHeaders.get(0), stockoutTime);
			for (int count = 0; count < value.size(); count++) {
				intialDetailsData.get(rowCount).put(tableHeaders.get(count + 1), value.get(count));
//			intialDetailsData.get(rowCount).put(tableHeaders.get(2), value.get(1));
//			intialDetailsData.get(rowCount).put(tableHeaders.get(3), value.get(2));
//			intialDetailsData.get(rowCount).put(tableHeaders.get(4), value.get(3));
//			intialDetailsData.get(rowCount).put(tableHeaders.get(5), value.get(4));
//			intialDetailsData.get(rowCount).put(tableHeaders.get(6), value.get(5));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public String getStockoutTime(String reportFormat, String reportTimeZone) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(reportFormat);
		TimeZone timeZone = TimeZone.getTimeZone(reportTimeZone);
		formatter.setTimeZone(timeZone);
		return (formatter.format(date));
	}

	public void verifyReportDetailsData(String stockoutTime, String scancode) {
		try {
			int rowCount = getReportDetailsRecord(stockoutTime, scancode);
			for (int iter = 0; iter < itemStockoutDetailsHeaders.size(); iter++) {
				Assert.assertTrue(reportsDetailsData.get(rowCount).get(itemStockoutDetailsHeaders.get(iter))
						.contains(intialDetailsData.get(rowCount).get(itemStockoutDetailsHeaders.get(iter))));
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public Map<Integer, Map<String, String>> getIntialData() {
		return intialData;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public Map<Integer, Map<String, String>> getIntialDetailsData() {
		return intialDetailsData;
	}

	public Map<Integer, Map<String, String>> getReportsDetailsData() {
		return reportsDetailsData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

	public List<String> getItemStockoutDetailsHeaders() {
		return itemStockoutDetailsHeaders;
	}

}
