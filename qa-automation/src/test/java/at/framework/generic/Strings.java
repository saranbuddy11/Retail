package at.framework.generic;

import at.smartshop.keys.Constants;

public class Strings {

	public String getRandomCharacter() {
		String randomCharacter = Constants.REGEX_CHAR;
		StringBuilder stringBuilder = new StringBuilder();
		while (stringBuilder.length() < 10) {
			int index = (int) (randomCharacter.length() * Math.random());
			stringBuilder.append(randomCharacter.charAt(index));
		}
		String randomData = stringBuilder.toString();
		return randomData.substring(0, 1).toUpperCase() + randomData.substring(1);
	}

	public boolean verifyNoSpecialCharacter(String input) {
		boolean status = false;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if ((Character.isAlphabetic(c) || Character.isDigit(c))) {
				status = true;
			} else {
				status = false;
				break;
			}
		}
		return status;
	}

	public boolean verifyOnlyNumberAndDot(String input) {
		boolean status = false;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isDigit(c) || c == '.') {
				status = true;
			} else {
				status = false;
				break;
			}
		}
		return status;

	}

}
