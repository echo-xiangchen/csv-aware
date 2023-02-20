package processFactbase;

import java.io.*;
import java.util.*;

public class CapStarEndNodeFromNeo4jResult {
	public static void main(String[] args) {
		if (args.length < 2 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains analysis result from Neo4j.\n"
					+ "Output: a .csv file that only contains start node and end node of paths.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, HashSet<String>> neo4jMap = new LinkedHashMap<>();
			Map<String, HashSet<String>> souffleMap = new LinkedHashMap<>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				// skip the first line - it's the title of Neo4j's result
				String line = reader.readLine();
				
				int linenum = 2;
				
				// first capture the start and end node of neo4j result
				while ((line = reader.readLine()) != null) {
					// some lines might be empty
					if (line.equals("\"\"")) {
						// dont use break here! it will quit the while loop!
						// use continue.
						//break;
						continue;
					}
					String[] splitId = line.trim().split("id\"\":\"\"");
					String startID = splitId[1].split("\"\"")[0];
					String endID = splitId[splitId.length - 1].split("\"\"")[0];
					//System.out.println(line);
					
					if (!neo4jMap.containsKey(startID)) {
						HashSet<String> endIDset = new HashSet<>();
						endIDset.add(endID);
						neo4jMap.put(startID, endIDset);
					} else if (!neo4jMap.get(startID).contains(endID)) {
						neo4jMap.get(startID).add(endID);
					}
					System.out.println("finished capturing line " + linenum + " of neo4j result.");
					linenum++;
				}
				
				// then capture the start and end node of souffle result
				reader = new BufferedReader(new FileReader(args[1]));
				
				String linesouffle;
				
				linenum = 1;
				
				while ((linesouffle = reader.readLine()) != null) {
					String[] splitLine = linesouffle.split("\t");
					String startID = splitLine[0];
					String endID = splitLine[1];
					
					// add start id and end id into souffleMap
					if (!souffleMap.containsKey(startID)) {
						HashSet<String> endIDset = new HashSet<>();
						endIDset.add(endID);
						souffleMap.put(startID, endIDset);
					} else {
						souffleMap.get(startID).add(endID);
					}
					System.out.println("finished capturing line " + linenum + " of souffle result.");
					linenum++;
				}
				
				if (neo4jMap.keySet().equals(souffleMap.keySet())) {
					System.out.println("keys are equal");
				} else {
					System.out.println("keys are not equal");
				}
				
				String keyValue = "";
				for (Map.Entry<String, HashSet<String>> entry : neo4jMap.entrySet()) {
					String key = entry.getKey();
					if (!neo4jMap.get(key).equals(souffleMap.get(key))) {
						keyValue = keyValue + "key-value pair for " + key + "is not equal.";
					}
				}
				
				if (keyValue.isBlank()) {
					System.out.println("key-value pairs of neo4j and souffle are identical.");
				}
				
//				String output = "";
				
//				// capture input file name, and use it in output file
//				String[] splitInputFileName = args[0].toString().split("/");
//				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
//				
//				writer = new BufferedWriter(new FileWriter(filename + ".startEndID.csv"));
//				
//				System.out.println("writing " + filename + ".startEndID.csv");
//				for (Map.Entry<String, List<String>> entry : neo4jMap.entrySet()) {
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
