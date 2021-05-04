package at.smartshop.testData;

public class TestDataFilesPaths {
	private TestDataFilesPaths() {
		
	}
	public static final String PATH = System.getProperty("user.dir");
	public static final String PROPERTY_CONFIG_FILE = PATH + "\\src\\test\\resources\\Config.properties";
	public static final String DRIVER_CHROME= PATH + "\\src\\test\\resources\\chromedriver.exe";
}
