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
import expr.Antlr2BDD;
import expr.composite.Expr;
import expr.visitor.BDDbuilder;

public class AddLineNum {

	public static void main(String[] args) {
		// hashmap stores the <pc, bddAddress> Map
		Map<String, Long> pcMap = new LinkedHashMap<String, Long>();
		
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains analysis result from Neo4j.\n"
					+ "Output: a .csv file with line numbers.\n"
					+ "Warning: You do not include any .csv files.");
		}
		else {
			CSVReader reader = null;
			
			try {
				//Get the CSVReader instance with specifying the delimiter to be used
			    reader = new CSVReader(new FileReader(args[0]));
			      
			    //Read CSV line by line and use the string array as you want
			    String[] nextLine;			    

			    int num = 1;
			    while ((nextLine = reader.readNext()) != null) {
			    	if (nextLine != null) {
			    		// get and split string
			    		String currentRow = "Line" + num + ": " + Arrays.toString(nextLine);
			    		System.out.println(currentRow);
			    		}
			    	num++;
			    	}
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
