package expr.root;

import java.io.*;
import java.util.*;

public class CapStarEndNodeFromNeo4jResult {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains analysis result from Neo4j.\n"
					+ "Output: a .csv file that only contains start node and end node of paths.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, List<String>> pathMap = new LinkedHashMap<>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				// skip the first line - it's the title of Neo4j's result
				String line = reader.readLine();
				
				while ((line = reader.readLine()) != null) {
					String[] splitId = line.trim().split("id\"\":\"\"");
					String startID = splitId[1].split("\"\"")[0];
					String endID = splitId[splitId.length - 1].split("\"\"")[0];
					//System.out.println(line);
					
					if (!pathMap.containsKey(startID)) {
						List<String> endList = new ArrayList<>();
						endList.add(endID);
						pathMap.put(startID, endList);
					} else if (!pathMap.get(startID).contains(endID)) {
						pathMap.get(startID).add(endID);
					}
				}
				
				String output = "";
				
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				
				writer = new BufferedWriter(new FileWriter(filename + ".startEndID.csv"));
				
				System.out.println("writing " + filename + ".startEndID.csv");
				for (Map.Entry<String, List<String>> entry : pathMap.entrySet()) {
					String key = entry.getKey();
					for (int i = 0; i < entry.getValue().size(); i++) {
						output = output + key + "\t" + entry.getValue().get(i) + "\n";
			        }
					
				}
				
				// write to file
				writer.write(output);
				writer.close();
				System.out.println("writing " + filename + ".startEndID.csv finished.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
