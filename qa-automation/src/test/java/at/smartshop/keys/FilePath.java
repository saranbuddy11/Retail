package at.smartshop.keys;

public class FilePath {
	private FilePath() {
		
	}
	public static final String PATH = System.getProperty("user.dir");
	public static final String PROPERTY_CONFIG_FILE = PATH + "\\src\\test\\resources\\Config.properties";
	public static final String DRIVER_CHROME= PATH + "\\src\\test\\resources\\chromedriver.exe";
	public static final String JSON_SALES_CREATION = PATH + "\\src\\test\\resources\\SalesCreation.json";
	public static final String JSON_BAD_SCAN = PATH + "\\src\\test\\resources\\BadScan.json";
}
