package processFactbase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountPathLength {
	public static void main(String[] args) {
		if (args.length < 1 ) {
			System.out.println("Usage: "
					+ "Input: analysis result(path) from Neo4j.\n"
					+ "Output: the range of the path lengths of the result.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			
			int minOccurrence = Integer.MAX_VALUE;
	        int maxOccurrence = Integer.MIN_VALUE;
			
			try {
				// args[0] is result from neo4j
				reader = new BufferedReader(new FileReader(args[0]));
				
				// skip the first line
				String line = reader.readLine();
				int linenum = 2;
				Pattern pattern = Pattern.compile("\"\"type\"\"");
				
				while ((line = reader.readLine()) != null) {
					Matcher matcher = pattern.matcher(line);
					int count = 0;
					
					while (matcher.find()) {
			            count++;
			        }
					// update minOccurrence and maxOccurrence
					 if (count < minOccurrence) {
		                    minOccurrence = count;
		             }
		             if (count > maxOccurrence) {
		                    maxOccurrence = count;
		             }
		        System.out.println("finished line " + linenum);
				linenum++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Smallest number of occurrence: " + minOccurrence);
	        System.out.println("Largest number of occurrence: " + maxOccurrence);
		}
	}
}
