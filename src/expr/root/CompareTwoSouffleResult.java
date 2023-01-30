package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class CompareTwoSouffleResult {
	public static void main(String[] args) {
		if (args.length < 2 ) {
			System.out.println("Usage: "
					+ "Input: two .csv file that both contains analysis result from souffle with different query.\n"
					+ "Output: if results from two files are identical.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, HashSet<String>> souffleMap1 = new LinkedHashMap<>();
			Map<String, HashSet<String>> souffleMap2 = new LinkedHashMap<>();
			Map<String, HashSet<String>> in1Not2Map = new LinkedHashMap<>();
			Map<String, HashSet<String>> in2Not1Map = new LinkedHashMap<>();
			
			try {
				// read first souffle result file
				reader = new BufferedReader(new FileReader(args[0]));
				
				String linesouffle1;
				int linenum = 1;
				
				while ((linesouffle1 = reader.readLine()) != null) {
					String[] splitLine = linesouffle1.split("\t");
					String startID = splitLine[0];
					String endID = splitLine[1];
					
					// add start id and end id into souffleMap
					if (!souffleMap1.containsKey(startID)) {
						HashSet<String> endIDset = new HashSet<>();
						endIDset.add(endID);
						souffleMap1.put(startID, endIDset);
					} else {
						souffleMap1.get(startID).add(endID);
					}
					System.out.println("finished capturing line " + linenum + " of souffle result 1.");
					linenum++;
				}
				
				// read second souffle result file
				reader = new BufferedReader(new FileReader(args[1]));
				String linesouffle2;
				linenum = 1;
				
				while ((linesouffle2 = reader.readLine()) != null) {
					String[] splitLine = linesouffle2.split("\t");
					String startID = splitLine[0];
					String endID = splitLine[1];
					
					// add start id and end id into souffleMap
					if (!souffleMap2.containsKey(startID)) {
						HashSet<String> endIDset = new HashSet<>();
						endIDset.add(endID);
						souffleMap2.put(startID, endIDset);
					} else {
						souffleMap2.get(startID).add(endID);
					}
					System.out.println("finished capturing line " + linenum + " of souffle result 2.");
					linenum++;
				}
				
				if (souffleMap1.keySet().equals(souffleMap2.keySet())) {
					System.out.println("keys are equal");
				} else {
					System.out.println("keys are not equal");
				}
				
				for (Map.Entry<String, HashSet<String>> entry : souffleMap1.entrySet()) {
					String key = entry.getKey();
					
					if (!souffleMap1.get(key).equals(souffleMap2.get(key))) {
						// first copy two hashsets (value representing end IDs)
						HashSet<String> in1Not2 = new HashSet<>(souffleMap1.get(key));
						HashSet<String> in2Not1 = new HashSet<>(souffleMap2.get(key));
						
						// captures the intersection of first and second file
						HashSet<String> intersection = new HashSet<>(souffleMap1.get(key));
						intersection.retainAll(souffleMap2.get(key));
						
						// souffleMap1 - intersection is the end IDs that in souffleMap1 but not in souffle2
						in1Not2.removeAll(intersection);
						
						// souffleMap2 - intersection is the end IDs that in souffleMap2 but not in souffleMap1
						in2Not1.removeAll(intersection);
						
						// capture end IDs returned by neo4j but not by souffle
						if (!in1Not2.isEmpty()) {
							in1Not2Map.put(key, in1Not2);
						}
						
						// capture end IDs returned by souffle but not by neo4j
						if (!in2Not1.isEmpty()) {
							in2Not1Map.put(key, in2Not1);
						}
					}
				}
				
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				
				// if there are any key-value pair return by 1 but not 2
				if (!in1Not2Map.isEmpty()) {
					String writeIn1Not2 = "";
					
					writer = new BufferedWriter(new FileWriter(filename + ".in1Not2Map.csv"));
					
					System.out.println("writing " + filename + ".in1Not2Map.csv");
					for (Map.Entry<String, HashSet<String>> entry : in1Not2Map.entrySet()) {
						String key = entry.getKey();
						for (String value : in1Not2Map.get(key)) {
							writeIn1Not2 = writeIn1Not2 + key + "\t" + value + "\n";
				        }
						
					}
					
					// write to file
					writer.write(writeIn1Not2);
					writer.close();
					System.out.println("writing " + filename + ".in1Not2Map.csv finished.");
				} 
				
				// if there are any key-value pair return by souffle but not neo4j
				if (!in2Not1Map.isEmpty()) {
					String writeIn2Not1 = "";
					
					writer = new BufferedWriter(new FileWriter(filename + ".in2Not1Map.csv"));
					
					System.out.println("writing " + filename + ".in2Not1Map.csv");
					for (Map.Entry<String, HashSet<String>> entry : in2Not1Map.entrySet()) {
						String key = entry.getKey();
						for (String value : in2Not1Map.get(key)) {
							writeIn2Not1 = writeIn2Not1 + key + "\t" + value + "\n";
				        }
						
					}
					
					// write to file
					writer.write(writeIn2Not1);
					writer.close();
					System.out.println("writing " + filename + ".in2Not1Map.csv finished.");
				}
				
				// if both inNeo4jNotSouffleMap and inSouffleNotNeo4jMap are empty
				// then results are identical
				if (in1Not2Map.isEmpty() && in2Not1Map.isEmpty()) {
					System.out.println("Results of two Souffle results are identical.");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
