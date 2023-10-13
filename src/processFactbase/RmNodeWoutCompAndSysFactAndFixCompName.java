package processFactbase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

public class RmNodeWoutCompAndSysFactAndFixCompName {
	public static void main(String[] args) {
		if (args.length < 3 ) {
			System.out.println("Usage: "
					+ "Input: three .csv file - 1. nodes without file name, 2. node and edges file.\n"
					+ "Output: node file that rm those without comp, and sys facts, and rm file name in comp"
					+ "edges file that remove the facts containing the nodes.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			BufferedWriter compWriter;
			
			HashSet<String> nodeSet = new HashSet<>();
			HashSet<String> compSet = new HashSet<>();
			
			try {
				// args[0] is nodes without filename
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
				
				// first capture node id
				while ((line = reader.readLine()) != null) {
					String[] splitId = line.trim().split("\t");
					nodeSet.add(splitId[0]);
				}
				
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[1].toString().split("/");
				String nodefilename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
				
				writer = new BufferedWriter(new FileWriter(nodefilename + ".rmNodeWoutCompAndSysFactAndFixCompName.csv"));
				compWriter = new BufferedWriter(new FileWriter(nodefilename + ".comp.csv"));
				
				/* **************************************************************************
				 * start dealing with node.csv: generate node.comp.csv for current comp
				 * **************************************************************************
				 */
				// args[1] is node file
				reader = new BufferedReader(new FileReader(args[1]));
				
				line = reader.readLine();
				
				// write the first line
				writer.write("id:ID" + "\t" + ":LABEL" + "\t" + "comp"+ "\t" + "condition" + "\n");
				
				// rm node without comp and sys facts
				// and fix the component name
				while ((line = reader.readLine()) != null) {
					// check if this is node without comp
					boolean isNodeWoutComp = false;
					boolean writeToFile = true;
					for( String curKey : nodeSet ){
						// if this is the node without comp
						// set isNodeWoutComp to true
			            if (line.contains(curKey)) {
			            	isNodeWoutComp = true;
			            	break;
						}
			        }
					
					// if this line is the node without comp, skip this line and continue
					if (isNodeWoutComp) {
						continue;
					}
					
					// split line
					String[] splitline = line.trim().split("\t");
					String compOrigin = splitline[3];
					String comp = "";
					// check if this is sys facts, if yes, skip writing to output file
					if (!compOrigin.contains("/home/echo/Documents")) {
						writeToFile = false;
						
						// need to also save the node id to nodeSet
						// so it will be deleted from edges file
						nodeSet.add(splitline[0]);
						
					} else {
						String[] compOriginSplit = compOrigin.trim().split("/");

						//for (int i = 4; i < compOriginSplit.length - 1; i++) {
						for (int i = 4; i <= 5; i++) {
							comp = comp + compOriginSplit[i] + "/";
						}
					}
					// save the comp to compSet for later output
					compSet.add(comp);
					
					if (writeToFile) {
						// write the splited parts to file
						writer.write(splitline[0] + "\t" + splitline[1] 
								+ "\t" + comp + "\t" + splitline[2] + "\n");
					}
					
				}
				
				writer.close();
				System.out.println("writing " + nodefilename + ".rmNodeWoutCompAndSysFactAndFixCompName.csv finished.");
				
				/* **************************************************************************
				 * start dealing with edge.csv: generate edge.comp.csv for current comp
				 * **************************************************************************
				 */
				// args[2] is edge file
				reader = new BufferedReader(new FileReader(args[2]));
				
				// capture input file name, and use it in output file
				splitInputFileName = args[2].toString().split("/");
				String edgefilename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];

				writer = new BufferedWriter(new FileWriter(edgefilename + ".rmNodeWithoutComp.csv"));

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
				System.out.println("writing " + edgefilename + ".rmNodeWithoutComp.csv finished.");
				
				
				// finally write the comp list
				for( String curKey : compSet ){
					if (!curKey.isBlank()) {
						compWriter.write(curKey + "\n");
					}
		        }
				
				compWriter.close();
				System.out.println("writing " + nodefilename + ".comp.csv finished.");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
