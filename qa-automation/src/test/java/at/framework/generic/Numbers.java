package at.framework.generic;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import at.smartshop.keys.Constants;

public class Numbers {
	
	
	public int generateRandomNumber(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
	
	public int fetchNumberOfDecimalPoints(String input) {
		List<String> split = Arrays.asList(input.replace(".", "#").split(Constants.DELIMITER_HASH));
		return split.get(1).length();
	}
}
