package at.framework.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import com.aventstack.extentreports.Status;

import at.framework.reportsetup.ExtFactory;
import at.framework.ui.Foundation;
import at.smartshop.keys.Constants;

public class Excel {

	PropertyFile propertyFile = new PropertyFile();
	Foundation foundation = new Foundation();

	public void writeToExcel(String fileName, String workSheetName, String iterator, String cellValue) {
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook();
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			wb = new HSSFWorkbook(fis);
			HSSFSheet workSheet = wb.getSheet(workSheetName);

			List<String> iterationCount = Arrays.asList(iterator.split(Constants.DELIMITER_HASH));
			int columnCount = workSheet.getRow(Integer.parseInt(iterationCount.get(0))).getLastCellNum();
			if (cellValue.contains("~")) {
				List<String> reqValue = Arrays.asList(cellValue.split("~"));
				int reqIter = reqValue.size();
				for (int i = (Integer.parseInt(iterationCount.get(1))); i < reqIter + 1; i++) {
					List<String> value = Arrays.asList(reqValue.get(i - 1).split(Constants.DELIMITER_HASH));
					HSSFRow row = workSheet.getRow(i);
					for (int j = 0; j < columnCount; j++) {
						Cell cell = row.getCell(j);
						cell.setCellValue(value.get(j));
					}
				}
			} else {
				List<String> reqValue = Arrays.asList(cellValue.split(Constants.DELIMITER_HASH));
				for (int i = (Integer.parseInt(iterationCount.get(1))); i < Integer
						.parseInt(iterationCount.get(2)); i++) {
					HSSFRow row = workSheet.getRow(i);
					for (int j = 0; j < columnCount; j++) {
						Cell cell = row.getCell(j);
						cell.setCellValue(reqValue.get(j));
					}
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			try {
				FileOutputStream out = new FileOutputStream(new File(fileName));
				wb.write(out);
				out.close();
			} catch (Exception exc) {
				Assert.fail(exc.toString());
			}
		}
	}

	public boolean verifyExcelData(List<String> uiList, String filePath, int rowNum) {
		XSSFWorkbook workBook = null;
		Boolean isTest = false;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);

			workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheetAt(0);
			XSSFRow row = sheet.getRow(rowNum);

			for (String uiCelldata : uiList) {
				isTest = false;
				String.valueOf(uiCelldata);
				String uiRecord = uiCelldata.trim();

				for (Cell cell : row) {
					String cellValue;
					if (cell.getCellType() == 0) {
						double cellVal = cell.getNumericCellValue();
						cellValue = "$" + String.valueOf(cellVal) + 0;

					} else if (cell.getCellType() == 4) {
						cellValue = String.valueOf(cell.getBooleanCellValue());
					} else {
						cellValue = String.valueOf(cell.getStringCellValue());
					}
					if (uiRecord.equals(cellValue)) {
						isTest = true;
						ExtFactory.getInstance().getExtent().log(Status.INFO,
								"UI record [" + uiRecord + "] is available in excel");
						break;
					}
				}
				if (isTest.equals(false)) {
					ExtFactory.getInstance().getExtent().log(Status.INFO,
							"UI record [" + uiRecord + "] is not available in excel");
					return false;
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			try {

				workBook.close();
			} catch (Exception exc) {
				Assert.fail(exc.toString());
			}
		}

		return isTest;

	}

	public boolean verifyFirstCellData(String uiList, String filePath, int rowNum) {
		XSSFWorkbook workBook = null;
		Boolean isTest = false;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);

			workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheetAt(0);
			XSSFRow row = sheet.getRow(rowNum);

			String cellValue = String.valueOf(row.getCell(0).getStringCellValue());

			Assert.assertTrue(cellValue.contains(uiList));
			ExtFactory.getInstance().getExtent().log(Status.INFO, "UI record [" + uiList + "] is available in excel");

		} catch (Exception exc) {
			Assert.fail(exc.toString());
		} finally {
			try {

				workBook.close();
			} catch (Exception exc) {
				Assert.fail(exc.toString());
			}
		}

		return isTest;

	}

	public int getExcelRowCount(String filePath) {
		int rowNum = 0;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			XSSFWorkbook workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheetAt(0);
			rowNum = sheet.getLastRowNum();
			workBook.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return rowNum;

	}

	public boolean isFileDownloaded(String filePath) {
		final int sleepTime = 10;
		File file = new File(filePath);
		final int timeout = 10 * sleepTime;
		int timeElapsed = 0;
		while (timeElapsed < timeout) {
			if (file.exists()) {
				return true;
			} else {
				timeElapsed += sleepTime;
				foundation.threadWait(sleepTime);
			}
		}
		return false;
	}

	public Map<String, String> getExcelAsMap(String fileName, String workSheetName) throws IOException {
		HSSFWorkbook workBook = null;
		Map<String, String> singleRowData = new HashMap<>();
		List<String> columnHeader = new ArrayList<String>();
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			workBook = new HSSFWorkbook(fis);
			HSSFSheet workSheet = workBook.getSheet(workSheetName);
			Row row = workSheet.getRow(0);
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				columnHeader.add(cellIterator.next().getStringCellValue());
			}
			int rowCount = workSheet.getLastRowNum();
			int columnCount = row.getLastCellNum();
			for (int i = 1; i <= rowCount; i++) {

				Row row1 = workSheet.getRow(i);
				for (int j = 0; j < columnCount; j++) {
					Cell cell = row1.getCell(j);
					int cellType = cell.getCellType();

					if (cellType == 0) {
						singleRowData.put(columnHeader.get(j), String.valueOf(cell.getNumericCellValue()));

					} else if (cellType == 4) {
						singleRowData.put(columnHeader.get(j), String.valueOf(cell.getBooleanCellValue()));
					} else {

						singleRowData.put(columnHeader.get(j), cell.getStringCellValue());
					}
				}
			}
			workBook.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return singleRowData;
	}

	public Map<String, String> getExcelAsMapFromXSSFWorkbook(String fileName) throws IOException {
		XSSFWorkbook workBook = null;
		Map<String, String> singleRowData = new HashMap<>();
		List<String> columnHeader = new ArrayList<String>();
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			workBook = new XSSFWorkbook(fis);
			XSSFSheet workSheet = workBook.getSheetAt(0);
			XSSFRow row1 = workSheet.getRow(0);
			Iterator<Cell> cellIterator = row1.cellIterator();
			while (cellIterator.hasNext()) {
				columnHeader.add(cellIterator.next().getStringCellValue());
			}
			int rowCount = workSheet.getLastRowNum();
			int columnCount = row1.getLastCellNum();
			for (int i = 1; i <= rowCount; i++) {

				XSSFRow row2 = workSheet.getRow(i);
				for (int j = 0; j < columnCount; j++) {
					Cell cell = row2.getCell(j);
					int cellType = cell.getCellType();

					if (cellType == 0) {
						singleRowData.put(columnHeader.get(j), String.valueOf(cell.getNumericCellValue()));

					} else if (cellType == 4) {
						singleRowData.put(columnHeader.get(j), String.valueOf(cell.getBooleanCellValue()));
					} else {

						singleRowData.put(columnHeader.get(j), cell.getStringCellValue());
					}
				}
			}
			workBook.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return singleRowData;
	}

	public Map<String, String> getExcelData(String fileName, String workSheetName) throws IOException {
		HSSFWorkbook workBook = null;
		Map<String, String> singleRowData = new HashMap<>();
		List<String> columnHeader = new ArrayList<String>();
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			workBook = new HSSFWorkbook(fis);
			HSSFSheet workSheet = workBook.getSheet(workSheetName);
			Row row = workSheet.getRow(2);
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				columnHeader.add(cellIterator.next().getStringCellValue());
			}
			int rowCount = workSheet.getLastRowNum();
			int columnCount = row.getLastCellNum();
			for (int i = 2; i <= rowCount; i++) {

				Row row1 = workSheet.getRow(i);
				for (int j = 0; j < columnCount; j++) {
					Cell cell = row1.getCell(j);
					int cellType = cell.getCellType();

					if (cellType == 0) {
						singleRowData.put(columnHeader.get(j), String.valueOf(cell.getNumericCellValue()));

					} else if (cellType == 4) {
						singleRowData.put(columnHeader.get(j), String.valueOf(cell.getBooleanCellValue()));
					} else {

						singleRowData.put(columnHeader.get(j), cell.getStringCellValue());
					}
				}
			}
			workBook.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return singleRowData;
	}

}
