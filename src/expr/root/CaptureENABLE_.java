package expr.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaptureENABLE_ {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a file that contains lines containing ENABLE_ feature variables\n"
					+ "Output: a list of feature variables.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			
			Map<String, Long> varMap = new LinkedHashMap<String, Long>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
				Pattern MY_PATTERN = Pattern.compile("(ENABLE_[a-zA-Z0-9_]+)");
				
				while ((line = reader.readLine()) != null) {
					Matcher m = MY_PATTERN.matcher(line);
					while (m.find()) {
					    if (!varMap.containsKey(m.group(1))) {
					    	varMap.put(m.group(1), null);
					    }
					}
				}
		        
				for (String key : varMap.keySet()) {
					System.out.println("int " + key + ";");	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
