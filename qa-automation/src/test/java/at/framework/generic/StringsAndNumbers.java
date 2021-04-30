package at.framework.generic;

import java.util.Random;

import at.smartshop.keys.Constants;

public class StringsAndNumbers {
	public int generateRandomIntRange(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
	
	public String getSaltString() {
		String saltChars = Constants.REGEX_CHAR;
		StringBuilder salt = new StringBuilder();
		Random random = new Random();
		while (salt.length() < 10) { 
			int index = (int) (random.nextInt() * saltChars.length());
			salt.append(saltChars.charAt(index));
		}
		String saltData = salt.toString();
		String output = saltData.substring(0, 1).toUpperCase() + saltData.substring(1);
		return output;
	}
}