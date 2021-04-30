package at.framework.generic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.testng.Assert;

import at.smartshop.keys.Constants;

public class PropertyFile  {

	public String readConfig(String requiredData, String filePath) throws IOException  {
		Properties configfile = new Properties();
        String requiredString = Constants.EMPTY_STRING;
        FileInputStream fileInputStream=null;
        try {
        	fileInputStream = new FileInputStream(filePath);
            configfile.load(fileInputStream);
            requiredString = configfile.getProperty(requiredData);
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }finally {
        	fileInputStream.close();
        }
       
        return requiredString;
    }

}
