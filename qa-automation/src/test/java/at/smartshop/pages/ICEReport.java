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
import at.framework.files.PropertyFile;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class ICEReport extends Factory {

	private Foundation foundation = new Foundation();
	private PropertyFile propertyFile = new PropertyFile();
	private LocationSummary locationSummary = new LocationSummary();

	private static final By TBL_ICE = By.id("rptdt");
	private static final By LBL_REPORT_NAME = By
			.cssSelector("#report-container > div > div.col-12.comment-table-heading");
	private static final By TBL_ICE_GRID = By.cssSelector("#rptdt > tbody");

	private List<String> tableHeaders = new ArrayList<>();
	private List<String> admData = new ArrayList<>();
	private Map<Integer, Map<String, String>> productsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> reportsData = new LinkedHashMap<>();
	private Map<Integer, Map<String, String>> intialData = new LinkedHashMap<>();

	public Map<Integer, Map<String, String>> getTblRecordsUI() {
		try {
			int recordCount = 0;
			tableHeaders.clear();
			WebElement tableReportsList = getDriver().findElement(TBL_ICE_GRID);
			WebElement tableReports = getDriver().findElement(TBL_ICE);
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
				if (intialData.get(val).get(tableHeaders.get(2)).equals(scancode)) {
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
	
	public void updateFills(String scancode, String columnName, String reqCount) {
		int updatedInInv;
		try {
			int rowCount = getRequiredRecord(scancode);
			int recordCount = 0;
			for (recordCount = 0; recordCount < productsData.size(); recordCount++) {
				if ((productsData.get(recordCount).get(tableHeaders.get(2))).equals(scancode)) {
					break;
				}
			}
			String intialInInv = productsData.get(recordCount).get(columnName);
			if(Integer.parseInt(intialInInv) < 0) {
				updatedInInv = (Integer.parseInt(intialInInv) * -1) + Integer.parseInt(reqCount);
			} else {
				updatedInInv = Integer.parseInt(reqCount);
			}
			intialData.get(rowCount).put(tableHeaders.get(5), String.valueOf(updatedInInv));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void updateWaste(String value, String columnName, String reqCount) {
		int updatedInInv;
		try {
			int rowCount = getRequiredRecord(value);
			productsData = locationSummary.getProductsRecords(value);
			int recordCount = 0;
			for (recordCount = 0; recordCount < productsData.size(); recordCount++) {
				if ((productsData.get(recordCount).get(tableHeaders.get(2))).equals(value)) {
					break;
				}
			}
			String intialInInv = productsData.get(recordCount).get(columnName);
			updatedInInv = Integer.parseInt(intialInInv) - Integer.parseInt(reqCount);
			intialData.get(rowCount).put(tableHeaders.get(6), String.valueOf(updatedInInv));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void updateSold(String value) {
		try {
			int rowCount = getRequiredRecord(value);
			String intialSold = intialData.get(rowCount).get(tableHeaders.get(7));
			int updatedSold = Integer.parseInt(intialSold) + 1;
			intialData.get(rowCount).put(tableHeaders.get(7), String.valueOf(updatedSold));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}
	
	public void updateClosingLevel(String value, String columnName) {
		try {
			int rowCount = getRequiredRecord(value);
			productsData = locationSummary.getProductsRecords(value);
			int recordCount = 0;
			for (recordCount = 0; recordCount < productsData.size(); recordCount++) {
				if ((productsData.get(recordCount).get(tableHeaders.get(2))).equals(value)) {
					break;
				}
			}
			String inventoryValue = productsData.get(recordCount).get(columnName);
			intialData.get(rowCount).put(tableHeaders.get(8), String.valueOf(inventoryValue));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}


	public void updateData(String value, String columnNames) {
		try {
			int rowCount = getRequiredRecord(value);
			productsData = locationSummary.getProductsRecords(value);
			int recordCount = 0;
			for (recordCount = 0; recordCount < productsData.size(); recordCount++) {
				if ((productsData.get(recordCount).get(tableHeaders.get(2))).equals(value)) {
					break;
				}
			}
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HASH));
			intialData.get(rowCount).put(tableHeaders.get(0),
					propertyFile.readPropertyFile(Configuration.CURRENT_LOC, FilePath.PROPERTY_CONFIG_FILE));
			intialData.get(rowCount).put(tableHeaders.get(1), productsData.get(recordCount).get(columnName.get(5)));
			intialData.get(rowCount).put(tableHeaders.get(2), productsData.get(recordCount).get(columnName.get(1)));
			intialData.get(rowCount).put(tableHeaders.get(3), productsData.get(recordCount).get(columnName.get(0)));
			intialData.get(rowCount).put(tableHeaders.get(4), productsData.get(recordCount).get(columnName.get(3)));
			intialData.get(rowCount).put(tableHeaders.get(9), admData.get(2));
			intialData.get(rowCount).put(tableHeaders.get(10), admData.get(1));
			intialData.get(rowCount).put(tableHeaders.get(11), admData.get(0));
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
	}

	public void verifyReportHeaders(String columnNames) {
		try {
			List<String> columnName = Arrays.asList(columnNames.split(Constants.DELIMITER_HYPHEN));
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

	public List<String> getADMData() {
		return admData;
	}

}
