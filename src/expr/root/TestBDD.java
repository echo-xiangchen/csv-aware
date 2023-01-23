package expr.root;

import static info.scce.addlib.cudd.Cudd.Cudd_ReadLogicZero;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import expr.Antlr2BDD;
import expr.antlr.PCparserLexer;
import expr.antlr.PCparserParser;
import expr.composite.Expr;
import expr.visitor.BDDbuilder;

public class TestBDD {
	public static void main(String[] args) {
		// initialize antlr2Expr and bddbuilder for parsing
		final Antlr2BDD antlr2Expr = new Antlr2BDD();
		final BDDbuilder bddBuilder = new BDDbuilder();
		
		// create a false BDD for checking satisfiability
		long FF = Cudd_ReadLogicZero(bddBuilder.ddManager);;
		
		
		ANTLRInputStream input = new ANTLRInputStream(args[0]);
		PCparserLexer lexer = new PCparserLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		PCparserParser parser = new PCparserParser(tokens);
        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
        ParseTree tree = parser.stat(); // parse
        
        // generate the Expr hierarchy for initial string
        Long address1 = antlr2Expr.visit(tree.getChild(0));
        

        System.out.println(args[0] + ": " + address1);
        if (address1 == FF) {
			System.err.println(args[0] + " is unsatisfiable");
		} else {
			System.err.println(args[0] + " is satisfiable");
		}
        
        input = new ANTLRInputStream(args[1]);
		lexer = new PCparserLexer(input);
		tokens = new CommonTokenStream(lexer);
		parser = new PCparserParser(tokens);
        parser.setBuildParseTree(true);      // tell ANTLR to build a parse tree
        tree = parser.stat(); // parse
        
        // generate the Expr hierarchy for initial string
        Long address2 = antlr2Expr.visit(tree.getChild(0));
        

        System.out.println(args[1] + ": " + address2);
        if (address2 == FF) {
			System.err.println(args[1] + " is unsatisfiable");
		} else {
			System.err.println(args[1] + " is satisfiable");
		}
	}
}
