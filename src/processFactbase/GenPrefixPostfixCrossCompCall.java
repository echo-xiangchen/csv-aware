package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Iterator;

public class GenPrefixPostfixCrossCompCall {
	public static void main(String[] args) {
		if (args.length < 3 ) {
			System.out.println("Usage: "
					+ "Input: a folder contains a list of .csv files of prefixCall, postfixCall, and crossCompCall for each comp.\n"
					+ "Output: three .csv files that combines all the call results for each comp\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter prefixCall;
			BufferedWriter compCall;
			BufferedWriter postfixCall;
			
			try {
				/*
				 * ******************************************************************
		    	 * deal with prefixCall
		    	 * ******************************************************************
		    	 */
				// first get the folder name: args[0]
				File folder = new File(args[0]);
				File[] listOfFiles = folder.listFiles();
				
				// create writers
				prefixCall = new BufferedWriter(new FileWriter("prefixCall.csv"));
				
				// iterate each file and do the same combine and generate compCall relationship
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
				    	// write the first line to compCall.csv
				    	//call.write(line + "\n");
				    	
				    	// for each line:
				    	// write it to combine.csv
				    	// split each line and generate compCall fact, write to compCall.csv
				    	while ((line = reader.readLine()) != null) {
				    		// write the line to combine.csv
				    		//combineWriter.write(line + "\n");
				    		
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
							
							// generate condition for current line: the compCall relationship
							String condition = "";
							for (String pc : PCset) {
								condition = condition + "&&" + pc;
							}
							
							// remove the first "&&" in condition
							condition = condition.substring(2);
							
							// generate VPwrite fact for current line
							String prefixCallStr = startID + "\t" + endID + "\t" 
											+ "prefixCall" + "\t" + condition + "\n";
							
							// write the VPwrite fact to "compCall.csv"
							prefixCall.write(prefixCallStr);
				    	}
				    }
				}
				
				prefixCall.close();
				System.out.println("Finish writing prefixCall.csv");
				
				/*
				 * ******************************************************************
		    	 * deal with compCall
		    	 * ******************************************************************
		    	 */
				// first get the folder name: args[1]
				folder = new File(args[1]);
				listOfFiles = folder.listFiles();
				
				// create writers
				compCall = new BufferedWriter(new FileWriter("compCall.csv"));
				
				// iterate each file and do the same combine and generate compCall relationship
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
				    	// write the first line to compCall.csv
				    	//call.write(line + "\n");
				    	
				    	// for each line:
				    	// write it to combine.csv
				    	// split each line and generate compCall fact, write to compCall.csv
				    	while ((line = reader.readLine()) != null) {
				    		// write the line to combine.csv
				    		//combineWriter.write(line + "\n");
				    		
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
							
							// generate condition for current line: the compCall relationship
							String condition = "";
							for (String pc : PCset) {
								condition = condition + "&&" + pc;
							}
							
							// remove the first "&&" in condition
							condition = condition.substring(2);
							
							// generate VPwrite fact for current line
							String compCallStr = startID + "\t" + endID + "\t" 
											+ "compCall" + "\t" + condition + "\n";
							
							// write the VPwrite fact to "compCall.csv"
							compCall.write(compCallStr);
				    	}
				    }
				}
				
				compCall.close();
				System.out.println("Finish writing compCall.csv");
				
				/*
				 * ******************************************************************
		    	 * deal with postfixCall
		    	 * ******************************************************************
		    	 */
				// first get the folder name: args[1]
				folder = new File(args[2]);
				listOfFiles = folder.listFiles();
				
				// create writers
				postfixCall = new BufferedWriter(new FileWriter("postfixCall.csv"));
				
				// iterate each file and do the same combine and generate postfixCall relationship
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
				    	// write the first line to postfixCall.csv
				    	//call.write(line + "\n");
				    	
				    	// for each line:
				    	// write it to combine.csv
				    	// split each line and generate postfixCall fact, write to postfixCall.csv
				    	while ((line = reader.readLine()) != null) {
				    		// write the line to combine.csv
				    		//combineWriter.write(line + "\n");
				    		
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
							
							// generate condition for current line: the postfixCall relationship
							String condition = "";
							for (String pc : PCset) {
								condition = condition + "&&" + pc;
							}
							
							// remove the first "&&" in condition
							condition = condition.substring(2);
							
							// generate VPwrite fact for current line
							String postfixCallStr = startID + "\t" + endID + "\t" 
											+ "postfixCall" + "\t" + condition + "\n";
							
							// write the VPwrite fact to "postfixCall.csv"
							postfixCall.write(postfixCallStr);
				    	}
				    }
				}
				
				postfixCall.close();
				System.out.println("Finish writing postfixCall.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}