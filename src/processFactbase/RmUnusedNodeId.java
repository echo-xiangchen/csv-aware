package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class RmUnusedNodeId {
	public static void main(String[] args) {
		if (args.length < 2 ) {
			System.out.println("Usage: "
					+ "Input: two .csv file - node and edges file.\n"
					+ "Output: edge file that rm start and end id that do not appear in the node file.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			HashSet<String> nodeSet = new HashSet<>();
			
			try {
				/* **************************************************************************
				 * start dealing with node.csv: generate node.comp.csv for current comp
				 * **************************************************************************
				 */
				// args[0] is node file
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line = reader.readLine();
				
				// capture node ids
				while ((line = reader.readLine()) != null) {
					// split line and save node id to nodeSet
					String[] splitline = line.trim().split("\t");
					nodeSet.add(splitline[0]);
				}
				System.out.println("finish processing node.csv");
				
				/* **************************************************************************
				 * start dealing with edge.csv: generate edge.comp.csv for current comp
				 * **************************************************************************
				 */
				// args[1] is edge file
				reader = new BufferedReader(new FileReader(args[1]));
				
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[1].toString().split("/");
				String edgefilename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				
				writer = new BufferedWriter(new FileWriter(edgefilename + ".rmInvalidNode.csv"));
				
				// write the first line
				line = reader.readLine();
				writer.write(line + "\n");
				
				while ((line = reader.readLine()) != null) {
					// split line
					String[] splitline = line.trim().split("\t");
					if (nodeSet.contains(splitline[0]) && nodeSet.contains(splitline[1])) {
						writer.write(line + "\n");
					}
				}
				writer.close();
				System.out.println("writing " + edgefilename + ".rmInvalidNode.csv finished.");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
