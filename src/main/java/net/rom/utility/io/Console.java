package net.rom.utility.io;

import java.util.Scanner;

public class Console {
	private static Scanner scanner;

	public static Scanner scanner() {
		if (scanner == null) scanner = new Scanner(System.in);
		return scanner;
	}

	@SuppressWarnings("unused")
	private static String input() {
		return scanner().next();
	}
}

