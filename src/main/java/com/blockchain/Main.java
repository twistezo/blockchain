package com.blockchain;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("*** My Blockchain ***");

		String test = StringUtil.applySha256("test");
		System.out.println("Test string to hash: " + test);

		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
	}
}
