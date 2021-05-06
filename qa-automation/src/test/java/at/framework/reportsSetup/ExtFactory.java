package at.framework.reportsSetup;

import com.aventstack.extentreports.ExtentTest;

public class ExtFactory {
	private ExtFactory() {

	}

	private static ExtFactory instance = new ExtFactory();

	public static ExtFactory getInstance() {
		return instance;
	}

	ThreadLocal<ExtentTest> extent = new ThreadLocal<ExtentTest>();

	public ExtentTest getExtent() {
		return extent.get();
	}

	public void setExtent(ExtentTest extentTestObject) {
		extent.set(extentTestObject);
	}

	public void removeExtentObject() {
		extent.remove();
	}
}
