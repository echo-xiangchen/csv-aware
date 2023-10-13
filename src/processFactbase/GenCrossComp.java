package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class GenCrossComp {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains the result of cross comp communication.\n"
					+ "Output: 1. a .csv file of crossComp relationship.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is neo4j result of [call*]
				reader = new BufferedReader(new FileReader(args[0]));
				
				// create writers
				writer = new BufferedWriter(new FileWriter("crossComp.csv"));
				
				// read the first line: title of neo4j result
		    	String line = reader.readLine();
		    	
		    	while ((line = reader.readLine()) != null) {
		    		// do the ID spliting
		    		String[] splitId = line.trim().split("id\"\":\"\"");
		    		String startID = splitId[1].split("\"\"")[0];
					String endID = splitId[splitId.length - 1].split("\"\"")[0];
					
					// do the PC spliting
					String[] splitPC = line.trim().split("condition\"\":\"\"");
					// save each PC to a hashset to remove duplicates
					HashSet<String> PCset = new HashSet<>();
					
					for (int i = 1; i < splitPC.length; i++) {
						if ((splitPC[i].split("\"\"")[0]).isBlank()) {
							PCset.add("True");
						} else {
							PCset.add(splitPC[i].split("\"\"")[0]);
						}
					}
					
					// generate condition for current line: the crossComp relationship
					String condition = "";
					for (String pc : PCset) {
						condition = condition + "&&" + pc;
					}
					
					// remove the first "&&" in condition
					condition = condition.substring(2);
					
					// generate VPwrite fact for current line
					String crossComp = startID + "\t" + endID + "\t" 
									+ "crossComp" + "\t" + condition + "\n";
					
					// write the callClosure fact to "callClosure.csv"
					writer.write(crossComp);
				}
					
				writer.close();
			    System.out.println("Finish writing crossComp.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
