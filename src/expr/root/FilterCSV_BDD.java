package expr.root;

import static info.scce.addlib.cudd.Cudd.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ParseTree;

import com.opencsv.CSVReader;

import expr.antlr.PCparserLexer;
import expr.antlr.PCparserParser;
import expr.Antlr2Expr;
import expr.composite.Expr;
import expr.visitor.BDDbuilder;

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
			CSVReader reader = null;
			
			try {
				//Get the CSVReader instance with specifying the delimiter to be used
			    reader = new CSVReader(new FileReader(args[0]));
			    
			    // initialize antlr2Expr and bddbuilder for parsing
				final Antlr2Expr antlr2Expr = new Antlr2Expr();
				final BDDbuilder bddBuilder = new BDDbuilder();
				
				// create a false BDD for checking satisfiability
				long FF = Cudd_ReadLogicZero(bddBuilder.ddManager);;
			      
			    //Read CSV line by line and use the string array as you want
			    String[] nextLine;			    

			    while ((nextLine = reader.readNext()) != null) {
			    	if (nextLine != null) {
			    		// indicator for adding to the final result
			    		boolean writeToFile = true;
			    		
			    		// get and split string
			    		String currentRow = Arrays.toString(nextLine);
			    		String[] splitPC = currentRow.split("\"condition\":\"");
			    		
			    		// parse the first pc of current row if pcMap does not contain it
			    		String firstPC = splitPC[1].split("\"")[0];
			    		//System.out.println(firstPC);
			    		
			    		if (!pcMap.containsKey(firstPC)) {
			    			ANTLRInputStream input = new ANTLRInputStream(firstPC);
		    				PCparserLexer lexer = new PCparserLexer(input);
		    				CommonTokenStream tokens = new CommonTokenStream(lexer);
		    				PCparserParser parser = new PCparserParser(tokens);
		    		        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
		    		        ParseTree tree = parser.stat(); // parse
		    		        
		    		        // generate the Expr hierarchy for initial string
		    		        Expr expr = antlr2Expr.visit(tree.getChild(0));
		    		        
		    		        // generate the BDD
		    		        expr.accept(bddBuilder);
		    		        
		    		        // store the BDD into the map
		    		        pcMap.put(firstPC, bddBuilder.getBDDaddress());
						}
			    		
			    		Long PCpath = pcMap.get(firstPC);
			    		
			    		
			    		// perform SAT check for current row (path)
			    		for (int i = 2; i < splitPC.length; i++) {
			    			//print splitPC[i].split(",")[0]
			    			//System.out.println(i + ": " + splitPC[i].split(",")[0]);
			    			
			    			String currentPC = splitPC[i].split("\"")[0];
			    			// parse the string if pcMap does not contain it
			    			if (!pcMap.containsKey(currentPC)) {
			    				ANTLRInputStream input = new ANTLRInputStream(currentPC);
			    				PCparserLexer lexer = new PCparserLexer(input);
			    				CommonTokenStream tokens = new CommonTokenStream(lexer);
			    				PCparserParser parser = new PCparserParser(tokens);
			    		        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
			    		        ParseTree tree = parser.stat(); // parse
			    		        
			    		        // generate the Expr hierarchy for initial string
			    		        Expr expr = antlr2Expr.visit(tree.getChild(0));
			    		        
			    		        // generate the BDD
			    		        expr.accept(bddBuilder);
			    		        
			    		        // store the BDD into the map
			    		        pcMap.put(currentPC, bddBuilder.getBDDaddress());
							}
			    			
			    			PCpath = Cudd_bddAnd(BDDbuilder.ddManager, PCpath, pcMap.get(currentPC));
			    			
			    			if (PCpath == FF) {
								writeToFile = false;
								break;
							}
			    		}
			    		
			    		if (writeToFile) {
							System.out.println(currentRow);
						}
			    	}
			   }
			    
//			    for (Map.Entry<String, Pair<Expr, Long>> entry : pcMap.entrySet()) {
//	    			Pair<Expr, Long> exprBDD = entry.getValue();
//	    		    String name = entry.getKey();
//	    		    System.out.println("PC: " + name + "\n"
//	    		    		+ "Expr: " + exprBDD.a + "\n"
//	    		    		+ "BDD address: " + exprBDD.b);
//	    		 }
			} catch (Exception e) {
				e.printStackTrace();
				} finally {
					try {
						reader.close();
						} catch (IOException e) {
							e.printStackTrace();
							}
				}
		}
	}
}
