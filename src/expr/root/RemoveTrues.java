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

public class RemoveTrues {

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
			      
			    //Read CSV line by line and use the string array as you want
			    String[] nextLine;	
			    String TT = "True";

			    while ((nextLine = reader.readNext()) != null) {
			    	if (nextLine != null) {
			    		List<String> pcList = new ArrayList<String>();
			    		// indicator for adding to the final result
			    		boolean writeToFile = false;
			    		
			    		// get and split string
			    		String currentRow = Arrays.toString(nextLine);
			    		String[] splitPC = currentRow.split("\"condition\":\"");
			    		
			    		// parse the first pc of current row if pcMap does not contain it
			    		pcList.add(splitPC[1].split("\"")[0]);
			    		//System.out.println(firstPC);
			    		
			    		for (int i = 2; i < splitPC.length; i++) {
				    		//print splitPC[i].split(",")[0]
				    		//System.out.println(i + ": " + splitPC[i].split(",")[0]);
				    		pcList.add(splitPC[i].split("\"")[0]);
						}
			    		
			    		for (int i = 0; i < pcList.size(); i++) {
							if (!pcList.get(i).equals(TT)) {
								writeToFile = true;
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
