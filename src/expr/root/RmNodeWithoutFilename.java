package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class RmNodeWithoutFilename {
	public static void main(String[] args) {
		if (args.length < 2 ) {
			System.out.println("Usage: "
					+ "Input: two .csv file - 1. nodes without file name, 2. edges file.\n"
					+ "Output: edges file that remove the facts containing the nodes.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			HashSet<String> nodeSet = new HashSet<>();
			
			try {
				// args[0] is nodes without filename
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
				
				// first capture node id
				while ((line = reader.readLine()) != null) {
					String[] splitId = line.trim().split("\t");
					
					if (!nodeSet.contains(splitId[0])) {
						nodeSet.add(splitId[0]);
					}
				}
				
				// args[1] is edge file
				reader = new BufferedReader(new FileReader(args[1]));
				
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[1].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				
				writer = new BufferedWriter(new FileWriter(filename + ".rmNodeWithoutFilename.csv"));
				
				
				
				// iterate the edge file, remove those lines that contains the nodes
				while ((line = reader.readLine()) != null) {
					boolean writeToFile = true;
					for( String curKey : nodeSet ){
			            if (line.contains(curKey)) {
			            	writeToFile = false;
			            	break;
						}
			        }
					
					if (writeToFile) {
						writer.write(line+ "\n");
					}
				}
				
				writer.close();
				System.out.println("writing " + filename + ".rmNodeWithoutFilename.csv finished.");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
