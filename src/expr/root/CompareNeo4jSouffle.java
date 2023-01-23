package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CompareNeo4jSouffle {
	public static void main(String[] args) {
		
		if (args.length != 2) {
			System.out.println("Usage: "
					+ "Input: two .csv files that contains analysis result from Neo4j and souffle.\n"
					+ "Output: a .csv file that contains the difference between the results.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is result from souffle
			    reader = new BufferedReader(new FileReader(args[0]));
			    
				Map<String, List<String>> souffleMap = new LinkedHashMap<>();
			    
			    String line;
			    
			    while ((line = reader.readLine()) != null) {
					// get and split string
			    	String[] splitId = line.trim().split("\t");
			    	String startID = splitId[0];
			    	String endID = splitId[1];
			    		
			    	if (!souffleMap.containsKey(startID)) {
			    		List<String> endList = new ArrayList<>();
						endList.add(endID);
						souffleMap.put(startID, endList);
					} else if (!souffleMap.get(startID).contains(endID)) {
						souffleMap.get(startID).add(endID);
					}
				}
			    
			    // args[1] is result from neo4j
			    reader = new BufferedReader(new FileReader(args[1]));
			    
			    Map<String, List<String>> neo4jMap = new LinkedHashMap<>();
			    
			    // skip the first line - it's the title of Neo4j's result
				line = reader.readLine();
				
				while ((line = reader.readLine()) != null) {
					String[] splitId = line.trim().split("\t");
					String startID = splitId[0];
					String endID = splitId[splitId.length - 1];
					//System.out.println(line);
					
					if (!neo4jMap.containsKey(startID)) {
						List<String> endList = new ArrayList<>();
						endList.add(endID);
						neo4jMap.put(startID, endList);
					} else if (!neo4jMap.get(startID).contains(endID)) {
						neo4jMap.get(startID).add(endID);
					}
				}
				
				for (Map.Entry<String, List<String>> souffleentry : souffleMap.entrySet()) {
					String soufflekey = souffleentry.getKey();
					
					if (!neo4jMap.containsKey(soufflekey)) {
						System.out.println("key not in neo4j: " + soufflekey);
					} else {
						
						for (int i = 0; i < souffleentry.getValue().size(); i++) {
							if (!neo4jMap.get(soufflekey).contains(souffleentry.getValue().get(i))) {
								System.out.println("key-value pair not in neo4j: " 
										+ soufflekey + "\t" + souffleentry.getValue().get(i));
							}
				        }
					}
				}
			    
			    
			    
//			    String output = "";
//				
//				// capture input file name, and use it in output file
//				String[] splitInputFileName = args[0].toString().split("/");
//				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
//				
//				writer = new BufferedWriter(new FileWriter(filename + ".startEndID.csv"));
//				
//				System.out.println("writing " + filename + ".startEndID.csv");
//				for (Map.Entry<String, List<String>> entry : souffleMap.entrySet()) {
//					String key = entry.getKey();
//					for (int i = 0; i < entry.getValue().size(); i++) {
//						output = output + key + "\t" + entry.getValue().get(i) + "\n";
//			        }
//					
//				}
//				
//				// write to file
//				writer.write(output);
//				writer.close();
//				System.out.println("writing " + filename + ".startEndID.csv finished.");
			    
	    
//			    int total = 0;
//			    for (Map.Entry<String, List<String>> entry : pathMap.entrySet()) {
//					String key = entry.getKey();
//					total = total + pathMap.get(key).size();
//				}
//			    
//			    System.out.println(total);
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
