/**
 * 
 */
package com.csc.practice;

/**
 * @author 189993
 *
 */
public class ATMFileParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ATMFileParser atmFileParser = new ATMFileParser();
		atmFileParser.parseATM("ATM.txt");
		atmFileParser.parseATM("Account.txt");
		atmFileParser.parseATM("Operaction.txt");
	}

}
