/**
 * 
 */
package com.csc.practice.parser;

/**
 * @author 189993
 *
 */
public class OperationObject {
	private String operator;
	private int atmNumber;
	private int money;
	private String cardId;
	private String password;
	
	public OperationObject(String operator, int atmNumber, int money, String cardId, String password){
		this.operator = operator;
		this.atmNumber = atmNumber;
		this.money = money;
		this.cardId = cardId;
		this.password = password;
	}
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getAtmNumber() {
		return atmNumber;
	}
	public void setAtmNumber(int atmNumber) {
		this.atmNumber = atmNumber;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "OperationObject [operator=" + operator + ", atmNumber=" + atmNumber + ", money=" + money + ", cardId="
				+ cardId + ", password=" + password + "]";
	}
}
