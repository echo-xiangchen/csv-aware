package expr.root;

import java.io.*;
import java.util.*;

public class ToyboxInc {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: "
					+ "Input: the list of macros starts with \"For\", and a macro defined in the file.\n"
					+ "Output: a final ToyboxInc.h file based on different C file\n"
					+ "Warning: You do not include any .c files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			Map<String, Long> varMap = new LinkedHashMap<String, Long>();
			Map<String, Long> undefMap = new LinkedHashMap<String, Long>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				writer = new BufferedWriter(new FileWriter("toyboxInc.h"));
				
				String line;
				
				// load first file - feature variable list
				while ((line = reader.readLine()) != null) {
					if (!varMap.containsKey(line)) {
				    	varMap.put(line, null);
				    }
				}
				
				String commentMacro = "";
				for (int i = 1; i < args.length; i++) {
					if (varMap.containsKey(args[i])) {
						varMap.remove(args[i]);
						commentMacro = "//#undef " + args[i];
						undefMap.put(commentMacro, null);
					}
				}
				
				String output = "#define __extension__ /*ignore*/\n"
						+ "#define restrict /*ignore*/\n"
						+ "#define __restrict /*ignore*/\n"
						+ "\n"
						+ "#include \"listOfGuards.h\"\n"
						+ "#include \"listOfNeeds.h\"\n"
						+ "#include \"listPredefinedMacros.h\"\n"
						+ "#include \"SystemConfig.h\"\n"
						+ "#include \"OPTSTR.h\"\n"
						+ "#include \"listOfExtra.h\"\n\n";
				for (String macro : undefMap.keySet()) {
					output = output + macro + "\n";
				}
				
				for (String macro : varMap.keySet()) {
					output = output + "#undef " +macro + "\n";
				}
				
				writer.write(output);
				writer.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
