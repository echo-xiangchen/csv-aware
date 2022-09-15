package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.misc.Pair;


public class ReplaceConditions {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a desugared.c file that from SugarC\n"
					+ "Output: three files of renaming variable, renaming condition, and a file applies these replacements.\n"
					+ "Warning: You do not include any .c files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, Long> renameVarMap = new LinkedHashMap<>();
			Map<String, Long> renameCondMap = new LinkedHashMap<>();
			List<String> codebody = new ArrayList<>();
			
			// variables for spliting the replacement pair
			Map<String, String> replaceCondPair = new LinkedHashMap<>();
			String[] varpair;
			String left = "";
			String right = "";
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					// add lines starts with "__static_renaming" to renameVarMap
					if (line.startsWith("__static_renaming")) {
						if (!renameVarMap.containsKey(line)) {
							renameVarMap.put(line, null);
							// add lines starts with "__static_condition_renaming" to renameCondMap
						}
					} else if (line.startsWith("__static_condition_renaming")) {
						if (!renameCondMap.containsKey(line)) {
							renameCondMap.put(line, null);
						}
						// add the rest code body to CodeBodyMap
					} else if (!line.startsWith("extern const bool")) {
						codebody.add(line);
					}
				}
				
				// compute the replacement list
				for (String var : renameVarMap.keySet()) {
					// capture the replacement pair
					varpair = var.split(",");
					left = varpair[0].split("\\(")[1].replaceAll("\"", "");
					right = varpair[1].replaceAll("\"", "").replaceAll("\\);", "").trim();
					replaceCondPair.put(left, right);
				}
				
				for (String var : renameCondMap.keySet()) {
					// capture the replacement pair
					varpair = var.split(",");
					left = varpair[0].split("\\(")[1].replaceAll("\"", "");
					right = varpair[1].replaceAll("\"", "").replaceAll("defined ", "ENABLE_")
							.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(";", "").trim();
					replaceCondPair.put(left, right);
				}
				
				// output the rename variable file
				System.out.println("writing renameVar.c.");
				writer = new BufferedWriter(new FileWriter("renameVar.c"));
				String output = "";
				for (String var : renameVarMap.keySet()) {
					// add it to output
					output = output + var + "\n";
				}
				writer.write(output);
				writer.close();
				System.out.println("writing renameVar.c finished.");
				
				
				// output the rename condition file
				System.out.println("writing renameCond.c.");
				writer = new BufferedWriter(new FileWriter("renameCond.c"));
				output = "";
				for (String var : renameCondMap.keySet()) {
					output = output + var + "\n";
				}
				writer.write(output);
				writer.close();
				System.out.println("writing renameCond.c finished.");
				
				// output the code body file
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				writer = new BufferedWriter(new FileWriter(filename + ".desugared.replaced.c"));
				System.out.println("writing " + filename + ".desugared.replaced.c");
				
				output = "";
				String staticCond = "";
				for (int i = 0; i < codebody.size(); i++) {
					staticCond = codebody.get(i);
					// if found the replacement, replace with its actual value
					for (String pair : replaceCondPair.keySet()) {
						if (staticCond.contains(pair)) {
							staticCond = staticCond.replace(pair, replaceCondPair.get(pair));
						}
					}
					output = output + staticCond + "\n";
				}
				
				writer.write(output);
				writer.close();
				System.out.println("writing " + filename + ".desugared.replaced.c finished.");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
