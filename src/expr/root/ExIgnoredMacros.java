package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExIgnoredMacros {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: "
					+ "Input: a .c file that contains static_condition_renaming.\n"
					+ "Output: a list of ignored macros\n"
					+ "Warning: You do not include any .c files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, Long> varMap = new LinkedHashMap<String, Long>();
			Map<String, Long> undefMap = new LinkedHashMap<String, Long>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				writer = new BufferedWriter(new FileWriter("listOfExtra.h"));
				
				String line;
				
				// load first file - feature variable list
				while ((line = reader.readLine()) != null) {
					if (!varMap.containsKey(line)) {
				    	varMap.put(line, null);
				    }
				}
				Pattern MY_PATTERN = Pattern.compile("([a-zA-Z_][a-zA-Z0-9_]+)");
				reader = new BufferedReader(new FileReader(args[1]));
				
				while ((line = reader.readLine()) != null) {
					String condition = line.split(",")[1];
					Matcher m = MY_PATTERN.matcher(condition);
					while (m.find()) {
					    if (!varMap.containsKey(m.group(1))) {
					    	undefMap.put(m.group(1), null);
					    }
					}
				}
		        
				String output = "";
				String TT = "TT";
				String defined = "defined";
				for (String macro : undefMap.keySet()) {
					if (!macro.startsWith("FOR") && !macro.equals(TT) && !macro.equals(defined)) {
						output = output + "#undef " +macro + "\n";
					}
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
