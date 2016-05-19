/**
 * 
 */
package com.csc.practice;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.csc.practice.ATM.ATMType;
import com.csc.practice.ATM.BaseATM;
import com.csc.practice.ATM.CityATM;
import com.csc.practice.ATM.SuperATM;
import com.csc.practice.Bank.Account;
import com.csc.practice.Bank.Bank;
import com.csc.practice.parser.ATMParser;
import com.csc.practice.parser.AccountParser;
import com.csc.practice.parser.OperationObject;
import com.csc.practice.parser.OperationParser;

import Exception.InsufficientBalanceException;
import Exception.InsufficientMoneyException;
import Exception.NotLoginException;
import Exception.OutOfPassbookUpdateTimesException;
import Exception.OvercapacityException;

/**
 * @author 189993
 *
 */
public class ATMFileParser {
	private Bank bank;
	private Map<Integer, BaseATM> atmList = null;
	private Map<String, Account> accountList = null;
	private List<OperationObject> operationList = null;

	public ATMFileParser() {
		bank = new Bank(1);
	}

	public void parseATM(String filePath) {
		ATMParser atmParser = new ATMParser(bank);
		atmList = atmParser.parse(filePath);
		//for (BaseATM atm : atmList.values()) {
		//	System.out.println(atm.toString());
		//}
	}

	public void parseAccount(String filePath) {
		AccountParser accountParser = new AccountParser(bank);
		accountList = accountParser.parse(filePath);
		//for (Account account : accountList.values()) {
		//	System.out.println(account.toString());
		//}
	}

	public void parseOperation(String filePath) {
		OperationParser operationParser = new OperationParser();
		operationList = operationParser.parse(filePath);
		//for (OperationObject operation : operationList) {
		//	System.out.println(operation.toString());
		//}
	}

	public void ExecuteOperation() {
		for (OperationObject operationObject : operationList) {
			switch (operationObject.getOperator()) {
			case "P":
				executePut(operationObject);
				break;
			case "W":
				executeWithdraw(operationObject);
				break;
			case "D":
				executeDeposit(operationObject);
				break;
			default:
				System.out.println("Illegal Operation !");
				break;
			}
		}

		recordAtmMoney();
		recordAccountMoney();
	}

	private void executePut(OperationObject operationObject) {
		BaseATM baseATM = atmList.get(operationObject.getAtmNumber());
		if (baseATM != null) {
			try {
				baseATM.putMoney(operationObject.getMoney());
			} catch (OvercapacityException exception) {
				System.out.println("executePut OvercapacityException : " + exception.getMessage());
				recordErrorMessage(operationObject, exception.getMessage());
			} catch (Exception exception) {
				System.out.println(exception.toString());
				recordErrorMessage(operationObject, exception.toString());
			}
		}
	}

	private void executeWithdraw(OperationObject operationObject) {
		BaseATM baseATM = atmList.get(operationObject.getAtmNumber());
		if (baseATM != null) {
			try {
				baseATM.login(operationObject.getCardId(), operationObject.getPassword());
				baseATM.withDraw(operationObject.getMoney());
				baseATM.logout();
			} catch (NotLoginException exception) {
				System.out.println("executeWithdraw NotLoginException : " + exception.getMessage());
				recordErrorMessage(operationObject, exception.getMessage());
			} catch (InsufficientBalanceException exception) {
				System.out.println("executeWithdraw InsufficientBalanceException : " + exception.getMessage());
				recordErrorMessage(operationObject, exception.getMessage());
			} catch (InsufficientMoneyException exception) {
				System.out.println("executeWithdraw InsufficientMoneyException : " + exception.getMessage());
				recordErrorMessage(operationObject, exception.getMessage());
			} catch (OutOfPassbookUpdateTimesException exception) {
				System.out.println("executeWithdraw OutOfPassbookUpdateTimesException : " + exception.getMessage());
				recordErrorMessage(operationObject, exception.getMessage());
			} catch (Exception exception) {
				System.out.println(exception.toString());
				recordErrorMessage(operationObject, exception.toString());
			}
		}
	}

	private void executeDeposit(OperationObject operationObject) {
		BaseATM baseATM = atmList.get(operationObject.getAtmNumber());
		if (baseATM != null) {
			try {
				if (baseATM.geAtmType() == ATMType.CityATM) {
					baseATM.login(operationObject.getCardId(), operationObject.getPassword());
					((CityATM) baseATM).deposit(operationObject.getMoney());
					baseATM.logout();
				} else if (baseATM.geAtmType() == ATMType.SuperATM) {
					baseATM.login(operationObject.getCardId(), operationObject.getPassword());
					((SuperATM) baseATM).deposit(operationObject.getMoney());
					baseATM.logout();
				} else {
					String message = String.format("%d號ATM %s 沒有存款功能", baseATM.getAtmNumber(), baseATM.geAtmType().toString());
					System.out.println(message);
					recordErrorMessage(operationObject, message);
				}
			} catch (NotLoginException exception) {
				System.out.println("executeDeposit NotLoginException : " + exception.getMessage());
				recordErrorMessage(operationObject, exception.getMessage());
			} catch (OvercapacityException exception) {
				System.out.println("executeDeposit OvercapacityException : " + exception.getMessage());
				recordErrorMessage(operationObject, exception.getMessage());
			} catch (Exception exception) {
				System.out.println(exception.toString());
				recordErrorMessage(operationObject, exception.toString());
			}
		}
	}

	private void recordAtmMoney() {
		for (BaseATM baseATM : atmList.values()) {
			String moneyLog = String.format("%d[%s,%d]", baseATM.getAtmNumber(), baseATM.geAtmType().toString(),
					baseATM.getRemainMoney());
			writeToFile("ATMRemainMoney.txt", moneyLog);
		}
	}

	private void recordAccountMoney() {
		for (Account account : accountList.values()) {
			String accountLog = String.format("%s,%s,%d,%s", account.getCardId(), account.getPassword(),
					account.getDeposit(), account.getName());
			writeToFile("AccountRemainMoney.txt", accountLog);
		}
	}

	private void recordErrorMessage(OperationObject operationObject, String message) {
		int atmNumber = operationObject.getAtmNumber();
		String cardId = operationObject.getCardId();
		String errorMessage = String.format("[%d][%s][%s]", atmNumber, cardId, message);
		writeToFile("ErrorMessage.txt", errorMessage);
	}

	private void writeToFile(String fileName, String content) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName, true);
			fileWriter.write(content + "\r\n");
		} catch (IOException exception) {
			System.out.println("writeToFile IOException : " + exception.toString());
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (Exception exception) {
					System.out.println("writeToFile close : " + exception.toString());
				}

			}
		}
	}
}
