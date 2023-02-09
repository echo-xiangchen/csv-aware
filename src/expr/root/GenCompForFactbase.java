package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class GenCompForFactbase {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file - the factbase.\n"
					+ "Output: a .csv file - split filename and generate comp.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				
				writer = new BufferedWriter(new FileWriter(filename + ".genComp.csv"));
				
				// skip the first line - it's the title of Neo4j's result
				String line = reader.readLine() + "\n";
				
				// write the first line
				writer.write(line);
				
				String output = "";
				
				while ((line = reader.readLine()) != null) {
					String[] splitLine = line.trim().split("\t");
					String compOrigin = splitLine[splitLine.length - 1];
					
					String[] compOriginSplit = compOrigin.trim().split("/");
					String comp = "";
					
					for (int i = 0; i < compOriginSplit.length - 1; i++) {
						comp = comp + compOriginSplit[i] + "/";
					}
					
//					// remove the last "/" of comp
//					comp = comp.substring(0, comp.length() - 1);
					for (int i = 0; i < splitLine.length - 1; i++) {
						output = output + splitLine[i] + "\t";
					}
					output = output + comp + "\n";
					// write to file
					writer.write(output);
					
					// reset output
					output = "";
				}
				
				writer.close();
				System.out.println("writing " + filename + ".genComp.csv finished.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
