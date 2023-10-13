package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalVResult {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains the result of neo4j result.\n"
					+ "Output: 1. number and % of VResult.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// args[0] is neo4j result of the query
				reader = new BufferedReader(new FileReader(args[0]));
				
				// capture input node.csv file name, and use it in output file
				String[] splitNodeFileName = args[0].toString().split("/");
				String nodefilename = splitNodeFileName[splitNodeFileName.length - 1].split("\\.")[0];
				
				// create writers
//				writer = new BufferedWriter(new FileWriter(nodefilename + ".VResult.csv"));
				
				// read and skip the first line: title of neo4j result
		    	String line = reader.readLine();
		    	
		    	int totalNum = 0;
		    	int Vnum = 0;
		    	
		    	// create pattern
				Pattern MY_PATTERN = Pattern.compile("(ENABLE_[a-zA-Z0-9_]+)");
		    	
		    	while ((line = reader.readLine()) != null) {
		    		Matcher m = MY_PATTERN.matcher(line);
					while (m.find()) {
//						writer.write(line + "\n");
						Vnum++;
						break;
					}
					totalNum++;
		    	}
		    	
		    	System.out.println("total num: " + totalNum);
		    	System.out.println("variable num: " + Vnum);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
