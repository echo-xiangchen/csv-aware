package expr.root;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;


public class DistinctPC {


	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .tsv file that contains analysis result from Neo4j.\n"
					+ "Output: a .tsv file that only contains distinct variables in PCs.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			CSVReader reader = null;
			
			try {
				//Get the CSVReader instance with specifying the delimiter to be used
			    reader = new CSVReader(new FileReader(args[0]));
			    
			   //Read CSV line by line and use the string array as you want
			   String[] nextLine;
			   
			   while ((nextLine = reader.readNext()) != null) {
				   if (nextLine != null) {
					
				}
				
			}
				
			} catch (Exception e) {
				e.printStackTrace();
				} finally {
					try {
						reader.close();
						} catch (IOException e) {
							e.printStackTrace();
							}
				}
		}
	}
}
