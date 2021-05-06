package at.framework.reportsSetup;

import at.framework.generic.DateAndTime;
import at.smartshop.keys.Constants;
import at.smartshop.testData.TestDataFilesPaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtReport {

	public String rootFolder = TestDataFilesPaths.PATH + Constants.REPORTS;
	private String presentSubFolderName = Constants.EMPTY_STRING;
	private String presentRootFolderPath;
	private DateAndTime objDateNTime;

	ExtentSparkReporter objSparkReporter;
	ExtentReports objExtentReport;
	ExtentTest objExtentTest;

	private String reportMainTitleName = Constants.EMPTY_STRING;
	private String reportBrowserTitleName = Constants.EMPTY_STRING;

	public ExtReport(String reportMainTitleName, String reportBrowserTitleName) {
		objDateNTime = new DateAndTime();
		Path path = Paths.get(this.rootFolder + objDateNTime.getTimeStamp(Constants.REGEX_DDMMMYYYY));
		try {
			if (!Files.exists(path)) {
				Files.createDirectory(path);
			}
			this.presentRootFolderPath = path.toString();
			this.reportMainTitleName = reportMainTitleName;
			this.reportBrowserTitleName = reportBrowserTitleName;
		} catch (IOException exc) {
			exc.printStackTrace();
			Assert.fail(exc.toString());
		}

	}

	public ExtentReports getReporter() {
		try {
			System.out.println(createReportSubFolder() + Constants.REPORT_NAME);
			objSparkReporter = new ExtentSparkReporter(createReportSubFolder() + Constants.REPORT_NAME);
			objSparkReporter.config().setReportName(this.reportMainTitleName);
			objSparkReporter.config().setDocumentTitle(this.reportBrowserTitleName);
			objExtentReport = new ExtentReports();
			objExtentReport.attachReporter(objSparkReporter);

		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail(exc.toString());
		}

		return objExtentReport;
	}

	public String getPresentRootFolderPath() {
		return presentRootFolderPath;
	}

	public String getPresentSubFolderPath() {
		return presentSubFolderName;
	}

	private String createReportSubFolder() {
		presentSubFolderName = presentRootFolderPath + "\\" + objDateNTime.getTimeStamp(Constants.REGEX_HHMMSS);
		try {
			Files.createDirectories(Paths.get(presentSubFolderName));
		} catch (IOException exc) {			
			Assert.fail(exc.toString());
		}
		return presentSubFolderName;
	}
	
	public String getScreenshot(String methodName, WebDriver driver) {
		String destinationFile = null;
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			destinationFile = getPresentSubFolderPath() + "\\"+methodName+".png";
			FileUtils.copyFile(source, new File(destinationFile));
		} catch (Exception exc) {			
			Assert.fail(exc.toString());
		}
		return destinationFile;
	}
	
	

}
