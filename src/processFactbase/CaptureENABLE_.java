package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaptureENABLE_ {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: "
					+ "Input: node.csv and edge.csv files that contains lines containing ENABLE_ feature variables\n"
					+ "Output: a list of feature variables.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter featurewriter;
			BufferedWriter listwriter;
			
			HashSet<String> varSet = new HashSet<>();
			
			try {
				// first read node.csv
				reader = new BufferedReader(new FileReader(args[0]));
				featurewriter = new BufferedWriter(new FileWriter("feature.h"));
				listwriter = new BufferedWriter(new FileWriter("featureList.txt"));
				
				
				// create pattern
				Pattern MY_PATTERN = Pattern.compile("(ENABLE_[a-zA-Z0-9_]+)");
				
				String nodeline;
				
				while ((nodeline = reader.readLine()) != null) {
					Matcher m = MY_PATTERN.matcher(nodeline);
					while (m.find()) {
						varSet.add(m.group(1));
					}
				}
				
				// then read edge.csv
				reader = new BufferedReader(new FileReader(args[1]));
				
				String edgeline;
				
				while ((edgeline = reader.readLine()) != null) {
					Matcher m = MY_PATTERN.matcher(edgeline);
					while (m.find()) {
						varSet.add(m.group(1));
					}
				}
		        
				for (String var : varSet) {
					featurewriter.write("int " + var + ";\n");
					listwriter.write(var + "\n");
				}
				
				featurewriter.close();
				listwriter.close();
		    	System.out.println("Finish writing feature.h");
		    	System.out.println("Finish writing featureList.h");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
