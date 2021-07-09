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
import at.smartshop.keys.Constants;

public class ProductPricingReport extends Factory {
	
	private Foundation foundation = new Foundation();
	private LocationSummary locationSummary = new LocationSummary();

	private static final By TBL_PRODUCT_PRICING = By.id("rptdt");
	public static final By LBL_REPORT_NAME = By.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By TBL_PRODUCT_PRICING_GRID = By.cssSelector("#rptdt > tbody");

	private List<String> tableHeaders = new ArrayList<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_PRODUCT_PRICING_GRID);
			WebElement tableReports = getDriver().findElement(TBL_PRODUCT_PRICING);
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
		return reportsData;
	}

	private int getRequiredRecord(String scancode) {
		int rowCount = 0;
		try {
			int recordCount = 0;
			for (int val = 0; val < intialData.size(); val++) {
				if (intialData.get(val).get(tableHeaders.get(4)).equals(scancode)) {
					rowCount = recordCount;
					break;
				}
				recordCount++;
			}
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return rowCount;
	}

	public void verifyReportName(String reportName) {
		try {
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			Assert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void updateData(String value, String columnNames) {
		try {
			int rowCount = getRequiredRecord(value);
			Map<Integer, Map<String, String>> productsData = locationSummary.getProductsRecords(value);
			int recordCount = 0;
			for(recordCount = 0; recordCount < productsData.size(); recordCount++) {
				if((productsData.get(recordCount).get(tableHeaders.get(4))).equals(value)) {
					break;
				}
			}
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			intialData.get(rowCount).put(tableHeaders.get(0), productsData.get(recordCount).get(columnName.get(0)));
			intialData.get(rowCount).put(tableHeaders.get(1), productsData.get(recordCount).get(columnName.get(1)));
			intialData.get(rowCount).put(tableHeaders.get(2), productsData.get(recordCount).get(columnName.get(2)));
			intialData.get(rowCount).put(tableHeaders.get(3), productsData.get(recordCount).get(columnName.get(3)));
			intialData.get(rowCount).put(tableHeaders.get(4), productsData.get(recordCount).get(columnName.get(4)));
			intialData.get(rowCount).put(tableHeaders.get(5), productsData.get(recordCount).get(columnName.get(5)));
			intialData.get(rowCount).put(tableHeaders.get(6), productsData.get(recordCount).get(columnName.get(6)));
			intialData.get(rowCount).put(tableHeaders.get(7), productsData.get(recordCount).get(columnName.get(7)));
			intialData.get(rowCount).put(tableHeaders.get(8), productsData.get(recordCount).get(columnName.get(8)));
			intialData.get(rowCount).put(tableHeaders.get(9), productsData.get(recordCount).get(columnName.get(8)));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			for (int iter = 0; iter < tableHeaders.size(); iter++) {
				Assert.assertTrue(tableHeaders.get(iter).equals(columnName.get(iter)));
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

	public Map<Integer, Map<String, String>> getIntialData() {
		return intialData;
	}

	public Map<Integer, Map<String, String>> getReportsData() {
		return reportsData;
	}

	public List<String> getTableHeaders() {
		return tableHeaders;
	}

}
