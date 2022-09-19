package expr.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class CompareFORs {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: "
					+ "Input: two files of macros starts with \"For\", one from ToyboxInc.h, one extracted from .c files inside toys folder.\n"
					+ "Output: a final FOR macros that do not appear in the second file\n"
					+ "Warning: You do not include any .c files.");
		} else {
			BufferedReader reader;
			
			Map<String, Long> ForInCMap = new LinkedHashMap<String, Long>();
			
			try {
				
				reader = new BufferedReader(new FileReader(args[0]));
				String line;
			
				// load first file - FOR macros in C files
				while ((line = reader.readLine()) != null) {
					if (!ForInCMap.containsKey(line)) {
						ForInCMap.put(line, null);
					}
				}
				
				reader = new BufferedReader(new FileReader(args[1]));
				// load first file - feature variable list
				while ((line = reader.readLine()) != null) {
					if (!ForInCMap.containsKey(line)) {
						System.out.println(line);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}
	}
}
