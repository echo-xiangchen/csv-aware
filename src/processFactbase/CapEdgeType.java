package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class CapEdgeType {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains the edges.\n"
					+ "Output: a list of edge types.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is the edge file
				reader = new BufferedReader(new FileReader(args[0]));
				
				// create writers
				writer = new BufferedWriter(new FileWriter("ListOfEdgeType.csv"));
				
				// read the first line: title of neo4j result
		    	String line = reader.readLine();
		    	
		    	// create type hashset
		    	HashSet<String> typeSet = new HashSet<>();
		    	
		    	while ((line = reader.readLine()) != null) {
					// do the type spliting
		    		String[] splitType = line.split("\t");
		    		String type = splitType[2];
		    		
		    		typeSet.add(type);
				}
		    	
		    	for (String t : typeSet) {
		    		writer.write(t + "\n");
				}
		    	
		    	writer.close();
			    System.out.println("Finish writing ListOfEdgeType.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
