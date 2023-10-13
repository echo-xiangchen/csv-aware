package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class filterBA {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains the result of BA.\n"
					+ "Output: a .csv file of BA filtered by comps.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is neo4j result of BA
				reader = new BufferedReader(new FileReader(args[0]));
				
				// create writers
				writer = new BufferedWriter(new FileWriter("filterBA.csv"));
				
				// read the first line: title of neo4j result
		    	String line = reader.readLine();
		    	
		    	while ((line = reader.readLine()) != null) {
		    		// do the comp spliting
		    		String[] splitComp = line.split("\"\"comp\"\":\"\"");
		    		
		    		// save each PC to a hashset to remove duplicates
					List<String> CompList = new ArrayList<>();
					for (int i = 1; i < splitComp.length; i++) {
						if (!splitComp[i].split("\"\"")[0].isBlank()) {
							CompList.add(splitComp[i].split("\"\"")[0]);
						}
					}
		    	}
		    	writer.close();
			    System.out.println("Finish writing filterBA.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
