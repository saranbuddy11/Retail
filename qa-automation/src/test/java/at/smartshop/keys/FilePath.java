package at.smartshop.keys;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import at.smartshop.pages.ReportList;

public class FilePath {
	public FilePath() {

	}

	private static ReportList reportList = new ReportList();

	public static final String FILE = "file:\\\\";
	public static final String PATH = System.getProperty("user.dir");
	public static final String HOME_PATH = System.getProperty("user.home");
	public static String PROPERTY_CONFIG_FILE;
	public static final String DRIVER_CHROME = PATH + "\\src\\test\\resources\\chromedriver.exe";
	public static final String GMA_ACCOUNT_TEMPLATE = PATH + "\\src\\test\\resources\\SOS_GMA_Account_template.xls";
	public static final String EGIFT_CARD_TEMPLATE = PATH + "\\src\\test\\resources\\gift-cards.xls";
	public static final String JSON_GMA_ADD_VALUE = PATH + "\\src\\test\\resources\\GMAAddValue.json";
	public static final String JSON_MKA = PATH + "\\src\\test\\resources\\MKAAccount.json";
	public static final String JSON_KIOSK_CASH_OUT = PATH + "\\src\\test\\resources\\KioskCashOut.json";
	public static final String JSON_SALES_CREATION = PATH + "\\src\\test\\resources\\SalesCreation.json";
	public static final String JSON_BAD_SCAN = PATH + "\\src\\test\\resources\\BadScan.json";
	public static final String IMAGE_PATH = PATH + "\\src\\test\\resources\\lunch.jpg";
	public static final String EXCEL_PROD_SRC = HOME_PATH + "\\Downloads\\products.xlsx";
	public static final String EXCEL_PROD_TAR = PATH + "\\src\\test\\resources\\products.xlsx";
	public static final String EXCEL_LOCAL_PROD = HOME_PATH + "\\Downloads\\location-product-list.xlsx";
	public static final String EXCEL_PROD = PATH + "\\src\\test\\resources\\location-product-list.xlsx";
	public static final String IMAGE_PNG_PATH = PATH + "\\src\\test\\resources\\fruits.png";
	public static final String IMAGE_SIZE_MORE = PATH + "\\src\\test\\resources\\Earth.jpg";
	public static final String IMAGE_TEXT_PATH = PATH + "\\src\\test\\resources\\Capture.txt";
	public static final String IMAGE_PIXEL_SIZE = PATH + "\\src\\test\\resources\\pixelsize1.jpg";
	public static final String EXCEL_DEVICE_EXPORT_SRC = HOME_PATH + "\\Downloads\\device.xlsx";
	public static final String EXCEL_DEVICE_EXPORT_TAR = PATH + "\\src\\test\\resources\\device.xlsx";
	public static final String EXCEL_DISBURSEMENT_EXPORT_SRC = HOME_PATH
			+ "\\Downloads\\Disbursement Report 02/05/2021.xls";
	public static final String EXCEL_VARIANCE_EXPORT_SRC = HOME_PATH
			+ "\\Downloads\\EFT Variance Report 02/05/2021.xls";
	public static final String EXCEL_CSV_REPORT_EXPORT_SRC = HOME_PATH + "\\Downloads\\Upload_ 02/05/2021.csv";
	public static final String EXCEL_CONTACT_SRC = HOME_PATH + "\\Downloads\\contact.xls";
	public static final String EXCEL_CONSUMER = HOME_PATH + "\\Downloads\\consumer_list.xls";
	public static final String PATH_TO_DOWNLOAD = HOME_PATH + "\\Downloads";
	public static final String GIFT_CARDS = HOME_PATH + "\\Downloads\\gift-cards.xls";

	public static String reportFilePath(String filename) {
		String EXCEL_REPORT = HOME_PATH + "\\Downloads\\" + filename + ".xlsx";
		return EXCEL_REPORT;
	}

	public static String reportFilePathWithDate(String filename, String formate) {
		String date = " " + reportList.getTodaysDate(formate);
		String EXCEL_REPORT = HOME_PATH + "\\Downloads\\" + filename + date + ".xlsx";
		return EXCEL_REPORT;
	}

	public static String reportFilePathWithDateWithoutSpace(String filename, String formate) {
		String date = reportList.getTodaysDate(formate);
		String EXCEL_REPORT = HOME_PATH + "\\Downloads\\" + filename + date + ".xlsx";
		return EXCEL_REPORT;
	}

	public static String reportFilePathWithOrgAndGMA(String orgname, String formate) {
		String date = "_" + reportList.getTodaysDate(formate);
		String EXCEL_REPORT = HOME_PATH + "\\Downloads\\" + orgname + date + "-GMA.xlsx";
		return EXCEL_REPORT;
	}

	public static String pickListFilePathWithDateAndDay(String filename, String formate) {
		Calendar calendar = Calendar.getInstance();
		Date day = calendar.getTime();
		String date = reportList.getTodaysDate(formate);
		String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(day.getTime());
		System.out.println(HOME_PATH + "\\Downloads\\" + filename + currentDay + " " + date + ".xls");
		String EXCEL_REPORT = HOME_PATH + "\\Downloads\\" + filename + currentDay + " " + date + ".xls";
		return EXCEL_REPORT;
	}

	public String getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
		return currentDay;
	}

	public void setEnvironment(String environment) {
		if (environment.equals(Constants.PRE_PROD)) {
			PROPERTY_CONFIG_FILE = PATH + "\\src\\test\\resources\\ConfigPreProd.properties";
		} else if (environment.equals(Constants.TEST3)) {
			PROPERTY_CONFIG_FILE = PATH + "\\src\\test\\resources\\ConfigTest3.properties";
		} else {
			PROPERTY_CONFIG_FILE = PATH + "\\src\\test\\resources\\ConfigTest4.properties";
		}
	}

}
