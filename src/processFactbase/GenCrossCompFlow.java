package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GenCrossCompFlow {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains the result of flow of cross comp communication.\n"
					+ "Output: 1. a .csv file of crossCompFlow relationship.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is neo4j result of [crossComp*]
				reader = new BufferedReader(new FileReader(args[0]));
				
				// create writers
				writer = new BufferedWriter(new FileWriter("crossCompFlow.csv"));
				
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
					
					// generate condition for current line: the crossCompFlow relationship
					String condition = "";
					for (String pc : PCset) {
						condition = condition + "&&" + pc;
					}
					
					// remove the first "&&" in condition
					condition = condition.substring(2);
					
					// do the comp spliting
					String[] splitComp = line.trim().split("comp\"\":\"\"");
					
					// save each PC to a hashset to remove duplicates
					List<String> CompList = new ArrayList<>();			
					
					for (int i = 1; i < splitComp.length; i++) {
						CompList.add(splitComp[i].split("\"\"")[0]);
					}
					
					String compFlow = "";
					for (int i = 0; i < CompList.size(); i++) {
						if (!CompList.get(i).isBlank()) {
							compFlow = compFlow + CompList.get(i) + ", ";
						}
					}
					// remove the last ","
					compFlow = compFlow.substring(0, compFlow.length() - 2);
					
					// generate crossCompFlow fact for current line
					String crossCompFlow = startID + "\t" + endID + "\t" 
									+ "crossCompFlow" + "\t" + condition + "\t" + compFlow + "\n";
					
					// write the callClosure fact to "callClosure.csv"
					writer.write(crossCompFlow);
				}
					
				writer.close();
			    System.out.println("Finish writing crossCompFlow.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
