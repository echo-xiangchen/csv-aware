package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class RmEmptyLineInNeo4jResult {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains analysis result from Neo4j.\n"
					+ "Output: same result but remove empty lines.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is neo4j output
				reader = new BufferedReader(new FileReader(args[0]));
				
				// skip the first line - it's the title of Neo4j's result
				String line = reader.readLine();
				
				int linenum = 2;
				
				// if line is not empty, add to output
				String output = "";
				
				while ((line = reader.readLine()) != null) {
					// some lines might be empty
					if (line.equals("\"\"")) {
						// dont use break here! it will quit the while loop!
						// use continue.
						//break;
						continue;
					}
					output = output + line + "\n";
					System.out.println("finished capturing line " + linenum + " of neo4j result.");
					linenum++;
				}
				
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				
				writer = new BufferedWriter(new FileWriter(filename + ".removeEmptyLine.csv"));
				
				System.out.println("writing " + filename + ".removeEmptyLine.csv");
				
				// write to file
				writer.write(output);
				writer.close();
				System.out.println("writing " + filename + ".removeEmptyLine.csv finished.");
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
