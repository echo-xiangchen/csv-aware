package expr.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CompareStartEndIDsouffleNeo4j {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: "
					+ "Input: two .csv files: 1. souffle output, 2. start and end ID of neo4j output \n"
					+ "Output: compare these two files to see if they matches with each other.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			
			Map<String, HashSet<String>> neo4jMap = new LinkedHashMap<>();
			Map<String, HashSet<String>> souffleMap = new LinkedHashMap<>();
			try {
				// first read the file from neo4j - already extracted the start and end node ID
				reader = new BufferedReader(new FileReader(args[0]));
				
				String lineneo4j;
				
				while ((lineneo4j = reader.readLine()) != null) {
					// split the file to get start id and end id
					String[] splitLine = lineneo4j.split("\t");
					String startID = splitLine[0];
					String endID = splitLine[1];
					
					// add start id and end id into neo4jMap
					if (!neo4jMap.containsKey(startID)) {
						HashSet<String> endIDset = new HashSet<>();
						endIDset.add(endID);
						neo4jMap.put(startID, endIDset);
					} else {
						neo4jMap.get(startID).add(endID);
					}
				}
				
				// then read the file from souffle
				reader = new BufferedReader(new FileReader(args[1]));
				
				String linesouffle;
				
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
				}
				
				if (neo4jMap.keySet().equals(souffleMap.keySet())) {
					System.out.println("keys are equal");
				} else {
					System.out.println("keys are not equal");
				}
				
				for (Map.Entry<String, HashSet<String>> entry : neo4jMap.entrySet()) {
					String key = entry.getKey();
					if (!neo4jMap.get(key).equals(souffleMap.get(key))) {
						System.out.println("key-value pair for " + key + "is not equal.");
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
