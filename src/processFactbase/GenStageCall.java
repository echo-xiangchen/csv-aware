package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.antlr.v4.runtime.misc.Pair;

public class GenStageCall {
	public static void main(String[] args) {
		if (args.length < 2 ) {
			System.out.println("Usage: "
					+ "Input: two .csv files: 1. contains the result of [call*], 2. the idList.csv.\n"
					+ "Output: 1. a .csv file of staged call relationship - capture the pair of calls.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is neo4j result of [call*]
				reader = new BufferedReader(new FileReader(args[0]));
				// the callPair is <start, <end, condition>> pair of call relation
				HashMap<String, List<HashMap<String, String>>> callPair= new HashMap<>();
				HashMap<String, String> idList = new HashMap<>();
				
				// create writers
				writer = new BufferedWriter(new FileWriter("stageCall.csv"));
				
				// read the first line: title of neo4j result
		    	String line = reader.readLine();
		    	
		    	String start, end, condition;
		    	
		    	while ((line = reader.readLine()) != null) {
		    		// do the ID spliting
		    		String[] splitId = line.trim().split("\"\"type\"\":\"\"call\"\",\"\"start\"\":");
		    		for (int i = 1; i < splitId.length; i++) {
						String[] startEnd = splitId[i].trim().split(",\"\"end\"\":");
						start = startEnd[0];
						end = startEnd[1].split(",\"\"end\"\":")[0].split(",\"\"properties\"\"")[0];
						condition = startEnd[1].split("\"\"condition\"\":\"\"")[1].split("\"\"")[0];
						
						boolean containsKey = false;
						if (!callPair.containsKey(start)) {
							HashMap<String, String> endCondition = new HashMap<>();
							List<HashMap<String, String>> endList = new ArrayList<>();
							endCondition.put(end, condition);
							endList.add(endCondition);
							callPair.put(start, endList);
						} else {
							for (HashMap<String, String> innerMap : callPair.get(start)) {
								if (innerMap.containsKey(end)) {
							        containsKey = true;
							        break;
							    }
							}
							if (!containsKey) {
								HashMap<String, String> endCondition = new HashMap<>();
								endCondition.put(end, condition);
								callPair.get(start).add(endCondition);
							}
						}
					}
		    	}
		    	
		    	// args[1] is neo4j result of idList.csv
				reader = new BufferedReader(new FileReader(args[1]));
				
				while ((line = reader.readLine()) != null) {
					String[] idPair = line.trim().split("\t");
					idList.put(idPair[0], idPair[1]);
				}
				
				
				for (String startid : callPair.keySet()) {
				    List<HashMap<String, String>> innerList = callPair.get(startid);
				    
				    for (HashMap<String, String> innerMap : innerList) {
				    	for (String endid : innerMap.keySet()) {
				            String cond = innerMap.get(endid);
				            writer.write(idList.get(startid) + "\t"
				            		+ idList.get(endid) + "\t"
				            		+ "stageCall" + "\t"
				            		+ cond + "\t" + "\n");
				        }
				    }
				}
				
				writer.close();
			    System.out.println("Finish writing stageCall.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
