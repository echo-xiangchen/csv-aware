package expr.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class RmDuplicateVar {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .csv files that contains extracted feature variables.\n"
					+ "Output: distinct feature variables.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			
			Map<String, Long> varMap = new LinkedHashMap<String, Long>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					varMap.put(line, null);
				}
				
				for (String key : varMap.keySet()) {
					System.out.println(key);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
