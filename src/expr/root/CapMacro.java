package expr.root;

import java.io.*;
import java.util.*;

public class CapMacro {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: "
					+ "Input: a file that contains egrep #ifdef result.\n"
					+ "Output: a list of feature variables.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, Long> varMap = new LinkedHashMap<String, Long>();
			Map<String, Long> undefMap = new LinkedHashMap<String, Long>();
		
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				writer = new BufferedWriter(new FileWriter("tmpMacro.h"));
				
				String line;
				
				// load first file - feature variable list
				while ((line = reader.readLine()) != null) {
					if (!varMap.containsKey(line)) {
				    	varMap.put(line, null);
				    }
				}
				
				reader = new BufferedReader(new FileReader(args[1]));
				
				// load first file - feature variable list
				while ((line = reader.readLine()) != null) {
					String[] splitLine = line.split(" ");
					if (!varMap.containsKey(splitLine[0])) {
						undefMap.put(splitLine[0], null);
					}
				}
				
				String output = "";
				for (String macro : undefMap.keySet()) {
					output = output + "#undef " +macro + "\n";
				}
				//System.out.println(output);
				writer.write(output);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
	}
}
