/**
 * 
 */
package com.csc.practice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 189993
 *
 */
public class ATMFileParser {

	public void parseATM(String filePath) {
		try {
			List<String> atmList = readInput(filePath);
			for (String string : atmList) {
				System.out.println(string);
			}
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}

	public void parseAccount(String filePath) {
		try {
			List<String> accountList = readInput(filePath);
			for (String string : accountList) {
				System.out.println(string);
			}
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}

	public void parseOperation(String filePath) {
		try {
			List<String> operationList = readInput(filePath);
			for (String string : operationList) {
				System.out.println(string);
			}
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private List<String> readInput(String filePath) throws IOException {
		List<String> result = new ArrayList<>();
		BufferedReader fileReader = null;

		try {
			fileReader = new BufferedReader(new FileReader(filePath));

			String line;
			while ((line = fileReader.readLine()) != null) {
				result.add(line);
			}
		} finally {
			if (fileReader != null) {
				fileReader.close();
			}
		}
		return result;
	}
}
