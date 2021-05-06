package at.framework.generic;

import java.util.Random;

public class RandomNumber {
	public int generateRandomNumber(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
}
