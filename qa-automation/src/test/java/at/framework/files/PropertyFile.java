package at.framework.files;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;

import at.smartshop.keys.Constants;

public class PropertyFile  {

	public String readPropertyFile(String requiredData, String filePath)  {
		Properties configFile = new Properties();
        String requiredString = Constants.EMPTY_STRING;
        FileInputStream fileInputStream=null;
        try {
        	fileInputStream = new FileInputStream(filePath);
            configFile.load(fileInputStream);
            requiredString = configFile.getProperty(requiredData);
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }finally {
        	try {
				fileInputStream.close();
			} catch (IOException exc) {
				 Assert.fail(exc.toString());
			}
        }
       
        return requiredString;
    }

}
