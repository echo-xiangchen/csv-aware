package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.StyledEditorKit.ForegroundAction;

public class filterICBC {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains the result of ICBC.\n"
					+ "Output: a .csv file of ICBC filtered by comps.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is neo4j result of ICBC
				reader = new BufferedReader(new FileReader(args[0]));
				
				// create writers
				writer = new BufferedWriter(new FileWriter("filterICBC.csv"));
				
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
					
					HashSet<String> crossCompSet = new HashSet<>();
					String[] crossComp = CompList.get(1).split(",");
					for (int i = 0; i < crossComp.length; i++) {
						crossCompSet.add(crossComp[i].trim());
					}
					
					if (!crossCompSet.contains(CompList.get(3))) {
						writer.write(line + "\n");
					}
		    	}
		    	writer.close();
			    System.out.println("Finish writing filterICBC.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
