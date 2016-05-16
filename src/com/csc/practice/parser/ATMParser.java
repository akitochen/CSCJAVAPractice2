/**
 * 
 */
package com.csc.practice.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ATMParser extends AbstractParser {
	private Bank bank;

	public ATMParser(Bank bank) {
		this.bank = bank;
	}

	public Map<Integer, BaseATM> parse(String filePath) {
		Map<Integer, BaseATM> atmList = new HashMap<Integer, BaseATM>();
		try {
			String pattern = "^(\\d+)\\[([A-Za-z]+)\\,(\\d+)\\]$";
			Pattern r = Pattern.compile(pattern);
			List<String> atmStringList = super.readInput(filePath);
			for (String line : atmStringList) {
				//System.out.println(line);
				Matcher m = r.matcher(line);
				if (m.find()) {
					int atmNumber = Integer.parseInt(m.group(1));
					ATMType atmType = ATMType.parseATMType(m.group(2));
					int initialMoney = Integer.parseInt(m.group(3));

					BaseATM baseATM = bank.addATM(atmType, initialMoney);
					baseATM.setAtmNumber(atmNumber);
					atmList.put(atmNumber, baseATM);
				} else {
					// should throw format exception
					System.out.println("NO MATCH");
				}
			}
		} catch (IOException exception) {
			System.out.println("parseATM IOException : " + exception.toString());
		} catch (Exception exception) {
			System.out.println("parseATM Exception : " + exception.toString());
		}

		return atmList;
	}
}
