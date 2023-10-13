package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class AddFuncEntryExitLabel {
	public static void main(String[] args) {
		if (args.length < 2 ) {
			System.out.println("Usage: "
					+ "Input: 1 .csv files: 1. list of cross component function call IDs 2. node.csv.\n"
					+ "Output: new node.csv where those cross component functions are labeled exitFunc and entryFunc.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			HashSet<String> exitFuncSet = new HashSet<>();
			HashSet<String> entryFuncSet = new HashSet<>();
			
			try {
				// first store the list of function IDs from args[0]
				reader = new BufferedReader(new FileReader(args[0]));
				String funcLine = reader.readLine();
				
				// start iterating function IDs and store them in exitFuncSet and entryFuncSet
	            while ((funcLine = reader.readLine()) != null) {
	            	String[] splitNodeLine = funcLine.trim().split("\",\"");
	            	exitFuncSet.add(splitNodeLine[0].substring(1));
	            	entryFuncSet.add(splitNodeLine[1].substring(0, splitNodeLine[1].length() - 1));
	            }
	            
	            // start modifying node.csv based on exitFuncSet and entryFuncSet and add the label
	            reader = new BufferedReader(new FileReader(args[1]));
	            String nodeLine = reader.readLine();
	            
	            // capture input node.csv file name, and use it in output file
				String[] splitNodeFileName = args[1].toString().split("/");
				String nodefilename = splitNodeFileName[splitNodeFileName.length - 1].split("\\.")[0];
				
	            // create writers
				writer = new BufferedWriter(new FileWriter(nodefilename + ".addLabel.csv"));
				//write the header of neo4j output
				writer.write(nodeLine + "\n");
				
				while ((nodeLine = reader.readLine()) != null) {
					String[] splitNodeLine = nodeLine.trim().split("\t");
					String id = splitNodeLine[0];
					
					if (exitFuncSet.contains(id) && entryFuncSet.contains(id)) {
						if (splitNodeLine.length < 4) {
							writer.write(id + "\t" + splitNodeLine[1] + ";exitFunc;entryFunc" + "\t"
									+ splitNodeLine[2] + "\t\t\n");
						} else {
							writer.write(id + "\t" + splitNodeLine[1] + ";exitFunc;entryFunc" + "\t"
									+ splitNodeLine[2] + "\t"
									+ splitNodeLine[3] + "\n");
						}
					} else if (exitFuncSet.contains(id)) {
						if (splitNodeLine.length < 4) {
							writer.write(id + "\t" + splitNodeLine[1] + ";exitFunc" + "\t"
									+ splitNodeLine[2] + "\t\t\n");
						} else {
							writer.write(id + "\t" + splitNodeLine[1] + ";exitFunc" + "\t"
									+ splitNodeLine[2] + "\t"
									+ splitNodeLine[3] + "\n");
						}
						
					} else if (entryFuncSet.contains(id)) {
						if (splitNodeLine.length < 4) {
							writer.write(id + "\t" + splitNodeLine[1] + ";entryFunc" + "\t"
									+ splitNodeLine[2] + "\t\t\n");
						} else {
							writer.write(id + "\t" + splitNodeLine[1] + ";entryFunc" + "\t"
									+ splitNodeLine[2] + "\t"
									+ splitNodeLine[3] + "\n");
						}
						
					} else {
						writer.write(nodeLine + "\n");
					}
				}
				
				writer.close();
			    System.out.println("Finish writing" + nodefilename + ".addLabel.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
