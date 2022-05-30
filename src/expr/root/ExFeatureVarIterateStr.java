package expr.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExFeatureVarIterateStr {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains edge result from Rex.\n"
					+ "Output: a list of feature variables.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			
			Map<String, Long> varMap = new LinkedHashMap<String, Long>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					for (int i = 0; i < line.length(); i++) {
						if (Character.compare(line.charAt(i), 'K') == 0 
								|| Character.compare(line.charAt(i), 'k') == 0) {
							if (!Character.isLetter(line.charAt(i-1))
									&& !Character.isDigit(line.charAt(i-1))
									&& !(Character.compare(line.charAt(i-1), '_') == 0)) {
								for (int j = i + 1; j < line.length(); j++) {
									if (Character.isLetter(line.charAt(j))
											|| Character.isDigit(line.charAt(j))
											|| Character.compare(line.charAt(j), '_') == 0) {
										continue;
									} else {
										varMap.put(line.substring(i, j), null);
										i = j;
										break;
									}
								}
							}
						}
					}
				}
		        
				for (String key : varMap.keySet()) {
					System.out.println(key);	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
}
