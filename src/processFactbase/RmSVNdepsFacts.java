package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class RmSVNdepsFacts {
	public static void main(String[] args) {
		if (args.length < 2 ) {
			System.out.println("Usage: "
					+ "Input: two .csv file - svn node and edges file.\n"
					+ "Output: node file that rm those with subversion/deps/"
					+ "edges file that remove the facts containing the nodes.\n"
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
				// args[0] is nodes file
				reader = new BufferedReader(new FileReader(args[0]));
				
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[0].toString().split("/");
				String nodefilename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				
				writer = new BufferedWriter(new FileWriter(nodefilename + ".rmDeps.csv"));
				
				String line = reader.readLine();
				// write the first line
				writer.write(line + "\n");
				
				// rm the nodes with "subversion/deps/" component
				// and save it to nodeSet
				while ((line = reader.readLine()) != null) {
					if (line.contains("subversion/deps/")) {
						// split line
						String[] splitline = line.trim().split("\t");
						// add the node to nodeSet and skip it
						nodeSet.add(splitline[0]);
						continue;
					}
					// write to file with nodes not in "subversion/deps/" component
					writer.write(line + "\n");
				}
				writer.close();
				System.out.println("writing " + nodefilename + ".rmDeps.csv finished.");
				
				/* **************************************************************************
				 * start dealing with edge.csv: generate edge.comp.csv for current comp
				 * **************************************************************************
				 */
				// args[1] is edge file
				reader = new BufferedReader(new FileReader(args[1]));
				
				// capture input file name, and use it in output file
				splitInputFileName = args[1].toString().split("/");
				String edgefilename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];

				writer = new BufferedWriter(new FileWriter(edgefilename + ".rmDeps.csv"));
				
				// iterate the edge file, remove those lines that contains the nodes
				while ((line = reader.readLine()) != null) {
					boolean writeToFile = true;
					for( String curKey : nodeSet ){
			            if (line.contains(curKey)) {
			            	writeToFile = false;
			            	break;
						}
			        }
					
					if (writeToFile) {
						writer.write(line+ "\n");
					}
				}
				
				writer.close();
				System.out.println("writing " + edgefilename + ".rmDeps.csv finished.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
