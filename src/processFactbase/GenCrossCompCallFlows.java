package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class GenCrossCompCallFlows {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains the result of cross component call flows.\n"
					+ "path=(a:exitFunc)-[:crossCompCallFlow*1..]->(b:exitFunc) WHERE a.comp <> b.comp\n"
					+ "Output: 1. a .csv file of crossCompCallFlows relationship.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is neo4j result of [call*]
				reader = new BufferedReader(new FileReader(args[0]));
				
				// create writers
				writer = new BufferedWriter(new FileWriter("crossCompCallFlows.csv"));
				
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
					
					// generate condition for current line: the crossCompCall relationship
					String condition = "";
					for (String pc : PCset) {
						condition = condition + "&&" + pc;
					}
					
					// remove the first "&&" in condition
					condition = condition.substring(2);
					
					// generate VPwrite fact for current line
					String crossCompCallFlows = startID + "\t" + endID + "\t" 
									+ "crossCompCallFlows" + "\t" + condition + "\n";
					
					// write the callClosure fact to "callClosure.csv"
					writer.write(crossCompCallFlows);
				}
					
				writer.close();
			    System.out.println("Finish writing crossCompCallFlows.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
