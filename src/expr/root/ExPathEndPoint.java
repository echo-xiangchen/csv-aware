package expr.root;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVReader;

public class ExPathEndPoint {
	public static void main(String[] args) {
		// hashmap stores the <start, <end, PC>> Map
		Map<String, List<String>> pathMap = new LinkedHashMap<>();
		
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains analysis result from Neo4j.\n"
					+ "Output: a .csv file that only contains start node, end node and PC of paths.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			CSVReader reader = null;
			
			try {
				
				//Get the CSVReader instance with specifying the delimiter to be used
			    reader = new CSVReader(new FileReader(args[0]));
			    
			    //Read CSV line by line and use the string array as you want
			    String[] nextLine;
			    
			    while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						// get and split string
			    		String currentRow = Arrays.toString(nextLine);
			    		String[] splitId = currentRow.split("\"id\":\"");
			    		String startId = splitId[1].split("\"")[0];
			    		String endId = splitId[splitId.length-1].split("\"")[0];
						
						if (!pathMap.containsKey(startId)) {
							List<String> endList = new ArrayList<>();
							endList.add(endId);
							pathMap.put(startId, endList);
						} else if (!pathMap.get(startId).contains(endId)) {
							pathMap.get(startId).add(endId);
						}
					}
				}
			    
			    int total = 0;
			    for (Map.Entry<String, List<String>> entry : pathMap.entrySet()) {
					String key = entry.getKey();
					total = total + pathMap.get(key).size();
				}
			    
			    System.out.println(total);
				
			} catch (Exception e) {
				e.printStackTrace();
				} finally {
					try {
						reader.close();
						} catch (IOException e) {
							e.printStackTrace();
							}
				}
		}
	}
}
