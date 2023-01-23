package expr;

import java.util.*;
import expr.antlr.*;
import expr.antlr.PCparserParser.*;
import com.microsoft.z3.*;

public class Antlr2z3 extends PCparserBaseVisitor<BoolExpr> {
	
	
	/* *****************************************************************************************
	 * TODO Methods for boolExpr rule
	 * *****************************************************************************************
	 */
	// hashmap stores the variables
		private static Map<String, BoolExpr> varMap = new LinkedHashMap<String, BoolExpr>();
		
		// create static context
		public static Context z3ctx = new Context();
	
	// array list that stores all the addresses
	//private static ArrayList<Long> address = new ArrayList<Long>();
	
	// Negation
	@Override
	public BoolExpr visitNot(NotContext ctx) {
		return z3ctx.mkNot(visit(ctx.boolExpr()));
	}
	
	// Conjunction
	@Override
	public BoolExpr visitAnd(AndContext ctx) {
		return z3ctx.mkAnd(visit(ctx.boolExpr(0)), visit(ctx.boolExpr(1)));
	}
	
	// Disjunction
	@Override
	public BoolExpr visitOr(OrContext ctx) {
		return z3ctx.mkOr(visit(ctx.boolExpr(0)), visit(ctx.boolExpr(1)));
	}
		
	// boolean true declaration
		@Override
		public BoolExpr visitBoolTrue(BoolTrueContext ctx) {
			return z3ctx.mkTrue();
		}
		
		// boolean false declaration
		@Override
		public BoolExpr visitBoolFalse(BoolFalseContext ctx) {
			return z3ctx.mkFalse();
		}
		
	// boolean variable verification
	@Override
	public BoolExpr visitBoolVar(BoolVarContext ctx) {
		
		// need to check if the variable has been created
		if (!varMap.containsKey(ctx.ID().getText())) {
			// store the variable to the map
			varMap.put(ctx.ID().getText(), z3ctx.mkBoolConst(ctx.ID().getText()));
		}
		
		//address.add(varMap.get(boolVar.name));
		return varMap.get(ctx.ID().getText());
	}
	
	
	// parentheses
	@Override
	public BoolExpr visitParen(ParenContext ctx) {
		return visit(ctx.boolExpr());
	}
}
