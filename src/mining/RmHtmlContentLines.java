package mining;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RmHtmlContentLines {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a dataset from MSR'24.\n"
					+ "Output: same file but remove the HtmlContent lines.\n"
					+ "Warning: You do not include any file.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				
				//Get the CSVReader instance with specifying the delimiter to be used
				reader = new BufferedReader(new FileReader(args[0]));
				writer = new BufferedWriter(new FileWriter(filename + ".rmHtmlContent.json"));
				
				String line;
	            while ((line = reader.readLine()) != null) {
	                // Check if line starts with the specified string
	                if (!line.trim().startsWith("\"HTMLContent\":")) {
	                    writer.write(line);
	                    writer.newLine();
	                } else {
	                	writer.write("\"HTMLContent\":\"\"");
	                    writer.newLine();
					}
	            }
	            writer.close();
				System.out.println("writing " + filename + ".rmHtmlContent.json finished.");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
