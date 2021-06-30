package at.framework.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import at.smartshop.keys.Constants;

public class Excel {

	PropertyFile propertyFile = new PropertyFile();

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

	public boolean readExcel(List<String> uiList, String workSheetName) {
		XSSFWorkbook workBook = null;

		try {
			File file = new File("C:\\Users\\ajaybabur\\Desktop\\products (1).xlsx"); // creating a new file instance
			FileInputStream fis = new FileInputStream(file); // obtaining bytes from the file
			// creating Workbook instance that refers to .xlsx file
			workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheetAt(0); // creating a Sheet object to retrieve object
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

}
