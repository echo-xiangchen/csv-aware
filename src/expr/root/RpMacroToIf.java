package expr.root;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class RpMacroToIf {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(
					"Usage: " + "Input: files with macros (#if)\n"
							+ "Output: files removing #if.\n"
							+ "Warning: You do not include any .c files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				String output = "";
				String line;
				
				while ((line = reader.readLine()) != null) {
					String replaceLineString = "";
					
						if (line.trim().startsWith("#if")) {
							if (line.trim().startsWith("#ifdef") && line.contains("ENABLE_")) {
								replaceLineString = line.replace("#ifdef ", "if (") + ") {";
							}
							else if (line.trim().startsWith("#ifndef") && line.contains("ENABLE_")) {
								replaceLineString = line.replace("#ifndef ", "if (!") + ") {";
							}
							else {
								replaceLineString = line.replace("#if ", "if (") + ") {";
							}
						}
						else if (line.trim().startsWith("#elif")) {
							replaceLineString = line.replace("#elif ", "} else if (") + ") {";
						}
						else if (line.trim().startsWith("#else")) {
							replaceLineString = line.replace("#else", "} else {");
						}
						else if (line.trim().startsWith("#endif")) {
							replaceLineString = line.replace("#endif", "}");
						}
						else {
						replaceLineString = line;
						}
					output = output + replaceLineString + "\n";
				}
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				//writer = new BufferedWriter(new FileWriter(filename + ".replaced.c"));
				writer = new BufferedWriter(new FileWriter("replaced.c"));
				System.out.println("writing " + filename + ".replaced.c");
				
				writer.write(output);
				writer.close();
				System.out.println("writing " + filename + ".desugared.replaced.c finished.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
