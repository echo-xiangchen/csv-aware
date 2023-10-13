package desugarProgram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CapVarFromProgram {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: "
					+ "Input: a file that contains the output of \"egrep -ir --include=*.{c,h} ENABLE\".\n"
					+ "Output: a list of feature variables (roughly).\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			HashSet<String> varSet = new HashSet<>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				writer = new BufferedWriter(new FileWriter("listOfVar.txt"));
				
				// create pattern
				Pattern MY_PATTERN = Pattern.compile("([A-Z][a-zA-Z0-9_]+)");
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					Matcher m = MY_PATTERN.matcher(line);
					while (m.find()) {
						varSet.add(m.group(1));
					}
				}
				
				int num = 1;
				for (String var : varSet) {
					writer.write(var + "\n");
					num++;
				}
				
				writer.close();
				System.out.println("Finish writing listOfVar.txt.");
				System.out.println("Number of variables: " + num + ".");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
