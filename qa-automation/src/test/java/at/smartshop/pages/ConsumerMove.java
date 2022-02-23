package at.smartshop.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;

import at.framework.browser.Factory;
import at.framework.generic.DateAndTime;
import at.framework.ui.Dropdown;
import at.framework.ui.Foundation;
import at.framework.ui.Table;
import at.framework.ui.TextBox;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;

public class ConsumerMove extends Factory {
	private DateAndTime dateAndTime = new DateAndTime();
	private Dropdown dropDown = new Dropdown();
	private Foundation foundation = new Foundation();
	private TextBox textBox = new TextBox();
	private Table table = new Table();

	public static final By DPD_ORG = By.id("org");
	public static final By DPD_LOCATION = By.id("locations");
	public static final By BTN_GO = By.id("findBtn");
	public static final By TXT_SEARCH_FILTER = By.xpath("//*[@id='consumerdt_filter']//input");
	public static final By BTN_MOVE = By.id("moveBtn");
	public static final By BTN_MOVE_LIST_OK = By.id("movelistok");
	public static final By BTN_SAVE = By.id("reasonSaveBtn");
	public static final By DPD_MOVE_TO_ORG = By.id("moveform-org");
	public static final By DPD_MOVE_TO_LOCATION = By.id("moveform-loc");
	public static final By BTN_EXPORT = By.id("exportResult");
	public static final By BTN_HISTORY = By.xpath("//*[text()='History']");
	public static final By LBL_COMPLETE_MOVE = By.xpath("//*[@class='row-fluid']//span");
	public static final By LBL_COMPLETE_MOVE_SUCCESS = By
			.xpath("//*[@class='row-fluid']//span[text()='Consumer move is complete, please see result!']");
	public static final By TBL_CONSUMER = By.xpath("//*[@id='consumerdt_wrapper']//*[@id='consumerdt']");
	public static final By TBL_CONSUMER_ROW = By
			.xpath("//*[@id='consumerdt_wrapper']//*[@id='consumerdt']//tbody/tr[@class='odd']//td");

	public Map<String, String> getExcelDataAsMap(String filePath) throws IOException {
		FileInputStream fis = new FileInputStream(filePath);
		HSSFWorkbook workBook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workBook.getSheetAt(0);
		Map<String, String> singleRowData = new HashMap<>();
		List<String> columnHeader = new ArrayList<String>();
		Row row = sheet.getRow(1);
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			columnHeader.add(cellIterator.next().getStringCellValue());
		}
		int rowCount = sheet.getLastRowNum();
		int columnCount = row.getLastCellNum();
		for (int i = 2; i <= rowCount; i++) {

			Row row1 = sheet.getRow(i);
			for (int j = 0; j < columnCount; j++) {
				Cell cell = row1.getCell(j);
				int cellType = cell.getCellType();

				if (cellType == 0) {
					singleRowData.put(columnHeader.get(j), String.valueOf(cell.getNumericCellValue()));

				} else {

					singleRowData.put(columnHeader.get(j), cell.getStringCellValue());
				}

			}

		}
		workBook.close();
		return singleRowData;
	}

	public String getFileName() {
		String date = dateAndTime.getDateAndTime(Constants.REGEX_MMDDYYYY, Constants.TIME_ZONE_INDIA);
		final String path = FilePath.HOME_PATH + "\\Downloads\\consumer_move_result" + date + ".xls";
		return path;
	}

	public boolean moveConsumer(String consumer, String toOrg, String toLocation) {
		table.selectRow(consumer);
		foundation.click(ConsumerMove.BTN_MOVE);
		foundation.waitforElement(ConsumerMove.BTN_MOVE_LIST_OK, Constants.SHORT_TIME);
		foundation.click(ConsumerMove.BTN_MOVE_LIST_OK);
		dropDown.selectItem(ConsumerMove.DPD_MOVE_TO_ORG, toOrg, Constants.TEXT);
		foundation.threadWait(Constants.THREE_SECOND);
		dropDown.selectItem(ConsumerMove.DPD_MOVE_TO_LOCATION, toLocation, Constants.TEXT);
		foundation.click(ConsumerMove.BTN_SAVE);
		foundation.waitforElement(ConsumerMove.BTN_EXPORT, Constants.SHORT_TIME);
		return foundation.isDisplayed(ConsumerMove.LBL_COMPLETE_MOVE_SUCCESS);
	}

	public boolean searchConsumer(String consumer, String fromOrg, String fromLocation) {
		dropDown.selectItem(ConsumerMove.DPD_ORG, fromOrg, Constants.TEXT);
		foundation.threadWait(Constants.THREE_SECOND);
		dropDown.selectItem(ConsumerMove.DPD_LOCATION, fromLocation, Constants.TEXT);
		foundation.click(ConsumerMove.BTN_GO);
		textBox.enterText(ConsumerMove.TXT_SEARCH_FILTER, consumer);
		return foundation.isDisplayed(By.xpath("//tr//*[text()='" + consumer + "']"));
	}

}
