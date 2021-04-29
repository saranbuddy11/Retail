package at.framework.generic;

import java.io.FileInputStream;
import java.util.Properties;
import org.testng.Assert;

import at.smartshop.keys.Constants;

public class PropertyFile  {

	public String readConfig(String requiredData, String filePath) {
		Properties configfile = null;
        String requiredString = Constants.EMPTY_STRING;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            configfile = new Properties();
            configfile.load(fileInputStream);
            requiredString = configfile.getProperty(requiredData);
            fileInputStream.close();
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }
       
        return requiredString;
    }

}
