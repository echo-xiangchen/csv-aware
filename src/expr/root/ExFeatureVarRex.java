package expr.root;

import java.awt.RenderingHints.Key;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExFeatureVarRex {
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains node result from Rex.\n"
					+ "Output: a list of feature variables.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			
			// hashmap stores the <pc, bddAddress> Map
			Map<String, Long> varMap = new LinkedHashMap<String, Long>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
			
				while ((line = reader.readLine()) != null) {
					boolean writeToFile = false;
					
					String[] varSplit = (line.split(";;")[0]).split("decl;unknownFeature;");
					String var = varSplit[varSplit.length - 1];
					
					if (Character.compare(var.charAt(0), ':') == 0) {
						var = var.substring(2);
						if (Character.compare(var.charAt(0), 'k') == 0 || Character.compare(var.charAt(0), 'K') == 0) {
							writeToFile = true;
						}
					} else if (Character.compare(var.charAt(0), 'k') == 0 || Character.compare(var.charAt(0), 'K') == 0) {
						writeToFile = true;
					}
					
					if (writeToFile) {
						//finalOutput = finalOutput.concat(var + "\n");
						if (!varMap.containsKey(var)) {
							varMap.put(var, null);
						}
					}
				}
				
				for (String key : varMap.keySet()) {
					System.out.println(key);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
