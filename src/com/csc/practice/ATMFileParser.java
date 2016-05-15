/**
 * 
 */
package com.csc.practice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csc.practice.ATM.ATMFactory;
import com.csc.practice.ATM.ATMType;
import com.csc.practice.ATM.BaseATM;
import com.csc.practice.Bank.Bank;

/**
 * @author 189993
 *
 */
public class ATMFileParser {
	private Bank bank;
	
	public ATMFileParser(){
		bank = new Bank();
	}
	
	public void parseATM(String filePath) {
		try {
			String pattern = "^(\\d+)\\[([A-Za-z]+)\\,(\\d+)\\]$";
			Pattern r = Pattern.compile(pattern);
			List<String> atmList = readInput(filePath);
			for (String line : atmList) {
				System.out.println(line);
				Matcher m = r.matcher(line);
				if (m.find()) {
					int atmNumber = Integer.parseInt(m.group(1));
					String atmTypeString =  m.group(2);
					int initialMoney = Integer.parseInt(m.group(3));
					
					ATMType atmType = ATMType.parseATMType(atmTypeString);
					BaseATM baseATM = ATMFactory.createATM(atmType, bank, initialMoney);
				} else {
					// throw format exception
					System.out.println("NO MATCH");
				}
			}
		} catch (IOException exception) {
			System.out.println("parseATM IOException : " + exception.getMessage());
		} catch (Exception exception) {
			System.out.println("parseATM Exception : " + exception.getMessage());
		}
	}

	public void parseAccount(String filePath) {
		try {
			String pattern = "^([A-Z]\\d+)\\,(\\d+)\\,(\\d+)\\,([A-Za-z]+)$";
			Pattern r = Pattern.compile(pattern);
			List<String> accountList = readInput(filePath);
			for (String line : accountList) {
				System.out.println(line);
				Matcher m = r.matcher(line);
				if (m.find()) {
					System.out.println("Found value: " + m.group(1));
					System.out.println("Found value: " + m.group(2));
					System.out.println("Found value: " + m.group(3));
					System.out.println("Found value: " + m.group(4));
				} else {
					// throw format exception
					System.out.println("NO MATCH");
				}
			}
		} catch (IOException exception) {
			System.out.println("parseAccount IOException : " + exception.getMessage());
		} catch (Exception exception) {
			System.out.println("parseAccount Exception : " + exception.getMessage());
		}
	}

	public void parseOperation(String filePath) {
		try {
			String pattern = "^([PWD])\\###(\\d+)\\###(\\d+)\\###([A-Za-z]\\d+)*\\###(\\d+)*";
			Pattern r = Pattern.compile(pattern);
			List<String> operationList = readInput(filePath);
			for (String line : operationList) {
				System.out.println(line);
				Matcher m = r.matcher(line);
				if (m.find()) {
					System.out.println("Found value: " + m.group(1));
					System.out.println("Found value: " + m.group(2));
					System.out.println("Found value: " + m.group(3));
					System.out.println("Found value: " + m.group(4));
					System.out.println("Found value: " + m.group(5));
				} else {
					// throw format exception
					System.out.println("NO MATCH");
				}
			}
		} catch (IOException exception) {
			System.out.println("parseOperation IOException : " + exception.getMessage());
		} catch (Exception exception) {
			System.out.println("parseOperation Exception : " + exception.getMessage());
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
