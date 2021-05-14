package at.smartshop.keys;

public class FilePath {
	private FilePath() {
		
	}
	public static final String PATH = System.getProperty("user.dir");
	public static final String PROPERTY_CONFIG_FILE = PATH + "\\src\\test\\resources\\Config.properties";
	public static final String DRIVER_CHROME= PATH + "\\src\\test\\resources\\chromedriver.exe";
	public static final String GMA_ACCOUNT_TEMPLATE = PATH+"\\src\\test\\resources\\SOS_GMA_Account_template.xls";
}
