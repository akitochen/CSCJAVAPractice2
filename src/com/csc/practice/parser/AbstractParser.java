/**
 * 
 */
package com.csc.practice.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 189993
 * @param <T>
 *
 */
public abstract class AbstractParser<T>{	
	public abstract T parse(String fileName);
	
	public List<String> readInput(String filePath) throws IOException {
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
