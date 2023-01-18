package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Replace_swig_types {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println(
					"Usage: " + "Input: a file contains the defines and a file of the source code of subversion\n"
							+ "Output: source code with replacement of swig_types.\n"
							+ "Warning: You do not include any .c files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;

			Map<String, String> swig_tpyeMap = new LinkedHashMap<>();

			try {
				reader = new BufferedReader(new FileReader(args[0]));

				String line;
				// compute the defines list - replacement list
				while ((line = reader.readLine()) != null) {
					String[] splitLine = line.split(" ");
					if (!swig_tpyeMap.containsKey(splitLine[1])) {
						swig_tpyeMap.put(splitLine[1], splitLine[2]);
					}
				}
				
				// perform the replacement
				reader = new BufferedReader(new FileReader(args[1]));
				writer = new BufferedWriter(new FileWriter("after_replace.c"));
				
				String output = "";
				while ((line = reader.readLine()) != null) {
					for (String replace : swig_tpyeMap.keySet()) {
						if (line.contains(replace)) {
							line = line.replaceAll(replace, swig_tpyeMap.get(replace));
						}
					}
					output = output + line + "\n";
				}
				
				writer.write(output);
				writer.close();
				System.out.println("writing " + "after_replace.c finished.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
