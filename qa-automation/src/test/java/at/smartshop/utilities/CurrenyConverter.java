package at.smartshop.utilities;

import java.text.NumberFormat;
import java.util.Locale;
import org.testng.Assert;


public class CurrenyConverter {
	public String convertTOCurrency(double amount) {
		String currency = null;
		try {
			NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
			currency = formatter.format(amount);
		} catch (Exception exc) {
			Assert.fail(exc.toString());
		}
		return currency;
	}

}
