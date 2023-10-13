package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class GenNodeList {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains the result of list of nodes in neo4j.\n"
					+ "Output: 1. a .csv file of the pair of node int ID and string ID.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is neo4j result of [call*]
				reader = new BufferedReader(new FileReader(args[0]));
				
				HashMap<String, String> idMap = new HashMap<>();
				
				// create writers
				writer = new BufferedWriter(new FileWriter("idList.csv"));
				
				// read the first line: title of neo4j result
		    	String line = reader.readLine();
		    	
		    	String intID, strID;
		    	
		    	while ((line = reader.readLine()) != null) {
		    		// do the ID spliting
		    		String[] splitId = line.trim().split("\"\"id\"\":");
		    		intID = splitId[1].split(",\"\"labels\"\"")[0];
		    		strID = splitId[2].split("\"\"")[1].split("\"\"")[0];
		    		writer.write(intID + "\t" + strID + "\n");
		    		
		    	}
		    	writer.close();
			    System.out.println("Finish writing idList.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
