package expr.root;

import static info.scce.addlib.cudd.Cudd.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import expr.antlr.PCparserLexer;
import expr.antlr.PCparserParser;
import expr.Antlr2BDD;

public class FilterCSV_BDD {

	public static void main(String[] args) {
		// hashmap stores the <pc, bddAddress> Map
		Map<String, Long> pcMap = new LinkedHashMap<String, Long>();
		
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains analysis result from Neo4j.\n"
					+ "Output: a .csv file that only contains valid paths (variability-aware analysis result)"
					+ "Warning: You do not include any .csv files.");
		}
		else {
			BufferedReader reader;
			BufferedWriter writer;
			
			try {
				//Get the CSVReader instance with specifying the delimiter to be used
				reader = new BufferedReader(new FileReader(args[0]));
				
				// capture input file name, and use it in output file
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];
			    
				writer = new BufferedWriter(new FileWriter(filename + ".awareBDD.csv"));
				
			    // initialize antlr2Expr and bddbuilder for parsing
				final Antlr2BDD antlr2Expr = new Antlr2BDD();
				
				// create a false BDD for checking satisfiability
				long FF = Cudd_ReadLogicZero(antlr2Expr.ddManager);;
			      
				// skip the first line - it's the title of Neo4j's result
				String line = reader.readLine();	
				
				int linenum = 2;
			    while ((line = reader.readLine()) != null) {
			    	// indicator for adding to the final result
		    		boolean writeToFile = true;
			    	// some lines might be empty
					if (line.equals("\"\"")) {
						// dont use break here! it will quit the while loop!
						// use continue.
						//break;
						writeToFile = true;
					} else {
						
			    		// get and split string
			    		String[] splitPC = line.split("condition\"\":\"\"");
			    		
			    		// parse the first pc of current row if pcMap does not contain it
			    		String firstPC = splitPC[1].split("\"\"")[0];
			    		
			    		if (firstPC.isEmpty()) {
			    			firstPC = "True";
						}
			    		//System.out.println(firstPC);
			    		
			    		if (!pcMap.containsKey(firstPC)) {
			    			CharStream input = CharStreams.fromString(firstPC);
		    				PCparserLexer lexer = new PCparserLexer(input);
		    				CommonTokenStream tokens = new CommonTokenStream(lexer);
		    				PCparserParser parser = new PCparserParser(tokens);
		    		        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
		    		        ParseTree tree = parser.stat(); // parse
		    		        
		    		        // generate the bdd address for initial string
		    		        Long bddaddress = antlr2Expr.visit(tree.getChild(0));
		    		        
		    		        // generate the BDD
		    		        //expr.accept(bddBuilder);
		    		        
		    		        // store the BDD into the map
		    		        pcMap.put(firstPC, bddaddress);
						}
			    		
			    		Long PCpath = pcMap.get(firstPC);
			    		
			    		
			    		// perform SAT check for current row (path)
			    		for (int i = 2; i < splitPC.length; i++) {
			    			//print splitPC[i].split(",")[0]
			    			//System.out.println(i + ": " + splitPC[i].split(",")[0]);
			    			
			    			String currentPC = splitPC[i].split("\"\"")[0];
			    			if (currentPC.isEmpty()) {
								currentPC = "True";
							}
			    			// parse the string if pcMap does not contain it
			    			if (!pcMap.containsKey(currentPC)) {
			    				CharStream input = CharStreams.fromString(currentPC);
			    				PCparserLexer lexer = new PCparserLexer(input);
			    				CommonTokenStream tokens = new CommonTokenStream(lexer);
			    				PCparserParser parser = new PCparserParser(tokens);
			    		        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
			    		        ParseTree tree = parser.stat(); // parse
			    		        
			    		        // generate the Expr hierarchy for initial string
			    		        Long bddaddress = antlr2Expr.visit(tree.getChild(0));
			    		        
			    		        // generate the BDD
			    		        //expr.accept(bddBuilder);
			    		        
			    		        // store the BDD into the map
			    		        pcMap.put(currentPC, bddaddress);
							}
			    			
			    			PCpath = Cudd_bddAnd(antlr2Expr.ddManager, PCpath, pcMap.get(currentPC));
			    			Cudd_Ref(PCpath);
			    			
			    			if (PCpath == FF) {
								writeToFile = false;
								break;
							}
			    		}
					}
		    		
		    		
		    		if (writeToFile) {
						writer.write(line + "\n");
					}
		    		System.out.println("finished line " + linenum);
			    	linenum++;
			   }
				
				writer.close();
				System.out.println("writing " + filename + ".awareBDD.csv finished.");
			    
//			    for (Map.Entry<String, Pair<Expr, Long>> entry : pcMap.entrySet()) {
//	    			Pair<Expr, Long> exprBDD = entry.getValue();
//	    		    String name = entry.getKey();
//	    		    System.out.println("PC: " + name + "\n"
//	    		    		+ "Expr: " + exprBDD.a + "\n"
//	    		    		+ "BDD address: " + exprBDD.b);
//	    		 }
			} catch (Exception e) {
				e.printStackTrace();
				}
		}
	}
}
