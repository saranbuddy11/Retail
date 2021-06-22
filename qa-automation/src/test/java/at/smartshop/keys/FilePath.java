package at.smartshop.keys;

public class FilePath {
	public FilePath() {
		
	}
	public static final String PATH = System.getProperty("user.dir");
	public static String PROPERTY_CONFIG_FILE;
	public static final String DRIVER_CHROME= PATH + "\\src\\test\\resources\\chromedriver.exe";
	public static final String GMA_ACCOUNT_TEMPLATE = PATH+"\\src\\test\\resources\\SOS_GMA_Account_template.xls";
	public static final String JSON_GMA_ADD_VALUE = PATH + "\\src\\test\\resources\\GMAAddValue.json";
	public static final String JSON_MKA = PATH + "\\src\\test\\resources\\MKAAccount.json";
	public static final String JSON_KIOSK_CASH_OUT = PATH + "\\src\\test\\resources\\KioskCashOut.json";
	public static final String JSON_SALES_CREATION = PATH + "\\src\\test\\resources\\SalesCreation.json";
	public static final String JSON_BAD_SCAN = PATH + "\\src\\test\\resources\\BadScan.json";
	public static final String IMAGE_PATH = PATH+ "\\src\\test\\resources\\icecream.jpg";
	
	public void setEnvironment(String environment) {
		if(environment.equals("test4")) {
			PROPERTY_CONFIG_FILE = PATH + "\\src\\test\\resources\\ConfigTest4.properties";
		}
		else if(environment.equals("test3")) {
			PROPERTY_CONFIG_FILE = PATH + "\\src\\test\\resources\\ConfigTest3.properties";
		}
		else {
			PROPERTY_CONFIG_FILE = PATH + "\\src\\test\\resources\\ConfigPreProd.properties";
		}
	}
}
