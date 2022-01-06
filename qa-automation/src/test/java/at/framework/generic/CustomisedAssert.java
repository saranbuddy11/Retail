package at.framework.generic;

import java.util.Map;

import org.testng.Assert;

import com.aventstack.extentreports.Status;

import at.framework.reportsetup.ExtFactory;
import at.smartshop.tests.TestInfra;

public class CustomisedAssert {
	
	public static void assertNotEquals(Object actual, Object expected) {
		try {
			Assert.assertNotEquals(actual, expected);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "AssertNotEqual: ["+actual+"] and ["+expected+"]");
		}
		catch (AssertionError exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}

	public static void assertEquals(boolean actual, boolean expected) {
		try {
			Assert.assertEquals(actual, expected);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "AssertEqual: ["+actual+"] and ["+expected+"]");
		}
		catch (AssertionError exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public static void assertEquals(Map<?, ?> actual, Map<?, ?> expected) {
		try {
			Assert.assertEquals(actual, expected);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "AssertEqual: ["+actual+"] and ["+expected+"]");
		}
		catch (AssertionError exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public static void assertEquals(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "AssertEqual: ["+actual+"] and ["+expected+"]");
		}
		catch (AssertionError exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public static void assertEquals(int actual, int expected) {
		try {
			Assert.assertEquals(actual, expected);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "AssertEqual: ["+actual+"] and ["+expected+"]");
		}
		catch (AssertionError exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public static void assertEquals(double actual, double expected) {
		try {
			Assert.assertEquals(actual, expected);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "AssertEqual: ["+actual+"] and ["+expected+"]");
		}
		catch (AssertionError exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public static void assertTrue(boolean condition, String message) {
		try {
			Assert.assertTrue(condition,message);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "AssertTrue: ["+condition+"] and message-["+message+"]");
		}
		catch (AssertionError exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public static void assertTrue(boolean condition) {
		try {
			Assert.assertTrue(condition);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "AssertTrue: ["+condition+"]");
		}
		catch (AssertionError exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
	
	public static void assertFalse(boolean condition) {
		try {
			Assert.assertFalse(condition);
			ExtFactory.getInstance().getExtent().log(Status.INFO, "AssertFalse: ["+condition+"]");
		}
		catch (AssertionError exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
	}
}
