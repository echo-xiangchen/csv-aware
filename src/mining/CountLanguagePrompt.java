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
			// this writer is for writing the lines of `RepoLanguage` and `NumberOfPrompts`
			BufferedWriter lan_prompt_writer;
			// this writer is for writing the maximum, minimum and average value of each language
			BufferedWriter prompt_data_writer;
			// this writer is for writing the accumulative data for each file inside the folder
			BufferedWriter accu_prompt_data_writer;
			
			Map<String, List<Integer>> acc_languageMap = new HashMap<String, List<Integer>>();
			
			 try {
				 
				// first get the folder name: args[0]
				File folder = new File(args[0]);
				File[] listOfFiles = folder.listFiles();
					
		        for (File file : listOfFiles) {
		        	if (file.isFile()) {
		        		 System.out.println("Dealing with " + file);
		        		 
		        		// read the file
					    reader = new BufferedReader(new FileReader(file));
					    
					    // create the hashmap for seperate file
					    Map<String, List<Integer>> languageMap = new HashMap<String, List<Integer>>();
					    
					    // capture input file name, and use it in output file
						String[] splitInputFileName = file.toString().split("/");
						String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
						
						//Get the CSVReader instance with specifying the delimiter to be used
						//reader = new BufferedReader(new FileReader(args[0]));
						lan_prompt_writer = new BufferedWriter(new FileWriter(filename + ".captureLanguagePrompt.csv"));
						prompt_data_writer = new BufferedWriter(new FileWriter(filename + ".promptData.csv"));
					    
					    String line;
				        String currentLanguage = null;
		        		 
		        		 while ((line = reader.readLine()) != null) {
		 		        	
				                if (line.trim().startsWith("\"RepoLanguage\":") || line.trim().startsWith("\"NumberOfPrompts\":")) {
				                	lan_prompt_writer.write(line);
				                	lan_prompt_writer.newLine();
				                } 
				        	 	// check if meeting the language part
				                if (line.trim().startsWith("\"RepoLanguage\":")) {
				                	if (line.split("\"").length > 3) {
				                		currentLanguage = line.split("\"")[3];
									} else { // normally ""RepoLanguage": null,"
										currentLanguage = null;
										System.out.println(line);
									}
				                    
				                } else if (line.trim().startsWith("\"NumberOfPrompts\":") && currentLanguage != null) {
				                    int numberOfPrompts = Integer.parseInt(line.split(":")[1].replaceAll("[^\\d]", ""));
				                    languageMap.computeIfAbsent(currentLanguage, k -> new ArrayList<>()).add(numberOfPrompts);
				                    acc_languageMap.computeIfAbsent(currentLanguage, k -> new ArrayList<>()).add(numberOfPrompts); 
				                }
				            }
				         	lan_prompt_writer.close();
							System.out.println("writing " + filename + ".captureLanguagePrompt.csv finished.");
							
							prompt_data_writer.write("language,number of prompts, max prompts, min prompts, average prompts\n");
							for (String language : languageMap.keySet()) {
					            List<Integer> prompts = languageMap.get(language);

					            // Calculating maximum and minimum
					            int max = Collections.max(prompts);
					            int min = Collections.min(prompts);

					            // Calculating average
					            double sum = 0;
					            for (int prompt : prompts) {
					                sum += prompt;
					            }
					            double average = sum / prompts.size();

					            // Print the results for each language
					            prompt_data_writer.write(language + "," + languageMap.get(language).size() + "," + max + "," + min + "," + average + "\n");
							}
							prompt_data_writer.close();
							System.out.println("writing " + filename + ".promptData.csv finished.");
		        	 }
		         }
		        
		        // write the accumulative data
		        accu_prompt_data_writer = new BufferedWriter(new FileWriter("accu_promptData.csv"));
		        accu_prompt_data_writer.write("language,number of prompts, max prompts, min prompts, average prompts\n");
				for (String language : acc_languageMap.keySet()) {
		            List<Integer> prompts = acc_languageMap.get(language);

		            // Calculating maximum and minimum
		            int max = Collections.max(prompts);
		            int min = Collections.min(prompts);

		            // Calculating average
		            double sum = 0;
		            for (int prompt : prompts) {
		                sum += prompt;
		            }
		            double average = sum / prompts.size();

		            // Print the results for each language
		            accu_prompt_data_writer.write(language + "," + acc_languageMap.get(language).size() + "," + max + "," + min + "," + average + "\n");
				}
				accu_prompt_data_writer.close();
				System.out.println("writing accu_promptData.csv finished.");
		         
		         
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}	
