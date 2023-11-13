package visualization;

import java.io.*;
import java.util.*;

public class CapDistinctAuthorNames {
	public static void main(String[] args) {
		Set<String> uniqueNames = new HashSet<>();
		
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a file that contains lines of author names.\n"
					+ "Output: a file that only contains distinct author names.\n"
					+ "Warning: You do not include any file.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				//Get the CSVReader instance with specifying the delimiter to be used
				reader = new BufferedReader(new FileReader(args[0]));
				writer = new BufferedWriter(new FileWriter("distinct_author.txt"));
				
				String line;
				
				int num = 0;
				while ((line = reader.readLine()) != null) {
					String[] names = line.split(",");
	                for (String name : names) {
	                    String trimmedName = name.trim();
	                    num++;
	                    if (!trimmedName.isEmpty()) {
	                        uniqueNames.add(trimmedName);
	                    }
	                }
				}
				
				for (String name : uniqueNames) {
					writer.write(name);
					writer.newLine();
	            }
				
				writer.close();
				System.out.println("number: " + num);
				System.out.println("writing distinct_author.txt finished.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
