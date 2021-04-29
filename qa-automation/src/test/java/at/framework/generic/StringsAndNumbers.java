package at.framework.generic;

import java.util.Random;

import at.smartshop.keys.Constants;

public class StringsAndNumbers {
	public static int generateRandomIntRange(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
	
	public static String getSaltString() {
		String SALTCHARS = Constants.REGEX_CHAR;
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { 
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		String output = saltStr.substring(0, 1).toUpperCase() + saltStr.substring(1);
		return output;
	}
}