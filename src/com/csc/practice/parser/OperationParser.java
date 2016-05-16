/**
 * 
 */
package com.csc.practice.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 189993
 *
 */
public class OperationParser extends AbstractParser {

	@Override
	public List<OperationObject> parse(String filePath) {
		List<OperationObject> operationObjectList = new ArrayList<OperationObject>();

		try {
			String pattern = "^([PWD])\\###(\\d+)\\###(\\d+)\\###([A-Za-z]\\d+)*\\###(\\d+)*";
			Pattern r = Pattern.compile(pattern);
			List<String> operationList = super.readInput(filePath);
			for (String line : operationList) {
				//System.out.println(line);
				Matcher m = r.matcher(line);
				if (m.find()) {
					String operator = m.group(1);
					int  atmNumber = Integer.parseInt(m.group(2));
					int money = Integer.parseInt(m.group(3));
					String cardId = m.group(4);
					String password = m.group(5);
					
					OperationObject operationObject = new OperationObject(operator, atmNumber, money, cardId, password);
					operationObjectList.add(operationObject);
				} else {
					System.out.println("No Match Operation");
				}
			}
		} catch (IOException exception) {
			System.out.println("parseOperation IOException : " + exception.toString());
		} catch (Exception exception) {
			System.out.println("parseOperation Exception : " + exception.toString());
		}

		return operationObjectList;
	}

}
