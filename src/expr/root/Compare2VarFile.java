package expr.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

import expr.visitor.FeatureVarextractor;

public class Compare2VarFile {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: "
					+ "Input: two .csv files that contains extracted feature variables.\n"
					+ "Output: difference between them.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			
			Map<String, Long> varMap1 = new LinkedHashMap<String, Long>();
			Map<String, Long> varMap2 = new LinkedHashMap<String, Long>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					varMap1.put(line, null);
				}
				
				reader = new BufferedReader(new FileReader(args[1]));
				
				while ((line = reader.readLine()) != null) {
					if (!varMap1.containsKey(line)) {
						varMap2.put(line, null);
					}
				}
				
				for (String key : varMap2.keySet()) {
					System.out.println(key);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
