package processFactbase;

import static info.scce.addlib.cudd.Cudd.Cudd_ReadLogicZero;
import static info.scce.addlib.cudd.Cudd.Cudd_Ref;
import static info.scce.addlib.cudd.Cudd.Cudd_bddAnd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import expr.Antlr2BDD;
import expr.antlr.PCparserLexer;
import expr.antlr.PCparserParser;

public class GenOverheadFactbase {
	public static void main(String[] args) {
		if (args.length < 3 ) {
			System.out.println("Usage: "
					+ "Input: 3 .csv files: 1. list of feature variables; 2. node.csv; 3 edges.csv.\n"
					+ "Output: filtered node.csv and edge.csv that all feature variable is on.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;
			
			// hashmap stores the <pc, bddAddress> Map
			Map<String, Long> varPCMap = new LinkedHashMap<String, Long>();
			
			// hashset stores the nodes that are unsatisfiable when all features are on
			// we need to also remove these nodes in the edge.csv
			HashSet<String> nodeSet = new HashSet<>();
			
			try {
				// first read the list of feature variables (for generating the big conjunction)
				reader = new BufferedReader(new FileReader(args[0]));
				
				System.out.println("Start generating conjunction for the list of feature variables.");
				
				// initialize antlr2Expr and bddbuilder for parsing
				final Antlr2BDD antlr2bdd = new Antlr2BDD();
				
				// create a false BDD for checking satisfiability
				long FF = Cudd_ReadLogicZero(antlr2bdd.ddManager);;
				
				// first parse and generate bdd for the first feature variable
				String featureVar = reader.readLine();
				
				if (!varPCMap.containsKey(featureVar)) {
	    			CharStream input = CharStreams.fromString(featureVar);
    				PCparserLexer lexer = new PCparserLexer(input);
    				CommonTokenStream tokens = new CommonTokenStream(lexer);
    				PCparserParser parser = new PCparserParser(tokens);
    		        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
    		        ParseTree tree = parser.stat(); // parse
    		        
    		        // generate the bdd address for initial string
    		        Long bddaddress = antlr2bdd.visit(tree.getChild(0));
    		        
    		        // generate the BDD
    		        //expr.accept(bddBuilder);
    		        
    		        // store the BDD into the map
    		        varPCMap.put(featureVar, bddaddress);  
				}
				
				// accFeatureVar stores the accumulative bdd value for the list of feature variables
		        Long accFeatureVar = varPCMap.get(featureVar);
		        
		        while ((featureVar = reader.readLine()) != null) {
		        	// parse the string if pcMap does not contain it
	    			if (!varPCMap.containsKey(featureVar)) {
	    				CharStream input = CharStreams.fromString(featureVar);
	    				PCparserLexer lexer = new PCparserLexer(input);
	    				CommonTokenStream tokens = new CommonTokenStream(lexer);
	    				PCparserParser parser = new PCparserParser(tokens);
	    		        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
	    		        ParseTree tree = parser.stat(); // parse
	    		        
	    		        // generate the Expr hierarchy for initial string
	    		        Long bddaddress = antlr2bdd.visit(tree.getChild(0));
	    		        
	    		        // generate the BDD
	    		        //expr.accept(bddBuilder);
	    		        
	    		        // store the BDD into the map
	    		        varPCMap.put(featureVar, bddaddress);
					}
	    			
	    			accFeatureVar = Cudd_bddAnd(antlr2bdd.ddManager, accFeatureVar, varPCMap.get(featureVar));
	    			Cudd_Ref(accFeatureVar);
		        }
		        
		        System.out.println("Finish generating conjunction for the list of feature variables.");
		        
		        // capture input node.csv file name, and use it in output file
				String[] splitNodeFileName = args[1].toString().split("/");
				String nodefilename = splitNodeFileName[splitNodeFileName.length - 1].split("\\.")[0];
				
				// capture input edge.csv file name, and use it in output file
				String[] splitEdgeFileName = args[2].toString().split("/");
				String edgefilename = splitEdgeFileName[splitEdgeFileName.length - 1].split("\\.")[0];
		        
				/* **************************************************************************
				 * start dealing with node.csv: generate node.comp.csv for current comp
				 * **************************************************************************
				 */
		        // then read node.csv and filter those facts that is unsatisfiable when all feature is on
				reader = new BufferedReader(new FileReader(args[1]));
				writer = new BufferedWriter(new FileWriter(nodefilename + ".overhead.csv"));
		        System.out.println("Start filtering " + args[1]);
		        
		        // create line indicator for node.csv 
	            String nodeLine;
	            
	            // skip the first line - it's the title of node.csv
	            nodeLine = reader.readLine();
	            // write the first line: the header of node.csv
	            writer.write(nodeLine + "\n");
	            
	            int linenum = 2;
	            // start iterating node.csv and save those nodes are satisfiable when all features are on
	            while ((nodeLine = reader.readLine()) != null) {
	            	String[] splitNodeLine = nodeLine.trim().split("\t");
	            	//String nodelinepc = splitNodeLine[3];
	            	
	            	if (splitNodeLine.length <= 3) {
						writer.write(nodeLine + "\n");
					} else {
						String nodelinepc = splitNodeLine[3];
						// if varPCMap do not contain the pc for current line, parse it and generate bdd
						if (!varPCMap.containsKey(nodelinepc)) {
							CharStream input = CharStreams.fromString(nodelinepc);
		    				PCparserLexer lexer = new PCparserLexer(input);
		    				CommonTokenStream tokens = new CommonTokenStream(lexer);
		    				PCparserParser parser = new PCparserParser(tokens);
		    		        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
		    		        ParseTree tree = parser.stat(); // parse
		    		        
		    		        // generate the Expr hierarchy for initial string
		    		        Long bddaddress = antlr2bdd.visit(tree.getChild(0));
		    		        
		    		        // generate the BDD
		    		        //expr.accept(bddBuilder);
		    		        
		    		        // store the BDD into the map
		    		        varPCMap.put(nodelinepc, bddaddress);
						}
						
						// curNodeLinePC is the bdd for the oc of current line (node)
						Long curNodeLinePC = varPCMap.get(nodelinepc);
						
						// generate the conjunction of accFeatureVar and curNodeLinePC
						// if it's satisfiable, write to file
		    			Long checkSAT = Cudd_bddAnd(antlr2bdd.ddManager, accFeatureVar, curNodeLinePC);
		    			Cudd_Ref(checkSAT);
		    			
		    			if (checkSAT != FF) {
		    				writer.write(nodeLine + "\n");
						} else {
							nodeSet.add(splitNodeLine[0]);
						}
					}
	            	System.out.println("finished line " + linenum + " of " + args[1]);
	            	linenum++;
	            }
	            writer.close();
	            System.out.println("Finish filtering " + args[1]);
	            
	            /* **************************************************************************
				 * start dealing with edge.csv: generate edge.comp.csv for current comp
				 * **************************************************************************
				 */
	            // then read edge.csv and filter those facts that is unsatisfiable when all feature is on
				reader = new BufferedReader(new FileReader(args[2]));
				writer = new BufferedWriter(new FileWriter(edgefilename + ".overhead.csv"));
		        System.out.println("Start filtering " + args[2]);
		        
		        // create line indicator for edge.csv 
	            String edgeLine;
	            
	            // skip the first line - it's the title of edge.csv
	            edgeLine = reader.readLine();
	            // write the first line: the header of edge.csv
	            writer.write(edgeLine + "\n");
	            
	            linenum = 2;
	            // start iterating edge.csv and save those edges are satisfiable when all features are on
	            while ((edgeLine = reader.readLine()) != null) {
	            	String[] splitEdgeLine = edgeLine.trim().split("\t");
	            	
	            	// check if current edge's start id or end id is 
	            	// the nodes that are not satisfiable when all features are on
	            	
	            	boolean writeToFile = true;
					for( String curKey : nodeSet ){
			            if (edgeLine.contains(curKey)) {
			            	writeToFile = false;
			            	break;
						}
			        }
					
					// only when writeToFile = true: current edge do not contain those nodes
	            	// continue to filter this edge
					if (writeToFile) {
						if (splitEdgeLine.length <= 3) {
							writer.write(edgeLine + "\n");
						} else {
							String edgelinepc = splitEdgeLine[3];
							// if varPCMap do not contain the pc for current line, parse it and generate bdd
							if (!varPCMap.containsKey(edgelinepc)) {
								CharStream input = CharStreams.fromString(edgelinepc);
			    				PCparserLexer lexer = new PCparserLexer(input);
			    				CommonTokenStream tokens = new CommonTokenStream(lexer);
			    				PCparserParser parser = new PCparserParser(tokens);
			    		        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
			    		        ParseTree tree = parser.stat(); // parse
			    		        
			    		        // generate the Expr hierarchy for initial string
			    		        Long bddaddress = antlr2bdd.visit(tree.getChild(0));
			    		        
			    		        // generate the BDD
			    		        //expr.accept(bddBuilder);
			    		        
			    		        // store the BDD into the map
			    		        varPCMap.put(edgelinepc, bddaddress);
							}
							
							// curEdgeLinePC is the bdd for the pc of current line (edge)
							Long curEdgeLinePC = varPCMap.get(edgelinepc);
							
							// generate the conjunction of accFeatureVar and curEdgeLinePC
							// if it's satisfiable, write to file
			    			Long checkSAT = Cudd_bddAnd(antlr2bdd.ddManager, accFeatureVar, curEdgeLinePC);
			    			Cudd_Ref(checkSAT);
			    			
			    			if (checkSAT != FF) {
			    				writer.write(edgeLine + "\n");
							}
						}
					}
	            	
	            	System.out.println("finished line " + linenum + " of " + args[2]);
	            	linenum++;
	            }
	            writer.close();
	            System.out.println("Finish filtering " + args[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
