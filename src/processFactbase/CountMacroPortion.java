package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

public class CountMacroPortion {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a folder contains several files.\n"
					+ "Output: portion of macro guarded codes.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			int totalLinesOfCode = 0;
		    int ifdefLinesOfCode = 0;
		    
		    BufferedReader reader;
			
		    try {
		    	// first get the folder name: args[0]
				File folder = new File(args[0]);
				File[] listOfFiles = folder.listFiles();
				
				// iterate each file and do the same combine and generate compVPwrite relationship
				for (File file : listOfFiles) {
					if (file.isFile() && file.getName().endsWith(".c")) {
						reader = new BufferedReader(new FileReader(file));
								
						boolean insideIfdef = false;
						
						String line;
						
						while ((line = reader.readLine()) != null)  {
							totalLinesOfCode++;
							if (insideIfdef) {
			                    ifdefLinesOfCode++;
			                }
							
							if (line.contains("#if")) {
								insideIfdef = true;
							} else if (line.contains("#endif")) {
								insideIfdef = false;
							}
						}
					}
				}
				System.out.println("Total number of C files: " + listOfFiles.length);
	            System.out.println("Total number of lines of code: " + totalLinesOfCode);
	            System.out.println("Total number of ifdef lines of code: " + ifdefLinesOfCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
