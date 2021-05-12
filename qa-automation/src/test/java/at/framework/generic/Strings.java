package at.framework.generic;

import java.util.Random;

import at.smartshop.keys.Constants;

public class Strings {
	
	public String getRandomCharacter() {
		String randomCharacter = Constants.REGEX_CHAR;
		StringBuilder stringBuilder = new StringBuilder();
		Random objRandom = new Random();
		while (stringBuilder.length() < 10) { 
			int index = (int) (objRandom.nextInt() * randomCharacter.length());
			stringBuilder.append(randomCharacter.charAt(index));
		}
		String randomData = stringBuilder.toString();
		return  randomData.substring(0, 1).toUpperCase() + randomData.substring(1);
	}
} 


