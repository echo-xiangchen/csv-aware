package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CompareAwareNonawareOutputs {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: "
					+ "Input: two .csv file that contains non-aware and aware analysis result from Neo4j.\n"
					+ "Output: a .csv file that contain the result in filtered aware but not in filtered non-aware results.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, String> resultMap = new LinkedHashMap<>();
			
			try {
				// first read the file that has small number of results
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line1, line2;
				
				while ((line1 = reader.readLine()) != null) {
					// add all results in the first file into resultMap
					resultMap.put(line1, null);
				}
				
				// then read the second file that has larger number of results
				reader = new BufferedReader(new FileReader(args[1]));
				
				while ((line2 = reader.readLine()) != null) {
					// see if there is any line that is not in the first file
					// if yes, output the line
					if (!resultMap.containsKey(line2)) {
						System.out.println(line2);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
