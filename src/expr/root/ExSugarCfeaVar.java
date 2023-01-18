package expr.root;

import java.io.*;
import java.util.*;

public class ExSugarCfeaVar {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a file that contains egrep #ifdef result.\n"
					+ "Output: a list of feature variables.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, Map<String, Long>> varMap = new LinkedHashMap<>();
			
			//Map<String, Long> varMap = new LinkedHashMap<String, Long>(); 
			try {
//				reader = new BufferedReader(new FileReader(args[0]));
//				writer = new BufferedWriter(new FileWriter("output.txt"));
//				String line;
//				
//				while ((line = reader.readLine()) != null) {
//					String[] splitLine = line.split(":");
//					String[] splitVarlist = splitLine[1].split(" ");
//					
//					if (!varMap.containsKey(splitLine[0])) {
//						varMap.put(splitLine[0], new LinkedHashMap<>());
//					}
//			
//					for (int i = 1; i < splitVarlist.length; i++) {
//						if (!varMap.get(splitLine[0]).containsKey(splitVarlist[i])) {
//							varMap.get(splitLine[0]).put(splitVarlist[i], null);
//						}
//					}
//				}
//				String output = "";
//				for (String file : varMap.keySet()) {
//					output = output + file + "\t";
//					for (String var : varMap.get(file).keySet()) {
//						output = output + var + ",";
//					}
//					output = output.substring(0, output.length() - 1) + "\n";
//				}
//				//System.out.println(output);
//				writer.write(output);
//				writer.close();
				
				// count number of feature variables
				reader = new BufferedReader(new FileReader(args[0]));
				String line;
				
				while ((line = reader.readLine()) != null) {
					// use trim to remove leading empty space and end space
					String[] splitLine = line.trim().split(":");
					String[] splitVarlist = splitLine[1].split(" ");
					
					for (int i = 1; i < splitVarlist.length; i++) {
						if (!varMap.containsKey(splitVarlist[i].trim()) && !(Character.compare(splitVarlist[i].charAt(0), '_') == 0)) {
								//&& !(Character.compare(splitVarlist[i].charAt(0), '_') == 0)) {
							varMap.put(splitVarlist[i].trim(), null);
						}
					}
				}
				for (String key : varMap.keySet()) {
					System.out.println(key);	
				}
				System.out.println(varMap.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
