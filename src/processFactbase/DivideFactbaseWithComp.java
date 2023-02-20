package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class DivideFactbaseWithComp {
	public static void main(String[] args) {
		if (args.length < 3 ) {
			System.out.println("Usage: "
					+ "Input: 3 .csv files: 1. list of comps; 2. node.csv; 3 edges.csv.\n"
					+ "Output: several .csv files: list of node.comp.csv that are split from node.csv based on comp;"
					+ "list of edges.comp.csv that split from edges.csv based on comp.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			HashSet<String> compSet = new HashSet<>();
			
			try {
				// first store the list of comps from args[0]
				reader = new BufferedReader(new FileReader(args[0]));
				
				String compLine;
				
				while ((compLine = reader.readLine()) != null) {
					compSet.add(compLine);
				}
				
				// capture input node.csv file name, and use it in output file
				String[] splitNodeFileName = args[1].toString().split("/");
				String nodefilename = splitNodeFileName[splitNodeFileName.length - 1].split("\\.")[0];
				
				// capture input edge.csv file name, and use it in output file
				String[] splitEdgeFileName = args[2].toString().split("/");
				String edgefilename = splitEdgeFileName[splitEdgeFileName.length - 1].split("\\.")[0];
				
				
				// Then based on compSet, split node.csv and edge.csv
				// for each component do the same spliting
				for( String curcomp : compSet ){
					System.out.println("Start generating files for comp: " + curcomp);
					/* **************************************************************************
					 * start dealing with node.csv: generate node.comp.csv for current comp
					 * **************************************************************************
					 */
					// create line indicator for node.csv 
		            String nodeLine;
		            
		            // hashset that stores the nodes for current comp
		            HashSet<String> nodeSet = new HashSet<>();
		            
		            // create writer for node.comp.csv
		            //writer = new BufferedWriter(new FileWriter(nodefilename + "." + curcomp.replaceAll("/", ".") + "csv"));
		            writer = new BufferedWriter(new FileWriter("compVPwriteFact/" + nodefilename + "." + curcomp.replaceAll("/", ".") + "csv"));
		            System.out.println("Start writing " + nodefilename + "." + curcomp.replaceAll("/", ".") + "csv");
		            // read node.csv: args[1]
		            reader = new BufferedReader(new FileReader(args[1]));
		            nodeLine = reader.readLine();
		            
		            // write the first line: the header of node.csv
		            writer.write(nodeLine + "\n");
		            
		            // start iterating node.csv and save those nodes belong to current component
		            while ((nodeLine = reader.readLine()) != null) {
		            	String[] splitNodeLine = nodeLine.trim().split("\t");
		            	String nodeID = splitNodeLine[0];
		            	String nodeComp = splitNodeLine[2];
		            	
		            	// if node belong to current comp
		            	// save it to nodeSet, and write the fact to node.comp.csv
		            	if (nodeComp.equals(curcomp)) {
		            		nodeSet.add(nodeID);
							writer.write(nodeLine + "\n");
						}
		            }
		            
		            writer.close();
		            System.out.println("Finish writing " + nodefilename + "." + curcomp.replaceAll("/", ".") + "csv");
		            
		            /* **************************************************************************
					 * start dealing with edge.csv: generate edge.comp.csv for current comp
					 * **************************************************************************
					 */
		            // create line indicator for edge.csv 
		            String edgeLine;
		            // create writer for node.comp.csv
		            //writer = new BufferedWriter(new FileWriter(edgefilename + "." + curcomp.replaceAll("/", ".") + "csv"));
		            writer = new BufferedWriter(new FileWriter("compVPwriteFact/" + edgefilename + "." + curcomp.replaceAll("/", ".") + "csv"));
		            System.out.println("Start writing " + edgefilename + "." + curcomp.replaceAll("/", ".") + "csv");
		            
		            // read edge.csv: args[2]
		            reader = new BufferedReader(new FileReader(args[2]));
		            edgeLine = reader.readLine();
		            
		            // write first line: header of edge.csv
		            writer.write(edgeLine + "\n");
		            
		            // start iterating edge.csv and save those edges belong to current component
		            while ((edgeLine = reader.readLine()) != null) {
		            	String[] splitEdgeLine = edgeLine.trim().split("\t");
		            	String startID = splitEdgeLine[0];
		            	String endID = splitEdgeLine[1];
		            	String edgeType = splitEdgeLine[2];
		            	
		            	// only when startID and endID are in nodeSet for current comp
		            	// and the edge type is varWrite or parWrite
		            	// save it to the file
		            	if ((nodeSet.contains(startID) && nodeSet.contains(endID))
		            			&& (edgeType.equals("varWrite") || edgeType.equals("parWrite"))) {
							writer.write(edgeLine + "\n");
						}
		            }
		            
		            writer.close();
		            System.out.println("Finish writing " + edgefilename + "." + curcomp.replaceAll("/", ".") + "csv");   
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
