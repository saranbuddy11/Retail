package at.smartshop.utilities;

import java.text.NumberFormat;
import java.util.Locale;

import org.testng.Assert;

import at.smartshop.tests.TestInfra;


public class CurrenyConverter {
	public String convertTOCurrency(double amount) {
		String currency = null;
		try {
			NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
			currency = formatter.format(amount);
		} catch (Exception exc) {
			TestInfra.failWithScreenShot(exc.toString());
		}
		return currency;
	}

}
