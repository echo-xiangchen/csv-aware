package expr.root;

import java.io.*;
import java.util.*;

public class CompareNeo4jSouffle {
	public static void main(String[] args) {
		if (args.length < 2 ) {
			System.out.println("Usage: "
					+ "Input: two .csv file that contains analysis result from Neo4j and souffle.\n"
					+ "Output: if results from Neo4j and souffle are identical.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, HashSet<String>> neo4jMap = new LinkedHashMap<>();
			Map<String, HashSet<String>> souffleMap = new LinkedHashMap<>();
			Map<String, HashSet<String>> inNeo4jNotSouffleMap = new LinkedHashMap<>();
			Map<String, HashSet<String>> inSouffleNotNeo4jMap = new LinkedHashMap<>();
			
			
			
			try {
				// args[0] is neo4j output
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

				
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				
				if (neo4jMap.keySet().equals(souffleMap.keySet())) {
					System.out.println("keys are equal");
				} else {
					System.out.println("keys are not equal");
					HashSet<String> inNeo4jkey = new HashSet<>(neo4jMap.keySet());
					HashSet<String> inSoufflekey = new HashSet<>(souffleMap.keySet());
					
					// calculate keyset intersection
					HashSet<String> intersection = new HashSet<>(neo4jMap.keySet());
					intersection.retainAll(souffleMap.keySet());
					
					// key in neo4j not in souffle
					inNeo4jkey.removeAll(intersection);
					
					// key in souffle not in neo4j
					inSoufflekey.removeAll(intersection);
					
					if (!inNeo4jkey.isEmpty()) {
						System.out.println("===============================");
						System.out.println("Keys in Neo4j not in Souffle:");
						System.out.println("===============================");
						for( String curKey : inNeo4jkey ){
				            System.out.println( curKey );
				        }
						System.out.println("===============================");
					}
					
					if (!inSoufflekey.isEmpty()) {
						System.out.println("===============================");
						System.out.println("Keys in Souffle not in Neo4j:");
						System.out.println("===============================");
						for( String curKey : inSoufflekey ){
				            System.out.println( curKey );
				        }
						System.out.println("===============================");
					}
				}
				
				for (Map.Entry<String, HashSet<String>> entry : neo4jMap.entrySet()) {
					String key = entry.getKey();
					
					if (!neo4jMap.get(key).equals(souffleMap.get(key))) {
						// first copy two hashsets (value representing end IDs)
						HashSet<String> inNeo4jNotSouffle = new HashSet<>(neo4jMap.get(key));
						HashSet<String> inSouffleNotNeo4j = new HashSet<>(souffleMap.get(key));
						
						// captures the intersection of neo4j and souffle
						HashSet<String> intersection = new HashSet<>(neo4jMap.get(key));
						intersection.retainAll(souffleMap.get(key));
						
						// Neo4j - intersection is the end IDs that in Neo4j but not in souffle
						inNeo4jNotSouffle.removeAll(intersection);
						
						// souffle - intersection is the end ID that in souffle but not in neo4j
						inSouffleNotNeo4j.removeAll(intersection);
						
						// capture end IDs returned by neo4j but not by souffle
						if (!inNeo4jNotSouffle.isEmpty()) {
							inNeo4jNotSouffleMap.put(key, inNeo4jNotSouffle);
						}
						
						// capture end IDs returned by souffle but not by neo4j
						if (!inSouffleNotNeo4j.isEmpty()) {
							inSouffleNotNeo4jMap.put(key, inSouffleNotNeo4j);
						}
						
					}
				}
				
				// if both inNeo4jNotSouffleMap and inSouffleNotNeo4jMap are empty
				// then results are identical
				if (inNeo4jNotSouffleMap.isEmpty() && inSouffleNotNeo4jMap.isEmpty()) {
					System.out.println("Results of Neo4j and Souffle are identical.");
				}
				
				// if there are any key-value pair return by neo4j but not souffle
				if (!inNeo4jNotSouffleMap.isEmpty()) {
					String writeInNeo4jNotSouffle = "";
					
					writer = new BufferedWriter(new FileWriter(filename + ".inNeo4jNotSouffle.csv"));
					
					System.out.println("writing " + filename + ".inNeo4jNotSouffle.csv");
					for (Map.Entry<String, HashSet<String>> entry : inNeo4jNotSouffleMap.entrySet()) {
						String key = entry.getKey();
						for (String value : inNeo4jNotSouffleMap.get(key)) {
							writeInNeo4jNotSouffle = key + "\t" + value + "\n";
							// write to file
							writer.write(writeInNeo4jNotSouffle);
				        }
						
					}
					
//					// write to file
//					writer.write(writeInNeo4jNotSouffle);
					writer.close();
					System.out.println("writing " + filename + ".inNeo4jNotSouffle.csv finished.");
				} 
				
				// if there are any key-value pair return by souffle but not neo4j
				if (!inSouffleNotNeo4jMap.isEmpty()) {
					String writeInSouffleNotNeo4j = "";
					
					writer = new BufferedWriter(new FileWriter(filename + ".inSouffleNotNeo4j.csv"));
					
					System.out.println("writing " + filename + ".inSouffleNotNeo4j.csv");
					for (Map.Entry<String, HashSet<String>> entry : inSouffleNotNeo4jMap.entrySet()) {
						String key = entry.getKey();
						for (String value : inSouffleNotNeo4jMap.get(key)) {
							writeInSouffleNotNeo4j = key + "\t" + value + "\n";
							// write to file
							writer.write(writeInSouffleNotNeo4j);
				        }
						
					}
					
//					// write to file
//					writer.write(writeInSouffleNotNeo4j);
					writer.close();
					System.out.println("writing " + filename + ".inSouffleNotNeo4j.csv finished.");
				}
				
				writer = new BufferedWriter(new FileWriter(filename + ".startEndID.csv"));
				
				// output the start and end ID of neo4j result
				String output = "";
				System.out.println("writing " + filename + ".startEndID.csv");
				for (Map.Entry<String, HashSet<String>> entry : neo4jMap.entrySet()) {
					String key = entry.getKey();
					for (String value : neo4jMap.get(key)) {
						output = key + "\t" + value + "\n";
						// write to file
						writer.write(output);
			        }
					
				}
				
//				// write to file
//				writer.write(output);
				writer.close();
				System.out.println("writing " + filename + ".startEndID.csv finished.");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
