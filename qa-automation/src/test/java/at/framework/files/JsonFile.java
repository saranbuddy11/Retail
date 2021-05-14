package at.framework.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.smartshop.keys.Constants;

public class JsonFile {

	public String readFileAsString(String filePath) {
		String value = Constants.EMPTY_STRING;
		try {
			value = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException exc) {
			exc.printStackTrace();
			Assert.fail(exc.toString());
		}
		return value;
	}

	public JsonObject convertStringToJson(String reqString) {
		JsonObject jsonObject = null;
		try {
			jsonObject = JsonParser.parseString(reqString).getAsJsonObject();
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.fail(exc.toString());
		}
		return jsonObject;
	}

}
