package expr.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExFileNameRuntimeEnabled {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .txt file that contains all the file containing 'RuntimeEnabled.*'.\n"
					+ "Output: a list of file names.\n"
					+ "Warning: You do not include any .txt files.");
		} else {
			BufferedReader reader;
			// hashmap stores the <pc, bddAddress> Map
			Map<String, Long> fileMap = new LinkedHashMap<String, Long>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					/*
					 *  splits on every zero-length string 
					 *  that is preceded by a "/" ((?<=/)) and 
					 *  followed by a letter (lower or upper case) ((?=[a-zA-Z])).
					 */
					
					// this would only extract the file name (excluding path)
					// e.g., v8_window.cc
//					String[] lineSplit = line.split("(?<=/)(?=[a-zA-Z])");
//					String fileName = lineSplit[lineSplit.length - 1].split(":")[0];
//					fileMap.put(fileName, null);
					
					// this would extract the whole path
					// e.g., /third_party/blink/renderer/core/exported/web_origin_trials.cc
					String[] lineSplit = line.split("(?<=[a-z])(?=:)");
					String ifString = lineSplit[1].substring(1).strip();
					
					if (ifString.startsWith("if")) {
						fileMap.put(lineSplit[0].substring(2), null);
					}
					//System.out.println(lineSplit[0].substring(1));
					
				}
				for (String key : fileMap.keySet()) {
					System.out.println(key);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
