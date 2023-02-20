package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Iterator;

public class GenVPwriteAndCombine {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a folder contains a list of .csv files of [varWrite|parWrite*] for each comp.\n"
					+ "Output: 1. a .csv file that combines all the [varWrite|parWrite*] results for each comp;"
					+ "2. a .csv file of compVPwrite relationship.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter VPWriter;
			BufferedWriter combineWriter;
			
			try {
				// first get the folder name: args[0]
				File folder = new File(args[0]);
				File[] listOfFiles = folder.listFiles();
				
				// create writers
				VPWriter = new BufferedWriter(new FileWriter("compVPwrite.csv"));
				combineWriter = new BufferedWriter(new FileWriter("combine.csv"));
				
				// iterate each file and do the same combine and generate compVPwrite relationship
				for (File file : listOfFiles) {
				    if (file.isFile()) {
				    	System.out.println("Dealing with " + file);
				        // read the file
				    	reader = new BufferedReader(new FileReader(file));
				    	
				    	// read the first line: title of edge.csv
				    	String line = reader.readLine();
				    	
				    	/*
				    	 * don't do the following
				    	 * otherwise for each file in the folder, will repeatly write the header
				    	 */
				    	// write the first line to compVPwrite.csv
				    	//VPWriter.write(line + "\n");
				    	
				    	// for each line:
				    	// write it to combine.csv
				    	// split each line and generate compVPwrite fact, write to compVPwrite.csv
				    	while ((line = reader.readLine()) != null) {
				    		// write the line to combine.csv
				    		combineWriter.write(line + "\n");
				    		
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
							
							// generate condition for current line: the compVPwrite relationship
							String condition = "";
							for (String pc : PCset) {
								condition = condition + "&&" + pc;
							}
							
							// remove the first "&&" in condition
							condition = condition.substring(2);
							
							// generate VPwrite fact for current line
							String VPwrite = startID + "\t" + endID + "\t" 
											+ "compVPwrite" + "\t" + condition + "\n";
							
							// write the VPwrite fact to "compVPwrite.csv"
							VPWriter.write(VPwrite);
				    	}
				    }
				}
				
				VPWriter.close();
				combineWriter.close();
				System.out.println("Finish writing compVPwrite.csv");
				System.out.println("Finish writing combine.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
