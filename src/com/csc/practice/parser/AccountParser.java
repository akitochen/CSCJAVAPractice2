/**
 * 
 */
package com.csc.practice.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csc.practice.Bank.Account;
import com.csc.practice.Bank.Bank;

/**
 * @author 189993
 *
 */
public class AccountParser extends AbstractParser {
	private Bank bank;

	public AccountParser(Bank bank) {
		this.bank = bank;
	}
	
	@Override
	public Map<String, Account> parse(String filePath) {
		Map<String, Account> accountList = new HashMap<String, Account>();

		try {
			String pattern = "^([A-Z]\\d+)\\,(\\d+)\\,(\\d+)\\,([A-Za-z]+)$";
			Pattern r = Pattern.compile(pattern);
			List<String> accountStringList = readInput(filePath);
			for (String line : accountStringList) {
				//System.out.println(line);
				Matcher m = r.matcher(line);
				if (m.find()) {
					String cardId = m.group(1);
					String password = m.group(2);
					int deposit = Integer.parseInt(m.group(3));
					String name = m.group(4);
					
					Account account = bank.addAccount(cardId, name, password, deposit);
					accountList.put(cardId, account);
				} else {
					// throw format exception
					System.out.println("NO MATCH");
				}
			}
		} catch (IOException exception) {
			System.out.println("parseAccount IOException : " + exception.toString());
		} catch (Exception exception) {
			System.out.println("parseAccount Exception : " + exception.toString());
		}

		return accountList;
	}

}
