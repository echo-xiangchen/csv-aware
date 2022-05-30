package expr.root;

import java.io.BufferedReader;
import java.io.FileReader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import expr.Antlr2ExprExtend;
import expr.antlrExtend.*;
import expr.composite.Expr;
import expr.visitor.FeatureVarextractor;

public class ExVarInCondition {
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains edge result from Rex.\n"
					+ "Output: a list of feature variables.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					
					// initialize antlr2Expr and bddbuilder for parsing
					final Antlr2ExprExtend antlr2Expr = new Antlr2ExprExtend();
					final FeatureVarextractor varExtractor = new FeatureVarextractor();
					
					ANTLRInputStream input = new ANTLRInputStream(line);
    				PCparserLexer lexer = new PCparserLexer(input);
    				CommonTokenStream tokens = new CommonTokenStream(lexer);
    				PCparserParser parser = new PCparserParser(tokens);
    		        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
    		        ParseTree tree = parser.stat(); // parse
    		        
    		        // generate the Expr hierarchy for initial string
    		        Expr expr = antlr2Expr.visit(tree.getChild(0));
    		        
    		        // generate the BDD
    		        expr.accept(varExtractor);
    		        
				}
				for (String key : FeatureVarextractor.varMap.keySet()) {
					if (Character.compare(key.charAt(0), 'k') == 0 
							|| Character.compare(key.charAt(0), 'K') == 0) {
						System.out.println(key);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
