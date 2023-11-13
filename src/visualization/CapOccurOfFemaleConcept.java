package visualization;

import java.io.*;
import java.util.*;

public class CapOccurOfFemaleConcept {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a file that contains lines of femail concept keywords.\n"
					+ "Output: the format of D3 cluster visualization.\n"
					+ "Warning: You do not include any file.");
		} else {
			Map<String, Integer> keywordCounts = new HashMap<>();
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				//Get the CSVReader instance with specifying the delimiter to be used
				reader = new BufferedReader(new FileReader(args[0]));
				writer = new BufferedWriter(new FileWriter("female-cluster.txt"));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					// Split the line into keywords using a comma as the delimiter
	                String[] keywords = line.split(",");
	                
	             // Count the occurrences of each keyword
	                for (String keyword : keywords) {
	                    keyword = keyword.trim(); // Remove leading/trailing whitespace
	                    keywordCounts.put(keyword, keywordCounts.getOrDefault(keyword, 0) + 1);
	                }
				}
				
				boolean firstEntry = true;
				
				for (Map.Entry<String, Integer> entry : keywordCounts.entrySet()) {
					if (!firstEntry) {
						writer.write(",\n");
	                }
					// remove the entry that has the number of occurrence less than 50
					if (entry.getValue() >= 50) {
						writer.write("{ \"text\": \"" + entry.getKey() + "\", \"value\": " + entry.getValue() + "}");
					}
	                firstEntry = false;
				}
				writer.close();
				System.out.println("writing female-cluster.txt finished.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
