package mining;

import java.io.*;
import java.util.*;

public class CountLanguagePrompt {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a dataset from MSR'24.\n"
					+ "Output: counting the number of prompts for each language.\n"
					+ "Warning: You do not include any file.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, List<Integer>> languageMap = new HashMap<String, List<Integer>>();
			 try {
				 
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
					
				//Get the CSVReader instance with specifying the delimiter to be used
				reader = new BufferedReader(new FileReader(args[0]));
				writer = new BufferedWriter(new FileWriter(filename + ".captureLanguagePrompt.json"));
					
				 String line;
		         String currentLanguage = null;
		         
		         while ((line = reader.readLine()) != null) {
		        	
//		                if (line.trim().startsWith("\"RepoLanguage\":") || line.trim().startsWith("\"NumberOfPrompts\":")) {
//		                	writer.write(line);
//		                    writer.newLine();
//		                } 
		        	 	// check if meeting the language part
		                if (line.trim().startsWith("\"RepoLanguage\":")) {
		                	if (line.split("\"").length > 3) {
		                		currentLanguage = line.split("\"")[3];
							} else { // normally ""RepoLanguage": null,"
								System.out.println(line);
							}
		                    
		                } else if (line.trim().startsWith("\"NumberOfPrompts\":") && currentLanguage != null) {
		                    int numberOfPrompts = Integer.parseInt(line.split(":")[1].replaceAll("[^\\d]", ""));
		                    languageMap.computeIfAbsent(currentLanguage, k -> new ArrayList<>()).add(numberOfPrompts);
		                }
		            }
		         	writer.close();
					System.out.println("writing " + filename + ".captureLanguagePrompt.json finished.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}	
