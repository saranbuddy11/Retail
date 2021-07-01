package at.framework.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

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

	public boolean verifyExcelHeaders(List<String> uiList, String filePath) {
		XSSFWorkbook workBook = null;

		try {
			File file = new File("C:\\Users\\ajaybabur\\Downloads\\products.xlsx");
			FileInputStream fis = new FileInputStream(file);

			workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheetAt(0);
			XSSFRow row = sheet.getRow(0);

			for (String uiCelldata : uiList) {
				Boolean isTest = false;
				for (Cell cell : row) {

					if (uiCelldata.equals(cell.getStringCellValue())) {
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
		} finally {
			try {

				workBook.close();
			} catch (Exception exc) {
				Assert.fail(exc.toString());
			}
		}
		return true;

	}

	public int getExcelRowCount(String filePath) {
		int rowNum = 0;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			XSSFWorkbook workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheetAt(0);
			rowNum = sheet.getLastRowNum() - 1;

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return rowNum;

	}

	public boolean isFileDownloaded()  {
	    final int sleepTimeMS = 100;
	    File file = new File("C:\\Users\\\\ajaybabur\\Downloads\\products.xlsx");
	    final int timeout = 60* sleepTimeMS;
	    int timeElapsed = 0;
	    while (timeElapsed<timeout){
	        if (file.exists()) {
	            System.out.println("products.xlsx is present");
	            return true;
	        } else {
	            timeElapsed +=sleepTimeMS;
	          foundation.threadWait(sleepTimeMS);
	        }
	    }
	    return false;
	}

}
