package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class GenerateFeature_H {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: " + "Input: a file that contains list of feature variables.\n"
					+ "Output: a feature.h file.\n" + "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;

			Map<String, Map<String, Long>> varMap = new LinkedHashMap<>();

			try {
				reader = new BufferedReader(new FileReader(args[0]));
				writer = new BufferedWriter(new FileWriter("feature.h"));
				String line;

				while ((line = reader.readLine()) != null) {
					if (!varMap.containsKey(line)) {
						varMap.put(line, null);
					}
				}

				reader = new BufferedReader(new FileReader(args[1]));
				while ((line = reader.readLine()) != null) {
					if (!varMap.containsKey(line)) {
						varMap.put(line, null);
					}
				}

				String output = "";
				for (String macro : varMap.keySet()) {
					output = output + "int ENABLE_" + macro + ";\n";
				}
				//System.out.println(output);
				writer.write(output);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
