package at.smartshop.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.openqa.selenium.By;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.framework.files.JsonFile;
import at.framework.files.PropertyFile;
import at.framework.generic.CustomisedAssert;
import at.framework.ui.Foundation;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.keys.Reports;
import at.smartshop.tests.TestInfra;
import at.smartshop.utilities.WebService;

public class SalesAnalysisReport {
	
	public static final By LBL_REPORT_NAME = By.cssSelector("div[id='Sales Analysis Report']");
	private Foundation foundation = new Foundation();
	private WebService webService = new WebService();
	private JsonFile jsonFunctions = new JsonFile();
	private PropertyFile propertyFile = new PropertyFile();
	
	
	private Map<String, Object> jsonData = new HashMap<>();

	
	public void verifyReportName(String reportName) {
		try {
			String reportTitle = foundation.getText(LBL_REPORT_NAME);
			CustomisedAssert.assertTrue(reportTitle.contains(reportName));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public void processAPI(String value) {
		try {
			generateJsonDetails(value);
			salesJsonDataUpdate();
			webService.apiReportPostRequest(	propertyFile.readPropertyFile(Configuration.TRANS_SALES, FilePath.PROPERTY_CONFIG_FILE),
																(String) jsonData.get(Reports.JSON));
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	private void generateJsonDetails(String reportFormat) {
		try {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Reports.DATE_FORMAT);
			DateTimeFormatter reqFormat = DateTimeFormatter.ofPattern(reportFormat);
			LocalDateTime tranDate = LocalDateTime.now();
			String transDate = tranDate.format(dateFormat);
			String reportDate = tranDate.format(reqFormat);
			String transID = propertyFile.readPropertyFile(Configuration.DEVICE_ID, FilePath.PROPERTY_CONFIG_FILE)
					+ Constants.DELIMITER_HYPHEN
					+ transDate.replaceAll(Reports.REGEX_TRANS_DATE, Constants.EMPTY_STRING);
			jsonData.put(Reports.TRANS_ID, transID);
			jsonData.put(Reports.TRANS_DATE, transDate);
			jsonData.put(Reports.TRANS_DATE_TIME, reportDate);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	private void salesJsonDataUpdate() {
		try {
			String salesHeaderID = UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN,Constants.EMPTY_STRING);
			String saleValue = jsonFunctions.readFileAsString(FilePath.JSON_SALES_CREATION);
			JsonObject saleJson = jsonFunctions.convertStringToJson(saleValue);
			saleJson.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			saleJson.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			String sale = saleJson.get(Reports.SALE).getAsString();
			JsonObject salesObj = jsonFunctions.convertStringToJson(sale);
			salesObj.addProperty(Reports.ID, salesHeaderID);
			salesObj.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
			salesObj.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));
			jsonArrayDataUpdate(salesObj, Reports.ITEMS, salesHeaderID);
			jsonArrayDataUpdate(salesObj, Reports.PAYMENTS, salesHeaderID);
			saleJson.addProperty(Reports.SALE, salesObj.toString());
			jsonData.put(Reports.JSON, saleJson.toString());
			jsonData.put(Reports.SALES, salesObj);
			
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	private void jsonArrayDataUpdate(JsonObject jsonObj, String reqString, String salesheader) {
		try {
			JsonArray items = jsonObj.get(reqString).getAsJsonArray();
			for (JsonElement item : items) {
				JsonObject json = item.getAsJsonObject();
				json.addProperty(Reports.ID,
						UUID.randomUUID().toString().replace(Constants.DELIMITER_HYPHEN, Constants.EMPTY_STRING));
				json.addProperty(Reports.SALES_HEADER, salesheader);
				json.addProperty(Reports.TRANS_ID, (String) jsonData.get(Reports.TRANS_ID));
				json.addProperty(Reports.TRANS_DATE, (String) jsonData.get(Reports.TRANS_DATE));				
			}
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	
	public By objReportColumn(String columnName, String productName) {
		return By.xpath("//table//td[text()='"+productName+"']/following::td[@aria-describedby='hierarchicalGrid_"+columnName+"']");
	}
	
	public double getGMValueUsingCalculation(String productPrice,String productName, String columnSold,String columnDiscount,String columnCost) {
		String soldCount = foundation.getText(objReportColumn(columnSold, productName));
		double totalProductPrice = Double.parseDouble(soldCount)*Double.parseDouble(productPrice);
		
		String discountValue = foundation.getText(objReportColumn(columnDiscount, productName)).replace("$", Constants.EMPTY_STRING);
		String costValue = foundation.getText(objReportColumn(columnCost, productName)).replace("$", Constants.EMPTY_STRING);
		
		double finalValue = (totalProductPrice-Double.parseDouble(discountValue.trim())-Double.parseDouble(costValue.trim()))*100/(totalProductPrice-Double.parseDouble(discountValue.trim()));
		finalValue = Math.round(finalValue*100.0)/100.0;
		return finalValue;
	}
	
	public double getGMValue(String columnName, String productName) {
		String value = foundation.getText(objReportColumn(columnName, productName));
		return Double.parseDouble(value.replace("%", Constants.EMPTY_STRING).trim());
	}
	
}
